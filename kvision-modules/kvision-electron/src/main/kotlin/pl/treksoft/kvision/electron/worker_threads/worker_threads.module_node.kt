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

@file:JsModule("worker_threads")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.worker_threads

import pl.treksoft.kvision.electron.events.internal.EventEmitter
import pl.treksoft.kvision.electron.stream.internal.Readable
import pl.treksoft.kvision.electron.stream.internal.Writable
import pl.treksoft.kvision.electron.vm.Context
import kotlin.js.Promise

external var isMainThread: Boolean

external var parentPort: MessagePort?

external var SHARE_ENV: Any

external var threadId: Number

external var workerData: Any

external open class MessageChannel {
    open var port1: MessagePort
    open var port2: MessagePort
}

external open class MessagePort : EventEmitter {
    open fun close()
    open fun postMessage(value: Any, transferList: Array<dynamic /* ArrayBuffer | MessagePort */> = definedExternally)
    open fun ref()
    open fun unref()
    open fun start()
    open fun addListener(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun addListener(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    override fun addListener(event: String, listener: (args: Any) -> Unit): MessagePort /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun emit(event: String /* "close" */): Boolean
    open fun emit(event: String /* "message" */, value: Any): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun on(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun on(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    override fun on(event: String, listener: (args: Any) -> Unit): MessagePort /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun once(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun once(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): MessagePort /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun prependListener(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun prependListener(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): MessagePort /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun prependOnceListener(
        event: String /* "message" */,
        listener: (value: Any) -> Unit
    ): MessagePort /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): MessagePort /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun removeListener(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun removeListener(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    override fun removeListener(event: String, listener: (args: Any) -> Unit): MessagePort /* this */
    override fun removeListener(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun off(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun off(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    override fun off(event: String, listener: (args: Any) -> Unit): MessagePort /* this */
    override fun off(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
}

external interface WorkerOptions {
    var eval: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var env: dynamic /* NodeJS.ProcessEnv? | Any? */
        get() = definedExternally
        set(value) = definedExternally
    var workerData: Any?
        get() = definedExternally
        set(value) = definedExternally
    var stdin: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var stdout: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var stderr: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var execArgv: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external open class Worker(filename: String, options: WorkerOptions = definedExternally) : EventEmitter {
    open var stdin: Writable?
    open var stdout: Readable
    open var stderr: Readable
    open var threadId: Number
    open fun postMessage(value: Any, transferList: Array<dynamic /* ArrayBuffer | MessagePort */> = definedExternally)
    open fun ref()
    open fun unref()
    open fun terminate(): Promise<Number>
    open fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun addListener(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun addListener(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun addListener(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun addListener(event: String, listener: (args: Any) -> Unit): Worker /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun emit(event: String /* "error" */, err: Error): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    open fun emit(event: String /* "exit" */, exitCode: Number): Boolean
    open fun emit(event: String /* "message" */, value: Any): Boolean
    open fun emit(event: String /* "online" */): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun on(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun on(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun on(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun on(event: String, listener: (args: Any) -> Unit): Worker /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun once(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun once(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun once(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): Worker /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun prependListener(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun prependListener(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun prependListener(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): Worker /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun prependOnceListener(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun prependOnceListener(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun prependOnceListener(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Worker /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun removeListener(event: String /* "error" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun removeListener(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun removeListener(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun removeListener(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun removeListener(event: String, listener: (args: Any) -> Unit): Worker /* this */
    override fun removeListener(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun off(event: String /* "error" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun off(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun off(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun off(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun off(event: String, listener: (args: Any) -> Unit): Worker /* this */
    override fun off(event: Any, listener: (args: Any) -> Unit): Worker /* this */
}

external fun moveMessagePortToContext(port: MessagePort, context: Context): MessagePort

external interface `T$81` {
    var message: Any
}

external fun receiveMessageOnPort(port: MessagePort): `T$81`?