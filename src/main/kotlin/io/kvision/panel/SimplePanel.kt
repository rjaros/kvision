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
import io.kvision.core.Widget
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.set

/**
 * Basic container class, rendered as a DIV element with all children directly within.
 *
 * @constructor
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class SimplePanel(classes: Set<String> = setOf(), init: (SimplePanel.() -> Unit)? = null) : Widget(classes),
    Container {
    protected val privateChildren: MutableList<Component> = mutableListOf()
    protected val children: MutableList<Component> = mutableListOf()

    internal var archivedState: dynamic = null

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
    protected open fun addPrivate(child: Component): SimplePanel {
        privateChildren.add(child)
        child.parent?.remove(child)
        child.parent = this
        refresh()
        return this
    }

    /**
     * Protected method to add given component to the current container.
     * @param child child component
     * @return current container
     */
    protected open fun addInternal(child: Component): SimplePanel {
        children.add(child)
        child.parent?.remove(child)
        child.parent = this
        refresh()
        return this
    }

    /**
     * Protected method to add given component to the current container at the given position.
     * @param position the position to insert child component
     * @param child child component
     * @return current container
     */
    protected open fun addInternal(position: Int, child: Component): SimplePanel {
        children.add(position, child)
        child.parent?.remove(child)
        child.parent = this
        refresh()
        return this
    }

    override fun add(child: Component): SimplePanel {
        return addInternal(child)
    }

    override fun add(position: Int, child: Component): SimplePanel {
        return addInternal(position, child)
    }

    override fun addAll(children: List<Component>): SimplePanel {
        this.children.addAll(children)
        children.map {
            it.parent?.remove(it)
            it.parent = this
        }
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

    override fun removeAt(position: Int): SimplePanel {
        val child = children.getOrNull(position)
        if (child != null) {
            children.removeAt(position)
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

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.simplePanel(
    classes: Set<String>? = null,
    className: String? = null,
    init: (SimplePanel.() -> Unit)? = null
): SimplePanel {
    val simplePanel = SimplePanel(classes ?: className.set, init)
    this.add(simplePanel)
    return simplePanel
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.simplePanel(
    state: ObservableState<S>,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SimplePanel.(S) -> Unit)
) = simplePanel(classes, className).bind(state, true, init)
