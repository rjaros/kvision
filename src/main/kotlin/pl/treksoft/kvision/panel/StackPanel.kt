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
import pl.treksoft.kvision.routing.routing

/**
 * The container with only one active (visible) child at any moment.
 *
 * It supports activating children by a JavaScript route.
 *
 * @constructor
 * @param activateLast determines if added component is automatically activated (default true)
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class StackPanel(
    private val activateLast: Boolean = true,
    classes: Set<String> = setOf(), init: (StackPanel.() -> Unit)? = null
) : SimplePanel(classes) {

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
    open fun add(panel: Component, route: String): StackPanel {
        add(panel)
        val currentIndex = counter++
        childrenMap[currentIndex] = panel
        routing.on(route, { _ ->
            activeChild = childrenMap[currentIndex]!!
        }).resolve()
        return this
    }

    override fun add(child: Component): StackPanel {
        super.add(child)
        if (activateLast) activeIndex = children.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun addAll(children: List<Component>): StackPanel {
        super.addAll(children)
        if (activateLast) activeIndex = this.children.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun remove(child: Component): StackPanel {
        super.remove(child)
        childrenMap.filter { it.value == child }.keys.firstOrNull()?.let {
            childrenMap.remove(it)
        }
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }

    override fun removeAll(): StackPanel {
        super.removeAll()
        childrenMap.clear()
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.stackPanel(
            activateLast: Boolean = true, classes: Set<String> = setOf(), init: (StackPanel.() -> Unit)? = null
        ): StackPanel {
            val stackPanel = StackPanel(activateLast, classes, init)
            this.add(stackPanel)
            return stackPanel
        }
    }
}
