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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalTo
import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@ExperimentalCoroutinesApi
class WebsocketHandlerKtTest {
    private val deSerializer = kotlinxObjectDeSerializer()
    private lateinit var service: DummyService
    private lateinit var rawClientToServer: Channel<String>
    private lateinit var rawServerToClient: Channel<String>
    private val undeliveredFromClient = mutableListOf<String>()
    private val undeliveredFromServer = mutableListOf<String>()

    @BeforeMethod
    fun setUp() {
        service = DummyService()
        rawClientToServer = Channel { undeliveredFromClient += it }
        rawServerToClient = Channel { undeliveredFromServer += it }
    }

    @Test
    fun passesParametersToFunction() {
        // setup
        val message = "a message from client to server"

        // execution
        val actual = runTest {
            sendMessage(message)
            service.incoming.receive()
        }

        // evaluation
        assertChannelsClosedNoUndelivered()
        assertThat(actual, equalTo(message))
    }

    @Test
    fun writesDataGeneratedByFunctionToOutChannel() {
        // setup
        val message = "a message from server to client"

        // execution
        val actual = runTest {
            service.outgoing.send(message)
            rawServerToClient.receive()
        }

        // evaluation
        assertThat(
            deSerializer.deserialize<JsonRpcResponse>(actual).result,
            // note that `result` is encoded as JSON within the JSON, thus double quotes:
            equalTo("\"$message\"")
        )
    }

    @Test
    fun closesChannels_ifFunctionThrows() {
        // execution (and throws evaluation)
        Assert.assertThrows(DummyException::class.java) {
            runTest {
                service.beforeReturn = { throw DummyException() }
            }
        }

        // evaluation
        assertChannelsClosedNoUndelivered()
    }

    @Test
    fun closesOnInvalidDataFromClient() {
        // execution (and expect evaluation)
        Assert.expectThrows(SerializationException::class.java) {
            runTest {
                rawClientToServer.send("invalid")
            }
        }

        // evaluation
        assertChannelsClosedNoUndelivered()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun assertChannelsClosedNoUndelivered() {
        assertThat("rawIncoming.isClosedForReceive", rawClientToServer.isClosedForReceive, equalTo(true))
        assertThat("rawIncoming.isClosedForSend", rawClientToServer.isClosedForSend, equalTo(true))
        assertThat("rawOutgoing.isClosedForReceive", rawServerToClient.isClosedForReceive, equalTo(true))
        assertThat("rawOutgoing.isClosedForSend", rawServerToClient.isClosedForSend, equalTo(true))
        assertThat(undeliveredFromClient, empty())
        assertThat(undeliveredFromServer, empty())
    }

    private fun <T> runTest(test: suspend () -> T): T =
        runBlocking {
            handleWebsocketConnection(this)
            withContext(Dispatchers.Default) {
                service.waitUntilInitialized()
                val result = test()
                service.endSession()
                result
            }
        }

    private fun handleWebsocketConnection(scope: CoroutineScope) {
        scope.launch {
            handleWebsocketConnection(
                deSerializer,
                rawClientToServer,
                rawServerToClient,
                String.serializer(),
                String.serializer(),
                service
            ) { receiveChannel, sendChannel -> service.serviceMethod(receiveChannel, sendChannel) }
        }
    }

    private suspend fun sendMessage(message: String) {
        rawClientToServer.send(deSerializer.serializeNonNull(JsonRpcRequest(
            id = 42,
            method = "dummy",
            params = listOf(deSerializer.serializeNonNull(message))
        )))
    }
}

private class DummyException : Exception("dummy exception")

private class DummyService {
    var beforeReturn = {}
    lateinit var incoming: ReceiveChannel<String>
    lateinit var outgoing: SendChannel<String>

    private val initialized = Mutex(true)
    private val mayComplete = Mutex(true)

    suspend fun serviceMethod(incoming: ReceiveChannel<String>, outgoing: SendChannel<String>) {
        require(!this::incoming.isInitialized && !this::outgoing.isInitialized)
        this.incoming = incoming
        this.outgoing = outgoing
        initialized.unlock()
        // the actual logic happens in the test methods, so we just wait here until the test method signals that we
        // may complete
        mayComplete.lock()
        beforeReturn()
    }

    suspend fun waitUntilInitialized() {
        initialized.lock()
        initialized.unlock()
    }

    fun endSession() {
        mayComplete.unlock()
    }
}
