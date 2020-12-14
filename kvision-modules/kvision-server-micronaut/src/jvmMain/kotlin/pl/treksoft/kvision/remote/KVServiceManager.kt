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

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.websocket.WebSocketSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.treksoft.kvision.types.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import kotlin.reflect.KClass

/**
 * Multiplatform service manager for Micronaut.
 */
@Suppress("LargeClass", "TooManyFunctions", "BlockingMethodInNonBlockingContext")
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }

    val getRequests: MutableMap<String, suspend (HttpRequest<*>, ThreadLocal<HttpRequest<*>>, ApplicationContext) -> HttpResponse<String>> =
        mutableMapOf()
    val postRequests: MutableMap<String, suspend (HttpRequest<*>, ThreadLocal<HttpRequest<*>>, ApplicationContext) -> HttpResponse<String>> =
        mutableMapOf()
    val putRequests: MutableMap<String, suspend (HttpRequest<*>, ThreadLocal<HttpRequest<*>>, ApplicationContext) -> HttpResponse<String>> =
        mutableMapOf()
    val deleteRequests: MutableMap<String, suspend (HttpRequest<*>, ThreadLocal<HttpRequest<*>>, ApplicationContext) -> HttpResponse<String>> =
        mutableMapOf()
    val optionsRequests: MutableMap<String, suspend (HttpRequest<*>, ThreadLocal<HttpRequest<*>>, ApplicationContext) -> HttpResponse<String>> =
        mutableMapOf()
    val webSocketsRequests: MutableMap<String, suspend (
        WebSocketSession, ThreadLocal<WebSocketSession>, ApplicationContext, ReceiveChannel<String>, SendChannel<String>
    ) -> Unit> = mutableMapOf()

    val mapper = jacksonObjectMapper().apply {
        val module = SimpleModule()
        module.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        module.addSerializer(LocalDate::class.java, LocalDateSerializer())
        module.addSerializer(LocalTime::class.java, LocalTimeSerializer())
        module.addSerializer(OffsetDateTime::class.java, OffsetDateTimeSerializer())
        module.addSerializer(OffsetTime::class.java, OffsetTimeSerializer())
        module.addSerializer(BigDecimal::class.java, BigDecimalSerializer())
        module.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
        module.addDeserializer(LocalDate::class.java, LocalDateDeserializer())
        module.addDeserializer(LocalTime::class.java, LocalTimeDeserializer())
        module.addDeserializer(OffsetDateTime::class.java, OffsetDateTimeDeserializer())
        module.addDeserializer(OffsetTime::class.java, OffsetTimeDeserializer())
        module.addDeserializer(BigDecimal::class.java, BigDecimalDeserializer())
        this.registerModule(module)
    }
    var counter: Int = 0

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    protected actual inline fun <reified RET> bind(
        noinline function: suspend T.() -> RET,
        method: HttpMethod, route: String?
    ) {
        bind(method, route) {
            requireParameterCountEqualTo(it, 0)
            function.invoke(this)
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    protected actual inline fun <reified PAR, reified RET> bind(
        noinline function: suspend T.(PAR) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        bind(method, route) {
            requireParameterCountEqualTo(it, 1)
            function.invoke(this, getParameter(it[0]))
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        bind(method, route) {
            requireParameterCountEqualTo(it, 2)
            function.invoke(this, getParameter(it[0]), getParameter(it[1]))
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        bind(method, route) {
            requireParameterCountEqualTo(it, 3)
            function.invoke(this, getParameter(it[0]), getParameter(it[1]), getParameter(it[2]))
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        bind(method, route) {
            requireParameterCountEqualTo(it, 4)
            function.invoke(this, getParameter(it[0]), getParameter(it[1]), getParameter(it[2]), getParameter(it[3]))
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3,
            reified PAR4, reified PAR5, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        bind(method, route) {
            requireParameterCountEqualTo(it, 5)
            function.invoke(
                this,
                getParameter(it[0]),
                getParameter(it[1]),
                getParameter(it[2]),
                getParameter(it[3]),
                getParameter(it[4]),
            )
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3,
            reified PAR4, reified PAR5, reified PAR6, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5, PAR6) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        bind(method, route) {
            requireParameterCountEqualTo(it, 6)
            function.invoke(
                this,
                getParameter(it[0]),
                getParameter(it[1]),
                getParameter(it[2]),
                getParameter(it[3]),
                getParameter(it[4]),
                getParameter(it[5]),
            )
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param method a HTTP method
     * @param route a route
     * @param function a function of the receiver
     */
    @Suppress("TooGenericExceptionCaught")
    protected fun bind(method: HttpMethod, route: String?, function: suspend T.(params: List<String?>) -> Any?) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, tlReq, ctx ->
            tlReq.set(req)
            val service = ctx.getBean(serviceClass.java)
            tlReq.remove()
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                JsonRpcRequest(req.parameters["id"]?.toInt() ?: 0, "", listOf())
            } else {
                req.getBody(JsonRpcRequest::class.java).get()
            }

            HttpResponse.ok(
                mapper.writeValueAsString(
                    try {
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
                    }
                )
            )
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
     * Binds a given function of the receiver as a tabulator component source
     * @param function a function of the receiver
     */
    protected actual inline fun <reified RET> bindTabulatorRemote(
        noinline function: suspend T.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<RET>,
        route: String?
    ) {
        bind(function, HttpMethod.POST, route)
    }

    /**
     * @suppress internal function
     */
    fun addRoute(
        method: HttpMethod,
        path: String,
        handler: suspend (HttpRequest<*>, ThreadLocal<HttpRequest<*>>, ApplicationContext) -> HttpResponse<String>
    ) {
        when (method) {
            HttpMethod.GET -> getRequests[path] = handler
            HttpMethod.POST -> postRequests[path] = handler
            HttpMethod.PUT -> putRequests[path] = handler
            HttpMethod.DELETE -> deleteRequests[path] = handler
            HttpMethod.OPTIONS -> optionsRequests[path] = handler
        }
    }

    /**
     * @suppress internal function
     */
    protected inline fun <reified T> getParameter(str: String?): T {
        return str?.let {
            if (T::class == String::class) {
                str as T
            } else {
                mapper.readValue(str)
            }
        } ?: null as T
    }
}
