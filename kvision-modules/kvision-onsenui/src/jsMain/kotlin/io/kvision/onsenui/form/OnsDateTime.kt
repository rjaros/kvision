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
import io.kvision.core.Widget
import io.kvision.form.DateFormControl
import io.kvision.form.FieldLabel
import io.kvision.form.InvalidFeedback
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.utils.SnOn
import kotlin.js.Date

/**
 * Onsen UI form field date/time component.
 *
 * @constructor Creates a form field date/time component.
 * @param value date/time input value
 * @param mode date/time input mode
 * @param min minimal value
 * @param max maximal value
 * @param step step value
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class OnsDateTime(
    value: Date? = null,
    mode: DateTimeMode = DateTimeMode.DATETIME,
    min: Date? = null,
    max: Date? = null,
    step: Number? = null,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    className: String? = null,
    init: (OnsDateTime.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "form-group kv-mb-3 kv-ons-form-group"), DateFormControl,
    MutableState<Date?> {

    /**
     * Date/time input value.
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
     * bound to the date/time input value.
     */
    var startValue
        get() = input.startValue
        set(value) {
            input.startValue = value
        }

    /**
     * Minimal date/time value.
     */
    var min
        get() = input.min
        set(value) {
            input.min = value
        }

    /**
     * Maximal date/time value.
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
     * Determines if the date/time input is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }

    /**
     * Determines if the date/time input is read-only.
     */
    var readonly
        get() = input.readonly
        set(value) {
            input.readonly = value
        }

    /**
     * The label text bound to the date/time input element.
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

    protected val idc = "kv_ons_form_datetime_${counter}"
    final override val input: OnsDateTimeInput = OnsDateTimeInput(value, mode, min, max, step, idc).apply {
        modifier = "underbar"
        this.name = name
        this.eventTarget = this@OnsDateTime
    }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, "form-label")
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        @Suppress("LeakingThis")
        init?.invoke(this)
        counter++
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

    override fun getState(): Date? = input.getState()

    override fun subscribe(observer: (Date?) -> Unit): () -> Unit {
        return input.subscribe(observer)
    }

    override fun setState(state: Date?) {
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
fun Container.onsDateTime(
    value: Date? = null,
    mode: DateTimeMode = DateTimeMode.DATETIME,
    min: Date? = null,
    max: Date? = null,
    step: Number? = null,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    className: String? = null,
    init: (OnsDateTime.() -> Unit)? = null
): OnsDateTime {
    val onsDateTime =
        OnsDateTime(value, mode, min, max, step, name, label, rich, className, init)
    this.add(onsDateTime)
    return onsDateTime
}
