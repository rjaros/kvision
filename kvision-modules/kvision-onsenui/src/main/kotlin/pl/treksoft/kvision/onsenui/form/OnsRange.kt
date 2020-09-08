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

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.InvalidFeedback
import pl.treksoft.kvision.form.NumberFormControl
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.utils.SnOn
import pl.treksoft.kvision.utils.set

/**
 * Onsen UI form field range component.
 *
 * @constructor Creates a form field range component.
 * @param value number input value
 * @param min minimal value (default 0)
 * @param max maximal value (default 100)
 * @param step step value (default 1)
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsRange(
    value: Number? = null,
    min: Number = 0,
    max: Number = 100,
    step: Number = DEFAULT_STEP,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    classes: Set<String> = setOf(),
    init: (OnsRange.() -> Unit)? = null
) : SimplePanel(classes + setOf("form-group", "kv-ons-form-group")), NumberFormControl, ObservableState<Number?> {

    /**
     * Range input value.
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
     * bound to the range input value.
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
     * Determines if the range input is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }

    /**
     * Determines if the range input is read-only.
     */
    var readonly
        get() = input.readonly
        set(value) {
            input.readonly = value
        }

    /**
     * The label text bound to the range input element.
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

    protected val idc = "kv_ons_form_range_${counter}"
    final override val input: OnsRangeInput = OnsRangeInput(value, min, max, step, idc).apply {
        this.name = name
        this.eventTarget = this@OnsRange
    }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, setOf("control-label"))
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        @Suppress("LeakingThis")
        init?.invoke(this)
        counter++
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (validatorError != null) {
            cl.add("text-danger" to true)
        }
        return cl
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

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsRange(
    value: Number? = null,
    min: Number = 0,
    max: Number = 100,
    step: Number = DEFAULT_STEP,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsRange.() -> Unit)? = null
): OnsRange {
    val onsRange =
        OnsRange(value, min, max, step, name, label, rich, classes ?: className.set, init)
    this.add(onsRange)
    return onsRange
}
