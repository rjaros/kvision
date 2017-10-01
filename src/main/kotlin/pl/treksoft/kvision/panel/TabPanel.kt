package pl.treksoft.kvision.panel

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

open class TabPanel : Container(setOf()) {
    private var nav = Tag(TAG.UL, classes = setOf("nav", "nav-tabs"))
    private var content = StackPanel(false)
    var activeIndex
        get() = content.activeIndex
        set(value) {
            content.activeIndex = value
            nav.children.forEach { it.removeCssClass("active") }
            if (content.activeIndex >= 0 && content.activeIndex <= nav.children.size) {
                nav.children[content.activeIndex].addCssClass("active")
            }
        }

    init {
        this.add(nav)
        this.add(content)
    }

    open fun addTab(title: String, panel: Widget, icon: String? = null,
                    image: ResString? = null): TabPanel {
        val tag = Tag(TAG.LI)
        tag.role = "presentation"
        tag.add(Link(title, "#", icon, image))
        val index = nav.children.size
        tag.setEventListener {
            click = { e ->
                activeIndex = index
                e.preventDefault()
            }
        }
        nav.add(tag)
        if (nav.children.size == 1) {
            tag.addCssClass("active")
            activeIndex = 0
        }
        content.add(panel)
        return this
    }

    open fun removeTab(index: Int): TabPanel {
        nav.removeAt(index)
        content.removeAt(index)
        activeIndex = content.activeIndex
        return this
    }
}
