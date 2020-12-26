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

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
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
typealias WebsocketHandler = suspend (
    WebSocketSession, ThreadLocal<WebSocketSession>, ApplicationContext, ReceiveChannel<String>, SendChannel<String>
) -> Unit
/**
 * Multiplatform service manager for Spring Boot.
 */
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) : KVServiceMgr<T>,
    KVServiceBinder<T, RequestHandler, WebsocketHandler>() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }
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


    override fun createRequestHandler(
        method: HttpMethod,
        function: suspend T.(params: List<String?>) -> Any?
    ): RequestHandler =
        { req, tlReq, ctx ->
            tlReq.set(req)
            val service = ctx.getBean(serviceClass.java)
            tlReq.remove()
            initializeService(service, req)
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                JsonRpcRequest(req.queryParam("id").map { it.toInt() }.orElse(0), "", listOf())
            } else {
                req.awaitBody()
            }
            ServerResponse.ok().json().bodyValueAndAwait(deSerializer.serializeNonNullToString(try {
                val result = function.invoke(service, jsonRpcRequest.params)
                JsonRpcResponse(
                    id = jsonRpcRequest.id,
                    result = deSerializer.serializeNullableToString(result)
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

    override fun <REQ, RES> createWebsocketHandler(
        requestMessageType: Class<REQ>,
        responseMessageType: Class<RES>,
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit
    ): WebsocketHandler =
        { webSocketSession, tlWsSession, ctx, incoming, outgoing ->
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

            handleWebsocketConnection(
                deSerializer = deSerializer,
                rawIn = incoming,
                rawOut = outgoing,
                parsedInType = requestMessageType,
                service = service,
                function = function
            )
        }
}
