package pl.treksoft.kvision.panel

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

enum class GRIDTYPE {
    BOOTSTRAP,
    DSG
}

enum class GRIDSIZE(val size: String) {
    XS("xs"),
    SM("sm"),
    MD("md"),
    LG("lg")
}

const val FULLPERCENT = 100
const val MAX_COLUMNS = 12

internal data class WidgetParam(val widget: Widget, val size: Int, val offset: Int)

open class GridPanel(private val gridtype: GRIDTYPE = GRIDTYPE.BOOTSTRAP, private val gridsize: GRIDSIZE = GRIDSIZE.MD,
                     protected var rows: Int = 0, protected var cols: Int = 0, align: ALIGN = ALIGN.NONE,
                     classes: Set<String> = setOf()) : Container(classes) {
    var align = align
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
        return this
    }

    override fun add(child: Widget): Container {
        return this.add(child, 0, this.cols)
    }

    override fun addAll(children: List<Widget>): Container {
        children.forEach { this.add(it) }
        return this
    }

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
        return this
    }

    open fun removeAt(row: Int, col: Int): Container {
        map[row]?.remove(col)
        return this
    }

    override fun removeAt(index: Int): Container {
        return this.removeAt(0, index)
    }

    override fun childrenVNodes(): Array<VNode> {
        return if (gridtype == GRIDTYPE.BOOTSTRAP) {
            childrenVNodesBts()
        } else {
            childrenVNodesDsg()
        }
    }

    @Suppress("NestedBlockDepth", "LoopToCallChain")
    protected open fun childrenVNodesDsg(): Array<VNode> {
        val ret = mutableListOf<VNode>()
        val num = FULLPERCENT / cols
        for (i in 0 until rows) {
            val rowContainer = Container(setOf("dsgrow"))
            val row = map[i]
            if (row != null) {
                for (j in 0 until cols) {
                    val wp = row[j]
                    val widget = wp?.widget?.addCssClass("dsgcol") ?: Tag(TAG.DIV, classes = setOf("dsgcol"))
                    widget.widthPercent = num
                    if (align != ALIGN.NONE) {
                        widget.addCssClass(align.className)
                    }
                    rowContainer.add(widget)
                }
            }
            ret.add(rowContainer.render())
        }
        return ret.toTypedArray()
    }

    @Suppress("NestedBlockDepth", "LoopToCallChain")
    private fun childrenVNodesBts(): Array<VNode> {
        val ret = mutableListOf<VNode>()
        val num = MAX_COLUMNS / cols
        for (i in 0 until rows) {
            val rowContainer = Container(setOf("row"))
            val row = map[i]
            if (row != null) {
                for (j in 0 until cols) {
                    val wp = row[j]
                    if (auto) {
                        val widget = wp?.widget?.addCssClass("col-" + gridsize.size + "-" + num) ?:
                                Tag(TAG.DIV, classes = setOf("col-" + gridsize.size + "-" + num))
                        if (align != ALIGN.NONE) {
                            widget.addCssClass(align.className)
                        }
                        rowContainer.add(widget)
                    } else {
                        if (wp != null) {
                            val s = if (wp.size > 0) wp.size else num
                            wp.widget.addCssClass("col-" + gridsize.size + "-" + s)
                            if (wp.offset > 0) {
                                wp.widget.addCssClass("col-" + gridsize.size + "-offset-" + wp.offset)
                            }
                            if (align != ALIGN.NONE) {
                                wp.widget.addCssClass(align.className)
                            }
                            rowContainer.add(wp.widget)
                        }
                    }
                }
            }
            ret.add(rowContainer.render())
        }
        return ret.toTypedArray()
    }
}
