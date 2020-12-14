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

import com.google.inject.Injector
import io.vertx.core.Vertx
import io.vertx.core.http.ServerWebSocket
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.treksoft.kvision.types.*
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

typealias RequestHandler = (RoutingContext) -> Unit

/**
 * Multiplatform service manager for Vert.x.
 */
@Suppress("LargeClass", "TooManyFunctions", "BlockingMethodInNonBlockingContext")
actual open class KVServiceManager<T : Any> actual constructor(val serviceClass: KClass<T>) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(KVServiceManager::class.java.name)
        const val KV_WS_INCOMING_KEY = "pl.treksoft.kvision.ws.incoming.key"
        const val KV_WS_OUTGOING_KEY = "pl.treksoft.kvision.ws.outgoing.key"
    }

    val routeMapRegistry = createRouteMapRegistry<RequestHandler>()

    @Suppress("DEPRECATION")
    @Deprecated("use routeMapRegistry instead", ReplaceWith("routeMapRegistry"))
    val getRequests by RouteMapDelegate(routeMapRegistry, HttpMethod.GET)
    @Suppress("DEPRECATION")
    @Deprecated("use routeMapRegistry instead", ReplaceWith("routeMapRegistry"))
    val postRequests by RouteMapDelegate(routeMapRegistry, HttpMethod.POST)
    @Suppress("DEPRECATION")
    @Deprecated("use routeMapRegistry instead", ReplaceWith("routeMapRegistry"))
    val putRequests by RouteMapDelegate(routeMapRegistry, HttpMethod.PUT)
    @Suppress("DEPRECATION")
    @Deprecated("use routeMapRegistry instead", ReplaceWith("routeMapRegistry"))
    val deleteRequests by RouteMapDelegate(routeMapRegistry, HttpMethod.DELETE)
    @Suppress("DEPRECATION")
    @Deprecated("use routeMapRegistry instead", ReplaceWith("routeMapRegistry"))
    val optionsRequests by RouteMapDelegate(routeMapRegistry, HttpMethod.OPTIONS)

    val webSocketRequests: MutableMap<String, (Injector, ServerWebSocket) -> Unit> =
        mutableMapOf()

    val mapper = createDefaultObjectMapper()
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
            function.invoke(this, getParameter(it[0]), getParameter(it[1]),)
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
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        addRoute(method, "/kv/$routeDef") { ctx ->
            val jsonRpcRequest = if (method == HttpMethod.GET) {
                JsonRpcRequest(ctx.request().getParam("id").toInt(), "", listOf())
            } else {
                ctx.bodyAsJson.mapTo(JsonRpcRequest::class.java)
            }
            @Suppress("MagicNumber")
            val injector = ctx.get<Injector>(KV_INJECTOR_KEY)
            val service = injector.getInstance(serviceClass.java)
            GlobalScope.launch(ctx.vertx().dispatcher()) {
                val response = try {
                    val result = function.invoke(service, jsonRpcRequest.params)
                    JsonRpcResponse(
                        id = jsonRpcRequest.id,
                        result = mapper.writeValueAsString(result)
                    )
                } catch (e: IllegalParameterCountException) {
                    JsonRpcResponse(id = jsonRpcRequest.id, error = "Invalid parameters")
                } catch (e: Exception) {
                    if (e !is ServiceException) LOG.error(e.message, e)
                    JsonRpcResponse(
                        id = jsonRpcRequest.id, error = e.message ?: "Error",
                        exceptionType = e.javaClass.canonicalName
                    )
                }
                ctx.response().putHeader("Content-Type", "application/json").end(Json.encode(response))
            }
        }
    }

    /**
     * Binds a given web socket connection with a function of the receiver.
     * @param function a function of the receiver
     * @param route a route
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    protected actual inline fun <reified PAR1 : Any, reified PAR2 : Any> bind(
        noinline function: suspend T.(ReceiveChannel<PAR1>, SendChannel<PAR2>) -> Unit,
        route: String?
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        webSocketRequests["/kvws/$routeDef"] = { injector, ws ->
            val incoming = Channel<String>()
            val outgoing = Channel<String>()
            val service = injector.getInstance(serviceClass.java)
            val vertx = injector.getInstance(Vertx::class.java)
            ws.textMessageHandler { message ->
                GlobalScope.launch(vertx.dispatcher()) {
                    incoming.send(message)
                }
            }
            ws.closeHandler {
                GlobalScope.launch(vertx.dispatcher()) {
                    outgoing.close()
                    incoming.close()
                }
            }
            ws.exceptionHandler {
                GlobalScope.launch(vertx.dispatcher()) {
                    outgoing.close()
                    incoming.close()
                }
            }
            GlobalScope.launch(vertx.dispatcher()) {
                coroutineScope {
                    launch {
                        for (text in outgoing) {
                            ws.writeTextMessage(text)
                        }
                        if (!ws.isClosed) ws.close()
                    }
                    launch {
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
                            }
                            launch {
                                function.invoke(service, requestChannel, responseChannel)
                                if (!responseChannel.isClosedForReceive) responseChannel.close()
                            }
                        }
                        if (!outgoing.isClosedForReceive) outgoing.close()
                    }
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
     * @suppress Internal method
     */
    fun addRoute(
        method: HttpMethod,
        path: String,
        handler: RequestHandler
    ) {
        routeMapRegistry.addRoute(method, path, handler)
    }

    /**
     * @suppress Internal method
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

/**
 * A function to generate routes based on definitions from the service manager.
 */
fun <T : Any> Vertx.applyRoutes(router: Router, serviceManager: KVServiceManager<T>) {
    serviceManager.routeMapRegistry.asSequence().forEach { (method, path, handler) ->
        try {
            io.vertx.core.http.HttpMethod.valueOf(method.name)
        } catch (e: IllegalArgumentException) {
            null
        }?.let {
            router.route(it, path).handler(handler)
        }
    }
}
