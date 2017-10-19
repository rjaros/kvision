package pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.SnOn

abstract class AbstractText(label: String? = null, rich: Boolean = false) :
        Container(setOf("form-group")), StringFormField {

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
    internal abstract val input: AbstractTextInput
    internal val flabel: FieldLabel = FieldLabel(idc, label, rich)

    init {
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
}
