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
import io.kvision.core.Style
import io.kvision.core.Widget

/**
 * Basic container class, rendered as a DIV element with all children directly within.
 *
 * @constructor
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class SimplePanel(className: String? = null, init: (SimplePanel.() -> Unit)? = null) : Widget(className),
    Container {
    protected var privateChildren: MutableList<Component>? = null
    protected var children: MutableList<Component>? = null

    /**
     * @suppress
     * Internal variable
     */
    var _archivedState: dynamic = null

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
    @Suppress("UnsafeCastFromDynamic")
    protected open fun childrenVNodes(): Array<VNode> {
        return if (privateChildren == null && children == null) {
            emptyArray()
        } else if (privateChildren == null && children != null) {
            children!!.toTypedArray().asDynamic().filter { c: Component -> c.visible }
                .map { c: Component -> c.renderVNode() }
        } else if (privateChildren != null && children == null) {
            privateChildren!!.toTypedArray().asDynamic().filter { c: Component -> c.visible }
                .map { c: Component -> c.renderVNode() }
        } else {
            (privateChildren!! + children!!).toTypedArray().asDynamic().filter { c: Component -> c.visible }
                .map { c: Component -> c.renderVNode() }
        }
    }

    /**
     * Protected and final method to add given component to the current container as a private child (not removable).
     * @param child child component
     * @return current container
     */
    protected open fun addPrivate(child: Component): SimplePanel {
        if (privateChildren == null) privateChildren = mutableListOf()
        privateChildren!!.add(child)
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
        if (children == null) children = mutableListOf()
        children!!.add(child)
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
        if (children == null) children = mutableListOf()
        children!!.add(position, child)
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
        if (this.children == null) this.children = mutableListOf()
        this.children!!.addAll(children)
        children.map {
            it.parent?.remove(it)
            it.parent = this
        }
        refresh()
        return this
    }

    override fun remove(child: Component): SimplePanel {
        if (children != null && children!!.remove(child)) {
            child.clearParent()
            refresh()
        }
        return this
    }

    override fun removeAt(position: Int): SimplePanel {
        val child = children?.getOrNull(position)
        if (child != null) {
            children?.removeAt(position)
            child.clearParent()
            refresh()
        }
        return this
    }

    override fun removeAll(): SimplePanel {
        children?.map { it.clearParent() }
        children = null
        refresh()
        return this
    }

    override fun disposeAll(): Container {
        children?.forEach { it.dispose() }
        return removeAll()
    }

    override fun getChildren(): List<Component> {
        return children ?: emptyList()
    }

    override fun dispose() {
        super.dispose()
        children?.forEach { it.dispose() }
        privateChildren?.forEach { it.dispose() }
        children?.map { it.clearParent() }
        children?.clear()
        children = null
        privateChildren?.map { it.clearParent() }
        privateChildren?.clear()
        privateChildren = null
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.simplePanel(
    className: String? = null,
    init: (SimplePanel.() -> Unit)? = null
): SimplePanel {
    val simplePanel = SimplePanel(className, init)
    this.add(simplePanel)
    return simplePanel
}

/**
 * DSL builder extension function with Style support
 */
fun Container.simplePanel(
    vararg styles: Style,
    init: (SimplePanel.() -> Unit)? = null
): SimplePanel {
    val simplePanel = SimplePanel(init = init).apply {
        styles.forEach { this.addCssStyle(it) }
    }
    this.add(simplePanel)
    return simplePanel
}
