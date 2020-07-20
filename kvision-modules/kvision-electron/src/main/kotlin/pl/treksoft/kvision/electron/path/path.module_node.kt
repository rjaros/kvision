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

@file:JsModule("path")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.path

external interface ParsedPath {
    var root: String
    var dir: String
    var base: String
    var ext: String
    var name: String
}

external interface FormatInputPathObject {
    var root: String?
        get() = definedExternally
        set(value) = definedExternally
    var dir: String?
        get() = definedExternally
        set(value) = definedExternally
    var base: String?
        get() = definedExternally
        set(value) = definedExternally
    var ext: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
}

external fun normalize(p: String): String

external fun join(vararg paths: String): String

external fun resolve(vararg pathSegments: String): String

external fun isAbsolute(path: String): Boolean

external fun relative(from: String, to: String): String

external fun dirname(p: String): String

external fun basename(p: String, ext: String = definedExternally): String

external fun extname(p: String): String

external var sep: String /* '\\' | '/' */

external var delimiter: String /* ';' | ':' */

external fun parse(pathString: String): ParsedPath

external fun format(pathObject: FormatInputPathObject): String