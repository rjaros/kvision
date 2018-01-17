package pl.treksoft.kvision.form

import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.snabbdom.StringPair

open class FieldLabel(
    internal val forId: String, text: String? = null, rich: Boolean = false,
    classes: Set<String> = setOf("control-label")
) : Tag(
    TAG.LABEL,
    text, rich, classes = classes
) {

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + ("for" to forId)
    }

}
