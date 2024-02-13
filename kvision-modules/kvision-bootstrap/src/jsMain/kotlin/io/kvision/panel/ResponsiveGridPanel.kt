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

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.WidgetWrapper
import io.kvision.html.Align
import io.kvision.html.TAG
import io.kvision.html.Tag
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

/**
 * Bootstrap grid sizes.
 */
enum class GridSize(internal val size: String) {
    XS("xs"),
    SM("sm"),
    MD("md"),
    LG("lg"),
    XL("xl"),
    XXL("xxl")
}

internal const val MAX_COLUMNS = 12

internal data class WidgetParam(val widget: Component, val size: Int, val offset: Int, val className: String?)

/**
 * The container with support for Bootstrap responsive grid layout.
 *
 * @constructor
 * @param gridSize grid size
 * @param rows number of rows
 * @param cols number of columns
 * @param align text align of grid cells
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class ResponsiveGridPanel(
    private val gridSize: GridSize = GridSize.MD,
    private var rows: Int = 0, private var cols: Int = 0, align: Align? = null,
    className: String? = null, init: (ResponsiveGridPanel.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "container-fluid") {

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
     * @param className additional css styles
     */
    open fun add(
        child: Component,
        col: Int,
        row: Int,
        size: Int = 0,
        offset: Int = 0,
        className: String? = null
    ) {
        val cRow = maxOf(row, 1)
        val cCol = maxOf(col, 1)
        if (cRow > rows) rows = cRow
        if (cCol > cols) cols = cCol
        map.getOrPut(cRow) { mutableMapOf() }[cCol] = WidgetParam(child, size, offset, className)
        if (size > 0 || offset > 0) auto = false
        refreshRowContainers()
    }

    /**
     * DSL function to add components with additional options.
     * @param builder DSL builder function
     */
    open fun options(
        col: Int, row: Int, size: Int = 0, offset: Int = 0, className: String? = null,
        builder: Container.() -> Unit
    ) {
        object : Container by this@ResponsiveGridPanel {
            override fun add(child: Component) {
                add(child, col, row, size, offset, className)
            }
        }.builder()
    }

    override fun add(child: Component) {
        this.add(child, this.cols, 0)
    }

    override fun addAll(children: List<Component>) {
        singleRender {
            children.forEach { this.add(it) }
        }
    }

    @Suppress("NestedBlockDepth")
    override fun remove(child: Component) {
        map.values.forEach { row ->
            row.filterValues { it.widget == child }
                .forEach { (i, _) -> row.remove(i) }
        }
        refreshRowContainers()
    }

    /**
     * Removes child component at given location (column, row).
     * @param col column number
     * @param row row number
     */
    open fun removeAt(col: Int, row: Int) {
        map[row]?.remove(col)
        refreshRowContainers()
    }

    @Suppress("ComplexMethod", "NestedBlockDepth")
    private fun refreshRowContainers() {
        singleRender {
            disposeAll()
            val num = MAX_COLUMNS / cols
            for (i in 1..rows) {
                val rowContainer = SimplePanel("row")
                val row = map[i]
                if (row != null) {
                    (1..cols).map { row[it] }.forEach { wp ->
                        if (auto) {
                            val widget = wp?.widget?.let {
                                WidgetWrapper(
                                    it,
                                    "col-" + gridSize.size + "-" + num + (wp.className?.let { " $it" } ?: ""))
                            } ?: Tag(TAG.DIV, className = "col-" + gridSize.size + "-" + num)
                            align?.let {
                                widget.addCssClass(it.className)
                            }
                            rowContainer.add(widget)
                        } else {
                            if (wp != null) {
                                val s = if (wp.size > 0) wp.size else num
                                val widget = WidgetWrapper(
                                    wp.widget,
                                    "col-" + gridSize.size + "-" + s + (wp.className?.let { " $it" } ?: ""))
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
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.responsiveGridPanel(
    gridSize: GridSize = GridSize.MD,
    rows: Int = 0, cols: Int = 0, align: Align? = null,
    className: String? = null,
    init: (ResponsiveGridPanel.() -> Unit)? = null
): ResponsiveGridPanel {
    val responsiveGridPanel = ResponsiveGridPanel(gridSize, rows, cols, align, className, init)
    this.add(responsiveGridPanel)
    return responsiveGridPanel
}
