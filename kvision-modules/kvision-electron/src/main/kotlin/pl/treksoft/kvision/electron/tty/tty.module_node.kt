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

@file:JsModule("tty")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.tty

import pl.treksoft.kvision.electron.net.Socket
import pl.treksoft.kvision.electron.net.SocketConstructorOpts

external fun isatty(fd: Number): Boolean

external open class ReadStream(fd: Number, options: SocketConstructorOpts = definedExternally) : Socket {
    open var isRaw: Boolean
    open fun setRawMode(mode: Boolean): ReadStream /* this */
    open var isTTY: Boolean
}

external open class WriteStream(fd: Number) : Socket {
    override fun addListener(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    override fun addListener(event: String /* "resize" */, listener: () -> Unit): WriteStream /* this */
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    override fun emit(event: String /* "resize" */): Boolean
    override fun on(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    override fun on(event: String /* "resize" */, listener: () -> Unit): WriteStream /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    override fun once(event: String /* "resize" */, listener: () -> Unit): WriteStream /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    override fun prependListener(event: String /* "resize" */, listener: () -> Unit): WriteStream /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    override fun prependOnceListener(event: String /* "resize" */, listener: () -> Unit): WriteStream /* this */
    open fun clearLine(dir: String /* -1 */, callback: () -> Unit = definedExternally): Boolean
    open fun clearLine(dir: Number /* 0 | 1 */, callback: () -> Unit = definedExternally): Boolean
    open fun clearScreenDown(callback: () -> Unit = definedExternally): Boolean
    open fun cursorTo(x: Number, y: Number = definedExternally, callback: () -> Unit = definedExternally): Boolean
    open fun cursorTo(x: Number, callback: () -> Unit): Boolean
    open fun moveCursor(dx: Number, dy: Number, callback: () -> Unit = definedExternally): Boolean
    open fun getColorDepth(env: Any = definedExternally): Number
    open fun hasColors(depth: Number = definedExternally): Boolean
    open fun hasColors(env: Any = definedExternally): Boolean
    open fun hasColors(depth: Number, env: Any = definedExternally): Boolean
    open fun getWindowSize(): dynamic /* JsTuple<Number, Number> */
    open var columns: Number
    open var rows: Number
    open var isTTY: Boolean
}