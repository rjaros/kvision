package pl.treksoft.kvision.panel

import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.WidgetWrapper
import pl.treksoft.kvision.snabbdom.StringPair

enum class FLEXDIR(val dir: String) {
    ROW("row"),
    ROWREV("row-reverse"),
    COLUMN("column"),
    COLUMNREV("column-reverse")
}

enum class FLEXWRAP(val wrap: String) {
    NOWRAP("nowrap"),
    WRAP("wrap"),
    WRAPREV("wrap-reverse")
}

enum class FLEXJUSTIFY(val justify: String) {
    FLEXSTART("flex-start"),
    FLEXEND("flex-end"),
    CENTER("center"),
    SPACEBETWEEN("space-between"),
    SPACEAROUND("space-around"),
    SPACEEVENLY("space-evenly")
}

enum class FLEXALIGNITEMS(val alignItems: String) {
    FLEXSTART("flex-start"),
    FLEXEND("flex-end"),
    CENTER("center"),
    BASELINE("baseline"),
    STRETCH("stretch")
}

enum class FLEXALIGNCONTENT(val alignContent: String) {
    FLEXSTART("flex-start"),
    FLEXEND("flex-end"),
    CENTER("center"),
    SPACEBETWEEN("space-between"),
    SPACEAROUND("space-around"),
    STRETCH("stretch")
}

open class FlexPanel(
    direction: FLEXDIR? = null, wrap: FLEXWRAP? = null, justify: FLEXJUSTIFY? = null,
    alignItems: FLEXALIGNITEMS? = null, alignContent: FLEXALIGNCONTENT? = null,
    classes: Set<String> = setOf()
) : SimplePanel(classes) {
    var direction = direction
        set(value) {
            field = value
            refresh()
        }
    var wrap = wrap
        set(value) {
            field = value
            refresh()
        }
    var justify = justify
        set(value) {
            field = value
            refresh()
        }
    var alignItems = alignItems
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
        child: Component, order: Int? = null, grow: Int? = null, shrink: Int? = null,
        basis: Int? = null, alignSelf: FLEXALIGNITEMS? = null, classes: Set<String> = setOf()
    ): FlexPanel {
        addInternal(FlexWrapper(child, order, grow, shrink, basis, alignSelf, classes))
        return this
    }

    override fun add(child: Component): FlexPanel {
        return add(child, null)
    }

    override fun addAll(children: List<Component>): FlexPanel {
        children.forEach { add(it, null) }
        return this
    }

    override fun remove(child: Component): FlexPanel {
        children.find { (it as FlexWrapper).delegate == child }?.let {
            super.remove(it)
            it.dispose()
        }
        return this
    }

    override fun removeAll(): FlexPanel {
        children.map {
            it.clearParent()
            it.dispose()
        }
        children.clear()
        refresh()
        return this
    }

    override fun getSnStyle(): List<StringPair> {
        val snstyle = super.getSnStyle().toMutableList()
        snstyle.add("display" to "flex")
        direction?.let {
            snstyle.add("flex-direction" to it.dir)
        }
        wrap?.let {
            snstyle.add("flex-wrap" to it.wrap)
        }
        justify?.let {
            snstyle.add("justify-content" to it.justify)
        }
        alignItems?.let {
            snstyle.add("align-items" to it.alignItems)
        }
        alignContent?.let {
            snstyle.add("align-content" to it.alignContent)
        }
        return snstyle
    }
}

class FlexWrapper(
    delegate: Component, private val order: Int? = null, private val grow: Int? = null,
    private val shrink: Int? = null, private val basis: Int? = null,
    private val alignSelf: FLEXALIGNITEMS? = null,
    classes: Set<String> = setOf()
) : WidgetWrapper(delegate, classes) {

    override fun getSnStyle(): List<StringPair> {
        val snstyle = super.getSnStyle().toMutableList()
        order?.let {
            snstyle.add("order" to "$it")
        }
        grow?.let {
            snstyle.add("flex-grow" to "$it")
        }
        shrink?.let {
            snstyle.add("flex-shrink" to "$it")
        }
        basis?.let {
            snstyle.add("flex-basis" to "$it%")
        }
        alignSelf?.let {
            snstyle.add("align-self" to it.alignItems)
        }
        return snstyle
    }

}
