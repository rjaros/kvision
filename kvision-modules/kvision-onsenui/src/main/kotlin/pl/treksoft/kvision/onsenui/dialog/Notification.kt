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

package pl.treksoft.kvision.onsenui.dialog

import pl.treksoft.kvision.KVManagerOnsenui.ons
import pl.treksoft.kvision.utils.obj
import kotlin.js.Promise

/**
 * Displays alert notification.
 * @param message a notification message
 * @param title a window title
 * @param buttonLabels labels of the buttons
 * @param primaryButtonIndex an index of the default button
 * @param cancelable whether the notification can be cancelled
 * @param animation whether the notification is animated
 * @param id the id of the notification HTML element
 * @param className the CSS class of the notification HTML element
 * @param modifier a modifier attribute to specify custom styles
 * @param maskColor a color of the background mask
 * @param callback a callback function called after the dialog has been closed
 */
fun showAlert(
    message: String,
    title: String? = null,
    buttonLabels: List<String>? = null,
    primaryButtonIndex: Int? = null,
    cancelable: Boolean = false,
    animation: Boolean = true,
    id: String? = null,
    className: String? = null,
    modifier: String? = null,
    maskColor: String? = null,
    callback: (() -> Unit)? = null
): Promise<Int> {
    val options = optionsObj(
        title,
        buttonLabels,
        primaryButtonIndex,
        cancelable,
        animation,
        id,
        className,
        modifier,
        maskColor,
        callback
    )
    @Suppress("UnsafeCastFromDynamic")
    return ons.notification.alert(message, options)
}

/**
 * Displays confirm notification.
 * @param message a notification message
 * @param title a window title
 * @param buttonLabels labels of the buttons
 * @param primaryButtonIndex an index of the default button
 * @param cancelable whether the notification can be cancelled
 * @param animation whether the notification is animated
 * @param id the id of the notification HTML element
 * @param className the CSS class of the notification HTML element
 * @param modifier a modifier attribute to specify custom styles
 * @param maskColor a color of the background mask
 * @param callback a callback function called after the dialog has been closed
 */
fun showConfirm(
    message: String,
    title: String? = null,
    buttonLabels: List<String>? = null,
    primaryButtonIndex: Int? = null,
    cancelable: Boolean = false,
    animation: Boolean = true,
    id: String? = null,
    className: String? = null,
    modifier: String? = null,
    maskColor: String? = null,
    callback: (() -> Unit)? = null
): Promise<Int> {
    val options = optionsObj(
        title,
        buttonLabels,
        primaryButtonIndex,
        cancelable,
        animation,
        id,
        className,
        modifier,
        maskColor,
        callback
    )
    @Suppress("UnsafeCastFromDynamic")
    return ons.notification.confirm(message, options)
}

/**
 * Displays prompt notification.
 * @param message a notification message
 * @param title a window title
 * @param defaultValue a default value for the input
 * @param placeholder a placeholder for the input
 * @param inputType an input type
 * @param buttonLabels labels of the buttons
 * @param primaryButtonIndex an index of the default button
 * @param autofocus whether the input element is focused
 * @param submitOnEnter close automatically when the ENTER key is pressed
 * @param cancelable whether the notification can be cancelled
 * @param animation whether the notification is animated
 * @param id the id of the notification HTML element
 * @param className the CSS class of the notification HTML element
 * @param modifier a modifier attribute to specify custom styles
 * @param maskColor a color of the background mask
 * @param callback a callback function called after the dialog has been closed
 */
fun showPrompt(
    message: String,
    title: String? = null,
    defaultValue: String? = null,
    placeholder: String? = null,
    inputType: String? = null,
    buttonLabels: List<String>? = null,
    primaryButtonIndex: Int? = null,
    autofocus: Boolean = true,
    submitOnEnter: Boolean = true,
    cancelable: Boolean = false,
    animation: Boolean = true,
    id: String? = null,
    className: String? = null,
    modifier: String? = null,
    maskColor: String? = null,
    callback: (() -> Unit)? = null
): Promise<String?> {
    val options = optionsObj(
        title,
        buttonLabels,
        primaryButtonIndex,
        cancelable,
        animation,
        id,
        className,
        modifier,
        maskColor,
        callback
    )
    defaultValue?.let {
        options.defaultValue = it
    }
    placeholder?.let {
        options.placeholder = it
    }
    inputType?.let {
        options.inputType = it
    }
    if (!autofocus) options.autofocus = false
    if (!submitOnEnter) options.submitOnEnter = false
    @Suppress("UnsafeCastFromDynamic")
    return ons.notification.prompt(message, options)
}

private fun optionsObj(
    title: String? = null,
    buttonLabels: List<String>? = null,
    primaryButtonIndex: Int? = null,
    cancelable: Boolean = false,
    animation: Boolean = true,
    id: String? = null,
    className: String? = null,
    modifier: String? = null,
    maskColor: String? = null,
    callback: (() -> Unit)? = null
): dynamic {
    return obj {
        title?.let {
            this.title = it
        }
        buttonLabels?.let {
            this.buttonLabels = it.toTypedArray()
        }
        primaryButtonIndex?.let {
            this.primaryButtonIndex = it
        }
        if (cancelable) this.cancelable = true
        if (!animation) this.animation = "none"
        id?.let {
            this.id = it
        }
        className?.let {
            this.`class` = it
        }
        modifier?.let {
            this.modifier = it
        }
        maskColor?.let {
            this.maskColor = it
        }
        callback?.let {
            this.callback = it
        }
    }
}

/**
 * Displays toast notification.
 * @param message a notification message
 * @param buttonLabel a label of the button
 * @param animation the animation type
 * @param timeout a number of milliseconds where the toast is visible before hiding automatically
 * @param force whether toast skips the notification queue and is shown immediately
 * @param id the id of the notification HTML element
 * @param className the CSS class of the notification HTML element
 * @param modifier a modifier attribute to specify custom styles
 * @param callback a callback function called after the toast has been closed
 */
fun showToast(
    message: String,
    buttonLabel: String? = null,
    animation: ToastAnimation? = null,
    timeout: Int? = 2000,
    force: Boolean = false,
    id: String? = null,
    className: String? = null,
    modifier: String? = null,
    callback: (() -> Unit)? = null
): Promise<Int> {
    val options = obj {
        buttonLabel?.let {
            this.buttonLabel = it
        }
        animation?.let {
            this.animation = it.type
        }
        timeout?.let {
            this.timeout = it
        }
        if (force) this.force = true
        id?.let {
            this.id = it
        }
        className?.let {
            this.`class` = it
        }
        modifier?.let {
            this.modifier = it
        }
        callback?.let {
            this.callback = it
        }
    }
    @Suppress("UnsafeCastFromDynamic")
    return ons.notification.toast(message, options)
}

/**
 * Displays a dynamic action sheet.
 * @param title a title of the action sheet
 * @param cancelable whether the action sheet can be canceled
 * @param animation determines if the transitions are animated
 */
fun showActionSheet(
    title: String? = null,
    cancelable: Boolean? = null,
    animation: Boolean? = null,
    buttons: List<dynamic>? = null
): Promise<Int> {
    val options = obj {
        this.title = title
        cancelable?.let {
            this.cancelable = it
        }
        animation?.let {
            this.animation = it
        }
        buttons?.let {
            this.buttons = it.toTypedArray()
        }
    }
    @Suppress("UnsafeCastFromDynamic")
    return ons.openActionSheet(options)
}
