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
import io.kvision.snabbdom.VNode
import io.kvision.state.MutableState
import org.w3c.dom.HTMLInputElement

internal const val SPINNER_DEFAULT_STEP = 1

/**
 * Spinner input component.
 *
 * @constructor
 * @param value spinner input value
 * @param min minimal value
 * @param max maximal value
 * @param step step value (default 1)
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class SpinnerInput(
    value: Number? = null, min: Long? = null, max: Long? = null, step: Int = SPINNER_DEFAULT_STEP,
    className: String? = null, init: (SpinnerInput.() -> Unit)? = null
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
     * bound to the spinner input value.
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
     * Step value.
     */
    var step by refreshOnUpdate(step)

    /**
     * The placeholder for the spinner input.
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
     * Determines if the spinner input is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * Determines if the spinner input is read-only.
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
        val numberRegex = Regex("\\d")
        this.setInternalEventListener<SpinnerInput> {
            keydown = {
                val fieldValue = it.currentTarget.unsafeCast<HTMLInputElement>().value
                if (!(it.key.length > 1 || it.ctrlKey ||
                        (it.key == "-" && fieldValue.isEmpty() && (self.min == null || self.min!! < 0)) ||
                        (numberRegex.matches(it.key) && fieldValue.dropWhile { it == '-' }.length < 18))
                ) it.preventDefault()
            }
            input = {
                self.changeValue()
            }
            blur = {
                self.changeValue()
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
        attributeSetBuilder.add("type", "number")
        startValue?.let {
            attributeSetBuilder.add("value", "$it")
        }
        placeholder?.let {
            attributeSetBuilder.add("placeholder", translate(it))
        }
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        min?.let {
            attributeSetBuilder.add("min", "$min")
        }
        max?.let {
            attributeSetBuilder.add("max", "$max")
        }
        attributeSetBuilder.add("step", "$step")
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
        refreshState()
    }

    /**
     * Returns the value of the spinner as a String.
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.toString()
    }

    /**
     * Change value in plus.
     */
    open fun spinUp() {
        (getElement() as? HTMLInputElement)?.stepUp()
    }

    /**
     * Change value in minus.
     */
    open fun spinDown() {
        (getElement() as? HTMLInputElement)?.stepDown()
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun refreshState() {
        getElementD()?.value = value?.toLong() ?: ""
        changeValue()
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun changeValue() {
        val v = getElementD()?.value?.unsafeCast<String>()
        if (v != null && v != "") {
            val newValue = v.toLongOrNull()?.let {
                if (min != null && it < (min ?: 0))
                    min
                else if (max != null && it > (max ?: 0))
                    max
                else it
            }
            if (this.value != newValue) this.value = newValue
        } else {
            this.value = null
        }
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
fun Container.spinnerInput(
    value: Number? = null, min: Long? = null, max: Long? = null, step: Int = SPINNER_DEFAULT_STEP,
    className: String? = null,
    init: (SpinnerInput.() -> Unit)? = null
): SpinnerInput {
    val spinnerInput = SpinnerInput(value, min, max, step, className, init)
    this.add(spinnerInput)
    return spinnerInput
}
