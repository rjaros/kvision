package pl.treksoft.kvision.form.spinner

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.form.NumberFormControl
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.SnOn
import pl.treksoft.kvision.snabbdom.StringBoolPair

open class Spinner(value: Number? = null, min: Int = 0, max: Int = DEFAULT_MAX, step: Double = DEFAULT_STEP,
                   decimals: Int = 0, buttonsType: BUTTONSTYPE = BUTTONSTYPE.VERTICAL,
                   forceType: FORCETYPE = FORCETYPE.NONE, label: String? = null,
                   rich: Boolean = false) : SimplePanel(setOf("form-group")), NumberFormControl {

    override var value
        get() = input.value
        set(value) {
            input.value = value
        }
    var min
        get() = input.min
        set(value) {
            input.min = value
        }
    var max
        get() = input.max
        set(value) {
            input.max = value
        }
    var step
        get() = input.step
        set(value) {
            input.step = value
        }
    var decimals
        get() = input.decimals
        set(value) {
            input.decimals = value
        }
    var buttonsType
        get() = input.buttonsType
        set(value) {
            input.buttonsType = value
        }
    var forceType
        get() = input.forceType
        set(value) {
            input.forceType = value
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

    protected val idc = "kv_form_spinner_" + counter
    final override val input: SpinnerInput = SpinnerInput(value, min, max, step, decimals, buttonsType, forceType)
            .apply { id = idc }
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

    override fun getValueAsString(): String? {
        return input.getValueAsString()
    }

    open fun spinUp(): Spinner {
        input.spinUp()
        return this
    }

    open fun spinDown(): Spinner {
        input.spinDown()
        return this
    }
}
