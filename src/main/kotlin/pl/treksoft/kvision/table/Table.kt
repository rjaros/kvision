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
package pl.treksoft.kvision.table

import com.github.snabbdom.VNode
import com.github.snabbdom.h
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.snClasses
import pl.treksoft.kvision.utils.snOpt

/**
 * HTML table types.
 */
enum class TableType(internal val type: String) {
    STRIPED("table-striped"),
    BORDERED("table-bordered"),
    HOVER("table-hover"),
    CONDENSED("table-condensed")
}

/**
 * HTML table component.
 *
 * @constructor
 * @param headerNames a list of table headers names
 * @param types a set of table types
 * @param caption table caption
 * @param responsive determines if the table is responsive
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Table(
    headerNames: List<String>? = null,
    types: Set<TableType> = setOf(), caption: String? = null, responsive: Boolean = false,
    classes: Set<String> = setOf(), init: (Table.() -> Unit)? = null
) : SimplePanel(classes + "table") {

    /**
     * Table headers names.
     */
    var headerNames by refreshOnUpdate(headerNames) { refreshHeaders() }
    /**
     * Table types.
     */
    var types by refreshOnUpdate(types)
    /**
     * Table caption.
     */
    var caption by refreshOnUpdate(caption)
    /**
     * Determines if the table is responsive.
     */
    var responsive by refreshOnUpdate(responsive)

    private val theadRow = Tag(TAG.TR)
    private val thead = Tag(TAG.THEAD).add(theadRow)
    private val tbody = Tag(TAG.TBODY)

    init {
        refreshHeaders()
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    private fun refreshHeaders() {
        theadRow.removeAll()
        headerNames?.forEach {
            theadRow.add(HeaderCell(it))
        }
    }

    /**
     * Adds new header cell to the table.
     * @param cell header cell
     * @return this table
     */
    fun addHeaderCell(cell: HeaderCell): Table {
        theadRow.add(cell)
        return this
    }

    /**
     * Removes given header cell from the table.
     * @param cell header cell
     * @return this table
     */
    fun removeHeaderCell(cell: HeaderCell): Table {
        theadRow.remove(cell)
        return this
    }

    /**
     * Removes all header cells from table.
     * @return this table
     */
    fun removeHeaderCells(): Table {
        theadRow.removeAll()
        return this
    }

    override fun render(): VNode {
        return if (responsive) {
            val opt = snOpt {
                `class` = snClasses(listOf("table-responsive" to true))
            }
            h("div", opt, arrayOf(render("table", childrenVNodes())))
        } else {
            render("table", childrenVNodes())
        }
    }

    override fun childrenVNodes(): Array<VNode> {
        val captionElement = caption?.let {
            Tag(TAG.CAPTION, it)
        }
        return listOf(captionElement, thead, tbody).mapNotNull { it?.renderVNode() }.toTypedArray()
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        types.forEach {
            cl.add(it.type to true)
        }
        return cl
    }

    override fun add(child: Component): SimplePanel {
        tbody.add(child)
        return this
    }

    override fun addAll(children: List<Component>): SimplePanel {
        tbody.addAll(children)
        return this
    }

    override fun remove(child: Component): SimplePanel {
        tbody.remove(child)
        return this
    }

    override fun removeAll(): SimplePanel {
        tbody.removeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return tbody.getChildren()
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.table(
            headerNames: List<String>? = null,
            types: Set<TableType> = setOf(), caption: String? = null, responsive: Boolean = false,
            classes: Set<String> = setOf(), init: (Table.() -> Unit)? = null
        ): Table {
            val table =
                Table(headerNames, types, caption, responsive, classes, init)
            this.add(table)
            return table
        }
    }
}
