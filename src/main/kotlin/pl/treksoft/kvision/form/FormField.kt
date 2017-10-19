package pl.treksoft.kvision.form

enum class INPUTSIZE(val className: String) {
    LARGE("input-lg"),
    SMALL("input-sm")
}

interface FormField {
    var disabled: Boolean
    var size: INPUTSIZE?
    fun getValueAsString(): String?
}

interface StringFormField : FormField {
    var value: String?
    override fun getValueAsString(): String? = value
}

interface NumberFormField : FormField {
    var value: Number?
    override fun getValueAsString(): String? = value?.toString()
}

interface BoolFormField : FormField {
    var value: Boolean
    override fun getValueAsString(): String? = value.toString()
}
