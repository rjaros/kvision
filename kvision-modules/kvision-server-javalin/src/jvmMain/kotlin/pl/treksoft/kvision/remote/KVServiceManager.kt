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

import com.google.inject.Injector
import io.javalin.Javalin
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.websocket.WsContext
import io.javalin.websocket.WsHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

typealias RequestHandler = (Context) -> Unit
typealias WebsocketHandler = (WsHandler) -> Unit

/**
 * Multiplatform service manager for Javalin.
 */
@Suppress("LargeClass", "TooManyFunctions", "BlockingMethodInNonBlockingContext")
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) : KVServiceMgr<T>,
    KVServiceBinder<T, RequestHandler, WebsocketHandler>() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
        const val KV_WS_INCOMING_KEY = "pl.treksoft.kvision.ws.incoming.key"
        const val KV_WS_OUTGOING_KEY = "pl.treksoft.kvision.ws.outgoing.key"
    }

    /**
     * @suppress internal function
     */
    @Suppress("DEPRECATION")
    fun initializeService(service: T, ctx: Context) {
        if (service is WithContext) {
            service.ctx = ctx
        }
        if (service is WithHttpSession) {
            service.httpSession = ctx.req.session
        }
    }

    /**
     * @suppress internal function
     */
    @Suppress("DEPRECATION")
    fun initializeWsService(service: T, wsCtx: WsContext) {
        if (service is WithWsContext) {
            service.wsCtx = wsCtx
        }
        if (service is WithWsSession) {
            service.wsSession = wsCtx.session
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun createRequestHandler(
        method: HttpMethod,
        function: suspend T.(params: List<String?>) -> Any?
    ): RequestHandler =
        { ctx ->
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                JsonRpcRequest(ctx.queryParam<Int>("id").get(), "", listOf())
            } else {
                ctx.body<JsonRpcRequest>()
            }

            @Suppress("MagicNumber")
            val injector = ctx.attribute<Injector>(KV_INJECTOR_KEY)!!
            val service = injector.getInstance(serviceClass.java)
            initializeService(service, ctx)
            val future = GlobalScope.future {
                try {
                    val result = function.invoke(service, jsonRpcRequest.params)
                    JsonRpcResponse(
                        id = jsonRpcRequest.id,
                        result = deSerializer.serializeNullableToString(result)
                    )
                } catch (e: IllegalParameterCountException) {
                    JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters")
                } catch (e: Exception) {
                    if (e !is ServiceException) LOG.error(e.message, e)
                    JsonRpcResponse(
                        id = jsonRpcRequest.id, error = e.message ?: "Error",
                        exceptionType = e.javaClass.canonicalName
                    )
                }
            }
            ctx.json(future)
        }

    override fun <REQ, RES> createWebsocketHandler(
        requestMessageType: Class<REQ>,
        responseMessageType: Class<RES>,
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit
    ): WebsocketHandler =
        { ws ->
            ws.onConnect { ctx ->
                val incoming = Channel<String>()
                val outgoing = Channel<String>()
                ctx.attribute(KV_WS_INCOMING_KEY, incoming)
                ctx.attribute(KV_WS_OUTGOING_KEY, outgoing)
                val injector = ctx.attribute<Injector>(KV_INJECTOR_KEY)!!
                val service = injector.getInstance(serviceClass.java)
                initializeWsService(service, ctx)
                GlobalScope.launch {
                    coroutineScope {
                        launch(Dispatchers.IO) {
                            for (text in outgoing) {
                                ctx.send(text)
                            }
                            ctx.session.close()
                        }
                        launch {
                            val requestChannel = Channel<REQ>()
                            val responseChannel = Channel<RES>()
                            coroutineScope {
                                launch {
                                    for (p in incoming) {
                                        val jsonRpcRequest = deSerializer.deserialize<JsonRpcRequest>(p)
                                        if (jsonRpcRequest.params.size == 1) {
                                            val par = deSerializer.deserialize(
                                                jsonRpcRequest.params[0], requestMessageType
                                            )
                                            requestChannel.send(par)
                                        }
                                    }
                                    requestChannel.close()
                                }
                                launch(Dispatchers.IO) {
                                    for (p in responseChannel) {
                                        val text = deSerializer.serializeNonNullToString(
                                            JsonRpcResponse(
                                                id = 0,
                                                result = deSerializer.serializeNullableToString(p)
                                            )
                                        )
                                        outgoing.send(text)
                                    }
                                }
                                launch {
                                    function.invoke(service, requestChannel, responseChannel)
                                    if (!responseChannel.isClosedForReceive) responseChannel.close()
                                }
                            }
                            if (!outgoing.isClosedForReceive) outgoing.close()
                        }
                    }
                }
            }
            ws.onClose { ctx ->
                GlobalScope.launch {
                    val outgoing = ctx.attribute<Channel<String>>(KV_WS_OUTGOING_KEY)!!
                    val incoming = ctx.attribute<Channel<String>>(KV_WS_INCOMING_KEY)!!
                    outgoing.close()
                    incoming.close()
                }
            }
            ws.onMessage { ctx ->
                GlobalScope.launch {
                    val incoming = ctx.attribute<Channel<String>>(KV_WS_INCOMING_KEY)!!
                    incoming.send(ctx.message())
                }
            }
        }
}

/**
 * A function to generate routes based on definitions from the service manager.
 */
fun <T : Any> Javalin.applyRoutes(serviceManager: KVServiceManager<T>, roles: Set<Role> = setOf()) {
    serviceManager.routeMapRegistry.asSequence().forEach { (method, path, handler) ->
        when (method) {
            HttpMethod.GET -> get(path, handler, roles)
            HttpMethod.POST -> post(path, handler, roles)
            HttpMethod.PUT -> put(path, handler, roles)
            HttpMethod.DELETE -> delete(path, handler, roles)
            HttpMethod.OPTIONS -> options(path, handler, roles)
        }
    }
    serviceManager.webSocketRequests.forEach { (path, handler) ->
        ws(path, handler, roles)
    }
}
