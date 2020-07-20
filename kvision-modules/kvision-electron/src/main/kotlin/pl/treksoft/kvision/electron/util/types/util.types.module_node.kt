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

@file:JsQualifier("pl.treksoft.kvision.electron.util.types")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.util.types

external fun isAnyArrayBuffer(obj: Any): Boolean

external fun isArgumentsObject(obj: Any): Boolean

external fun isArrayBuffer(obj: Any): Boolean

external fun isArrayBufferView(obj: Any): Boolean

external fun isAsyncFunction(obj: Any): Boolean

external fun isBooleanObject(obj: Any): Boolean

external fun isBoxedPrimitive(obj: Any): Boolean

external fun isDataView(obj: Any): Boolean

external fun isDate(obj: Any): Boolean

external fun isExternal(obj: Any): Boolean

external fun isFloat32Array(obj: Any): Boolean

external fun isFloat64Array(obj: Any): Boolean

external fun isGeneratorFunction(obj: Any): Boolean

external fun isGeneratorObject(obj: Any): Boolean

external fun isInt8Array(obj: Any): Boolean

external fun isInt16Array(obj: Any): Boolean

external fun isInt32Array(obj: Any): Boolean

external fun isMap(obj: Any): Boolean

external fun isMapIterator(obj: Any): Boolean

external fun isModuleNamespaceObject(value: Any): Boolean

external fun isNativeError(obj: Any): Boolean

external fun isNumberObject(obj: Any): Boolean

external fun isPromise(obj: Any): Boolean

external fun isProxy(obj: Any): Boolean

external fun isRegExp(obj: Any): Boolean

external fun isSet(obj: Any): Boolean

external fun isSetIterator(obj: Any): Boolean

external fun isSharedArrayBuffer(obj: Any): Boolean

external fun isStringObject(obj: Any): Boolean

external fun isSymbolObject(obj: Any): Boolean

external fun isTypedArray(obj: Any): Boolean

external fun isUint8Array(obj: Any): Boolean

external fun isUint8ClampedArray(obj: Any): Boolean

external fun isUint16Array(obj: Any): Boolean

external fun isUint32Array(obj: Any): Boolean

external fun isWeakMap(obj: Any): Boolean

external fun isWeakSet(obj: Any): Boolean

external fun isWebAssemblyCompiledModule(obj: Any): Boolean

external fun isBigInt64Array(value: Any): Boolean

external fun isBigUint64Array(value: Any): Boolean