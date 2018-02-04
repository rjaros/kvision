package pl.treksoft.kvision.form.text

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.form.StringFormControl
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.SnOn
import pl.treksoft.kvision.core.StringBoolPair

abstract class AbstractText(label: String? = null, rich: Boolean = false) :
    SimplePanel(setOf("form-group")), StringFormControl {

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
    var maxlength
        get() = input.maxlength
        set(value) {
            input.maxlength = value
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

    protected val idc = "kv_form_text_" + counter
    abstract override val input: AbstractTextInput
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich)
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        this.addInternal(flabel)
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
}
