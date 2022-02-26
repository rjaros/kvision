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
package io.kvision.form.text

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Widget
import io.kvision.form.FieldLabel
import io.kvision.form.FormHorizontalRatio
import io.kvision.form.InvalidFeedback
import io.kvision.form.StringFormControl
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.utils.SnOn

/**
 * Base class for form field text components.
 *
 * @constructor
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param floating use floating label
 * @param className CSS class names
 */
abstract class AbstractText(
    label: String? = null,
    rich: Boolean = false,
    val floating: Boolean = false,
    className: String? = null
) :
    SimplePanel((className?.let { "$it " } ?: "") + if (floating) "form-floating kv-mb-3" else "form-group kv-mb-3"),
    StringFormControl, MutableState<String?> {

    /**
     * Text input value.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value?.ifEmpty { null }
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
     * Maximal length of the text input value.
     */
    var maxlength
        get() = input.maxlength
        set(value) {
            input.maxlength = value
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
        get() = flabel.content
        set(value) {
            flabel.content = value
            flabel.visible = value != null
        }

    /**
     * Determines if [label] can contain HTML code.
     */
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
        }

    /**
     * @suppress
     * Internal property
     */
    protected val idc = "kv_form_text_$counter"
    abstract override val input: AbstractTextInput
    final override val flabel: FieldLabel =
        FieldLabel(idc, label, rich, "form-label").apply { visible = label != null }
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        counter++
    }

    companion object {
        internal var counter = 0
    }

    protected fun floatingPlaceholder() {
        if (floating && placeholder == null && label != null) placeholder = label ?: "Enter data"
    }

    override fun styleForHorizontalFormPanel(horizontalRatio: FormHorizontalRatio) {
        if (!floating) {
            addCssClass("row")
            addCssClass("kv-control-horiz")
            flabel.addCssClass("col-sm-${horizontalRatio.labels}")
            flabel.addCssClass("col-form-label")
            input.addSurroundingCssClass("col-sm-${horizontalRatio.fields}")
            invalidFeedback.addCssClass("offset-sm-${horizontalRatio.labels}")
            invalidFeedback.addCssClass("col-sm-${horizontalRatio.fields}")
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (validatorError != null) {
            classSetBuilder.add("text-danger")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Int {
        return input.setEventListener(block)
    }

    override fun removeEventListener(id: Int): Widget {
        input.removeEventListener(id)
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

    override fun getState(): String? = input.getState()

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        return input.subscribe(observer)
    }

    override fun setState(state: String?) {
        input.setState(state)
    }
}
