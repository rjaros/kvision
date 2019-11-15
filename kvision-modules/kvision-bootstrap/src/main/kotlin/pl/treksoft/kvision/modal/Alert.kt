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

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.ButtonStyle
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.utils.ENTER_KEY

/**
 * Alert window based on Bootstrap modal.
 *
 * @constructor
 * @param caption window title
 * @param text window content text.
 * @param rich determines if [text] can contain HTML code
 * @param align text align
 * @param size modal window size
 * @param animation determines if animations are used
 * @param callback a function called after closing window with OK button
 */
open class Alert(
    caption: String? = null, text: String? = null, rich: Boolean = false,
    align: Align? = null, size: ModalSize? = null, animation: Boolean = true,
    private val callback: (() -> Unit)? = null
) : Modal(caption, true, size, animation) {

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

    private val contentTag = Tag(TAG.DIV, text, rich, align)

    init {
        body.add(contentTag)
        val okButton = Button("OK", "fas fa-check", ButtonStyle.PRIMARY)
        okButton.setEventListener<Button> {
            click = {
                hide()
            }
        }
        this.addButton(okButton)
        this.setEventListener<Button> {
            keydown = { e ->
                if (e.keyCode == ENTER_KEY) {
                    hide()
                }
            }
        }
    }

    override fun hide(): Widget {
        super.hide()
        this.callback?.invoke()
        return this
    }

    companion object {
        /**
         * Helper function for opening Alert window.
         * @param caption window title
         * @param text window content text.
         * @param rich determines if [text] can contain HTML code
         * @param align text align
         * @param size modal window size
         * @param animation determines if animations are used
         * @param callback a function called after closing window with OK button
         */
        @Suppress("LongParameterList")
        fun show(
            caption: String? = null, text: String? = null, rich: Boolean = false,
            align: Align? = null, size: ModalSize? = null, animation: Boolean = true,
            callback: (() -> Unit)? = null
        ) {
            Alert(caption, text, rich, align, size, animation, callback).show()
        }
    }
}
