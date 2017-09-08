package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

enum class BUTTONSTYLE(val className: String) {
    DEFAULT("btn-default"),
    PRIMARY("btn-primary"),
    SUCCESS("btn-success"),
    INFO("btn-info"),
    WARNING("btn-warning"),
    DANGER("btn-danger"),
    LINK("btn-link")
}

enum class BUTTONSIZE(val className: String) {
    LARGE("btn-lg"),
    SMALL("btn-sm"),
    XSMALL("btn-xs")
}

open class Button(text: String, icon: String? = null, style: BUTTONSTYLE = BUTTONSTYLE.DEFAULT,
                  size: BUTTONSIZE? = null, block: Boolean = false, disabled: Boolean = false,
                  image: ResString? = null, classes: Set<String> = setOf()) : Widget(classes) {
    var text = text
        set(value) {
            field = value
            refresh()
        }
    var icon = icon
        set(value) {
            field = value
            refresh()
        }
    var style = style
        set(value) {
            field = value
            refresh()
        }
    var size = size
        set(value) {
            field = value
            refresh()
        }
    var block = block
        set(value) {
            field = value
            refresh()
        }
    var disabled = disabled
        set(value) {
            field = value
            refresh()
        }
    var image = image
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        val t = if (icon != null) {
            if (icon?.startsWith("fa-") == true) {
                arrayOf(KVManager.virtualize("<i class='fa $icon fa-lg'></i>"), " " + text)
            } else {
                arrayOf(KVManager.virtualize("<span class='glyphicon glyphicon-$icon'></span>"), " " + text)
            }
        } else if (image != null) {
            arrayOf(KVManager.virtualize("<img src='$image' alt='' />"), " " + text)
        } else {
            arrayOf(text)
        }
        return kvh("button", t)
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("btn" to true)
        cl.add(style.className to true)
        if (size != null) {
            cl.add(size?.className.orEmpty() to true)
        }
        if (block) {
            cl.add("btn-block" to true)
        }
        if (disabled) {
            cl.add("disabled" to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + ("type" to "button")
    }

}
