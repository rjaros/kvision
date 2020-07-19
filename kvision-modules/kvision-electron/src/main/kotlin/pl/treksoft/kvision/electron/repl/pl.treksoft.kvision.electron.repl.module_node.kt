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

@file:JsModule("repl")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.repl

import pl.treksoft.kvision.electron.NodeJS.ReadableStream
import pl.treksoft.kvision.electron.NodeJS.WritableStream
import pl.treksoft.kvision.electron.readline.Interface
import pl.treksoft.kvision.electron.util.InspectOptions
import pl.treksoft.kvision.electron.vm.Context

external interface ReplOptions {
    var prompt: String?
        get() = definedExternally
        set(value) = definedExternally
    var input: ReadableStream?
        get() = definedExternally
        set(value) = definedExternally
    var output: WritableStream?
        get() = definedExternally
        set(value) = definedExternally
    var terminal: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var eval: REPLEval?
        get() = definedExternally
        set(value) = definedExternally
    var useColors: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var useGlobal: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var ignoreUndefined: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var writer: REPLWriter?
        get() = definedExternally
        set(value) = definedExternally
    var completer: dynamic /* Completer? | AsyncCompleter? */
        get() = definedExternally
        set(value) = definedExternally
    var replMode: Any?
        get() = definedExternally
        set(value) = definedExternally
    var breakEvalOnSigint: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$76` {
    var options: InspectOptions
}

external var writer: REPLWriter /* REPLWriter & `T$76` */

external interface REPLCommand {
    var help: String?
        get() = definedExternally
        set(value) = definedExternally
    var action: REPLCommandAction
}

external interface `T$77` {
    @nativeGetter
    operator fun get(name: String): REPLCommand?

    @nativeSetter
    operator fun set(name: String, value: REPLCommand?)
}

external open class REPLServer : Interface {
    open var context: Context
    open var inputStream: ReadableStream
    open var outputStream: WritableStream
    open var commands: `T$77`
    open var editorMode: Boolean
    open var underscoreAssigned: Boolean
    open var last: Any
    open var underscoreErrAssigned: Boolean
    open var lastError: Any
    open var eval: REPLEval
    open var useColors: Boolean
    open var useGlobal: Boolean
    open var ignoreUndefined: Boolean
    open var writer: REPLWriter
    open var completer: dynamic /* Completer | AsyncCompleter */
    open var replMode: Any
    open fun defineCommand(keyword: String, cmd: REPLCommandAction)
    open fun defineCommand(keyword: String, cmd: REPLCommand)
    open fun displayPrompt(preserveCursor: Boolean = definedExternally)
    open fun clearBufferedCommand()
    open fun setupHistory(path: String, cb: (err: Error?, repl: REPLServer /* this */) -> Unit)
    override fun addListener(event: String, listener: (args: Any) -> Unit): REPLServer /* this */
    override fun addListener(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" | "exit" */,
        listener: () -> Unit
    ): REPLServer /* this */

    override fun addListener(event: String /* "line" */, listener: (input: String) -> Unit): REPLServer /* this */
    open fun addListener(event: String /* "reset" */, listener: (context: Context) -> Unit): REPLServer /* this */
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    override fun emit(event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" | "exit" */): Boolean
    override fun emit(event: String /* "line" */, input: String): Boolean
    open fun emit(event: String /* "reset" */, context: Context): Boolean
    override fun on(event: String, listener: (args: Any) -> Unit): REPLServer /* this */
    override fun on(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" | "exit" */,
        listener: () -> Unit
    ): REPLServer /* this */

    override fun on(event: String /* "line" */, listener: (input: String) -> Unit): REPLServer /* this */
    open fun on(event: String /* "reset" */, listener: (context: Context) -> Unit): REPLServer /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): REPLServer /* this */
    override fun once(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" | "exit" */,
        listener: () -> Unit
    ): REPLServer /* this */

    override fun once(event: String /* "line" */, listener: (input: String) -> Unit): REPLServer /* this */
    open fun once(event: String /* "reset" */, listener: (context: Context) -> Unit): REPLServer /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): REPLServer /* this */
    override fun prependListener(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" | "exit" */,
        listener: () -> Unit
    ): REPLServer /* this */

    override fun prependListener(event: String /* "line" */, listener: (input: String) -> Unit): REPLServer /* this */
    open fun prependListener(event: String /* "reset" */, listener: (context: Context) -> Unit): REPLServer /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): REPLServer /* this */
    override fun prependOnceListener(
        event: String /* "close" | "pause" | "resume" | "SIGCONT" | "SIGINT" | "SIGTSTP" | "exit" */,
        listener: () -> Unit
    ): REPLServer /* this */

    override fun prependOnceListener(
        event: String /* "line" */,
        listener: (input: String) -> Unit
    ): REPLServer /* this */

    open fun prependOnceListener(
        event: String /* "reset" */,
        listener: (context: Context) -> Unit
    ): REPLServer /* this */
}

external var REPL_MODE_SLOPPY: Any

external var REPL_MODE_STRICT: Any

external fun start(options: String = definedExternally): REPLServer

external fun start(options: ReplOptions = definedExternally): REPLServer

external open class Recoverable(err: Error) {
    open var err: Error
}