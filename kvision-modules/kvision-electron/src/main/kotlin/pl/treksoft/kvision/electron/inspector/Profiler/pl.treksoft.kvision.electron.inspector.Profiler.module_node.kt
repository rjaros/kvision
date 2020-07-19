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

@file:JsQualifier("pl.treksoft.kvision.electron.inspector.Profiler")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.inspector.Profiler

import pl.treksoft.kvision.electron.inspector.Debugger.Location
import pl.treksoft.kvision.electron.inspector.Runtime.CallFrame
import pl.treksoft.kvision.electron.inspector.Runtime.ScriptId

external interface ProfileNode {
    var id: Number
    var callFrame: CallFrame
    var hitCount: Number?
        get() = definedExternally
        set(value) = definedExternally
    var children: Array<Number>?
        get() = definedExternally
        set(value) = definedExternally
    var deoptReason: String?
        get() = definedExternally
        set(value) = definedExternally
    var positionTicks: Array<PositionTickInfo>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Profile {
    var nodes: Array<ProfileNode>
    var startTime: Number
    var endTime: Number
    var samples: Array<Number>?
        get() = definedExternally
        set(value) = definedExternally
    var timeDeltas: Array<Number>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PositionTickInfo {
    var line: Number
    var ticks: Number
}

external interface CoverageRange {
    var startOffset: Number
    var endOffset: Number
    var count: Number
}

external interface FunctionCoverage {
    var functionName: String
    var ranges: Array<CoverageRange>
    var isBlockCoverage: Boolean
}

external interface ScriptCoverage {
    var scriptId: ScriptId
    var url: String
    var functions: Array<FunctionCoverage>
}

external interface TypeObject {
    var name: String
}

external interface TypeProfileEntry {
    var offset: Number
    var types: Array<TypeObject>
}

external interface ScriptTypeProfile {
    var scriptId: ScriptId
    var url: String
    var entries: Array<TypeProfileEntry>
}

external interface SetSamplingIntervalParameterType {
    var interval: Number
}

external interface StartPreciseCoverageParameterType {
    var callCount: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var detailed: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface StopReturnType {
    var profile: Profile
}

external interface TakePreciseCoverageReturnType {
    var result: Array<ScriptCoverage>
}

external interface GetBestEffortCoverageReturnType {
    var result: Array<ScriptCoverage>
}

external interface TakeTypeProfileReturnType {
    var result: Array<ScriptTypeProfile>
}

external interface ConsoleProfileStartedEventDataType {
    var id: String
    var location: Location
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ConsoleProfileFinishedEventDataType {
    var id: String
    var location: Location
    var profile: Profile
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
}