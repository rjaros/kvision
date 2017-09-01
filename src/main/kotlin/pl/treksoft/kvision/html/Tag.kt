package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.StringBoolPair

enum class TAG(val tagName: String) {
    H1("h1"),
    H2("h2"),
    H3("h3"),
    H4("h4"),
    H5("h5"),
    H6("h6"),
    P("p"),
    ABBR("abbr"),
    ADDRESS("address"),
    BLOCKQUOTE("blockquote"),
    FOOTER("footer"),
    PRE("pre")
}

enum class ALIGN(val className: String) {
    NONE(""),
    LEFT("text-left"),
    CENTER("text-center"),
    RIGHT("text-right"),
    JUSTIFY("text-justify"),
    NOWRAP("text-nowrap")
}

open class Tag(type: TAG, text: String, rich: Boolean = false, align: ALIGN = ALIGN.NONE, classes: Set<String> = setOf()) : Widget(classes) {
    var type = type
        set(value) {
            field = value
            refresh()
        }
    var text = text
        set(value) {
            field = value
            refresh()
        }
    var rich = rich
        set(value) {
            field = value
            refresh()
        }
    var align = align
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        if (rich) {
            return kvh(type.tagName, arrayOf(KVManager.virtualize("<span>$text</span>")))
        } else {
            return kvh(type.tagName, arrayOf(text))
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (align != ALIGN.NONE) {
            cl.add(align.className to true)
        }
        return cl
    }
}