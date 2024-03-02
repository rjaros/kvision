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

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.sse.Event
import io.micronaut.websocket.WebSocketSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.flux
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass

typealias RequestHandler =
    suspend (HttpRequest<*>, ThreadLocal<HttpRequest<*>>, ThreadLocal<HttpResponseMutator>, ApplicationContext) -> HttpResponse<String>

typealias WebsocketHandler = suspend (
    WebSocketSession, ThreadLocal<WebSocketSession>, ApplicationContext, ReceiveChannel<String>, SendChannel<String>
) -> Unit

typealias SseHandler =
        (HttpRequest<*>, ThreadLocal<HttpRequest<*>>, ApplicationContext) -> Publisher<Event<String>>

/**
 * Multiplatform service manager for Micronaut.
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
        return { req, tlReq, tlResponseMutator, ctx ->
            val httpResponseMutator = HttpResponseMutator()
            tlReq.set(req)
            tlResponseMutator.set(httpResponseMutator)
            val service = ctx.getBean(serviceClass.java)
            tlReq.remove()
            tlResponseMutator.remove()
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                val parameters = (0..<numberOfParams).map {
                    req.parameters["p$it"]?.let {
                        URLDecoder.decode(it, StandardCharsets.UTF_8)
                    }
                }
                JsonRpcRequest(0, "", parameters)
            } else {
                req.getBody(JsonRpcRequest::class.java).get()
            }

            HttpResponse.ok(
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
            ).also { mutableHttpResponse -> httpResponseMutator.responseMutator?.let { mutableHttpResponse.it() } }
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
            applicationScope.launch {
                handleSseConnection(
                    deSerializer = deSerializer,
                    rawOut = channel,
                    serializerOut = serializer,
                    service = service,
                    function = function
                )
            }
            flux {
                for (item in channel) {
                    send(Event.of(item))
                }
            }.doOnCancel {
                channel.close()
            }
        }
    }
}
