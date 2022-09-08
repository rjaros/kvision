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

import io.javalin.http.Context
import io.javalin.http.HandlerType
import io.javalin.http.HttpStatus
import io.javalin.websocket.WsContext
import jakarta.servlet.ServletOutputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.eclipse.jetty.websocket.api.CloseStatus
import org.eclipse.jetty.websocket.api.RemoteEndpoint
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.SuspendToken
import org.eclipse.jetty.websocket.api.UpgradeRequest
import org.eclipse.jetty.websocket.api.UpgradeResponse
import org.eclipse.jetty.websocket.api.WebSocketBehavior
import org.eclipse.jetty.websocket.api.WebSocketPolicy
import java.io.InputStream
import java.net.InetSocketAddress
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

/**
 * Empty subclass of the Context class
 */
internal class KVContext : Context {
    override fun <T> appAttribute(key: String): T {
        throw IllegalStateException("Empty implementation")
    }

    override fun endpointHandlerPath(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun future(future: Supplier<out CompletableFuture<*>>) {
        throw IllegalStateException("Empty implementation")
    }

    override fun handlerType(): HandlerType {
        throw IllegalStateException("Empty implementation")
    }

    override fun matchedPath(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun outputStream(): ServletOutputStream {
        throw IllegalStateException("Empty implementation")
    }

    override fun pathParam(key: String): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun pathParamMap(): Map<String, String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun redirect(location: String, status: HttpStatus) {
        throw IllegalStateException("Empty implementation")
    }

    override fun req(): HttpServletRequest {
        throw IllegalStateException("Empty implementation")
    }

    override fun res(): HttpServletResponse {
        throw IllegalStateException("Empty implementation")
    }

    override fun result(): String? {
        throw IllegalStateException("Empty implementation")
    }

    override fun result(resultStream: InputStream): Context {
        throw IllegalStateException("Empty implementation")
    }

    override fun resultInputStream(): InputStream? {
        throw IllegalStateException("Empty implementation")
    }

}

/**
 * Empty subclass of the WsContext class
 */
internal class KVWsContext : WsContext("EMPTY", KVSession())

/**
 * Empty implementation of the Session interface
 */
internal class KVSession : Session {
    override fun getRemote(): RemoteEndpoint {
        throw IllegalStateException("Empty implementation")
    }

    override fun getLocalAddress(): InetSocketAddress {
        throw IllegalStateException("Empty implementation")
    }

    override fun disconnect() {
        throw IllegalStateException("Empty implementation")
    }

    override fun getProtocolVersion(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getUpgradeResponse(): UpgradeResponse {
        throw IllegalStateException("Empty implementation")
    }

    override fun getPolicy(): WebSocketPolicy {
        throw IllegalStateException("Empty implementation")
    }

    override fun getUpgradeRequest(): UpgradeRequest {
        throw IllegalStateException("Empty implementation")
    }

    override fun suspend(): SuspendToken {
        throw IllegalStateException("Empty implementation")
    }

    override fun isOpen(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getBehavior(): WebSocketBehavior {
        throw IllegalStateException("Empty implementation")
    }

    override fun getIdleTimeout(): Duration {
        throw IllegalStateException("Empty implementation")
    }

    override fun getInputBufferSize(): Int {
        throw IllegalStateException("Empty implementation")
    }

    override fun getOutputBufferSize(): Int {
        throw IllegalStateException("Empty implementation")
    }

    override fun getMaxBinaryMessageSize(): Long {
        throw IllegalStateException("Empty implementation")
    }

    override fun getMaxTextMessageSize(): Long {
        throw IllegalStateException("Empty implementation")
    }

    override fun getMaxFrameSize(): Long {
        throw IllegalStateException("Empty implementation")
    }

    override fun isAutoFragment(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun setIdleTimeout(duration: Duration?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setInputBufferSize(size: Int) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setOutputBufferSize(size: Int) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setMaxBinaryMessageSize(size: Long) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setMaxTextMessageSize(size: Long) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setMaxFrameSize(maxFrameSize: Long) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setAutoFragment(autoFragment: Boolean) {
        throw IllegalStateException("Empty implementation")
    }

    override fun close() {
        throw IllegalStateException("Empty implementation")
    }

    override fun close(closeStatus: CloseStatus?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun close(statusCode: Int, reason: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun isSecure(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRemoteAddress(): InetSocketAddress {
        throw IllegalStateException("Empty implementation")
    }

}
