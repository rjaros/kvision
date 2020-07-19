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

@file:JsQualifier("pl.treksoft.kvision.electron.fs.realpathSync")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.fs.realpathSync

import node.buffer.Buffer
import pl.treksoft.kvision.electron.child_process.`T$22`
import pl.treksoft.kvision.electron.fs.`T$38`
import pl.treksoft.kvision.electron.url.URL

external fun native(
    path: String,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun native(
    path: Buffer,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun native(
    path: URL,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun native(path: String, options: `T$38`): Buffer

external fun native(path: String, options: String /* "buffer" */): Buffer

external fun native(path: Buffer, options: `T$38`): Buffer

external fun native(path: Buffer, options: String /* "buffer" */): Buffer

external fun native(path: URL, options: `T$38`): Buffer

external fun native(path: URL, options: String /* "buffer" */): Buffer

external fun native(path: String, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun native(path: String, options: String? = definedExternally): dynamic /* String | Buffer */

external fun native(path: Buffer, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun native(path: Buffer, options: String? = definedExternally): dynamic /* String | Buffer */

external fun native(path: URL, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun native(path: URL, options: String? = definedExternally): dynamic /* String | Buffer */