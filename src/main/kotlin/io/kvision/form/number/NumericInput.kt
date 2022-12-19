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

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.i18n.I18n
import io.kvision.snabbdom.VNode
import io.kvision.state.MutableState
import io.kvision.utils.obj
import io.kvision.utils.toFixedNoRound

/**
 * Numeric input component.
 *
 * @constructor
 * @param value numeric input value
 * @param min minimal value
 * @param max maximal value
 * @param decimals the number of decimal digits (default 2)
 * @param decimalSeparator the decimal separator (default: auto detect)
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class NumericInput(
    value: Number? = null, min: Number? = null, max: Number? = null, decimals: Int = 2,
    decimalSeparator: String = I18n.detectDecimalSeparator(),
    className: String? = null, init: (NumericInput.() -> Unit)? = null
) : Widget((className?.let { "$it " } ?: "") + "form-control"), GenericFormComponent<Number?>, FormInput,
    MutableState<Number?> {

    protected val observers = mutableListOf<(Number?) -> Unit>()

    /**
     * Spinner input value.
     */
    override var value by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(it) } }

    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the numeric input value.
     */
    var startValue by refreshOnUpdate(value) { this.value = it; refresh() }

    /**
     * Minimal value.
     */
    var min by refreshOnUpdate(min)

    /**
     * Maximal value.
     */
    var max by refreshOnUpdate(max)

    /**
     * The number of decimal digits.
     */
    var decimals by refreshOnUpdate(decimals)

    /**
     * The decimal separator.
     */
    var decimalSeparator by refreshOnUpdate(decimalSeparator)

    /**
     * The placeholder for the numeric input.
     */
    var placeholder: String? by refreshOnUpdate()

    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()

    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false)

    /**
     * Determines if the numeric input is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * Determines if the numeric input is read-only.
     */
    var readonly: Boolean? by refreshOnUpdate()

    /**
     * The size of the input.
     */
    override var size: InputSize? by refreshOnUpdate()

    /**
     * The validation status of the input.
     */
    override var validationStatus: ValidationStatus? by refreshOnUpdate()

    init {
        useSnabbdomDistinctKey()
        this.setInternalEventListener<NumericInput> {
            input = {
                self.changeValue()
            }
            blur = {
                self.formatValue()
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("input")
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(validationStatus)
        classSetBuilder.add(size)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", "text")
        attributeSetBuilder.add("maxlength", "14")
        val decimalSeparatorRegex = if (decimalSeparator == ".") "\\." else decimalSeparator
        val pattern = if (decimals > 0) {
            "^-?(\\d+($decimalSeparatorRegex\\d{1,$decimals})?)\$"
        } else {
            "^-?(\\d+)\$"
        }
        attributeSetBuilder.add("pattern", pattern)
        startValue?.let {
            attributeSetBuilder.add("value", "$it")
        }
        placeholder?.let {
            attributeSetBuilder.add("placeholder", translate(it))
        }
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        autofocus?.let {
            if (it) {
                attributeSetBuilder.add("autofocus")
            }
        }
        readonly?.let {
            if (it) {
                attributeSetBuilder.add("readonly")
            }
        }
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
    }

    override fun afterInsert(node: VNode) {
        formatValue()
    }

    /**
     * Returns the value of the numeric input as a String.
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.toString()
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun refreshState() {
        getElementD()?.value = value?.toString()?.replace(".", decimalSeparator) ?: ""
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun changeValue() {
        val v = getElementD()?.value?.unsafeCast<String>()
        if (v != null && v != "") {
            val newValue = v.replace(decimalSeparator, ".").toDoubleOrNull()?.let {
                if (min != null && it < (min?.toDouble() ?: 0.0))
                    min
                else if (max != null && it > (max?.toDouble() ?: 0.0))
                    max
                else it
            }
            if (this.value != newValue && newValue != null && v != "-0" && v != "-0$decimalSeparator" && v != "-0.") {
                this.value = newValue
            }
        } else {
            this.value = null
        }
    }

    protected open fun formatValue() {
        if (value != null) {
            value = value?.toFixedNoRound(decimals)?.toDouble()
        }
        val formattedValue = if (value != null) {
            value?.asDynamic().toLocaleString(undefined, obj {
                minimumFractionDigits = decimals
                maximumFractionDigits = decimals
                useGrouping = false
                roundingMode = "trunc"
            }).unsafeCast<String>().replace(I18n.detectDecimalSeparator(), decimalSeparator)
        } else ""
        getElementD()?.value = formattedValue
    }

    override fun getState(): Number? = value

    override fun subscribe(observer: (Number?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    override fun setState(state: Number?) {
        value = state
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.numericInput(
    value: Number? = null, min: Number? = null, max: Number? = null,
    decimals: Int = 2, decimalSeparator: String = I18n.detectDecimalSeparator(),
    className: String? = null,
    init: (NumericInput.() -> Unit)? = null
): NumericInput {
    val numericInput = NumericInput(value, min, max, decimals, decimalSeparator, className, init)
    this.add(numericInput)
    return numericInput
}
