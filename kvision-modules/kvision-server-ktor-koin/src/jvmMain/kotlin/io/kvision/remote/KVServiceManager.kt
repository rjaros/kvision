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

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule
import org.koin.ktor.ext.getKoin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

typealias RequestHandler = suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit
typealias WebsocketHandler = suspend WebSocketServerSession.() -> Unit
typealias SseHandler = RequestHandler

/**
 * Multiplatform service manager for Ktor.
 */
actual open class KVServiceManager<out T : Any> actual constructor(private val serviceClass: KClass<T>) :
    KVServiceMgr<T>,
    KVServiceBinder<T, RequestHandler, WebsocketHandler, SseHandler>() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }

    override fun <RET> createRequestHandler(
        method: HttpMethod,
        function: suspend T.(params: List<String?>) -> RET,
        serializerFactory: () -> KSerializer<RET>
    ): RequestHandler {
        val serializer by lazy { serializerFactory() }
        return {
            KoinModule.threadLocalApplicationCall.set(call)
            val service = call.getKoin().get<T>(serviceClass)
            KoinModule.threadLocalApplicationCall.remove()
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                JsonRpcRequest(call.request.queryParameters["id"]?.toInt() ?: 0, "", listOf())
            } else {
                call.receive()
            }
            try {
                val result = function.invoke(service, jsonRpcRequest.params)
                call.respond(
                    JsonRpcResponse(
                        id = jsonRpcRequest.id,
                        result = deSerializer.serializeNullableToString(result, serializer)
                    )
                )
            } catch (e: IllegalParameterCountException) {
                call.respond(
                    JsonRpcResponse(
                        id = jsonRpcRequest.id,
                        error = "Invalid parameters"
                    )
                )
            } catch (e: Exception) {
                if (e !is ServiceException && e !is AbstractServiceException) LOG.error(e.message, e)
                val exceptionJson = if (e is AbstractServiceException) {
                    RemoteSerialization.getJson().encodeToString(e)
                } else null
                call.respond(
                    JsonRpcResponse(
                        id = jsonRpcRequest.id,
                        error = e.message ?: "Error",
                        exceptionType = e.javaClass.canonicalName,
                        exceptionJson = exceptionJson
                    )
                )
            }
        }
    }

    override fun <REQ, RES> createWebsocketHandler(
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit,
        requestSerializerFactory: () -> KSerializer<REQ>,
        responseSerializerFactory: () -> KSerializer<RES>
    ): WebsocketHandler {
        val requestSerializer by lazy { requestSerializerFactory() }
        val responseSerializer by lazy { responseSerializerFactory() }
        return {
            KoinModule.threadLocalApplicationCall.set(call)
            KoinModule.threadLocalWebSocketServerSession.set(this)
            val service = call.getKoin().get<T>(serviceClass)
            KoinModule.threadLocalApplicationCall.remove()
            KoinModule.threadLocalWebSocketServerSession.remove()
            handleWebsocketConnection(
                deSerializer = deSerializer,
                rawIn = incoming,
                rawInToText = { (it as? Frame.Text)?.readText() },
                rawOut = outgoing,
                rawOutFromText = { Frame.Text(it) },
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
        return {
            val channel = Channel<String>()
            KoinModule.threadLocalApplicationCall.set(call)
            val service = call.getKoin().get<T>(serviceClass)
            KoinModule.threadLocalApplicationCall.remove()
            call.response.cacheControl(CacheControl.NoCache(null))
            call.response.header("Connection", "keep-alive")
            coroutineScope {
                launch {
                    call.respondTextWriter(contentType = ContentType.Text.EventStream) {
                        try {
                            channel.consumeEach {
                                write("data: $it\n\n")
                                flush()
                            }
                        } finally {
                            channel.close()
                        }
                    }
                }
                launch {
                    handleSseConnection(
                        deSerializer = deSerializer,
                        rawOut = channel,
                        serializerOut = serializer,
                        service = service,
                        function = function
                    )
                }
            }
        }
    }
}

/**
 * A function to generate routes based on definitions from the service manager.
 */
fun <T : Any> Route.applyRoutes(
    serviceManager: KVServiceManager<T>,
    serializersModules: List<SerializersModule>? = null
) {
    serviceManager.deSerializer = kotlinxObjectDeSerializer(serializersModules)
    serviceManager.routeMapRegistry.asSequence().forEach { (method, path, handler) ->
        when (method) {
            HttpMethod.GET -> get(path, handler)
            HttpMethod.POST -> post(path, handler)
            HttpMethod.PUT -> put(path, handler)
            HttpMethod.DELETE -> delete(path, handler)
            HttpMethod.OPTIONS -> options(path, handler)
        }
    }
    serviceManager.webSocketRequests.forEach { (path, handler) ->
        this.webSocket(path) {
            handler()
        }
    }
    serviceManager.sseRequests.forEach { (path, handler) ->
        this.route(path) { handle(handler) }
    }
}
