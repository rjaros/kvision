package pl.treksoft.kvision.form

import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.snabbdom.StringPair

open class FieldLabel(private val forId: String, text: String? = null, rich: Boolean = false) : Tag(TAG.LABEL,
        text, rich) {

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + ("for" to forId)
    }

}
