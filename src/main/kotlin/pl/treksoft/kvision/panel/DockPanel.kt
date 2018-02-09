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
 */
open class DockPanel(classes: Set<String> = setOf()) : SimplePanel(classes = classes) {
    /**
     * @suppress
     * Internal property.
     */
    protected var left: Component? = null
    /**
     * @suppress
     * Internal property.
     */
    protected var center: Component? = null
    /**
     * @suppress
     * Internal property.
     */
    protected var right: Component? = null
    /**
     * @suppress
     * Internal property.
     */
    protected var up: Component? = null
    /**
     * @suppress
     * Internal property.
     */
    protected var down: Component? = null

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
                up?.let { mainContainer.remove(it) }
                up = child
                mainContainer.add(child, 1, alignSelf = FLEXALIGNITEMS.CENTER)
            }
            SIDE.CENTER -> {
                center?.let { subContainer.remove(it) }
                center = child
                subContainer.add(child, 2)
            }
            SIDE.LEFT -> {
                left?.let { subContainer.remove(it) }
                left = child
                subContainer.add(child, 1)
            }
            SIDE.RIGHT -> {
                right?.let { subContainer.remove(it) }
                right = child
                subContainer.add(child, 3)
            }
            SIDE.DOWN -> {
                down?.let { mainContainer.remove(it) }
                down = child
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
        if (child == left) removeAt(SIDE.LEFT)
        if (child == center) removeAt(SIDE.CENTER)
        if (child == right) removeAt(SIDE.RIGHT)
        if (child == up) removeAt(SIDE.UP)
        if (child == down) removeAt(SIDE.DOWN)
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
                up?.let { mainContainer.remove(it) }
                up = null
            }
            SIDE.CENTER -> {
                center?.let { subContainer.remove(it) }
                center = null
            }
            SIDE.LEFT -> {
                left?.let { subContainer.remove(it) }
                left = null
            }
            SIDE.RIGHT -> {
                right?.let { subContainer.remove(it) }
                right = null
            }
            SIDE.DOWN -> {
                down?.let { mainContainer.remove(it) }
                down = null
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
}
