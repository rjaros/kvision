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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.serializer
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@ExperimentalCoroutinesApi
class SseHandlerKtTest {
    private val deSerializer = kotlinxObjectDeSerializer()
    private lateinit var service: DummyServiceSse
    private lateinit var rawServerToClient: Channel<String>
    private val undeliveredFromServer = mutableListOf<String>()

    @BeforeMethod
    fun setUp() {
        service = DummyServiceSse()
        rawServerToClient = Channel { undeliveredFromServer += it }
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
        MatcherAssert.assertThat(
            deSerializer.deserialize<JsonRpcResponse>(actual).result,
            // note that `result` is encoded as JSON within the JSON, thus double quotes:
            Matchers.equalTo("\"$message\"")
        )
    }

    @Test
    fun closesChannels_ifFunctionThrows() {
        // execution (and throws evaluation)
        Assert.assertThrows(DummyExceptionSse::class.java) {
            runTest {
                service.beforeReturn = { throw DummyExceptionSse() }
            }
        }

        // evaluation
        assertChannelsClosedNoUndelivered()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun assertChannelsClosedNoUndelivered() {
        MatcherAssert.assertThat(
            "rawOutgoing.isClosedForReceive",
            rawServerToClient.isClosedForReceive,
            Matchers.equalTo(true)
        )
        MatcherAssert.assertThat(
            "rawOutgoing.isClosedForSend",
            rawServerToClient.isClosedForSend,
            Matchers.equalTo(true)
        )
        MatcherAssert.assertThat(undeliveredFromServer, Matchers.empty())
    }

    private fun <T> runTest(test: suspend () -> T): T =
        runBlocking {
            handleSseConnection(this)
            withContext(Dispatchers.Default) {
                service.waitUntilInitialized()
                val result = test()
                service.endSession()
                result
            }
        }

    private fun handleSseConnection(scope: CoroutineScope) {
        scope.launch {
            handleSseConnection(
                deSerializer,
                rawServerToClient,
                String.serializer(),
                service
            ) { sendChannel -> service.serviceMethod(sendChannel) }
        }
    }
}

private class DummyExceptionSse : Exception("dummy exception")

private class DummyServiceSse {
    var beforeReturn = {}
    lateinit var outgoing: SendChannel<String>

    private val initialized = Mutex(true)
    private val mayComplete = Mutex(true)

    suspend fun serviceMethod(outgoing: SendChannel<String>) {
        require(!this::outgoing.isInitialized)
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
