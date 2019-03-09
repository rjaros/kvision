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
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
    "UNUSED", "TooManyFunctions"
)
@file:JsQualifier("NodeJS")

package pl.treksoft.kvision.electron.nodejs

import pl.treksoft.kvision.electron.CPUUsage
import pl.treksoft.kvision.electron.EventEmitter
import pl.treksoft.kvision.electron.IOCounters
import pl.treksoft.kvision.electron.ProcessMemoryInfo
import pl.treksoft.kvision.electron.SystemMemoryInfo

external interface Process : EventEmitter {
    override fun on(event: String /* "loaded" */, listener: Function<*>): Process /* this */
    override fun once(event: String /* "loaded" */, listener: Function<*>): Process /* this */
    override fun addListener(event: String /* "loaded" */, listener: Function<*>): Process /* this */
    override fun removeListener(event: String /* "loaded" */, listener: Function<*>): Process /* this */
    fun crash()
    fun getCPUUsage(): CPUUsage
    fun getIOCounters(): IOCounters
    fun getProcessMemoryInfo(): ProcessMemoryInfo
    fun getSystemMemoryInfo(): SystemMemoryInfo
    fun hang()
    fun setFdLimit(maxDescriptors: Number)
    var defaultApp: Boolean? get() = definedExternally; set(value) = definedExternally
    var mas: Boolean? get() = definedExternally; set(value) = definedExternally
    var noAsar: Boolean? get() = definedExternally; set(value) = definedExternally
    var noDeprecation: Boolean? get() = definedExternally; set(value) = definedExternally
    var resourcesPath: String? get() = definedExternally; set(value) = definedExternally
    var sandboxed: Boolean? get() = definedExternally; set(value) = definedExternally
    var throwDeprecation: Boolean? get() = definedExternally; set(value) = definedExternally
    var traceDeprecation: Boolean? get() = definedExternally; set(value) = definedExternally
    var traceProcessWarnings: Boolean? get() = definedExternally; set(value) = definedExternally
    var type: String? get() = definedExternally; set(value) = definedExternally
    var versions: ProcessVersions? get() = definedExternally; set(value) = definedExternally
    var windowsStore: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface ProcessVersions {
    var electron: String
    var chrome: String
}
