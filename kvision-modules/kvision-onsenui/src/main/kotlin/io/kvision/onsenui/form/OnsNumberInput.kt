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

package io.kvision.onsenui.form

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.core.getElementJQuery
import io.kvision.core.getElementJQueryD
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.state.MutableState
import io.kvision.utils.set

internal const val DEFAULT_STEP = 1

/**
 * OnsenUI number input component.
 *
 * @constructor Creates a number input component.
 * @param value number input value
 * @param min minimal value
 * @param max maximal value
 * @param step step value (default 1)
 * @param placeholder the placeholder for the number input
 * @param floatLabel whether the placeholder will be animated in Material Design
 * @param inputId the ID of the input element
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsNumberInput(
    value: Number? = null,
    min: Number? = null,
    max: Number? = null,
    step: Number = DEFAULT_STEP,
    placeholder: String? = null,
    floatLabel: Boolean? = null,
    inputId: String? = null,
    classes: Set<String> = setOf(),
    init: (OnsNumberInput.() -> Unit)? = null
) : Widget(classes + "kv-ons-form-control"), GenericFormComponent<Number?>, FormInput, MutableState<Number?> {

    protected val observers = mutableListOf<(Number?) -> Unit>()

    /**
     * Number input value.
     */
    override var value by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(it) } }

    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the number input value.
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
     * The placeholder for the number input.
     */
    var placeholder by refreshOnUpdate(placeholder)

    /**
     * Whether the placeholder will be animated in Material Design.
     */
    var floatLabel: Boolean? by refreshOnUpdate(floatLabel)

    /**
     * The ID of the input element.
     */
    var inputId: String? by refreshOnUpdate(inputId)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()

    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false)

    /**
     * Determines if the number input is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * Determines if the number input is read-only.
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
     * Determines if autocomplete is enabled for the input element.
     */
    var autocomplete: Boolean? by refreshOnUpdate()

    init {
        this.setInternalEventListener<OnsNumberInput> {
            input = {
                self.changeValue()
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-input")
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
        min?.let {
            attributeSetBuilder.add("min", "$it")
        }
        max?.let {
            attributeSetBuilder.add("max", "$it")
        }
        attributeSetBuilder.add("step", step.toString())
        placeholder?.let {
            attributeSetBuilder.add("placeholder", translate(it))
        }
        if (floatLabel == true) {
            attributeSetBuilder.add("float")
        }
        inputId?.let {
            attributeSetBuilder.add("input-id", it)
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
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
        autocomplete?.let {
            attributeSetBuilder.add("autocomplete", if (it) "on" else "off")
        }
    }

    override fun afterInsert(node: VNode) {
        refreshState()
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun refreshState() {
        value?.let {
            getElementJQuery()?.`val`("$it")
        } ?: getElementJQueryD()?.`val`(null)
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun changeValue() {
        val v = getElementJQuery()?.`val`() as String?
        if (v != null && v != "") {
            val newValue = v.toDoubleOrNull()
            if (this.value != newValue) this.value = newValue
        } else {
            this.value = null
        }
    }

    /**
     * Returns the value of the number input as a String.
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.toString()
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
fun Container.onsNumberInput(
    value: Number? = null,
    min: Number? = null,
    max: Number? = null,
    step: Number = DEFAULT_STEP,
    placeholder: String? = null,
    floatLabel: Boolean? = null,
    inputId: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsNumberInput.() -> Unit)? = null
): OnsNumberInput {
    val onsNumberInput =
        OnsNumberInput(value, min, max, step, placeholder, floatLabel, inputId, classes ?: className.set, init)
    this.add(onsNumberInput)
    return onsNumberInput
}
