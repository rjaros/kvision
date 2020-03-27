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
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.core.WidgetWrapper
import pl.treksoft.kvision.utils.px

/**
 * CSS flexbox directions.
 */
enum class FlexDir(internal val dir: String) {
    ROW("row"),
    ROWREV("row-reverse"),
    COLUMN("column"),
    COLUMNREV("column-reverse")
}

/**
 * CSS flexbox wrap modes.
 */
enum class FlexWrap(internal val wrap: String) {
    NOWRAP("nowrap"),
    WRAP("wrap"),
    WRAPREV("wrap-reverse")
}

/**
 * CSS flexbox justification options.
 */
enum class FlexJustify(internal val justify: String) {
    FLEXSTART("flex-start"),
    FLEXEND("flex-end"),
    CENTER("center"),
    SPACEBETWEEN("space-between"),
    SPACEAROUND("space-around"),
    SPACEEVENLY("space-evenly")
}

/**
 * CSS flexbox alignments options.
 */
enum class FlexAlignItems(internal val alignItems: String) {
    FLEXSTART("flex-start"),
    FLEXEND("flex-end"),
    CENTER("center"),
    BASELINE("baseline"),
    STRETCH("stretch")
}

/**
 * CSS flexbox content alignment options.
 */
enum class FlexAlignContent(internal val alignContent: String) {
    FLEXSTART("flex-start"),
    FLEXEND("flex-end"),
    CENTER("center"),
    SPACEBETWEEN("space-between"),
    SPACEAROUND("space-around"),
    STRETCH("stretch")
}

/**
 * The container with CSS flexbox layout support.
 *
 * @constructor
 * @param direction flexbox direction
 * @param wrap flexbox wrap
 * @param justify flexbox content justification
 * @param alignItems flexbox items alignment
 * @param alignContent flexbox content alignment
 * @param spacing spacing between columns/rows
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class FlexPanel(
    direction: FlexDir? = null, wrap: FlexWrap? = null, justify: FlexJustify? = null,
    alignItems: FlexAlignItems? = null, alignContent: FlexAlignContent? = null,
    spacing: Int? = null, classes: Set<String> = setOf(), init: (FlexPanel.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * CSS flexbox direction.
     */
    var direction by refreshOnUpdate(direction) { refreshSpacing(); refresh() }

    /**
     * CSS flexbox wrap mode.
     */
    var wrap by refreshOnUpdate(wrap)

    /**
     * CSS flexbox content justification.
     */
    var justify by refreshOnUpdate(justify)

    /**
     * CSS flexbox items alignment.
     */
    var alignItems by refreshOnUpdate(alignItems)

    /**
     * CSS flexbox content alignment.
     */
    var alignContent by refreshOnUpdate(alignContent)

    /**
     * The spacing between columns/rows.
     */
    var spacing by refreshOnUpdate(spacing) { refreshSpacing(); refresh() }

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * Adds a component to the flexbox container.
     * @param child child component
     * @param order child flexbox ordering
     * @param grow child flexbox grow
     * @param shrink child flexbox shrink
     * @param basis child flexbox basis
     * @param alignSelf child self alignment
     * @param classes a set of CSS class names
     */
    @Suppress("LongParameterList")
    fun add(
        child: Component, order: Int? = null, grow: Int? = null, shrink: Int? = null,
        basis: Int? = null, alignSelf: FlexAlignItems? = null, classes: Set<String> = setOf()
    ): FlexPanel {
        val wrapper = FlexWrapper(child, order, grow, shrink, basis, alignSelf, classes)
        addInternal(applySpacing(wrapper))
        return this
    }

    private fun refreshSpacing() {
        getChildren().filterIsInstance<Widget>().map { applySpacing(it) }
    }

    private fun applySpacing(wrapper: Widget): Widget {
        wrapper.marginTop = null
        wrapper.marginRight = null
        wrapper.marginBottom = null
        wrapper.marginLeft = null
        spacing?.let {
            when (direction) {
                FlexDir.COLUMN -> wrapper.marginBottom = it.px
                FlexDir.ROWREV -> {
                    if (justify == FlexJustify.FLEXEND) wrapper.marginRight = it.px else wrapper.marginLeft = it.px
                }
                FlexDir.COLUMNREV -> wrapper.marginTop = it.px
                else -> {
                    if (justify == FlexJustify.FLEXEND) wrapper.marginLeft = it.px else wrapper.marginRight = it.px
                }
            }
        }
        return wrapper
    }

    override fun add(child: Component): FlexPanel {
        return add(child, null)
    }

    override fun addAll(children: List<Component>): FlexPanel {
        children.forEach { add(it, null) }
        return this
    }

    override fun remove(child: Component): FlexPanel {
        children.find { (it as FlexWrapper).wrapped == child }?.let {
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

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.flexPanel(
    direction: FlexDir? = null, wrap: FlexWrap? = null, justify: FlexJustify? = null,
    alignItems: FlexAlignItems? = null, alignContent: FlexAlignContent? = null,
    spacing: Int? = null, classes: Set<String> = setOf(), init: (FlexPanel.() -> Unit)? = null
): FlexPanel {
    val flexPanel = FlexPanel(direction, wrap, justify, alignItems, alignContent, spacing, classes, init)
    this.add(flexPanel)
    return flexPanel
}

/**
 * Helper class form CSS flexbox layout.
 */
internal class FlexWrapper(
    delegate: Component, private val order: Int? = null, private val grow: Int? = null,
    private val shrink: Int? = null, private val basis: Int? = null,
    private val alignSelf: FlexAlignItems? = null,
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
