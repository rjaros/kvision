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
import io.kvision.core.Widget


/**
 * Basic container class, rendered as a DIV element with all children directly within.
 *
 * This container is not annotated with a @WidgetMarker, and is a base for containers, which need to
 * override default DSL receiver constraints (e.g. FormPanel).
 *
 * @constructor
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@ExperimentalNonDslContainer
open class BasicPanel(classes: Set<String> = setOf(), init: (BasicPanel.() -> Unit)? = null) : Widget(classes),
    Container {
    protected val privateChildren: MutableList<Component> = mutableListOf()
    protected val children: MutableList<Component> = mutableListOf()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("div", childrenVNodes())
    }

    /**
     * Returns the array of the children Snabbdom vnodes.
     * @return array of children vnodes
     */
    protected open fun childrenVNodes(): Array<VNode> {
        return (privateChildren + children).filter { it.visible }.map { it.renderVNode() }.toTypedArray()
    }

    /**
     * Protected and final method to add given component to the current container as a private child (not removable).
     * @param child child component
     * @return current container
     */
    protected open fun addPrivate(child: Component): BasicPanel {
        privateChildren.add(child)
        child.parent?.remove(child)
        child.parent = this
        refresh()
        return this
    }

    /**
     * Protected and final method to add given component to the current container.
     * @param child child component
     * @return current container
     */
    protected open fun addInternal(child: Component): BasicPanel {
        children.add(child)
        child.parent?.remove(child)
        child.parent = this
        refresh()
        return this
    }

    override fun add(child: Component): BasicPanel {
        return addInternal(child)
    }

    override fun addAll(children: List<Component>): BasicPanel {
        this.children.addAll(children)
        children.map { it.parent = this }
        refresh()
        return this
    }

    override fun remove(child: Component): BasicPanel {
        if (children.remove(child)) {
            child.clearParent()
            refresh()
        }
        return this
    }

    override fun removeAll(): BasicPanel {
        children.map { it.clearParent() }
        children.clear()
        refresh()
        return this
    }

    override fun disposeAll(): Container {
        children.forEach { it.dispose() }
        return removeAll()
    }

    override fun getChildren(): List<Component> {
        return children
    }

    override fun dispose() {
        super.dispose()
        children.forEach { it.dispose() }
        privateChildren.forEach { it.dispose() }
        children.map { it.clearParent() }
        children.clear()
        privateChildren.map { it.clearParent() }
        privateChildren.clear()
    }
}
