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
package pl.treksoft.kvision.form.spinner

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.form.NumberFormControl
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.SnOn

/**
 * The form field component for spinner control.
 *
 * @constructor
 * @param value spinner value
 * @param min minimal value (default 0)
 * @param max maximal value (default 100)
 * @param step step value (default 1)
 * @param decimals number of decimal digits (default 0)
 * @param buttonsType spinner buttons type
 * @param forceType spinner force rounding type
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
open class Spinner(
    value: Number? = null, min: Int = 0, max: Int = DEFAULT_MAX, step: Double = DEFAULT_STEP,
    decimals: Int = 0, buttonsType: ButtonsType = ButtonsType.VERTICAL,
    forceType: ForceType = ForceType.NONE, label: String? = null,
    rich: Boolean = false
) : SimplePanel(setOf("form-group")), NumberFormControl {

    /**
     * Spinner value.
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
     * bound to the spinner input value.
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
     * Step value.
     */
    var step
        get() = input.step
        set(value) {
            input.step = value
        }
    /**
     * Number of decimal digits value.
     */
    var decimals
        get() = input.decimals
        set(value) {
            input.decimals = value
        }
    /**
     * Spinner buttons type.
     */
    var buttonsType
        get() = input.buttonsType
        set(value) {
            input.buttonsType = value
        }
    /**
     * Spinner force rounding type.
     */
    var forceType
        get() = input.forceType
        set(value) {
            input.forceType = value
        }
    /**
     * The placeholder for the spinner input.
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
    override var disabled
        get() = input.disabled
        set(value) {
            input.disabled = value
        }
    /**
     * Determines if the spinner is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }
    /**
     * Determines if the spinner is read-only.
     */
    var readonly
        get() = input.readonly
        set(value) {
            input.readonly = value
        }
    /**
     * The label text bound to the spinner input element.
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

    protected val idc = "kv_form_spinner_" + counter
    final override val input: SpinnerInput = SpinnerInput(value, min, max, step, decimals, buttonsType, forceType)
        .apply { id = idc }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich)
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(flabel)
        this.addInternal(input)
        this.addInternal(validationInfo)
        counter++
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

    override fun getValueAsString(): String? {
        return input.getValueAsString()
    }

    /**
     * Change value in plus.
     */
    open fun spinUp(): Spinner {
        input.spinUp()
        return this
    }

    /**
     * Change value in minus.
     */
    open fun spinDown(): Spinner {
        input.spinDown()
        return this
    }

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.spinner(
            value: Number? = null, min: Int = 0, max: Int = DEFAULT_MAX, step: Double = DEFAULT_STEP,
            decimals: Int = 0, buttonsType: ButtonsType = ButtonsType.VERTICAL,
            forceType: ForceType = ForceType.NONE, label: String? = null,
            rich: Boolean = false, init: (Spinner.() -> Unit)? = null
        ): Spinner {
            val spinner = Spinner(value, min, max, step, decimals, buttonsType, forceType, label, rich).apply {
                init?.invoke(
                    this
                )
            }
            this.add(spinner)
            return spinner
        }
    }
}
