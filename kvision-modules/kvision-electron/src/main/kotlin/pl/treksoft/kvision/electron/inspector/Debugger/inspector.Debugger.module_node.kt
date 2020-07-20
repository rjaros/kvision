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

@file:JsQualifier("pl.treksoft.kvision.electron.inspector.Debugger")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.inspector.Debugger

import pl.treksoft.kvision.electron.inspector.Runtime.CallArgument
import pl.treksoft.kvision.electron.inspector.Runtime.ExceptionDetails
import pl.treksoft.kvision.electron.inspector.Runtime.ExecutionContextId
import pl.treksoft.kvision.electron.inspector.Runtime.RemoteObject
import pl.treksoft.kvision.electron.inspector.Runtime.ScriptId
import pl.treksoft.kvision.electron.inspector.Runtime.StackTrace
import pl.treksoft.kvision.electron.inspector.Runtime.StackTraceId
import pl.treksoft.kvision.electron.inspector.Runtime.UniqueDebuggerId

external interface Location {
    var scriptId: ScriptId
    var lineNumber: Number
    var columnNumber: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ScriptPosition {
    var lineNumber: Number
    var columnNumber: Number
}

external interface CallFrame {
    var callFrameId: CallFrameId
    var functionName: String
    var functionLocation: Location?
        get() = definedExternally
        set(value) = definedExternally
    var location: Location
    var url: String
    var scopeChain: Array<Scope>
    var `this`: RemoteObject
    var returnValue: RemoteObject?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Scope {
    var type: String
    var `object`: RemoteObject
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var startLocation: Location?
        get() = definedExternally
        set(value) = definedExternally
    var endLocation: Location?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SearchMatch {
    var lineNumber: Number
    var lineContent: String
}

external interface BreakLocation {
    var scriptId: ScriptId
    var lineNumber: Number
    var columnNumber: Number?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SetBreakpointsActiveParameterType {
    var active: Boolean
}

external interface SetSkipAllPausesParameterType {
    var skip: Boolean
}

external interface SetBreakpointByUrlParameterType {
    var lineNumber: Number
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
    var urlRegex: String?
        get() = definedExternally
        set(value) = definedExternally
    var scriptHash: String?
        get() = definedExternally
        set(value) = definedExternally
    var columnNumber: Number?
        get() = definedExternally
        set(value) = definedExternally
    var condition: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SetBreakpointParameterType {
    var location: Location
    var condition: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RemoveBreakpointParameterType {
    var breakpointId: BreakpointId
}

external interface GetPossibleBreakpointsParameterType {
    var start: Location
    var end: Location?
        get() = definedExternally
        set(value) = definedExternally
    var restrictToFunction: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ContinueToLocationParameterType {
    var location: Location
    var targetCallFrames: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PauseOnAsyncCallParameterType {
    var parentStackTraceId: StackTraceId
}

external interface StepIntoParameterType {
    var breakOnAsyncCall: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GetStackTraceParameterType {
    var stackTraceId: StackTraceId
}

external interface SearchInContentParameterType {
    var scriptId: ScriptId
    var query: String
    var caseSensitive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isRegex: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SetScriptSourceParameterType {
    var scriptId: ScriptId
    var scriptSource: String
    var dryRun: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RestartFrameParameterType {
    var callFrameId: CallFrameId
}

external interface GetScriptSourceParameterType {
    var scriptId: ScriptId
}

external interface SetPauseOnExceptionsParameterType {
    var state: String
}

external interface EvaluateOnCallFrameParameterType {
    var callFrameId: CallFrameId
    var expression: String
    var objectGroup: String?
        get() = definedExternally
        set(value) = definedExternally
    var includeCommandLineAPI: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var silent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var returnByValue: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var generatePreview: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var throwOnSideEffect: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SetVariableValueParameterType {
    var scopeNumber: Number
    var variableName: String
    var newValue: CallArgument
    var callFrameId: CallFrameId
}

external interface SetReturnValueParameterType {
    var newValue: CallArgument
}

external interface SetAsyncCallStackDepthParameterType {
    var maxDepth: Number
}

external interface SetBlackboxPatternsParameterType {
    var patterns: Array<String>
}

external interface SetBlackboxedRangesParameterType {
    var scriptId: ScriptId
    var positions: Array<ScriptPosition>
}

external interface EnableReturnType {
    var debuggerId: UniqueDebuggerId
}

external interface SetBreakpointByUrlReturnType {
    var breakpointId: BreakpointId
    var locations: Array<Location>
}

external interface SetBreakpointReturnType {
    var breakpointId: BreakpointId
    var actualLocation: Location
}

external interface GetPossibleBreakpointsReturnType {
    var locations: Array<BreakLocation>
}

external interface GetStackTraceReturnType {
    var stackTrace: StackTrace
}

external interface SearchInContentReturnType {
    var result: Array<SearchMatch>
}

external interface SetScriptSourceReturnType {
    var callFrames: Array<CallFrame>?
        get() = definedExternally
        set(value) = definedExternally
    var stackChanged: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var asyncStackTrace: StackTrace?
        get() = definedExternally
        set(value) = definedExternally
    var asyncStackTraceId: StackTraceId?
        get() = definedExternally
        set(value) = definedExternally
    var exceptionDetails: ExceptionDetails?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RestartFrameReturnType {
    var callFrames: Array<CallFrame>
    var asyncStackTrace: StackTrace?
        get() = definedExternally
        set(value) = definedExternally
    var asyncStackTraceId: StackTraceId?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GetScriptSourceReturnType {
    var scriptSource: String
}

external interface EvaluateOnCallFrameReturnType {
    var result: RemoteObject
    var exceptionDetails: ExceptionDetails?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ScriptParsedEventDataType {
    var scriptId: ScriptId
    var url: String
    var startLine: Number
    var startColumn: Number
    var endLine: Number
    var endColumn: Number
    var executionContextId: ExecutionContextId
    var hash: String
    var executionContextAuxData: Any?
        get() = definedExternally
        set(value) = definedExternally
    var isLiveEdit: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var sourceMapURL: String?
        get() = definedExternally
        set(value) = definedExternally
    var hasSourceURL: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isModule: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var length: Number?
        get() = definedExternally
        set(value) = definedExternally
    var stackTrace: StackTrace?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ScriptFailedToParseEventDataType {
    var scriptId: ScriptId
    var url: String
    var startLine: Number
    var startColumn: Number
    var endLine: Number
    var endColumn: Number
    var executionContextId: ExecutionContextId
    var hash: String
    var executionContextAuxData: Any?
        get() = definedExternally
        set(value) = definedExternally
    var sourceMapURL: String?
        get() = definedExternally
        set(value) = definedExternally
    var hasSourceURL: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isModule: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var length: Number?
        get() = definedExternally
        set(value) = definedExternally
    var stackTrace: StackTrace?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BreakpointResolvedEventDataType {
    var breakpointId: BreakpointId
    var location: Location
}

external interface PausedEventDataType {
    var callFrames: Array<CallFrame>
    var reason: String
    var data: Any?
        get() = definedExternally
        set(value) = definedExternally
    var hitBreakpoints: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var asyncStackTrace: StackTrace?
        get() = definedExternally
        set(value) = definedExternally
    var asyncStackTraceId: StackTraceId?
        get() = definedExternally
        set(value) = definedExternally
    var asyncCallStackTraceId: StackTraceId?
        get() = definedExternally
        set(value) = definedExternally
}