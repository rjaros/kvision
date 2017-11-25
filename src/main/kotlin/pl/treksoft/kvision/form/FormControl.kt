package pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Component
import kotlin.js.Date

enum class INPUTSIZE(val className: String) {
    LARGE("input-lg"),
    SMALL("input-sm")
}

interface FormControl : Component {
    var disabled: Boolean
    var size: INPUTSIZE?
    val input: Component
    val flabel: FieldLabel
    val validationInfo: HelpBlock
    fun getValue(): Any?
    fun setValue(v: Any?)
    fun getValueAsString(): String?
    fun refresh(): Component
    var validatorError: String?
        get() = validationInfo.text
        set(value) {
            validationInfo.text = value
            validationInfo.visible = value != null
            refresh()
        }
}

interface StringFormControl : FormControl {
    var value: String?
    override fun getValue(): String? = value
    override fun setValue(v: Any?) {
        value = v as? String
    }

    override fun getValueAsString(): String? = value
}

interface NumberFormControl : FormControl {
    var value: Number?
    override fun getValue(): Number? = value
    override fun setValue(v: Any?) {
        value = v as? Number
    }

    override fun getValueAsString(): String? = value?.toString()
}

interface BoolFormControl : FormControl {
    var value: Boolean
    override fun getValue(): Boolean = value
    override fun setValue(v: Any?) {
        value = v as? Boolean ?: false
    }

    override fun getValueAsString(): String? = value.toString()
}

interface DateFormControl : FormControl {
    var value: Date?
    override fun getValue(): Date? = value
    override fun setValue(v: Any?) {
        value = v as? Date
    }

    override fun getValueAsString(): String? = value?.toString()
}
