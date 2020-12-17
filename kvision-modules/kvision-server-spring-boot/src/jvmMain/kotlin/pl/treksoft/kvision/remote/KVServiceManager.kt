/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.treksoft.kvision.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json
import org.springframework.web.reactive.socket.WebSocketSession
import kotlin.reflect.KClass

typealias RequestHandler = suspend (ServerRequest, ThreadLocal<ServerRequest>, ApplicationContext) -> ServerResponse
/**
 * Multiplatform service manager for Spring Boot.
 */
@Suppress("LargeClass", "TooManyFunctions", "BlockingMethodInNonBlockingContext")
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) : KVServiceMgr<T>,
    KVServiceBinder<T>() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }

    val routeMapRegistry = createRouteMapRegistry<RequestHandler>()
    val webSocketsRequests: MutableMap<String, suspend (
        WebSocketSession, ThreadLocal<WebSocketSession>, ApplicationContext, ReceiveChannel<String>, SendChannel<String>
    ) -> Unit> = mutableMapOf()

    val mapper = createDefaultObjectMapper()
    var counter: Int = 0

    /**
     * @suppress internal function
     */
    @Suppress("DEPRECATION")
    suspend fun initializeService(service: T, req: ServerRequest) {
        if (service is WithRequest) {
            service.serverRequest = req
        }
        if (service is WithWebSession) {
            val session = req.session().awaitSingle()
            service.webSession = session
        }
        if (service is WithPrincipal) {
            val principal = req.principal().awaitSingle()
            service.principal = principal
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param method a HTTP method
     * @param route a route
     * @param function a function of the receiver
     */
    @Suppress("TooGenericExceptionCaught")
    override fun bind(
        method: HttpMethod,
        route: String?,
        function: suspend T.(params: List<String?>) -> Any?
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, tlReq, ctx ->
            tlReq.set(req)
            val service = ctx.getBean(serviceClass.java)
            tlReq.remove()
            initializeService(service, req)
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                JsonRpcRequest(req.queryParam("id").map { it.toInt() }.orElse(0), "", listOf())
            } else {
                req.awaitBody()
            }
            ServerResponse.ok().json().bodyValueAndAwait(mapper.writeValueAsString(try {
                val result = function.invoke(service, jsonRpcRequest.params)
                JsonRpcResponse(
                    id = jsonRpcRequest.id,
                    result = mapper.writeValueAsString(result)
                )
            } catch (e: IllegalParameterCountException) {
                JsonRpcResponse(
                    id = jsonRpcRequest.id,
                    error = "Invalid parameters"
                )
            } catch (e: Exception) {
                if (e !is ServiceException) LOG.error(e.message, e)
                JsonRpcResponse(
                    id = jsonRpcRequest.id,
                    error = e.message ?: "Error",
                    exceptionType = e.javaClass.canonicalName
                )
            }))
        }
    }

    /**
     * Binds a given web socket connetion with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    protected actual inline fun <reified PAR1 : Any, reified PAR2 : Any> bind(
        noinline function: suspend T.(ReceiveChannel<PAR1>, SendChannel<PAR2>) -> Unit,
        route: String?
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        webSocketsRequests[routeDef] = { webSocketSession, tlWsSession, ctx, incoming, outgoing ->
            tlWsSession.set(webSocketSession)
            val service = ctx.getBean(serviceClass.java)
            tlWsSession.remove()
            @Suppress("DEPRECATION")
            if (service is WithWebSocketSession) {
                service.webSocketSession = webSocketSession
            }
            @Suppress("DEPRECATION")
            if (service is WithPrincipal) {
                val principal = webSocketSession.handshakeInfo.principal.awaitSingle()
                service.principal = principal
            }
            val requestChannel = Channel<PAR1>()
            val responseChannel = Channel<PAR2>()
            coroutineScope {
                launch {
                    for (p in incoming) {
                        val jsonRpcRequest = getParameter<JsonRpcRequest>(p)
                        if (jsonRpcRequest.params.size == 1) {
                            val par = getParameter<PAR1>(jsonRpcRequest.params[0])
                            requestChannel.send(par)
                        }
                    }
                    requestChannel.close()
                }
                launch {
                    for (p in responseChannel) {
                        val text = mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = 0,
                                result = mapper.writeValueAsString(p)
                            )
                        )
                        outgoing.send(text)
                    }
                    if (!incoming.isClosedForReceive) incoming.cancel()
                }
                launch {
                    function.invoke(service, requestChannel, responseChannel)
                    if (!responseChannel.isClosedForReceive) responseChannel.close()
                }
            }
        }
    }

    /**
     * @suppress internal function
     */
    fun addRoute(
        method: HttpMethod,
        path: String,
        handler: RequestHandler
    ) {
        routeMapRegistry.addRoute(method, path, handler)
    }

    override fun <T> getParameter(str: String?, type: Class<T>): T =
        if (str == null || type == String::class) type.cast(str)
        else mapper.readValue(str, type)
}
