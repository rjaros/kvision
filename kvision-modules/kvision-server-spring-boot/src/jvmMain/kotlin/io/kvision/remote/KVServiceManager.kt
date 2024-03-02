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
package io.kvision.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.flux
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json
import org.springframework.web.reactive.socket.WebSocketSession
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass

typealias RequestHandler = suspend (ServerRequest, ThreadLocal<ServerRequest>, ApplicationContext) -> ServerResponse
typealias WebsocketHandler = suspend (
    WebSocketSession, ThreadLocal<WebSocketSession>, ApplicationContext, ReceiveChannel<String>, SendChannel<String>
) -> Unit

typealias SseHandler = RequestHandler

/**
 * Multiplatform service manager for Spring Boot.
 */
actual open class KVServiceManager<out T : Any> actual constructor(private val serviceClass: KClass<T>) :
    KVServiceMgr<T>,
    KVServiceBinder<T, RequestHandler, WebsocketHandler, SseHandler>() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun <RET> createRequestHandler(
        method: HttpMethod,
        function: suspend T.(params: List<String?>) -> RET,
        numberOfParams: Int,
        serializerFactory: () -> KSerializer<RET>
    ): RequestHandler {
        val serializer by lazy { serializerFactory() }
        return { req, tlReq, ctx ->
            tlReq.set(req)
            val service = ctx.getBean(serviceClass.java)
            tlReq.remove()
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                val parameters = (0..<numberOfParams).map {
                    req.queryParam("p$it").getOrNull()?.let {
                        URLDecoder.decode(it, StandardCharsets.UTF_8)
                    }
                }
                JsonRpcRequest(0, "", parameters)
            } else {
                req.awaitBody()
            }
            ServerResponse.ok().json().bodyValueAndAwait(
                deSerializer.serializeNonNull(
                    try {
                        val result = function.invoke(service, jsonRpcRequest.params)
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            result = deSerializer.serializeNullableToString(result, serializer)
                        )
                    } catch (e: IllegalParameterCountException) {
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = "Invalid parameters"
                        )
                    } catch (e: Exception) {
                        if (e !is ServiceException && e !is AbstractServiceException) LOG.error(e.message, e)
                        val exceptionJson = if (e is AbstractServiceException) {
                            RemoteSerialization.getJson().encodeToString(e)
                        } else null
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = e.message ?: "Error",
                            exceptionType = e.javaClass.canonicalName,
                            exceptionJson = exceptionJson
                        )
                    }
                )
            )
        }
    }

    override fun <REQ, RES> createWebsocketHandler(
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit,
        requestSerializerFactory: () -> KSerializer<REQ>,
        responseSerializerFactory: () -> KSerializer<RES>,
    ): WebsocketHandler {
        val requestSerializer by lazy { requestSerializerFactory() }
        val responseSerializer by lazy { responseSerializerFactory() }
        return { webSocketSession, tlWsSession, ctx, incoming, outgoing ->
            tlWsSession.set(webSocketSession)
            val service = ctx.getBean(serviceClass.java)
            tlWsSession.remove()
            handleWebsocketConnection(
                deSerializer = deSerializer,
                rawIn = incoming,
                rawOut = outgoing,
                serializerIn = requestSerializer,
                serializerOut = responseSerializer,
                service = service,
                function = function
            )
        }
    }

    override fun <PAR> createSseHandler(
        function: suspend T.(SendChannel<PAR>) -> Unit,
        serializerFactory: () -> KSerializer<PAR>
    ): SseHandler {
        val serializer by lazy { serializerFactory() }
        return { req, tlReq, ctx ->
            tlReq.set(req)
            val service = ctx.getBean(serviceClass.java)
            tlReq.remove()
            val channel = Channel<String>()
            val events = flux {
                for (item in channel) {
                    send(
                        ServerSentEvent.builder<String>()
                            .event("message")
                            .data(item)
                            .build()
                    )
                }
            }.doOnCancel {
                channel.close()
            }
            applicationScope.launch {
                handleSseConnection(
                    deSerializer = deSerializer,
                    rawOut = channel,
                    serializerOut = serializer,
                    service = service,
                    function = function
                )
            }
            ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(BodyInserters.fromServerSentEvents(events)).awaitSingle()
        }
    }
}
