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

package io.kvision.cordova

import io.kvision.utils.obj
import kotlinx.browser.document
import kotlinx.browser.window
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Media error class.
 */
internal external class CaptureError {
    val code: Int

    companion object {
        val CAPTURE_INTERNAL_ERR: Int
        val CAPTURE_APPLICATION_BUSY: Int
        val CAPTURE_INVALID_ARGUMENT: Int
        val CAPTURE_NO_MEDIA_FILES: Int
        val CAPTURE_PERMISSION_DENIED: Int
        val CAPTURE_NOT_SUPPORTED: Int
    }
}

/**
 * Media file details class.
 */
external class MediaFileData {
    val codecs: String?
    val bitrate: Number
    val height: Number
    val width: Number
    val duration: Number
}

/**
 * Media file information class.
 */
external class MediaFile {
    val name: String
    val fullPath: String
    val type: String
    val lastModifiedDate: Number
    val size: Number

    fun getFormatData(successCallback: (MediaFileData) -> Unit, errorCallback: (() -> Unit)? = definedExternally)
}

/**
 * Get details for the given file.
 */
suspend fun MediaFile.getFormatData(): MediaFileData? {
    return suspendCoroutine { continuation ->
        this.getFormatData({
            continuation.resume(it)
        }, {
            continuation.resume(null)
        })
    }
}

/**
 * Exception class for media capture errors.
 */
class CaptureException(code: MediaCapture.CaptureErrorCode) : Exception("Capture exception: $code")

/**
 * Main media capture object.
 */
object MediaCapture {

    enum class CaptureErrorCode {
        CAPTURE_INTERNAL_ERR,
        CAPTURE_APPLICATION_BUSY,
        CAPTURE_INVALID_ARGUMENT,
        CAPTURE_NO_MEDIA_FILES,
        CAPTURE_PERMISSION_DENIED,
        CAPTURE_NOT_SUPPORTED
    }

    @Suppress("MagicNumber")
    private fun codeToEnum(code: Int): CaptureErrorCode {
        return when (code) {
            0 -> CaptureErrorCode.CAPTURE_INTERNAL_ERR
            1 -> CaptureErrorCode.CAPTURE_APPLICATION_BUSY
            2 -> CaptureErrorCode.CAPTURE_INVALID_ARGUMENT
            3 -> CaptureErrorCode.CAPTURE_NO_MEDIA_FILES
            4 -> CaptureErrorCode.CAPTURE_PERMISSION_DENIED
            else -> CaptureErrorCode.CAPTURE_NOT_SUPPORTED
        }
    }

    /**
     * Capture audio file(s).
     * @param limit The maximum number of audio clips the user can capture in a single capture operation.
     * @param duration The maximum duration of an audio clip, in seconds.
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun captureAudio(limit: Number = 1, duration: Number? = null): Result<List<MediaFile>> {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                window.navigator.asDynamic().device.capture.captureAudio({ result: Array<MediaFile> ->
                    continuation.resume(Result.success(result.asList()))
                }, { err: CaptureError ->
                    continuation.resume(Result.failure(CaptureException(codeToEnum(err.code))))
                }, obj {
                    this.limit = limit
                    if (duration != null) this.duration = duration
                })
            }
        }
    }

    /**
     * Capture image file(s).
     * @param limit The maximum number of image files the user can capture in a single capture operation.
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun captureImage(limit: Number = 1): Result<List<MediaFile>> {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                window.navigator.asDynamic().device.capture.captureImage({ result: Array<MediaFile> ->
                    continuation.resume(Result.success(result.asList()))
                }, { err: CaptureError ->
                    continuation.resume(Result.failure(CaptureException(codeToEnum(err.code))))
                }, obj {
                    this.limit = limit
                })
            }
        }
    }

    /**
     * Capture video file(s).
     * @param limit The maximum number of video clips the user can capture in a single capture operation.
     * @param duration The maximum duration of an video clip, in seconds.
     * @param lowQuality Capture video with a lower quality (Android only)
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun captureVideo(
        limit: Number = 1,
        duration: Number? = null,
        lowQuality: Boolean = false
    ): Result<List<MediaFile>> {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                window.navigator.asDynamic().device.capture.captureVideo({ result: Array<MediaFile> ->
                    continuation.resume(Result.success(result.asList()))
                }, { err: CaptureError ->
                    continuation.resume(Result.failure(CaptureException(codeToEnum(err.code))))
                }, obj {
                    this.limit = limit
                    if (duration != null) this.duration = duration
                    if (lowQuality) this.quality = 0
                })
            }
        }
    }

    /**
     * Add listeners for pending capture Cordova events.
     */
    fun addPendingCaptureListener(listener: (Result<List<MediaFile>>) -> Unit) {
        addDeviceReadyListener {
            document.addEventListener("pendingcaptureresult", { result ->
                @Suppress("CAST_NEVER_SUCCEEDS")
                listener(Result.success((result as? Array<MediaFile>)?.asList() ?: listOf()))
            }, false)
            document.addEventListener("pendingcaptureerror", { error ->
                @Suppress("CAST_NEVER_SUCCEEDS")
                listener(Result.failure(CaptureException(codeToEnum((error as? CaptureError)?.code ?: 0))))
            }, false)
        }
    }
}
