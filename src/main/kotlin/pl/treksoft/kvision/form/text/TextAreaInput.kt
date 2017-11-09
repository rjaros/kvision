package pl.treksoft.kvision.form.text

import com.github.snabbdom.VNode
import pl.treksoft.kvision.snabbdom.StringPair

open class TextAreaInput(cols: Int? = null, rows: Int? = null, value: String? = null, classes: Set<String> = setOf()) :
        AbstractTextInput(value, classes) {

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
        return startValue?.let {
            kvh("textarea", arrayOf(it))
        } ?: kvh("textarea")
    }

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
