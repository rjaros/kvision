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

import io.kvision.core.AlignItems
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.ExperimentalNonDslContainer
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.utils.perc

/**
 * The container with dock layout (up, down, left, right and center positions).
 *
 * This container is not annotated with a @WidgetMarker.
 * It should be used only as a base class for other components.
 *
 * @constructor
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@ExperimentalNonDslContainer
open class BasicDockPanel(classes: Set<String> = setOf(), init: (BasicDockPanel.() -> Unit)? = null) :
    BasicPanel(classes = classes) {
    /**
     * @suppress
     * Internal property.
     */
    protected var leftComponent: Component? = null

    /**
     * @suppress
     * Internal property.
     */
    protected var centerComponent: Component? = null

    /**
     * @suppress
     * Internal property.
     */
    protected var rightComponent: Component? = null

    /**
     * @suppress
     * Internal property.
     */
    protected var upComponent: Component? = null

    /**
     * @suppress
     * Internal property.
     */
    protected var downComponent: Component? = null

    /**
     * @suppress
     * Internal property.
     */
    protected val mainContainer = FlexPanel(
        direction = FlexDirection.COLUMN, justify = JustifyContent.SPACEBETWEEN, alignItems = AlignItems.STRETCH
    ) {
        @Suppress("MagicNumber")
        width = 100.perc
        @Suppress("MagicNumber")
        height = 100.perc
    }

    /**
     * @suppress
     * Internal property.
     */
    protected val subContainer = FlexPanel(justify = JustifyContent.SPACEBETWEEN, alignItems = AlignItems.STRETCH) {
        @Suppress("MagicNumber")
        width = 100.perc
        @Suppress("MagicNumber")
        height = 100.perc
    }

    init {
        this.addPrivate(mainContainer)
        mainContainer.add(subContainer, 2, grow = 1, basis = 0.perc)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * DSL function to add components at the UP position.
     * @param builder DSL builder function
     */
    open fun up(builder: Container.() -> Unit) {
        object : Container by this@BasicDockPanel {
            override fun add(child: Component): Container {
                return add(child, Side.UP)
            }
        }.builder()
    }

    /**
     * DSL function to add components at the DOWN position.
     * @param builder DSL builder function
     */
    open fun down(builder: Container.() -> Unit) {
        object : Container by this@BasicDockPanel {
            override fun add(child: Component): Container {
                return add(child, Side.DOWN)
            }
        }.builder()
    }

    /**
     * DSL function to add components on the LEFT side.
     * @param builder DSL builder function
     */
    open fun left(builder: Container.() -> Unit) {
        object : Container by this@BasicDockPanel {
            override fun add(child: Component): Container {
                return add(child, Side.LEFT)
            }
        }.builder()
    }

    /**
     * DSL function to add components on the RIGHT side.
     * @param builder DSL builder function
     */
    open fun right(builder: Container.() -> Unit) {
        object : Container by this@BasicDockPanel {
            override fun add(child: Component): Container {
                return add(child, Side.RIGHT)
            }
        }.builder()
    }

    /**
     * DSL function to add components in CENTER.
     * @param builder DSL builder function
     */
    open fun center(builder: Container.() -> Unit) {
        val tempPanel = object : Container by this@BasicDockPanel {
            override fun add(child: Component): Container {
                return add(child, Side.CENTER)
            }
        }
        tempPanel.builder()
    }

    /**
     * Adds a component to the dock container.
     * @param child child component
     * @param position position in the dock
     * @return current container
     */
    @Suppress("MagicNumber", "ComplexMethod")
    open fun add(child: Component, position: Side): BasicDockPanel {
        when (position) {
            Side.UP -> {
                upComponent?.let { mainContainer.remove(it) }
                upComponent = child
                mainContainer.add(child, 1, basis = 0.perc)
            }
            Side.CENTER -> {
                centerComponent?.let { subContainer.remove(it) }
                centerComponent = child
                subContainer.add(child, 2, grow = 1, basis = 0.perc)
            }
            Side.LEFT -> {
                leftComponent?.let { subContainer.remove(it) }
                leftComponent = child
                subContainer.add(child, 1, basis = 0.perc)
            }
            Side.RIGHT -> {
                rightComponent?.let { subContainer.remove(it) }
                rightComponent = child
                subContainer.add(child, 3, basis = 0.perc)
            }
            Side.DOWN -> {
                downComponent?.let { mainContainer.remove(it) }
                downComponent = child
                mainContainer.add(child, 3, basis = 0.perc)
            }
        }
        return this
    }

    override fun add(child: Component): BasicDockPanel {
        return this.add(child, Side.CENTER)
    }

    override fun addAll(children: List<Component>): BasicDockPanel {
        children.forEach { this.add(it) }
        return this
    }

    override fun remove(child: Component): BasicDockPanel {
        if (child == leftComponent) removeAt(Side.LEFT)
        if (child == centerComponent) removeAt(Side.CENTER)
        if (child == rightComponent) removeAt(Side.RIGHT)
        if (child == upComponent) removeAt(Side.UP)
        if (child == downComponent) removeAt(Side.DOWN)
        return this
    }

    /**
     * Removes child from given position in the dock.
     * @param position position in the dock
     * @return current container
     */
    @Suppress("ComplexMethod")
    open fun removeAt(position: Side): BasicDockPanel {
        when (position) {
            Side.UP -> {
                upComponent?.let { mainContainer.remove(it) }
                upComponent = null
            }
            Side.CENTER -> {
                centerComponent?.let { subContainer.remove(it) }
                centerComponent = null
            }
            Side.LEFT -> {
                leftComponent?.let { subContainer.remove(it) }
                leftComponent = null
            }
            Side.RIGHT -> {
                rightComponent?.let { subContainer.remove(it) }
                rightComponent = null
            }
            Side.DOWN -> {
                downComponent?.let { mainContainer.remove(it) }
                downComponent = null
            }
        }
        return this
    }

    override fun removeAll(): BasicDockPanel {
        removeAt(Side.LEFT)
        removeAt(Side.CENTER)
        removeAt(Side.RIGHT)
        removeAt(Side.UP)
        removeAt(Side.DOWN)
        return this
    }

    override fun disposeAll(): Container {
        leftComponent?.dispose()
        centerComponent?.dispose()
        rightComponent?.dispose()
        upComponent?.dispose()
        downComponent?.dispose()
        return removeAll()
    }
}
