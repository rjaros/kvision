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
package io.kvision.remote

import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.await
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.w3c.dom.get
import org.w3c.fetch.RequestInit

/**
 * Client side agent for JSON-RPC remote calls.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("LargeClass", "TooManyFunctions")
open class KVRemoteAgent<T : Any>(
    val serviceManager: KVServiceMgr<T>,
    serializersModules: List<SerializersModule>? = null,
    val requestFilter: (suspend RequestInit.() -> Unit)? = null
) {

    val callAgent = CallAgent()
    val json = RemoteSerialization.getJson(serializersModules)

    inline fun <reified PAR> serialize(value: PAR): String {
        return json.encodeToString(value)
    }

    // Workaround for KT-41282 on legacy compiler
    inline fun <reified PAR> serialize(serializer: SerializationStrategy<PAR>, value: PAR): String {
        return json.encodeToString(serializer, value)
    }

    inline fun <reified PAR : Any> deserialize(value: String): PAR {
        return json.decodeFromString(value)
    }

    // Workaround for KT-41282 on legacy compiler
    inline fun <reified PAR : Any> deserialize(serializer: DeserializationStrategy<PAR>, value: String): PAR {
        return json.decodeFromString(serializer, value)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified RET : Any, T> call(noinline function: suspend T.() -> RET): RET {
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, method = method, requestFilter = requestFilter).await()
        return deserialize(result)

    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified RET : Any, T> call(
        noinline function: suspend T.() -> List<RET>
    ): List<RET> {
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, method = method, requestFilter = requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified PAR, reified RET : Any, T> call(
        noinline function: suspend T.(PAR) -> RET, p: PAR
    ): RET {
        val data = serialize(p)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified PAR, reified RET : Any, T> call(
        noinline function: suspend T.(PAR) -> List<RET>, p: PAR
    ): List<RET> {
        val data = serialize(p)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified PAR1, reified PAR2, reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2) -> RET, p1: PAR1, p2: PAR2
    ): RET {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data1, data2), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified PAR1, reified PAR2, reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2) -> List<RET>, p1: PAR1, p2: PAR2
    ): List<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data1, data2), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2, PAR3) -> RET, p1: PAR1, p2: PAR2, p3: PAR3
    ): RET {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data1, data2, data3), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2, PAR3) -> List<RET>, p1: PAR1, p2: PAR2, p3: PAR3
    ): List<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data1, data2, data3), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4) -> RET, p1: PAR1, p2: PAR2, p3: PAR3, p4: PAR4
    ): RET {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    suspend inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4) -> List<RET>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4
    ): List<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    @Suppress("LongParameterList")
    suspend inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5,
        reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5) -> RET,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        p5: PAR5
    ): RET {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val data5 = serialize(p5)
        val (url, method) = serviceManager.requireCall(function)
        val result =
            callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    @Suppress("LongParameterList")
    suspend inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5,
        reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5) -> List<RET>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        p5: PAR5
    ): List<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val data5 = serialize(p5)
        val (url, method) = serviceManager.requireCall(function)
        val result =
            callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5), method, requestFilter).await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    @Suppress("LongParameterList")
    suspend inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5, reified PAR6,
        reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5, PAR6) -> RET,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        p5: PAR5,
        p6: PAR6
    ): RET {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val data5 = serialize(p5)
        val data6 = serialize(p6)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5, data6), method, requestFilter)
            .await()
        return deserialize(result)
    }

    /**
     * Executes defined call to a remote web service.
     */
    @Suppress("LongParameterList")
    suspend inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5, reified PAR6,
        reified RET : Any, T> call(
        noinline function: suspend T.(PAR1, PAR2, PAR3, PAR4, PAR5, PAR6) -> List<RET>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        p5: PAR5,
        p6: PAR6
    ): List<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val data5 = serialize(p5)
        val data6 = serialize(p6)
        val (url, method) = serviceManager.requireCall(function)
        val result = callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5, data6), method, requestFilter)
            .await()
        return deserialize(result)
    }

    /**
     * Executes defined web socket connection
     */
    @Suppress("ComplexMethod", "TooGenericExceptionCaught")
    suspend inline fun <reified PAR1 : Any, reified PAR2 : Any> webSocket(
        noinline function: suspend T.(ReceiveChannel<PAR1>, SendChannel<PAR2>) -> Unit,
        noinline handler: suspend (SendChannel<PAR1>, ReceiveChannel<PAR2>) -> Unit
    ) {
        val kvUrlPrefix = window["kv_remote_url_prefix"]
        val urlPrefix: String = if (kvUrlPrefix != undefined) "$kvUrlPrefix/" else ""
        val (url, _) = serviceManager.requireCall(function)
        val serializerPAR1 = serializer<PAR1>()
        val serializerPAR2 = serializer<PAR2>()
        val socket = Socket()
        val requestChannel = Channel<PAR1>()
        val responseChannel = Channel<PAR2>()
        try {
            coroutineScope {
                socket.connect(getWebSocketUrl(urlPrefix + url.drop(1)))
                lateinit var responseJob: Job
                lateinit var handlerJob: Job
                val requestJob = launch {
                    for (par1 in requestChannel) {
                        val param = serialize(serializerPAR1, par1)
                        val str = RemoteSerialization.plain.encodeToString(
                            JsonRpcRequest(
                                0,
                                url,
                                listOf(param)
                            )
                        )
                        if (!socket.sendOrFalse(str)) break
                    }
                    responseJob.cancel()
                    handlerJob.cancel()
                    if (!requestChannel.isClosedForReceive) requestChannel.close()
                    if (!responseChannel.isClosedForSend) responseChannel.close()
                }
                responseJob = launch {
                    while (true) {
                        val str = socket.receiveOrNull() ?: break
                        val data = kotlin.js.JSON.parse<dynamic>(str).result ?: ""
                        val par2 = deserialize(serializerPAR2, data)
                        responseChannel.send(par2)
                    }
                    requestJob.cancel()
                    handlerJob.cancel()
                    if (!requestChannel.isClosedForReceive) requestChannel.close()
                    if (!responseChannel.isClosedForSend) responseChannel.close()
                }
                handlerJob = launch {
                    exceptionHelper {
                        handler(requestChannel, responseChannel)
                    }
                    requestJob.cancel()
                    responseJob.cancel()
                    if (!requestChannel.isClosedForReceive) requestChannel.close()
                    if (!responseChannel.isClosedForSend) responseChannel.close()
                }
            }
        } catch (e: Exception) {
            console.log(e)
        }
        if (!requestChannel.isClosedForReceive) requestChannel.close()
        if (!responseChannel.isClosedForSend) responseChannel.close()
        socket.close()
    }

    /**
     * Executes defined web socket connection returning list objects
     */
    @Suppress("ComplexMethod", "TooGenericExceptionCaught")
    suspend inline fun <reified PAR1 : Any, reified PAR2 : Any> webSocket(
        noinline function: suspend T.(ReceiveChannel<PAR1>, SendChannel<List<PAR2>>) -> Unit,
        noinline handler: suspend (SendChannel<PAR1>, ReceiveChannel<List<PAR2>>) -> Unit
    ) {
        val kvUrlPrefix = window["kv_remote_url_prefix"]
        val urlPrefix: String = if (kvUrlPrefix != undefined) "$kvUrlPrefix/" else ""
        val (url, _) = serviceManager.requireCall(function)
        val serializerPAR1 = serializer<PAR1>()
        val serializerPAR2 = serializer<PAR2>()
        val socket = Socket()
        val requestChannel = Channel<PAR1>()
        val responseChannel = Channel<List<PAR2>>()
        try {
            coroutineScope {
                socket.connect(getWebSocketUrl(urlPrefix + url.drop(1)))
                lateinit var responseJob: Job
                lateinit var handlerJob: Job
                val requestJob = launch {
                    for (par1 in requestChannel) {
                        val param = serialize(serializerPAR1, par1)
                        val str = RemoteSerialization.plain.encodeToString(
                            JsonRpcRequest(
                                0,
                                url,
                                listOf(param)
                            )
                        )
                        if (!socket.sendOrFalse(str)) break
                    }
                    responseJob.cancel()
                    handlerJob.cancel()
                    if (!requestChannel.isClosedForReceive) requestChannel.close()
                    if (!responseChannel.isClosedForSend) responseChannel.close()
                }
                responseJob = launch {
                    while (true) {
                        val str = socket.receiveOrNull() ?: break
                        val data = kotlin.js.JSON.parse<dynamic>(str).result ?: ""
                        val par2 = deserialize(ListSerializer(serializerPAR2), data)
                        responseChannel.send(par2)
                    }
                    requestJob.cancel()
                    handlerJob.cancel()
                    if (!requestChannel.isClosedForReceive) requestChannel.close()
                    if (!responseChannel.isClosedForSend) responseChannel.close()
                }
                handlerJob = launch {
                    exceptionHelper {
                        handler(requestChannel, responseChannel)
                    }
                    requestJob.cancel()
                    responseJob.cancel()
                    if (!requestChannel.isClosedForReceive) requestChannel.close()
                    if (!responseChannel.isClosedForSend) responseChannel.close()
                }
            }
        } catch (e: Exception) {
            console.log(e)
        }
        if (!requestChannel.isClosedForReceive) requestChannel.close()
        if (!responseChannel.isClosedForSend) responseChannel.close()
        socket.close()
    }

    /**
     * @suppress internal function
     */
    suspend fun Socket.receiveOrNull(): String? {
        return try {
            this.receive()
        } catch (e: SocketClosedException) {
            console.log("Socket was closed: ${e.reason}")
            null
        }
    }

    /**
     * @suppress internal function
     */
    fun Socket.sendOrFalse(str: String): Boolean {
        return try {
            this.send(str)
            true
        } catch (e: SocketClosedException) {
            console.log("Socket was closed: ${e.reason}")
            false
        }
    }

    /**
     * @suppress internal function
     */
    @Suppress("TooGenericExceptionCaught")
    suspend fun exceptionHelper(block: suspend () -> Unit) {
        try {
            block()
        } catch (e: CancellationException) {
            console.log(e.message)
        } catch (e: Exception) {
            console.log(e)
        }
    }

}
