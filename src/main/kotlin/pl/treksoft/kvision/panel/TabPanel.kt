/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.treksoft.kvision.panel

import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.WidgetWrapper
import pl.treksoft.kvision.html.Icon
import pl.treksoft.kvision.html.Link.Companion.link
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.routing.routing
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.html.Icon.Companion.icon as cicon

/**
 * Tab position.
 */
enum class TabPosition {
    TOP,
    LEFT,
    RIGHT
}

/**
 * Left or right tab size.
 */
enum class SideTabSize {
    SIZE_1,
    SIZE_2,
    SIZE_3,
    SIZE_4,
    SIZE_5,
    SIZE_6
}

/**
 * The container rendering it's children as tabs.
 *
 * It supports activating children by a JavaScript route.
 *
 * @constructor
 * @param tabPosition tab position
 * @param sideTabSize side tab size
 * @param scrollableTabs determines if tabs are scrollable (default: false)
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class TabPanel(
    private val tabPosition: TabPosition = TabPosition.TOP,
    private val sideTabSize: SideTabSize = SideTabSize.SIZE_3,
    scrollableTabs: Boolean = false,
    classes: Set<String> = setOf(),
    init: (TabPanel.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * The index of active (visible) tab.
     */
    var activeIndex
        get() = content.activeIndex
        set(value) {
            if (content.activeIndex != value) {
                content.activeIndex = value
                nav.children.forEach {
                    it.removeCssClass("active")
                }
                if (content.activeIndex in nav.children.indices) {
                    nav.children[content.activeIndex].addCssClass("active")
                }
            }
        }
    private val navClasses = when (tabPosition) {
        TabPosition.TOP -> if (scrollableTabs) setOf("nav", "nav-tabs", "tabs-top") else setOf("nav", "nav-tabs")
        TabPosition.LEFT -> setOf("nav", "nav-tabs", "tabs-left")
        TabPosition.RIGHT -> setOf("nav", "nav-tabs", "tabs-right")
    }
    private var nav = Tag(TAG.UL, classes = navClasses)
    private var content = StackPanel(false)

    internal val childrenMap = mutableMapOf<Int, Component>()

    init {
        when (tabPosition) {
            TabPosition.TOP -> {
                this.addInternal(nav)
                this.addInternal(content)
            }
            TabPosition.LEFT -> {
                this.addCssClass("clearfix")
                val sizes = calculateSideClasses()
                this.addInternal(WidgetWrapper(nav, setOf(sizes.first, "col-nopadding")))
                this.addInternal(WidgetWrapper(content, setOf(sizes.second, "col-nopadding")))
            }
            TabPosition.RIGHT -> {
                this.addCssClass("clearfix")
                val sizes = calculateSideClasses()
                this.addInternal(WidgetWrapper(content, setOf(sizes.second, "col-nopadding")))
                this.addInternal(WidgetWrapper(nav, setOf(sizes.first, "col-nopadding")))
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    private fun calculateSideClasses(): Pair<String, String> {
        return when (sideTabSize) {
            SideTabSize.SIZE_1 -> Pair("col-xs-1", "col-xs-11")
            SideTabSize.SIZE_2 -> Pair("col-xs-2", "col-xs-10")
            SideTabSize.SIZE_3 -> Pair("col-xs-3", "col-xs-9")
            SideTabSize.SIZE_4 -> Pair("col-xs-4", "col-xs-8")
            SideTabSize.SIZE_5 -> Pair("col-xs-5", "col-xs-7")
            SideTabSize.SIZE_6 -> Pair("col-xs-6", "col-xs-6")
        }
    }

    /**
     * Adds new tab and optionally bounds it's activation to a given route.
     * @param title title of the tab
     * @param panel child component
     * @param icon icon of the tab
     * @param image image of the tab
     * @param closable determines if this tab is closable
     * @param route JavaScript route to activate given child
     * @return current container
     */
    open fun addTab(
        title: String, panel: Component, icon: String? = null,
        image: ResString? = null, closable: Boolean = false, route: String? = null
    ): TabPanel {
        val currentIndex = counter++
        childrenMap[currentIndex] = panel
        val tag = Tag(TAG.LI) {
            role = "presentation"
            link(title, "#", icon, image) {
                if (closable) {
                    cicon("remove") {
                        addCssClass("kv-tab-close")
                        setEventListener<Icon> {
                            click = { e ->
                                val actIndex = this@TabPanel.content.children.indexOf(childrenMap[currentIndex])
                                e.asDynamic().data = actIndex
                                @Suppress("UnsafeCastFromDynamic")
                                if (this@TabPanel.dispatchEvent(
                                        "tabClosing",
                                        obj { detail = e; cancelable = true }) != false
                                ) {
                                    this@TabPanel.removeTab(actIndex)
                                    this@TabPanel.dispatchEvent("tabClosed", obj { detail = e })
                                }
                                e.stopPropagation()
                            }
                        }
                    }
                }
            }
            setEventListener {
                click = { e ->
                    activeIndex = this@TabPanel.content.children.indexOf(childrenMap[currentIndex])
                    e.preventDefault()
                    if (route != null) {
                        routing.navigate(route)
                    }
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
            routing.on(route, { _ -> activeIndex = this@TabPanel.content.children.indexOf(childrenMap[currentIndex]) })
                .resolve()
        }
        return this
    }

    /**
     * Removes tab at given index.
     */
    open fun removeTab(index: Int): TabPanel {
        nav.remove(nav.children[index])
        childrenMap.filter { it.value == content.children[index] }.keys.firstOrNull()?.let {
            childrenMap.remove(it)
        }
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

    /**
     * Returns child component by tab index.
     * @param index tab index
     */
    open fun getChildComponent(index: Int): Component? {
        return content.children[index]
    }

    /**
     * Returns tab header component by tab index.
     * @param index tab index
     */
    open fun getNavComponent(index: Int): Tag? {
        return nav.children[index] as? Tag
    }

    override fun removeAll(): TabPanel {
        content.removeAll()
        nav.removeAll()
        childrenMap.clear()
        refresh()
        return this
    }

    companion object {
        internal var counter = 0
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.tabPanel(
            tabPosition: TabPosition = TabPosition.TOP,
            sideTabSize: SideTabSize = SideTabSize.SIZE_3,
            scrollableTabs: Boolean = false,
            classes: Set<String> = setOf(),
            init: (TabPanel.() -> Unit)? = null
        ): TabPanel {
            val tabPanel = TabPanel(tabPosition, sideTabSize, scrollableTabs, classes, init)
            this.add(tabPanel)
            return tabPanel
        }
    }
}
