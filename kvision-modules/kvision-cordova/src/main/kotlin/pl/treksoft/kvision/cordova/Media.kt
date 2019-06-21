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

package pl.treksoft.kvision.cordova

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Media error class.
 */
external class MediaError {
    val code: Int
    val message: String

    companion object {
        val MEDIA_ERR_ABORTED: Int
        val MEDIA_ERR_NETWORK: Int
        val MEDIA_ERR_DECODE: Int
        val MEDIA_ERR_NONE_SUPPORTED: Int
    }
}

/**
 * Cordova media class.
 */
@Suppress("TooManyFunctions")
external class Media(
    src: String,
    mediaSuccess: (() -> Unit)? = definedExternally,
    mediaError: ((MediaError) -> Unit)? = definedExternally,
    mediaStatus: ((Int) -> Unit)? = definedExternally
) {

    fun play()
    fun pause()
    fun stop()
    fun startRecord()
    fun pauseRecord()
    fun resumeRecord()
    fun stopRecord()
    fun seekTo(millis: Number)
    fun setRate(rate: Number)
    fun setVolume(volume: Number)
    fun getDuration(): Number
    fun getCurrentAmplitude(mediaSuccess: (Number) -> Unit, mediaError: ((Int) -> Unit)? = definedExternally)
    fun getCurrentPosition(mediaSuccess: (Number) -> Unit, mediaError: ((Int) -> Unit)? = definedExternally)
    fun release()

    val position: Number
    val duration: Number

    companion object {
        val MEDIA_NONE: Int
        val MEDIA_STARTING: Int
        val MEDIA_RUNNING: Int
        val MEDIA_PAUSED: Int
        val MEDIA_STOPPED: Int
    }
}

/**
 * Returns the current amplitude within an audio file.
 */
suspend fun Media.getCurrentAmplitude(): Number {
    return suspendCoroutine { continuation ->
        this.getCurrentAmplitude({ amp ->
            continuation.resume(amp)
        }, {
            continuation.resume(-1)
        })
    }
}

/**
 * Returns the current position within an audio file.
 */
suspend fun Media.getCurrentPosition(): Number {
    return suspendCoroutine { continuation ->
        this.getCurrentPosition({ pos ->
            continuation.resume(pos)
        }, {
            continuation.resume(-1)
        })
    }
}
