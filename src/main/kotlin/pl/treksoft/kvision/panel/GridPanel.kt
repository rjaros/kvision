package pl.treksoft.kvision.panel

import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.WidgetWrapper
import pl.treksoft.kvision.core.StringPair

enum class GRIDJUSTIFY(val justify: String) {
    START("start"),
    END("end"),
    CENTER("center"),
    STRETCH("stretch")
}

enum class GRIDALIGN(val align: String) {
    START("start"),
    END("end"),
    CENTER("center"),
    STRETCH("stretch")
}

enum class GRIDJUSTIFYCONTENT(val justifyContent: String) {
    START("start"),
    END("end"),
    CENTER("center"),
    STRETCH("stretch"),
    SPACEAROUND("space-around"),
    SPACEBETWEEN("space-between"),
    SPACEEVENLY("space-evenly")
}

enum class GRIDALIGNCONTENT(val alignContent: String) {
    START("start"),
    END("end"),
    CENTER("center"),
    STRETCH("stretch"),
    SPACEAROUND("space-around"),
    SPACEBETWEEN("space-between"),
    SPACEEVENLY("space-evenly")
}

enum class GRIDFLOW(val flow: String) {
    ROW("row"),
    COLUMN("column"),
    ROWDENSE("row dense"),
    COLUMNDENSE("column dense")
}

open class GridPanel(
    autoColumns: String? = null, autoRows: String? = null, autoFlow: GRIDFLOW? = null,
    templateColumns: String? = null, templateRows: String? = null, templateAreas: List<String>? = null,
    columnGap: Int? = null, rowGap: Int? = null, justifyItems: GRIDJUSTIFY? = null,
    alignItems: GRIDALIGN? = null, justifyContent: GRIDJUSTIFYCONTENT? = null,
    alignContent: GRIDALIGNCONTENT? = null, classes: Set<String> = setOf()
) : SimplePanel(classes) {
    var autoColumns = autoColumns
        set(value) {
            field = value
            refresh()
        }
    var autoRows = autoRows
        set(value) {
            field = value
            refresh()
        }
    var autoFlow = autoFlow
        set(value) {
            field = value
            refresh()
        }
    var templateColumns = templateColumns
        set(value) {
            field = value
            refresh()
        }
    var templateRows = templateRows
        set(value) {
            field = value
            refresh()
        }
    var templateAreas = templateAreas
        set(value) {
            field = value
            refresh()
        }
    var columnGap = columnGap
        set(value) {
            field = value
            refresh()
        }
    var rowGap = rowGap
        set(value) {
            field = value
            refresh()
        }
    var justifyItems = justifyItems
        set(value) {
            field = value
            refresh()
        }
    var alignItems = alignItems
        set(value) {
            field = value
            refresh()
        }
    var justifyContent = justifyContent
        set(value) {
            field = value
            refresh()
        }
    var alignContent = alignContent
        set(value) {
            field = value
            refresh()
        }

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
        children.find { (it as GridWrapper).delegate == child }?.let {
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
