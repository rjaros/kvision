package pl.treksoft.kvision.form.check

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.form.StringFormControl
import pl.treksoft.kvision.form.select.Select
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

open class RadioGroup(
    options: List<StringPair>? = null, value: String? = null, inline: Boolean = false,
    label: String? = null,
    rich: Boolean = false
) : SimplePanel(setOf("form-group")), StringFormControl {

    internal var options = options
        set(value) {
            field = value
            setChildrenFromOptions()
        }

    override var value = value
        set(value) {
            field = value
            setValueToChildren(value)
        }

    var inline: Boolean = inline
        set(value) {
            field = value
            refresh()
        }

    override var disabled
        get() = getDisabledFromChildren()
        set(value) {
            setDisabledToChildren(value)
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
    override var size: INPUTSIZE? = null

    private val idc = "kv_form_radiogroup_" + Select.counter
    final override val input = Widget()
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich)
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        setChildrenFromOptions()
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
        if (inline) {
            cl.add("kv-radiogroup-inline" to true)
        } else {
            cl.add("kv-radiogroup" to true)
        }
        return cl
    }

    private fun setValueToChildren(value: String?) {
        val radios = getChildren().filterIsInstance<Radio>()
        radios.forEach { it.value = false }
        radios.find {
            it.extraValue == value
        }?.value = true
    }

    private fun getDisabledFromChildren(): Boolean {
        return getChildren().filterIsInstance<Radio>().map { it.disabled }.firstOrNull() ?: false
    }

    private fun setDisabledToChildren(disabled: Boolean) {
        getChildren().filterIsInstance<Radio>().forEach { it.disabled = disabled }
    }

    private fun setChildrenFromOptions() {
        super.removeAll()
        this.addInternal(flabel)
        options?.let {
            val tidc = this.idc
            val tinline = this.inline
            val c = it.map {
                Radio(false, extraValue = it.first, label = it.second).apply {
                    inline = tinline
                    name = tidc
                    eventTarget = this@RadioGroup
                    setEventListener<Radio> {
                        change = {
                            this@RadioGroup.value = self.extraValue
                        }
                    }
                }
            }
            super.addAll(c)
        }
        this.addInternal(validationInfo)
    }

}
