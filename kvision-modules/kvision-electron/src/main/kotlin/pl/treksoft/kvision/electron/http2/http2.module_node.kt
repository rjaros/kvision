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

@file:JsModule("http2")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.http2

import node.buffer.Buffer
import org.khronos.webgl.*
import pl.treksoft.kvision.electron.events.internal.EventEmitter
import pl.treksoft.kvision.electron.fs.Stats
import pl.treksoft.kvision.electron.fs.promises.FileHandle
import pl.treksoft.kvision.electron.http.OutgoingHttpHeaders
import pl.treksoft.kvision.electron.net.Server
import pl.treksoft.kvision.electron.net.Socket
import pl.treksoft.kvision.electron.stream.internal.*
import pl.treksoft.kvision.electron.tls.ConnectionOptions
import pl.treksoft.kvision.electron.tls.TLSSocket
import pl.treksoft.kvision.electron.tls.TlsOptions
import pl.treksoft.kvision.electron.url.URL
import pl.treksoft.kvision.electron.http.IncomingHttpHeaders as Http1IncomingHttpHeaders

external interface IncomingHttpStatusHeader {
    operator fun get(key: String): Number?
    operator fun set(key: String, value: Number?)
}

external interface IncomingHttpHeaders : Http1IncomingHttpHeaders {
    override operator fun get(key: String): String?
    override operator fun set(key: String, value: String?)
}

external interface StreamPriorityOptions {
    var exclusive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var parent: Number?
        get() = definedExternally
        set(value) = definedExternally
    var weight: Number?
        get() = definedExternally
        set(value) = definedExternally
    var silent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface StreamState {
    var localWindowSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var state: Number?
        get() = definedExternally
        set(value) = definedExternally
    var localClose: Number?
        get() = definedExternally
        set(value) = definedExternally
    var remoteClose: Number?
        get() = definedExternally
        set(value) = definedExternally
    var sumDependencyWeight: Number?
        get() = definedExternally
        set(value) = definedExternally
    var weight: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ServerStreamResponseOptions {
    var endStream: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var waitForTrailers: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface StatOptions {
    var offset: Number
    var length: Number
}

external interface ServerStreamFileResponseOptions {
    val statCheck: ((stats: Stats, headers: OutgoingHttpHeaders, statOptions: StatOptions) -> dynamic)?
        get() = definedExternally
    var waitForTrailers: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var offset: Number?
        get() = definedExternally
        set(value) = definedExternally
    var length: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ServerStreamFileResponseOptionsWithError : ServerStreamFileResponseOptions {
    val onError: ((err: Exception) -> Unit)?
        get() = definedExternally
}

external interface Http2Stream : Duplex {
    var aborted: Boolean
    var bufferSize: Number
    var closed: Boolean
    override var destroyed: Boolean
    var endAfterHeaders: Boolean
    var id: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pending: Boolean
    var rstCode: Number
    var sentHeaders: OutgoingHttpHeaders
    var sentInfoHeaders: Array<OutgoingHttpHeaders>?
        get() = definedExternally
        set(value) = definedExternally
    var sentTrailers: OutgoingHttpHeaders?
        get() = definedExternally
        set(value) = definedExternally
    var session: Http2Session
    var state: StreamState
    fun close(code: Number = definedExternally, callback: () -> Unit = definedExternally)
    fun priority(options: StreamPriorityOptions)
    fun setTimeout(msecs: Number, callback: () -> Unit = definedExternally)
    fun sendTrailers(headers: OutgoingHttpHeaders)
    override fun addListener(
        event: String /* "aborted" | "close" | "drain" | "end" | "finish" | "timeout" | "wantTrailers" */,
        listener: () -> Unit
    ): Http2Stream /* this */

    override fun addListener(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2Stream /* this */

    override fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Stream /* this */
    fun addListener(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number) -> Unit
    ): Http2Stream /* this */

    fun addListener(event: String /* "pipe" | "unpipe" */, listener: (src: Readable) -> Unit): Http2Stream /* this */
    fun addListener(event: String /* "streamClosed" */, listener: (code: Number) -> Unit): Http2Stream /* this */
    fun addListener(
        event: String /* "trailers" */,
        listener: (trailers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Stream /* this */

    override fun addListener(event: String, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun emit(event: String /* "aborted" | "close" | "drain" | "end" | "finish" | "timeout" | "wantTrailers" */): Boolean
    fun emit(event: String /* "data" */, chunk: Buffer): Boolean
    override fun emit(event: String /* "data" */, chunk: Any): Boolean
    fun emit(event: String /* "data" */, chunk: String): Boolean
    override fun emit(event: String /* "error" */, err: Error): Boolean
    fun emit(event: String /* "frameError" */, frameType: Number, errorCode: Number): Boolean
    fun emit(event: String /* "pipe" | "unpipe" */, src: Readable): Boolean
    fun emit(event: String /* "streamClosed" */, code: Number): Boolean
    fun emit(event: String /* "trailers" */, trailers: IncomingHttpHeaders, flags: Number): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    override fun on(
        event: String /* "aborted" | "close" | "drain" | "end" | "finish" | "timeout" | "wantTrailers" */,
        listener: () -> Unit
    ): Http2Stream /* this */

    override fun on(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2Stream /* this */

    override fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Stream /* this */
    fun on(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number) -> Unit
    ): Http2Stream /* this */

    fun on(event: String /* "pipe" | "unpipe" */, listener: (src: Readable) -> Unit): Http2Stream /* this */
    fun on(event: String /* "streamClosed" */, listener: (code: Number) -> Unit): Http2Stream /* this */
    fun on(
        event: String /* "trailers" */,
        listener: (trailers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Stream /* this */

    override fun on(event: String, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun once(
        event: String /* "aborted" | "close" | "drain" | "end" | "finish" | "timeout" | "wantTrailers" */,
        listener: () -> Unit
    ): Http2Stream /* this */

    override fun once(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2Stream /* this */

    override fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Stream /* this */
    fun once(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number) -> Unit
    ): Http2Stream /* this */

    fun once(event: String /* "pipe" | "unpipe" */, listener: (src: Readable) -> Unit): Http2Stream /* this */
    fun once(event: String /* "streamClosed" */, listener: (code: Number) -> Unit): Http2Stream /* this */
    fun once(
        event: String /* "trailers" */,
        listener: (trailers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Stream /* this */

    override fun once(event: String, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun prependListener(
        event: String /* "aborted" | "close" | "drain" | "end" | "finish" | "timeout" | "wantTrailers" */,
        listener: () -> Unit
    ): Http2Stream /* this */

    override fun prependListener(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2Stream /* this */

    override fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Stream /* this */
    fun prependListener(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number) -> Unit
    ): Http2Stream /* this */

    fun prependListener(
        event: String /* "pipe" | "unpipe" */,
        listener: (src: Readable) -> Unit
    ): Http2Stream /* this */

    fun prependListener(event: String /* "streamClosed" */, listener: (code: Number) -> Unit): Http2Stream /* this */
    fun prependListener(
        event: String /* "trailers" */,
        listener: (trailers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Stream /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun prependOnceListener(
        event: String /* "aborted" | "close" | "drain" | "end" | "finish" | "timeout" | "wantTrailers" */,
        listener: () -> Unit
    ): Http2Stream /* this */

    override fun prependOnceListener(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2Stream /* this */

    override fun prependOnceListener(
        event: String /* "error" */,
        listener: (err: Error) -> Unit
    ): Http2Stream /* this */

    fun prependOnceListener(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number) -> Unit
    ): Http2Stream /* this */

    fun prependOnceListener(
        event: String /* "pipe" | "unpipe" */,
        listener: (src: Readable) -> Unit
    ): Http2Stream /* this */

    fun prependOnceListener(
        event: String /* "streamClosed" */,
        listener: (code: Number) -> Unit
    ): Http2Stream /* this */

    fun prependOnceListener(
        event: String /* "trailers" */,
        listener: (trailers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Stream /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Http2Stream /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): Http2Stream /* this */
}

external interface ClientHttp2Stream : Http2Stream {
    fun addListener(event: String /* "continue" */, listener: () -> Any): ClientHttp2Stream /* this */
    override fun addListener(
        event: String /* "headers" | "response" */,
        listener: (headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Stream /* this */

    override fun addListener(event: String, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    override fun emit(event: String /* "continue" */): Boolean
    override fun emit(
        event: String /* "headers" | "push" | "response" */,
        headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */,
        flags: Number
    ): Boolean

    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    fun on(event: String /* "continue" */, listener: () -> Any): ClientHttp2Stream /* this */
    override fun on(
        event: String /* "headers" | "response" */,
        listener: (headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Stream /* this */

    override fun on(event: String, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    fun once(event: String /* "continue" */, listener: () -> Any): ClientHttp2Stream /* this */
    override fun once(
        event: String /* "headers" | "response" */,
        listener: (headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Stream /* this */

    override fun once(event: String, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    fun prependListener(event: String /* "continue" */, listener: () -> Any): ClientHttp2Stream /* this */
    override fun prependListener(
        event: String /* "headers" | "response" */,
        listener: (headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Stream /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    fun prependOnceListener(event: String /* "continue" */, listener: () -> Any): ClientHttp2Stream /* this */
    override fun prependOnceListener(
        event: String /* "headers" | "response" */,
        listener: (headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Stream /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): ClientHttp2Stream /* this */
}

external interface ServerHttp2Stream : Http2Stream {
    var headersSent: Boolean
    var pushAllowed: Boolean
    fun additionalHeaders(headers: OutgoingHttpHeaders)
    fun pushStream(
        headers: OutgoingHttpHeaders,
        callback: (err: Error?, pushStream: ServerHttp2Stream, headers: OutgoingHttpHeaders) -> Unit = definedExternally
    )

    fun pushStream(
        headers: OutgoingHttpHeaders,
        options: StreamPriorityOptions = definedExternally,
        callback: (err: Error?, pushStream: ServerHttp2Stream, headers: OutgoingHttpHeaders) -> Unit = definedExternally
    )

    fun respond(
        headers: OutgoingHttpHeaders = definedExternally,
        options: ServerStreamResponseOptions = definedExternally
    )

    fun respondWithFD(
        fd: Number,
        headers: OutgoingHttpHeaders = definedExternally,
        options: ServerStreamFileResponseOptions = definedExternally
    )

    fun respondWithFD(
        fd: FileHandle,
        headers: OutgoingHttpHeaders = definedExternally,
        options: ServerStreamFileResponseOptions = definedExternally
    )

    fun respondWithFile(
        path: String,
        headers: OutgoingHttpHeaders = definedExternally,
        options: ServerStreamFileResponseOptionsWithError = definedExternally
    )
}

external interface Settings {
    var headerTableSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var enablePush: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var initialWindowSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxFrameSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxConcurrentStreams: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxHeaderListSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var enableConnectProtocol: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ClientSessionRequestOptions {
    var endStream: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var exclusive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var parent: Number?
        get() = definedExternally
        set(value) = definedExternally
    var weight: Number?
        get() = definedExternally
        set(value) = definedExternally
    var waitForTrailers: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SessionState {
    var effectiveLocalWindowSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var effectiveRecvDataLength: Number?
        get() = definedExternally
        set(value) = definedExternally
    var nextStreamID: Number?
        get() = definedExternally
        set(value) = definedExternally
    var localWindowSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var lastProcStreamID: Number?
        get() = definedExternally
        set(value) = definedExternally
    var remoteWindowSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var outboundQueueSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var deflateDynamicTableSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var inflateDynamicTableSize: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Http2Session : EventEmitter {
    var alpnProtocol: String?
        get() = definedExternally
        set(value) = definedExternally
    var closed: Boolean
    var connecting: Boolean
    var destroyed: Boolean
    var encrypted: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var localSettings: Settings
    var originSet: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var pendingSettingsAck: Boolean
    var remoteSettings: Settings
    var socket: dynamic /* net.Socket | tls.TLSSocket */
        get() = definedExternally
        set(value) = definedExternally
    var state: SessionState
    var type: Number
    fun close(callback: () -> Unit = definedExternally)
    fun destroy(error: Error = definedExternally, code: Number = definedExternally)
    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Uint8Array = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Uint8ClampedArray = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Uint16Array = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Uint32Array = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Int8Array = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Int16Array = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Int32Array = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Float32Array = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: Float64Array = definedExternally
    )

    fun goaway(
        code: Number = definedExternally,
        lastStreamID: Number = definedExternally,
        opaqueData: DataView = definedExternally
    )

    fun ping(callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Uint8Array, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Uint8ClampedArray, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Uint16Array, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Uint32Array, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Int8Array, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Int16Array, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Int32Array, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Float32Array, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: Float64Array, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ping(payload: DataView, callback: (err: Error?, duration: Number, payload: Buffer) -> Unit): Boolean
    fun ref()
    fun setTimeout(msecs: Number, callback: () -> Unit = definedExternally)
    fun settings(settings: Settings)
    fun unref()
    fun addListener(event: String /* "close" | "ping" | "timeout" */, listener: () -> Unit): Http2Session /* this */
    fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Session /* this */
    fun addListener(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number, streamID: Number) -> Unit
    ): Http2Session /* this */

    fun addListener(
        event: String /* "goaway" */,
        listener: (errorCode: Number, lastStreamID: Number, opaqueData: Buffer) -> Unit
    ): Http2Session /* this */

    fun addListener(
        event: String /* "localSettings" | "remoteSettings" */,
        listener: (settings: Settings) -> Unit
    ): Http2Session /* this */

    override fun addListener(event: String, listener: (args: Any) -> Unit): Http2Session /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): Http2Session /* this */
    fun emit(event: String /* "close" | "ping" | "timeout" */): Boolean
    fun emit(event: String /* "error" */, err: Error): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    fun emit(event: String /* "frameError" */, frameType: Number, errorCode: Number, streamID: Number): Boolean
    fun emit(event: String /* "goaway" */, errorCode: Number, lastStreamID: Number, opaqueData: Buffer): Boolean
    fun emit(event: String /* "localSettings" | "remoteSettings" */, settings: Settings): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    fun on(event: String /* "close" | "ping" | "timeout" */, listener: () -> Unit): Http2Session /* this */
    fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Session /* this */
    fun on(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number, streamID: Number) -> Unit
    ): Http2Session /* this */

    fun on(
        event: String /* "goaway" */,
        listener: (errorCode: Number, lastStreamID: Number, opaqueData: Buffer) -> Unit
    ): Http2Session /* this */

    fun on(
        event: String /* "localSettings" | "remoteSettings" */,
        listener: (settings: Settings) -> Unit
    ): Http2Session /* this */

    override fun on(event: String, listener: (args: Any) -> Unit): Http2Session /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): Http2Session /* this */
    fun once(event: String /* "close" | "ping" | "timeout" */, listener: () -> Unit): Http2Session /* this */
    fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Session /* this */
    fun once(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number, streamID: Number) -> Unit
    ): Http2Session /* this */

    fun once(
        event: String /* "goaway" */,
        listener: (errorCode: Number, lastStreamID: Number, opaqueData: Buffer) -> Unit
    ): Http2Session /* this */

    fun once(
        event: String /* "localSettings" | "remoteSettings" */,
        listener: (settings: Settings) -> Unit
    ): Http2Session /* this */

    override fun once(event: String, listener: (args: Any) -> Unit): Http2Session /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): Http2Session /* this */
    fun prependListener(event: String /* "close" | "ping" | "timeout" */, listener: () -> Unit): Http2Session /* this */
    fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Session /* this */
    fun prependListener(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number, streamID: Number) -> Unit
    ): Http2Session /* this */

    fun prependListener(
        event: String /* "goaway" */,
        listener: (errorCode: Number, lastStreamID: Number, opaqueData: Buffer) -> Unit
    ): Http2Session /* this */

    fun prependListener(
        event: String /* "localSettings" | "remoteSettings" */,
        listener: (settings: Settings) -> Unit
    ): Http2Session /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): Http2Session /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): Http2Session /* this */
    fun prependOnceListener(
        event: String /* "close" | "ping" | "timeout" */,
        listener: () -> Unit
    ): Http2Session /* this */

    fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): Http2Session /* this */
    fun prependOnceListener(
        event: String /* "frameError" */,
        listener: (frameType: Number, errorCode: Number, streamID: Number) -> Unit
    ): Http2Session /* this */

    fun prependOnceListener(
        event: String /* "goaway" */,
        listener: (errorCode: Number, lastStreamID: Number, opaqueData: Buffer) -> Unit
    ): Http2Session /* this */

    fun prependOnceListener(
        event: String /* "localSettings" | "remoteSettings" */,
        listener: (settings: Settings) -> Unit
    ): Http2Session /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Http2Session /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): Http2Session /* this */
}

external interface ClientHttp2Session : Http2Session {
    fun request(
        headers: OutgoingHttpHeaders = definedExternally,
        options: ClientSessionRequestOptions = definedExternally
    ): ClientHttp2Stream

    fun addListener(
        event: String /* "altsvc" */,
        listener: (alt: String, origin: String, stream: Number) -> Unit
    ): ClientHttp2Session /* this */

    fun addListener(
        event: String /* "origin" */,
        listener: (origins: Array<String>) -> Unit
    ): ClientHttp2Session /* this */

    fun addListener(
        event: String /* "connect" */,
        listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ClientHttp2Session /* this */

    fun addListener(
        event: String /* "stream" */,
        listener: (stream: ClientHttp2Stream, headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Session /* this */

    override fun addListener(event: String, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    fun emit(event: String /* "altsvc" */, alt: String, origin: String, stream: Number): Boolean
    fun emit(event: String /* "origin" */, origins: Array<String>): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    fun emit(event: String /* "connect" */, session: ClientHttp2Session, socket: Socket): Boolean
    fun emit(event: String /* "connect" */, session: ClientHttp2Session, socket: TLSSocket): Boolean
    fun emit(
        event: String /* "stream" */,
        stream: ClientHttp2Stream,
        headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */,
        flags: Number
    ): Boolean

    override fun emit(event: Any, vararg args: Any): Boolean
    fun on(
        event: String /* "altsvc" */,
        listener: (alt: String, origin: String, stream: Number) -> Unit
    ): ClientHttp2Session /* this */

    fun on(event: String /* "origin" */, listener: (origins: Array<String>) -> Unit): ClientHttp2Session /* this */
    fun on(
        event: String /* "connect" */,
        listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ClientHttp2Session /* this */

    fun on(
        event: String /* "stream" */,
        listener: (stream: ClientHttp2Stream, headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Session /* this */

    override fun on(event: String, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    fun once(
        event: String /* "altsvc" */,
        listener: (alt: String, origin: String, stream: Number) -> Unit
    ): ClientHttp2Session /* this */

    fun once(event: String /* "origin" */, listener: (origins: Array<String>) -> Unit): ClientHttp2Session /* this */
    fun once(
        event: String /* "connect" */,
        listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ClientHttp2Session /* this */

    fun once(
        event: String /* "stream" */,
        listener: (stream: ClientHttp2Stream, headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Session /* this */

    override fun once(event: String, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    fun prependListener(
        event: String /* "altsvc" */,
        listener: (alt: String, origin: String, stream: Number) -> Unit
    ): ClientHttp2Session /* this */

    fun prependListener(
        event: String /* "origin" */,
        listener: (origins: Array<String>) -> Unit
    ): ClientHttp2Session /* this */

    fun prependListener(
        event: String /* "connect" */,
        listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ClientHttp2Session /* this */

    fun prependListener(
        event: String /* "stream" */,
        listener: (stream: ClientHttp2Stream, headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Session /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    fun prependOnceListener(
        event: String /* "altsvc" */,
        listener: (alt: String, origin: String, stream: Number) -> Unit
    ): ClientHttp2Session /* this */

    fun prependOnceListener(
        event: String /* "origin" */,
        listener: (origins: Array<String>) -> Unit
    ): ClientHttp2Session /* this */

    fun prependOnceListener(
        event: String /* "connect" */,
        listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ClientHttp2Session /* this */

    fun prependOnceListener(
        event: String /* "stream" */,
        listener: (stream: ClientHttp2Stream, headers: IncomingHttpHeaders /* IncomingHttpHeaders & IncomingHttpStatusHeader */, flags: Number) -> Unit
    ): ClientHttp2Session /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): ClientHttp2Session /* this */
}

external interface AlternativeServiceOptions {
    var origin: dynamic /* Number | String | url.URL */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$65` {
    var origin: String
}

external interface ServerHttp2Session : Http2Session {
    var server: dynamic /* Http2Server | Http2SecureServer */
        get() = definedExternally
        set(value) = definedExternally

    fun altsvc(alt: String, originOrStream: Number)
    fun altsvc(alt: String, originOrStream: String)
    fun altsvc(alt: String, originOrStream: URL)
    fun altsvc(alt: String, originOrStream: AlternativeServiceOptions)
    fun origin(vararg args: dynamic /* String | url.URL | `T$65` */)
    fun addListener(
        event: String /* "connect" */,
        listener: (session: ServerHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ServerHttp2Session /* this */

    fun addListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): ServerHttp2Session /* this */

    override fun addListener(event: String, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    fun emit(event: String /* "connect" */, session: ServerHttp2Session, socket: Socket): Boolean
    fun emit(event: String /* "connect" */, session: ServerHttp2Session, socket: TLSSocket): Boolean
    fun emit(
        event: String /* "stream" */,
        stream: ServerHttp2Stream,
        headers: IncomingHttpHeaders,
        flags: Number
    ): Boolean

    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    fun on(
        event: String /* "connect" */,
        listener: (session: ServerHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ServerHttp2Session /* this */

    fun on(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): ServerHttp2Session /* this */

    override fun on(event: String, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    fun once(
        event: String /* "connect" */,
        listener: (session: ServerHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ServerHttp2Session /* this */

    fun once(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): ServerHttp2Session /* this */

    override fun once(event: String, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    fun prependListener(
        event: String /* "connect" */,
        listener: (session: ServerHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ServerHttp2Session /* this */

    fun prependListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): ServerHttp2Session /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    fun prependOnceListener(
        event: String /* "connect" */,
        listener: (session: ServerHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
    ): ServerHttp2Session /* this */

    fun prependOnceListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): ServerHttp2Session /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): ServerHttp2Session /* this */
}

external interface SessionOptions {
    var maxDeflateDynamicTableSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxSessionMemory: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxHeaderListPairs: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxOutstandingPings: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxSendHeaderBlockLength: Number?
        get() = definedExternally
        set(value) = definedExternally
    var paddingStrategy: Number?
        get() = definedExternally
        set(value) = definedExternally
    var peerMaxConcurrentStreams: Number?
        get() = definedExternally
        set(value) = definedExternally
    var settings: Settings?
        get() = definedExternally
        set(value) = definedExternally
    val selectPadding: ((frameLen: Number, maxFrameLen: Number) -> Number)?
        get() = definedExternally
    val createConnection: ((authority: URL, option: SessionOptions) -> Duplex)?
        get() = definedExternally
}

external interface ClientSessionOptions : SessionOptions {
    var maxReservedRemoteStreams: Number?
        get() = definedExternally
        set(value) = definedExternally
    override var createConnection: ((authority: URL, option: SessionOptions) -> Duplex)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ServerSessionOptions : SessionOptions {
    var Http1IncomingMessage: Any?
        get() = definedExternally
        set(value) = definedExternally
    var Http1ServerResponse: Any?
        get() = definedExternally
        set(value) = definedExternally
    var Http2ServerRequest: Any?
        get() = definedExternally
        set(value) = definedExternally
    var Http2ServerResponse: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SecureClientSessionOptions : ClientSessionOptions, ConnectionOptions

external interface SecureServerSessionOptions : ServerSessionOptions, TlsOptions

external interface ServerOptions : ServerSessionOptions

external interface SecureServerOptions : SecureServerSessionOptions {
    var allowHTTP1: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var origins: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Http2Server : Server {
    fun addListener(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2Server /* this */

    fun addListener(
        event: String /* "session" */,
        listener: (session: ServerHttp2Session) -> Unit
    ): Http2Server /* this */

    override fun addListener(event: String /* "sessionError" */, listener: (err: Error) -> Unit): Http2Server /* this */
    fun addListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Server /* this */

    override fun addListener(event: String /* "timeout" */, listener: () -> Unit): Http2Server /* this */
    override fun addListener(event: String, listener: (args: Any) -> Unit): Http2Server /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): Http2Server /* this */
    fun emit(
        event: String /* "checkContinue" | "request" */,
        request: Http2ServerRequest,
        response: Http2ServerResponse
    ): Boolean

    fun emit(event: String /* "session" */, session: ServerHttp2Session): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: String /* "sessionError" */, err: Error): Boolean
    fun emit(
        event: String /* "stream" */,
        stream: ServerHttp2Stream,
        headers: IncomingHttpHeaders,
        flags: Number
    ): Boolean

    override fun emit(event: String /* "timeout" */): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    fun on(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2Server /* this */

    fun on(event: String /* "session" */, listener: (session: ServerHttp2Session) -> Unit): Http2Server /* this */
    override fun on(event: String /* "sessionError" */, listener: (err: Error) -> Unit): Http2Server /* this */
    fun on(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Server /* this */

    override fun on(event: String /* "timeout" */, listener: () -> Unit): Http2Server /* this */
    override fun on(event: String, listener: (args: Any) -> Unit): Http2Server /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): Http2Server /* this */
    fun once(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2Server /* this */

    fun once(event: String /* "session" */, listener: (session: ServerHttp2Session) -> Unit): Http2Server /* this */
    override fun once(event: String /* "sessionError" */, listener: (err: Error) -> Unit): Http2Server /* this */
    fun once(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Server /* this */

    override fun once(event: String /* "timeout" */, listener: () -> Unit): Http2Server /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): Http2Server /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): Http2Server /* this */
    fun prependListener(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2Server /* this */

    fun prependListener(
        event: String /* "session" */,
        listener: (session: ServerHttp2Session) -> Unit
    ): Http2Server /* this */

    override fun prependListener(
        event: String /* "sessionError" */,
        listener: (err: Error) -> Unit
    ): Http2Server /* this */

    fun prependListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Server /* this */

    override fun prependListener(event: String /* "timeout" */, listener: () -> Unit): Http2Server /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): Http2Server /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): Http2Server /* this */
    fun prependOnceListener(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2Server /* this */

    fun prependOnceListener(
        event: String /* "session" */,
        listener: (session: ServerHttp2Session) -> Unit
    ): Http2Server /* this */

    override fun prependOnceListener(
        event: String /* "sessionError" */,
        listener: (err: Error) -> Unit
    ): Http2Server /* this */

    fun prependOnceListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2Server /* this */

    override fun prependOnceListener(event: String /* "timeout" */, listener: () -> Unit): Http2Server /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Http2Server /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): Http2Server /* this */
    fun setTimeout(msec: Number = definedExternally, callback: () -> Unit = definedExternally): Http2Server /* this */
}

external interface Http2SecureServer : pl.treksoft.kvision.electron.tls.Server {
    fun addListener(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2SecureServer /* this */

    fun addListener(
        event: String /* "session" */,
        listener: (session: ServerHttp2Session) -> Unit
    ): Http2SecureServer /* this */

    override fun addListener(
        event: String /* "sessionError" */,
        listener: (err: Error) -> Unit
    ): Http2SecureServer /* this */

    fun addListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2SecureServer /* this */

    override fun addListener(event: String /* "timeout" */, listener: () -> Unit): Http2SecureServer /* this */
    override fun addListener(
        event: String /* "unknownProtocol" */,
        listener: (socket: TLSSocket) -> Unit
    ): Http2SecureServer /* this */

    override fun addListener(event: String, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    fun emit(
        event: String /* "checkContinue" | "request" */,
        request: Http2ServerRequest,
        response: Http2ServerResponse
    ): Boolean

    fun emit(event: String /* "session" */, session: ServerHttp2Session): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: String /* "sessionError" */, err: Error): Boolean
    fun emit(
        event: String /* "stream" */,
        stream: ServerHttp2Stream,
        headers: IncomingHttpHeaders,
        flags: Number
    ): Boolean

    override fun emit(event: String /* "timeout" */): Boolean
    override fun emit(event: String /* "unknownProtocol" */, socket: TLSSocket): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    fun on(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2SecureServer /* this */

    fun on(event: String /* "session" */, listener: (session: ServerHttp2Session) -> Unit): Http2SecureServer /* this */
    override fun on(event: String /* "sessionError" */, listener: (err: Error) -> Unit): Http2SecureServer /* this */
    fun on(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2SecureServer /* this */

    override fun on(event: String /* "timeout" */, listener: () -> Unit): Http2SecureServer /* this */
    override fun on(
        event: String /* "unknownProtocol" */,
        listener: (socket: TLSSocket) -> Unit
    ): Http2SecureServer /* this */

    override fun on(event: String, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    fun once(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2SecureServer /* this */

    fun once(
        event: String /* "session" */,
        listener: (session: ServerHttp2Session) -> Unit
    ): Http2SecureServer /* this */

    override fun once(event: String /* "sessionError" */, listener: (err: Error) -> Unit): Http2SecureServer /* this */
    fun once(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2SecureServer /* this */

    override fun once(event: String /* "timeout" */, listener: () -> Unit): Http2SecureServer /* this */
    override fun once(
        event: String /* "unknownProtocol" */,
        listener: (socket: TLSSocket) -> Unit
    ): Http2SecureServer /* this */

    override fun once(event: String, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    fun prependListener(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2SecureServer /* this */

    fun prependListener(
        event: String /* "session" */,
        listener: (session: ServerHttp2Session) -> Unit
    ): Http2SecureServer /* this */

    override fun prependListener(
        event: String /* "sessionError" */,
        listener: (err: Error) -> Unit
    ): Http2SecureServer /* this */

    fun prependListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2SecureServer /* this */

    override fun prependListener(event: String /* "timeout" */, listener: () -> Unit): Http2SecureServer /* this */
    override fun prependListener(
        event: String /* "unknownProtocol" */,
        listener: (socket: TLSSocket) -> Unit
    ): Http2SecureServer /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    fun prependOnceListener(
        event: String /* "checkContinue" | "request" */,
        listener: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit
    ): Http2SecureServer /* this */

    fun prependOnceListener(
        event: String /* "session" */,
        listener: (session: ServerHttp2Session) -> Unit
    ): Http2SecureServer /* this */

    override fun prependOnceListener(
        event: String /* "sessionError" */,
        listener: (err: Error) -> Unit
    ): Http2SecureServer /* this */

    fun prependOnceListener(
        event: String /* "stream" */,
        listener: (stream: ServerHttp2Stream, headers: IncomingHttpHeaders, flags: Number) -> Unit
    ): Http2SecureServer /* this */

    override fun prependOnceListener(event: String /* "timeout" */, listener: () -> Unit): Http2SecureServer /* this */
    override fun prependOnceListener(
        event: String /* "unknownProtocol" */,
        listener: (socket: TLSSocket) -> Unit
    ): Http2SecureServer /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): Http2SecureServer /* this */
    fun setTimeout(
        msec: Number = definedExternally,
        callback: () -> Unit = definedExternally
    ): Http2SecureServer /* this */
}

external open class Http2ServerRequest(
    stream: ServerHttp2Stream,
    headers: IncomingHttpHeaders,
    options: ReadableOptions,
    rawHeaders: Array<String>
) : Readable {
    open var aborted: Boolean
    open var authority: String
    open var headers: IncomingHttpHeaders
    open var httpVersion: String
    open var method: String
    open var rawHeaders: Array<String>
    open var rawTrailers: Array<String>
    open var scheme: String
    open var socket: dynamic /* net.Socket | tls.TLSSocket */
    open var stream: ServerHttp2Stream
    open var trailers: IncomingHttpHeaders
    open var url: String
    open fun setTimeout(msecs: Number, callback: () -> Unit = definedExternally)
    override fun read(size: Number): dynamic /* Buffer? | String? */
    open fun addListener(
        event: String /* "aborted" */,
        listener: (hadError: Boolean, code: Number) -> Unit
    ): Http2ServerRequest /* this */

    override fun addListener(
        event: String /* "close" | "end" | "readable" */,
        listener: () -> Unit
    ): Http2ServerRequest /* this */

    override fun addListener(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2ServerRequest /* this */

    override fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Http2ServerRequest /* this */
    override fun addListener(event: String, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    open fun emit(event: String /* "aborted" */, hadError: Boolean, code: Number): Boolean
    override fun emit(event: String /* "close" | "end" | "readable" */): Boolean
    open fun emit(event: String /* "data" */, chunk: Buffer): Boolean
    override fun emit(event: String /* "data" */, chunk: Any): Boolean
    open fun emit(event: String /* "data" */, chunk: String): Boolean
    override fun emit(event: String /* "error" */, err: Error): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun on(
        event: String /* "aborted" */,
        listener: (hadError: Boolean, code: Number) -> Unit
    ): Http2ServerRequest /* this */

    override fun on(
        event: String /* "close" | "end" | "readable" */,
        listener: () -> Unit
    ): Http2ServerRequest /* this */

    override fun on(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2ServerRequest /* this */

    override fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Http2ServerRequest /* this */
    override fun on(event: String, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    open fun once(
        event: String /* "aborted" */,
        listener: (hadError: Boolean, code: Number) -> Unit
    ): Http2ServerRequest /* this */

    override fun once(
        event: String /* "close" | "end" | "readable" */,
        listener: () -> Unit
    ): Http2ServerRequest /* this */

    override fun once(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2ServerRequest /* this */

    override fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Http2ServerRequest /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    open fun prependListener(
        event: String /* "aborted" */,
        listener: (hadError: Boolean, code: Number) -> Unit
    ): Http2ServerRequest /* this */

    override fun prependListener(
        event: String /* "close" | "end" | "readable" */,
        listener: () -> Unit
    ): Http2ServerRequest /* this */

    override fun prependListener(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2ServerRequest /* this */

    override fun prependListener(
        event: String /* "error" */,
        listener: (err: Error) -> Unit
    ): Http2ServerRequest /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    open fun prependOnceListener(
        event: String /* "aborted" */,
        listener: (hadError: Boolean, code: Number) -> Unit
    ): Http2ServerRequest /* this */

    override fun prependOnceListener(
        event: String /* "close" | "end" | "readable" */,
        listener: () -> Unit
    ): Http2ServerRequest /* this */

    override fun prependOnceListener(
        event: String /* "data" */,
        listener: (chunk: dynamic /* Buffer | String */) -> Unit
    ): Http2ServerRequest /* this */

    override fun prependOnceListener(
        event: String /* "error" */,
        listener: (err: Error) -> Unit
    ): Http2ServerRequest /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): Http2ServerRequest /* this */
}

external open class Http2ServerResponse(stream: ServerHttp2Stream) : Stream {
    open var connection: dynamic /* net.Socket | tls.TLSSocket */
    open var finished: Boolean
    open var headersSent: Boolean
    open var socket: dynamic /* net.Socket | tls.TLSSocket */
    open var stream: ServerHttp2Stream
    open var sendDate: Boolean
    open var statusCode: Number
    open var statusMessage: String /* '' */
    open fun addTrailers(trailers: OutgoingHttpHeaders)
    open fun end(callback: () -> Unit = definedExternally)
    open fun end(data: String, callback: () -> Unit = definedExternally)
    open fun end(data: Uint8Array, callback: () -> Unit = definedExternally)
    open fun end(data: String, encoding: String, callback: () -> Unit = definedExternally)
    open fun end(data: Uint8Array, encoding: String, callback: () -> Unit = definedExternally)
    open fun getHeader(name: String): String
    open fun getHeaderNames(): Array<String>
    open fun getHeaders(): OutgoingHttpHeaders
    open fun hasHeader(name: String): Boolean
    open fun removeHeader(name: String)
    open fun setHeader(name: String, value: Number)
    open fun setHeader(name: String, value: String)
    open fun setHeader(name: String, value: Array<String>)
    open fun setTimeout(msecs: Number, callback: () -> Unit = definedExternally)
    open fun write(chunk: String, callback: (err: Error) -> Unit = definedExternally): Boolean
    open fun write(chunk: Uint8Array, callback: (err: Error) -> Unit = definedExternally): Boolean
    open fun write(chunk: String, encoding: String, callback: (err: Error) -> Unit = definedExternally): Boolean
    open fun write(chunk: Uint8Array, encoding: String, callback: (err: Error) -> Unit = definedExternally): Boolean
    open fun writeContinue()
    open fun writeHead(
        statusCode: Number,
        headers: OutgoingHttpHeaders = definedExternally
    ): Http2ServerResponse /* this */

    open fun writeHead(
        statusCode: Number,
        statusMessage: String,
        headers: OutgoingHttpHeaders = definedExternally
    ): Http2ServerResponse /* this */

    open fun createPushResponse(headers: OutgoingHttpHeaders, callback: (err: Error?, res: Http2ServerResponse) -> Unit)
    open fun addListener(
        event: String /* "close" | "drain" | "finish" */,
        listener: () -> Unit
    ): Http2ServerResponse /* this */

    open fun addListener(event: String /* "error" */, listener: (error: Error) -> Unit): Http2ServerResponse /* this */
    open fun addListener(
        event: String /* "pipe" | "unpipe" */,
        listener: (src: Readable) -> Unit
    ): Http2ServerResponse /* this */

    override fun addListener(event: String, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    open fun emit(event: String /* "close" | "drain" | "finish" */): Boolean
    open fun emit(event: String /* "error" */, error: Error): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    open fun emit(event: String /* "pipe" | "unpipe" */, src: Readable): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun on(event: String /* "close" | "drain" | "finish" */, listener: () -> Unit): Http2ServerResponse /* this */
    open fun on(event: String /* "error" */, listener: (error: Error) -> Unit): Http2ServerResponse /* this */
    open fun on(
        event: String /* "pipe" | "unpipe" */,
        listener: (src: Readable) -> Unit
    ): Http2ServerResponse /* this */

    override fun on(event: String, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    open fun once(
        event: String /* "close" | "drain" | "finish" */,
        listener: () -> Unit
    ): Http2ServerResponse /* this */

    open fun once(event: String /* "error" */, listener: (error: Error) -> Unit): Http2ServerResponse /* this */
    open fun once(
        event: String /* "pipe" | "unpipe" */,
        listener: (src: Readable) -> Unit
    ): Http2ServerResponse /* this */

    override fun once(event: String, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    open fun prependListener(
        event: String /* "close" | "drain" | "finish" */,
        listener: () -> Unit
    ): Http2ServerResponse /* this */

    open fun prependListener(
        event: String /* "error" */,
        listener: (error: Error) -> Unit
    ): Http2ServerResponse /* this */

    open fun prependListener(
        event: String /* "pipe" | "unpipe" */,
        listener: (src: Readable) -> Unit
    ): Http2ServerResponse /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    open fun prependOnceListener(
        event: String /* "close" | "drain" | "finish" */,
        listener: () -> Unit
    ): Http2ServerResponse /* this */

    open fun prependOnceListener(
        event: String /* "error" */,
        listener: (error: Error) -> Unit
    ): Http2ServerResponse /* this */

    open fun prependOnceListener(
        event: String /* "pipe" | "unpipe" */,
        listener: (src: Readable) -> Unit
    ): Http2ServerResponse /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): Http2ServerResponse /* this */
}

external fun getDefaultSettings(): Settings

external fun getPackedSettings(settings: Settings): Buffer

external fun getUnpackedSettings(buf: Uint8Array): Settings

external fun createServer(onRequestHandler: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit = definedExternally): Http2Server

external fun createServer(
    options: ServerOptions,
    onRequestHandler: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit = definedExternally
): Http2Server

external fun createSecureServer(onRequestHandler: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit = definedExternally): Http2SecureServer

external fun createSecureServer(
    options: SecureServerOptions,
    onRequestHandler: (request: Http2ServerRequest, response: Http2ServerResponse) -> Unit = definedExternally
): Http2SecureServer

external fun connect(
    authority: String,
    listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
): ClientHttp2Session

external fun connect(
    authority: URL,
    listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit
): ClientHttp2Session

external fun connect(
    authority: String,
    options: ClientSessionOptions = definedExternally,
    listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit = definedExternally
): ClientHttp2Session

external fun connect(
    authority: String,
    options: SecureClientSessionOptions = definedExternally,
    listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit = definedExternally
): ClientHttp2Session

external fun connect(
    authority: URL,
    options: ClientSessionOptions = definedExternally,
    listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit = definedExternally
): ClientHttp2Session

external fun connect(
    authority: URL,
    options: SecureClientSessionOptions = definedExternally,
    listener: (session: ClientHttp2Session, socket: dynamic /* net.Socket | tls.TLSSocket */) -> Unit = definedExternally
): ClientHttp2Session