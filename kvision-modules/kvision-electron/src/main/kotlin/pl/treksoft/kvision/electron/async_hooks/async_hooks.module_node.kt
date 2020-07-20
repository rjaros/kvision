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

@file:JsModule("async_hooks")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.async_hooks

external fun executionAsyncId(): Number

external fun executionAsyncResource(): Any?

external fun triggerAsyncId(): Number

external interface HookCallbacks {
    val init: ((asyncId: Number, type: String, triggerAsyncId: Number, resource: Any?) -> Unit)?
        get() = definedExternally
    val before: ((asyncId: Number) -> Unit)?
        get() = definedExternally
    val after: ((asyncId: Number) -> Unit)?
        get() = definedExternally
    val promiseResolve: ((asyncId: Number) -> Unit)?
        get() = definedExternally
    val destroy: ((asyncId: Number) -> Unit)?
        get() = definedExternally
}

external interface AsyncHook {
    fun enable(): AsyncHook /* this */
    fun disable(): AsyncHook /* this */
}

external fun createHook(options: HookCallbacks): AsyncHook

external interface AsyncResourceOptions {
    var triggerAsyncId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var requireManualDestroy: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external open class AsyncResource {
    constructor(type: String, triggerAsyncId: Number = definedExternally)
    constructor(type: String, triggerAsyncId: AsyncResourceOptions = definedExternally)

    open fun <This, Result> runInAsyncScope(
        fn: (self: This, args: Any) -> Result,
        thisArg: This = definedExternally,
        vararg args: Any
    ): Result

    open fun emitDestroy()
    open fun asyncId(): Number
    open fun triggerAsyncId(): Number
}

external open class AsyncLocalStorage<T> {
    open fun disable()
    open fun getStore(): T?
    open fun run(store: T, callback: (args: Any) -> Unit, vararg args: Any)
    open fun exit(callback: (args: Any) -> Unit, vararg args: Any)
    open fun <R> runSyncAndReturn(store: T, callback: (args: Any) -> R, vararg args: Any): R
    open fun <R> exitSyncAndReturn(callback: (args: Any) -> R, vararg args: Any): R
    open fun enterWith(store: T)
}