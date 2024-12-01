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
package io.kvision.form.number

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.FieldLabel
import io.kvision.form.FormHorizontalRatio
import io.kvision.form.InvalidFeedback
import io.kvision.form.NumberFormControl
import io.kvision.i18n.I18n
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.utils.SnOn

/**
 * The form field component for the numeric control with masked input.
 *
 * @constructor
 * @param value numeric value
 * @param name the name attribute of the generated HTML input element
 * @param min minimal value
 * @param max maximal value
 * @param decimals the number of decimal digits (default 2)
 * @param decimalSeparator the decimal separator (default: auto detect)
 * @param thousandsSeparator the thousands separator
 * @param padFractionalZeros pads fractional zeros at end to the length of scale
 * @param normalizeZeros appends or removes zeros at the end
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
open class ImaskNumeric(
    value: Number? = null,
    name: String? = null,
    min: Number? = null,
    max: Number? = null,
    decimals: Int = 2,
    decimalSeparator: Char = I18n.detectDecimalSeparator(),
    thousandsSeparator: Char? = I18n.detectThousandsSeparator(),
    padFractionalZeros: Boolean = false,
    normalizeZeros: Boolean = true,
    label: String? = null,
    rich: Boolean = false,
    init: (ImaskNumeric.() -> Unit)? = null
) : SimplePanel("form-group kv-mb-3"), NumberFormControl, MutableState<Number?> {

    /**
     * Numeric value.
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
     * bound to the numeric input value.
     */
    var startValue
        get() = input.startValue
        set(value) {
            input.startValue = value
        }

    /**
     * Minimal value.
     */
    var min
        get() = input.min
        set(value) {
            input.min = value
        }

    /**
     * Maximal value.
     */
    var max
        get() = input.max
        set(value) {
            input.max = value
        }

    /**
     * The number of decimal digits.
     */
    var decimals
        get() = input.decimals
        set(value) {
            input.decimals = value
        }

    /**
     * The decimal separator.
     */
    var decimalSeparator
        get() = input.decimalSeparator
        set(value) {
            input.decimalSeparator = value
        }

    /**
     * The thousands separator.
     */
    var thousandsSeparator
        get() = input.thousandsSeparator
        set(value) {
            input.thousandsSeparator = value
        }

    /**
     * Pads fractional zeros at end to the length of scale.
     */
    var padFractionalZeros
        get() = input.padFractionalZeros
        set(value) {
            input.padFractionalZeros = value
        }

    /**
     * Appends or removes zeros at the end.
     */
    var normalizeZeros
        get() = input.normalizeZeros
        set(value) {
            input.normalizeZeros = value
        }

    /**
     * The placeholder for the numeric input.
     */
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }

    /**
     * Determines if the numeric input is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }

    /**
     * Determines if the numeric input is read-only.
     */
    var readonly
        get() = input.readonly
        set(value) {
            input.readonly = value
        }

    /**
     * The label text bound to the numeric input element.
     */
    var label
        get() = flabel.content
        set(value) {
            flabel.content = value
        }

    /**
     * Determines if [label] can contain HTML code.
     */
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
        }

    override var validatorError: String?
        get() = super.validatorError
        set(value) {
            super.validatorError = value
            if (value != null) {
                input.addSurroundingCssClass("is-invalid")
            } else {
                input.removeSurroundingCssClass("is-invalid")
            }
        }

    protected val idc = "kv_form_imask_numeric_$counter"
    final override val input: ImaskNumericInput =
        ImaskNumericInput(
            value,
            min,
            max,
            decimals,
            decimalSeparator,
            thousandsSeparator,
            padFractionalZeros,
            normalizeZeros
        ).apply {
            this.id = this@ImaskNumeric.idc
            this.name = name
        }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, "form-label")
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        counter++
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (validatorError != null) {
            classSetBuilder.add("kv-text-danger")
        }
    }

    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Int {
        return input.setEventListener(block)
    }

    override fun removeEventListener(id: Int) {
        input.removeEventListener(id)
    }

    override fun removeEventListeners() {
        input.removeEventListeners()
    }

    override fun getValueAsString(): String? {
        return input.getValueAsString()
    }

    override fun focus() {
        input.focus()
    }

    override fun blur() {
        input.blur()
    }

    override fun styleForHorizontalFormPanel(horizontalRatio: FormHorizontalRatio) {
        addCssClass("row")
        addCssClass("kv-control-horiz")
        flabel.addCssClass("col-sm-${horizontalRatio.labels}")
        flabel.addCssClass("col-form-label")
        input.addSurroundingCssClass("col-sm-${horizontalRatio.fields}")
        invalidFeedback.addCssClass("offset-sm-${horizontalRatio.labels}")
        invalidFeedback.addCssClass("col-sm-${horizontalRatio.fields}")
    }

    override fun getState(): Number? = input.getState()

    override fun subscribe(observer: (Number?) -> Unit): () -> Unit {
        return input.subscribe(observer)
    }

    override fun setState(state: Number?) {
        input.setState(state)
    }

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.imaskNumeric(
    value: Number? = null, name: String? = null, min: Number? = null, max: Number? = null,
    decimals: Int = 2, decimalSeparator: Char = I18n.detectDecimalSeparator(),
    thousandsSeparator: Char? = I18n.detectThousandsSeparator(),
    padFractionalZeros: Boolean = false, normalizeZeros: Boolean = true,
    label: String? = null, rich: Boolean = false,
    init: (ImaskNumeric.() -> Unit)? = null
): ImaskNumeric {
    val imaskNumeric =
        ImaskNumeric(
            value,
            name,
            min,
            max,
            decimals,
            decimalSeparator,
            thousandsSeparator,
            padFractionalZeros,
            normalizeZeros,
            label,
            rich,
            init
        )
    this.add(imaskNumeric)
    return imaskNumeric
}
