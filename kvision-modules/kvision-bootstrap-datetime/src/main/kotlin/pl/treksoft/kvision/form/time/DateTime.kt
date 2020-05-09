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
package pl.treksoft.kvision.form.time

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.DateFormControl
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.InvalidFeedback
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.SnOn
import kotlin.js.Date

/**
 * Form field date/time chooser component.
 *
 * @constructor
 * @param value date/time input value
 * @param name the name attribute of the generated HTML input element
 * @param format date/time format (default YYYY-MM-DD HH:mm)
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
open class DateTime(
    value: Date? = null, name: String? = null, format: String = "YYYY-MM-DD HH:mm", label: String? = null,
    rich: Boolean = false
) : SimplePanel(setOf("form-group")), DateFormControl {

    /**
     * Date/time input value.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }
    /**
     * Date/time format.
     */
    var format
        get() = input.format
        set(value) {
            input.format = value
        }
    /**
     * The placeholder for the date/time input.
     */
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
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
     * Days of the week that should be disabled. Multiple values should be comma separated.
     */
    var daysOfWeekDisabled
        get() = input.daysOfWeekDisabled
        set(value) {
            input.daysOfWeekDisabled = value
        }
    /**
     * Determines if *Clear* button should be visible.
     */
    var showClear
        get() = input.showClear
        set(value) {
            input.showClear = value
        }
    /**
     * Determines if *Close* button should be visible.
     */
    var showClose
        get() = input.showClose
        set(value) {
            input.showClose = value
        }
    /**
     * Determines if *Today* button should be visible.
     */
    var showTodayButton
        get() = input.showTodayButton
        set(value) {
            input.showTodayButton = value
        }
    /**
     * The increment used to build the hour view.
     */
    var stepping
        get() = input.stepping
        set(value) {
            input.stepping = value
        }
    /**
     * Prevents date selection before this date.
     */
    var minDate
        get() = input.minDate
        set(value) {
            input.minDate = value
        }
    /**
     * Prevents date selection after this date.
     */
    var maxDate
        get() = input.maxDate
        set(value) {
            input.maxDate = value
        }
    /**
     * Shows date and time pickers side by side.
     */
    var sideBySide
        get() = input.sideBySide
        set(value) {
            input.sideBySide = value
        }
    /**
     * An array of enabled dates.
     */
    var enabledDates
        get() = input.enabledDates
        set(value) {
            input.enabledDates = value
        }
    /**
     * An array of disabled dates.
     */
    var disabledDates
        get() = input.disabledDates
        set(value) {
            input.disabledDates = value
        }
    /**
     * Allow date picker for readonly component..
     */
    var ignoreReadonly
        get() = input.ignoreReadonly
        set(value) {
            input.ignoreReadonly = value
        }
    /**
     * The label text bound to the input element.
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

    private val idc = "kv_form_time_$counter"
    final override val input: DateTimeInput = DateTimeInput(value, format).apply {
        this.input.id = idc
        this.name = name
    }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, setOf("control-label"))
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(flabel)
        this.addInternal(input)
        this.addInternal(invalidFeedback)
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

    /**
     * Open date/time chooser popup.
     */
    open fun showPopup() {
        input.showPopup()
    }

    /**
     * Hides date/time chooser popup.
     */
    open fun hidePopup() {
        input.hidePopup()
    }

    /**
     * Toggle date/time chooser popup.
     */
    open fun togglePopup() {
        input.togglePopup()
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

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.dateTime(
    value: Date? = null, name: String? = null, format: String = "YYYY-MM-DD HH:mm", label: String? = null,
    rich: Boolean = false, init: (DateTime.() -> Unit)? = null
): DateTime {
    val dateTime = DateTime(value, name, format, label, rich).apply { init?.invoke(this) }
    this.add(dateTime)
    return dateTime
}
