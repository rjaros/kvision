package pl.treksoft.kvision.panel

import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.routing.routing

open class TabPanel : SimplePanel(setOf()) {
    var activeIndex
        get() = content.activeIndex
        set(value) {
            if (content.activeIndex != value) {
                content.activeIndex = value
                nav.children.forEach {
                    it.removeCssClass("active")
                }
                if (content.activeIndex >= 0 && content.activeIndex <= nav.children.size) {
                    nav.children[content.activeIndex].addCssClass("active")
                }
            }
        }

    private var nav = Tag(TAG.UL, classes = setOf("nav", "nav-tabs"))
    private var content = StackPanel(false)

    init {
        this.addInternal(nav)
        this.addInternal(content)
    }

    open fun addTab(
        title: String, panel: Component, icon: String? = null,
        image: ResString? = null, route: String? = null
    ): TabPanel {
        val tag = Tag(TAG.LI)
        tag.role = "presentation"
        tag.add(Link(title, "#", icon, image))
        val index = nav.children.size
        tag.setEventListener {
            click = { e ->
                activeIndex = index
                e.preventDefault()
                if (route != null) {
                    routing.navigate(route)
                }
            }
        }
        nav.add(tag)
        if (nav.children.size == 1) {
            tag.addCssClass("active")
            activeIndex = 0
        }
        content.add(panel)
        if (route != null) {
            routing.on(route, { _ -> activeIndex = index }).resolve()
        }
        return this
    }

    open fun removeTab(index: Int): TabPanel {
        nav.remove(nav.children[index])
        content.remove(content.children[index])
        activeIndex = content.activeIndex
        return this
    }

    override fun add(child: Component): TabPanel {
        return addTab("", child)
    }

    override fun addAll(children: List<Component>): TabPanel {
        children.forEach { add(it) }
        return this
    }

    override fun remove(child: Component): TabPanel {
        val index = content.children.indexOf(child)
        return removeTab(index)
    }

    override fun removeAll(): TabPanel {
        content.removeAll()
        nav.removeAll()
        refresh()
        return this
    }
}
