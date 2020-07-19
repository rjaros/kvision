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

@file:JsQualifier("pl.treksoft.kvision.electron.fs.promises")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.fs.promises

import node.buffer.Buffer
import org.khronos.webgl.Uint8Array
import pl.treksoft.kvision.electron.child_process.`T$22`
import pl.treksoft.kvision.electron.fs.*
import pl.treksoft.kvision.electron.url.URL
import kotlin.js.Date
import kotlin.js.Promise

external interface `T$56` {
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var mode: dynamic /* String? | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var flag: dynamic /* String? | Number? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$57` {
    var encoding: Any?
        get() = definedExternally
        set(value) = definedExternally
    var flag: dynamic /* String? | Number? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$58` {
    var encoding: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
    var flag: dynamic /* String? | Number? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$59` {
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var flag: dynamic /* String? | Number? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface FileHandle {
    var fd: Number
    fun appendFile(data: Any, options: `T$56`? = definedExternally): Promise<Unit>
    fun appendFile(data: Any, options: String? = definedExternally): Promise<Unit>
    fun chown(uid: Number, gid: Number): Promise<Unit>
    fun chmod(mode: String): Promise<Unit>
    fun chmod(mode: Number): Promise<Unit>
    fun datasync(): Promise<Unit>
    fun sync(): Promise<Unit>
    fun <TBuffer : Uint8Array> read(
        buffer: TBuffer,
        offset: Number? = definedExternally,
        length: Number? = definedExternally,
        position: Number? = definedExternally
    ): Promise<dynamic>

    fun readFile(options: `T$57`? = definedExternally): Promise<Buffer>
    fun readFile(options: `T$58`): Promise<String>
    fun readFile(options: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */): Promise<String>
    fun readFile(options: `T$59`? = definedExternally): Promise<dynamic /* String | Buffer */>
    fun readFile(options: String? = definedExternally): Promise<dynamic /* String | Buffer */>
    fun stat(): Promise<Stats>
    fun truncate(len: Number = definedExternally): Promise<Unit>
    fun utimes(atime: String, mtime: String): Promise<Unit>
    fun utimes(atime: String, mtime: Number): Promise<Unit>
    fun utimes(atime: String, mtime: Date): Promise<Unit>
    fun utimes(atime: Number, mtime: String): Promise<Unit>
    fun utimes(atime: Number, mtime: Number): Promise<Unit>
    fun utimes(atime: Number, mtime: Date): Promise<Unit>
    fun utimes(atime: Date, mtime: String): Promise<Unit>
    fun utimes(atime: Date, mtime: Number): Promise<Unit>
    fun utimes(atime: Date, mtime: Date): Promise<Unit>
    fun <TBuffer : Uint8Array> write(
        buffer: TBuffer,
        offset: Number? = definedExternally,
        length: Number? = definedExternally,
        position: Number? = definedExternally
    ): Promise<dynamic>

    fun write(data: Any, position: Number? = definedExternally, encoding: String? = definedExternally): Promise<dynamic>
    fun writeFile(data: Any, options: `T$56`? = definedExternally): Promise<Unit>
    fun writeFile(data: Any, options: String? = definedExternally): Promise<Unit>
    fun writev(
        buffers: Array<dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */>,
        position: Number = definedExternally
    ): Promise<WriteVResult>

    fun close(): Promise<Unit>
}

external fun access(path: String, mode: Number = definedExternally): Promise<Unit>

external fun access(path: Buffer, mode: Number = definedExternally): Promise<Unit>

external fun access(path: URL, mode: Number = definedExternally): Promise<Unit>

external fun copyFile(src: String, dest: String, flags: Number = definedExternally): Promise<Unit>

external fun copyFile(src: String, dest: Buffer, flags: Number = definedExternally): Promise<Unit>

external fun copyFile(src: String, dest: URL, flags: Number = definedExternally): Promise<Unit>

external fun copyFile(src: Buffer, dest: String, flags: Number = definedExternally): Promise<Unit>

external fun copyFile(src: Buffer, dest: Buffer, flags: Number = definedExternally): Promise<Unit>

external fun copyFile(src: Buffer, dest: URL, flags: Number = definedExternally): Promise<Unit>

external fun copyFile(src: URL, dest: String, flags: Number = definedExternally): Promise<Unit>

external fun copyFile(src: URL, dest: Buffer, flags: Number = definedExternally): Promise<Unit>

external fun copyFile(src: URL, dest: URL, flags: Number = definedExternally): Promise<Unit>

external fun open(path: String, flags: String, mode: String = definedExternally): Promise<FileHandle>

external fun open(path: String, flags: String, mode: Number = definedExternally): Promise<FileHandle>

external fun open(path: String, flags: Number, mode: String = definedExternally): Promise<FileHandle>

external fun open(path: String, flags: Number, mode: Number = definedExternally): Promise<FileHandle>

external fun open(path: Buffer, flags: String, mode: String = definedExternally): Promise<FileHandle>

external fun open(path: Buffer, flags: String, mode: Number = definedExternally): Promise<FileHandle>

external fun open(path: Buffer, flags: Number, mode: String = definedExternally): Promise<FileHandle>

external fun open(path: Buffer, flags: Number, mode: Number = definedExternally): Promise<FileHandle>

external fun open(path: URL, flags: String, mode: String = definedExternally): Promise<FileHandle>

external fun open(path: URL, flags: String, mode: Number = definedExternally): Promise<FileHandle>

external fun open(path: URL, flags: Number, mode: String = definedExternally): Promise<FileHandle>

external fun open(path: URL, flags: Number, mode: Number = definedExternally): Promise<FileHandle>

external fun <TBuffer : Uint8Array> read(
    handle: FileHandle,
    buffer: TBuffer,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Promise<dynamic>

external fun <TBuffer : Uint8Array> write(
    handle: FileHandle,
    buffer: TBuffer,
    offset: Number? = definedExternally,
    length: Number? = definedExternally,
    position: Number? = definedExternally
): Promise<dynamic>

external fun write(
    handle: FileHandle,
    string: Any,
    position: Number? = definedExternally,
    encoding: String? = definedExternally
): Promise<dynamic>

external fun rename(oldPath: String, newPath: String): Promise<Unit>

external fun rename(oldPath: String, newPath: Buffer): Promise<Unit>

external fun rename(oldPath: String, newPath: URL): Promise<Unit>

external fun rename(oldPath: Buffer, newPath: String): Promise<Unit>

external fun rename(oldPath: Buffer, newPath: Buffer): Promise<Unit>

external fun rename(oldPath: Buffer, newPath: URL): Promise<Unit>

external fun rename(oldPath: URL, newPath: String): Promise<Unit>

external fun rename(oldPath: URL, newPath: Buffer): Promise<Unit>

external fun rename(oldPath: URL, newPath: URL): Promise<Unit>

external fun truncate(path: String, len: Number = definedExternally): Promise<Unit>

external fun truncate(path: Buffer, len: Number = definedExternally): Promise<Unit>

external fun truncate(path: URL, len: Number = definedExternally): Promise<Unit>

external fun ftruncate(handle: FileHandle, len: Number = definedExternally): Promise<Unit>

external fun rmdir(path: String, options: RmDirAsyncOptions = definedExternally): Promise<Unit>

external fun rmdir(path: Buffer, options: RmDirAsyncOptions = definedExternally): Promise<Unit>

external fun rmdir(path: URL, options: RmDirAsyncOptions = definedExternally): Promise<Unit>

external fun fdatasync(handle: FileHandle): Promise<Unit>

external fun fsync(handle: FileHandle): Promise<Unit>

external fun mkdir(path: String, options: Number? = definedExternally): Promise<Unit>

external fun mkdir(path: String, options: String? = definedExternally): Promise<Unit>

external fun mkdir(path: String, options: MakeDirectoryOptions? = definedExternally): Promise<Unit>

external fun mkdir(path: Buffer, options: Number? = definedExternally): Promise<Unit>

external fun mkdir(path: Buffer, options: String? = definedExternally): Promise<Unit>

external fun mkdir(path: Buffer, options: MakeDirectoryOptions? = definedExternally): Promise<Unit>

external fun mkdir(path: URL, options: Number? = definedExternally): Promise<Unit>

external fun mkdir(path: URL, options: String? = definedExternally): Promise<Unit>

external fun mkdir(path: URL, options: MakeDirectoryOptions? = definedExternally): Promise<Unit>

external interface `T$60` {
    var encoding: String? /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
        get() = definedExternally
        set(value) = definedExternally
    var withFileTypes: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun readdir(
    path: String,
    options: dynamic /* `T$60`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<Array<String>>

external fun readdir(
    path: Buffer,
    options: dynamic /* `T$60`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<Array<String>>

external fun readdir(
    path: URL,
    options: dynamic /* `T$60`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<Array<String>>

external fun readdir(path: String, options: `T$40`): Promise<Array<Buffer>>

external fun readdir(path: String, options: String /* "buffer" */): Promise<Array<Buffer>>

external fun readdir(path: Buffer, options: `T$40`): Promise<Array<Buffer>>

external fun readdir(path: Buffer, options: String /* "buffer" */): Promise<Array<Buffer>>

external fun readdir(path: URL, options: `T$40`): Promise<Array<Buffer>>

external fun readdir(path: URL, options: String /* "buffer" */): Promise<Array<Buffer>>

external fun readdir(
    path: String,
    options: `T$41`? = definedExternally
): Promise<dynamic /* Array<String> | Array<Buffer> */>

external fun readdir(
    path: String,
    options: String? = definedExternally
): Promise<dynamic /* Array<String> | Array<Buffer> */>

external fun readdir(
    path: Buffer,
    options: `T$41`? = definedExternally
): Promise<dynamic /* Array<String> | Array<Buffer> */>

external fun readdir(
    path: Buffer,
    options: String? = definedExternally
): Promise<dynamic /* Array<String> | Array<Buffer> */>

external fun readdir(
    path: URL,
    options: `T$41`? = definedExternally
): Promise<dynamic /* Array<String> | Array<Buffer> */>

external fun readdir(
    path: URL,
    options: String? = definedExternally
): Promise<dynamic /* Array<String> | Array<Buffer> */>

external fun readdir(path: String, options: `T$42`): Promise<Array<Dirent>>

external fun readdir(path: Buffer, options: `T$42`): Promise<Array<Dirent>>

external fun readdir(path: URL, options: `T$42`): Promise<Array<Dirent>>

external fun readlink(
    path: String,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<String>

external fun readlink(
    path: Buffer,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<String>

external fun readlink(
    path: URL,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<String>

external fun readlink(path: String, options: `T$38`): Promise<Buffer>

external fun readlink(path: String, options: String /* "buffer" */): Promise<Buffer>

external fun readlink(path: Buffer, options: `T$38`): Promise<Buffer>

external fun readlink(path: Buffer, options: String /* "buffer" */): Promise<Buffer>

external fun readlink(path: URL, options: `T$38`): Promise<Buffer>

external fun readlink(path: URL, options: String /* "buffer" */): Promise<Buffer>

external fun readlink(path: String, options: `T$22`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readlink(path: String, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readlink(path: Buffer, options: `T$22`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readlink(path: Buffer, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readlink(path: URL, options: `T$22`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readlink(path: URL, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun symlink(target: String, path: String, type: String? = definedExternally): Promise<Unit>

external fun symlink(target: String, path: Buffer, type: String? = definedExternally): Promise<Unit>

external fun symlink(target: String, path: URL, type: String? = definedExternally): Promise<Unit>

external fun symlink(target: Buffer, path: String, type: String? = definedExternally): Promise<Unit>

external fun symlink(target: Buffer, path: Buffer, type: String? = definedExternally): Promise<Unit>

external fun symlink(target: Buffer, path: URL, type: String? = definedExternally): Promise<Unit>

external fun symlink(target: URL, path: String, type: String? = definedExternally): Promise<Unit>

external fun symlink(target: URL, path: Buffer, type: String? = definedExternally): Promise<Unit>

external fun symlink(target: URL, path: URL, type: String? = definedExternally): Promise<Unit>

external fun fstat(handle: FileHandle): Promise<Stats>

external fun lstat(path: String): Promise<Stats>

external fun lstat(path: Buffer): Promise<Stats>

external fun lstat(path: URL): Promise<Stats>

external fun stat(path: String): Promise<Stats>

external fun stat(path: Buffer): Promise<Stats>

external fun stat(path: URL): Promise<Stats>

external fun link(existingPath: String, newPath: String): Promise<Unit>

external fun link(existingPath: String, newPath: Buffer): Promise<Unit>

external fun link(existingPath: String, newPath: URL): Promise<Unit>

external fun link(existingPath: Buffer, newPath: String): Promise<Unit>

external fun link(existingPath: Buffer, newPath: Buffer): Promise<Unit>

external fun link(existingPath: Buffer, newPath: URL): Promise<Unit>

external fun link(existingPath: URL, newPath: String): Promise<Unit>

external fun link(existingPath: URL, newPath: Buffer): Promise<Unit>

external fun link(existingPath: URL, newPath: URL): Promise<Unit>

external fun unlink(path: String): Promise<Unit>

external fun unlink(path: Buffer): Promise<Unit>

external fun unlink(path: URL): Promise<Unit>

external fun fchmod(handle: FileHandle, mode: String): Promise<Unit>

external fun fchmod(handle: FileHandle, mode: Number): Promise<Unit>

external fun chmod(path: String, mode: String): Promise<Unit>

external fun chmod(path: String, mode: Number): Promise<Unit>

external fun chmod(path: Buffer, mode: String): Promise<Unit>

external fun chmod(path: Buffer, mode: Number): Promise<Unit>

external fun chmod(path: URL, mode: String): Promise<Unit>

external fun chmod(path: URL, mode: Number): Promise<Unit>

external fun lchmod(path: String, mode: String): Promise<Unit>

external fun lchmod(path: String, mode: Number): Promise<Unit>

external fun lchmod(path: Buffer, mode: String): Promise<Unit>

external fun lchmod(path: Buffer, mode: Number): Promise<Unit>

external fun lchmod(path: URL, mode: String): Promise<Unit>

external fun lchmod(path: URL, mode: Number): Promise<Unit>

external fun lchown(path: String, uid: Number, gid: Number): Promise<Unit>

external fun lchown(path: Buffer, uid: Number, gid: Number): Promise<Unit>

external fun lchown(path: URL, uid: Number, gid: Number): Promise<Unit>

external fun fchown(handle: FileHandle, uid: Number, gid: Number): Promise<Unit>

external fun chown(path: String, uid: Number, gid: Number): Promise<Unit>

external fun chown(path: Buffer, uid: Number, gid: Number): Promise<Unit>

external fun chown(path: URL, uid: Number, gid: Number): Promise<Unit>

external fun utimes(path: String, atime: String, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun utimes(path: String, atime: Number, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun utimes(path: String, atime: Date, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun utimes(path: Buffer, atime: String, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun utimes(path: Buffer, atime: Number, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun utimes(path: Buffer, atime: Date, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun utimes(path: URL, atime: String, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun utimes(path: URL, atime: Number, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun utimes(path: URL, atime: Date, mtime: dynamic /* String | Number | Date */): Promise<Unit>

external fun futimes(handle: FileHandle, atime: String, mtime: String): Promise<Unit>

external fun futimes(handle: FileHandle, atime: String, mtime: Number): Promise<Unit>

external fun futimes(handle: FileHandle, atime: String, mtime: Date): Promise<Unit>

external fun futimes(handle: FileHandle, atime: Number, mtime: String): Promise<Unit>

external fun futimes(handle: FileHandle, atime: Number, mtime: Number): Promise<Unit>

external fun futimes(handle: FileHandle, atime: Number, mtime: Date): Promise<Unit>

external fun futimes(handle: FileHandle, atime: Date, mtime: String): Promise<Unit>

external fun futimes(handle: FileHandle, atime: Date, mtime: Number): Promise<Unit>

external fun futimes(handle: FileHandle, atime: Date, mtime: Date): Promise<Unit>

external fun realpath(
    path: String,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<String>

external fun realpath(
    path: Buffer,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<String>

external fun realpath(
    path: URL,
    options: dynamic /* `T$37`? | String | String | String | String | String | String | String | String | String | String */ = definedExternally
): Promise<String>

external fun realpath(path: String, options: `T$38`): Promise<Buffer>

external fun realpath(path: String, options: String /* "buffer" */): Promise<Buffer>

external fun realpath(path: Buffer, options: `T$38`): Promise<Buffer>

external fun realpath(path: Buffer, options: String /* "buffer" */): Promise<Buffer>

external fun realpath(path: URL, options: `T$38`): Promise<Buffer>

external fun realpath(path: URL, options: String /* "buffer" */): Promise<Buffer>

external fun realpath(path: String, options: `T$22`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun realpath(path: String, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun realpath(path: Buffer, options: `T$22`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun realpath(path: Buffer, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun realpath(path: URL, options: `T$22`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun realpath(path: URL, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun mkdtemp(prefix: String, options: `T$37`? = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "ascii" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "utf8" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "utf-8" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "utf16le" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "ucs2" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "ucs-2" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "base64" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "latin1" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "binary" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: String /* "hex" */ = definedExternally): Promise<String>

external fun mkdtemp(prefix: String, options: `T$38`): Promise<Buffer>

external fun mkdtemp(prefix: String, options: String /* "buffer" */): Promise<Buffer>

external fun mkdtemp(prefix: String, options: `T$22`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun mkdtemp(prefix: String, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun writeFile(path: String, data: Any, options: `T$56`? = definedExternally): Promise<Unit>

external fun writeFile(path: String, data: Any, options: String? = definedExternally): Promise<Unit>

external fun writeFile(path: Buffer, data: Any, options: `T$56`? = definedExternally): Promise<Unit>

external fun writeFile(path: Buffer, data: Any, options: String? = definedExternally): Promise<Unit>

external fun writeFile(path: URL, data: Any, options: `T$56`? = definedExternally): Promise<Unit>

external fun writeFile(path: URL, data: Any, options: String? = definedExternally): Promise<Unit>

external fun writeFile(path: FileHandle, data: Any, options: `T$56`? = definedExternally): Promise<Unit>

external fun writeFile(path: FileHandle, data: Any, options: String? = definedExternally): Promise<Unit>

external fun appendFile(path: String, data: Any, options: `T$56`? = definedExternally): Promise<Unit>

external fun appendFile(path: String, data: Any, options: String? = definedExternally): Promise<Unit>

external fun appendFile(path: Buffer, data: Any, options: `T$56`? = definedExternally): Promise<Unit>

external fun appendFile(path: Buffer, data: Any, options: String? = definedExternally): Promise<Unit>

external fun appendFile(path: URL, data: Any, options: `T$56`? = definedExternally): Promise<Unit>

external fun appendFile(path: URL, data: Any, options: String? = definedExternally): Promise<Unit>

external fun appendFile(path: FileHandle, data: Any, options: `T$56`? = definedExternally): Promise<Unit>

external fun appendFile(path: FileHandle, data: Any, options: String? = definedExternally): Promise<Unit>

external fun readFile(path: String, options: `T$57`? = definedExternally): Promise<Buffer>

external fun readFile(path: Buffer, options: `T$57`? = definedExternally): Promise<Buffer>

external fun readFile(path: URL, options: `T$57`? = definedExternally): Promise<Buffer>

external fun readFile(path: FileHandle, options: `T$57`? = definedExternally): Promise<Buffer>

external fun readFile(
    path: String,
    options: dynamic /* `T$58` | String | String | String | String | String | String | String | String | String | String */
): Promise<String>

external fun readFile(
    path: Buffer,
    options: dynamic /* `T$58` | String | String | String | String | String | String | String | String | String | String */
): Promise<String>

external fun readFile(
    path: URL,
    options: dynamic /* `T$58` | String | String | String | String | String | String | String | String | String | String */
): Promise<String>

external fun readFile(
    path: FileHandle,
    options: dynamic /* `T$58` | String | String | String | String | String | String | String | String | String | String */
): Promise<String>

external fun readFile(path: String, options: `T$59`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readFile(path: String, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readFile(path: Buffer, options: `T$59`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readFile(path: Buffer, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readFile(path: URL, options: `T$59`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readFile(path: URL, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readFile(path: FileHandle, options: `T$59`? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun readFile(path: FileHandle, options: String? = definedExternally): Promise<dynamic /* String | Buffer */>

external fun opendir(path: String, options: OpenDirOptions = definedExternally): Promise<Dir>