package pl.treksoft.kvision.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.snabbdom.StringPair

enum class TEXTINPUTTYPE(val type: String) {
    TEXT("text"),
    PASSWORD("password")
}

class TextInput(type: TEXTINPUTTYPE = TEXTINPUTTYPE.TEXT, placeholder: String? = null,
                value: String? = null, name: String? = null, maxlength: Int? = null,
                disabled: Boolean = false, id: String? = null, classes: Set<String> = setOf()) :
        AbstractTextInput(placeholder, value, name, maxlength, disabled, id, classes) {

    var type: TEXTINPUTTYPE = type
        set(value) {
            field = value
            refresh()
        }
    var autocomplete: Boolean? = null
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return kvh("input")
    }

    @Suppress("ComplexMethod")
    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("type" to type.type)
        startValue?.let {
            sn.add("value" to it)
        }
        autocomplete?.let {
            if (it) {
                sn.add("autocomplete" to "on")
            } else {
                sn.add("autocomplete" to "off")
            }
        }
        return sn
    }
}
