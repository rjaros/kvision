package pl.treksoft.kvision.panel

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.core.WidgetWrapper
import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

enum class GRIDSIZE(val size: String) {
    XS("xs"),
    SM("sm"),
    MD("md"),
    LG("lg")
}

const val MAX_COLUMNS = 12

internal data class WidgetParam(val widget: Widget, val size: Int, val offset: Int)

open class ResponsiveGridPanel(private val gridsize: GRIDSIZE = GRIDSIZE.MD,
                               private var rows: Int = 0, private var cols: Int = 0, align: ALIGN? = null,
                               classes: Set<String> = setOf()) : Container(classes) {
    protected var align = align
        set(value) {
            field = value
            refresh()
        }

    internal val map = mutableMapOf<Int, MutableMap<Int, WidgetParam>>()
    private var auto: Boolean = true

    open fun add(child: Widget, row: Int, col: Int, size: Int = 0, offset: Int = 0): Container {
        val cRow = if (row < 0) 0 else row
        val cCol = if (col < 0) 0 else col
        if (row > rows - 1) rows = cRow + 1
        if (col > cols - 1) cols = cCol + 1
        map.getOrPut(cRow, { mutableMapOf() }).put(cCol, WidgetParam(child, size, offset))
        if (size > 0 || offset > 0) auto = false
        refreshRowContainers()
        return this
    }

    override fun add(child: Widget): Container {
        return this.add(child, 0, this.cols)
    }

    override fun addAll(children: List<Widget>): Container {
        children.forEach { this.add(it) }
        return this
    }

    @Suppress("NestedBlockDepth")
    override fun remove(child: Widget): Container {
        for (i in 0 until rows) {
            val row = map[i]
            if (row != null) {
                for (j in 0 until cols) {
                    val wp = row[j]
                    if (wp != null) {
                        if (wp.widget == child) row.remove(j)
                    }
                }
            }
        }
        refreshRowContainers()
        return this
    }

    open fun removeAt(row: Int, col: Int): Container {
        map[row]?.remove(col)
        refreshRowContainers()
        return this
    }

    @Suppress("ComplexMethod", "NestedBlockDepth")
    protected open fun refreshRowContainers() {
        clearRowContainers()
        val num = MAX_COLUMNS / cols
        for (i in 0 until rows) {
            val rowContainer = Container(setOf("row"))
            val row = map[i]
            if (row != null) {
                for (j in 0 until cols) {
                    val wp = row[j]
                    if (auto) {
                        val widget = wp?.widget?.let {
                            WidgetWrapper(it, setOf("col-" + gridsize.size + "-" + num))
                        } ?: Tag(TAG.DIV, classes = setOf("col-" + gridsize.size + "-" + num))
                        align?.let {
                            widget.addCssClass(it.className)
                        }
                        rowContainer.add(widget)
                    } else {
                        if (wp != null) {
                            val s = if (wp.size > 0) wp.size else num
                            val widget = WidgetWrapper(wp.widget, setOf("col-" + gridsize.size + "-" + s))
                            if (wp.offset > 0) {
                                widget.addCssClass("col-" + gridsize.size + "-offset-" + wp.offset)
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

    private fun clearRowContainers() {
        children.forEach { it.dispose() }
        removeAll()
    }
}
