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
import io.kvision.core.Container
import io.kvision.core.ExperimentalNonDslContainer
import io.kvision.routing.RoutingManager
import kotlinx.browser.window
import kotlin.collections.List
import kotlin.collections.Set
import kotlin.collections.filter
import kotlin.collections.firstOrNull
import kotlin.collections.indices
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.setOf

/**
 * The container with only one active (visible) child at any moment.
 *
 * It supports activating children by a JavaScript route.
 *
 * This container is not annotated with a @WidgetMarker.
 * It should be used only as a base class for other components.
 *
 * @constructor
 * @param activateLast determines if added component is automatically activated (default true)
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@ExperimentalNonDslContainer
open class BasicStackPanel(
    private val activateLast: Boolean = true,
    classes: Set<String> = setOf(), init: (BasicStackPanel.() -> Unit)? = null
) : BasicPanel(classes) {

    /**
     * The index of active (visible) child.
     */
    var activeIndex by refreshOnUpdate(-1)

    /**
     * The active (visible) child.
     */
    var activeChild
        get() = children[activeIndex]
        set(value) {
            activeIndex = children.indexOf(value)
        }

    internal val childrenMap = mutableMapOf<Int, Component>()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun childrenVNodes(): Array<VNode> {
        return if (activeIndex in children.indices) {
            arrayOf(children[activeIndex].renderVNode())
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
    open fun add(panel: Component, route: String): BasicStackPanel {
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
        object : Container by this@BasicStackPanel {
            override fun add(child: Component): Container {
                return add(child, route)
            }
        }.builder()
    }

    override fun add(child: Component): BasicStackPanel {
        super.add(child)
        if (activateLast) activeIndex = children.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun addAll(children: List<Component>): BasicStackPanel {
        super.addAll(children)
        if (activateLast) activeIndex = this.children.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun remove(child: Component): BasicStackPanel {
        super.remove(child)
        childrenMap.filter { it.value == child }.keys.firstOrNull()?.let {
            childrenMap.remove(it)
        }
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }

    override fun removeAll(): BasicStackPanel {
        super.removeAll()
        childrenMap.clear()
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }

    companion object {
        internal var counter = 0
    }
}
