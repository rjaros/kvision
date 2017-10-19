package pl.treksoft.kvision.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.snabbdom.StringPair

class TextAreaInput(cols: Int? = null, rows: Int? = null, placeholder: String? = null,
                    value: String? = null, name: String? = null, maxlength: Int? = null,
                    disabled: Boolean = false, id: String? = null, classes: Set<String> = setOf()) :
        AbstractTextInput(placeholder, value, name, maxlength, disabled, id, classes) {

    var cols: Int? = cols
        set(value) {
            field = value
            refresh()
        }
    var rows: Int? = rows
        set(value) {
            field = value
            refresh()
        }
    var wrapHard: Boolean = false
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return value?.let {
            kvh("textarea", arrayOf(it))
        } ?: kvh("textarea")
    }

    @Suppress("ComplexMethod")
    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        cols?.let {
            sn.add("cols" to ("" + it))
        }
        rows?.let {
            sn.add("rows" to ("" + it))
        }
        if (wrapHard) {
            sn.add("wrap" to "hard")
        }
        return sn
    }
}
