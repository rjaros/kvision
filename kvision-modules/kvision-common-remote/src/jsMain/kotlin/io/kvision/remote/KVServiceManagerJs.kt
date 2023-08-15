package io.kvision.remote

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlin.reflect.KFunction

@PublishedApi
internal fun getCallName(function: Function<*>) =
    if (function is KFunction<*>) function.name
    else function.toString().replace("\\s".toRegex(), "")

open class KVServiceManagerJs<out T: Any> : KVServiceMgr<T> {
    val calls: MutableMap<String, Pair<String, HttpMethod>> = mutableMapOf()
    var counter: Int = 0

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified RET> bind(
        noinline function: suspend T.() -> RET,
        method: HttpMethod, route: String?
    ) {
        bindFunctionInternal(route, function, method)
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR, reified RET> bind(
        noinline function: suspend T.(PAR) -> RET,
        method: HttpMethod, route: String?
    ) {
        bindFunctionWithParamsInternal(method, route, function)
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2) -> RET,
        method: HttpMethod, route: String?
    ) {
        bindFunctionWithParamsInternal(method, route, function)
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3) -> RET,
        method: HttpMethod, route: String?
    ) {
        bindFunctionWithParamsInternal(method, route, function)
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4) -> RET,
        method: HttpMethod, route: String?
    ) {
        bindFunctionWithParamsInternal(method, route, function)
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3,
            reified PAR4, reified PAR5, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5) -> RET,
        method: HttpMethod, route: String?
    ) {
        bindFunctionWithParamsInternal(method, route, function)
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3,
            reified PAR4, reified PAR5, reified PAR6, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5, PAR6) -> RET,
        method: HttpMethod, route: String?
    ) {
        bindFunctionWithParamsInternal(method, route, function)
    }

    /**
     * Binds a given function of the receiver as a tabulator component source
     * @param function a function of the receiver
     */
    inline fun <reified RET> bindTabulatorRemote(
        noinline function: suspend T.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<RET>,
        route: String?
    ) {
        bindFunctionInternal(route, function, HttpMethod.POST)
    }

    /**
     * Binds a given web socket connection with a function of the receiver.
     * @param function a function of the receiver
     * @param route a web socket route
     */
    inline fun <reified PAR1 : Any, reified PAR2 : Any> bind(
        noinline function: suspend T.(ReceiveChannel<PAR1>, SendChannel<PAR2>) -> Unit,
        route: String?
    ) {
        bindFunctionInternal(route, function, HttpMethod.POST, "/kvws/")
    }

    /**
     * Binds a given server-sent events connection with a function of the receiver.
     * @param function a function of the receiver
     * @param route a server-sent events route
     */
    inline fun <reified PAR : Any> bind(
        noinline function: suspend T.(SendChannel<PAR>) -> Unit,
        route: String?
    ) {
        bindFunctionInternal(route, function, HttpMethod.GET, "/kvsse/")
    }

    @PublishedApi
    internal fun bindFunctionWithParamsInternal(method: HttpMethod, route: String?, function: Function<*>) {
        if (method == HttpMethod.GET)
            throw UnsupportedOperationException("GET method is only supported for methods without parameters")
        bindFunctionInternal(route, function, method)
    }

    @PublishedApi
    internal fun bindFunctionInternal(
        route: String?,
        function: Function<*>,
        method: HttpMethod,
        routePrefix: String = "/kv/"
    ) {
        val routeDef = route ?: "route${this::class.simpleName}${counter++}"
        calls[getCallName(function)] = Pair("$routePrefix$routeDef", method)
    }

    override fun getCall(function: Function<*>): Pair<String, HttpMethod>? =
        calls[getCallName(function)]
}
