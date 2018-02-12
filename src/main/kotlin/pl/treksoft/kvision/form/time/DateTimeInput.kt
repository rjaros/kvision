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

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.toDateF
import pl.treksoft.kvision.utils.toStringF
import kotlin.js.Date

internal const val DEFAULT_MINUTE_STEP = 5
internal const val MAX_VIEW = 4

/**
 * Basic date/time chooser component.
 *
 * @constructor
 * @param value date/time input value
 * @param format date/time format (default YYYY-MM-DD HH:mm)
 * @param classes a set of CSS class names
 */
@Suppress("TooManyFunctions")
open class DateTimeInput(
    value: Date? = null, format: String = "YYYY-MM-DD HH:mm",
    classes: Set<String> = setOf()
) : Widget(classes + "form-control") {

    init {
        this.setInternalEventListener<DateTimeInput> {
            change = {
                self.changeValue()
            }
        }
    }

    /**
     * Date/time input value.
     */
    var value: Date? = value
        set(value) {
            field = value
            refreshState()
        }
    /**
     * Date/time format.
     */
    var format: String = format
        set(value) {
            field = value
            refreshDatePicker()
        }
    /**
     * The placeholder for the date/time input.
     */
    var placeholder: String? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * The name attribute of the generated HTML input element.
     */
    var name: String? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the field is disabled.
     */
    var disabled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the text input is automatically focused.
     */
    var autofocus: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the date/time input is read-only.
     */
    var readonly: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * The size of the input.
     */
    var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Day of the week start. 0 (Sunday) to 6 (Saturday).
     */
    var weekStart: Int = 0
        set(value) {
            field = value
            refreshDatePicker()
        }
    /**
     * Days of the week that should be disabled. Multiple values should be comma separated.
     */
    var daysOfWeekDisabled: Array<Int> = arrayOf()
        set(value) {
            field = value
            refreshDatePicker()
        }
    /**
     * Determines if *Clear* button should be visible.
     */
    var clearBtn: Boolean = true
        set(value) {
            field = value
            refreshDatePicker()
        }
    /**
     * Determines if *Today* button should be visible.
     */
    var todayBtn: Boolean = false
        set(value) {
            field = value
            refreshDatePicker()
        }
    /**
     * Determines if the current day should be highlighted.
     */
    var todayHighlight: Boolean = false
        set(value) {
            field = value
            refreshDatePicker()
        }
    /**
     * The increment used to build the hour view.
     */
    var minuteStep: Int = DEFAULT_MINUTE_STEP
        set(value) {
            field = value
            refreshDatePicker()
        }
    /**
     * Determines if meridian views are visible in day and hour views.
     */
    var showMeridian: Boolean = false
        set(value) {
            field = value
            refreshDatePicker()
        }

    override fun render(): VNode {
        return render("input")
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("type" to "text")
        placeholder?.let {
            sn.add("placeholder" to it)
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
            sn.add("disabled" to "true")
            value?.let {
                sn.add("value" to it.toStringF(format))
            }
        }
        return sn
    }

    @Suppress("UnsafeCastFromDynamic")
    protected open fun refreshState() {
        value?.let {
            getElementJQueryD()?.datetimepicker("update", it)
        } ?: run {
            getElementJQueryD()?.`val`(null)
            getElementJQueryD()?.datetimepicker("update", null)
        }
    }

    protected open fun refreshDatePicker() {
        getElementJQueryD()?.`val`(null)
        getElementJQueryD()?.datetimepicker("remove")
        initDateTimePicker()
        refreshState()
    }

    protected open fun changeValue() {
        val v = getElementJQuery()?.`val`() as String?
        if (v != null && v.isNotEmpty()) {
            this.value = v.toDateF(format)
        } else {
            this.value = null
        }
    }

    /**
     * Open date/time chooser popup.
     */
    open fun showPopup() {
        getElementJQueryD()?.datetimepicker("show")
    }

    /**
     * Hides date/time chooser popup.
     */
    open fun hidePopup() {
        getElementJQueryD()?.datetimepicker("hide")
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        if (!this.disabled) {
            this.initDateTimePicker()
            this.getElementJQuery()?.on("changeDate", { e, _ ->
                this.dispatchEvent("change", obj { detail = e })
            })
            this.getElementJQuery()?.on("show", { e, _ ->
                this.dispatchEvent("showBsDateTime", obj { detail = e })
            })
            this.getElementJQuery()?.on("hide", { e, _ ->
                this.dispatchEvent("hideBsDateTime", obj { detail = e })
            })
            refreshState()
        }
    }

    private fun initDateTimePicker() {
        val datePickerFormat = format.toDatePickerFormat()
        val minView = if (format.contains("HH") || format.contains("mm")) 0 else 2
        val maxView = if (format.contains("YY") || format.contains("M") || format.contains("D")) MAX_VIEW else 1
        val startView = if (maxView < 2) maxView else 2
        getElementJQueryD()?.datetimepicker(obj {
            this.format = datePickerFormat
            this.startView = startView
            this.minView = minView
            this.maxView = maxView
            this.minuteStep = minuteStep
            this.todayHighlight = todayHighlight
            this.clearBtn = clearBtn
            this.todayBtn = todayBtn
            this.weekStart = weekStart
            this.showMeridian = showMeridian
            this.daysOfWeekDisabled = daysOfWeekDisabled
            this.autoclose = true
        })
    }

    /**
     * Get value of date/time input control as String
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.toStringF(format)
    }

    companion object {
        private fun String.toDatePickerFormat(): String {
            return this.replace("YY", "yy").replace("m", "i").replace("MMMM", "{----}").replace("MMM", "{---}")
                .replace("M", "m").replace("{----}", "MM").replace("{---}", "M").replace("H", "{-}")
                .replace("h", "H").replace("{-}", "h").replace("D", "d").replace("a", "p").replace("A", "P")
        }

        /**
         * DSL builder extension function
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.dateTimeInput(
            value: Date? = null, format: String = "YYYY-MM-DD HH:mm", classes: Set<String> = setOf(),
            init: (DateTimeInput.() -> Unit)? = null
        ) {
            this.add(DateTimeInput(value, format, classes).apply { init?.invoke(this) })
        }
    }
}
