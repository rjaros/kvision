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

/**
 * Dock layout directions.
 */
enum class SIDE {
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
        direction = FLEXDIR.COLUMN, justify = FLEXJUSTIFY.SPACEBETWEEN,
        alignItems = FLEXALIGNITEMS.STRETCH
    )
    /**
     * @suppress
     * Internal property.
     */
    protected val subContainer = FlexPanel(justify = FLEXJUSTIFY.SPACEBETWEEN, alignItems = FLEXALIGNITEMS.CENTER)

    init {
        this.addInternal(mainContainer)
        mainContainer.add(subContainer, 2)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * Adds a component to the dock container.
     * @param child child component
     * @param position position in the dock
     * @return current container
     */
    @Suppress("MagicNumber")
    open fun add(child: Component, position: SIDE): DockPanel {
        when (position) {
            SIDE.UP -> {
                upComponent?.let { mainContainer.remove(it) }
                upComponent = child
                mainContainer.add(child, 1, alignSelf = FLEXALIGNITEMS.CENTER)
            }
            SIDE.CENTER -> {
                centerComponent?.let { subContainer.remove(it) }
                centerComponent = child
                subContainer.add(child, 2)
            }
            SIDE.LEFT -> {
                leftComponent?.let { subContainer.remove(it) }
                leftComponent = child
                subContainer.add(child, 1)
            }
            SIDE.RIGHT -> {
                rightComponent?.let { subContainer.remove(it) }
                rightComponent = child
                subContainer.add(child, 3)
            }
            SIDE.DOWN -> {
                downComponent?.let { mainContainer.remove(it) }
                downComponent = child
                mainContainer.add(child, 3, alignSelf = FLEXALIGNITEMS.CENTER)
            }
        }
        return this
    }

    override fun add(child: Component): DockPanel {
        return this.add(child, SIDE.CENTER)
    }

    override fun addAll(children: List<Component>): DockPanel {
        children.forEach { this.add(it) }
        return this
    }

    override fun remove(child: Component): DockPanel {
        if (child == leftComponent) removeAt(SIDE.LEFT)
        if (child == centerComponent) removeAt(SIDE.CENTER)
        if (child == rightComponent) removeAt(SIDE.RIGHT)
        if (child == upComponent) removeAt(SIDE.UP)
        if (child == downComponent) removeAt(SIDE.DOWN)
        return this
    }

    /**
     * Removes child from given position in the dock.
     * @param position position in the dock
     * @return current container
     */
    open fun removeAt(position: SIDE): DockPanel {
        when (position) {
            SIDE.UP -> {
                upComponent?.let { mainContainer.remove(it) }
                upComponent = null
            }
            SIDE.CENTER -> {
                centerComponent?.let { subContainer.remove(it) }
                centerComponent = null
            }
            SIDE.LEFT -> {
                leftComponent?.let { subContainer.remove(it) }
                leftComponent = null
            }
            SIDE.RIGHT -> {
                rightComponent?.let { subContainer.remove(it) }
                rightComponent = null
            }
            SIDE.DOWN -> {
                downComponent?.let { mainContainer.remove(it) }
                downComponent = null
            }
        }
        return this
    }

    override fun removeAll(): DockPanel {
        removeAt(SIDE.LEFT)
        removeAt(SIDE.CENTER)
        removeAt(SIDE.RIGHT)
        removeAt(SIDE.UP)
        removeAt(SIDE.DOWN)
        return this
    }

    companion object {
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
    }
}
