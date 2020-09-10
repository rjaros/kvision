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

import pl.treksoft.kvision.core.AlignContent
import pl.treksoft.kvision.core.AlignItems
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Display
import pl.treksoft.kvision.core.GridAutoFlow
import pl.treksoft.kvision.core.JustifyContent
import pl.treksoft.kvision.core.JustifyItems
import pl.treksoft.kvision.core.WidgetWrapper
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.set

@Deprecated(
    "Use pl.treksoft.kvision.core.JustifyItems instead.",
    replaceWith = ReplaceWith("JustifyItems", "pl.treksoft.kvision.core.JustifyItems")
)
typealias GridJustify = JustifyItems

@Deprecated(
    "Use pl.treksoft.kvision.core.AlignItems instead.",
    replaceWith = ReplaceWith("AlignItems", "pl.treksoft.kvision.core.AlignItems")
)
typealias GridAlign = AlignItems

@Deprecated(
    "Use pl.treksoft.kvision.core.JustifyContent instead.",
    replaceWith = ReplaceWith("JustifyContent", "pl.treksoft.kvision.core.JustifyContent")
)
typealias GridJustifyContent = JustifyContent

@Deprecated(
    "Use pl.treksoft.kvision.core.AlignContent instead.",
    replaceWith = ReplaceWith("AlignContent", "pl.treksoft.kvision.core.AlignContent")
)
typealias GridAlignContent = AlignContent

@Deprecated(
    "Use pl.treksoft.kvision.core.GridAutoFlow instead.",
    replaceWith = ReplaceWith("GridAutoFlow", "pl.treksoft.kvision.core.GridAutoFlow")
)
typealias GridFlow = GridAutoFlow

/**
 * The container with CSS grid layout support.
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
open class GridPanel(
    autoColumns: String? = null, autoRows: String? = null, autoFlow: GridAutoFlow? = null,
    templateColumns: String? = null, templateRows: String? = null, templateAreas: List<String>? = null,
    columnGap: Int? = null, rowGap: Int? = null, justifyItems: JustifyItems? = null,
    alignItems: AlignItems? = null, justifyContent: JustifyContent? = null,
    alignContent: AlignContent? = null, private val noWrappers: Boolean = false,
    classes: Set<String> = setOf(), init: (GridPanel.() -> Unit)? = null
) : SimplePanel(classes) {

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
    ): GridPanel {
        val wrapper = if (noWrappers) {
            child
        } else {
            WidgetWrapper(child, classes).apply {
                this.gridColumnStart = columnStart
                this.gridRowStart = rowStart
                this.gridColumnEnd = columnEnd
                this.gridRowEnd = rowEnd
                this.gridArea = area
                this.justifySelf = justifySelf
                this.alignSelf = alignSelf
            }
        }
        addInternal(wrapper)
        return this
    }

    override fun add(child: Component): GridPanel {
        return add(child, null, null)
    }

    override fun addAll(children: List<Component>): GridPanel {
        children.forEach { add(it, null, null) }
        return this
    }

    override fun remove(child: Component): GridPanel {
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

    override fun removeAll(): GridPanel {
        children.map {
            it.clearParent()
            it.dispose()
        }
        children.clear()
        refresh()
        return this
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.gridPanel(
    autoColumns: String? = null, autoRows: String? = null, autoFlow: GridAutoFlow? = null,
    templateColumns: String? = null, templateRows: String? = null, templateAreas: List<String>? = null,
    columnGap: Int? = null, rowGap: Int? = null, justifyItems: JustifyItems? = null,
    alignItems: AlignItems? = null, justifyContent: JustifyContent? = null,
    alignContent: AlignContent? = null,
    noWrappers: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (GridPanel.() -> Unit)? = null
): GridPanel {
    val gridPanel = GridPanel(
        autoColumns,
        autoRows,
        autoFlow,
        templateColumns,
        templateRows,
        templateAreas,
        columnGap,
        rowGap,
        justifyItems,
        alignItems,
        justifyContent,
        alignContent,
        noWrappers,
        classes ?: className.set,
        init
    )
    this.add(gridPanel)
    return gridPanel
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.gridPanel(
    state: ObservableState<S>,
    autoColumns: String? = null, autoRows: String? = null, autoFlow: GridAutoFlow? = null,
    templateColumns: String? = null, templateRows: String? = null, templateAreas: List<String>? = null,
    columnGap: Int? = null, rowGap: Int? = null, justifyItems: JustifyItems? = null,
    alignItems: AlignItems? = null, justifyContent: JustifyContent? = null,
    alignContent: AlignContent? = null,
    noWrappers: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (GridPanel.(S) -> Unit)
) = gridPanel(
    autoColumns,
    autoRows,
    autoFlow,
    templateColumns,
    templateRows,
    templateAreas,
    columnGap,
    rowGap,
    justifyItems,
    alignItems,
    justifyContent,
    alignContent,
    noWrappers,
    classes,
    className,
).bind(state, true, init)
