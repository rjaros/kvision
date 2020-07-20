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

@file:JsModule("zlib")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.zlib

import node.buffer.Buffer
import org.khronos.webgl.*
import pl.treksoft.kvision.electron.stream.internal.Transform

external interface ZlibOptions {
    var flush: Number?
        get() = definedExternally
        set(value) = definedExternally
    var finishFlush: Number?
        get() = definedExternally
        set(value) = definedExternally
    var chunkSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var windowBits: Number?
        get() = definedExternally
        set(value) = definedExternally
    var level: Number?
        get() = definedExternally
        set(value) = definedExternally
    var memLevel: Number?
        get() = definedExternally
        set(value) = definedExternally
    var strategy: Number?
        get() = definedExternally
        set(value) = definedExternally
    var dictionary: dynamic /* Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? | ArrayBuffer? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$82` {
    @nativeGetter
    operator fun get(key: Number): dynamic /* Boolean? | Number? */

    @nativeSetter
    operator fun set(key: Number, value: Boolean)

    @nativeSetter
    operator fun set(key: Number, value: Number)
}

external interface BrotliOptions {
    var flush: Number?
        get() = definedExternally
        set(value) = definedExternally
    var finishFlush: Number?
        get() = definedExternally
        set(value) = definedExternally
    var chunkSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var params: `T$82`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Zlib {
    var bytesRead: Number
    var bytesWritten: Number
    var shell: dynamic /* Boolean? | String? */
        get() = definedExternally
        set(value) = definedExternally

    fun close(callback: () -> Unit = definedExternally)
    fun flush(kind: Number = definedExternally, callback: () -> Unit = definedExternally)
    fun flush(kind: () -> Unit = definedExternally, callback: () -> Unit = definedExternally)
}

external interface ZlibParams {
    fun params(level: Number, strategy: Number, callback: () -> Unit)
}

external interface ZlibReset {
    fun reset()
}

external interface BrotliCompress : Transform, Zlib

external interface BrotliDecompress : Transform, Zlib

external interface Gzip : Transform, Zlib

external interface Gunzip : Transform, Zlib

external interface Deflate : Transform, Zlib, ZlibReset, ZlibParams

external interface Inflate : Transform, Zlib, ZlibReset

external interface DeflateRaw : Transform, Zlib, ZlibReset, ZlibParams

external interface InflateRaw : Transform, Zlib, ZlibReset

external interface Unzip : Transform, Zlib

external fun createBrotliCompress(options: BrotliOptions = definedExternally): BrotliCompress

external fun createBrotliDecompress(options: BrotliOptions = definedExternally): BrotliDecompress

external fun createGzip(options: ZlibOptions = definedExternally): Gzip

external fun createGunzip(options: ZlibOptions = definedExternally): Gunzip

external fun createDeflate(options: ZlibOptions = definedExternally): Deflate

external fun createInflate(options: ZlibOptions = definedExternally): Inflate

external fun createDeflateRaw(options: ZlibOptions = definedExternally): DeflateRaw

external fun createInflateRaw(options: ZlibOptions = definedExternally): InflateRaw

external fun createUnzip(options: ZlibOptions = definedExternally): Unzip

external fun brotliCompress(buf: String, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: ArrayBuffer, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Uint8Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Uint8ClampedArray, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Uint16Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Uint32Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Int8Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Int16Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Int32Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Float32Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: Float64Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: DataView, options: BrotliOptions, callback: CompressCallback)

external fun brotliCompress(buf: String, callback: CompressCallback)

external fun brotliCompress(buf: ArrayBuffer, callback: CompressCallback)

external fun brotliCompress(buf: Uint8Array, callback: CompressCallback)

external fun brotliCompress(buf: Uint8ClampedArray, callback: CompressCallback)

external fun brotliCompress(buf: Uint16Array, callback: CompressCallback)

external fun brotliCompress(buf: Uint32Array, callback: CompressCallback)

external fun brotliCompress(buf: Int8Array, callback: CompressCallback)

external fun brotliCompress(buf: Int16Array, callback: CompressCallback)

external fun brotliCompress(buf: Int32Array, callback: CompressCallback)

external fun brotliCompress(buf: Float32Array, callback: CompressCallback)

external fun brotliCompress(buf: Float64Array, callback: CompressCallback)

external fun brotliCompress(buf: DataView, callback: CompressCallback)

external fun brotliCompressSync(buf: String, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: ArrayBuffer, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Uint8Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Uint8ClampedArray, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Uint16Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Uint32Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Int8Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Int16Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Int32Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Float32Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: Float64Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliCompressSync(buf: DataView, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompress(buf: String, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: ArrayBuffer, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Uint8Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Uint8ClampedArray, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Uint16Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Uint32Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Int8Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Int16Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Int32Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Float32Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: Float64Array, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: DataView, options: BrotliOptions, callback: CompressCallback)

external fun brotliDecompress(buf: String, callback: CompressCallback)

external fun brotliDecompress(buf: ArrayBuffer, callback: CompressCallback)

external fun brotliDecompress(buf: Uint8Array, callback: CompressCallback)

external fun brotliDecompress(buf: Uint8ClampedArray, callback: CompressCallback)

external fun brotliDecompress(buf: Uint16Array, callback: CompressCallback)

external fun brotliDecompress(buf: Uint32Array, callback: CompressCallback)

external fun brotliDecompress(buf: Int8Array, callback: CompressCallback)

external fun brotliDecompress(buf: Int16Array, callback: CompressCallback)

external fun brotliDecompress(buf: Int32Array, callback: CompressCallback)

external fun brotliDecompress(buf: Float32Array, callback: CompressCallback)

external fun brotliDecompress(buf: Float64Array, callback: CompressCallback)

external fun brotliDecompress(buf: DataView, callback: CompressCallback)

external fun brotliDecompressSync(buf: String, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: ArrayBuffer, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Uint8Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Uint8ClampedArray, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Uint16Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Uint32Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Int8Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Int16Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Int32Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Float32Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: Float64Array, options: BrotliOptions = definedExternally): Buffer

external fun brotliDecompressSync(buf: DataView, options: BrotliOptions = definedExternally): Buffer

external fun deflate(buf: String, callback: CompressCallback)

external fun deflate(buf: ArrayBuffer, callback: CompressCallback)

external fun deflate(buf: Uint8Array, callback: CompressCallback)

external fun deflate(buf: Uint8ClampedArray, callback: CompressCallback)

external fun deflate(buf: Uint16Array, callback: CompressCallback)

external fun deflate(buf: Uint32Array, callback: CompressCallback)

external fun deflate(buf: Int8Array, callback: CompressCallback)

external fun deflate(buf: Int16Array, callback: CompressCallback)

external fun deflate(buf: Int32Array, callback: CompressCallback)

external fun deflate(buf: Float32Array, callback: CompressCallback)

external fun deflate(buf: Float64Array, callback: CompressCallback)

external fun deflate(buf: DataView, callback: CompressCallback)

external fun deflate(buf: String, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: ArrayBuffer, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Uint8Array, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Uint8ClampedArray, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Uint16Array, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Uint32Array, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Int8Array, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Int16Array, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Int32Array, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Float32Array, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: Float64Array, options: ZlibOptions, callback: CompressCallback)

external fun deflate(buf: DataView, options: ZlibOptions, callback: CompressCallback)

external fun deflateSync(buf: String, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: ArrayBuffer, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Uint8Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Uint8ClampedArray, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Uint16Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Uint32Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Int8Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Int16Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Int32Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Float32Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: Float64Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateSync(buf: DataView, options: ZlibOptions = definedExternally): Buffer

external fun deflateRaw(buf: String, callback: CompressCallback)

external fun deflateRaw(buf: ArrayBuffer, callback: CompressCallback)

external fun deflateRaw(buf: Uint8Array, callback: CompressCallback)

external fun deflateRaw(buf: Uint8ClampedArray, callback: CompressCallback)

external fun deflateRaw(buf: Uint16Array, callback: CompressCallback)

external fun deflateRaw(buf: Uint32Array, callback: CompressCallback)

external fun deflateRaw(buf: Int8Array, callback: CompressCallback)

external fun deflateRaw(buf: Int16Array, callback: CompressCallback)

external fun deflateRaw(buf: Int32Array, callback: CompressCallback)

external fun deflateRaw(buf: Float32Array, callback: CompressCallback)

external fun deflateRaw(buf: Float64Array, callback: CompressCallback)

external fun deflateRaw(buf: DataView, callback: CompressCallback)

external fun deflateRaw(buf: String, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: ArrayBuffer, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Uint8Array, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Uint8ClampedArray, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Uint16Array, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Uint32Array, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Int8Array, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Int16Array, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Int32Array, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Float32Array, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: Float64Array, options: ZlibOptions, callback: CompressCallback)

external fun deflateRaw(buf: DataView, options: ZlibOptions, callback: CompressCallback)

external fun deflateRawSync(buf: String, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: ArrayBuffer, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Uint8Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Uint8ClampedArray, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Uint16Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Uint32Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Int8Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Int16Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Int32Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Float32Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: Float64Array, options: ZlibOptions = definedExternally): Buffer

external fun deflateRawSync(buf: DataView, options: ZlibOptions = definedExternally): Buffer

external fun gzip(buf: String, callback: CompressCallback)

external fun gzip(buf: ArrayBuffer, callback: CompressCallback)

external fun gzip(buf: Uint8Array, callback: CompressCallback)

external fun gzip(buf: Uint8ClampedArray, callback: CompressCallback)

external fun gzip(buf: Uint16Array, callback: CompressCallback)

external fun gzip(buf: Uint32Array, callback: CompressCallback)

external fun gzip(buf: Int8Array, callback: CompressCallback)

external fun gzip(buf: Int16Array, callback: CompressCallback)

external fun gzip(buf: Int32Array, callback: CompressCallback)

external fun gzip(buf: Float32Array, callback: CompressCallback)

external fun gzip(buf: Float64Array, callback: CompressCallback)

external fun gzip(buf: DataView, callback: CompressCallback)

external fun gzip(buf: String, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: ArrayBuffer, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Uint8Array, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Uint8ClampedArray, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Uint16Array, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Uint32Array, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Int8Array, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Int16Array, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Int32Array, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Float32Array, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: Float64Array, options: ZlibOptions, callback: CompressCallback)

external fun gzip(buf: DataView, options: ZlibOptions, callback: CompressCallback)

external fun gzipSync(buf: String, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: ArrayBuffer, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Uint8Array, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Uint8ClampedArray, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Uint16Array, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Uint32Array, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Int8Array, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Int16Array, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Int32Array, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Float32Array, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: Float64Array, options: ZlibOptions = definedExternally): Buffer

external fun gzipSync(buf: DataView, options: ZlibOptions = definedExternally): Buffer

external fun gunzip(buf: String, callback: CompressCallback)

external fun gunzip(buf: ArrayBuffer, callback: CompressCallback)

external fun gunzip(buf: Uint8Array, callback: CompressCallback)

external fun gunzip(buf: Uint8ClampedArray, callback: CompressCallback)

external fun gunzip(buf: Uint16Array, callback: CompressCallback)

external fun gunzip(buf: Uint32Array, callback: CompressCallback)

external fun gunzip(buf: Int8Array, callback: CompressCallback)

external fun gunzip(buf: Int16Array, callback: CompressCallback)

external fun gunzip(buf: Int32Array, callback: CompressCallback)

external fun gunzip(buf: Float32Array, callback: CompressCallback)

external fun gunzip(buf: Float64Array, callback: CompressCallback)

external fun gunzip(buf: DataView, callback: CompressCallback)

external fun gunzip(buf: String, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: ArrayBuffer, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Uint8Array, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Uint8ClampedArray, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Uint16Array, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Uint32Array, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Int8Array, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Int16Array, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Int32Array, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Float32Array, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: Float64Array, options: ZlibOptions, callback: CompressCallback)

external fun gunzip(buf: DataView, options: ZlibOptions, callback: CompressCallback)

external fun gunzipSync(buf: String, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: ArrayBuffer, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Uint8Array, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Uint8ClampedArray, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Uint16Array, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Uint32Array, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Int8Array, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Int16Array, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Int32Array, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Float32Array, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: Float64Array, options: ZlibOptions = definedExternally): Buffer

external fun gunzipSync(buf: DataView, options: ZlibOptions = definedExternally): Buffer

external fun inflate(buf: String, callback: CompressCallback)

external fun inflate(buf: ArrayBuffer, callback: CompressCallback)

external fun inflate(buf: Uint8Array, callback: CompressCallback)

external fun inflate(buf: Uint8ClampedArray, callback: CompressCallback)

external fun inflate(buf: Uint16Array, callback: CompressCallback)

external fun inflate(buf: Uint32Array, callback: CompressCallback)

external fun inflate(buf: Int8Array, callback: CompressCallback)

external fun inflate(buf: Int16Array, callback: CompressCallback)

external fun inflate(buf: Int32Array, callback: CompressCallback)

external fun inflate(buf: Float32Array, callback: CompressCallback)

external fun inflate(buf: Float64Array, callback: CompressCallback)

external fun inflate(buf: DataView, callback: CompressCallback)

external fun inflate(buf: String, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: ArrayBuffer, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Uint8Array, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Uint8ClampedArray, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Uint16Array, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Uint32Array, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Int8Array, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Int16Array, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Int32Array, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Float32Array, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: Float64Array, options: ZlibOptions, callback: CompressCallback)

external fun inflate(buf: DataView, options: ZlibOptions, callback: CompressCallback)

external fun inflateSync(buf: String, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: ArrayBuffer, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Uint8Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Uint8ClampedArray, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Uint16Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Uint32Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Int8Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Int16Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Int32Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Float32Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: Float64Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateSync(buf: DataView, options: ZlibOptions = definedExternally): Buffer

external fun inflateRaw(buf: String, callback: CompressCallback)

external fun inflateRaw(buf: ArrayBuffer, callback: CompressCallback)

external fun inflateRaw(buf: Uint8Array, callback: CompressCallback)

external fun inflateRaw(buf: Uint8ClampedArray, callback: CompressCallback)

external fun inflateRaw(buf: Uint16Array, callback: CompressCallback)

external fun inflateRaw(buf: Uint32Array, callback: CompressCallback)

external fun inflateRaw(buf: Int8Array, callback: CompressCallback)

external fun inflateRaw(buf: Int16Array, callback: CompressCallback)

external fun inflateRaw(buf: Int32Array, callback: CompressCallback)

external fun inflateRaw(buf: Float32Array, callback: CompressCallback)

external fun inflateRaw(buf: Float64Array, callback: CompressCallback)

external fun inflateRaw(buf: DataView, callback: CompressCallback)

external fun inflateRaw(buf: String, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: ArrayBuffer, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Uint8Array, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Uint8ClampedArray, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Uint16Array, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Uint32Array, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Int8Array, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Int16Array, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Int32Array, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Float32Array, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: Float64Array, options: ZlibOptions, callback: CompressCallback)

external fun inflateRaw(buf: DataView, options: ZlibOptions, callback: CompressCallback)

external fun inflateRawSync(buf: String, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: ArrayBuffer, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Uint8Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Uint8ClampedArray, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Uint16Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Uint32Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Int8Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Int16Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Int32Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Float32Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: Float64Array, options: ZlibOptions = definedExternally): Buffer

external fun inflateRawSync(buf: DataView, options: ZlibOptions = definedExternally): Buffer

external fun unzip(buf: String, callback: CompressCallback)

external fun unzip(buf: ArrayBuffer, callback: CompressCallback)

external fun unzip(buf: Uint8Array, callback: CompressCallback)

external fun unzip(buf: Uint8ClampedArray, callback: CompressCallback)

external fun unzip(buf: Uint16Array, callback: CompressCallback)

external fun unzip(buf: Uint32Array, callback: CompressCallback)

external fun unzip(buf: Int8Array, callback: CompressCallback)

external fun unzip(buf: Int16Array, callback: CompressCallback)

external fun unzip(buf: Int32Array, callback: CompressCallback)

external fun unzip(buf: Float32Array, callback: CompressCallback)

external fun unzip(buf: Float64Array, callback: CompressCallback)

external fun unzip(buf: DataView, callback: CompressCallback)

external fun unzip(buf: String, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: ArrayBuffer, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Uint8Array, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Uint8ClampedArray, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Uint16Array, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Uint32Array, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Int8Array, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Int16Array, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Int32Array, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Float32Array, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: Float64Array, options: ZlibOptions, callback: CompressCallback)

external fun unzip(buf: DataView, options: ZlibOptions, callback: CompressCallback)

external fun unzipSync(buf: String, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: ArrayBuffer, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Uint8Array, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Uint8ClampedArray, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Uint16Array, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Uint32Array, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Int8Array, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Int16Array, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Int32Array, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Float32Array, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: Float64Array, options: ZlibOptions = definedExternally): Buffer

external fun unzipSync(buf: DataView, options: ZlibOptions = definedExternally): Buffer

external var Z_NO_FLUSH: Number

external var Z_PARTIAL_FLUSH: Number

external var Z_SYNC_FLUSH: Number

external var Z_FULL_FLUSH: Number

external var Z_FINISH: Number

external var Z_BLOCK: Number

external var Z_TREES: Number

external var Z_OK: Number

external var Z_STREAM_END: Number

external var Z_NEED_DICT: Number

external var Z_ERRNO: Number

external var Z_STREAM_ERROR: Number

external var Z_DATA_ERROR: Number

external var Z_MEM_ERROR: Number

external var Z_BUF_ERROR: Number

external var Z_VERSION_ERROR: Number

external var Z_NO_COMPRESSION: Number

external var Z_BEST_SPEED: Number

external var Z_BEST_COMPRESSION: Number

external var Z_DEFAULT_COMPRESSION: Number

external var Z_FILTERED: Number

external var Z_HUFFMAN_ONLY: Number

external var Z_RLE: Number

external var Z_FIXED: Number

external var Z_DEFAULT_STRATEGY: Number

external var Z_BINARY: Number

external var Z_TEXT: Number

external var Z_ASCII: Number

external var Z_UNKNOWN: Number

external var Z_DEFLATED: Number