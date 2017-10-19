package pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.SnOn
import pl.treksoft.kvision.snabbdom.StringBoolPair

enum class CHECKBOXSTYLE(val className: String) {
    DEFAULT("checkbox-default"),
    PRIMARY("checkbox-primary"),
    SUCCESS("checkbox-success"),
    INFO("checkbox-info"),
    WARNING("checkbox-warning"),
    DANGER("checkbox-danger"),
}

open class CheckBox(value: Boolean = false, name: String? = null, style: CHECKBOXSTYLE? = null,
                    circled: Boolean = false, inline: Boolean = false, disabled: Boolean = false,
                    label: String? = null, rich: Boolean = false) : Container(setOf("checkbox")), BoolFormField {

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
    var circled = circled
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

    private val idc = "kv_form_checkbox_" + counter
    val input: CheckBoxInput = CheckBoxInput(value, name, disabled, idc, setOf("styled"))
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
        input.setEventListener<T>(block)
        return this
    }

    override fun setEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        input.setEventListener(block)
        return this
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        style?.let {
            cl.add(it.className to true)
        }
        if (circled) {
            cl.add("checkbox-circle" to true)
        }
        if (inline) {
            cl.add("checkbox-inline" to true)
        }
        return cl
    }
}
