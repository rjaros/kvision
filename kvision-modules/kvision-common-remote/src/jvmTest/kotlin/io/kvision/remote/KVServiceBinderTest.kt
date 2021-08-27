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

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertEquals
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test


// The bind-method can bind to functions with args of various types up to 6 parameters.
// So here are some compatible type definitions for zero to six params:
private typealias F_0 = suspend Any.() -> Boolean
private typealias F_1 = suspend Any.(a: String) -> String
private typealias F_2 = suspend Any.(a: String, b: Int) -> Int
private typealias F_3 = suspend Any.(a: String, b: Int, c: Double) -> Double
private typealias F_4 = suspend Any.(a: String, b: Int, c: Double, d: Float) -> Float
private typealias F_5 = suspend Any.(a: String, b: Int, c: Double, d: Float, e: Byte) -> Byte
private typealias F_6 = suspend Any.(a: String, b: Int, c: Double, d: Float, e: Byte, f: Char) -> Char

// Here are some sample implementations of the types above, that just return `this` and all args as a list:
val PARAM_0_FUN: F_0 = { true }
val PARAM_1_FUN: F_1 = { a -> a }
val PARAM_2_FUN: F_2 = { a, b -> b }
val PARAM_3_FUN: F_3 = { a, b, c -> c }
val PARAM_4_FUN: F_4 = { a, b, c, d -> d }
val PARAM_5_FUN: F_5 = { a, b, c, d, e -> e }
val PARAM_6_FUN: F_6 = { a, b, c, d, e, f -> f }

private typealias RouteHandler = Any.(params: List<String?>) -> Any?
private typealias WebsocketHandler = Any.(ReceiveChannel<String>, SendChannel<String>) -> Any?
private typealias BindingInitializer = KVServiceBinder<Any, RouteHandler, *>.(method: HttpMethod, route: String?) -> Unit

// Array of some helper functions, for binding each of the seven sample request handler implementations, so we can
// easily iterate them in the tests:
val BINDING_INITIALIZERS: Array<BindingInitializer> = arrayOf(
    { method, route -> bind(PARAM_0_FUN, method, route) },
    { method, route -> bind(PARAM_1_FUN, method, route) },
    { method, route -> bind(PARAM_2_FUN, method, route) },
    { method, route -> bind(PARAM_3_FUN, method, route) },
    { method, route -> bind(PARAM_4_FUN, method, route) },
    { method, route -> bind(PARAM_5_FUN, method, route) },
    { method, route -> bind(PARAM_6_FUN, method, route) },
)

// Just a sample object which is supposed to arrive as `this` for the handler methods:
val HANDLER_THIS = Any()

// These are some args as string, that can be parsed to the desired type:
val SAMPLE_STRING_ARGS = listOf("\"some text\"", "42", "42", "42", "42", "\"c\"")

// When the full list of elements above are parsed and converted to the types as defined by the function type [F_6]
// this is the expected result:
val SAMPLE_PARSED_ARGS = listOf(true, "some text", 42, 42.0, 42.toFloat(), 42.toByte(), 'c')

class KVServiceBinderTest {
    private lateinit var serviceBinder: KVServiceBinderImpl

    @BeforeMethod
    fun setUp() {
        serviceBinder = KVServiceBinderImpl()
    }

    @Test(dataProvider = "provide_binder_args_expectedArgs")
    fun bind_registersFunctionParsingParametersAndDelegatingToHandlerFunction(
        binder: BindingInitializer,
        args: List<String?>,
        result: Any
    ) {
        // execution
        testBind(binder, args, result = result)
    }

    private fun testBind(
        binder: BindingInitializer,
        args: List<String?> = emptyList(),
        method: HttpMethod = HttpMethod.POST,
        result: Any
    ) {
        // setup
        val route = "someRoute"

        // execution
        binder.invoke(serviceBinder, method, route)
        val actualEntry = serviceBinder.routeMapRegistry.asSequence().single()
        val handlerResult = actualEntry.handler.invoke(HANDLER_THIS, args)

        // evaluation
        assertThat(actualEntry.method, equalTo(method))
        assertThat(actualEntry.path, equalTo("/kv/$route"))

        assertEquals(handlerResult, result)
    }

    @DataProvider
    fun provide_binder_args_expectedArgs(): Array<Array<Any?>> {
        fun args(argCount: Int, f: BindingInitializer): Array<Any?> =
            arrayOf(f, SAMPLE_STRING_ARGS.subList(0, argCount), SAMPLE_PARSED_ARGS[argCount])

        // Try for each of the seven possible `bind`-invocations, that the correct method with correctly converted
        // arguments is registered and called:
        return Array(BINDING_INITIALIZERS.size) { args(it, BINDING_INITIALIZERS[it]) }
    }

    @Test
    fun bind_canUseGetMethod_ifNoArgs() {
        // execution & evaluation
        testBind(BINDING_INITIALIZERS[0], method = HttpMethod.GET, result = true)
    }

    @Test(
        dataProvider = "provide_bindingInitializersWithArgs",
        expectedExceptions = [UnsupportedOperationException::class]
    )
    fun bind_failsForGetMethod_ifHasArgs(bindingInitializer: BindingInitializer) {
        // execution
        testBind(bindingInitializer, method = HttpMethod.GET, result = true)

        // evaluation handled by expectedExceptions
    }

    @DataProvider
    fun provide_bindingInitializersWithArgs(): Array<BindingInitializer> =
        BINDING_INITIALIZERS.copyOfRange(1, BINDING_INITIALIZERS.size)

    @Test
    fun bind_generatesRouteName_ifNoneGiven() {
        // execution
        serviceBinder.bind(PARAM_0_FUN, HttpMethod.GET)

        // evaluation
        assertThat(serviceBinder.routeMapRegistry.asSequence().single().path, equalTo("/kv/routeKVServiceBinderImpl0"))
    }

    @DelicateCoroutinesApi
    @Test
    fun bind_delegatesToHandlingFunction_forWebsockets() {
        // setup
        val handler: suspend Any.(ReceiveChannel<String>, SendChannel<String>) -> Unit =
            { requestChannel, responseChannel ->
                GlobalScope.launch {
                    responseChannel.send(requestChannel.receive())
                }
            }
        val receiveChannel = Channel<String>()
        val responseChannel = Channel<String>()
        val clientServerMessage = "a message from client to server"

        // execution
        serviceBinder.bind(handler)
        val entry = serviceBinder.webSocketRequests.entries.single()
        entry.value(HANDLER_THIS, receiveChannel, responseChannel)
        val response = runBlocking {
            receiveChannel.send(clientServerMessage)
            responseChannel.receive()
        }

        // evaluation
        assertEquals(response, clientServerMessage)
    }

    @Test(dataProvider = "provide_route_expectedUrl_forWebsockets")
    fun bind_registersCorrectUrl_forWebsockets(route: String?, expectedUrl: String) {
        // execution
        serviceBinder.bind({ _: ReceiveChannel<String>, _: SendChannel<String> -> }, route)

        // evaluation
        assertThat(serviceBinder.webSocketRequests.keys.single(), equalTo(expectedUrl))
    }

    @DataProvider
    fun provide_route_expectedUrl_forWebsockets(): Array<Array<Any?>> = arrayOf(
        arrayOf("someRoute", "/kvws/someRoute"),
        arrayOf(null, "/kvws/routeKVServiceBinderImpl0")
    )
}

private class KVServiceBinderImpl : KVServiceBinder<Any, RouteHandler, WebsocketHandler>() {
    override fun <RET> createRequestHandler(
        method: HttpMethod,
        function: suspend Any.(params: List<String?>) -> RET,
        serializer: KSerializer<RET>
    ): RouteHandler =
        { runBlocking { function.invoke(HANDLER_THIS, it) } }

    @OptIn(FlowPreview::class, DelicateCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    override fun <REQ, RES> createWebsocketHandler(
        function: suspend Any.(ReceiveChannel<REQ>, SendChannel<RES>) -> Unit,
        requestSerializer: KSerializer<REQ>,
        responseSerializer: KSerializer<RES>,
    ): WebsocketHandler {
        return { receiveChannel, sendChannel ->
            runBlocking {
                function.invoke(
                    HANDLER_THIS,
                    receiveChannel.consumeAsFlow().map { it as REQ }.produceIn(GlobalScope),
                    sendChannel as SendChannel<RES>
                )
            }
        }
    }
}
