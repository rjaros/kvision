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

@file:JsModule("util")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.util

import org.khronos.webgl.*
import kotlin.js.Promise

external interface InspectOptions : pl.treksoft.kvision.electron.NodeJS.InspectOptions

external fun format(format: Any, vararg param: Any): String

external fun formatWithOptions(inspectOptions: InspectOptions, format: String, vararg param: Any): String

external fun log(string: String)

external fun inspect(
    obj: Any,
    showHidden: Boolean = definedExternally,
    depth: Number? = definedExternally,
    color: Boolean = definedExternally
): String

external fun inspect(obj: Any, options: InspectOptions): String

external fun isArray(obj: Any): Boolean

external fun isRegExp(obj: Any): Boolean

external fun isDate(obj: Any): Boolean

external fun isError(obj: Any): Boolean

external fun inherits(constructor: Any, superConstructor: Any)

external fun debuglog(key: String): (msg: String, param: Any) -> Unit

external fun isBoolean(obj: Any): Boolean

external fun isBuffer(obj: Any): Boolean

external fun isFunction(obj: Any): Boolean

external fun isNull(obj: Any): Boolean

external fun isNullOrUndefined(obj: Any): Boolean

external fun isNumber(obj: Any): Boolean

external fun isObject(obj: Any): Boolean

external fun isPrimitive(obj: Any): Boolean

external fun isString(obj: Any): Boolean

external fun isSymbol(obj: Any): Boolean

external fun isUndefined(obj: Any): Boolean

external fun <T : Function<*>> deprecate(fn: T, message: String, code: String = definedExternally): T

external fun isDeepStrictEqual(val1: Any, val2: Any): Boolean

external fun callbackify(fn: () -> Promise<Unit>): (callback: (err: Exception) -> Unit) -> Unit

external fun <TResult> callbackify(fn: () -> Promise<TResult>): (callback: (err: Exception, result: TResult) -> Unit) -> Unit

external fun <T1> callbackify(fn: (arg1: T1) -> Promise<Unit>): (arg1: T1, callback: (err: Exception) -> Unit) -> Unit

external fun <T1, TResult> callbackify(fn: (arg1: T1) -> Promise<TResult>): (arg1: T1, callback: (err: Exception, result: TResult) -> Unit) -> Unit

external fun <T1, T2> callbackify(fn: (arg1: T1, arg2: T2) -> Promise<Unit>): (arg1: T1, arg2: T2, callback: (err: Exception) -> Unit) -> Unit

external fun <T1, T2, TResult> callbackify(fn: (arg1: T1, arg2: T2) -> Promise<TResult>): (arg1: T1, arg2: T2, callback: (err: Exception?, result: TResult) -> Unit) -> Unit

external fun <T1, T2, T3> callbackify(fn: (arg1: T1, arg2: T2, arg3: T3) -> Promise<Unit>): (arg1: T1, arg2: T2, arg3: T3, callback: (err: Exception) -> Unit) -> Unit

external fun <T1, T2, T3, TResult> callbackify(fn: (arg1: T1, arg2: T2, arg3: T3) -> Promise<TResult>): (arg1: T1, arg2: T2, arg3: T3, callback: (err: Exception?, result: TResult) -> Unit) -> Unit

external fun <T1, T2, T3, T4> callbackify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4) -> Promise<Unit>): (arg1: T1, arg2: T2, arg3: T3, arg4: T4, callback: (err: Exception) -> Unit) -> Unit

external fun <T1, T2, T3, T4, TResult> callbackify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4) -> Promise<TResult>): (arg1: T1, arg2: T2, arg3: T3, arg4: T4, callback: (err: Exception?, result: TResult) -> Unit) -> Unit

external fun <T1, T2, T3, T4, T5> callbackify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5) -> Promise<Unit>): (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, callback: (err: Exception) -> Unit) -> Unit

external fun <T1, T2, T3, T4, T5, TResult> callbackify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5) -> Promise<TResult>): (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, callback: (err: Exception?, result: TResult) -> Unit) -> Unit

external fun <T1, T2, T3, T4, T5, T6> callbackify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, arg6: T6) -> Promise<Unit>): (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, arg6: T6, callback: (err: Exception) -> Unit) -> Unit

external fun <T1, T2, T3, T4, T5, T6, TResult> callbackify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, arg6: T6) -> Promise<TResult>): (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, arg6: T6, callback: (err: Exception?, result: TResult) -> Unit) -> Unit

external fun <TResult> promisify(fn: (callback: (err: Any, result: TResult) -> Unit) -> Unit): () -> Promise<TResult>

external fun promisify(fn: (callback: (err: Any) -> Unit) -> Unit): () -> Promise<Unit>

external fun <T1, TResult> promisify(fn: (arg1: T1, callback: (err: Any, result: TResult) -> Unit) -> Unit): (arg1: T1) -> Promise<TResult>

external fun <T1> promisify(fn: (arg1: T1, callback: (err: Any) -> Unit) -> Unit): (arg1: T1) -> Promise<Unit>

external fun <T1, T2, TResult> promisify(fn: (arg1: T1, arg2: T2, callback: (err: Any, result: TResult) -> Unit) -> Unit): (arg1: T1, arg2: T2) -> Promise<TResult>

external fun <T1, T2> promisify(fn: (arg1: T1, arg2: T2, callback: (err: Any) -> Unit) -> Unit): (arg1: T1, arg2: T2) -> Promise<Unit>

external fun <T1, T2, T3, TResult> promisify(fn: (arg1: T1, arg2: T2, arg3: T3, callback: (err: Any, result: TResult) -> Unit) -> Unit): (arg1: T1, arg2: T2, arg3: T3) -> Promise<TResult>

external fun <T1, T2, T3> promisify(fn: (arg1: T1, arg2: T2, arg3: T3, callback: (err: Any) -> Unit) -> Unit): (arg1: T1, arg2: T2, arg3: T3) -> Promise<Unit>

external fun <T1, T2, T3, T4, TResult> promisify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4, callback: (err: Any, result: TResult) -> Unit) -> Unit): (arg1: T1, arg2: T2, arg3: T3, arg4: T4) -> Promise<TResult>

external fun <T1, T2, T3, T4> promisify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4, callback: (err: Any) -> Unit) -> Unit): (arg1: T1, arg2: T2, arg3: T3, arg4: T4) -> Promise<Unit>

external fun <T1, T2, T3, T4, T5, TResult> promisify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, callback: (err: Any, result: TResult) -> Unit) -> Unit): (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5) -> Promise<TResult>

external fun <T1, T2, T3, T4, T5> promisify(fn: (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, callback: (err: Any) -> Unit) -> Unit): (arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5) -> Promise<Unit>

external fun promisify(fn: Function<*>): Function<*>

external interface `T$79` {
    var fatal: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var ignoreBOM: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$80` {
    var stream: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external open class TextDecoder(encoding: String = definedExternally, options: `T$79` = definedExternally) {
    open var encoding: String
    open var fatal: Boolean
    open var ignoreBOM: Boolean
    open fun decode(input: Uint8Array? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: Uint8ClampedArray? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: Uint16Array? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: Uint32Array? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: Int8Array? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: Int16Array? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: Int32Array? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: Float32Array? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: Float64Array? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: DataView? = definedExternally, options: `T$80` = definedExternally): String
    open fun decode(input: ArrayBuffer? = definedExternally, options: `T$80` = definedExternally): String
}

external interface EncodeIntoResult {
    var read: Number
    var written: Number
}

external open class TextEncoder {
    open var encoding: String
    open fun encode(input: String = definedExternally): Uint8Array
    open fun encodeInto(input: String, output: Uint8Array): EncodeIntoResult
}