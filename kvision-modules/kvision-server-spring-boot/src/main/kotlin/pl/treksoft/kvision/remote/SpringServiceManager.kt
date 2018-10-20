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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.KClass

/**
 * Multiplatform service manager for Spring Boot.
 */
actual open class SpringServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) : ServiceManager {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(JoobyServiceManager::class.java.name)
    }

    val postRequests: MutableMap<String, (Request, HttpServletResponse) -> Unit> = mutableMapOf()
    val putRequests: MutableMap<String, (Request, HttpServletResponse) -> Unit> = mutableMapOf()
    val deleteRequests: MutableMap<String, (Request, HttpServletResponse) -> Unit> = mutableMapOf()
    val optionsRequests: MutableMap<String, (Request, HttpServletResponse) -> Unit> = mutableMapOf()

    val mapper = jacksonObjectMapper().apply {
        dateFormat = SimpleDateFormat("YYYY-MM-DD HH:mm:ss")
    }
    var counter: Int = 0

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     * @param method a HTTP method
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified RET> bind(
        noinline function: T.() -> Deferred<RET>,
        route: String?, method: RpcHttpMethod
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, res ->
            val service = SpringContext.getBean(serviceClass.java)
            val jsonRpcRequest = mapper.readValue(req.inputStream, JsonRpcRequest::class.java)
            try {
                val result = runBlocking { function.invoke(service).await() }
                res.writeJSON(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            result = mapper.writeValueAsString(result)
                        )
                    )
                )
            } catch (e: Exception) {
                LOG.error(e.message, e)
                res.writeJSON(
                    mapper.writeValueAsString(
                        JsonRpcResponse(
                            id = jsonRpcRequest.id,
                            error = e.message ?: "Error"
                        )
                    )
                )
            }
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     * @param method a HTTP method
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR, reified RET> bind(
        noinline function: T.(PAR) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, res ->
            val service = SpringContext.getBean(serviceClass.java)
            val jsonRpcRequest = mapper.readValue(req.inputStream, JsonRpcRequest::class.java)
            if (jsonRpcRequest.params.size == 1) {
                val param = getParameter<PAR>(jsonRpcRequest.params[0])
                try {
                    val result = runBlocking { function.invoke(service, param).await() }
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    LOG.error(e.message, e)
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error"
                            )
                        )
                    )
                }
            } else {
                res.writeJSON(
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
     * @param route a route
     * @param method a HTTP method
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR1, reified PAR2, reified RET> bind(
        noinline function: T.(PAR1, PAR2) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, res ->
            val service = SpringContext.getBean(serviceClass.java)
            val jsonRpcRequest = mapper.readValue(req.inputStream, JsonRpcRequest::class.java)
            if (jsonRpcRequest.params.size == 2) {
                val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                try {
                    val result = runBlocking { function.invoke(service, param1, param2).await() }
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    LOG.error(e.message, e)
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error"
                            )
                        )
                    )
                }
            } else {
                res.writeJSON(
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
     * @param route a route
     * @param method a HTTP method
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET> bind(
        noinline function: T.(PAR1, PAR2, PAR3) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, res ->
            val service = SpringContext.getBean(serviceClass.java)
            val jsonRpcRequest = mapper.readValue(req.inputStream, JsonRpcRequest::class.java)
            @Suppress("MagicNumber")
            if (jsonRpcRequest.params.size == 3) {
                val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                try {
                    val result = runBlocking { function.invoke(service, param1, param2, param3).await() }
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    LOG.error(e.message, e)
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error"
                            )
                        )
                    )
                }
            } else {
                res.writeJSON(
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
     * @param route a route
     * @param method a HTTP method
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET> bind(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, res ->
            val service = SpringContext.getBean(serviceClass.java)
            val jsonRpcRequest = mapper.readValue(req.inputStream, JsonRpcRequest::class.java)
            @Suppress("MagicNumber")
            if (jsonRpcRequest.params.size == 4) {
                val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                val param4 = getParameter<PAR4>(jsonRpcRequest.params[3])
                try {
                    val result =
                        runBlocking { function.invoke(service, param1, param2, param3, param4).await() }
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    LOG.error(e.message, e)
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error"
                            )
                        )
                    )
                }
            } else {
                res.writeJSON(
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
     * @param route a route
     * @param method a HTTP method
     */
    @Suppress("TooGenericExceptionCaught")
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3,
            reified PAR4, reified PAR5, reified RET> bind(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, PAR5) -> Deferred<RET>,
        route: String?,
        method: RpcHttpMethod
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { req, res ->
            val service = SpringContext.getBean(serviceClass.java)
            val jsonRpcRequest = mapper.readValue(req.inputStream, JsonRpcRequest::class.java)
            @Suppress("MagicNumber")
            if (jsonRpcRequest.params.size == 5) {
                val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                val param4 = getParameter<PAR4>(jsonRpcRequest.params[3])
                val param5 = getParameter<PAR5>(jsonRpcRequest.params[4])
                try {
                    val result =
                        runBlocking {
                            function.invoke(service, param1, param2, param3, param4, param5).await()
                        }
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                result = mapper.writeValueAsString(result)
                            )
                        )
                    )
                } catch (e: Exception) {
                    LOG.error(e.message, e)
                    res.writeJSON(
                        mapper.writeValueAsString(
                            JsonRpcResponse(
                                id = jsonRpcRequest.id,
                                error = e.message ?: "Error"
                            )
                        )
                    )
                }
            } else {
                res.writeJSON(
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

    fun addRoute(
        method: RpcHttpMethod,
        path: String,
        handler: (Request, HttpServletResponse) -> Unit
    ) {
        when (method) {
            RpcHttpMethod.POST -> postRequests[path] = handler
            RpcHttpMethod.PUT -> putRequests[path] = handler
            RpcHttpMethod.DELETE -> deleteRequests[path] = handler
            RpcHttpMethod.OPTIONS -> optionsRequests[path] = handler
        }
    }

    @Suppress("TooGenericExceptionCaught")
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

fun HttpServletResponse.writeJSON(json: String) {
    val out = this.outputStream
    this.contentType = "application/json"
    this.characterEncoding = "UTF-8"
    out.write(json.toByteArray())
    out.flush()
}
