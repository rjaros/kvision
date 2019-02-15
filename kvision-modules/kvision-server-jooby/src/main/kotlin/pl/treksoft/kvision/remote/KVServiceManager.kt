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

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.inject.Injector
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jooby.Kooby
import org.jooby.Request
import org.jooby.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.treksoft.kvision.types.KV_JSON_DATE_FORMAT
import java.text.SimpleDateFormat
import kotlin.reflect.KClass

/**
 * Multiplatform service manager for Jooby.
 */
@Suppress("LargeClass")
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
    }

    val routes: MutableList<Kooby.() -> Unit> = mutableListOf()
    val mapper = jacksonObjectMapper().apply {
        dateFormat = SimpleDateFormat(KV_JSON_DATE_FORMAT)
    }
    var counter: Int = 0

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
        routes.add {
            call(method, "/kv/$routeDef") { req, res ->
                val jsonRpcRequest = if (method == HttpMethod.GET) {
                    JsonRpcRequest(req.param("id").intValue(), "", listOf())
                } else {
                    req.body(JsonRpcRequest::class.java)
                }
                val injector = req.require(Injector::class.java)
                val service = injector.getInstance(serviceClass.java)
                GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
                    try {
                        val result = function.invoke(service)
                        res.send(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    } catch (e: Exception) {
                        LOG.error(e.message, e)
                        res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = e.message ?: "Error"))
                    }
                }
            }.invoke(this)
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
        routes.add {
            call(method, "/kv/$routeDef") { req, res ->
                val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                if (jsonRpcRequest.params.size == 1) {
                    val param = getParameter<PAR>(jsonRpcRequest.params[0])
                    val injector = req.require(Injector::class.java)
                    val service = injector.getInstance(serviceClass.java)
                    GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
                        try {
                            val result = function.invoke(service, param)
                            res.send(
                                JsonRpcResponse(
                                    id = jsonRpcRequest.id,
                                    result = mapper.writeValueAsString(result)
                                )
                            )
                        } catch (e: Exception) {
                            LOG.error(e.message, e)
                            res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = e.message ?: "Error"))
                        }
                    }
                } else {
                    res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                }
            }.invoke(this)
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
        routes.add {
            call(method, "/kv/$routeDef") { req, res ->
                val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                if (jsonRpcRequest.params.size == 2) {
                    val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                    val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                    val injector = req.require(Injector::class.java)
                    val service = injector.getInstance(serviceClass.java)
                    GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
                        try {
                            val result = function.invoke(service, param1, param2)
                            res.send(
                                JsonRpcResponse(
                                    id = jsonRpcRequest.id,
                                    result = mapper.writeValueAsString(result)
                                )
                            )
                        } catch (e: Exception) {
                            LOG.error(e.message, e)
                            res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = e.message ?: "Error"))
                        }
                    }
                } else {
                    res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                }
            }.invoke(this)
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
        routes.add {
            call(method, "/kv/$routeDef") { req, res ->
                val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                @Suppress("MagicNumber")
                if (jsonRpcRequest.params.size == 3) {
                    val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                    val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                    val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                    val injector = req.require(Injector::class.java)
                    val service = injector.getInstance(serviceClass.java)
                    GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
                        try {
                            val result = function.invoke(service, param1, param2, param3)
                            res.send(
                                JsonRpcResponse(
                                    id = jsonRpcRequest.id,
                                    result = mapper.writeValueAsString(result)
                                )
                            )
                        } catch (e: Exception) {
                            LOG.error(e.message, e)
                            res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = e.message ?: "Error"))
                        }
                    }
                } else {
                    res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                }
            }.invoke(this)
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
        routes.add {
            call(method, "/kv/$routeDef") { req, res ->
                val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                @Suppress("MagicNumber")
                if (jsonRpcRequest.params.size == 4) {
                    val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                    val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                    val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                    val param4 = getParameter<PAR4>(jsonRpcRequest.params[3])
                    val injector = req.require(Injector::class.java)
                    val service = injector.getInstance(serviceClass.java)
                    GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
                        try {
                            val result = function.invoke(service, param1, param2, param3, param4)
                            res.send(
                                JsonRpcResponse(
                                    id = jsonRpcRequest.id,
                                    result = mapper.writeValueAsString(result)
                                )
                            )
                        } catch (e: Exception) {
                            LOG.error(e.message, e)
                            res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = e.message ?: "Error"))
                        }
                    }
                } else {
                    res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                }
            }.invoke(this)
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
        routes.add {
            call(method, "/kv/$routeDef") { req, res ->
                val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                @Suppress("MagicNumber")
                if (jsonRpcRequest.params.size == 5) {
                    val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                    val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                    val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                    val param4 = getParameter<PAR4>(jsonRpcRequest.params[3])
                    val param5 = getParameter<PAR5>(jsonRpcRequest.params[4])
                    val injector = req.require(Injector::class.java)
                    val service = injector.getInstance(serviceClass.java)
                    GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
                        try {
                            val result = function.invoke(service, param1, param2, param3, param4, param5)
                            res.send(
                                JsonRpcResponse(
                                    id = jsonRpcRequest.id,
                                    result = mapper.writeValueAsString(result)
                                )
                            )
                        } catch (e: Exception) {
                            LOG.error(e.message, e)
                            res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = e.message ?: "Error"))
                        }
                    }
                } else {
                    res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                }
            }.invoke(this)
        }
    }

    /**
     * Binds a given function of the receiver as a select options source
     * @param function a function of the receiver
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual fun bind(
        function: T.(String?, String?) -> List<RemoteSelectOption>
    ) {
        val routeDef = "route${this::class.simpleName}${counter++}"
        routes.add {
            call(HttpMethod.POST, "/kv/$routeDef") { req, res ->
                val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                if (jsonRpcRequest.params.size == 2) {
                    val param1 = getParameter<String?>(jsonRpcRequest.params[0])
                    val param2 = getParameter<String?>(jsonRpcRequest.params[1])
                    val injector = req.require(Injector::class.java)
                    val service = injector.getInstance(serviceClass.java)
                    GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
                        try {
                            val result = function.invoke(service, param1, param2)
                            res.send(
                                JsonRpcResponse(
                                    id = jsonRpcRequest.id,
                                    result = mapper.writeValueAsString(result)
                                )
                            )
                        } catch (e: Exception) {
                            LOG.error(e.message, e)
                            res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = e.message ?: "Error"))
                        }
                    }
                } else {
                    res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                }
            }.invoke(this)
        }
    }

    fun call(
        method: HttpMethod,
        path: String,
        handler: (Request, Response) -> Unit
    ): Kooby.() -> Unit {
        return {
            when (method) {
                HttpMethod.GET -> get(path, handler)
                HttpMethod.POST -> post(path, handler)
                HttpMethod.PUT -> put(path, handler)
                HttpMethod.DELETE -> delete(path, handler)
                HttpMethod.OPTIONS -> options(path, handler)
            }
        }
    }

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

fun <T : Any> Kooby.applyRoutes(serviceManager: KVServiceManager<T>) {
    serviceManager.routes.forEach {
        it.invoke(this@applyRoutes)
    }
}
