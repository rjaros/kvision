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

import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlin.coroutines.CoroutineContext

/**
 * @suppress internal class
 */
@Suppress("UNUSED_PARAMETER", "OverridingDeprecatedMember")
class DummyWebSocketServerSession : WebSocketServerSession {
    override val call: ApplicationCall
        get() = throw UnsupportedOperationException()
    override val coroutineContext: CoroutineContext
        get() = throw UnsupportedOperationException()

    override val extensions: List<WebSocketExtension<*>>
        get() = throw UnsupportedOperationException()
    override val incoming: ReceiveChannel<Frame>
        get() = throw UnsupportedOperationException()
    override var masking: Boolean
        get() = throw UnsupportedOperationException()
        set(value) {
            throw UnsupportedOperationException()
        }
    override var maxFrameSize: Long
        get() = throw UnsupportedOperationException()
        set(value) {
            throw UnsupportedOperationException()
        }
    override val outgoing: SendChannel<Frame>
        get() = throw UnsupportedOperationException()

    override suspend fun flush() {
        throw UnsupportedOperationException()
    }

    @Deprecated("Use cancel() instead.", replaceWith = ReplaceWith("cancel()", "kotlinx.coroutines.cancel"))
    override fun terminate() {
        throw UnsupportedOperationException()
    }
}
