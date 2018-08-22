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
package pl.treksoft.kvision.modal

import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.ButtonStyle
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

/**
 * Confirm window based on Bootstrap modal.
 *
 * @constructor
 * @param caption window title
 * @param text window content text.
 * @param rich determines if [text] can contain HTML code
 * @param align text align
 * @param size modal window size
 * @param animation determines if animations are used
 * @param cancelVisible determines if Cancel button is visible
 * @param yesTitle yes button text
 * @param noTitle no button text
 * @param cancelTitle cancel button text
 * @param noCallback a function called after closing window with No button
 * @param yesCallback a function called after closing window with Yes button
 */
open class Confirm(
    caption: String? = null, text: String? = null, rich: Boolean = false,
    align: Align? = null, size: ModalSize? = null, animation: Boolean = true,
    cancelVisible: Boolean = false, yesTitle: String = "Yes", noTitle: String = "No", cancelTitle: String = "Cancel",
    private val noCallback: (() -> Unit)? = null,
    private val yesCallback: (() -> Unit)? = null
) : Modal(caption, false, size, animation, false) {
    /**
     * Window content text.
     */
    var text
        get() = contentTag.content
        set(value) {
            contentTag.content = value
        }
    /**
     * Determines if [text] can contain HTML code.
     */
    var rich
        get() = contentTag.rich
        set(value) {
            contentTag.rich = value
        }
    /**
     * Text align.
     */
    var align
        get() = contentTag.align
        set(value) {
            contentTag.align = value
        }
    /**
     * Determines if Cancel button is visible.
     */
    var cancelVisible by refreshOnUpdate(cancelVisible, { refreshCancelButton() })

    /**
     * Yes button text.
     */
    var yesTitle
        get() = yesButton.text
        set(value) {
            yesButton.text = value
        }

    /**
     * No button text.
     */
    var noTitle
        get() = noButton.text
        set(value) {
            noButton.text = value
        }

    /**
     * Cancel button text.
     */
    var cancelTitle
        get() = cancelButton.text
        set(value) {
            cancelButton.text = value
        }

    private val contentTag = Tag(TAG.DIV, text, rich, align)
    private val cancelButton = Button(cancelTitle, "remove")
    private val noButton = Button(noTitle, "ban-circle")
    private val yesButton = Button(yesTitle, "ok", ButtonStyle.PRIMARY)

    init {
        body.add(contentTag)
        cancelButton.setEventListener {
            click = {
                hide()
            }
        }
        this.addButton(cancelButton)
        noButton.setEventListener {
            click = {
                hide()
                noCallback?.invoke()
            }
        }
        this.addButton(noButton)
        yesButton.setEventListener {
            click = {
                hide()
                yesCallback?.invoke()
            }
        }
        this.addButton(yesButton)
        refreshCancelButton()
    }

    private fun refreshCancelButton() {
        if (cancelVisible) {
            cancelButton.show()
            closeIcon.show()
        } else {
            cancelButton.hide()
            closeIcon.hide()
        }
    }

    companion object {
        /**
         * Helper function for opening Confirm window.
         * @param caption window title
         * @param text window content text.
         * @param rich determines if [text] can contain HTML code
         * @param align text align
         * @param size modal window size
         * @param animation determines if animations are used
         * @param cancelVisible determines if Cancel button is visible
         * @param noCallback a function called after closing window with No button
         * @param yesCallback a function called after closing window with Yes button
         */
        @Suppress("LongParameterList")
        fun show(
            caption: String? = null, text: String? = null, rich: Boolean = false,
            align: Align? = null, size: ModalSize? = null, animation: Boolean = true,
            cancelVisible: Boolean = false, yesTitle: String = "Yes", noTitle: String = "No",
            cancelTitle: String = "Cancel", noCallback: (() -> Unit)? = null, yesCallback: (() -> Unit)? = null
        ) {
            Confirm(
                caption, text, rich, align, size, animation, cancelVisible, yesTitle, noTitle, cancelTitle,
                noCallback, yesCallback
            ).show()
        }
    }
}
