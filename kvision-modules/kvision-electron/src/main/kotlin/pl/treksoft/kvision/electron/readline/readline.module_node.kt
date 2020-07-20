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

@file:JsModule("readline")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.readline

import node.buffer.Buffer
import pl.treksoft.kvision.electron.NodeJS.ReadableStream
import pl.treksoft.kvision.electron.NodeJS.WritableStream
import pl.treksoft.kvision.electron.events.internal.EventEmitter

external interface Key {
    var sequence: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var ctrl: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var meta: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var shift: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external open class Interface : EventEmitter {
    open var terminal: Boolean
    open var line: String
    open var cursor: Number

    constructor(
        input: ReadableStream,
        output: WritableStream = definedExternally,
        completer: Completer = definedExternally,
        terminal: Boolean = definedExternally
    )

    constructor(
        input: ReadableStream,
        output: WritableStream = definedExternally,
        completer: AsyncCompleter = definedExternally,
        terminal: Boolean = definedExternally
    )

    constructor(options: ReadLineOptions)

    open fun setPrompt(prompt: String)
    open fun prompt(preserveCursor: Boolean = definedExternally)
    open fun question(query: String, callback: (answer: String) -> Unit)
    open fun pause(): Interface /* this */
    open fun resume(): Interface /* this */
    open fun close()
    open fun write(data: String, key: Key = definedExternally)
    open fun write(data: Buffer, key: Key = definedExternally)
    override fun addListener(event: String, listener: (args: Any) -> Unit): Interface /* this */
    open fun addListener(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" */,
        listener: () -> Unit
    ): Interface /* this */

    open fun addListener(event: String /* "line" */, listener: (input: String) -> Unit): Interface /* this */
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun emit(event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" */): Boolean
    open fun emit(event: String /* "line" */, input: String): Boolean
    override fun on(event: String, listener: (args: Any) -> Unit): Interface /* this */
    open fun on(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" */,
        listener: () -> Unit
    ): Interface /* this */

    open fun on(event: String /* "line" */, listener: (input: String) -> Unit): Interface /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): Interface /* this */
    open fun once(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" */,
        listener: () -> Unit
    ): Interface /* this */

    open fun once(event: String /* "line" */, listener: (input: String) -> Unit): Interface /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): Interface /* this */
    open fun prependListener(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" */,
        listener: () -> Unit
    ): Interface /* this */

    open fun prependListener(event: String /* "line" */, listener: (input: String) -> Unit): Interface /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Interface /* this */
    open fun prependOnceListener(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" */,
        listener: () -> Unit
    ): Interface /* this */

    open fun prependOnceListener(event: String /* "line" */, listener: (input: String) -> Unit): Interface /* this */
}

external interface ReadLineOptions {
    var input: ReadableStream
    var output: WritableStream?
        get() = definedExternally
        set(value) = definedExternally
    var completer: dynamic /* Completer? | AsyncCompleter? */
        get() = definedExternally
        set(value) = definedExternally
    var terminal: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var historySize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var prompt: String?
        get() = definedExternally
        set(value) = definedExternally
    var crlfDelay: Number?
        get() = definedExternally
        set(value) = definedExternally
    var removeHistoryDuplicates: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var escapeCodeTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external fun createInterface(
    input: ReadableStream,
    output: WritableStream = definedExternally,
    completer: Completer = definedExternally,
    terminal: Boolean = definedExternally
): Interface

external fun createInterface(
    input: ReadableStream,
    output: WritableStream = definedExternally,
    completer: AsyncCompleter = definedExternally,
    terminal: Boolean = definedExternally
): Interface

external fun createInterface(options: ReadLineOptions): Interface

external fun emitKeypressEvents(stream: ReadableStream, readlineInterface: Interface = definedExternally)

external fun clearLine(stream: WritableStream, dir: String /* -1 */, callback: () -> Unit = definedExternally): Boolean

external fun clearLine(stream: WritableStream, dir: Number /* 0 */, callback: () -> Unit = definedExternally): Boolean

external fun clearLine(stream: WritableStream, dir: Number /* 1 */, callback: () -> Unit = definedExternally): Boolean

external fun clearScreenDown(stream: WritableStream, callback: () -> Unit = definedExternally): Boolean

external fun cursorTo(
    stream: WritableStream,
    x: Number,
    y: Number = definedExternally,
    callback: () -> Unit = definedExternally
): Boolean

external fun moveCursor(
    stream: WritableStream,
    dx: Number,
    dy: Number,
    callback: () -> Unit = definedExternally
): Boolean