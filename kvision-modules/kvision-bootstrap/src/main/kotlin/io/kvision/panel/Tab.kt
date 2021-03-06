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
package io.kvision.panel

import com.github.snabbdom.VNode
import io.kvision.core.Component
import io.kvision.core.ExperimentalNonDslContainer
import io.kvision.core.ResString
import io.kvision.core.onClick
import io.kvision.html.Icon
import io.kvision.html.Link
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.routing.RoutingManager
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.obj

/**
 * The single Tab component inside the TabPanel container.
 *
 * @constructor
 * @param label label of the tab
 * @param icon icon of the tab
 * @param image image of the tab
 * @param closable determines if this tab is closable
 * @param route JavaScript route to activate given tab
 * @param init an initializer extension function
 */
open class Tab(
    label: String? = null, icon: String? = null,
    image: ResString? = null, closable: Boolean = false, val route: String? = null,
    init: (Tab.() -> Unit)? = null
) : Tag(TAG.LI, classes = setOf("nav-item")) {

    constructor(
        label: String? = null,
        child: Component,
        icon: String? = null,
        image: ResString? = null,
        closable: Boolean = false,
        route: String? = null,
        init: (Tab.() -> Unit)? = null
    ) : this(label, icon, image, closable, route, init) {
        @Suppress("LeakingThis")
        add(child)
    }

    /**
     * The label of the tab.
     */
    var label
        get() = link.label.ifBlank { null }
        set(value) {
            link.label = value ?: ""
        }

    /**
     * The icon of the tab.
     */
    var icon
        get() = link.icon
        set(value) {
            link.icon = value
        }

    /**
     * The image of the tab.
     */
    var image
        get() = link.image
        set(value) {
            link.image = value
        }

    /**
     * Determines if this tab is closable.
     */
    var closable
        get() = closeIcon.visible
        set(value) {
            closeIcon.visible = value
        }

    internal val closeIcon = Icon("fas fa-times").apply {
        addCssClass("kv-tab-close")
        visible = closable
        setEventListener<Icon> {
            click = { e ->
                val tabPanel = (this@Tab.parent as? TabPanelNav)?.tabPanel
                val actIndex = tabPanel?.getTabIndex(this@Tab) ?: -1
                e.asDynamic().data = actIndex
                @Suppress("UnsafeCastFromDynamic")
                val event = org.w3c.dom.CustomEvent("tabClosing", obj { detail = e; cancelable = true })
                if (tabPanel?.getElement()?.dispatchEvent(event) != false) {
                    tabPanel?.removeTab(actIndex)
                    @Suppress("UnsafeCastFromDynamic")
                    val closed = org.w3c.dom.CustomEvent("tabClosed", obj { detail = e })
                    tabPanel?.getElement()?.dispatchEvent(closed)
                }
                e.stopPropagation()
            }
        }
    }

    /**
     * A link component within the tab.
     */
    val link = Link(label ?: "", "#", icon, image, classes = setOf("nav-link")).apply {
        add(this@Tab.closeIcon)
    }

    internal val tabId = counter++

    protected val routingHandler = { _: Any ->
        (this@Tab.parent as? TabPanelNav)?.tabPanel?.activeTab = this
    }

    init {
        addPrivate(link)
        onClick { e ->
            (this@Tab.parent as? TabPanelNav)?.tabPanel?.activeTab = this
            e.preventDefault()
            if (route != null) {
                RoutingManager.getRouter().kvNavigate(route)
            }
        }
        if (route != null) RoutingManager.getRouter().kvOn(route, routingHandler)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun setDragDropData(format: String, data: String) {
        link.setDragDropData(format, data)
    }

    override fun childrenVNodes(): Array<VNode> {
        return (privateChildren).filter { it.visible }.map { it.renderVNode() }.toTypedArray()
    }

    override fun dispose() {
        super.dispose()
        if (route != null) RoutingManager.getRouter().kvOff(routingHandler)
    }

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun TabPanel.tab(
    label: String? = null, icon: String? = null,
    image: ResString? = null, closable: Boolean = false, route: String? = null,
    init: (Tab.() -> Unit)? = null
): Tab {
    val tab = Tab(label, icon, image, closable, route, init)
    this.add(tab)
    return tab
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> TabPanel.tab(
    state: ObservableState<S>,
    label: String? = null, icon: String? = null,
    image: ResString? = null, closable: Boolean = false, route: String? = null,
    init: (Tab.(S) -> Unit)
) = tab(label, icon, image, closable, route).bind(state, true, init)

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
@ExperimentalNonDslContainer
fun BasicTabPanel.tab(
    label: String? = null, icon: String? = null,
    image: ResString? = null, closable: Boolean = false, route: String? = null,
    init: (Tab.() -> Unit)? = null
): Tab {
    val tab = Tab(label, icon, image, closable, route, init)
    this.add(tab)
    return tab
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
@ExperimentalNonDslContainer
fun <S> BasicTabPanel.tab(
    state: ObservableState<S>,
    label: String? = null, icon: String? = null,
    image: ResString? = null, closable: Boolean = false, route: String? = null,
    init: (Tab.(S) -> Unit)
) = tab(label, icon, image, closable, route).bind(state, true, init)
