/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
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

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

/**
 * Binds HTTP calls to kotlin functions
 *
 * @param T the receiver of bound functions
 * @param RH the platform specific request handler
 * @param WH the platform specific websocket handler
 * @param SH the platform specific server-sent events handler
 *
 */
abstract class KVServiceBinder<out T, RH, WH, SH>(
    routeNameGenerator: NameGenerator? = null
) {
    @PublishedApi
    internal val generateRouteName: NameGenerator =
        routeNameGenerator ?: createNameGenerator("route${javaClass.simpleName}")
    val routeMapRegistry = createRouteMapRegistry<RH>()
    val webSocketRequests: MutableMap<String, WH> = HashMap()
    val sseRequests: MutableMap<String, SH> = HashMap()

    //  deSerializer has to public instead of protected because of https://youtrack.jetbrains.com/issue/KT-22625
    lateinit var deSerializer: ObjectDeSerializer

    abstract fun <RET> createRequestHandler(
        method: HttpMethod,
        function: suspend T.(params: List<String?>) -> RET,
        numberOfParams: Int,
        serializerFactory: () -> KSerializer<RET>
    ): RH

    abstract fun <REQ, RES> createWebsocketHandler(
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit,
        requestSerializerFactory: () -> KSerializer<REQ>,
        responseSerializerFactory: () -> KSerializer<RES>
    ): WH

    abstract fun <PAR> createSseHandler(
        function: suspend T.(SendChannel<PAR>) -> Unit,
        serializerFactory: () -> KSerializer<PAR>
    ): SH

    /**
     * Bind the given HTTP call defined by [method] and an optional [route] (auto-generated if null) to a function that
     * receives the parameters of the call as String array.
     */
    @PublishedApi
    internal inline fun <reified RET> bind(
        method: HttpMethod,
        route: String?,
        numberOfParams: Int,
        noinline function: suspend T.(params: List<String?>) -> RET
    ) {
        routeMapRegistry.addRoute(
            method,
            "/kv/${route ?: generateRouteName()}",
            createRequestHandler(method, function, numberOfParams) { deSerializer.serializersModule.serializer() }
        )
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified RET> bind(noinline function: suspend T.() -> RET, method: HttpMethod, route: String?) {
        bind(method, route, 0) {
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
    inline fun <reified PAR, reified RET> bind(
        noinline function: suspend T.(PAR) -> RET,
        method: HttpMethod,
        route: String?
    ) {
        bind(method, route, 1) {
            requireParameterCountEqualTo(it, 1)
            function.invoke(this, deserialize(it[0]))
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2) -> RET,
        method: HttpMethod,
        route: String?
    ) {
        bind(method, route, 2) {
            requireParameterCountEqualTo(it, 2)
            function.invoke(this, deserialize(it[0]), deserialize(it[1]))
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3) -> RET,
        method: HttpMethod,
        route: String?
    ) {
        bind(method, route, 3) {
            requireParameterCountEqualTo(it, 3)
            function.invoke(this, deserialize(it[0]), deserialize(it[1]), deserialize(it[2]))
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4) -> RET,
        method: HttpMethod,
        route: String?
    ) {
        bind(method, route, 4) {
            requireParameterCountEqualTo(it, 4)
            function.invoke(this, deserialize(it[0]), deserialize(it[1]), deserialize(it[2]), deserialize(it[3]))
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5) -> RET,
        method: HttpMethod,
        route: String?
    ) {
        bind(method, route, 5) {
            requireParameterCountEqualTo(it, 5)
            function.invoke(
                this,
                deserialize(it[0]),
                deserialize(it[1]),
                deserialize(it[2]),
                deserialize(it[3]),
                deserialize(it[4]),
            )
        }
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param route a route
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5, reified PAR6, reified RET> bind(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5, PAR6) -> RET,
        method: HttpMethod,
        route: String?
    ) {
        bind(method, route, 6) {
            requireParameterCountEqualTo(it, 6)
            function.invoke(
                this,
                deserialize(it[0]),
                deserialize(it[1]),
                deserialize(it[2]),
                deserialize(it[3]),
                deserialize(it[4]),
                deserialize(it[5]),
            )
        }
    }

    /**
     * Binds a given function of the receiver as a tabulator component source
     * @param function a function of the receiver
     */
    inline fun <reified RET> bindTabulatorRemote(
        noinline function: suspend T.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<RET>,
        route: String?
    ) {
        bind(function, HttpMethod.POST, route)
    }

    /**
     * Binds a given web socket connection with a function of the receiver.
     * @param REQ the type of each message received via websocket
     * @param RES the type of each message to be sent via websocket
     * @param route a route
     * @param function a function of the receiver
     */
    fun <REQ, RES> bindWebsocket(
        route: String? = null,
        function: suspend T.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit,
        requestSerializerFactory: () -> KSerializer<REQ>,
        responseSerializerFactory: () -> KSerializer<RES>,
    ) {
        webSocketRequests["/kvws/${route ?: generateRouteName()}"] =
            createWebsocketHandler(function, requestSerializerFactory, responseSerializerFactory)
    }

    /**
     * Binds a given web socket connection with a function of the receiver.
     * @param PAR1 the type of each message received via websocket
     * @param PAR2 the type of each message to be sent via websocket
     * @param function a function of the receiver
     * @param route a route
     */
    inline fun <reified PAR1 : Any, reified PAR2 : Any> bind(
        noinline function: suspend T.(ReceiveChannel<PAR1>, SendChannel<PAR2>) -> Unit,
        route: String?
    ) = bindWebsocket(
        route,
        function,
        { deSerializer.serializersModule.serializer() },
        { deSerializer.serializersModule.serializer() })

    /**
     * Binds a given server-sent events connection with a function of the receiver.
     * @param PAR the type of each message received via sse
     * @param function a function of the receiver
     * @param route a route
     */
    inline fun <reified PAR : Any> bind(
        noinline function: suspend T.(SendChannel<PAR>) -> Unit,
        route: String?
    ) {
        sseRequests["/kvsse/${route ?: generateRouteName()}"] =
            createSseHandler(function) { deSerializer.serializersModule.serializer() }
    }

    /**
     * Deserialize the parameter of type [T] from [txt]
     */
    @PublishedApi
    internal inline fun <reified T> deserialize(txt: String?): T {
        return deSerializer.deserialize(txt)
    }
}
