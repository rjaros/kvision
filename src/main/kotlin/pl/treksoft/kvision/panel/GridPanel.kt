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
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.WidgetWrapper

/**
 * CSS grid justification options.
 */
enum class GRIDJUSTIFY(internal val justify: String) {
    START("start"),
    END("end"),
    CENTER("center"),
    STRETCH("stretch")
}

/**
 * CSS grid alignment options.
 */
enum class GRIDALIGN(internal val align: String) {
    START("start"),
    END("end"),
    CENTER("center"),
    STRETCH("stretch")
}

/**
 * CSS grid content justification options.
 */
enum class GRIDJUSTIFYCONTENT(internal val justifyContent: String) {
    START("start"),
    END("end"),
    CENTER("center"),
    STRETCH("stretch"),
    SPACEAROUND("space-around"),
    SPACEBETWEEN("space-between"),
    SPACEEVENLY("space-evenly")
}

/**
 * CSS grid content alignment options.
 */
enum class GRIDALIGNCONTENT(internal val alignContent: String) {
    START("start"),
    END("end"),
    CENTER("center"),
    STRETCH("stretch"),
    SPACEAROUND("space-around"),
    SPACEBETWEEN("space-between"),
    SPACEEVENLY("space-evenly")
}

/**
 * CSS grid flow options.
 */
enum class GRIDFLOW(internal val flow: String) {
    ROW("row"),
    COLUMN("column"),
    ROWDENSE("row dense"),
    COLUMNDENSE("column dense")
}

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
 * @param classes a set of CSS class names
 */
open class GridPanel(
    autoColumns: String? = null, autoRows: String? = null, autoFlow: GRIDFLOW? = null,
    templateColumns: String? = null, templateRows: String? = null, templateAreas: List<String>? = null,
    columnGap: Int? = null, rowGap: Int? = null, justifyItems: GRIDJUSTIFY? = null,
    alignItems: GRIDALIGN? = null, justifyContent: GRIDJUSTIFYCONTENT? = null,
    alignContent: GRIDALIGNCONTENT? = null, classes: Set<String> = setOf()
) : SimplePanel(classes) {

    /**
     * CSS grid auto columns.
     */
    var autoColumns = autoColumns
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid auto rows.
     */
    var autoRows = autoRows
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid auto flow.
     */
    var autoFlow = autoFlow
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid columns template.
     */
    var templateColumns = templateColumns
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid rows template.
     */
    var templateRows = templateRows
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid areas template.
     */
    var templateAreas = templateAreas
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid column gap.
     */
    var columnGap = columnGap
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid row gap.
     */
    var rowGap = rowGap
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid items justification.
     */
    var justifyItems = justifyItems
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid items alignment.
     */
    var alignItems = alignItems
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid content justification.
     */
    var justifyContent = justifyContent
        set(value) {
            field = value
            refresh()
        }
    /**
     * CSS grid content alignment.
     */
    var alignContent = alignContent
        set(value) {
            field = value
            refresh()
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
        columnEnd: String? = null, rowEnd: String? = null, area: String? = null, justifySelf: GRIDJUSTIFY? = null,
        alignSelf: GRIDALIGN? = null, classes: Set<String> = setOf()
    ): GridPanel {
        addInternal(GridWrapper(child, columnStart, rowStart, columnEnd, rowEnd, area, justifySelf, alignSelf, classes))
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
        children.find { (it as GridWrapper).wrapped == child }?.let {
            super.remove(it)
            it.dispose()
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

    @Suppress("ComplexMethod")
    override fun getSnStyle(): List<StringPair> {
        val snstyle = super.getSnStyle().toMutableList()
        snstyle.add("display" to "grid")
        autoColumns?.let {
            snstyle.add("grid-auto-columns" to it)
        }
        autoRows?.let {
            snstyle.add("grid-auto-rows" to it)
        }
        autoFlow?.let {
            snstyle.add("grid-auto-flow" to it.flow)
        }
        templateColumns?.let {
            snstyle.add("grid-template-columns" to it)
        }
        templateRows?.let {
            snstyle.add("grid-template-rows" to it)
        }
        templateAreas?.let {
            snstyle.add("grid-template-areas" to it.joinToString("\n"))
        }
        columnGap?.let {
            snstyle.add("grid-column-gap" to "${it}px")
        }
        rowGap?.let {
            snstyle.add("grid-row-gap" to "${it}px")
        }
        justifyItems?.let {
            snstyle.add("justify-items" to it.justify)
        }
        alignItems?.let {
            snstyle.add("align-items" to it.align)
        }
        justifyContent?.let {
            snstyle.add("justify-content" to it.justifyContent)
        }
        alignContent?.let {
            snstyle.add("align-content" to it.alignContent)
        }
        return snstyle
    }
}

class GridWrapper(
    delegate: Component, private val columnStart: Int? = null, private val rowStart: Int? = null,
    private val columnEnd: String? = null, private val rowEnd: String? = null,
    private val area: String? = null, private val justifySelf: GRIDJUSTIFY? = null,
    private val alignSelf: GRIDALIGN? = null,
    classes: Set<String> = setOf()
) : WidgetWrapper(delegate, classes) {

    override fun getSnStyle(): List<StringPair> {
        val snstyle = super.getSnStyle().toMutableList()
        columnStart?.let {
            snstyle.add("grid-column-start" to "$it")
        }
        rowStart?.let {
            snstyle.add("grid-row-start" to "$it")
        }
        columnEnd?.let {
            snstyle.add("grid-column-end" to it)
        }
        rowEnd?.let {
            snstyle.add("grid-row-end" to it)
        }
        area?.let {
            snstyle.add("grid-area" to it)
        }
        justifySelf?.let {
            snstyle.add("justify-self" to it.justify)
        }
        alignSelf?.let {
            snstyle.add("align-self" to it.align)
        }
        return snstyle
    }

}
