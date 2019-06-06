@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
    "unused", "PropertyName", "TooManyFunctions", "VariableNaming", "MaxLineLength"
)

package pl.treksoft.kvision.cordova

import org.w3c.dom.events.EventTarget
import org.w3c.files.Blob
import org.w3c.xhr.ProgressEvent

external interface FileSaver : EventTarget {
    fun abort()
    var INIT: Number
    var WRITING: Number
    var DONE: Number
    var readyState: Number
    var error: Error
    var onwritestart: (event: ProgressEvent) -> Unit
    var onprogress: (event: ProgressEvent) -> Unit
    var onwrite: (event: ProgressEvent) -> Unit
    var onabort: (event: ProgressEvent) -> Unit
    var onerror: (event: ProgressEvent) -> Unit
    var onwriteend: (event: ProgressEvent) -> Unit

}

external interface FileWriter : FileSaver {
    var position: Number
    var length: Number
    fun write(data: Blob)
    fun seek(offset: Number)
    fun truncate(size: Number)
}

external interface FileWriterSync {
    var position: Number
    var length: Number
    fun write(data: Blob)
    fun seek(offset: Number)
    fun truncate(size: Number)
}
