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

import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.form.StringFormControl
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.SnOn

/**
 * Base class for form field text components.
 *
 * @constructor
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
abstract class AbstractText(label: String? = null, rich: Boolean = false) :
    SimplePanel(setOf("form-group")), StringFormControl {

    /**
     * Text input value.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }
    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the text input value.
     */
    var startValue
        get() = input.startValue
        set(value) {
            input.startValue = value
        }
    /**
     * The placeholder for the text input.
     */
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }
    /**
     * The name attribute of the generated HTML input element.
     */
    var name
        get() = input.name
        set(value) {
            input.name = value
        }
    /**
     * Maximal length of the text input value.
     */
    var maxlength
        get() = input.maxlength
        set(value) {
            input.maxlength = value
        }
    override var disabled
        get() = input.disabled
        set(value) {
            input.disabled = value
        }
    /**
     * Determines if the text input is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }
    /**
     * Determines if the text input is read-only.
     */
    var readonly
        get() = input.readonly
        set(value) {
            input.readonly = value
        }
    /**
     * The label text bound to the text input element.
     */
    var label
        get() = flabel.text
        set(value) {
            flabel.text = value
        }
    /**
     * Determines if [label] can contain HTML code.
     */
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
        }
    override var size
        get() = input.size
        set(value) {
            input.size = value
        }

    /**
     * @suppress
     * Internal property
     */
    protected val idc = "kv_form_text_$counter"
    abstract override val input: AbstractTextInput
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich)
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        this.addInternal(flabel)
        counter++
    }

    companion object {
        internal var counter = 0
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (validatorError != null) {
            cl.add("has-error" to true)
        }
        return cl
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Widget {
        input.setEventListener(block)
        return this
    }

    override fun setEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        input.setEventListener(block)
        return this
    }

    override fun removeEventListeners(): Widget {
        input.removeEventListeners()
        return this
    }

    override fun focus() {
        input.focus()
    }

    override fun blur() {
        input.blur()
    }
}
