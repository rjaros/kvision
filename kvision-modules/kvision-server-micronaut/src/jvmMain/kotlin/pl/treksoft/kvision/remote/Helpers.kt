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

import io.micronaut.core.convert.ArgumentConversionContext
import io.micronaut.core.convert.value.MutableConvertibleValues
import io.micronaut.http.MediaType
import io.micronaut.websocket.CloseReason
import io.micronaut.websocket.WebSocketSession
import org.reactivestreams.Publisher
import java.net.URI
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Empty implementation of the WebSocketSession interface
 */
internal class KVWebSocketSession : WebSocketSession {
    override fun <T : Any?> get(name: CharSequence?, conversionContext: ArgumentConversionContext<T>?): Optional<T> {
        throw IllegalStateException("Empty implementation")
    }

    override fun names(): MutableSet<String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun values(): MutableCollection<Any> {
        throw IllegalStateException("Empty implementation")
    }

    override fun put(key: CharSequence?, value: Any?): MutableConvertibleValues<Any> {
        throw IllegalStateException("Empty implementation")
    }

    override fun remove(key: CharSequence?): MutableConvertibleValues<Any> {
        throw IllegalStateException("Empty implementation")
    }

    override fun clear(): MutableConvertibleValues<Any> {
        throw IllegalStateException("Empty implementation")
    }

    override fun close() {
        throw IllegalStateException("Empty implementation")
    }

    override fun close(closeReason: CloseReason?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getId(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getAttributes(): MutableConvertibleValues<Any> {
        throw IllegalStateException("Empty implementation")
    }

    override fun isOpen(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun isWritable(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun isSecure(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getOpenSessions(): MutableSet<out WebSocketSession> {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRequestURI(): URI {
        throw IllegalStateException("Empty implementation")
    }

    override fun getProtocolVersion(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun <T : Any?> send(message: T, mediaType: MediaType?): Publisher<T> {
        throw IllegalStateException("Empty implementation")
    }

    override fun <T : Any?> sendAsync(message: T, mediaType: MediaType?): CompletableFuture<T> {
        throw IllegalStateException("Empty implementation")
    }

}