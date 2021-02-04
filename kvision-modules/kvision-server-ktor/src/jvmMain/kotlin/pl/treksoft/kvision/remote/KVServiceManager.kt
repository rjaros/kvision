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

import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.serialization.KSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

typealias RequestHandler = suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit
typealias WebsocketHandler = suspend WebSocketServerSession.() -> Unit

/**
 * Multiplatform service manager for Ktor.
 */
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) : KVServiceMgr<T>,
    KVServiceBinder<T, RequestHandler, WebsocketHandler>() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }

    override fun <RET> createRequestHandler(
        method: HttpMethod,
        function: suspend T.(params: List<String?>) -> RET,
        serializer: KSerializer<RET>
    ): RequestHandler =
        {
            val service = call.injector.createChildInjector(DummyWsSessionModule()).getInstance(serviceClass.java)
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
                if (e !is ServiceException) LOG.error(e.message, e)
                call.respond(
                    JsonRpcResponse(
                        id = jsonRpcRequest.id,
                        error = e.message ?: "Error",
                        exceptionType = e.javaClass.canonicalName
                    )
                )
            }
        }

    override fun <REQ, RES> createWebsocketHandler(
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit,
        requestSerializer: KSerializer<REQ>,
        responseSerializer: KSerializer<RES>
    ): WebsocketHandler =
        {
            val wsInjector = call.injector.createChildInjector(WsSessionModule(this))

            handleWebsocketConnection(
                deSerializer = deSerializer,
                rawIn = incoming,
                rawInToText = { (it as? Frame.Text)?.readText() },
                rawOut = outgoing,
                rawOutFromText = { Frame.Text(it) },
                serializerIn = requestSerializer,
                serializerOut = responseSerializer,
                service = wsInjector.getInstance(serviceClass.java),
                function = function
            )
        }
}

/**
 * A function to generate routes based on definitions from the service manager.
 */
fun <T : Any> Route.applyRoutes(serviceManager: KVServiceManager<T>) {
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
}
