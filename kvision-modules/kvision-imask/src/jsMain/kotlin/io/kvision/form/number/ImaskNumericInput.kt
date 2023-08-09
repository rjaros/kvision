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

import io.kvision.ImaskModule
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.form.text.ImaskOptions
import io.kvision.form.text.Mask
import io.kvision.form.text.MaskManager
import io.kvision.form.text.NumberMask
import io.kvision.i18n.I18n
import io.kvision.snabbdom.VNode
import io.kvision.state.MutableState
import org.w3c.dom.HTMLElement

/**
 * Numeric input component with masked input.
 *
 * @constructor
 * @param value numeric input value
 * @param min minimal value
 * @param max maximal value
 * @param decimals the number of decimal digits (default 2)
 * @param decimalSeparator the decimal separator (default: auto detect)
 * @param thousandsSeparator the thousands separator (default: auto detect)
 * @param padFractionalZeros pads fractional zeros at end to the length of scale
 * @param normalizeZeros appends or removes zeros at the end
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class ImaskNumericInput(
    value: Number? = null, min: Number? = null, max: Number? = null, decimals: Int = 2,
    decimalSeparator: Char = I18n.detectDecimalSeparator(), thousandsSeparator: Char? = I18n.detectThousandsSeparator(),
    padFractionalZeros: Boolean = false, normalizeZeros: Boolean = true,
    className: String? = null, init: (ImaskNumericInput.() -> Unit)? = null
) : Widget((className?.let { "$it " } ?: "") + "form-control"), GenericFormComponent<Number?>, FormInput,
    MutableState<Number?> {

    protected val observers = mutableListOf<(Number?) -> Unit>()

    /**
     * Spinner input value.
     */
    override var value: Number? by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(this.value) } }

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
    var min by refreshOnUpdate(min) { refreshState() }

    /**
     * Maximal value.
     */
    var max by refreshOnUpdate(max) { refreshState() }

    /**
     * The number of decimal digits.
     */
    var decimals by refreshOnUpdate(decimals) { refreshState() }

    /**
     * The decimal separator.
     */
    var decimalSeparator by refreshOnUpdate(decimalSeparator) { refreshState() }

    /**
     * The thousands separator.
     */
    var thousandsSeparator by refreshOnUpdate(thousandsSeparator) { refreshState() }

    /**
     * Pads fractional zeros at end to the length of scale.
     */
    var padFractionalZeros by refreshOnUpdate(padFractionalZeros) { refreshState() }

    /**
     * Appends or removes zeros at the end.
     */
    var normalizeZeros by refreshOnUpdate(normalizeZeros) { refreshState() }

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

    /**
     * The input mask controller.
     */
    protected var mask: Mask? = null

    /**
     * Temporary component value.
     */
    protected var tempValue: Number? = null
        set(value) {
            field = value
            observers.forEach { ob -> ob(value) }
        }

    init {
        useSnabbdomDistinctKey()
        this.setInternalEventListener<ImaskNumericInput> {
            blur = {
                if (self.value != tempValue) self.value = tempValue
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
        installMask()
        refreshState()
    }

    override fun afterDestroy() {
        uninstallMask()
        if (tempValue != null) {
            if (this.value != tempValue) this.value = tempValue
            tempValue = null
        }
    }

    /**
     * Returns the value of the numeric input as a String.
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.toString()
    }

    protected open fun refreshState() {
        val newValue = value?.toString()?.replace('.', decimalSeparator) ?: ""
        if (getElementD()?.value != newValue) {
            getElementD()?.value = newValue
            if (mask != null) {
                mask!!.refresh()
                val v = mask?.getValue()?.toDoubleOrNull()
                if (this.value != v) {
                    this.value = v
                }
            }
        }
    }

    /**
     * Install the input mask controller.
     */
    open fun installMask() {
        if (getElement() != null) {
            mask = MaskManager.factory!!.createMask(
                getElement().unsafeCast<HTMLElement>(),
                ImaskOptions(
                    number = NumberMask(
                        scale = decimals,
                        thousandsSeparator = thousandsSeparator,
                        padFractionalZeros = padFractionalZeros,
                        normalizeZeros = normalizeZeros,
                        radix = decimalSeparator,
                        mapToRadix = listOf('.'),
                        min = min,
                        max = max
                    )
                )
            )
            mask!!.onChange {
                val newValue = it?.toDoubleOrNull()
                if (tempValue != newValue) tempValue = newValue
            }
        }
    }

    /**
     * Uninstall the input mask controller.
     */
    open fun uninstallMask() {
        mask?.destroy()
        mask = null
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

    companion object {
        init {
            ImaskModule.initialize()
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.imaskNumericInput(
    value: Number? = null, min: Number? = null, max: Number? = null,
    decimals: Int = 2, decimalSeparator: Char = I18n.detectDecimalSeparator(),
    thousandsSeparator: Char? = I18n.detectThousandsSeparator(),
    padFractionalZeros: Boolean = false, normalizeZeros: Boolean = true,
    className: String? = null,
    init: (ImaskNumericInput.() -> Unit)? = null
): ImaskNumericInput {
    val imaskNumericInput = ImaskNumericInput(
        value,
        min,
        max,
        decimals,
        decimalSeparator,
        thousandsSeparator,
        padFractionalZeros,
        normalizeZeros,
        className,
        init
    )
    this.add(imaskNumericInput)
    return imaskNumericInput
}
