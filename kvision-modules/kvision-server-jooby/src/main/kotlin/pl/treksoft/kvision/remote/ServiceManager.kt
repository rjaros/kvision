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
import org.jooby.Response
import org.jooby.Status
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Multiplatform service manager.
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
actual open class ServiceManager<out T> actual constructor(val service: T) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(ServiceManager::class.java.name)
    }

    protected val routes: MutableList<JoobyServer.() -> Unit> = mutableListOf()
    val mapper = jacksonObjectMapper()
    var counter: Int = 0

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected actual inline fun <reified RET> bind(
        noinline function: T.(Request?) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod, prefix: String
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        routes.add({
            call(method, "$prefix$routeDef") { req, res ->
                if (service != null) {
                    val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                    try {
                        val result = runBlocking { function.invoke(service, req).await() }
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
                } else {
                    res.status(Status.SERVER_ERROR)
                }
            }.invoke(this)
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected actual inline fun <reified PAR, reified RET> bind(
        noinline function: T.(PAR, Request?) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod, prefix: String
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        routes.add({
            call(method, "$prefix$routeDef") { req, res ->
                if (service != null) {
                    val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                    if (jsonRpcRequest.params.size == 1) {
                        val param = getParameter<PAR>(jsonRpcRequest.params[0])
                        try {
                            val result = runBlocking { function.invoke(service, param, req).await() }
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
                    } else {
                        res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                    }
                } else {
                    res.status(Status.SERVER_ERROR)
                }
            }.invoke(this)
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified RET> bind(
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod, prefix: String
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        routes.add({
            call(method, "$prefix$routeDef") { req, res ->
                if (service != null) {
                    val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                    if (jsonRpcRequest.params.size == 2) {
                        val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                        val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                        try {
                            val result = runBlocking { function.invoke(service, param1, param2, req).await() }
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
                    } else {
                        res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                    }
                } else {
                    res.status(Status.SERVER_ERROR)
                }
            }.invoke(this)
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET> bind(
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod, prefix: String
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        routes.add({
            call(method, "$prefix$routeDef") { req, res ->
                if (service != null) {
                    val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                    if (jsonRpcRequest.params.size == 3) {
                        val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                        val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                        val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                        try {
                            val result = runBlocking { function.invoke(service, param1, param2, param3, req).await() }
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
                    } else {
                        res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                    }
                } else {
                    res.status(Status.SERVER_ERROR)
                }
            }.invoke(this)
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET> bind(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<RET>,
        route: String?, method: RpcHttpMethod, prefix: String
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        routes.add({
            call(method, "$prefix$routeDef") { req, res ->
                if (service != null) {
                    val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                    if (jsonRpcRequest.params.size == 4) {
                        val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                        val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                        val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                        val param4 = getParameter<PAR4>(jsonRpcRequest.params[3])
                        try {
                            val result =
                                runBlocking { function.invoke(service, param1, param2, param3, param4, req).await() }
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
                    } else {
                        res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                    }
                } else {
                    res.status(Status.SERVER_ERROR)
                }
            }.invoke(this)
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3,
            reified PAR4, reified PAR5, reified RET> bind(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, PAR5, Request?) -> Deferred<RET>,
        route: String?,
        method: RpcHttpMethod,
        prefix: String
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        routes.add({
            call(method, "$prefix$routeDef") { req, res ->
                if (service != null) {
                    val jsonRpcRequest = req.body(JsonRpcRequest::class.java)
                    if (jsonRpcRequest.params.size == 5) {
                        val param1 = getParameter<PAR1>(jsonRpcRequest.params[0])
                        val param2 = getParameter<PAR2>(jsonRpcRequest.params[1])
                        val param3 = getParameter<PAR3>(jsonRpcRequest.params[2])
                        val param4 = getParameter<PAR4>(jsonRpcRequest.params[3])
                        val param5 = getParameter<PAR5>(jsonRpcRequest.params[4])
                        try {
                            val result =
                                runBlocking {
                                    function.invoke(service, param1, param2, param3, param4, param5, req).await()
                                }
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
                    } else {
                        res.send(JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters"))
                    }
                } else {
                    res.status(Status.SERVER_ERROR)
                }
            }.invoke(this)
        })
    }

    fun call(
        method: RpcHttpMethod,
        path: String,
        handler: (Request, Response) -> Unit
    ): JoobyServer.() -> Unit {
        return {
            when (method) {
                RpcHttpMethod.POST -> post(path, handler)
                RpcHttpMethod.PUT -> put(path, handler)
                RpcHttpMethod.DELETE -> delete(path, handler)
                RpcHttpMethod.OPTIONS -> options(path, handler)
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

    /**
     * Applies all defined routes to the given server.
     * @param k a Jooby server
     */
    actual fun applyRoutes(k: JoobyServer) {
        routes.forEach {
            it.invoke(k)
        }
    }

    /**
     * Returns the list of defined bindings.
     * Not used on the jvm platform.
     */
    actual fun getCalls(): Map<String, Pair<String, RpcHttpMethod>> = mapOf()
}
