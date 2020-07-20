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

@file:JsModule("perf_hooks")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.perf_hooks

import pl.treksoft.kvision.electron.async_hooks.AsyncResource

external interface PerformanceEntry {
    var duration: Number
    var name: String
    var startTime: Number
    var entryType: String
    var kind: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PerformanceNodeTiming : PerformanceEntry {
    var bootstrapComplete: Number
    var clusterSetupEnd: Number
    var clusterSetupStart: Number
    var loopExit: Number
    var loopStart: Number
    var moduleLoadEnd: Number
    var moduleLoadStart: Number
    var nodeStart: Number
    var preloadModuleLoadEnd: Number
    var preloadModuleLoadStart: Number
    var thirdPartyMainEnd: Number
    var thirdPartyMainStart: Number
    var v8Start: Number
}

external interface Performance {
    fun clearFunctions(name: String = definedExternally)
    fun clearMarks(name: String = definedExternally)
    fun clearMeasures(name: String = definedExternally)
    fun getEntries(): Array<PerformanceEntry>
    fun getEntriesByName(name: String, type: String = definedExternally): Array<PerformanceEntry>
    fun getEntriesByType(type: String): Array<PerformanceEntry>
    fun mark(name: String = definedExternally)
    fun measure(name: String, startMark: String, endMark: String)
    var nodeTiming: PerformanceNodeTiming
    fun now(): Number
    var timeOrigin: Number
    fun <T : (optionalParams: Any) -> Any> timerify(fn: T): T
}

external interface PerformanceObserverEntryList {
    fun getEntries(): Array<PerformanceEntry>
    fun getEntriesByName(name: String, type: String = definedExternally): Array<PerformanceEntry>
    fun getEntriesByType(type: String): Array<PerformanceEntry>
}

external interface `T$75` {
    var entryTypes: Array<String>
    var buffered: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external open class PerformanceObserver(callback: PerformanceObserverCallback) : AsyncResource {
    open fun disconnect()
    open fun observe(options: `T$75`)
}

external var performance: Performance

external interface EventLoopMonitorOptions {
    var resolution: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface EventLoopDelayMonitor {
    fun enable(): Boolean
    fun disable(): Boolean
    fun reset()
    fun percentile(percentile: Number): Number
    var percentiles: Map<Number, Number>
    var exceeds: Number
    var min: Number
    var max: Number
    var mean: Number
    var stddev: Number
}

external fun monitorEventLoopDelay(options: EventLoopMonitorOptions = definedExternally): EventLoopDelayMonitor