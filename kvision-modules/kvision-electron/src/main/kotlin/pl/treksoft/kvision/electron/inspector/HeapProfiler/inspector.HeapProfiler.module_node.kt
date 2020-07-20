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

@file:JsQualifier("pl.treksoft.kvision.electron.inspector.HeapProfiler")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.inspector.HeapProfiler

import pl.treksoft.kvision.electron.inspector.Runtime.CallFrame
import pl.treksoft.kvision.electron.inspector.Runtime.RemoteObject
import pl.treksoft.kvision.electron.inspector.Runtime.RemoteObjectId

external interface SamplingHeapProfileNode {
    var callFrame: CallFrame
    var selfSize: Number
    var children: Array<SamplingHeapProfileNode>
}

external interface SamplingHeapProfile {
    var head: SamplingHeapProfileNode
}

external interface StartTrackingHeapObjectsParameterType {
    var trackAllocations: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface StopTrackingHeapObjectsParameterType {
    var reportProgress: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TakeHeapSnapshotParameterType {
    var reportProgress: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GetObjectByHeapObjectIdParameterType {
    var objectId: HeapSnapshotObjectId
    var objectGroup: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface AddInspectedHeapObjectParameterType {
    var heapObjectId: HeapSnapshotObjectId
}

external interface GetHeapObjectIdParameterType {
    var objectId: RemoteObjectId
}

external interface StartSamplingParameterType {
    var samplingInterval: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GetObjectByHeapObjectIdReturnType {
    var result: RemoteObject
}

external interface GetHeapObjectIdReturnType {
    var heapSnapshotObjectId: HeapSnapshotObjectId
}

external interface StopSamplingReturnType {
    var profile: SamplingHeapProfile
}

external interface GetSamplingProfileReturnType {
    var profile: SamplingHeapProfile
}

external interface AddHeapSnapshotChunkEventDataType {
    var chunk: String
}

external interface ReportHeapSnapshotProgressEventDataType {
    var done: Number
    var total: Number
    var finished: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LastSeenObjectIdEventDataType {
    var lastSeenObjectId: Number
    var timestamp: Number
}

external interface HeapStatsUpdateEventDataType {
    var statsUpdate: Array<Number>
}