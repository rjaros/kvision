package pl.treksoft.kvision.form.time

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.DateFormField
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair
import pl.treksoft.kvision.snabbdom.obj
import pl.treksoft.kvision.utils.toDateF
import pl.treksoft.kvision.utils.toStringF
import kotlin.js.Date

const val DEFAULT_MINUTE_STEP = 5
const val MAX_VIEW = 4

@Suppress("TooManyFunctions")
open class DateTimeInput(value: Date? = null, format: String = "YYYY-MM-DD HH:mm",
                         classes: Set<String> = setOf()) : Widget(classes + "form-control"), DateFormField {


    init {
        this.setInternalEventListener<DateTimeInput> {
            change = {
                self.changeValue()
            }
        }
    }

    override var value: Date? = value
        set(value) {
            field = value
            refreshState()
        }
    var format: String = format
        set(value) {
            field = value
            refreshDatePicker()
        }
    var placeholder: String? = null
        set(value) {
            field = value
            refresh()
        }
    var name: String? = null
        set(value) {
            field = value
            refresh()
        }
    override var disabled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    var autofocus: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    var readonly: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    override var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
        }
    var weekStart: Int = 0
        set(value) {
            field = value
            refreshDatePicker()
        }
    var daysOfWeekDisabled: Array<Int> = arrayOf()
        set(value) {
            field = value
            refreshDatePicker()
        }
    var clearBtn: Boolean = true
        set(value) {
            field = value
            refreshDatePicker()
        }
    var todayBtn: Boolean = false
        set(value) {
            field = value
            refreshDatePicker()
        }
    var todayHighlight: Boolean = false
        set(value) {
            field = value
            refreshDatePicker()
        }
    var minuteStep: Int = DEFAULT_MINUTE_STEP
        set(value) {
            field = value
            refreshDatePicker()
        }
    var showMeridian: Boolean = false
        set(value) {
            field = value
            refreshDatePicker()
        }

    override fun render(): VNode {
        return kvh("input")
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
        } ?: getElementJQueryD()?.datetimepicker("update", null)
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

    open fun showPopup() {
        getElementJQueryD()?.datetimepicker("show")
    }

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

    override fun getValueAsString(): String? {
        return value?.toStringF(format)
    }

    companion object {
        private fun String.toDatePickerFormat(): String {
            return this.replace("YY", "yy").replace("m", "i").replace("MMMM", "{----}").replace("MMM", "{---}")
                    .replace("M", "m").replace("{----}", "MM").replace("{---}", "M").replace("H", "{-}")
                    .replace("h", "H").replace("{-}", "h").replace("D", "d").replace("a", "p").replace("A", "P")
        }
    }
}
