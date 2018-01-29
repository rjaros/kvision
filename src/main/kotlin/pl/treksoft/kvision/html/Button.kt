package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import org.w3c.dom.events.MouseEvent
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

open class Button(
    text: String, icon: String? = null, style: BUTTONSTYLE = BUTTONSTYLE.DEFAULT,
    disabled: Boolean = false, classes: Set<String> = setOf()
) : Widget(classes) {
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
    var disabled = disabled
        set(value) {
            field = value
            refresh()
        }
    var image: ResString? = null
        set(value) {
            field = value
            refresh()
        }
    var size: BUTTONSIZE? = null
        set(value) {
            field = value
            refresh()
        }
    var block = false
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        val t = createLabelWithIcon(text, icon, image)
        return kvh("button", t)
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("btn" to true)
        cl.add(style.className to true)
        size?.let {
            cl.add(it.className to true)
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

    open fun onClick(handler: Button.(MouseEvent) -> Unit): Button {
        this.setEventListener<Button> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }
}
