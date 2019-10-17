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

import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.utils.perc

/**
 * Dock layout directions.
 */
enum class Side {
    LEFT,
    RIGHT,
    CENTER,
    UP,
    DOWN
}

/**
 * The container with dock layout (up, down, left, right and center positions).
 *
 * @constructor
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class DockPanel(classes: Set<String> = setOf(), init: (DockPanel.() -> Unit)? = null) :
    SimplePanel(classes = classes) {
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
        direction = FlexDir.COLUMN, justify = FlexJustify.SPACEBETWEEN, alignItems = FlexAlignItems.STRETCH
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
    protected val subContainer = FlexPanel(justify = FlexJustify.SPACEBETWEEN, alignItems = FlexAlignItems.STRETCH) {
        @Suppress("MagicNumber")
        width = 100.perc
        @Suppress("MagicNumber")
        height = 100.perc
    }

    init {
        this.addInternal(mainContainer)
        mainContainer.add(subContainer, 2, grow = 1, basis = 0)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * Adds a component to the dock container.
     * @param child child component
     * @param position position in the dock
     * @return current container
     */
    @Suppress("MagicNumber", "ComplexMethod")
    open fun add(child: Component, position: Side): DockPanel {
        when (position) {
            Side.UP -> {
                upComponent?.let { mainContainer.remove(it) }
                upComponent = child
                mainContainer.add(child, 1, basis = 0)
            }
            Side.CENTER -> {
                centerComponent?.let { subContainer.remove(it) }
                centerComponent = child
                subContainer.add(child, 2, grow = 1, basis = 0)
            }
            Side.LEFT -> {
                leftComponent?.let { subContainer.remove(it) }
                leftComponent = child
                subContainer.add(child, 1, basis = 0)
            }
            Side.RIGHT -> {
                rightComponent?.let { subContainer.remove(it) }
                rightComponent = child
                subContainer.add(child, 3, basis = 0)
            }
            Side.DOWN -> {
                downComponent?.let { mainContainer.remove(it) }
                downComponent = child
                mainContainer.add(child, 3, basis = 0)
            }
        }
        return this
    }

    override fun add(child: Component): DockPanel {
        return this.add(child, Side.CENTER)
    }

    override fun addAll(children: List<Component>): DockPanel {
        children.forEach { this.add(it) }
        return this
    }

    override fun remove(child: Component): DockPanel {
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
    open fun removeAt(position: Side): DockPanel {
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

    override fun removeAll(): DockPanel {
        removeAt(Side.LEFT)
        removeAt(Side.CENTER)
        removeAt(Side.RIGHT)
        removeAt(Side.UP)
        removeAt(Side.DOWN)
        return this
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.dockPanel(classes: Set<String> = setOf(), init: (DockPanel.() -> Unit)? = null): DockPanel {
    val dockPanel = DockPanel(classes, init)
    this.add(dockPanel)
    return dockPanel
}
