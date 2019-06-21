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

import org.w3c.files.File
import kotlin.js.Date

external object LocalFileSystem {
    var TEMPORARY: Number
    var PERSISTENT: Number
    fun requestFileSystem(
        type: Number,
        size: Number,
        successCallback: FileSystemCallback,
        errorCallback: ErrorCallback? = definedExternally /* null */
    )

    fun resolveLocalFileSystemURL(
        url: String,
        successCallback: EntryCallback,
        errorCallback: ErrorCallback? = definedExternally /* null */
    )

    fun webkitRequestFileSystem(
        type: Number,
        size: Number,
        successCallback: FileSystemCallback,
        errorCallback: ErrorCallback? = definedExternally /* null */
    )
}

external object LocalFileSystemSync {
    var TEMPORARY: Number
    var PERSISTENT: Number
    fun requestFileSystemSync(type: Number, size: Number): FileSystemSync
    fun resolveLocalFileSystemSyncURL(url: String): EntrySync
    fun webkitRequestFileSystemSync(type: Number, size: Number): FileSystemSync
}

external interface Metadata {
    var modificationTime: Date
    var size: Number
}

external interface Flags {
    var create: Boolean? get() = definedExternally; set(value) = definedExternally
    var exclusive: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface FileSystem {
    var name: String
    var root: DirectoryEntry
}

external interface Entry {
    var isFile: Boolean
    var isDirectory: Boolean
    fun getMetadata(successCallback: MetadataCallback, errorCallback: ErrorCallback? = definedExternally /* null */)
    var name: String
    var fullPath: String
    var filesystem: FileSystem
    fun moveTo(
        parent: DirectoryEntry,
        newName: String? = definedExternally /* null */,
        successCallback: EntryCallback? = definedExternally /* null */,
        errorCallback: ErrorCallback? = definedExternally /* null */
    )

    fun copyTo(
        parent: DirectoryEntry,
        newName: String? = definedExternally /* null */,
        successCallback: EntryCallback? = definedExternally /* null */,
        errorCallback: ErrorCallback? = definedExternally /* null */
    )

    fun toURL(): String
    fun toInternalURL(): String
    fun remove(successCallback: VoidCallback, errorCallback: ErrorCallback? = definedExternally /* null */)
    fun getParent(successCallback: DirectoryEntryCallback, errorCallback: ErrorCallback? = definedExternally /* null */)
}

external interface DirectoryEntry : Entry {
    fun createReader(): DirectoryReader
    fun getFile(
        path: String,
        options: Flags? = definedExternally /* null */,
        successCallback: FileEntryCallback?,
        errorCallback: ErrorCallback? = definedExternally /* null */
    )

    fun getDirectory(
        path: String,
        options: Flags? = definedExternally /* null */,
        successCallback: DirectoryEntryCallback?,
        errorCallback: ErrorCallback? = definedExternally /* null */
    )

    fun removeRecursively(successCallback: VoidCallback, errorCallback: ErrorCallback? = definedExternally /* null */)
}

external interface DirectoryReader {
    fun readEntries(successCallback: EntriesCallback, errorCallback: ErrorCallback? = definedExternally /* null */)
}

external interface FileEntry : Entry {
    fun createWriter(successCallback: FileWriterCallback, errorCallback: ErrorCallback? = definedExternally /* null */)
    fun file(successCallback: FileCallback, errorCallback: ErrorCallback? = definedExternally /* null */)
}

external interface FileSystemCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun FileSystemCallback.invoke(filesystem: FileSystem) {
    asDynamic()(filesystem)
}

external interface EntryCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun EntryCallback.invoke(entry: Entry) {
    asDynamic()(entry)
}

external interface FileEntryCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun FileEntryCallback.invoke(entry: FileEntry) {
    asDynamic()(entry)
}

external interface DirectoryEntryCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun DirectoryEntryCallback.invoke(entry: DirectoryEntry) {
    asDynamic()(entry)
}

external interface EntriesCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun EntriesCallback.invoke(entries: Array<Entry>) {
    asDynamic()(entries)
}

external interface MetadataCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun MetadataCallback.invoke(metadata: Metadata) {
    asDynamic()(metadata)
}

external interface FileWriterCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun FileWriterCallback.invoke(fileWriter: FileWriter) {
    asDynamic()(fileWriter)
}

external interface FileCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun FileCallback.invoke(file: File) {
    asDynamic()(file)
}

external interface VoidCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun VoidCallback.invoke() {
    asDynamic()()
}

external interface ErrorCallback

@Suppress("NOTHING_TO_INLINE")
inline operator fun ErrorCallback.invoke(err: Error) {
    asDynamic()(err)
}

external interface FileSystemSync {
    var name: String
    var root: DirectoryEntrySync
}

external interface EntrySync {
    var isFile: Boolean
    var isDirectory: Boolean
    fun getMetadata(): Metadata
    var name: String
    var fullPath: String
    var filesystem: FileSystemSync
    fun moveTo(parent: DirectoryEntrySync, newName: String? = definedExternally /* null */): EntrySync
    fun copyTo(parent: DirectoryEntrySync, newName: String? = definedExternally /* null */): EntrySync
    fun toURL(): String
    fun remove()
    fun getParent(): DirectoryEntrySync
}

external interface DirectoryEntrySync : EntrySync {
    fun createReader(): DirectoryReaderSync
    fun getFile(path: String, options: Flags? = definedExternally /* null */): FileEntrySync
    fun getDirectory(path: String, options: Flags? = definedExternally /* null */): DirectoryEntrySync
    fun removeRecursively()
}

external interface DirectoryReaderSync {
    fun readEntries(): Array<EntrySync>
}

external interface FileEntrySync : EntrySync {
    fun createWriter(): FileWriterSync
    fun file(): File
}
