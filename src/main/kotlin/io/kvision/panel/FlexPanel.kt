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

import io.kvision.KVManager
import io.kvision.core.*
import io.kvision.utils.px

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
 * @param useWrappers use additional div wrappers for child items
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class FlexPanel(
    direction: FlexDirection? = null,
    wrap: FlexWrap? = null,
    justify: JustifyContent? = null,
    alignItems: AlignItems? = null,
    alignContent: AlignContent? = null,
    spacing: Int? = null,
    private val useWrappers: Boolean = KVManager.panelsCompatibilityMode,
    className: String? = null,
    init: (FlexPanel.() -> Unit)? = null
) : SimplePanel(className) {

    /**
     * The spacing between columns/rows.
     */
    var spacing by refreshOnUpdate(spacing) { refreshSpacing(); refresh() }

    init {
        this.display = Display.FLEX
        this.flexDirection = direction
        this.flexWrap = wrap
        this.justifyContent = justify
        this.alignItems = alignItems
        this.alignContent = alignContent
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
     * @param className CSS class names
     */
    @Suppress("LongParameterList")
    fun add(
        child: Component, order: Int? = null, grow: Int? = null, shrink: Int? = null,
        basis: CssSize? = null, alignSelf: AlignItems? = null, className: String? = null
    ): FlexPanel {
        val wrapper = if (!useWrappers) {
            child
        } else {
            WidgetWrapper(child, className)
        }
        if (spacing != null) applySpacing(wrapper.unsafeCast<Widget>())
        (wrapper as? Widget)?.let {
            it.order = order
            it.flexGrow = grow
            it.flexShrink = shrink
            it.flexBasis = basis
            it.alignSelf = alignSelf
        }
        addInternal(wrapper)
        return this
    }

    /**
     * Adds a component to the flexbox container at the given position.
     * @param position the position of the child component
     * @param child child component
     * @param order child flexbox ordering
     * @param grow child flexbox grow
     * @param shrink child flexbox shrink
     * @param basis child flexbox basis
     * @param alignSelf child self alignment
     * @param className CSS class names
     */
    @Suppress("LongParameterList")
    fun add(
        position: Int, child: Component, order: Int? = null, grow: Int? = null, shrink: Int? = null,
        basis: CssSize? = null, alignSelf: AlignItems? = null, className: String? = null
    ): FlexPanel {
        val wrapper = if (!useWrappers) {
            child
        } else {
            WidgetWrapper(child, className)
        }
        if (spacing != null) applySpacing(wrapper.unsafeCast<Widget>())
        (wrapper as? Widget)?.let {
            it.order = order
            it.flexGrow = grow
            it.flexShrink = shrink
            it.flexBasis = basis
            it.alignSelf = alignSelf
        }
        addInternal(position, wrapper)
        return this
    }

    /**
     * DSL function to add components with additional options.
     * @param builder DSL builder function
     */
    open fun options(
        order: Int? = null, grow: Int? = null, shrink: Int? = null,
        basis: CssSize? = null, alignSelf: AlignItems? = null, className: String? = null,
        builder: Container.() -> Unit
    ) {
        object : Container by this@FlexPanel {
            override fun add(child: Component): Container {
                return add(child, order, grow, shrink, basis, alignSelf, className)
            }
        }.builder()
    }

    private fun refreshSpacing() {
        getChildren().forEach { applySpacing(it.unsafeCast<Widget>()) }
    }

    private fun applySpacing(wrapper: Widget): Widget {
        if (useWrappers) {
            wrapper.marginTop = null
            wrapper.marginRight = null
            wrapper.marginBottom = null
            wrapper.marginLeft = null
        }
        spacing?.let {
            when (flexDirection) {
                FlexDirection.COLUMN -> wrapper.marginBottom = it.px
                FlexDirection.ROWREV -> {
                    if (justifyContent == JustifyContent.FLEXEND) wrapper.marginRight = it.px else wrapper.marginLeft =
                        it.px
                }
                FlexDirection.COLUMNREV -> wrapper.marginTop = it.px
                else -> {
                    if (justifyContent == JustifyContent.FLEXEND) wrapper.marginLeft = it.px else wrapper.marginRight =
                        it.px
                }
            }
        }
        return wrapper
    }

    override fun add(child: Component): FlexPanel {
        return add(child, null)
    }

    override fun add(position: Int, child: Component): FlexPanel {
        return add(position, child, null)
    }

    override fun addAll(children: List<Component>): FlexPanel {
        children.forEach { add(it, null) }
        return this
    }

    override fun remove(child: Component): FlexPanel {
        if (children != null) {
            if (children!!.contains(child)) {
                super.remove(child)
            } else {
                children!!.find { (it as? WidgetWrapper)?.wrapped == child }?.let {
                    super.remove(it)
                    it.dispose()
                }
            }
        }
        return this
    }

    override fun removeAll(): FlexPanel {
        children?.map {
            it.clearParent()
            (it as? WidgetWrapper)?.dispose()
        }
        children?.clear()
        children = null
        refresh()
        return this
    }

    override fun disposeAll(): FlexPanel {
        children?.map {
            (it as? WidgetWrapper)?.let {
                it.wrapped?.dispose()
            }
        }
        return removeAll()
    }

    override fun dispose() {
        children?.map {
            (it as? WidgetWrapper)?.let {
                it.wrapped?.dispose()
            }
        }
        super.dispose()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.flexPanel(
    direction: FlexDirection? = null, wrap: FlexWrap? = null, justify: JustifyContent? = null,
    alignItems: AlignItems? = null, alignContent: AlignContent? = null,
    spacing: Int? = null,
    useWrappers: Boolean = KVManager.panelsCompatibilityMode,
    className: String? = null,
    init: (FlexPanel.() -> Unit)? = null
): FlexPanel {
    val flexPanel =
        FlexPanel(
            direction,
            wrap,
            justify,
            alignItems,
            alignContent,
            spacing,
            useWrappers,
            className,
            init
        )
    this.add(flexPanel)
    return flexPanel
}
