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
 * A response object for prompt function callback.
 */
external class PromptResponse {
    val buttonIndex: Int
    val input1: String
}

/**
 * Main object for Cordova notifications.
 */
@Deprecated("The kvision-cordova module is deprecated and will be removed in a future version.")
object Notification {

    /**
     * Show alert dialog.
     * @param message a message
     * @param title an optional dialog title (defaults to Alert)
     * @param buttonLabel an optional button label (defaults to OK)
     * @param callback a callback function
     */
    @Suppress("UnsafeCastFromDynamic")
    fun alert(message: String, title: String? = null, buttonLabel: String? = null, callback: () -> Unit) {
        addDeviceReadyListener {
            window.navigator.asDynamic().notification.alert(message, callback, title, buttonLabel)
        }
    }

    /**
     * Show confirm dialog.
     * @param message a message
     * @param title an optional dialog title (defaults to Confirm)
     * @param buttonLabels an optional list of button labels (defaults to [OK, Cancel])
     * @param callback a callback function
     */
    @Suppress("UnsafeCastFromDynamic")
    fun confirm(message: String, title: String? = null, buttonLabels: List<String>? = null, callback: (Int) -> Unit) {
        addDeviceReadyListener {
            window.navigator.asDynamic().notification.confirm(message, callback, title, buttonLabels?.toTypedArray())
        }
    }

    /**
     * Show prompt dialog.
     * @param message a message
     * @param title an optional dialog title (defaults to Prompt)
     * @param buttonLabels an optional list of button labels (defaults to [OK, Cancel])
     * @param defaultText default input value (default to empty string)
     * @param callback a callback function
     */
    @Suppress("UnsafeCastFromDynamic")
    fun prompt(
        message: String,
        title: String? = null,
        buttonLabels: List<String>? = null,
        defaultText: String? = null,
        callback: (PromptResponse) -> Unit
    ) {
        addDeviceReadyListener {
            window.navigator.asDynamic().notification.prompt(
                message,
                callback,
                title,
                buttonLabels?.toTypedArray(),
                defaultText
            )
        }
    }

    /**
     * Play a beep sound.
     * @param times the number of times to repeat the beep (defaults to 1)
     */
    @Suppress("UnsafeCastFromDynamic")
    fun beep(times: Int = 1) {
        addDeviceReadyListener {
            window.navigator.asDynamic().notification.beep(times)
        }
    }

}
