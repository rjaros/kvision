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
package pl.treksoft.kvision.data

import com.github.snabbdom.VNode
import com.lightningkite.kotlin.observable.list.ObservableList
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.panel.VPanel

/**
 * A container class with support for observable data model.
 *
 * @constructor Creates DataContainer bound to given data model.
 * @param M base data model type
 * @param C base component type
 * @param model data model of type *ObservableList<M>*
 * @param binding a function which creates component C from data model at given index
 * @param child internal container (defaults to [VPanel])
 */
class DataContainer<M : DataComponent, C : Component>(
    private val model: ObservableList<M>,
    private val binding: (Int) -> C,
    private val child: Container = VPanel()
) :
    Widget(setOf()), Container, DataUpdatable {

    override var visible
        get() = child.visible
        set(value) {
            child.visible = value
        }

    internal var onUpdateHandler: (() -> Unit)? = null

    init {
        child.parent = this
        model.onUpdate += { _ ->
            update()
        }
        update()
    }

    override fun add(child: Component): Container {
        this.child.add(child)
        return this
    }

    override fun addAll(children: List<Component>): Container {
        this.child.addAll(children)
        return this
    }

    override fun remove(child: Component): Container {
        this.child.remove(child)
        return this
    }

    override fun removeAll(): Container {
        this.child.removeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return this.child.getChildren()
    }

    override fun renderVNode(): VNode {
        return this.child.renderVNode()
    }

    /**
     * Updates view from the current data model state.
     */
    override fun update() {
        model.forEach { it.container = this }
        singleRender {
            child.removeAll()
            child.addAll(model.mapIndexed { index, _ -> binding(index) })
        }
        onUpdateHandler?.invoke()
    }

    /**
     * Sets a notification handler called after every update.
     * @param handler notification handler
     * @return current container
     */
    fun onUpdate(handler: () -> Unit): DataContainer<M, C> {
        onUpdateHandler = handler
        return this
    }

    /**
     * Clears notification handler.
     * @return current container
     */
    fun clearOnUpdate(): DataContainer<M, C> {
        onUpdateHandler = null
        return this
    }
}
