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
import kotlinx.coroutines.CoroutineStart
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
import org.springframework.security.core.Authentication
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json
import org.springframework.web.reactive.socket.WebSocketSession
import pl.treksoft.kvision.types.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import kotlin.reflect.KClass


/**
 * Multiplatform service manager for Spring Boot.
 */
@UseExperimental(ExperimentalCoroutinesApi::class)
@Suppress("LargeClass", "TooManyFunctions")
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }

    val getRequests: MutableMap<String, suspend (ServerRequest, ApplicationContext) -> ServerResponse> =
        mutableMapOf()
    val postRequests: MutableMap<String, suspend (ServerRequest, ApplicationContext) -> ServerResponse> =
        mutableMapOf()
    val putRequests: MutableMap<String, suspend (ServerRequest, ApplicationContext) -> ServerResponse> =
        mutableMapOf()
    val deleteRequests: MutableMap<String, suspend (ServerRequest, ApplicationContext) -> ServerResponse> =
        mutableMapOf()
    val optionsRequests: MutableMap<String, suspend (ServerRequest, ApplicationContext) -> ServerResponse> =
        mutableMapOf()
    val webSocketsRequests: MutableMap<String, suspend (
        WebSocketSession, ApplicationContext, ReceiveChannel<String>, SendChannel<String>
    ) -> Unit> =
        mutableMapOf()

    val mapper = jacksonObjectMapper().apply {
        val module = SimpleModule()
        module.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        module.addSerializer(LocalDate::class.java, LocalDateSerializer())
        module.addSerializer(LocalTime::class.java, LocalTimeSerializer())
        module.addSerializer(OffsetDateTime::class.java, OffsetDateTimeSerializer())
        module.addSerializer(OffsetTime::class.java, OffsetTimeSerializer())
        module.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
        module.addDeserializer(LocalDate::class.java, LocalDateDeserializer())
        module.addDeserializer(LocalTime::class.java, LocalTimeDeserializer())
        module.addDeserializer(OffsetDateTime::class.java, OffsetDateTimeDeserializer())
        module.addDeserializer(OffsetTime::class.java, OffsetTimeDeserializer())
        this.registerModule(module)
    }
    var counter: Int = 0

    /**
     * @suppress internal function
     */
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
        if (service is WithProfile) {
            val profile = req.principal().ofType(Authentication::class.java).map {
                it.principal as Profile
            }.awaitSingle()
            service.profile = profile
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified RET> bind(
        noinline function: suspend T.() -> RET,
        method: HttpMethod, route: String?
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, ctx ->
            val service = ctx.getBean(serviceClass.java)
            initializeService(service, req)
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                JsonRpcRequest(req.queryParam("id").orElse(null)?.toInt() ?: 0, "", listOf())
            } else {
                req.awaitBody()
            }
            try {
                val result = function.invoke(service)
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            result = mapper.writeValueAsString(result)
                        )
                    )
                )
            } catch (e: Exception) {
                if (!(e is ServiceException)) LOG.error(e.message, e)
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = e.message ?: "Error",
                                exceptionType = e.javaClass.canonicalName
                        )
                    )
                )
            }
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR, reified RET> bind(
        noinline function: suspend T.(PAR) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, ctx ->
            val service = ctx.getBean(serviceClass.java)
            initializeService(service, req)
            val jsonRpcRequest = req.awaitBody<JsonRpcRequest>()
            if (jsonRpcRequest.params.size == 1) {
                val param = getParameter<PAR>(jsonRpcRequest.params[0])
                try {
                    val result = function.invoke(service, param)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    if (!(e is ServiceException)) LOG.error(e.message, e)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error",
                                exceptionType = e.javaClass.canonicalName
                            )
                        )
                    )
                }
            } else {
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = "Invalid parameters"
                        )
                    )
                )
            }
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR1, reified PAR2, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, ctx ->
            val service = ctx.getBean(serviceClass.java)
            initializeService(service, req)
            val jsonRpcRequest = req.awaitBody<JsonRpcRequest>()
            if (jsonRpcRequest.params.size == 2) {
                val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                try {
                    val result = function.invoke(service, param1, param2)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    if (!(e is ServiceException)) LOG.error(e.message, e)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error",
                                exceptionType = e.javaClass.canonicalName
                            )
                        )
                    )
                }
            } else {
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = "Invalid parameters"
                        )
                    )
                )
            }
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, ctx ->
            val service = ctx.getBean(serviceClass.java)
            initializeService(service, req)
            val jsonRpcRequest = req.awaitBody<JsonRpcRequest>()
            @Suppress("MagicNumber")
            if (jsonRpcRequest.params.size == 3) {
                val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                try {
                    val result = function.invoke(service, param1, param2, param3)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    if (!(e is ServiceException)) LOG.error(e.message, e)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error",
                                exceptionType = e.javaClass.canonicalName
                            )
                        )
                    )
                }
            } else {
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = "Invalid parameters"
                        )
                    )
                )
            }
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, ctx ->
            val service = ctx.getBean(serviceClass.java)
            initializeService(service, req)
            val jsonRpcRequest = req.awaitBody<JsonRpcRequest>()
            @Suppress("MagicNumber")
            if (jsonRpcRequest.params.size == 4) {
                val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                val param4 = getParameter<PAR4>(jsonRpcRequest.params[3])
                try {
                    val result = function.invoke(service, param1, param2, param3, param4)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    if (!(e is ServiceException)) LOG.error(e.message, e)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error",
                                exceptionType = e.javaClass.canonicalName
                            )
                        )
                    )
                }
            } else {
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = "Invalid parameters"
                        )
                    )
                )
            }
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3,
            reified PAR4, reified PAR5, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5) -> RET,
        method: HttpMethod, route: String?
    ) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, ctx ->
            val service = ctx.getBean(serviceClass.java)
            initializeService(service, req)
            val jsonRpcRequest = req.awaitBody<JsonRpcRequest>()
            @Suppress("MagicNumber")
            if (jsonRpcRequest.params.size == 5) {
                val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                val param4 = getParameter<PAR4>(jsonRpcRequest.params[3])
                val param5 = getParameter<PAR5>(jsonRpcRequest.params[4])
                try {
                    val result = function.invoke(service, param1, param2, param3, param4, param5)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    if (!(e is ServiceException)) LOG.error(e.message, e)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error",
                                exceptionType = e.javaClass.canonicalName
                            )
                        )
                    )
                }
            } else {
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = "Invalid parameters"
                        )
                    )
                )
            }
        }
    }

    /**
     * Binds a given web socket connetion with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     */
    protected actual inline fun <reified PAR1 : Any, reified PAR2 : Any> bind(
        noinline function: suspend T.(ReceiveChannel<PAR1>, SendChannel<PAR2>) -> Unit,
        route: String?
    ) {
        val routeDef = "route${this::class.simpleName}${counter++}"
        webSocketsRequests[routeDef] = { webSocketSession, ctx, incoming, outgoing ->
            val service = ctx.getBean(serviceClass.java)
            if (service is WithWebSocketSession) {
                service.webSocketSession = webSocketSession
            }
            if (service is WithPrincipal) {
                val principal = webSocketSession.handshakeInfo.principal.awaitSingle()
                service.principal = principal
            }
            if (service is WithProfile) {
                val profile = webSocketSession.handshakeInfo.principal.ofType(Authentication::class.java).map {
                    it.principal as Profile
                }.awaitSingle()
                service.profile = profile
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
                launch(start = CoroutineStart.UNDISPATCHED) {
                    function.invoke(service, requestChannel, responseChannel)
                    if (!responseChannel.isClosedForReceive) responseChannel.close()
                }
            }
        }
    }

    /**
     * Binds a given function of the receiver as a select options source
     * @param function a function of the receiver
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual fun bindSelectRemote(
        function: suspend T.(String?, String?, String?) -> List<RemoteOption>
    ) {
        val routeDef = "route${this::class.simpleName}${counter++}"
        addRoute(HttpMethod.POST, "/kv/$routeDef") { req, ctx ->
            val service = ctx.getBean(serviceClass.java)
            initializeService(service, req)
            val jsonRpcRequest = req.awaitBody<JsonRpcRequest>()
            @Suppress("MagicNumber")
            if (jsonRpcRequest.params.size == 3) {
                val param1 = getParameter<String?>(jsonRpcRequest.params[0])
                val param2 = getParameter<String?>(jsonRpcRequest.params[1])
                val param3 = getParameter<String?>(jsonRpcRequest.params[2])
                try {
                    val result = function.invoke(service, param1, param2, param3)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    if (!(e is ServiceException)) LOG.error(e.message, e)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error",
                                exceptionType = e.javaClass.canonicalName
                            )
                        )
                    )
                }
            } else {
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = "Invalid parameters"
                        )
                    )
                )
            }
        }
    }

    /**
     * Binds a given function of the receiver as a tabulator component source
     * @param function a function of the receiver
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified RET> bindTabulatorRemote(
        noinline function: suspend T.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?) -> RemoteData<RET>
    ) {
        val routeDef = "route${this::class.simpleName}${counter++}"
        addRoute(HttpMethod.POST, "/kv/$routeDef") { req, ctx ->
            val service = ctx.getBean(serviceClass.java)
            initializeService(service, req)
            val jsonRpcRequest = req.awaitBody<JsonRpcRequest>()
            @Suppress("MagicNumber")
            if (jsonRpcRequest.params.size == 4) {
                val param1 = getParameter<Int?>(jsonRpcRequest.params[0])
                val param2 = getParameter<Int?>(jsonRpcRequest.params[1])
                val param3 = getParameter<List<RemoteFilter>?>(jsonRpcRequest.params[2])
                @Suppress("MagicNumber")
                val param4 = getParameter<List<RemoteSorter>?>(jsonRpcRequest.params[3])
                try {
                    val result = function.invoke(service, param1, param2, param3, param4)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    if (!(e is ServiceException)) LOG.error(e.message, e)
                    ServerResponse.ok().json().bodyValueAndAwait(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error",
                                exceptionType = e.javaClass.canonicalName
                            )
                        )
                    )
                }
            } else {
                ServerResponse.ok().json().bodyValueAndAwait(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = "Invalid parameters"
                        )
                    )
                )
            }
        }
    }

    /**
     * @suppress internal function
     */
    fun addRoute(
        method: HttpMethod,
        path: String,
        handler: suspend (ServerRequest, ApplicationContext) -> ServerResponse
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
                mapper.readValue(str, T::class.java)
            }
        } ?: null as T
    }
}
