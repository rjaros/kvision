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

import pl.treksoft.kvision.utils.obj
import kotlin.browser.window
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Exception class for camera errors.
 */
class CameraException(message: String) : Exception(message)

/**
 * Main object for Cordova camera.
 */
object Camera {

    private const val CAMERA_ACTIVE_STORAGE_KEY = "kv_camera_active_storage_key"
    private const val CAMERA_STATUS_OK = "OK"

    /**
     * Camera destination types.
     */
    enum class DestinationType {
        DATA_URL,
        FILE_URI,
        NATIVE_URI
    }

    /**
     * Picture encoding types.
     */
    enum class EncodingType {
        JPEG,
        PNG
    }

    /**
     * Picture/video media types.
     */
    enum class MediaType {
        PICTURE,
        VIDEO,
        ALLMEDIA
    }

    /**
     * Camera picture/video sources.
     */
    enum class PictureSourceType {
        PHOTOLIBRARY,
        CAMERA,
        SAVEDPHOTOALBUM
    }

    /**
     * Camera facing types.
     */
    enum class Direction {
        BACK,
        FRONT
    }

    /**
     * iOS popover arrow directions.
     */
    enum class PopoverArrowDirection {
        ARROW_UP,
        ARROW_DOWN,
        ARROW_LEFT,
        ARROW_RIGHT,
        ARROW_ANY
    }

    /**
     * iOS popover options.
     */
    data class CameraPopoverOptions(
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int,
        val arrowDir: PopoverArrowDirection,
        val popoverWidth: Int,
        val popoverHeight: Int
    )

    /**
     * Suspending function to get picture from the camera.
     *
     * Note: On Android platform you must also use [addCameraResultCallback] listener.
     *
     * @param options camera options
     * @return a [Result] class containing the picture or the exception
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun getPicture(options: CameraOptions): Result<String, CameraException> {
        return suspendCoroutine { continuation ->
            getPicture(options) {
                continuation.resume(it)
            }
        }
    }

    /**
     * A function to get picture from the camera.
     *
     * Note: On Android platform you must also use [addCameraResultCallback] listener.
     *
     * @param options camera options
     * @param resultCallback a callback function to get the [Result], containing the picture or the exception
     */
    @Suppress("UnsafeCastFromDynamic")
    fun getPicture(options: CameraOptions, resultCallback: (Result<String, CameraException>) -> Unit) {
        window.localStorage.setItem(CAMERA_ACTIVE_STORAGE_KEY, "true")
        addDeviceReadyListener {
            window.navigator.asDynamic().camera.getPicture({ image ->
                window.localStorage.removeItem(CAMERA_ACTIVE_STORAGE_KEY)
                resultCallback(Result.success(image))
            }, { message ->
                window.localStorage.removeItem(CAMERA_ACTIVE_STORAGE_KEY)
                resultCallback(Result.error(CameraException(message)))
            }, options.toJs())
        }
    }

    /**
     * An Android specific function to get picture from the camera after resume when the application
     * webview intent is killed.
     *
     * @param resultCallback a callback function to get the [Result], containing the picture or the exception
     */
    fun addCameraResultCallback(resultCallback: (Result<String, CameraException>) -> Unit) {
        addResumeListener { resumeEvent ->
            val isCameraActive = window.localStorage.getItem(CAMERA_ACTIVE_STORAGE_KEY) == "true"
            if (isCameraActive && resumeEvent.pendingResult != null) {
                window.localStorage.removeItem(CAMERA_ACTIVE_STORAGE_KEY)
                if (resumeEvent.pendingResult.pluginStatus == CAMERA_STATUS_OK) {
                    @Suppress("UnsafeCastFromDynamic")
                    resultCallback(Result.success(resumeEvent.pendingResult.result))
                } else {
                    @Suppress("UnsafeCastFromDynamic")
                    resultCallback(Result.error(CameraException(resumeEvent.pendingResult.result)))
                }
            }
        }
    }

    /**
     * Removes intermediate image files that are kept in the temporary storage after calling [getPicture].
     *
     * @param resultCallback an optional callback function to get the [Result] of the cleanup operation.
     */
    @Suppress("UnsafeCastFromDynamic")
    fun cleanup(resultCallback: ((Result<String, CameraException>) -> Unit)? = null) {
        addDeviceReadyListener {
            window.navigator.asDynamic().camera.cleanup({
                resultCallback?.invoke(Result.success(CAMERA_STATUS_OK))
            }, { message ->
                resultCallback?.invoke(Result.error(CameraException(message)))
            })
        }
    }
}

internal fun Camera.DestinationType.toJs(): dynamic = when (this) {
    Camera.DestinationType.DATA_URL -> js("Camera.DestinationType.DATA_URL")
    Camera.DestinationType.FILE_URI -> js("Camera.DestinationType.FILE_URI")
    Camera.DestinationType.NATIVE_URI -> js("Camera.DestinationType.NATIVE_URI")
}

internal fun Camera.EncodingType.toJs(): dynamic = when (this) {
    Camera.EncodingType.JPEG -> js("Camera.EncodingType.JPEG")
    Camera.EncodingType.PNG -> js("Camera.EncodingType.PNG")
}

internal fun Camera.MediaType.toJs(): dynamic = when (this) {
    Camera.MediaType.PICTURE -> js("Camera.MediaType.PICTURE")
    Camera.MediaType.VIDEO -> js("Camera.MediaType.VIDEO")
    Camera.MediaType.ALLMEDIA -> js("Camera.MediaType.ALLMEDIA")
}

internal fun Camera.PictureSourceType.toJs(): dynamic = when (this) {
    Camera.PictureSourceType.PHOTOLIBRARY -> js("Camera.PictureSourceType.PHOTOLIBRARY")
    Camera.PictureSourceType.CAMERA -> js("Camera.PictureSourceType.CAMERA")
    Camera.PictureSourceType.SAVEDPHOTOALBUM -> js("Camera.PictureSourceType.SAVEDPHOTOALBUM")
}

internal fun Camera.PopoverArrowDirection.toJs(): dynamic = when (this) {
    Camera.PopoverArrowDirection.ARROW_UP -> js("Camera.PopoverArrowDirection.ARROW_UP")
    Camera.PopoverArrowDirection.ARROW_DOWN -> js("Camera.PopoverArrowDirection.ARROW_DOWN")
    Camera.PopoverArrowDirection.ARROW_LEFT -> js("Camera.PopoverArrowDirection.ARROW_LEFT")
    Camera.PopoverArrowDirection.ARROW_RIGHT -> js("Camera.PopoverArrowDirection.ARROW_RIGHT")
    Camera.PopoverArrowDirection.ARROW_ANY -> js("Camera.PopoverArrowDirection.ARROW_ANY")
}

internal fun Camera.Direction.toJs(): dynamic = when (this) {
    Camera.Direction.BACK -> js("Camera.Direction.BACK")
    Camera.Direction.FRONT -> js("Camera.Direction.FRONT")
}

internal external class CameraPopoverOptions(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    arrowDir: dynamic,
    popoverWidth: Int,
    popoverHeight: Int
)

internal fun Camera.CameraPopoverOptions.toJs(): dynamic {
    return CameraPopoverOptions(x, y, width, height, arrowDir.toJs(), popoverWidth, popoverHeight)
}

/**
 * Camera options.
 */
data class CameraOptions(
    val quality: Int? = null,
    val destinationType: Camera.DestinationType? = null,
    val sourceType: Camera.PictureSourceType? = null,
    val allowEdit: Boolean? = null,
    val encodingType: Camera.EncodingType? = null,
    val targetWidth: Int? = null,
    val targetHeight: Int? = null,
    val mediaType: Camera.MediaType? = null,
    val correctOrientation: Boolean? = null,
    val saveToPhotoAlbum: Boolean? = null,
    val popoverOptions: Camera.CameraPopoverOptions? = null,
    val cameraDirection: Camera.Direction? = null
)

@Suppress("ComplexMethod")
internal fun CameraOptions.toJs(): dynamic {
    return obj {
        if (quality != null) this.quality = quality
        if (destinationType != null) this.destinationType = destinationType.toJs()
        if (sourceType != null) this.sourceType = sourceType.toJs()
        if (allowEdit != null) this.allowEdit = allowEdit
        if (encodingType != null) this.encodingType = encodingType.toJs()
        if (targetWidth != null) this.targetWidth = targetWidth
        if (targetHeight != null) this.targetHeight = targetHeight
        if (mediaType != null) this.mediaType = mediaType.toJs()
        if (correctOrientation != null) this.correctOrientation = correctOrientation
        if (saveToPhotoAlbum != null) this.saveToPhotoAlbum = saveToPhotoAlbum
        if (popoverOptions != null) this.popoverOptions = popoverOptions.toJs()
        if (cameraDirection != null) this.cameraDirection = cameraDirection.toJs()
    }
}
