package pl.treksoft.kvision.form

open class Password(value: String? = null, label: String? = null, rich: Boolean = false) : Text(TEXTINPUTTYPE.PASSWORD,
        value, label, rich)
