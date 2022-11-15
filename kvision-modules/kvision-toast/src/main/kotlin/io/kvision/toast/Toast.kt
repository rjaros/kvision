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
@file:Suppress("DEPRECATION")

package io.kvision.toast

import io.kvision.ToastModule
import io.kvision.utils.obj

/**
 * Toast message types.
 */
internal enum class ToastType(internal val type: String) {
    INFO("info"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error")
}

/**
 * Toast positions.
 */
enum class ToastPosition(internal val position: String) {
    TOPRIGHT("toast-top-right"),
    BOTTOMRIGHT("toast-bottom-right"),
    BOTTOMLEFT("toast-bottom-left"),
    TOPLEFT("toast-top-left"),
    TOPFULLWIDTH("toast-top-full-width"),
    BOTTOMFULLWIDTH("toast-bottom-full-width"),
    TOPCENTER("toast-top-center"),
    BOTTOMCENTER("toast-bottom-center")
}

/**
 * Toast easings.
 */
enum class ToastEasing(internal val easing: String) {
    SWING("swing"),
    LINEAR("linear")
}

/**
 * Toast animation methods.
 */
enum class ToastMethod(internal val method: String) {
    FADEIN("fadeIn"),
    FADEOUT("fadeOut"),
    SLIDEUP("slideUp"),
    SLIDEDOWN("slideDown"),
    SHOW("show"),
    HIDE("hide")
}

/**
 * Toast options.
 */
data class ToastOptions(
    val positionClass: ToastPosition? = null,
    val escapeHtml: Boolean? = null,
    val closeButton: Boolean? = null,
    val closeHtml: String? = null,
    val closeDuration: Int? = null,
    val newestOnTop: Boolean? = null,
    val showEasing: ToastEasing? = null,
    val hideEasing: ToastEasing? = null,
    val closeEasing: ToastEasing? = null,
    val showMethod: ToastMethod? = null,
    val hideMethod: ToastMethod? = null,
    val closeMethod: ToastMethod? = null,
    val preventDuplicates: Boolean? = null,
    val timeOut: Int? = null,
    val extendedTimeOut: Int? = null,
    val progressBar: Boolean? = null,
    val rtl: Boolean? = null,
    val onShown: (() -> Unit)? = null,
    val onHidden: (() -> Unit)? = null,
    val onClick: (() -> Unit)? = null,
    val onCloseClick: (() -> Unit)? = null
)

internal fun ToastOptions.toJs(): dynamic {
    return obj {
        if (positionClass != null) this.positionClass = positionClass.position
        if (escapeHtml != null) this.escapeHtml = escapeHtml
        if (closeButton != null) this.closeButton = closeButton
        if (closeHtml != null) this.closeHtml = closeHtml
        if (closeDuration != null) this.closeDuration = closeDuration
        if (newestOnTop != null) this.newestOnTop = newestOnTop
        if (showEasing != null) this.showEasing = showEasing.easing
        if (hideEasing != null) this.hideEasing = hideEasing.easing
        if (closeEasing != null) this.closeEasing = closeEasing.easing
        if (showMethod != null) this.showMethod = showMethod.method
        if (hideMethod != null) this.hideMethod = hideMethod.method
        if (closeMethod != null) this.closeMethod = closeMethod.method
        if (preventDuplicates != null) this.preventDuplicates = preventDuplicates
        if (timeOut != null) this.timeOut = timeOut
        if (extendedTimeOut != null) this.extendedTimeOut = extendedTimeOut
        if (progressBar != null) this.progressBar = progressBar
        if (rtl != null) this.rtl = rtl
        if (onShown != null) this.onShown = onShown
        if (onHidden != null) this.onHidden = onHidden
        if (onClick != null) this.onclick = onClick
        if (onCloseClick != null) this.onCloseClick = onCloseClick
    }
}

/**
 * Toast component object.
 */
@Deprecated("Use Toast object from the kvision-toastify module instead.")
object Toast {

    init {
        ToastModule.initialize()
    }

    /**
     * Shows a success toast.
     * @param message a toast message
     * @param title a toast title
     * @param options toast options
     */
    fun success(message: String, title: String? = null, options: ToastOptions? = null) {
        show(ToastType.SUCCESS, message, title, options)
    }

    /**
     * Shows an info toast.
     * @param message a toast message
     * @param title a toast title
     * @param options toast options
     */
    fun info(message: String, title: String? = null, options: ToastOptions? = null) {
        show(ToastType.INFO, message, title, options)
    }

    /**
     * Shows a warning toast.
     * @param message a toast message
     * @param title a toast title
     * @param options toast options
     */
    fun warning(message: String, title: String? = null, options: ToastOptions? = null) {
        show(ToastType.WARNING, message, title, options)
    }

    /**
     * Shows an error toast.
     * @param message a toast message
     * @param title a toast title
     * @param options toast options
     */
    fun error(message: String, title: String? = null, options: ToastOptions? = null) {
        show(ToastType.ERROR, message, title, options)
    }

    private fun show(type: ToastType, message: String, title: String? = null, options: ToastOptions? = null) {
        if (options != null) {
            ToastModule.toastr[type.type](message, title, options.toJs())
        } else {
            ToastModule.toastr[type.type](message, title)
        }
    }

}
