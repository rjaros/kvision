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

import io.kvision.snabbdom.VNode
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.routing.RoutingManager
import kotlinx.browser.window
import kotlin.collections.set

/**
 * The container with only one active (visible) child at any moment.
 *
 * It supports activating children by a JavaScript route.
 *
 * @constructor
 * @param activateLast determines if added component is automatically activated (default true)
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class StackPanel(
    private val activateLast: Boolean = true,
    className: String? = null, init: (StackPanel.() -> Unit)? = null
) : SimplePanel(className) {

    /**
     * The index of active (visible) child.
     */
    var activeIndex by refreshOnUpdate(-1)

    /**
     * The active (visible) child.
     */
    var activeChild: Component?
        get() = children?.get(activeIndex)
        set(value) {
            activeIndex = children?.indexOf(value) ?: -1
        }

    internal val childrenMap = mutableMapOf<Int, Component>()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun childrenVNodes(): Array<VNode> {
        return if (children != null && activeIndex in children!!.indices) {
            arrayOf(children!![activeIndex].renderVNode())
        } else {
            arrayOf()
        }
    }

    /**
     * Adds given component and bounds it's activation to a given route.
     * @param panel child component
     * @param route JavaScript route to activate given child
     * @return current container
     */
    open fun add(panel: Component, route: String): StackPanel {
        add(panel)
        val currentIndex = counter++
        childrenMap[currentIndex] = panel
        window.setTimeout({
            RoutingManager.getRouter().kvOn(route) { _ ->
                activeChild = childrenMap[currentIndex]!!
            }.kvResolve()
        }, 0)
        return this
    }

    /**
     * DSL function to add components with additional options.
     * @param builder DSL builder function
     */
    open fun route(
        route: String,
        builder: Container.() -> Unit
    ) {
        object : Container by this@StackPanel {
            override fun add(child: Component): Container {
                return add(child, route)
            }
        }.builder()
    }

    override fun add(child: Component): StackPanel {
        super.add(child)
        if (activateLast) activeIndex = children!!.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun add(position: Int, child: Component): SimplePanel {
        super.add(position, child)
        if (activateLast) activeIndex = children!!.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun addAll(children: List<Component>): StackPanel {
        super.addAll(children)
        if (activateLast) activeIndex = this.children!!.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun remove(child: Component): StackPanel {
        super.remove(child)
        childrenMap.filter { it.value == child }.keys.firstOrNull()?.let {
            childrenMap.remove(it)
        }
        if (children != null && activeIndex > children!!.size - 1) activeIndex = children!!.size - 1
        return this
    }

    override fun removeAt(position: Int): StackPanel {
        val child = children?.getOrNull(position)
        if (child != null) remove(child)
        return this
    }

    override fun removeAll(): StackPanel {
        super.removeAll()
        childrenMap.clear()
        if (children != null && activeIndex > children!!.size - 1) activeIndex = children!!.size - 1
        return this
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
fun Container.stackPanel(
    activateLast: Boolean = true,
    className: String? = null,
    init: (StackPanel.() -> Unit)? = null
): StackPanel {
    val stackPanel = StackPanel(activateLast, className, init)
    this.add(stackPanel)
    return stackPanel
}
