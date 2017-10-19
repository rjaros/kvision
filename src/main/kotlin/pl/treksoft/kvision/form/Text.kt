package pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Container


open class Text(type: TEXTINPUTTYPE = TEXTINPUTTYPE.TEXT, placeholder: String? = null, value: String? = null,
                name: String? = null, maxlength: Int? = null, label: String? = null, rich: Boolean = false,
                disabled: Boolean = false) : Container(setOf("form-group")), StringFormField {

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
    var type
        get() = input.type
        set(value) {
            input.type = value
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

    private val idc = "kv_form_text_" + counter
    internal val input: TextInput = TextInput(type, placeholder, value, name, maxlength, disabled, idc)
    internal val flabel: FieldLabel = FieldLabel(idc, label, rich)

    init {
        this.addInternal(flabel)
        this.addInternal(input)
        counter++
    }

    companion object {
        var counter = 0
    }

}
