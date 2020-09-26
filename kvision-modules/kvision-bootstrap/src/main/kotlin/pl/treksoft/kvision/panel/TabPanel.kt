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

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.WidgetWrapper
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set

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
 * The container rendering its children as tabs.
 *
 * It supports activating children by a JavaScript route.
 *
 * @constructor
 * @param tabPosition tab position
 * @param sideTabSize side tab size
 * @param scrollableTabs determines if tabs are scrollable (default: false)
 * @param draggableTabs determines if tabs are draggable (default: false)
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class TabPanel(
    private val tabPosition: TabPosition = TabPosition.TOP,
    private val sideTabSize: SideTabSize = SideTabSize.SIZE_3,
    val scrollableTabs: Boolean = false,
    val draggableTabs: Boolean = false,
    classes: Set<String> = setOf(),
    init: (TabPanel.() -> Unit)? = null
) : SimplePanel(classes) {

    private val navClasses = when (tabPosition) {
        TabPosition.TOP -> if (scrollableTabs) setOf("nav", "nav-tabs", "tabs-top") else setOf("nav", "nav-tabs")
        TabPosition.LEFT -> setOf("nav", "nav-tabs", "tabs-left", "flex-column")
        TabPosition.RIGHT -> setOf("nav", "nav-tabs", "tabs-right", "flex-column")
    }

    internal val tabs = mutableListOf<Tab>()

    private val nav = TabPanelNav(this, navClasses)
    private val content = TabPanelContent(this)

    /**
     * The index of the active tab.
     */
    var activeIndex: Int = -1
        set(value) {
            if (value >= -1 && value < tabs.size) {
                field = value
                tabs.forEach {
                    it.link.removeCssClass("active")
                }
                tabs.getOrNull(value)?.link?.addCssClass("active")
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("tabChange", obj { detail = obj { data = value } })
            }
        }

    /**
     * The active tab.
     */
    var activeTab: Tab?
        get() = tabs.getOrNull(activeIndex)
        set(value) {
            activeIndex = value?.let { tabs.indexOf(value) } ?: -1
        }

    init {
        when (tabPosition) {
            TabPosition.TOP -> {
                this.addPrivate(nav)
                this.addPrivate(content)
            }
            TabPosition.LEFT -> {
                this.addSurroundingCssClass("container-fluid")
                this.addCssClass("row")
                val sizes = calculateSideClasses()
                this.addPrivate(WidgetWrapper(nav, setOf(sizes.first, "pl-0", "pr-0")))
                this.addPrivate(WidgetWrapper(content, setOf(sizes.second, "pl-0", "pr-0")))
            }
            TabPosition.RIGHT -> {
                this.addSurroundingCssClass("container-fluid")
                this.addCssClass("row")
                val sizes = calculateSideClasses()
                this.addPrivate(WidgetWrapper(content, setOf(sizes.second, "pl-0", "pr-0")))
                this.addPrivate(WidgetWrapper(nav, setOf(sizes.first, "pl-0", "pr-0")))
            }
        }
        init?.invoke(this)
    }

    private fun calculateSideClasses(): Pair<String, String> {
        return when (sideTabSize) {
            SideTabSize.SIZE_1 -> Pair("col-sm-1", "col-sm-11")
            SideTabSize.SIZE_2 -> Pair("col-sm-2", "col-sm-10")
            SideTabSize.SIZE_3 -> Pair("col-sm-3", "col-sm-9")
            SideTabSize.SIZE_4 -> Pair("col-sm-4", "col-sm-8")
            SideTabSize.SIZE_5 -> Pair("col-sm-5", "col-sm-7")
            SideTabSize.SIZE_6 -> Pair("col-sm-6", "col-sm-6")
        }
    }

    /**
     * Returns the number of tabs.
     */
    open fun getSize(): Int {
        return tabs.size
    }

    /**
     * Returns the list of tabs.
     */
    open fun getTabs(): List<Tab> {
        return tabs
    }

    /**
     * Get the Tab component by index.
     * @param index the index of a Tab
     */
    open fun getTab(index: Int): Tab? {
        return tabs.getOrNull(index)
    }

    /**
     * Get the index of the given tab.
     * @param tab a Tab component
     */
    open fun getTabIndex(tab: Tab): Int {
        return tabs.indexOf(tab)
    }

    /**
     * Removes tab at given index.
     * @param index the index of the tab
     */
    open fun removeTab(index: Int): TabPanel {
        getTab(index)?.let {
            removeTab(it)
            refresh()
        }
        return this
    }

    /**
     * Find the tab which contains the given component.
     * @param component a component
     */
    open fun findTabWithComponent(component: Component): Tab? {
        return tabs.find { it.getChildren().contains(component) }
    }

    /**
     * Move the tab to a different position.
     * @param tab a Tab component
     * @param fromIndex source tab index
     * @param toIndex destination tab index
     */
    open fun moveTab(fromIndex: Int, toIndex: Int) {
        tabs.getOrNull(fromIndex)?.let {
            tabs.remove(it)
            tabs.add(toIndex, it)
            if (activeIndex == fromIndex) {
                activeIndex = toIndex
            } else if (activeIndex in (fromIndex + 1)..toIndex) {
                activeIndex--
            } else if (activeIndex in toIndex until fromIndex) {
                activeIndex++
            }
            refresh()
        }
    }

    /**
     * Add new Tab component.
     * @param tab a Tab component
     */
    protected open fun addTab(tab: Tab) {
        tab.parent = nav
        tabs.add(tab)
        if (tabs.size == 1) {
            tab.link.addCssClass("active")
            activeIndex = 0
        }
        if (draggableTabs) {
            tab.setDragDropData("text/plain", tab.tabId.toString())
            tab.setDropTargetData("text/plain") { data ->
                val toIdx = getTabIndex(tab)
                data?.toIntOrNull()?.let { tabId ->
                    tabs.find { it.tabId == tabId }?.let {
                        val fromIdx = getTabIndex(it)
                        moveTab(fromIdx, toIdx)
                    }
                }
            }
        }
    }

    /**
     * Add new child component.
     * @param child a child component
     */
    protected open fun addChild(child: Component) {
        if (child is Tab) {
            addTab(child)
        } else {
            addTab(Tab("", child))
        }
    }

    /**
     * Delete the given Tab component.
     * @param tab a Tab component
     */
    protected open fun removeTab(tab: Tab) {
        val index = tabs.indexOf(tab)
        if (index >= 0) {
            tabs.remove(tab)
            tab.parent = null
            if (activeIndex >= tabs.size) {
                activeIndex = tabs.size - 1
            } else if (activeIndex > index) {
                activeIndex--
            } else if (activeIndex == index) {
                activeIndex = activeIndex
            }
        }
    }

    override fun add(child: Component): TabPanel {
        addChild(child)
        refresh()
        return this
    }

    /**
     * Creates and adds new tab component.
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
        addTab(Tab(title, panel, icon, image, closable, route))
        refresh()
        return this
    }

    override fun addAll(children: List<Component>): TabPanel {
        children.forEach(::addChild)
        refresh()
        return this
    }

    override fun remove(child: Component): TabPanel {
        if (child is Tab) {
            removeTab(child)
            refresh()
        } else {
            findTabWithComponent(child)?.let {
                removeTab(it)
                refresh()
            }
        }
        return this
    }

    override fun removeAll(): TabPanel {
        tabs.forEach { removeTab(it) }
        return this
    }

    /**
     * Returns first child component by tab index.
     * @param index tab index
     */
    @Deprecated("Tabs can have more than one child components. Use getTab method instead to manage Tab content.")
    open fun getChildComponent(index: Int): Component? {
        return getTab(index)?.getChildren()?.firstOrNull()
    }

    /**
     * Returns tab header component by tab index.
     * @param index tab index
     */
    @Deprecated("Use getTab method instead.", replaceWith = ReplaceWith("getTab"))
    open fun getNavComponent(index: Int): Tag? {
        return getTab(index)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.tabPanel(
    tabPosition: TabPosition = TabPosition.TOP,
    sideTabSize: SideTabSize = SideTabSize.SIZE_3,
    scrollableTabs: Boolean = false,
    draggableTabs: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (TabPanel.() -> Unit)? = null
): TabPanel {
    val tabPanel = TabPanel(tabPosition, sideTabSize, scrollableTabs, draggableTabs, classes ?: className.set, init)
    this.add(tabPanel)
    return tabPanel
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.tabPanel(
    state: ObservableState<S>,
    tabPosition: TabPosition = TabPosition.TOP,
    sideTabSize: SideTabSize = SideTabSize.SIZE_3,
    scrollableTabs: Boolean = false,
    draggableTabs: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (TabPanel.(S) -> Unit)
) = tabPanel(tabPosition, sideTabSize, scrollableTabs, draggableTabs, classes, className).bind(state, true, init)


internal class TabPanelNav(internal val tabPanel: TabPanel, classes: Set<String>) : SimplePanel(classes) {

    override fun render(): VNode {
        return render("ul", childrenVNodes())
    }

    override fun childrenVNodes(): Array<VNode> {
        return tabPanel.tabs.filter { it.visible }.map { it.renderVNode() }.toTypedArray()
    }

}

internal class TabPanelContent(private val tabPanel: TabPanel) : SimplePanel() {

    override fun childrenVNodes(): Array<VNode> {
        return tabPanel.tabs.getOrNull(tabPanel.activeIndex)?.getChildren()?.map { it.renderVNode() }?.toTypedArray()
            ?: emptyArray()
    }

}
