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

@file:JsModule("vm")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.vm

import es5.Object
import node.buffer.Buffer

external interface Context {
    @nativeGetter
    operator fun get(key: String): Any?

    @nativeSetter
    operator fun set(key: String, value: Any)
}

external interface BaseOptions {
    var filename: String?
        get() = definedExternally
        set(value) = definedExternally
    var lineOffset: Number?
        get() = definedExternally
        set(value) = definedExternally
    var columnOffset: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ScriptOptions : BaseOptions {
    var displayErrors: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var cachedData: Buffer?
        get() = definedExternally
        set(value) = definedExternally
    var produceCachedData: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RunningScriptOptions : BaseOptions {
    var displayErrors: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var breakOnSigint: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CompileFunctionOptions : BaseOptions {
    var cachedData: Buffer?
        get() = definedExternally
        set(value) = definedExternally
    var produceCachedData: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var parsingContext: Context?
        get() = definedExternally
        set(value) = definedExternally
    var contextExtensions: Array<Object>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$78` {
    var strings: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var wasm: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CreateContextOptions {
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var origin: String?
        get() = definedExternally
        set(value) = definedExternally
    var codeGeneration: `T$78`?
        get() = definedExternally
        set(value) = definedExternally
}

external open class Script(code: String, options: ScriptOptions = definedExternally) {
    open fun runInContext(contextifiedSandbox: Context, options: RunningScriptOptions = definedExternally): Any
    open fun runInNewContext(
        sandbox: Context = definedExternally,
        options: RunningScriptOptions = definedExternally
    ): Any

    open fun runInThisContext(options: RunningScriptOptions = definedExternally): Any
    open fun createCachedData(): Buffer
}

external fun createContext(
    sandbox: Context = definedExternally,
    options: CreateContextOptions = definedExternally
): Context

external fun isContext(sandbox: Context): Boolean

external fun runInContext(
    code: String,
    contextifiedSandbox: Context,
    options: RunningScriptOptions = definedExternally
): Any

external fun runInContext(code: String, contextifiedSandbox: Context, options: String = definedExternally): Any

external fun runInNewContext(
    code: String,
    sandbox: Context = definedExternally,
    options: RunningScriptOptions = definedExternally
): Any

external fun runInNewContext(
    code: String,
    sandbox: Context = definedExternally,
    options: String = definedExternally
): Any

external fun runInThisContext(code: String, options: RunningScriptOptions = definedExternally): Any

external fun runInThisContext(code: String, options: String = definedExternally): Any

external fun compileFunction(
    code: String,
    params: Array<String> = definedExternally,
    options: CompileFunctionOptions = definedExternally
): Function<*>