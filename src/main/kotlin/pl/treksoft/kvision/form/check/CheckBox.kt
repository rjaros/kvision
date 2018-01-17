package pl.treksoft.kvision.form.check

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.BoolFormControl
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.panel.SimplePanel
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

open class CheckBox(
    value: Boolean = false, label: String? = null,
    rich: Boolean = false
) : SimplePanel(setOf("checkbox")), BoolFormControl {

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
    var style: CHECKBOXSTYLE? = null
        set(value) {
            field = value
            refresh()
        }
    var circled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    var inline: Boolean = false
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
    final override val input: CheckInput = CheckInput(
        CHECKINPUTTYPE.CHECKBOX, value,
        setOf("styled")
    ).apply { id = idc }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, classes = setOf())
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(input)
        this.addInternal(flabel)
        this.addInternal(validationInfo)
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
        style?.let {
            cl.add(it.className to true)
        }
        if (circled) {
            cl.add("checkbox-circle" to true)
        }
        if (inline) {
            cl.add("checkbox-inline" to true)
        }
        if (validatorError != null) {
            cl.add("has-error" to true)
        }
        return cl
    }
}
