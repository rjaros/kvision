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
import pl.treksoft.kvision.core.Widget

/**
 * Basic container class, rendered as a DIV element with all children directly within.
 *
 * @constructor
 * @param classes a set of CSS class names
 */
open class SimplePanel(classes: Set<String> = setOf()) : Widget(classes), Container {
    internal val children: MutableList<Component> = mutableListOf()

    override fun render(): VNode {
        return render("div", childrenVNodes())
    }

    /**
     * Returns the array of the children Snabbdom vnodes.
     * @return array of children vnodes
     */
    protected open fun childrenVNodes(): Array<VNode> {
        return children.filter { it.visible }.map { it.renderVNode() }.toTypedArray()
    }

    /**
     * Protected and final method to add given component to the current container.
     * @param child child component
     * @return current container
     */
    protected fun addInternal(child: Component): SimplePanel {
        children.add(child)
        child.parent = this
        refresh()
        return this
    }

    override fun add(child: Component): SimplePanel {
        return addInternal(child)
    }

    override fun addAll(children: List<Component>): SimplePanel {
        this.children.addAll(children)
        children.map { it.parent = this }
        refresh()
        return this
    }

    override fun remove(child: Component): SimplePanel {
        if (children.remove(child)) {
            child.clearParent()
            refresh()
        }
        return this
    }

    override fun removeAll(): SimplePanel {
        children.map { it.clearParent() }
        children.clear()
        refresh()
        return this
    }

    override fun getChildren(): List<Component> {
        return ArrayList(children)
    }

    override fun dispose() {
        children.forEach { it.dispose() }
        removeAll()
    }
}
