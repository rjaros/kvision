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

@file:JsModule("fs")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.fs

import node.buffer.Buffer
import org.khronos.webgl.*
import pl.treksoft.kvision.electron.child_process.`T$22`
import pl.treksoft.kvision.electron.events.internal.EventEmitter
import pl.treksoft.kvision.electron.stream.internal.Readable
import pl.treksoft.kvision.electron.stream.internal.Writable
import pl.treksoft.kvision.electron.url.URL
import kotlin.js.Date
import kotlin.js.Promise

external interface StatsBase<T> {
    fun isFile(): Boolean
    fun isDirectory(): Boolean
    fun isBlockDevice(): Boolean
    fun isCharacterDevice(): Boolean
    fun isSymbolicLink(): Boolean
    fun isFIFO(): Boolean
    fun isSocket(): Boolean
    var dev: T
    var ino: T
    var mode: T
    var nlink: T
    var uid: T
    var gid: T
    var rdev: T
    var size: T
    var blksize: T
    var blocks: T
    var atimeMs: T
    var mtimeMs: T
    var ctimeMs: T
    var birthtimeMs: T
    var atime: Date
    var mtime: Date
    var ctime: Date
    var birthtime: Date
}

external open class Stats : StatsBase<Number> {
    override fun isFile(): Boolean
    override fun isDirectory(): Boolean
    override fun isBlockDevice(): Boolean
    override fun isCharacterDevice(): Boolean
    override fun isSymbolicLink(): Boolean
    override fun isFIFO(): Boolean
    override fun isSocket(): Boolean
    override var dev: Number
    override var ino: Number
    override var mode: Number
    override var nlink: Number
    override var uid: Number
    override var gid: Number
    override var rdev: Number
    override var size: Number
    override var blksize: Number
    override var blocks: Number
    override var atimeMs: Number
    override var mtimeMs: Number
    override var ctimeMs: Number
    override var birthtimeMs: Number
    override var atime: Date
    override var mtime: Date
    override var ctime: Date
    override var birthtime: Date
}

external open class Dirent {
    open fun isFile(): Boolean
    open fun isDirectory(): Boolean
    open fun isBlockDevice(): Boolean
    open fun isCharacterDevice(): Boolean
    open fun isSymbolicLink(): Boolean
    open fun isFIFO(): Boolean
    open fun isSocket(): Boolean
    open var name: String
}

external open class Dir {
    open var path: String
    open fun close(): Promise<Unit>
    open fun close(cb: NoParamCallback)
    open fun closeSync()
    open fun read(): Promise<Dirent?>
    open fun read(cb: (err: Exception?, dirEnt: Dirent?) -> Unit)
    open fun readSync(): Dirent
}

external interface FSWatcher : EventEmitter {
    fun close()
    override fun addListener(event: String, listener: (args: Any) -> Unit): FSWatcher /* this */
    fun addListener(
        event: String /* "change" */,
        listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit
    ): FSWatcher /* this */

    fun addListener(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    fun addListener(event: String /* "close" */, listener: () -> Unit): FSWatcher /* this */
    override fun on(event: String, listener: (args: Any) -> Unit): FSWatcher /* this */
    fun on(
        event: String /* "change" */,
        listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit
    ): FSWatcher /* this */

    fun on(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    fun on(event: String /* "close" */, listener: () -> Unit): FSWatcher /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): FSWatcher /* this */
    fun once(
        event: String /* "change" */,
        listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit
    ): FSWatcher /* this */

    fun once(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    fun once(event: String /* "close" */, listener: () -> Unit): FSWatcher /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): FSWatcher /* this */
    fun prependListener(
        event: String /* "change" */,
        listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit
    ): FSWatcher /* this */

    fun prependListener(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    fun prependListener(event: String /* "close" */, listener: () -> Unit): FSWatcher /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): FSWatcher /* this */
    fun prependOnceListener(
        event: String /* "change" */,
        listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit
    ): FSWatcher /* this */

    fun prependOnceListener(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): FSWatcher /* this */
}

external open class ReadStream : Readable {
    open fun close()
    open var bytesRead: Number
    open var path: dynamic /* String | Buffer */
    override fun addListener(event: String, listener: (args: Any) -> Unit): ReadStream /* this */
    open fun addListener(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun addListener(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
    override fun on(event: String, listener: (args: Any) -> Unit): ReadStream /* this */
    open fun on(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun on(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): ReadStream /* this */
    open fun once(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun once(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): ReadStream /* this */
    open fun prependListener(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun prependListener(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): ReadStream /* this */
    open fun prependOnceListener(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
}

external open class WriteStream : Writable {
    open fun close()
    open var bytesWritten: Number
    open var path: dynamic /* String | Buffer */
    override fun addListener(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    open fun addListener(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun addListener(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
    override fun on(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    open fun on(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun on(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    open fun once(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun once(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    open fun prependListener(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun prependListener(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): WriteStream /* this */
    open fun prependOnceListener(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
}

external fun rename(oldPath: String, newPath: String, callback: NoParamCallback)

external fun rename(oldPath: String, newPath: Buffer, callback: NoParamCallback)

external fun rename(oldPath: String, newPath: URL, callback: NoParamCallback)

external fun rename(oldPath: Buffer, newPath: String, callback: NoParamCallback)

external fun rename(oldPath: Buffer, newPath: Buffer, callback: NoParamCallback)

external fun rename(oldPath: Buffer, newPath: URL, callback: NoParamCallback)

external fun rename(oldPath: URL, newPath: String, callback: NoParamCallback)

external fun rename(oldPath: URL, newPath: Buffer, callback: NoParamCallback)

external fun rename(oldPath: URL, newPath: URL, callback: NoParamCallback)

external fun renameSync(oldPath: String, newPath: String)

external fun renameSync(oldPath: String, newPath: Buffer)

external fun renameSync(oldPath: String, newPath: URL)

external fun renameSync(oldPath: Buffer, newPath: String)

external fun renameSync(oldPath: Buffer, newPath: Buffer)

external fun renameSync(oldPath: Buffer, newPath: URL)

external fun renameSync(oldPath: URL, newPath: String)

external fun renameSync(oldPath: URL, newPath: Buffer)

external fun renameSync(oldPath: URL, newPath: URL)

external fun truncate(path: String, len: Number?, callback: NoParamCallback)

external fun truncate(path: Buffer, len: Number?, callback: NoParamCallback)

external fun truncate(path: URL, len: Number?, callback: NoParamCallback)

external fun truncate(path: String, callback: NoParamCallback)

external fun truncate(path: Buffer, callback: NoParamCallback)

external fun truncate(path: URL, callback: NoParamCallback)

external fun truncateSync(path: String, len: Number? = definedExternally)

external fun truncateSync(path: Buffer, len: Number? = definedExternally)

external fun truncateSync(path: URL, len: Number? = definedExternally)

external fun ftruncate(fd: Number, len: Number?, callback: NoParamCallback)

external fun ftruncate(fd: Number, callback: NoParamCallback)

external fun ftruncateSync(fd: Number, len: Number? = definedExternally)

external fun chown(path: String, uid: Number, gid: Number, callback: NoParamCallback)

external fun chown(path: Buffer, uid: Number, gid: Number, callback: NoParamCallback)

external fun chown(path: URL, uid: Number, gid: Number, callback: NoParamCallback)

external fun chownSync(path: String, uid: Number, gid: Number)

external fun chownSync(path: Buffer, uid: Number, gid: Number)

external fun chownSync(path: URL, uid: Number, gid: Number)

external fun fchown(fd: Number, uid: Number, gid: Number, callback: NoParamCallback)

external fun fchownSync(fd: Number, uid: Number, gid: Number)

external fun lchown(path: String, uid: Number, gid: Number, callback: NoParamCallback)

external fun lchown(path: Buffer, uid: Number, gid: Number, callback: NoParamCallback)

external fun lchown(path: URL, uid: Number, gid: Number, callback: NoParamCallback)

external fun lchownSync(path: String, uid: Number, gid: Number)

external fun lchownSync(path: Buffer, uid: Number, gid: Number)

external fun lchownSync(path: URL, uid: Number, gid: Number)

external fun chmod(path: String, mode: String, callback: NoParamCallback)

external fun chmod(path: String, mode: Number, callback: NoParamCallback)

external fun chmod(path: Buffer, mode: String, callback: NoParamCallback)

external fun chmod(path: Buffer, mode: Number, callback: NoParamCallback)

external fun chmod(path: URL, mode: String, callback: NoParamCallback)

external fun chmod(path: URL, mode: Number, callback: NoParamCallback)

external fun chmodSync(path: String, mode: String)

external fun chmodSync(path: String, mode: Number)

external fun chmodSync(path: Buffer, mode: String)

external fun chmodSync(path: Buffer, mode: Number)

external fun chmodSync(path: URL, mode: String)

external fun chmodSync(path: URL, mode: Number)

external fun fchmod(fd: Number, mode: String, callback: NoParamCallback)

external fun fchmod(fd: Number, mode: Number, callback: NoParamCallback)

external fun fchmodSync(fd: Number, mode: String)

external fun fchmodSync(fd: Number, mode: Number)

external fun lchmod(path: String, mode: String, callback: NoParamCallback)

external fun lchmod(path: String, mode: Number, callback: NoParamCallback)

external fun lchmod(path: Buffer, mode: String, callback: NoParamCallback)

external fun lchmod(path: Buffer, mode: Number, callback: NoParamCallback)

external fun lchmod(path: URL, mode: String, callback: NoParamCallback)

external fun lchmod(path: URL, mode: Number, callback: NoParamCallback)

external fun lchmodSync(path: String, mode: String)

external fun lchmodSync(path: String, mode: Number)

external fun lchmodSync(path: Buffer, mode: String)

external fun lchmodSync(path: Buffer, mode: Number)

external fun lchmodSync(path: URL, mode: String)

external fun lchmodSync(path: URL, mode: Number)

external fun stat(path: String, callback: (err: Exception?, stats: Stats) -> Unit)

external fun stat(path: Buffer, callback: (err: Exception?, stats: Stats) -> Unit)

external fun stat(path: URL, callback: (err: Exception?, stats: Stats) -> Unit)

external fun statSync(path: String): Stats

external fun statSync(path: Buffer): Stats

external fun statSync(path: URL): Stats

external fun fstat(fd: Number, callback: (err: Exception?, stats: Stats) -> Unit)

external fun fstatSync(fd: Number): Stats

external fun lstat(path: String, callback: (err: Exception?, stats: Stats) -> Unit)

external fun lstat(path: Buffer, callback: (err: Exception?, stats: Stats) -> Unit)

external fun lstat(path: URL, callback: (err: Exception?, stats: Stats) -> Unit)

external fun lstatSync(path: String): Stats

external fun lstatSync(path: Buffer): Stats

external fun lstatSync(path: URL): Stats

external fun link(existingPath: String, newPath: String, callback: NoParamCallback)

external fun link(existingPath: String, newPath: Buffer, callback: NoParamCallback)

external fun link(existingPath: String, newPath: URL, callback: NoParamCallback)

external fun link(existingPath: Buffer, newPath: String, callback: NoParamCallback)

external fun link(existingPath: Buffer, newPath: Buffer, callback: NoParamCallback)

external fun link(existingPath: Buffer, newPath: URL, callback: NoParamCallback)

external fun link(existingPath: URL, newPath: String, callback: NoParamCallback)

external fun link(existingPath: URL, newPath: Buffer, callback: NoParamCallback)

external fun link(existingPath: URL, newPath: URL, callback: NoParamCallback)

external fun linkSync(existingPath: String, newPath: String)

external fun linkSync(existingPath: String, newPath: Buffer)

external fun linkSync(existingPath: String, newPath: URL)

external fun linkSync(existingPath: Buffer, newPath: String)

external fun linkSync(existingPath: Buffer, newPath: Buffer)

external fun linkSync(existingPath: Buffer, newPath: URL)

external fun linkSync(existingPath: URL, newPath: String)

external fun linkSync(existingPath: URL, newPath: Buffer)

external fun linkSync(existingPath: URL, newPath: URL)

external fun symlink(
    target: String,
    path: String,
    type: String /* "dir" | "file" | "junction" */,
    callback: NoParamCallback
)

external fun symlink(
    target: String,
    path: Buffer,
    type: String /* "dir" | "file" | "junction" */,
    callback: NoParamCallback
)

external fun symlink(
    target: String,
    path: URL,
    type: String /* "dir" | "file" | "junction" */,
    callback: NoParamCallback
)

external fun symlink(
    target: Buffer,
    path: String,
    type: String /* "dir" | "file" | "junction" */,
    callback: NoParamCallback
)

external fun symlink(
    target: Buffer,
    path: Buffer,
    type: String /* "dir" | "file" | "junction" */,
    callback: NoParamCallback
)

external fun symlink(
    target: Buffer,
    path: URL,
    type: String /* "dir" | "file" | "junction" */,
    callback: NoParamCallback
)

external fun symlink(
    target: URL,
    path: String,
    type: String /* "dir" | "file" | "junction" */,
    callback: NoParamCallback
)

external fun symlink(
    target: URL,
    path: Buffer,
    type: String /* "dir" | "file" | "junction" */,
    callback: NoParamCallback
)

external fun symlink(target: URL, path: URL, type: String /* "dir" | "file" | "junction" */, callback: NoParamCallback)

external fun symlink(target: String, path: String, callback: NoParamCallback)

external fun symlink(target: String, path: Buffer, callback: NoParamCallback)

external fun symlink(target: String, path: URL, callback: NoParamCallback)

external fun symlink(target: Buffer, path: String, callback: NoParamCallback)

external fun symlink(target: Buffer, path: Buffer, callback: NoParamCallback)

external fun symlink(target: Buffer, path: URL, callback: NoParamCallback)

external fun symlink(target: URL, path: String, callback: NoParamCallback)

external fun symlink(target: URL, path: Buffer, callback: NoParamCallback)

external fun symlink(target: URL, path: URL, callback: NoParamCallback)

external fun symlinkSync(
    target: String,
    path: String,
    type: String /* "dir" | "file" | "junction" */ = definedExternally
)

external fun symlinkSync(
    target: String,
    path: Buffer,
    type: String /* "dir" | "file" | "junction" */ = definedExternally
)

external fun symlinkSync(target: String, path: URL, type: String /* "dir" | "file" | "junction" */ = definedExternally)

external fun symlinkSync(
    target: Buffer,
    path: String,
    type: String /* "dir" | "file" | "junction" */ = definedExternally
)

external fun symlinkSync(
    target: Buffer,
    path: Buffer,
    type: String /* "dir" | "file" | "junction" */ = definedExternally
)

external fun symlinkSync(target: Buffer, path: URL, type: String /* "dir" | "file" | "junction" */ = definedExternally)

external fun symlinkSync(target: URL, path: String, type: String /* "dir" | "file" | "junction" */ = definedExternally)

external fun symlinkSync(target: URL, path: Buffer, type: String /* "dir" | "file" | "junction" */ = definedExternally)

external fun symlinkSync(target: URL, path: URL, type: String /* "dir" | "file" | "junction" */ = definedExternally)

external interface `T$37` {
    var encoding: String? /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
        get() = definedExternally
        set(value) = definedExternally
}

external fun readlink(
    path: String,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, linkString: String) -> Unit
)

external fun readlink(
    path: Buffer,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, linkString: String) -> Unit
)

external fun readlink(
    path: URL,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, linkString: String) -> Unit
)

external interface `T$38` {
    var encoding: String /* "buffer" */
}

external fun readlink(path: String, options: `T$38`, callback: (err: Exception?, linkString: Buffer) -> Unit)

external fun readlink(
    path: String,
    options: String /* "buffer" */,
    callback: (err: Exception?, linkString: Buffer) -> Unit
)

external fun readlink(path: Buffer, options: `T$38`, callback: (err: Exception?, linkString: Buffer) -> Unit)

external fun readlink(
    path: Buffer,
    options: String /* "buffer" */,
    callback: (err: Exception?, linkString: Buffer) -> Unit
)

external fun readlink(path: URL, options: `T$38`, callback: (err: Exception?, linkString: Buffer) -> Unit)

external fun readlink(
    path: URL,
    options: String /* "buffer" */,
    callback: (err: Exception?, linkString: Buffer) -> Unit
)

external fun readlink(
    path: String,
    options: `T$22`?,
    callback: (err: Exception?, linkString: dynamic /* String | Buffer */) -> Unit
)

external fun readlink(
    path: String,
    options: String?,
    callback: (err: Exception?, linkString: dynamic /* String | Buffer */) -> Unit
)

external fun readlink(
    path: Buffer,
    options: `T$22`?,
    callback: (err: Exception?, linkString: dynamic /* String | Buffer */) -> Unit
)

external fun readlink(
    path: Buffer,
    options: String?,
    callback: (err: Exception?, linkString: dynamic /* String | Buffer */) -> Unit
)

external fun readlink(
    path: URL,
    options: `T$22`?,
    callback: (err: Exception?, linkString: dynamic /* String | Buffer */) -> Unit
)

external fun readlink(
    path: URL,
    options: String?,
    callback: (err: Exception?, linkString: dynamic /* String | Buffer */) -> Unit
)

external fun readlink(path: String, callback: (err: Exception?, linkString: String) -> Unit)

external fun readlink(path: Buffer, callback: (err: Exception?, linkString: String) -> Unit)

external fun readlink(path: URL, callback: (err: Exception?, linkString: String) -> Unit)

external fun readlinkSync(
    path: String,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun readlinkSync(
    path: Buffer,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun readlinkSync(
    path: URL,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun readlinkSync(path: String, options: `T$38`): Buffer

external fun readlinkSync(path: String, options: String /* "buffer" */): Buffer

external fun readlinkSync(path: Buffer, options: `T$38`): Buffer

external fun readlinkSync(path: Buffer, options: String /* "buffer" */): Buffer

external fun readlinkSync(path: URL, options: `T$38`): Buffer

external fun readlinkSync(path: URL, options: String /* "buffer" */): Buffer

external fun readlinkSync(path: String, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun readlinkSync(path: String, options: String? = definedExternally): dynamic /* String | Buffer */

external fun readlinkSync(path: Buffer, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun readlinkSync(path: Buffer, options: String? = definedExternally): dynamic /* String | Buffer */

external fun readlinkSync(path: URL, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun readlinkSync(path: URL, options: String? = definedExternally): dynamic /* String | Buffer */

external fun realpath(
    path: String,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, resolvedPath: String) -> Unit
)

external fun realpath(
    path: Buffer,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, resolvedPath: String) -> Unit
)

external fun realpath(
    path: URL,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, resolvedPath: String) -> Unit
)

external fun realpath(path: String, options: `T$38`, callback: (err: Exception?, resolvedPath: Buffer) -> Unit)

external fun realpath(
    path: String,
    options: String /* "buffer" */,
    callback: (err: Exception?, resolvedPath: Buffer) -> Unit
)

external fun realpath(path: Buffer, options: `T$38`, callback: (err: Exception?, resolvedPath: Buffer) -> Unit)

external fun realpath(
    path: Buffer,
    options: String /* "buffer" */,
    callback: (err: Exception?, resolvedPath: Buffer) -> Unit
)

external fun realpath(path: URL, options: `T$38`, callback: (err: Exception?, resolvedPath: Buffer) -> Unit)

external fun realpath(
    path: URL,
    options: String /* "buffer" */,
    callback: (err: Exception?, resolvedPath: Buffer) -> Unit
)

external fun realpath(
    path: String,
    options: `T$22`?,
    callback: (err: Exception?, resolvedPath: dynamic /* String | Buffer */) -> Unit
)

external fun realpath(
    path: String,
    options: String?,
    callback: (err: Exception?, resolvedPath: dynamic /* String | Buffer */) -> Unit
)

external fun realpath(
    path: Buffer,
    options: `T$22`?,
    callback: (err: Exception?, resolvedPath: dynamic /* String | Buffer */) -> Unit
)

external fun realpath(
    path: Buffer,
    options: String?,
    callback: (err: Exception?, resolvedPath: dynamic /* String | Buffer */) -> Unit
)

external fun realpath(
    path: URL,
    options: `T$22`?,
    callback: (err: Exception?, resolvedPath: dynamic /* String | Buffer */) -> Unit
)

external fun realpath(
    path: URL,
    options: String?,
    callback: (err: Exception?, resolvedPath: dynamic /* String | Buffer */) -> Unit
)

external fun realpath(path: String, callback: (err: Exception?, resolvedPath: String) -> Unit)

external fun realpath(path: Buffer, callback: (err: Exception?, resolvedPath: String) -> Unit)

external fun realpath(path: URL, callback: (err: Exception?, resolvedPath: String) -> Unit)

external fun realpathSync(
    path: String,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun realpathSync(
    path: Buffer,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun realpathSync(
    path: URL,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): String

external fun realpathSync(path: String, options: `T$38`): Buffer

external fun realpathSync(path: String, options: String /* "buffer" */): Buffer

external fun realpathSync(path: Buffer, options: `T$38`): Buffer

external fun realpathSync(path: Buffer, options: String /* "buffer" */): Buffer

external fun realpathSync(path: URL, options: `T$38`): Buffer

external fun realpathSync(path: URL, options: String /* "buffer" */): Buffer

external fun realpathSync(path: String, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun realpathSync(path: String, options: String? = definedExternally): dynamic /* String | Buffer */

external fun realpathSync(path: Buffer, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun realpathSync(path: Buffer, options: String? = definedExternally): dynamic /* String | Buffer */

external fun realpathSync(path: URL, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun realpathSync(path: URL, options: String? = definedExternally): dynamic /* String | Buffer */

external fun unlink(path: String, callback: NoParamCallback)

external fun unlink(path: Buffer, callback: NoParamCallback)

external fun unlink(path: URL, callback: NoParamCallback)

external fun unlinkSync(path: String)

external fun unlinkSync(path: Buffer)

external fun unlinkSync(path: URL)

external interface RmDirOptions {
    var recursive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RmDirAsyncOptions : RmDirOptions {
    var emfileWait: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxBusyTries: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external fun rmdir(path: String, callback: NoParamCallback)

external fun rmdir(path: Buffer, callback: NoParamCallback)

external fun rmdir(path: URL, callback: NoParamCallback)

external fun rmdir(path: String, options: RmDirAsyncOptions, callback: NoParamCallback)

external fun rmdir(path: Buffer, options: RmDirAsyncOptions, callback: NoParamCallback)

external fun rmdir(path: URL, options: RmDirAsyncOptions, callback: NoParamCallback)

external fun rmdirSync(path: String, options: RmDirOptions = definedExternally)

external fun rmdirSync(path: Buffer, options: RmDirOptions = definedExternally)

external fun rmdirSync(path: URL, options: RmDirOptions = definedExternally)

external interface MakeDirectoryOptions {
    var recursive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var mode: dynamic /* Number? | String? */
        get() = definedExternally
        set(value) = definedExternally
}

external fun mkdir(path: String, options: Number?, callback: NoParamCallback)

external fun mkdir(path: String, options: String?, callback: NoParamCallback)

external fun mkdir(path: String, options: MakeDirectoryOptions?, callback: NoParamCallback)

external fun mkdir(path: Buffer, options: Number?, callback: NoParamCallback)

external fun mkdir(path: Buffer, options: String?, callback: NoParamCallback)

external fun mkdir(path: Buffer, options: MakeDirectoryOptions?, callback: NoParamCallback)

external fun mkdir(path: URL, options: Number?, callback: NoParamCallback)

external fun mkdir(path: URL, options: String?, callback: NoParamCallback)

external fun mkdir(path: URL, options: MakeDirectoryOptions?, callback: NoParamCallback)

external fun mkdir(path: String, callback: NoParamCallback)

external fun mkdir(path: Buffer, callback: NoParamCallback)

external fun mkdir(path: URL, callback: NoParamCallback)

external fun mkdirSync(path: String, options: Number? = definedExternally)

external fun mkdirSync(path: String, options: String? = definedExternally)

external fun mkdirSync(path: String, options: MakeDirectoryOptions? = definedExternally)

external fun mkdirSync(path: Buffer, options: Number? = definedExternally)

external fun mkdirSync(path: Buffer, options: String? = definedExternally)

external fun mkdirSync(path: Buffer, options: MakeDirectoryOptions? = definedExternally)

external fun mkdirSync(path: URL, options: Number? = definedExternally)

external fun mkdirSync(path: URL, options: String? = definedExternally)

external fun mkdirSync(path: URL, options: MakeDirectoryOptions? = definedExternally)

external fun mkdtemp(prefix: String, options: `T$37`?, callback: (err: Exception?, folder: String) -> Unit)

external fun mkdtemp(
    prefix: String,
    options: String /* "ascii" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "utf8" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "utf-8" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "utf16le" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "ucs2" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "ucs-2" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "base64" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "latin1" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "binary" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "hex" */,
    callback: (err: Exception?, folder: String) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String /* "buffer" */,
    callback: (err: Exception?, folder: Buffer) -> Unit
)

external fun mkdtemp(prefix: String, options: `T$38`, callback: (err: Exception?, folder: Buffer) -> Unit)

external fun mkdtemp(
    prefix: String,
    options: `T$22`?,
    callback: (err: Exception?, folder: dynamic /* String | Buffer */) -> Unit
)

external fun mkdtemp(
    prefix: String,
    options: String?,
    callback: (err: Exception?, folder: dynamic /* String | Buffer */) -> Unit
)

external fun mkdtemp(prefix: String, callback: (err: Exception?, folder: String) -> Unit)

external fun mkdtempSync(prefix: String, options: `T$37`? = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "ascii" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "utf8" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "utf-8" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "utf16le" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "ucs2" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "ucs-2" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "base64" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "latin1" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "binary" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: String /* "hex" */ = definedExternally): String

external fun mkdtempSync(prefix: String, options: `T$38`): Buffer

external fun mkdtempSync(prefix: String, options: String /* "buffer" */): Buffer

external fun mkdtempSync(prefix: String, options: `T$22`? = definedExternally): dynamic /* String | Buffer */

external fun mkdtempSync(prefix: String, options: String? = definedExternally): dynamic /* String | Buffer */

external interface `T$39` {
    var encoding: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
    var withFileTypes: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun readdir(
    path: String,
    options: dynamic /* `T$39`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, files: Array<String>) -> Unit
)

external fun readdir(
    path: Buffer,
    options: dynamic /* `T$39`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, files: Array<String>) -> Unit
)

external fun readdir(
    path: URL,
    options: dynamic /* `T$39`? | String | String | String | String | String | String | String | String | String | String */,
    callback: (err: Exception?, files: Array<String>) -> Unit
)

external interface `T$40` {
    var encoding: String /* "buffer" */
    var withFileTypes: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun readdir(path: String, options: `T$40`, callback: (err: Exception?, files: Array<Buffer>) -> Unit)

external fun readdir(
    path: String,
    options: String /* "buffer" */,
    callback: (err: Exception?, files: Array<Buffer>) -> Unit
)

external fun readdir(path: Buffer, options: `T$40`, callback: (err: Exception?, files: Array<Buffer>) -> Unit)

external fun readdir(
    path: Buffer,
    options: String /* "buffer" */,
    callback: (err: Exception?, files: Array<Buffer>) -> Unit
)

external fun readdir(path: URL, options: `T$40`, callback: (err: Exception?, files: Array<Buffer>) -> Unit)

external fun readdir(
    path: URL,
    options: String /* "buffer" */,
    callback: (err: Exception?, files: Array<Buffer>) -> Unit
)

external interface `T$41` {
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var withFileTypes: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun readdir(
    path: String,
    options: `T$41`?,
    callback: (err: Exception?, files: dynamic /* Array<String> | Array<Buffer> */) -> Unit
)

external fun readdir(
    path: String,
    options: String?,
    callback: (err: Exception?, files: dynamic /* Array<String> | Array<Buffer> */) -> Unit
)

external fun readdir(
    path: Buffer,
    options: `T$41`?,
    callback: (err: Exception?, files: dynamic /* Array<String> | Array<Buffer> */) -> Unit
)

external fun readdir(
    path: Buffer,
    options: String?,
    callback: (err: Exception?, files: dynamic /* Array<String> | Array<Buffer> */) -> Unit
)

external fun readdir(
    path: URL,
    options: `T$41`?,
    callback: (err: Exception?, files: dynamic /* Array<String> | Array<Buffer> */) -> Unit
)

external fun readdir(
    path: URL,
    options: String?,
    callback: (err: Exception?, files: dynamic /* Array<String> | Array<Buffer> */) -> Unit
)

external fun readdir(path: String, callback: (err: Exception?, files: Array<String>) -> Unit)

external fun readdir(path: Buffer, callback: (err: Exception?, files: Array<String>) -> Unit)

external fun readdir(path: URL, callback: (err: Exception?, files: Array<String>) -> Unit)

external interface `T$42` {
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var withFileTypes: Boolean
}

external fun readdir(path: String, options: `T$42`, callback: (err: Exception?, files: Array<Dirent>) -> Unit)

external fun readdir(path: Buffer, options: `T$42`, callback: (err: Exception?, files: Array<Dirent>) -> Unit)

external fun readdir(path: URL, options: `T$42`, callback: (err: Exception?, files: Array<Dirent>) -> Unit)

external fun readdirSync(
    path: String,
    options: dynamic /* `T$39`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Array<String>

external fun readdirSync(
    path: Buffer,
    options: dynamic /* `T$39`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Array<String>

external fun readdirSync(
    path: URL,
    options: dynamic /* `T$39`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Array<String>

external fun readdirSync(path: String, options: `T$40`): Array<Buffer>

external fun readdirSync(path: String, options: String /* "buffer" */): Array<Buffer>

external fun readdirSync(path: Buffer, options: `T$40`): Array<Buffer>

external fun readdirSync(path: Buffer, options: String /* "buffer" */): Array<Buffer>

external fun readdirSync(path: URL, options: `T$40`): Array<Buffer>

external fun readdirSync(path: URL, options: String /* "buffer" */): Array<Buffer>

external fun readdirSync(
    path: String,
    options: `T$41`? = definedExternally
): dynamic /* Array<String> | Array<Buffer> */

external fun readdirSync(
    path: String,
    options: String? = definedExternally
): dynamic /* Array<String> | Array<Buffer> */

external fun readdirSync(
    path: Buffer,
    options: `T$41`? = definedExternally
): dynamic /* Array<String> | Array<Buffer> */

external fun readdirSync(
    path: Buffer,
    options: String? = definedExternally
): dynamic /* Array<String> | Array<Buffer> */

external fun readdirSync(path: URL, options: `T$41`? = definedExternally): dynamic /* Array<String> | Array<Buffer> */

external fun readdirSync(path: URL, options: String? = definedExternally): dynamic /* Array<String> | Array<Buffer> */

external fun readdirSync(path: String, options: `T$42`): Array<Dirent>

external fun readdirSync(path: Buffer, options: `T$42`): Array<Dirent>

external fun readdirSync(path: URL, options: `T$42`): Array<Dirent>

external fun close(fd: Number, callback: NoParamCallback)

external fun closeSync(fd: Number)

external fun open(path: String, flags: String, mode: String?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: String, flags: String, mode: Number?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: String, flags: Number, mode: String?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: String, flags: Number, mode: Number?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: Buffer, flags: String, mode: String?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: Buffer, flags: String, mode: Number?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: Buffer, flags: Number, mode: String?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: Buffer, flags: Number, mode: Number?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: URL, flags: String, mode: String?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: URL, flags: String, mode: Number?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: URL, flags: Number, mode: String?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: URL, flags: Number, mode: Number?, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: String, flags: String, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: String, flags: Number, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: Buffer, flags: String, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: Buffer, flags: Number, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: URL, flags: String, callback: (err: Exception?, fd: Number) -> Unit)

external fun open(path: URL, flags: Number, callback: (err: Exception?, fd: Number) -> Unit)

external fun openSync(path: String, flags: String, mode: String? = definedExternally): Number

external fun openSync(path: String, flags: String, mode: Number? = definedExternally): Number

external fun openSync(path: String, flags: Number, mode: String? = definedExternally): Number

external fun openSync(path: String, flags: Number, mode: Number? = definedExternally): Number

external fun openSync(path: Buffer, flags: String, mode: String? = definedExternally): Number

external fun openSync(path: Buffer, flags: String, mode: Number? = definedExternally): Number

external fun openSync(path: Buffer, flags: Number, mode: String? = definedExternally): Number

external fun openSync(path: Buffer, flags: Number, mode: Number? = definedExternally): Number

external fun openSync(path: URL, flags: String, mode: String? = definedExternally): Number

external fun openSync(path: URL, flags: String, mode: Number? = definedExternally): Number

external fun openSync(path: URL, flags: Number, mode: String? = definedExternally): Number

external fun openSync(path: URL, flags: Number, mode: Number? = definedExternally): Number

external fun utimes(path: String, atime: String, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimes(path: String, atime: Number, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimes(path: String, atime: Date, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimes(path: Buffer, atime: String, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimes(path: Buffer, atime: Number, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimes(path: Buffer, atime: Date, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimes(path: URL, atime: String, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimes(path: URL, atime: Number, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimes(path: URL, atime: Date, mtime: dynamic /* String | Number | Date */, callback: NoParamCallback)

external fun utimesSync(path: String, atime: String, mtime: dynamic /* String | Number | Date */)

external fun utimesSync(path: String, atime: Number, mtime: dynamic /* String | Number | Date */)

external fun utimesSync(path: String, atime: Date, mtime: dynamic /* String | Number | Date */)

external fun utimesSync(path: Buffer, atime: String, mtime: dynamic /* String | Number | Date */)

external fun utimesSync(path: Buffer, atime: Number, mtime: dynamic /* String | Number | Date */)

external fun utimesSync(path: Buffer, atime: Date, mtime: dynamic /* String | Number | Date */)

external fun utimesSync(path: URL, atime: String, mtime: dynamic /* String | Number | Date */)

external fun utimesSync(path: URL, atime: Number, mtime: dynamic /* String | Number | Date */)

external fun utimesSync(path: URL, atime: Date, mtime: dynamic /* String | Number | Date */)

external fun futimes(fd: Number, atime: String, mtime: String, callback: NoParamCallback)

external fun futimes(fd: Number, atime: String, mtime: Number, callback: NoParamCallback)

external fun futimes(fd: Number, atime: String, mtime: Date, callback: NoParamCallback)

external fun futimes(fd: Number, atime: Number, mtime: String, callback: NoParamCallback)

external fun futimes(fd: Number, atime: Number, mtime: Number, callback: NoParamCallback)

external fun futimes(fd: Number, atime: Number, mtime: Date, callback: NoParamCallback)

external fun futimes(fd: Number, atime: Date, mtime: String, callback: NoParamCallback)

external fun futimes(fd: Number, atime: Date, mtime: Number, callback: NoParamCallback)

external fun futimes(fd: Number, atime: Date, mtime: Date, callback: NoParamCallback)

external fun futimesSync(fd: Number, atime: String, mtime: String)

external fun futimesSync(fd: Number, atime: String, mtime: Number)

external fun futimesSync(fd: Number, atime: String, mtime: Date)

external fun futimesSync(fd: Number, atime: Number, mtime: String)

external fun futimesSync(fd: Number, atime: Number, mtime: Number)

external fun futimesSync(fd: Number, atime: Number, mtime: Date)

external fun futimesSync(fd: Number, atime: Date, mtime: String)

external fun futimesSync(fd: Number, atime: Date, mtime: Number)

external fun futimesSync(fd: Number, atime: Date, mtime: Date)

external fun fsync(fd: Number, callback: NoParamCallback)

external fun fsyncSync(fd: Number)

external fun <TBuffer> write(
    fd: Number,
    buffer: TBuffer,
    offset: Number?,
    length: Number?,
    position: Number?,
    callback: (err: Exception?, written: Number, buffer: TBuffer) -> Unit
)

external fun <TBuffer> write(
    fd: Number,
    buffer: TBuffer,
    offset: Number?,
    length: Number?,
    callback: (err: Exception?, written: Number, buffer: TBuffer) -> Unit
)

external fun <TBuffer> write(
    fd: Number,
    buffer: TBuffer,
    offset: Number?,
    callback: (err: Exception?, written: Number, buffer: TBuffer) -> Unit
)

external fun <TBuffer> write(
    fd: Number,
    buffer: TBuffer,
    callback: (err: Exception?, written: Number, buffer: TBuffer) -> Unit
)

external fun write(
    fd: Number,
    string: Any,
    position: Number?,
    encoding: String?,
    callback: (err: Exception?, written: Number, str: String) -> Unit
)

external fun write(
    fd: Number,
    string: Any,
    position: Number?,
    callback: (err: Exception?, written: Number, str: String) -> Unit
)

external fun write(fd: Number, string: Any, callback: (err: Exception?, written: Number, str: String) -> Unit)

external fun writeSync(
    fd: Number,
    buffer: Uint8Array,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: Uint8ClampedArray,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: Uint16Array,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: Uint32Array,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: Int8Array,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: Int16Array,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: Int32Array,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: Float32Array,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: Float64Array,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    buffer: DataView,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Number

external fun writeSync(
    fd: Number,
    string: Any,
    position: Number? = definedExternally,
    encoding: String? = definedExternally
): Number

external fun <TBuffer> read(
    fd: Number,
    buffer: TBuffer,
    offset: Number,
    length: Number,
    position: Number?,
    callback: (err: Exception?, bytesRead: Number, buffer: TBuffer) -> Unit
)

external fun readSync(fd: Number, buffer: Uint8Array, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: Uint8ClampedArray, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: Uint16Array, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: Uint32Array, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: Int8Array, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: Int16Array, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: Int32Array, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: Float32Array, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: Float64Array, offset: Number, length: Number, position: Number?): Number

external fun readSync(fd: Number, buffer: DataView, offset: Number, length: Number, position: Number?): Number

external interface `T$46` {
    var encoding: Any?
        get() = definedExternally
        set(value) = definedExternally
    var flag: String?
        get() = definedExternally
        set(value) = definedExternally
}

external fun readFile(path: String, options: `T$46`?, callback: (err: Exception?, data: Buffer) -> Unit)

external fun readFile(path: Buffer, options: `T$46`?, callback: (err: Exception?, data: Buffer) -> Unit)

external fun readFile(path: URL, options: `T$46`?, callback: (err: Exception?, data: Buffer) -> Unit)

external fun readFile(path: Number, options: `T$46`?, callback: (err: Exception?, data: Buffer) -> Unit)

external interface `T$47` {
    var encoding: String
    var flag: String?
        get() = definedExternally
        set(value) = definedExternally
}

external fun readFile(path: String, options: `T$47`, callback: (err: Exception?, data: String) -> Unit)

external fun readFile(path: String, options: String, callback: (err: Exception?, data: String) -> Unit)

external fun readFile(path: Buffer, options: `T$47`, callback: (err: Exception?, data: String) -> Unit)

external fun readFile(path: Buffer, options: String, callback: (err: Exception?, data: String) -> Unit)

external fun readFile(path: URL, options: `T$47`, callback: (err: Exception?, data: String) -> Unit)

external fun readFile(path: URL, options: String, callback: (err: Exception?, data: String) -> Unit)

external fun readFile(path: Number, options: `T$47`, callback: (err: Exception?, data: String) -> Unit)

external fun readFile(path: Number, options: String, callback: (err: Exception?, data: String) -> Unit)

external interface `T$48` {
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var flag: String?
        get() = definedExternally
        set(value) = definedExternally
}

external fun readFile(
    path: String,
    options: `T$48`?,
    callback: (err: Exception?, data: dynamic /* String | Buffer */) -> Unit
)

external fun readFile(
    path: String,
    options: String?,
    callback: (err: Exception?, data: dynamic /* String | Buffer */) -> Unit
)

external fun readFile(
    path: Buffer,
    options: `T$48`?,
    callback: (err: Exception?, data: dynamic /* String | Buffer */) -> Unit
)

external fun readFile(
    path: Buffer,
    options: String?,
    callback: (err: Exception?, data: dynamic /* String | Buffer */) -> Unit
)

external fun readFile(
    path: URL,
    options: `T$48`?,
    callback: (err: Exception?, data: dynamic /* String | Buffer */) -> Unit
)

external fun readFile(
    path: URL,
    options: String?,
    callback: (err: Exception?, data: dynamic /* String | Buffer */) -> Unit
)

external fun readFile(
    path: Number,
    options: `T$48`?,
    callback: (err: Exception?, data: dynamic /* String | Buffer */) -> Unit
)

external fun readFile(
    path: Number,
    options: String?,
    callback: (err: Exception?, data: dynamic /* String | Buffer */) -> Unit
)

external fun readFile(path: String, callback: (err: Exception?, data: Buffer) -> Unit)

external fun readFile(path: Buffer, callback: (err: Exception?, data: Buffer) -> Unit)

external fun readFile(path: URL, callback: (err: Exception?, data: Buffer) -> Unit)

external fun readFile(path: Number, callback: (err: Exception?, data: Buffer) -> Unit)

external fun readFileSync(path: String, options: `T$46`? = definedExternally): Buffer

external fun readFileSync(path: Buffer, options: `T$46`? = definedExternally): Buffer

external fun readFileSync(path: URL, options: `T$46`? = definedExternally): Buffer

external fun readFileSync(path: Number, options: `T$46`? = definedExternally): Buffer

external fun readFileSync(path: String, options: `T$47`): String

external fun readFileSync(path: String, options: String): String

external fun readFileSync(path: Buffer, options: `T$47`): String

external fun readFileSync(path: Buffer, options: String): String

external fun readFileSync(path: URL, options: `T$47`): String

external fun readFileSync(path: URL, options: String): String

external fun readFileSync(path: Number, options: `T$47`): String

external fun readFileSync(path: Number, options: String): String

external fun readFileSync(path: String, options: `T$48`? = definedExternally): dynamic /* String | Buffer */

external fun readFileSync(path: String, options: String? = definedExternally): dynamic /* String | Buffer */

external fun readFileSync(path: Buffer, options: `T$48`? = definedExternally): dynamic /* String | Buffer */

external fun readFileSync(path: Buffer, options: String? = definedExternally): dynamic /* String | Buffer */

external fun readFileSync(path: URL, options: `T$48`? = definedExternally): dynamic /* String | Buffer */

external fun readFileSync(path: URL, options: String? = definedExternally): dynamic /* String | Buffer */

external fun readFileSync(path: Number, options: `T$48`? = definedExternally): dynamic /* String | Buffer */

external fun readFileSync(path: Number, options: String? = definedExternally): dynamic /* String | Buffer */

external interface `T$49` {
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var mode: dynamic /* Number? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var flag: String?
        get() = definedExternally
        set(value) = definedExternally
}

external fun writeFile(path: String, data: Any, options: `T$49`?, callback: NoParamCallback)

external fun writeFile(path: String, data: Any, options: String?, callback: NoParamCallback)

external fun writeFile(path: Buffer, data: Any, options: `T$49`?, callback: NoParamCallback)

external fun writeFile(path: Buffer, data: Any, options: String?, callback: NoParamCallback)

external fun writeFile(path: URL, data: Any, options: `T$49`?, callback: NoParamCallback)

external fun writeFile(path: URL, data: Any, options: String?, callback: NoParamCallback)

external fun writeFile(path: Number, data: Any, options: `T$49`?, callback: NoParamCallback)

external fun writeFile(path: Number, data: Any, options: String?, callback: NoParamCallback)

external fun writeFile(path: String, data: Any, callback: NoParamCallback)

external fun writeFile(path: Buffer, data: Any, callback: NoParamCallback)

external fun writeFile(path: URL, data: Any, callback: NoParamCallback)

external fun writeFile(path: Number, data: Any, callback: NoParamCallback)

external fun writeFileSync(path: String, data: Any, options: `T$49`? = definedExternally)

external fun writeFileSync(path: String, data: Any, options: String? = definedExternally)

external fun writeFileSync(path: Buffer, data: Any, options: `T$49`? = definedExternally)

external fun writeFileSync(path: Buffer, data: Any, options: String? = definedExternally)

external fun writeFileSync(path: URL, data: Any, options: `T$49`? = definedExternally)

external fun writeFileSync(path: URL, data: Any, options: String? = definedExternally)

external fun writeFileSync(path: Number, data: Any, options: `T$49`? = definedExternally)

external fun writeFileSync(path: Number, data: Any, options: String? = definedExternally)

external fun appendFile(file: String, data: Any, options: `T$49`?, callback: NoParamCallback)

external fun appendFile(file: String, data: Any, options: String?, callback: NoParamCallback)

external fun appendFile(file: Buffer, data: Any, options: `T$49`?, callback: NoParamCallback)

external fun appendFile(file: Buffer, data: Any, options: String?, callback: NoParamCallback)

external fun appendFile(file: URL, data: Any, options: `T$49`?, callback: NoParamCallback)

external fun appendFile(file: URL, data: Any, options: String?, callback: NoParamCallback)

external fun appendFile(file: Number, data: Any, options: `T$49`?, callback: NoParamCallback)

external fun appendFile(file: Number, data: Any, options: String?, callback: NoParamCallback)

external fun appendFile(file: String, data: Any, callback: NoParamCallback)

external fun appendFile(file: Buffer, data: Any, callback: NoParamCallback)

external fun appendFile(file: URL, data: Any, callback: NoParamCallback)

external fun appendFile(file: Number, data: Any, callback: NoParamCallback)

external fun appendFileSync(file: String, data: Any, options: `T$49`? = definedExternally)

external fun appendFileSync(file: String, data: Any, options: String? = definedExternally)

external fun appendFileSync(file: Buffer, data: Any, options: `T$49`? = definedExternally)

external fun appendFileSync(file: Buffer, data: Any, options: String? = definedExternally)

external fun appendFileSync(file: URL, data: Any, options: `T$49`? = definedExternally)

external fun appendFileSync(file: URL, data: Any, options: String? = definedExternally)

external fun appendFileSync(file: Number, data: Any, options: `T$49`? = definedExternally)

external fun appendFileSync(file: Number, data: Any, options: String? = definedExternally)

external interface `T$50` {
    var persistent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var interval: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external fun watchFile(filename: String, options: `T$50`?, listener: (curr: Stats, prev: Stats) -> Unit)

external fun watchFile(filename: Buffer, options: `T$50`?, listener: (curr: Stats, prev: Stats) -> Unit)

external fun watchFile(filename: URL, options: `T$50`?, listener: (curr: Stats, prev: Stats) -> Unit)

external fun watchFile(filename: String, listener: (curr: Stats, prev: Stats) -> Unit)

external fun watchFile(filename: Buffer, listener: (curr: Stats, prev: Stats) -> Unit)

external fun watchFile(filename: URL, listener: (curr: Stats, prev: Stats) -> Unit)

external fun unwatchFile(filename: String, listener: (curr: Stats, prev: Stats) -> Unit = definedExternally)

external fun unwatchFile(filename: Buffer, listener: (curr: Stats, prev: Stats) -> Unit = definedExternally)

external fun unwatchFile(filename: URL, listener: (curr: Stats, prev: Stats) -> Unit = definedExternally)

external interface `T$51` {
    var encoding: String? /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
        get() = definedExternally
        set(value) = definedExternally
    var persistent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var recursive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun watch(
    filename: String,
    options: dynamic /* `T$51`? | String | String | String | String | String | String | String | String | String | String */,
    listener: (event: String, filename: String) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: Buffer,
    options: dynamic /* `T$51`? | String | String | String | String | String | String | String | String | String | String */,
    listener: (event: String, filename: String) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: URL,
    options: dynamic /* `T$51`? | String | String | String | String | String | String | String | String | String | String */,
    listener: (event: String, filename: String) -> Unit = definedExternally
): FSWatcher

external interface `T$52` {
    var encoding: String /* "buffer" */
    var persistent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var recursive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun watch(
    filename: String,
    options: `T$52`,
    listener: (event: String, filename: Buffer) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: String,
    options: String /* "buffer" */,
    listener: (event: String, filename: Buffer) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: Buffer,
    options: `T$52`,
    listener: (event: String, filename: Buffer) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: Buffer,
    options: String /* "buffer" */,
    listener: (event: String, filename: Buffer) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: URL,
    options: `T$52`,
    listener: (event: String, filename: Buffer) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: URL,
    options: String /* "buffer" */,
    listener: (event: String, filename: Buffer) -> Unit = definedExternally
): FSWatcher

external interface `T$53` {
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var persistent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var recursive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun watch(
    filename: String,
    options: `T$53`?,
    listener: (event: String, filename: dynamic /* String | Buffer */) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: String,
    options: String?,
    listener: (event: String, filename: dynamic /* String | Buffer */) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: Buffer,
    options: `T$53`?,
    listener: (event: String, filename: dynamic /* String | Buffer */) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: Buffer,
    options: String?,
    listener: (event: String, filename: dynamic /* String | Buffer */) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: URL,
    options: `T$53`?,
    listener: (event: String, filename: dynamic /* String | Buffer */) -> Unit = definedExternally
): FSWatcher

external fun watch(
    filename: URL,
    options: String?,
    listener: (event: String, filename: dynamic /* String | Buffer */) -> Unit = definedExternally
): FSWatcher

external fun watch(filename: String, listener: (event: String, filename: String) -> Any = definedExternally): FSWatcher

external fun watch(filename: Buffer, listener: (event: String, filename: String) -> Any = definedExternally): FSWatcher

external fun watch(filename: URL, listener: (event: String, filename: String) -> Any = definedExternally): FSWatcher

external fun exists(path: String, callback: (exists: Boolean) -> Unit)

external fun exists(path: Buffer, callback: (exists: Boolean) -> Unit)

external fun exists(path: URL, callback: (exists: Boolean) -> Unit)

external fun existsSync(path: String): Boolean

external fun existsSync(path: Buffer): Boolean

external fun existsSync(path: URL): Boolean

external fun access(path: String, mode: Number?, callback: NoParamCallback)

external fun access(path: Buffer, mode: Number?, callback: NoParamCallback)

external fun access(path: URL, mode: Number?, callback: NoParamCallback)

external fun access(path: String, callback: NoParamCallback)

external fun access(path: Buffer, callback: NoParamCallback)

external fun access(path: URL, callback: NoParamCallback)

external fun accessSync(path: String, mode: Number = definedExternally)

external fun accessSync(path: Buffer, mode: Number = definedExternally)

external fun accessSync(path: URL, mode: Number = definedExternally)

external interface `T$54` {
    var flags: String?
        get() = definedExternally
        set(value) = definedExternally
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var fd: Number?
        get() = definedExternally
        set(value) = definedExternally
    var mode: Number?
        get() = definedExternally
        set(value) = definedExternally
    var autoClose: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var emitClose: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var start: Number?
        get() = definedExternally
        set(value) = definedExternally
    var end: Number?
        get() = definedExternally
        set(value) = definedExternally
    var highWaterMark: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external fun createReadStream(path: String, options: String = definedExternally): ReadStream

external fun createReadStream(path: String, options: `T$54` = definedExternally): ReadStream

external fun createReadStream(path: Buffer, options: String = definedExternally): ReadStream

external fun createReadStream(path: Buffer, options: `T$54` = definedExternally): ReadStream

external fun createReadStream(path: URL, options: String = definedExternally): ReadStream

external fun createReadStream(path: URL, options: `T$54` = definedExternally): ReadStream

external interface `T$55` {
    var flags: String?
        get() = definedExternally
        set(value) = definedExternally
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var fd: Number?
        get() = definedExternally
        set(value) = definedExternally
    var mode: Number?
        get() = definedExternally
        set(value) = definedExternally
    var autoClose: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var emitClose: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var start: Number?
        get() = definedExternally
        set(value) = definedExternally
    var highWaterMark: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external fun createWriteStream(path: String, options: String = definedExternally): WriteStream

external fun createWriteStream(path: String, options: `T$55` = definedExternally): WriteStream

external fun createWriteStream(path: Buffer, options: String = definedExternally): WriteStream

external fun createWriteStream(path: Buffer, options: `T$55` = definedExternally): WriteStream

external fun createWriteStream(path: URL, options: String = definedExternally): WriteStream

external fun createWriteStream(path: URL, options: `T$55` = definedExternally): WriteStream

external fun fdatasync(fd: Number, callback: NoParamCallback)

external fun fdatasyncSync(fd: Number)

external fun copyFile(src: String, dest: String, callback: NoParamCallback)

external fun copyFile(src: String, dest: Buffer, callback: NoParamCallback)

external fun copyFile(src: String, dest: URL, callback: NoParamCallback)

external fun copyFile(src: Buffer, dest: String, callback: NoParamCallback)

external fun copyFile(src: Buffer, dest: Buffer, callback: NoParamCallback)

external fun copyFile(src: Buffer, dest: URL, callback: NoParamCallback)

external fun copyFile(src: URL, dest: String, callback: NoParamCallback)

external fun copyFile(src: URL, dest: Buffer, callback: NoParamCallback)

external fun copyFile(src: URL, dest: URL, callback: NoParamCallback)

external fun copyFile(src: String, dest: String, flags: Number, callback: NoParamCallback)

external fun copyFile(src: String, dest: Buffer, flags: Number, callback: NoParamCallback)

external fun copyFile(src: String, dest: URL, flags: Number, callback: NoParamCallback)

external fun copyFile(src: Buffer, dest: String, flags: Number, callback: NoParamCallback)

external fun copyFile(src: Buffer, dest: Buffer, flags: Number, callback: NoParamCallback)

external fun copyFile(src: Buffer, dest: URL, flags: Number, callback: NoParamCallback)

external fun copyFile(src: URL, dest: String, flags: Number, callback: NoParamCallback)

external fun copyFile(src: URL, dest: Buffer, flags: Number, callback: NoParamCallback)

external fun copyFile(src: URL, dest: URL, flags: Number, callback: NoParamCallback)

external fun copyFileSync(src: String, dest: String, flags: Number = definedExternally)

external fun copyFileSync(src: String, dest: Buffer, flags: Number = definedExternally)

external fun copyFileSync(src: String, dest: URL, flags: Number = definedExternally)

external fun copyFileSync(src: Buffer, dest: String, flags: Number = definedExternally)

external fun copyFileSync(src: Buffer, dest: Buffer, flags: Number = definedExternally)

external fun copyFileSync(src: Buffer, dest: URL, flags: Number = definedExternally)

external fun copyFileSync(src: URL, dest: String, flags: Number = definedExternally)

external fun copyFileSync(src: URL, dest: Buffer, flags: Number = definedExternally)

external fun copyFileSync(src: URL, dest: URL, flags: Number = definedExternally)

external fun writev(
    fd: Number,
    buffers: Array<dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */>,
    cb: (err: Exception?, bytesWritten: Number, buffers: Array<dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */>) -> Unit
)

external fun writev(
    fd: Number,
    buffers: Array<dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */>,
    position: Number,
    cb: (err: Exception?, bytesWritten: Number, buffers: Array<dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */>) -> Unit
)

external interface WriteVResult {
    var bytesWritten: Number
    var buffers: Array<dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */>
}

external fun writevSync(
    fd: Number,
    buffers: Array<dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */>,
    position: Number = definedExternally
): Number

external interface OpenDirOptions {
    var encoding: String? /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
        get() = definedExternally
        set(value) = definedExternally
}

external fun opendirSync(path: String, options: OpenDirOptions = definedExternally): Dir

external fun opendir(path: String, cb: (err: Exception?, dir: Dir) -> Unit)

external fun opendir(path: String, options: OpenDirOptions, cb: (err: Exception?, dir: Dir) -> Unit)

external open class BigIntStats : StatsBase<Any> {
    override fun isFile(): Boolean
    override fun isDirectory(): Boolean
    override fun isBlockDevice(): Boolean
    override fun isCharacterDevice(): Boolean
    override fun isSymbolicLink(): Boolean
    override fun isFIFO(): Boolean
    override fun isSocket(): Boolean
    override var dev: Any
    override var ino: Any
    override var mode: Any
    override var nlink: Any
    override var uid: Any
    override var gid: Any
    override var rdev: Any
    override var size: Any
    override var blksize: Any
    override var blocks: Any
    override var atimeMs: Any
    override var mtimeMs: Any
    override var ctimeMs: Any
    override var birthtimeMs: Any
    override var atime: Date
    override var mtime: Date
    override var ctime: Date
    override var birthtime: Date
    open var atimeNs: Any
    open var mtimeNs: Any
    open var ctimeNs: Any
    open var birthtimeNs: Any
}

external interface BigIntOptions {
    var bigint: Boolean
}

external interface StatOptions {
    var bigint: Boolean
}

external fun stat(path: String, options: BigIntOptions, callback: (err: Exception?, stats: BigIntStats) -> Unit)

external fun stat(path: Buffer, options: BigIntOptions, callback: (err: Exception?, stats: BigIntStats) -> Unit)

external fun stat(path: URL, options: BigIntOptions, callback: (err: Exception?, stats: BigIntStats) -> Unit)

external fun stat(
    path: String,
    options: StatOptions,
    callback: (err: Exception?, stats: dynamic /* Stats | BigIntStats */) -> Unit
)

external fun stat(
    path: Buffer,
    options: StatOptions,
    callback: (err: Exception?, stats: dynamic /* Stats | BigIntStats */) -> Unit
)

external fun stat(
    path: URL,
    options: StatOptions,
    callback: (err: Exception?, stats: dynamic /* Stats | BigIntStats */) -> Unit
)

external fun statSync(path: String, options: BigIntOptions): BigIntStats

external fun statSync(path: Buffer, options: BigIntOptions): BigIntStats

external fun statSync(path: URL, options: BigIntOptions): BigIntStats

external fun statSync(path: String, options: StatOptions): dynamic /* Stats | BigIntStats */

external fun statSync(path: Buffer, options: StatOptions): dynamic /* Stats | BigIntStats */

external fun statSync(path: URL, options: StatOptions): dynamic /* Stats | BigIntStats */