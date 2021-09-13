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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.CloseEvent
import org.w3c.dom.ErrorEvent
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.WebSocket.Companion.CLOSED
import org.w3c.dom.WebSocket.Companion.CLOSING
import org.w3c.dom.WebSocket.Companion.OPEN
import org.w3c.dom.events.Event
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//
// Code taken from: https://discuss.kotlinlang.org/t/js-coroutines-experiements/8245/2
//

/**
 * Websocket closed exception class.
 */
class SocketClosedException(val reason: String) : Throwable(reason)

/**
 * A websocket client implementation.
 */
class Socket {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var eventQueue: Channel<Event> = Channel(Channel.UNLIMITED)
    private lateinit var ws: WebSocket
    val state: Short
        get() = ws.readyState

    private fun onWsEvent(event: Event) {
        scope.launch { eventQueue.send(event) }
    }

    /**
     * Connect to a websocket.
     */
    suspend fun connect(url: String, retryDelay: Long = 1000) {
        while (true) {
            val connected = suspendCoroutine<Boolean> { cont ->
                while (eventQueue.tryReceive().isSuccess) {/*drain*/
                }
                ws = WebSocket(url)
                ws.onopen = {
                    ws.onclose = ::onWsEvent
                    ws.onerror = ::onWsEvent
                    cont.resume(true)
                }
                ws.onmessage = ::onWsEvent
                ws.onerror = {
                    logError(it)
                }
                ws.onclose = {
                    cont.resume(false)
                }
            }
            if (connected)
                break
            else
                delay(retryDelay)
        }
    }

    /**
     * Receive a string from a websocket.
     */
    @Suppress("ThrowsCount", "MagicNumber")
    suspend fun receive(): String {
        return when (val event = eventQueue.receive()) {
            is MessageEvent -> {
                event.data as String
            }
            is CloseEvent -> {
                val reason = getReason(event.code)
                throw SocketClosedException(reason)
            }
            is ErrorEvent -> {
                logError(event)
                close()
                throw SocketClosedException(event.message)
            }
            else -> {
                val reason = getReason(4001)
                console.error(reason)
                close()
                throw SocketClosedException(reason)
            }
        }
    }

    /**
     * Send string to a websocket.
     */
    @Suppress("MagicNumber")
    fun send(obj: String) {
        when {
            isClosed() -> {
                console.error(getReason(4002))
                throw SocketClosedException(getReason(4002))
            }
            else -> ws.send(obj)
        }
    }

    /**
     * Close a websocket.
     */
    @Suppress("MagicNumber")
    fun close(code: Short = 1000) {
        when (state) {
            OPEN -> ws.close(code, getReason(1000))
        }
    }

    /**
     * Returns if a websocket is closed.
     */
    fun isClosed(): Boolean {
        return when (state) {
            CLOSED, CLOSING -> true
            else -> false
        }
    }

    private fun logError(event: Event) = console.error("An error %o occurred when connecting to ${ws.url}", event)

    @Suppress("ComplexMethod", "MagicNumber")
    private fun getReason(code: Short): String {
        return when (code.toInt()) { // See http://tools.ietf.org/html/rfc6455#section-7.4.1
            1000 -> "Normal closure"
            1001 -> "An endpoint is \"going away\", such as a server going down or " +
                    "a browser having navigated away from a page."
            1002 -> "An endpoint is terminating the connection due to a protocol error"
            1003 -> "An endpoint is terminating the connection because it has received a type of data it cannot " +
                    "accept (e.g., an endpoint that understands only text data MAY send this if it receives a " +
                    "binary message)."
            1004 -> "Reserved. The specific meaning might be defined in the future."
            1005 -> "No status code was actually present."
            1006 -> "The connection was closed abnormally, e.g., without sending or receiving a Close control frame"
            1007 -> "An endpoint is terminating the connection because it has received data within a message that " +
                    "was not consistent with the type of the message (e.g., non-UTF-8 " +
                    "[http://tools.ietf.org/html/rfc3629] data within a text message)."
            1008 -> "An endpoint is terminating the connection because it has received a message that " +
                    "\"violates its policy\". This reason is given either if there is no other sutible reason, or " +
                    "if there is a need to hide specific details about the policy."
            1009 -> "An endpoint is terminating the connection because it has received a message that is too big " +
                    "for it to process."
            1010 -> "An endpoint (client ) is terminating the connection because it has expected the server to " +
                    "negotiate one or more extension, but the server didn't return them in the response message of " +
                    "the WebSocket handshake. <br /> Specifically, the extensions that are needed are: "
            1011 -> "A server is terminating the connection because it encountered an unexpected condition that " +
                    "prevented it from fulfilling the request."
            1015 -> "The connection was closed due to a failure to perform a TLS handshake (e.g., the server " +
                    "certificate can't be verified)."
            4001 -> "Unexpected event"
            4002 -> "You are trying to use closed socket"
            else -> "Unknown reason"
        }
    }
}
