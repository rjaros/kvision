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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

typealias RequestHandler = suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit
typealias WebsocketHandler = suspend WebSocketServerSession.() -> Unit

/**
 * Multiplatform service manager for Ktor.
 */
@Suppress("LargeClass", "TooManyFunctions", "BlockingMethodInNonBlockingContext")
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) : KVServiceMgr<T>,
    KVServiceBinder<T, RequestHandler, WebsocketHandler>() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }

    @Suppress("TooGenericExceptionCaught")
    override fun createRequestHandler(
        method: HttpMethod,
        function: suspend T.(params: List<String?>) -> Any?
    ): RequestHandler =
        {
            val service = call.injector.createChildInjector(DummyWsSessionModule()).getInstance(serviceClass.java)
            val jsonRpcRequest = call.receive<JsonRpcRequest>()
            try {
                val result = function.invoke(service, jsonRpcRequest.params)
                call.respond(
                    JsonRpcResponse(
                        id = jsonRpcRequest.id,
                        result = deSerializer.serializeNullableToString(result)
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


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun <REQ, RES> createWebsocketHandler(
        requestMessageType: Class<REQ>,
        responseMessageType: Class<RES>,
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit
    ): WebsocketHandler =
        {
            val wsInjector = call.injector.createChildInjector(WsSessionModule(this))
            val service = wsInjector.getInstance(serviceClass.java)
            val requestChannel = Channel<REQ>()
            val responseChannel = Channel<RES>()
            val session = this
            coroutineScope {
                launch {
                    for (p in incoming) {
                        (p as? Frame.Text)?.readText()?.let { text ->
                            val jsonRpcRequest = deSerializer.deserialize<JsonRpcRequest>(text)
                            if (jsonRpcRequest.params.size == 1) {
                                val par = deSerializer.deserialize(jsonRpcRequest.params[0], requestMessageType)
                                requestChannel.send(par)
                            }
                        }
                    }
                    requestChannel.close()
                }
                launch {
                    for (p in responseChannel) {
                        val text = deSerializer.serializeNonNullToString(
                            JsonRpcResponse(
                                id = 0,
                                result = deSerializer.serializeNullableToString(p)
                            )
                        )
                        outgoing.send(Frame.Text(text))
                    }
                    try {
                        session.close(CloseReason(CloseReason.Codes.NORMAL, ""))
                    } catch (e: ClosedSendChannelException) {
                    }
                    session.close()
                }
                launch {
                    function.invoke(service, requestChannel, responseChannel)
                    if (!responseChannel.isClosedForReceive) responseChannel.close()
                }
            }
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
