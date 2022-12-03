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

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Display
import io.kvision.core.Widget
import io.kvision.form.FieldLabel
import io.kvision.form.InvalidFeedback
import io.kvision.form.NumberFormControl
import io.kvision.onsenui.OnsenUi
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.utils.SnOn

/**
 * Onsen UI form field number component.
 *
 * @constructor Creates a form field number component.
 * @param value number input value
 * @param min minimal value
 * @param max maximal value
 * @param step step value (default 1)
 * @param placeholder the placeholder for the number input
 * @param floatLabel whether the placeholder will be animated in Material Design
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class OnsNumber(
    value: Number? = null,
    min: Number? = null,
    max: Number? = null,
    step: Number = DEFAULT_STEP,
    placeholder: String? = null,
    floatLabel: Boolean? = null,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    className: String? = null,
    init: (OnsNumber.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "form-group kv-mb-3 kv-ons-form-group"), NumberFormControl,
    MutableState<Number?> {

    /**
     * Number input value.
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
     * bound to the number input value.
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
     * The placeholder for the number input.
     */
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }

    /**
     * Whether the placeholder will be animated in Material Design.
     */
    var floatLabel
        get() = input.floatLabel
        set(value) {
            input.floatLabel = value
            if (input.floatLabel == true && OnsenUi.isAndroid()) {
                flabel.display = Display.NONE
            } else {
                flabel.display = if (flabel.content != null) null else Display.NONE
            }
        }

    /**
     * Determines if the number input is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }

    /**
     * Determines if the number input is read-only.
     */
    var readonly
        get() = input.readonly
        set(value) {
            input.readonly = value
        }

    /**
     * The label text bound to the number input element.
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

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier
        get() = input.modifier
        set(value) {
            input.modifier = value
        }

    /**
     * Determines if autocomplete is enabled for the input element.
     */
    var autocomplete
        get() = input.autocomplete
        set(value) {
            input.autocomplete = value
        }

    protected val idc = "kv_ons_form_number_${counter}"
    final override val input: OnsNumberInput =
        OnsNumberInput(value, min, max, step, placeholder, floatLabel, idc).apply {
            modifier = "underbar"
            this.name = name
            this.eventTarget = this@OnsNumber
        }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, "form-label")
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        if (input.floatLabel == true && OnsenUi.isAndroid()) flabel.display = Display.NONE
        @Suppress("LeakingThis")
        init?.invoke(this)
        counter++
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
fun Container.onsNumber(
    value: Number? = null,
    min: Number? = null,
    max: Number? = null,
    step: Number = DEFAULT_STEP,
    placeholder: String? = null,
    floatLabel: Boolean? = null,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    className: String? = null,
    init: (OnsNumber.() -> Unit)? = null
): OnsNumber {
    val onsNumber =
        OnsNumber(value, min, max, step, placeholder, floatLabel, name, label, rich, className, init)
    this.add(onsNumber)
    return onsNumber
}
