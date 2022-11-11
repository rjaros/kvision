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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * InAppBrowser event type.
 */
external class InAppBrowserEvent {
    val type: String
    val url: String
    val code: Number?
    val message: String?
    val data: String?
}

/**
 * InAppBrowser reference object.
 */
external class Browser {
    fun addEventListener(eventname: String, callback: (InAppBrowserEvent) -> Unit)
    fun removeEventListener(eventname: String, callback: (InAppBrowserEvent) -> Unit)
    fun close()
    fun show()
    fun hide()
    fun executeScript(details: dynamic, callback: ((dynamic) -> Unit) = definedExternally)
    fun insertCSS(details: dynamic, callback: (() -> Unit) = definedExternally)
}

/**
 * Main object for Cordova InAppBrowser api.
 */
object InAppBrowser {

    /**
     * Open new browser window.
     * @param url an URL address
     * @param target a target window
     * @param options a string with window options
     * @return a browser window reference
     */
    suspend fun open(url: String, target: String = "_blank", options: String? = null): Browser {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                val ref = window.asDynamic().cordova.InAppBrowser.open(url, target, options)
                continuation.resume(ref)
            }
        }
    }

}
