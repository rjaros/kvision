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

import io.kvision.utils.toHexString
import kotlinx.browser.window
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Suppress("TopLevelPropertyNaming")
@JsName("StatusBar")
internal external val StatusBarJs: dynamic

/**
 * Main object for Cordova status bar.
 */
@Suppress("TooManyFunctions")
object StatusBar {

    /**
     * Show status bar.
     */
    fun show() {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.show()
        }
    }

    /**
     * Hide status bar.
     */
    fun hide() {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.hide()
        }
    }

    /**
     * Set overlay.
     * @param overlays determines if the status bar overlays the web view
     */
    fun overlaysWebView(overlays: Boolean) {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.overlaysWebView(overlays)
        }
    }

    /**
     * Use the default status bar (dark text, for light backgrounds).
     */
    fun styleDefault() {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.styleDefault()
        }
    }

    /**
     * Use the lightContent status bar (light text, for dark backgrounds).
     */
    fun styleLightContent() {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.styleLightContent()
        }
    }

    /**
     * Use the blackTranslucent status bar (light text, for dark backgrounds).
     */
    fun styleBlackTranslucent() {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.styleBlackTranslucent()
        }
    }

    /**
     * Use the blackOpaque status bar (light text, for dark backgrounds).
     */
    fun styleBlackOpaque() {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.styleBlackOpaque()
        }
    }

    /**
     * Sets the background color of the status bar by a hexadecimal Int.
     */
    fun setBackgroundColor(color: Int) {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.backgroundColorByHexString("#" + color.toHexString())
        }
    }

    /**
     * Sets the background color of the status bar by a hex string.
     */
    fun setBackgroundColorByHexString(color: String) {
        addDeviceReadyListener {
            @Suppress("UnsafeCastFromDynamic")
            StatusBarJs.backgroundColorByHexString(color)
        }
    }

    /**
     * Returns if the status bar is visible.
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun isVisible(): Boolean {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                continuation.resume(StatusBarJs.isVisible)
            }
        }
    }

    /**
     * Add listeners for the status bar tap event.
     */
    fun addTapListener(listener: () -> Unit) {
        addDeviceReadyListener {
            window.addEventListener("statusTap", {
                listener()
            })
        }
    }

}
