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
package io.kvision.form.time

import io.kvision.DatetimeModule
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.text.TextInput
import io.kvision.html.Icon
import io.kvision.html.Span
import io.kvision.html.icon
import io.kvision.i18n.I18n
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode
import io.kvision.state.MutableState
import io.kvision.types.toDateF
import io.kvision.types.toStringF
import io.kvision.utils.createInstance
import io.kvision.utils.obj
import kotlin.js.Date

internal const val DEFAULT_STEPPING = 1

/**
 * Date/time chooser component color themes.
 */
enum class Theme(internal val theme: String) {
    LIGHT("light"),
    DARK("dark"),
    AUTO("auto")
}

/**
 * Date/time chooser view modes.
 */
enum class ViewMode(internal val mode: String) {
    CLOCK("clock"),
    CALENDAR("calendar"),
    MONTHS("months"),
    YEARS("years"),
    DECADES("decades"),
}

/**
 * Date/time chooser toolbar placements.
 */
enum class ToolbarPlacement(internal val placement: String) {
    TOP("top"),
    BOTTOM("bottom"),
}

/**
 * Basic date/time chooser component.
 *
 * @constructor
 * @param value date/time input value
 * @param format date/time format (default YYYY-MM-DD HH:mm)
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions", "LeakingThis")
open class DateTimeInput(
    value: Date? = null, format: String = "YYYY-MM-DD HH:mm",
    className: String? = null,
    init: (DateTimeInput.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "input-group date"), GenericFormComponent<Date?>, FormInput,
    MutableState<Date?> {

    protected val observers = mutableListOf<(Date?) -> Unit>()

    private var dateTimePicker: dynamic = null
    private val idc = "kv_datetime_${counter}"

    val input = TextInput(value = value?.toStringF(format)) {
        this.id = "${idc}_input"
        setAttribute("data-td-target", "#${idc}")
    }
    private lateinit var icon: Icon
    private val addon = Span(className = "input-group-text") {
        this@DateTimeInput.icon = icon(this@DateTimeInput.getIconClass(format))
        setAttribute("data-td-target", "#${idc}")
        setAttribute("data-td-toggle", "datetimepicker")
    }

    /**
     * Date/time input value.
     */
    override var value
        get() = input.value?.toDateF(format)
        set(value) {
            input.value = value?.toStringF(format)
            refreshState()
        }

    /**
     * Date/time format.
     */
    var format by refreshOnUpdate(format) { refreshDatePicker() }

    /**
     * The placeholder for the date/time input.
     */
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }

    /**
     * The name attribute of the generated HTML input element.
     */
    override var name
        get() = input.name
        set(value) {
            input.name = value
        }

    /**
     * Determines if the field is disabled.
     */
    override var disabled
        get() = input.disabled
        set(value) {
            input.disabled = value
        }

    /**
     * Determines if the text input is automatically focused.
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
     * The size of the input.
     */
    override var size
        get() = input.size
        set(value) {
            input.size = value
        }

    /**
     * The validation status of the input.
     */
    override var validationStatus
        get() = input.validationStatus
        set(value) {
            input.validationStatus = value
            refresh()
        }

    /**
     * Days of the week that should be disabled. Multiple values should be comma separated.
     */
    var daysOfWeekDisabled by refreshOnUpdate(arrayOf<Int>()) { refreshDatePicker() }

    /**
     * Determines if *Clear* button should be visible.
     */
    var showClear by refreshOnUpdate(true) { refreshDatePicker() }

    /**
     * Determines if *Close* button should be visible.
     */
    var showClose by refreshOnUpdate(true) { refreshDatePicker() }

    /**
     * Determines if *Today* button should be visible.
     */
    var showToday by refreshOnUpdate(true) { refreshDatePicker() }

    /**
     * The increment used to build the hour view.
     */
    var stepping by refreshOnUpdate(DEFAULT_STEPPING) { refreshDatePicker() }

    /**
     * Prevents date selection before this date.
     */
    var minDate: Date? by refreshOnUpdate { refreshDatePicker() }

    /**
     * Prevents date selection after this date.
     */
    var maxDate: Date? by refreshOnUpdate { refreshDatePicker() }

    /**
     * Shows date and time pickers side by side.
     */
    var sideBySide by refreshOnUpdate(false) { refreshDatePicker() }

    /**
     * An array of enabled dates.
     */
    var enabledDates by refreshOnUpdate(arrayOf<Date>()) { refreshDatePicker() }

    /**
     * An array of disabled dates.
     */
    var disabledDates by refreshOnUpdate(arrayOf<Date>()) { refreshDatePicker() }

    /**
     * Show as inline.
     */
    var inline by refreshOnUpdate(false) { refreshDatePicker() }

    /**
     * Keep the popup open after selecting a date.
     */
    var keepOpen by refreshOnUpdate(false) { refreshDatePicker() }

    /**
     * Date/time chooser color theme.
     */
    var theme: Theme? by refreshOnUpdate { refreshDatePicker() }

    /**
     * Automatically open the chooser popup.
     */
    var allowInputToggle by refreshOnUpdate(true) { refreshDatePicker() }

    /**
     * The view date of the date/time chooser.
     */
    var viewDate: Date? by refreshOnUpdate { refreshDatePicker() }

    /**
     * Automatically open time component after date is selected.
     */
    var promptTimeOnDateChange by refreshOnUpdate(false) { refreshDatePicker() }

    /**
     * The view date of the date/time chooser.
     */
    var promptTimeOnDateChangeTransitionDelay: Int? by refreshOnUpdate { refreshDatePicker() }

    /**
     * Default view mode of the date/time chooser.
     */
    var viewMode: ViewMode? by refreshOnUpdate { refreshDatePicker() }

    /**
     * Date/time chooser toolbar placement.
     */
    var toolbarPlacement: ToolbarPlacement? by refreshOnUpdate { refreshDatePicker() }

    init {
        id = idc
        useSnabbdomDistinctKey()
        addPrivate(input)
        addPrivate(addon)
        setAttribute("data-td-target-input", "nearest")
        setAttribute("data-td-target-toggle", "nearest")
        counter++
        init?.invoke(this)
    }

    private fun refreshState() {
        if (dateTimePicker != null) {
            if (value != null) {
                val internalDateTime = dateTimePicker.dates.parseInput(value!!.toStringF(format))
                dateTimePicker.dates.setValue(internalDateTime)
            } else {
                dateTimePicker.dates.setValue(null)
            }
        }
    }

    private fun getIconClass(format: String): String {
        return if (format.contains("YYYY") || format.contains("MM") || format.contains("DD")) {
            "fas fa-calendar-alt"
        } else {
            "fas fa-clock"
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(validationStatus)
    }

    protected open fun refreshDatePicker() {
        if (dateTimePicker != null) {
            dateTimePicker.dispose()
            dateTimePicker = null
        }
        input.visible = !inline
        addon.visible = !inline
        initDateTimePicker()
        icon.icon = getIconClass(format)
    }

    /**
     * Open date/time chooser popup.
     */
    open fun showPopup() {
        if (dateTimePicker != null) dateTimePicker.show()
    }

    /**
     * Hides date/time chooser popup.
     */
    open fun hidePopup() {
        if (dateTimePicker != null) dateTimePicker.hide()
    }

    /**
     * Toggles date/time chooser popup.
     */
    open fun togglePopup() {
        if (dateTimePicker != null) dateTimePicker.toggle()
    }

    override fun afterInsert(node: VNode) {
        this.initDateTimePicker()
        this.initEventHandlers()
    }

    override fun afterDestroy() {
        if (dateTimePicker != null) {
            dateTimePicker.dispose()
            dateTimePicker = null
        }
    }

    private fun initDateTimePicker() {
        val calendarView = (format.contains("YYYY") || format.contains("MM") || format.contains("DD"))
        val clockView = (format.contains("HH") || format.contains("mm") || format.contains("ss"))
        val secondsView = format.contains("ss")
        val newFormat = format.replace("YYYY", "yyyy").replace("DD", "dd")
        val language = I18n.language
        val locale = DatetimeModule.locales[language] ?: js("{}")
        locale["locale"] = language
        locale["format"] = newFormat
        val initialViewMode = viewMode ?: if (calendarView) ViewMode.CALENDAR else ViewMode.CLOCK
        dateTimePicker = getElement()?.let { element ->
            DatetimeModule.tempusDominus.TempusDominus.unsafeCast<Any>().createInstance<Any>(element, obj {
                this.useCurrent = inline
                this.defaultDate =
                    if (inline && this@DateTimeInput.value != null) this@DateTimeInput.value else undefined
                this.stepping = stepping
                this.allowInputToggle = allowInputToggle
                if (viewDate != null) this.viewDate = viewDate
                this.promptTimeOnDateChange = promptTimeOnDateChange
                if (promptTimeOnDateChangeTransitionDelay != null) this.promptTimeOnDateChangeTransitionDelay =
                    promptTimeOnDateChangeTransitionDelay
                this.restrictions = obj {
                    if (minDate != null) this.minDate = minDate
                    if (maxDate != null) this.maxDate = maxDate
                    if (enabledDates.isNotEmpty()) this.enabledDates = enabledDates
                    if (disabledDates.isNotEmpty()) this.disabledDates = disabledDates
                    if (daysOfWeekDisabled.isNotEmpty()) this.daysOfWeekDisabled = daysOfWeekDisabled
                }
                this.display = obj {
                    this.viewMode = initialViewMode.mode
                    toolbarPlacement?.let { this.toolbarPlacement = it.placement }
                    this.sideBySide = sideBySide
                    this.buttons = obj {
                        this.clear = showClear
                        this.close = showClose
                        this.today = showToday
                    }
                    this.inline = inline
                    this.keepOpen = keepOpen
                    theme?.let { this.theme = it.theme }
                    this.components = obj {
                        this.calendar = calendarView
                        this.clock = clockView
                        this.seconds = secondsView
                    }
                }
                this.localization = locale
            })
        }
    }

    private fun initEventHandlers() {
        getElement()?.let { element ->
            element.addEventListener("change.td", { event ->
                val date = event.asDynamic().detail.date?.unsafeCast<Date>()
                val dateStr = date?.toStringF(format)
                val valueStr = this.value?.toStringF(format)
                if (dateStr != valueStr) {
                    this.value = date
                    @Suppress("UnsafeCastFromDynamic")
                    this.dispatchEvent("change", obj { detail = event.asDynamic().detail })
                }
            })
            element.addEventListener("error.td", { event ->
                this.value = null
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("change", obj { detail = event.asDynamic().detail })
            })
        }
    }

    /**
     * Get value of date/time input control as String
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.toStringF(format)
    }

    /**
     * Makes the input element focused.
     */
    override fun focus() {
        input.focus()
    }

    /**
     * Makes the input element blur.
     */
    override fun blur() {
        input.blur()
    }


    override fun getState(): Date? = value

    override fun subscribe(observer: (Date?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    override fun setState(state: Date?) {
        value = state
    }

    companion object {
        internal var counter = 0

        init {
            DatetimeModule.initialize()
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.dateTimeInput(
    value: Date? = null, format: String = "YYYY-MM-DD HH:mm",
    className: String? = null,
    init: (DateTimeInput.() -> Unit)? = null
): DateTimeInput {
    val dateTimeInput = DateTimeInput(value, format, className, init)
    this.add(dateTimeInput)
    return dateTimeInput
}
