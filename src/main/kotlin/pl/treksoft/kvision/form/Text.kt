package pl.treksoft.kvision.form


open class Text(type: TEXTINPUTTYPE = TEXTINPUTTYPE.TEXT, placeholder: String? = null, value: String? = null,
                name: String? = null, maxlength: Int? = null, label: String? = null, rich: Boolean = false,
                disabled: Boolean = false) : AbstractText(label, rich) {

    var type
        get() = input.type
        set(value) {
            input.type = value
        }
    var autocomplete
        get() = input.autocomplete
        set(value) {
            input.autocomplete = value
        }

    override final val input: TextInput = TextInput(type, placeholder, value, name, maxlength, disabled, idc)

    init {
        this.addInternal(input)
    }
}
