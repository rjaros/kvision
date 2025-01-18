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

import kotlinx.browser.window

/**
 * Main object for Cordova screen.
 */
@Deprecated("The kvision-cordova module is deprecated and will be removed in a future version.")
object Screen {

    /**
     * Screen orientations
     */
    enum class Orientation(internal val type: String) {
        PORTRAIT_PRIMARY("portrait-primary"),
        PORTRAIT_SECONDARY("portrait-secondary"),
        LANDSCAPE_PRIMARY("landscape-primary"),
        LANDSCAPE_SECONDARY("landscape-secondary"),
        PORTRAIT("portrait"),
        LANDSCAPE("landscape"),
        ANY("any")
    }

    /**
     * Lock screen orientation.
     * @param orientation selected orientation
     */
    fun lock(orientation: Orientation) {
        window.screen.asDynamic().orientation.lock(orientation.type)
    }

    /**
     * Unlock screen orientation.
     */
    fun unlock() {
        window.screen.asDynamic().orientation.unlock()
    }

    /**
     * Get screen orientation.
     */
    fun getOrientation(): Orientation {
        val type = window.screen.asDynamic().orientation.type
        return Orientation.entries.find {
            it.type == type
        } ?: Orientation.ANY
    }

    /**
     * Add listeners for screen orientation change Cordova events.
     */
    fun addOrientationChangeListener(listener: (Orientation) -> Unit) {
        window.addEventListener("orientationchange", {
            listener(getOrientation())
        }, false)
    }
}
