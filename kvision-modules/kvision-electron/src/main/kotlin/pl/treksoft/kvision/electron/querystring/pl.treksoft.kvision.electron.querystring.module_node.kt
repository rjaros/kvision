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

@file:JsModule("querystring")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.querystring

external interface StringifyOptions {
    var encodeURIComponent: ((str: String) -> String)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ParseOptions {
    var maxKeys: Number?
        get() = definedExternally
        set(value) = definedExternally
    var decodeURIComponent: ((str: String) -> String)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ParsedUrlQuery {
    @nativeGetter
    operator fun get(key: String): dynamic /* String? | Array<String>? */

    @nativeSetter
    operator fun set(key: String, value: String)

    @nativeSetter
    operator fun set(key: String, value: Array<String>)
}

external interface ParsedUrlQueryInput {
    @nativeGetter
    operator fun get(key: String): dynamic /* String? | Number? | Boolean? | Array<String>? | Array<Number>? | Array<Boolean>? */

    @nativeSetter
    operator fun set(key: String, value: String?)

    @nativeSetter
    operator fun set(key: String, value: Number?)

    @nativeSetter
    operator fun set(key: String, value: Boolean?)

    @nativeSetter
    operator fun set(key: String, value: Array<String>?)

    @nativeSetter
    operator fun set(key: String, value: Array<Number>?)

    @nativeSetter
    operator fun set(key: String, value: Array<Boolean>?)
}

external fun stringify(
    obj: ParsedUrlQueryInput = definedExternally,
    sep: String = definedExternally,
    eq: String = definedExternally,
    options: StringifyOptions = definedExternally
): String

external fun parse(
    str: String,
    sep: String = definedExternally,
    eq: String = definedExternally,
    options: ParseOptions = definedExternally
): ParsedUrlQuery

external var encode: Any

external var decode: Any

external fun escape(str: String): String

external fun unescape(str: String): String