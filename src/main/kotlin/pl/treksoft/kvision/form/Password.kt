package pl.treksoft.kvision.form

open class Password(placeholder: String? = null, value: String? = null,
                    name: String? = null, maxlength: Int? = null, label: String? = null, rich: Boolean = false,
                    disabled: Boolean = false) : Text(TEXTINPUTTYPE.PASSWORD, placeholder, value, name, maxlength,
        label, rich, disabled)
