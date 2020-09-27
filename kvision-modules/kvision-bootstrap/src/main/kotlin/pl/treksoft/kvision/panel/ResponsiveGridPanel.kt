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
import pl.treksoft.kvision.core.WidgetWrapper
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.set

/**
 * Bootstrap grid sizes.
 */
enum class GridSize(internal val size: String) {
    SM("sm"),
    MD("md"),
    LG("lg"),
    XL("xl")
}

internal const val MAX_COLUMNS = 12

internal data class WidgetParam(val widget: Component, val size: Int, val offset: Int)

/**
 * The container with support for Bootstrap responsive grid layout.
 *
 * @constructor
 * @param gridSize grid size
 * @param rows number of rows
 * @param cols number of columns
 * @param align text align of grid cells
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class ResponsiveGridPanel(
    private val gridSize: GridSize = GridSize.MD,
    private var rows: Int = 0, private var cols: Int = 0, align: Align? = null,
    classes: Set<String> = setOf(), init: (ResponsiveGridPanel.() -> Unit)? = null
) : SimplePanel(classes + "container-fluid") {

    /**
     * Text align of grid cells.
     */
    var align by refreshOnUpdate(align) { refreshRowContainers() }

    internal val map = mutableMapOf<Int, MutableMap<Int, WidgetParam>>()
    private var auto: Boolean = true

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * Adds child component to the grid.
     * @param child child component
     * @param col column number
     * @param row row number
     * @param size cell size (colspan)
     * @param offset cell offset
     * @return this container
     */
    open fun add(child: Component, col: Int, row: Int, size: Int = 0, offset: Int = 0): ResponsiveGridPanel {
        val cRow = maxOf(row, 1)
        val cCol = maxOf(col, 1)
        if (cRow > rows) rows = cRow
        if (cCol > cols) cols = cCol
        map.getOrPut(cRow) { mutableMapOf() }[cCol] = WidgetParam(child, size, offset)
        if (size > 0 || offset > 0) auto = false
        refreshRowContainers()
        return this
    }

    /**
     * DSL function to add components with additional options.
     * @param builder DSL builder function
     */
    open fun options(
        col: Int, row: Int, size: Int = 0, offset: Int = 0,
        builder: Container.() -> Unit
    ) {
        object : Container by this@ResponsiveGridPanel {
            override fun add(child: Component): Container {
                return add(child, col, row, size, offset)
            }
        }.builder()
    }

    override fun add(child: Component): ResponsiveGridPanel {
        return this.add(child, this.cols, 0)
    }

    override fun addAll(children: List<Component>): ResponsiveGridPanel {
        children.forEach { this.add(it) }
        return this
    }

    @Suppress("NestedBlockDepth")
    override fun remove(child: Component): ResponsiveGridPanel {
        map.values.forEach { row ->
            row.filterValues { it.widget == child }
                .forEach { (i, _) -> row.remove(i) }
        }
        refreshRowContainers()
        return this
    }

    /**
     * Removes child component at given location (column, row).
     * @param col column number
     * @param row row number
     * @return this container
     */
    open fun removeAt(col: Int, row: Int): ResponsiveGridPanel {
        map[row]?.remove(col)
        refreshRowContainers()
        return this
    }

    @Suppress("ComplexMethod", "NestedBlockDepth")
    private fun refreshRowContainers() {
        singleRender {
            clearRowContainers()
            val num = MAX_COLUMNS / cols
            for (i in 1..rows) {
                val rowContainer = SimplePanel(setOf("row"))
                val row = map[i]
                if (row != null) {
                    (1..cols).map { row[it] }.forEach { wp ->
                        if (auto) {
                            val widget = wp?.widget?.let {
                                WidgetWrapper(it, setOf("col-" + gridSize.size + "-" + num))
                            } ?: Tag(TAG.DIV, classes = setOf("col-" + gridSize.size + "-" + num))
                            align?.let {
                                widget.addCssClass(it.className)
                            }
                            rowContainer.add(widget)
                        } else {
                            if (wp != null) {
                                val s = if (wp.size > 0) wp.size else num
                                val widget = WidgetWrapper(wp.widget, setOf("col-" + gridSize.size + "-" + s))
                                if (wp.offset > 0) {
                                    widget.addCssClass("offset-" + gridSize.size + "-" + wp.offset)
                                }
                                align?.let {
                                    widget.addCssClass(it.className)
                                }
                                rowContainer.add(widget)
                            }
                        }
                    }
                }
                addInternal(rowContainer)
            }
        }
    }

    private fun clearRowContainers() {
        children.forEach { it.dispose() }
        removeAll()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.responsiveGridPanel(
    gridSize: GridSize = GridSize.MD,
    rows: Int = 0, cols: Int = 0, align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (ResponsiveGridPanel.() -> Unit)? = null
): ResponsiveGridPanel {
    val responsiveGridPanel = ResponsiveGridPanel(gridSize, rows, cols, align, classes ?: className.set, init)
    this.add(responsiveGridPanel)
    return responsiveGridPanel
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.responsiveGridPanel(
    state: ObservableState<S>,
    gridSize: GridSize = GridSize.MD,
    rows: Int = 0, cols: Int = 0, align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (ResponsiveGridPanel.(S) -> Unit)
) = responsiveGridPanel(gridSize, rows, cols, align, classes, className).bind(state, true, init)
