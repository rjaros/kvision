package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.core.Widget

enum class INTAG(val tagName: String) {
    MARK("mark"),
    DEL("del"),
    S("s"),
    INS("ins"),
    U("u"),
    SMALL("small"),
    STRONG("strong"),
    EM("em"),
    CITE("cite"),
    CODE("code"),
    KBD("kbd"),
    VAR("var"),
    SAMP("samp"),
    SPAN("span")
}

open class InTag(type: INTAG, text: String, rich: Boolean = false, classes: Set<String> = setOf()) : Widget(classes) {
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

    override fun render(): VNode {
        if (rich) {
            return kvh(type.tagName, arrayOf(KVManager.virtualize("<span>$text</span>")))
        } else {
            return kvh(type.tagName, arrayOf(text))
        }
    }
}