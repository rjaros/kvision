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
package pl.treksoft.kvision.form.text

import pl.treksoft.kvision.core.Container

/**
 * Form field textarea component.
 *
 * @constructor
 * @param cols number of columns
 * @param rows number of rows
 * @param value text input value
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
open class TextArea(
    cols: Int? = null, rows: Int? = null, value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false
) : AbstractText(label, rich) {

    /**
     * Number of columns.
     */
    var cols
        get() = input.cols
        set(value) {
            input.cols = value
        }
    /**
     * Number of rows.
     */
    var rows
        get() = input.rows
        set(value) {
            input.rows = value
        }
    /**
     * Determines if hard wrapping is enabled for the textarea element.
     */
    var wrapHard
        get() = input.wrapHard
        set(value) {
            input.wrapHard = value
        }

    final override val input: TextAreaInput = TextAreaInput(cols, rows, value).apply {
        this.id = idc
        this.name = name
    }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(input)
        this.addInternal(invalidFeedback)
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.textArea(
            cols: Int? = null, rows: Int? = null, value: String? = null, name: String? = null,
            label: String? = null, rich: Boolean = false, init: (TextArea.() -> Unit)? = null
        ): TextArea {
            val textArea = TextArea(cols, rows, value, name, label, rich).apply { init?.invoke(this) }
            this.add(textArea)
            return textArea
        }
    }
}
