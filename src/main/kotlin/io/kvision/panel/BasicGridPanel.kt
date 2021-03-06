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

import io.kvision.core.*

/**
 * The container with CSS grid layout support.
 *
 * This container is not annotated with a @WidgetMarker.
 * It should be used only as a base class for other components.
 *
 * @constructor
 * @param autoColumns grid auto columns
 * @param autoRows grid auto rows
 * @param autoFlow grid auto flow
 * @param templateColumns grid columns template
 * @param templateRows grid rows template
 * @param templateAreas grid areas template
 * @param columnGap grid column gap
 * @param rowGap grid row gap
 * @param justifyItems grid items justification
 * @param alignItems grid items alignment
 * @param justifyContent flexbox content justification
 * @param alignContent flexbox content alignment
 * @param noWrappers do not use additional div wrappers for child items
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
@ExperimentalNonDslContainer
open class BasicGridPanel(
    autoColumns: String? = null, autoRows: String? = null, autoFlow: GridAutoFlow? = null,
    templateColumns: String? = null, templateRows: String? = null, templateAreas: List<String>? = null,
    columnGap: Int? = null, rowGap: Int? = null, justifyItems: JustifyItems? = null,
    alignItems: AlignItems? = null, justifyContent: JustifyContent? = null,
    alignContent: AlignContent? = null, private val noWrappers: Boolean = false,
    classes: Set<String> = setOf(), init: (BasicGridPanel.() -> Unit)? = null
) : BasicPanel(classes) {

    init {
        this.display = Display.GRID
        this.gridAutoColumns = autoColumns
        this.gridAutoRows = autoRows
        this.gridAutoFlow = autoFlow
        this.gridTemplateColumns = templateColumns
        this.gridTemplateRows = templateRows
        this.gridTemplateAreas = templateAreas
        this.gridColumnGap = columnGap
        this.gridRowGap = rowGap
        this.justifyItems = justifyItems
        this.alignItems = alignItems
        this.justifyContent = justifyContent
        this.alignContent = alignContent
        init?.invoke(this)
    }

    /**
     * Adds a component to the grid container.
     * @param child child component
     * @param columnStart number of starting column
     * @param rowStart number of starting row
     * @param columnEnd number of ending column
     * @param rowEnd number of ending row
     * @param area grid area
     * @param justifySelf child self justification
     * @param alignSelf child self alignment
     * @param classes a set of CSS class names
     * @return current container
     */
    @Suppress("LongParameterList")
    fun add(
        child: Component, columnStart: Int? = null, rowStart: Int? = null,
        columnEnd: String? = null, rowEnd: String? = null, area: String? = null, justifySelf: JustifyItems? = null,
        alignSelf: AlignItems? = null, classes: Set<String> = setOf()
    ): BasicGridPanel {
        val wrapper = if (noWrappers) {
            child
        } else {
            WidgetWrapper(child, classes)
        }
        (wrapper as? Widget)?.let {
            it.gridColumnStart = columnStart
            it.gridRowStart = rowStart
            it.gridColumnEnd = columnEnd
            it.gridRowEnd = rowEnd
            it.gridArea = area
            it.justifySelf = justifySelf
            it.alignSelf = alignSelf
        }
        addInternal(wrapper)
        return this
    }

    /**
     * DSL function to add components with additional options.
     * @param builder DSL builder function
     */
    open fun options(
        columnStart: Int? = null, rowStart: Int? = null,
        columnEnd: String? = null, rowEnd: String? = null, area: String? = null, justifySelf: JustifyItems? = null,
        alignSelf: AlignItems? = null, classes: Set<String> = setOf(),
        builder: Container.() -> Unit
    ) {
        object : Container by this@BasicGridPanel {
            override fun add(child: Component): Container {
                return add(child, columnStart, rowStart, columnEnd, rowEnd, area, justifySelf, alignSelf, classes)
            }
        }.builder()
    }

    override fun add(child: Component): BasicGridPanel {
        return add(child, null, null)
    }

    override fun addAll(children: List<Component>): BasicGridPanel {
        children.forEach { add(it, null, null) }
        return this
    }

    override fun remove(child: Component): BasicGridPanel {
        if (children.contains(child)) {
            super.remove(child)
        } else {
            children.find { (it as? WidgetWrapper)?.wrapped == child }?.let {
                super.remove(it)
                it.dispose()
            }
        }
        return this
    }

    override fun removeAll(): BasicGridPanel {
        children.map {
            it.clearParent()
            (it as? WidgetWrapper)?.dispose()
        }
        children.clear()
        refresh()
        return this
    }

    override fun disposeAll(): BasicGridPanel {
        children.map {
            (it as? WidgetWrapper)?.let {
                it.wrapped?.dispose()
            }
        }
        return removeAll()
    }

    override fun dispose() {
        children.map {
            (it as? WidgetWrapper)?.let {
                it.wrapped?.dispose()
            }
        }
        super.dispose()
    }
}
