package pl.treksoft.kvision.form.time

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.DateFormControl
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.SnOn
import pl.treksoft.kvision.snabbdom.StringBoolPair
import kotlin.js.Date

open class DateTime(value: Date? = null, format: String = "YYYY-MM-DD HH:mm", label: String? = null,
                    rich: Boolean = false) : SimplePanel(setOf("form-group")), DateFormControl {

    override var value
        get() = input.value
        set(value) {
            input.value = value
        }
    var format
        get() = input.format
        set(value) {
            input.format = value
        }
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }
    var name
        get() = input.name
        set(value) {
            input.name = value
        }
    override var disabled
        get() = input.disabled
        set(value) {
            input.disabled = value
        }
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }
    var readonly
        get() = input.readonly
        set(value) {
            input.readonly = value
        }
    var weekStart
        get() = input.weekStart
        set(value) {
            input.weekStart = value
        }
    var daysOfWeekDisabled
        get() = input.daysOfWeekDisabled
        set(value) {
            input.daysOfWeekDisabled = value
        }
    var clearBtn
        get() = input.clearBtn
        set(value) {
            input.clearBtn = value
        }
    var todayBtn
        get() = input.todayBtn
        set(value) {
            input.todayBtn = value
        }
    var todayHighlight
        get() = input.todayHighlight
        set(value) {
            input.todayHighlight = value
        }
    var minuteStep
        get() = input.minuteStep
        set(value) {
            input.minuteStep = value
        }
    var showMeridian
        get() = input.showMeridian
        set(value) {
            input.showMeridian = value
        }
    var label
        get() = flabel.text
        set(value) {
            flabel.text = value
        }
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
        }
    override var size
        get() = input.size
        set(value) {
            input.size = value
        }

    protected val idc = "kv_form_time_" + counter
    final override val input: DateTimeInput = DateTimeInput(value, format).apply { id = idc }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich)
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(flabel)
        this.addInternal(input)
        this.addInternal(validationInfo)
        counter++
    }

    companion object {
        var counter = 0
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (validatorError != null) {
            cl.add("has-error" to true)
        }
        return cl
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Widget {
        input.setEventListener(block)
        return this
    }

    override fun setEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        input.setEventListener(block)
        return this
    }

    override fun removeEventListeners(): Widget {
        input.removeEventListeners()
        return this
    }

    open fun showPopup() {
        input.showPopup()
    }

    open fun hidePopup() {
        input.hidePopup()
    }

    override fun getValueAsString(): String? {
        return input.getValueAsString()
    }
}
