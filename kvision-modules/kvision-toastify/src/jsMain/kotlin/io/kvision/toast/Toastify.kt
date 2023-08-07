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

package io.kvision.toast

import io.kvision.ToastifyModule
import io.kvision.utils.obj

/**
 * Toast message types.
 */
internal enum class ToastType(internal val type: String) {
    PRIMARY("kv-toastify-primary"),
    SECONDARY("kv-toastify-secondary"),
    INFO("kv-toastify-info"),
    SUCCESS("kv-toastify-success"),
    WARNING("kv-toastify-warning"),
    DANGER("kv-toastify-danger"),
    LIGHT("kv-toastify-light"),
    DARK("kv-toastify-dark"),
}

/**
 * Toast positions.
 */
enum class ToastPosition {
    TOPRIGHT,
    BOTTOMRIGHT,
    BOTTOMLEFT,
    TOPLEFT
}

/**
 * Toast options.
 */
data class ToastOptions(
    val position: ToastPosition? = null,
    val className: String? = null,
    val escapeHtml: Boolean? = null,
    val close: Boolean? = null,
    val oldestFirst: Boolean? = null,
    val duration: Int? = null,
    val stopOnFocus: Boolean? = null,
    val onClick: (() -> Unit)? = null,
    val destination: String? = null,
    val newWindow: Boolean? = null,
    val callback: (() -> Unit)? = null,
    val avatar: String? = null,
    val offset: dynamic = null,
    val style: dynamic = null,
    val ariaLive: String? = null
)

internal fun ToastOptions.toJs(): dynamic {
    val positionType = when (position) {
        ToastPosition.TOPRIGHT, ToastPosition.BOTTOMRIGHT -> "right"
        ToastPosition.TOPLEFT, ToastPosition.BOTTOMLEFT -> "left"
        else -> null
    }
    val gravityType = when (position) {
        ToastPosition.TOPRIGHT, ToastPosition.TOPLEFT -> "top"
        ToastPosition.BOTTOMRIGHT, ToastPosition.BOTTOMLEFT -> "bottom"
        else -> null
    }
    return obj {
        if (positionType != null) this.position = positionType
        if (gravityType != null) this.gravity = gravityType
        if (escapeHtml != null) this.escapeHtml = escapeHtml
        if (close != null) this.close = close
        if (oldestFirst != null) this.oldestFirst = oldestFirst
        if (duration != null) this.duration = duration
        if (stopOnFocus != null) this.stopOnFocus = stopOnFocus
        if (onClick != null) this.onClick = onClick
        if (destination != null) this.destination = destination
        if (newWindow != null) this.newWindow = newWindow
        if (callback != null) this.callback = callback
        if (avatar != null) this.avatar = avatar
        if (offset != null) this.offset = offset
        if (style != null) this.style = style
        if (ariaLive != null) this.ariaLive = ariaLive
    }
}

/**
 * Toast component object.
 */
object Toast {

    init {
        ToastifyModule.initialize()
    }

    /**
     * Shows a primary toast.
     * @param message a toast message
     * @param options toast options
     */
    fun primary(message: String, options: ToastOptions? = null) {
        show(ToastType.PRIMARY, message, options)
    }

    /**
     * Shows a secondary toast.
     * @param message a toast message
     * @param options toast options
     */
    fun secondary(message: String, options: ToastOptions? = null) {
        show(ToastType.SECONDARY, message, options)
    }

    /**
     * Shows a success toast.
     * @param message a toast message
     * @param options toast options
     */
    fun success(message: String, options: ToastOptions? = null) {
        show(ToastType.SUCCESS, message, options)
    }

    /**
     * Shows an info toast.
     * @param message a toast message
     * @param options toast options
     */
    fun info(message: String, options: ToastOptions? = null) {
        show(ToastType.INFO, message, options)
    }

    /**
     * Shows a warning toast.
     * @param message a toast message
     * @param options toast options
     */
    fun warning(message: String, options: ToastOptions? = null) {
        show(ToastType.WARNING, message, options)
    }

    /**
     * Shows a danger toast.
     * @param message a toast message
     * @param options toast options
     */

    fun danger(message: String, options: ToastOptions? = null) {
        show(ToastType.DANGER, message, options)
    }

    /**
     * Shows a light toast.
     * @param message a toast message
     * @param options toast options
     */
    fun light(message: String, options: ToastOptions? = null) {
        show(ToastType.LIGHT, message, options)
    }

    /**
     * Shows a dark toast.
     * @param message a toast message
     * @param options toast options
     */
    fun dark(message: String, options: ToastOptions? = null) {
        show(ToastType.DARK, message, options)
    }

    private fun show(type: ToastType, message: String, options: ToastOptions? = null) {
        val optJs = if (options != null) {
            val opts = options.toJs()
            opts["text"] = message
            opts["className"] = options.className ?: type.type
            opts
        } else {
            obj {
                this.text = message
                this.className = type.type
            }
        }
        ToastifyModule.toastify(optJs).showToast()
    }

}
