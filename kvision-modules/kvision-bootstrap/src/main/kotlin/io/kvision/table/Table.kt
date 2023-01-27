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
package io.kvision.table

import io.kvision.snabbdom.VNode
import io.kvision.snabbdom.h
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.panel.SimplePanel
import io.kvision.utils.snClasses
import io.kvision.utils.snOpt

/**
 * HTML table color variants.
 */
enum class TableColor(override val className: String) : CssClass {
    PRIMARY("table-primary"),
    SECONDARY("table-secondary"),
    SUCCESS("table-success"),
    DANGER("table-danger"),
    WARNING("table-warning"),
    INFO("table-info"),
    LIGHT("table-light"),
    DARK("table-dark")
}

/**
 * HTML table types.
 */
enum class TableType(val type: String) {
    STRIPED("table-striped"),
    BORDERED("table-bordered"),
    BORDERLESS("table-borderless"),
    HOVER("table-hover"),
    SMALL("table-sm"),
    STRIPEDCOLUMNS("table-striped-columns"),
}

/**
 * HTML table responsive types.
 */
enum class ResponsiveType(val type: String) {
    RESPONSIVE("table-responsive"),
    RESPONSIVESM("table-responsive-sm"),
    RESPONSIVEMD("table-responsive-md"),
    RESPONSIVELG("table-responsive-lg"),
    RESPONSIVEXL("table-responsive-xl"),
    RESPONSIVEXXL("table-responsive-xxl")
}

/**
 * HTML table component.
 *
 * @constructor
 * @param headerNames a list of table headers names
 * @param types a set of table types
 * @param caption table caption
 * @param responsiveType determines if the table is responsive
 * @param tableColor table color variant
 * @param theadColor table header color variant
 * @param tbodyDivider add table body group divider
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Table(
    headerNames: List<String>? = null,
    types: Set<TableType> = setOf(), caption: String? = null, responsiveType: ResponsiveType? = null,
    tableColor: TableColor? = null, val theadColor: TableColor? = null, tbodyDivider: Boolean = false,
    className: String? = null, init: (Table.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "table") {

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
    var responsiveType by refreshOnUpdate(responsiveType)

    /**
     * Table color variant.
     */
    var tableColor by refreshOnUpdate(tableColor)

    /**
     * Table body group divider.
     */
    var tbodyDivider
        get() = tbody.hasCssClass("table-group-divider")
        set(value) {
            if (value) {
                tbody.addCssClass("table-group-divider")
            } else {
                tbody.removeCssClass("table-group-divider")
            }
        }

    internal val theadRow = Tag(TAG.TR)
    private val thead = Tag(TAG.THEAD).apply {
        if (theadColor != null) addCssClass(theadColor.className)
        add(this@Table.theadRow)
    }
    private val tbody = Tag(TAG.TBODY).apply {
        if (tbodyDivider) addCssClass("table-group-divider")
    }

    init {
        @Suppress("LeakingThis")
        thead.parent = this
        @Suppress("LeakingThis")
        tbody.parent = this
        refreshHeaders()
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    private fun refreshHeaders() {
        theadRow.disposeAll()
        headerNames?.forEach {
            theadRow.add(HeaderCell(it, scope = Scope.COL))
        }
    }

    /**
     * Adds new header cell to the table.
     * @param cell header cell
     */
    fun addHeaderCell(cell: HeaderCell) {
        theadRow.add(cell)
    }

    /**
     * Removes given header cell from the table.
     * @param cell header cell
     */
    fun removeHeaderCell(cell: HeaderCell) {
        theadRow.remove(cell)
    }

    /**
     * Removes all header cells from table.
     */
    fun removeHeaderCells() {
        theadRow.disposeAll()
    }

    override fun render(): VNode {
        return if (responsiveType != null) {
            val opt = snOpt {
                `class` = snClasses(listOf(responsiveType!!.type to true))
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

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        types.forEach {
            classSetBuilder.add(it.type)
        }
        classSetBuilder.add(tableColor)
    }

    override fun add(child: Component) {
        tbody.add(child)
    }

    override fun add(position: Int, child: Component) {
        tbody.add(position, child)
    }

    override fun addAll(children: List<Component>) {
        tbody.addAll(children)
    }

    override fun remove(child: Component) {
        tbody.remove(child)
    }

    override fun removeAt(position: Int) {
        tbody.removeAt(position)
    }

    override fun removeAll() {
        tbody.removeAll()
    }

    override fun disposeAll() {
        tbody.disposeAll()
    }

    override fun getChildren(): List<Component> {
        return tbody.getChildren()
    }

    override fun dispose() {
        super.dispose()
        thead.dispose()
        tbody.dispose()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.table(
    headerNames: List<String>? = null,
    types: Set<TableType> = setOf(), caption: String? = null, responsiveType: ResponsiveType? = null,
    tableColor: TableColor? = null, theadColor: TableColor? = null, tbodyDivider: Boolean = false,
    className: String? = null,
    init: (Table.() -> Unit)? = null
): Table {
    val table =
        Table(headerNames, types, caption, responsiveType, tableColor, theadColor, tbodyDivider, className, init)
    this.add(table)
    return table
}
