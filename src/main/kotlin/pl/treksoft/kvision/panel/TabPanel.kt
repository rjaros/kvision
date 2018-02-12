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
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.routing.routing

/**
 * The container rendering it's children as tabs.
 *
 * It supports activating children by a JavaScript route.
 *
 * @constructor
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class TabPanel(classes: Set<String> = setOf(), init: (TabPanel.() -> Unit)? = null) : SimplePanel(classes) {

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

        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * Adds new tab and optionally bounds it's activation to a given route.
     * @param title title of the tab
     * @param panel child component
     * @param icon icon of the tab
     * @param image image of the tab
     * @param route JavaScript route to activate given child
     * @return current container
     */
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

    /**
     * Removes tab at given index.
     */
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

    companion object {
        /**
         * DSL builder extension function
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.tabPanel(classes: Set<String> = setOf(), init: (TabPanel.() -> Unit)? = null) {
            this.add(TabPanel(classes, init))
        }
    }
}
