package pl.treksoft.kvision.form.text

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.StringPair

enum class TEXTINPUTTYPE(val type: String) {
    TEXT("text"),
    PASSWORD("password")
}

open class TextInput(type: TEXTINPUTTYPE = TEXTINPUTTYPE.TEXT, value: String? = null, classes: Set<String> = setOf()) :
    AbstractTextInput(value, classes + "form-control") {

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
