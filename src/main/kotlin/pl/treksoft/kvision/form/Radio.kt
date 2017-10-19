package pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.SnOn
import pl.treksoft.kvision.snabbdom.StringBoolPair

enum class RADIOSTYLE(val className: String) {
    DEFAULT("radio-default"),
    PRIMARY("radio-primary"),
    SUCCESS("radio-success"),
    INFO("radio-info"),
    WARNING("radio-warning"),
    DANGER("radio-danger"),
}

open class Radio(value: Boolean = false, extraValue: String? = null, name: String? = null, style: RADIOSTYLE? = null,
                 squared: Boolean = false, inline: Boolean = false, disabled: Boolean = false,
                 label: String? = null, rich: Boolean = false) : Container(), BoolFormField {

    override var value
        get() = input.value
        set(value) {
            input.value = value
        }
    var startValue
        get() = input.startValue
        set(value) {
            input.startValue = value
        }
    var extraValue
        get() = input.extraValue
        set(value) {
            input.extraValue = value
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
    var style = style
        set(value) {
            field = value
            refresh()
        }
    var squared = squared
        set(value) {
            field = value
            refresh()
        }
    var inline = inline
        set(value) {
            field = value
            refresh()
        }
    override var size
        get() = input.size
        set(value) {
            input.size = value
        }

    private val idc = "kv_form_radio_" + counter
    val input: CheckInput = CheckInput(CHECKINPUTTYPE.RADIO, value, name, disabled, idc, extraValue)
    val flabel: FieldLabel = FieldLabel(idc, label, rich)

    init {
        this.addInternal(input)
        this.addInternal(flabel)
        counter++
    }

    companion object {
        var counter = 0
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

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (!squared) {
            cl.add("radio" to true)
            style?.let {
                cl.add(it.className to true)
            }
            if (inline) {
                cl.add("radio-inline" to true)
            }
        } else {
            cl.add("checkbox" to true)
            style?.let {
                cl.add(it.className.replace("radio", "checkbox") to true)
            }
            if (inline) {
                cl.add("checkbox-inline" to true)
            }
        }
        return cl
    }
}
