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

@file:JsModule("dgram")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.dgram

import node.buffer.Buffer
import org.khronos.webgl.Uint8Array
import pl.treksoft.kvision.electron.dns.LookupOneOptions
import pl.treksoft.kvision.electron.events.internal.EventEmitter
import pl.treksoft.kvision.electron.net.AddressInfo

external interface RemoteInfo {
    var address: String
    var family: String /* 'IPv4' | 'IPv6' */
    var port: Number
    var size: Number
}

external interface BindOptions {
    var port: Number?
        get() = definedExternally
        set(value) = definedExternally
    var address: String?
        get() = definedExternally
        set(value) = definedExternally
    var exclusive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var fd: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SocketOptions {
    var type: String /* "udp4" | "udp6" */
    var reuseAddr: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var ipv6Only: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var recvBufferSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var sendBufferSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var lookup: ((hostname: String, options: LookupOneOptions, callback: (err: Exception?, address: String, family: Number) -> Unit) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external fun createSocket(
    type: String /* "udp4" */,
    callback: (msg: Buffer, rinfo: RemoteInfo) -> Unit = definedExternally
): Socket

external fun createSocket(
    type: String /* "udp6" */,
    callback: (msg: Buffer, rinfo: RemoteInfo) -> Unit = definedExternally
): Socket

external fun createSocket(
    options: SocketOptions,
    callback: (msg: Buffer, rinfo: RemoteInfo) -> Unit = definedExternally
): Socket

external open class Socket : EventEmitter {
    open fun addMembership(multicastAddress: String, multicastInterface: String = definedExternally)
    open fun address(): AddressInfo
    open fun bind(
        port: Number = definedExternally,
        address: String = definedExternally,
        callback: () -> Unit = definedExternally
    )

    open fun bind(port: Number = definedExternally, callback: () -> Unit = definedExternally)
    open fun bind(callback: () -> Unit = definedExternally)
    open fun bind(options: BindOptions, callback: () -> Unit = definedExternally)
    open fun close(callback: () -> Unit = definedExternally)
    open fun connect(port: Number, address: String = definedExternally, callback: () -> Unit = definedExternally)
    open fun connect(port: Number, callback: () -> Unit)
    open fun disconnect()
    open fun dropMembership(multicastAddress: String, multicastInterface: String = definedExternally)
    open fun getRecvBufferSize(): Number
    open fun getSendBufferSize(): Number
    open fun ref(): Socket /* this */
    open fun remoteAddress(): AddressInfo
    open fun send(
        msg: String,
        port: Number = definedExternally,
        address: String = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: Uint8Array,
        port: Number = definedExternally,
        address: String = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: Array<Any>,
        port: Number = definedExternally,
        address: String = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: String,
        port: Number = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: Uint8Array,
        port: Number = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: Array<Any>,
        port: Number = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(msg: String, callback: (error: Error?, bytes: Number) -> Unit = definedExternally)
    open fun send(msg: Uint8Array, callback: (error: Error?, bytes: Number) -> Unit = definedExternally)
    open fun send(msg: Array<Any>, callback: (error: Error?, bytes: Number) -> Unit = definedExternally)
    open fun send(
        msg: String,
        offset: Number,
        length: Number,
        port: Number = definedExternally,
        address: String = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: Uint8Array,
        offset: Number,
        length: Number,
        port: Number = definedExternally,
        address: String = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: String,
        offset: Number,
        length: Number,
        port: Number = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: Uint8Array,
        offset: Number,
        length: Number,
        port: Number = definedExternally,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: String,
        offset: Number,
        length: Number,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun send(
        msg: Uint8Array,
        offset: Number,
        length: Number,
        callback: (error: Error?, bytes: Number) -> Unit = definedExternally
    )

    open fun setBroadcast(flag: Boolean)
    open fun setMulticastInterface(multicastInterface: String)
    open fun setMulticastLoopback(flag: Boolean)
    open fun setMulticastTTL(ttl: Number)
    open fun setRecvBufferSize(size: Number)
    open fun setSendBufferSize(size: Number)
    open fun setTTL(ttl: Number)
    open fun unref(): Socket /* this */
    override fun addListener(event: String, listener: (args: Any) -> Unit): Socket /* this */
    open fun addListener(event: String /* "close" | "connect" | "listening" */, listener: () -> Unit): Socket /* this */
    open fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    open fun addListener(
        event: String /* "message" */,
        listener: (msg: Buffer, rinfo: RemoteInfo) -> Unit
    ): Socket /* this */

    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun emit(event: String /* "close" | "connect" | "listening" */): Boolean
    open fun emit(event: String /* "error" */, err: Error): Boolean
    open fun emit(event: String /* "message" */, msg: Buffer, rinfo: RemoteInfo): Boolean
    override fun on(event: String, listener: (args: Any) -> Unit): Socket /* this */
    open fun on(event: String /* "close" | "connect" | "listening" */, listener: () -> Unit): Socket /* this */
    open fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    open fun on(event: String /* "message" */, listener: (msg: Buffer, rinfo: RemoteInfo) -> Unit): Socket /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): Socket /* this */
    open fun once(event: String /* "close" | "connect" | "listening" */, listener: () -> Unit): Socket /* this */
    open fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    open fun once(event: String /* "message" */, listener: (msg: Buffer, rinfo: RemoteInfo) -> Unit): Socket /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): Socket /* this */
    open fun prependListener(
        event: String /* "close" | "connect" | "listening" */,
        listener: () -> Unit
    ): Socket /* this */

    open fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    open fun prependListener(
        event: String /* "message" */,
        listener: (msg: Buffer, rinfo: RemoteInfo) -> Unit
    ): Socket /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Socket /* this */
    open fun prependOnceListener(
        event: String /* "close" | "connect" | "listening" */,
        listener: () -> Unit
    ): Socket /* this */

    open fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    open fun prependOnceListener(
        event: String /* "message" */,
        listener: (msg: Buffer, rinfo: RemoteInfo) -> Unit
    ): Socket /* this */
}