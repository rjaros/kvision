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
 * Sorter types.
 */
enum class SorterType {
    ASC,
    DESC
}

/**
 * A container class with support for mutable/observable data model.
 *
 * @constructor Creates DataContainer bound to given data model.
 * @param M data model type
 * @param C visual component type
 * @param CONT container type
 * @param model data model of type *MutableList<M>*
 * @param factory a function which creates component C from data model at given index
 * @param container internal container
 * @param containerAdd function to add component C to the internal container CONT
 * @param filter a filtering function
 * @param sorter a sorting function
 * @param sorterType a sorting type selection function
 * @param init an initializer extension function
 */
class DataContainer<M, C : Component, CONT : Container>(
    private val model: MutableList<M>,
    private val factory: (M, Int, MutableList<M>) -> C,
    private val container: CONT,
    private val containerAdd: (CONT.(C, M) -> Unit)? = null,
    private val filter: ((M) -> Boolean)? = null,
    private val sorter: ((M) -> Comparable<*>?)? = null,
    private val sorterType: () -> SorterType = { SorterType.ASC },
    init: (DataContainer<M, C, CONT>.() -> Unit)? = null
) :
    Widget(setOf()), Container, DataUpdatable {

    override var visible
        get() = container.visible
        set(value) {
            container.visible = value
        }

    internal var onUpdateHandler: (() -> Unit)? = null

    init {
        container.parent = this
        if (model is ObservableList) {
            model.onUpdate += {
                update()
            }
        }
        update()
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun add(child: Component): Container {
        this.container.add(child)
        return this
    }

    override fun addAll(children: List<Component>): Container {
        this.container.addAll(children)
        return this
    }

    override fun remove(child: Component): Container {
        this.container.remove(child)
        return this
    }

    override fun removeAll(): Container {
        this.container.removeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return this.container.getChildren()
    }

    override fun renderVNode(): VNode {
        return this.container.renderVNode()
    }

    /**
     * Updates view from the current data model state.
     */
    @Suppress("ComplexMethod")
    override fun update() {
        model.forEach {
            if (it is DataComponent) it.container = this
        }
        singleRender {
            container.removeAll()
            val indexed = model.mapIndexed { index, m -> m to index }
            val sorted = if (sorter != null) {
                when (sorterType()) {
                    SorterType.ASC ->
                        indexed.sortedBy {
                            @Suppress("UNCHECKED_CAST")
                            sorter.invoke(it.first) as Comparable<Any>?
                        }
                    SorterType.DESC ->
                        indexed.sortedByDescending {
                            @Suppress("UNCHECKED_CAST")
                            sorter.invoke(it.first) as Comparable<Any>?
                        }
                }
            } else {
                indexed
            }
            val filtered = if (filter != null) {
                sorted.filter { filter.invoke(it.first) }
            } else {
                sorted
            }
            val children = filtered.map { p -> p.first to factory(p.first, p.second, model) }
            if (containerAdd != null) {
                children.forEach { child ->
                    containerAdd.invoke(container, child.second, child.first)
                }
            } else {
                container.addAll(children.map { it.second })
            }
        }
        onUpdateHandler?.invoke()
    }

    /**
     * Sets a notification handler called after every update.
     * @param handler notification handler
     * @return current container
     */
    fun onUpdate(handler: () -> Unit): DataContainer<M, C, CONT> {
        onUpdateHandler = handler
        return this
    }

    /**
     * Clears notification handler.
     * @return current container
     */
    fun clearOnUpdate(): DataContainer<M, C, CONT> {
        onUpdateHandler = null
        return this
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun <M, C : Component, CONT : Container> Container.dataContainer(
            model: MutableList<M>,
            factory: (M, Int, MutableList<M>) -> C,
            container: CONT,
            containerAdd: (CONT.(C, M) -> Unit)? = null,
            filter: ((M) -> Boolean)? = null,
            sorter: ((M) -> Comparable<*>?)? = null,
            sorterType: () -> SorterType = { SorterType.ASC },
            init: (DataContainer<M, C, CONT>.() -> Unit)? = null
        ): DataContainer<M, C, CONT> {
            val dataContainer = DataContainer(model, factory, container, containerAdd, filter, sorter, sorterType, init)
            this.add(dataContainer)
            return dataContainer
        }

        /**
         * DSL builder extension function with VPanel default.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun <M, C : Component> Container.dataContainer(
            model: MutableList<M>,
            factory: (M, Int, MutableList<M>) -> C,
            containerAdd: (VPanel.(C, M) -> Unit)? = null,
            filter: ((M) -> Boolean)? = null,
            sorter: ((M) -> Comparable<*>?)? = null,
            sorterType: () -> SorterType = { SorterType.ASC },
            init: (DataContainer<M, C, VPanel>.() -> Unit)? = null
        ): DataContainer<M, C, VPanel> {
            val dataContainer = DataContainer(model, factory, VPanel(), containerAdd, filter, sorter, sorterType, init)
            this.add(dataContainer)
            return dataContainer
        }
    }
}
