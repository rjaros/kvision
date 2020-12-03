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

package pl.treksoft.kvision.onsenui.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.ClassSetBuilder
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FormInput
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.form.ValidationStatus
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.utils.set

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
) : Widget(classes + "kv-ons-form-control"), FormInput, ObservableState<Number?> {

    protected val observers = mutableListOf<(Number?) -> Unit>()

    /**
     * Number input value.
     */
    var value by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(it) } }

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

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("type" to "number")
        startValue?.let {
            sn.add("value" to "$it")
        }
        min?.let {
            sn.add("min" to "$it")
        }
        max?.let {
            sn.add("max" to "$it")
        }
        sn.add("step" to step.toString())
        placeholder?.let {
            sn.add("placeholder" to translate(it))
        }
        if (floatLabel == true) {
            sn.add("float" to "float")
        }
        inputId?.let {
            sn.add("input-id" to it)
        }
        modifier?.let {
            sn.add("modifier" to it)
        }
        name?.let {
            sn.add("name" to it)
        }
        autofocus?.let {
            if (it) {
                sn.add("autofocus" to "autofocus")
            }
        }
        readonly?.let {
            if (it) {
                sn.add("readonly" to "readonly")
            }
        }
        if (disabled) {
            sn.add("disabled" to "disabled")
        }
        autocomplete?.let {
            if (it) {
                sn.add("autocomplete" to "on")
            } else {
                sn.add("autocomplete" to "off")
            }
        }
        return sn
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
            this.value = v.toDoubleOrNull()
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

    /**
     * Makes the input element focused.
     */
    override fun focus() {
        getElementJQuery()?.focus()
    }

    /**
     * Makes the input element blur.
     */
    override fun blur() {
        getElementJQuery()?.blur()
    }

    override fun getState(): Number? = value

    override fun subscribe(observer: (Number?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
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
