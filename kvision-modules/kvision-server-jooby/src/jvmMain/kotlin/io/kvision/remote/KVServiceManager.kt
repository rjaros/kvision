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

import com.google.inject.Injector
import io.jooby.Context
import io.jooby.ServerSentEmitter
import io.jooby.WebSocketConfigurer
import io.jooby.kt.CoroutineRouter
import io.jooby.kt.HandlerContext
import io.jooby.kt.Kooby
import io.jooby.kt.ServerSentHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass

typealias RequestHandler = suspend HandlerContext.() -> Any
typealias WebsocketHandler = (ctx: Context, configurer: WebSocketConfigurer) -> Unit
typealias SseHandler = ServerSentHandler.() -> Unit

/**
 * Multiplatform service manager for Jooby.
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
        return {
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                val parameters = (0..<numberOfParams).map {
                    ctx.query("p$it").valueOrNull()?.let {
                        URLDecoder.decode(it, StandardCharsets.UTF_8)
                    }
                }
                JsonRpcRequest(0, "", parameters)
            } else {
                ctx.body(JsonRpcRequest::class.java)
            }
            val injector = ctx.getAttribute<Injector>(KV_INJECTOR_KEY)!!
            val service = injector.getInstance(serviceClass.java)
            ctx.setResponseType("application/json")
            try {
                val result = function.invoke(service, jsonRpcRequest.params)
                JsonRpcResponse(
                    id = jsonRpcRequest.id,
                    result = deSerializer.serializeNullableToString(result, serializer)
                )
            } catch (e: IllegalParameterCountException) {
                JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters")
            } catch (e: Exception) {
                if (e !is ServiceException && e !is AbstractServiceException) LOG.error(e.message, e)
                val exceptionJson = if (e is AbstractServiceException) {
                    RemoteSerialization.getJson().encodeToString(e)
                } else null
                JsonRpcResponse(
                    id = jsonRpcRequest.id, error = e.message ?: "Error",
                    exceptionType = e.javaClass.canonicalName,
                    exceptionJson = exceptionJson
                )
            }
        }
    }

    override fun <REQ, RES> createWebsocketHandler(
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit,
        requestSerializerFactory: () -> KSerializer<REQ>,
        responseSerializerFactory: () -> KSerializer<RES>,
    ): WebsocketHandler {
        val requestSerializer by lazy { requestSerializerFactory() }
        val responseSerializer by lazy { responseSerializerFactory() }
        return { ctx, configurer ->
            val injector = ctx.require(Injector::class.java).createChildInjector(ContextModule(ctx))
            val service = injector.getInstance(serviceClass.java)
            val incoming = Channel<String>()
            val outgoing = Channel<String>()
            configurer.onConnect { ws ->
                applicationScope.launch {
                    coroutineScope {
                        launch(Dispatchers.IO) {
                            outgoing.consumeEach { ws.send(it) }
                            ws.close()
                        }
                        launch {
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
                }
            }
            configurer.onClose { _, _ ->
                applicationScope.launch {
                    outgoing.close()
                    incoming.close()
                }
            }
            configurer.onMessage { _, msg ->
                applicationScope.launch {
                    incoming.send(msg.value())
                }
            }
        }
    }

    override fun <PAR> createSseHandler(
        function: suspend T.(SendChannel<PAR>) -> Unit,
        serializerFactory: () -> KSerializer<PAR>
    ): SseHandler {
        val serializer by lazy { serializerFactory() }
        return {
            val channel = Channel<String>()
            val injector = ctx.require(Injector::class.java).createChildInjector(ContextModule(ctx))
            val service = injector.getInstance(serviceClass.java)
            sse.onClose {
                channel.close()
            }
            runBlocking {
                coroutineScope {
                    launch {
                        channel.consumeEach {
                            sse.send(it)
                        }
                        sse.close()
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
}

/**
 * A function to generate routes based on definitions from the service manager.
 */
fun <T : Any> CoroutineRouter.applyRoutes(
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
        this.router.ws(path, handler)
    }
    serviceManager.sseRequests.forEach { (path, handler) ->
        val emiterHandler = ServerSentEmitter.Handler { sse ->
            handler(ServerSentHandler(sse.context, sse))
        }
        this.router.sse(path, emiterHandler)
    }
}

/**
 * A function to generate routes based on definitions from the service manager.
 */
fun <T : Any> Kooby.applyRoutes(
    serviceManager: KVServiceManager<T>, serializersModules: List<SerializersModule>? = null
) {
    coroutine {
        applyRoutes(serviceManager, serializersModules)
    }
}
