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
import io.kvision.core.Container
import io.kvision.core.StyledComponent
import io.kvision.core.UNIT
import io.kvision.core.Widget
import io.kvision.snabbdom.VNode
import io.kvision.utils.obj
import kotlin.math.ceil

/**
 * Split panel direction.
 */
enum class Direction(internal val dir: String) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical")
}

/**
 * Split panel gutter alignment.
 */
enum class GutterAlign(internal val align: String) {
    CENTER("center"),
    START("start"),
    END("end"),
}

/**
 * The container with draggable splitter.
 *
 * It is required to have exactly two children, for both sides of the splitter. Otherwise it will be
 * rendered as empty.
 *
 * @constructor
 * @param direction direction of the splitter
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class SplitPanel(
    private val direction: Direction = Direction.VERTICAL,
    className: String? = null, init: (SplitPanel.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + ("splitpanel-" + direction.dir)) {

    /**
     * The gutter size.
     */
    var gutterSize by refreshOnUpdate(9)

    /**
     * The gutter align.
     */
    var gutterAlign: GutterAlign? by refreshOnUpdate()

    /**
     * The minimum size.
     */
    var minSize by refreshOnUpdate(0)

    /**
     * The maximum size.
     */
    var maxSize: Int? by refreshOnUpdate()

    /**
     * Expand to minimum size.
     */
    var expandToMin: Boolean? by refreshOnUpdate()

    /**
     * The snap offset.
     */
    var snapOffset by refreshOnUpdate(0)

    /**
     * The drag interval.
     */
    var dragInterval: Int? by refreshOnUpdate()

    @Suppress("LeakingThis")
    internal val splitter = Splitter(this, direction)

    protected var splitJs: dynamic = null

    init {
        useSnabbdomDistinctKey()
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun afterInsertSplitter() {
        if (children?.size == 2) {
            children!!.forEach { (it as Widget).useSnabbdomDistinctKey() }
            val horizontal = direction == Direction.HORIZONTAL
            val perc = UNIT.perc
            val self = this
            val splitJsDirection = if (direction == Direction.HORIZONTAL) "vertical" else "horizontal"
            val sizes = if (horizontal) {
                val h1 = (children!![0] as? StyledComponent)?.height
                if (h1 != null && h1.second == UNIT.perc) {
                    arrayOf(h1.first, (100 - h1.first.toDouble()))
                } else {
                    val height = getElementD()?.getBoundingClientRect().height ?: 0
                    val firstHeight = getElement()?.firstChild?.asDynamic().getBoundingClientRect().height ?: 0
                    if (height != 0 && firstHeight != 0) {
                        val firstPerc = ceil(firstHeight.unsafeCast<Double>() * 100 / height.unsafeCast<Double>())
                        arrayOf(firstPerc, 100 - firstPerc)
                    } else {
                        arrayOf(0, 100)
                    }
                }
            } else {
                val h1 = (children!![0] as? StyledComponent)?.width
                if (h1 != null && h1.second == UNIT.perc) {
                    arrayOf(h1.first, (100 - h1.first.toDouble()))
                } else {
                    val width = getElementD()?.getBoundingClientRect().width ?: 0
                    val firstWidth = getElement()?.firstChild?.asDynamic().getBoundingClientRect().width ?: 0
                    if (width != 0 && firstWidth != 0) {
                        val firstPerc = ceil(firstWidth.unsafeCast<Double>() * 100 / width.unsafeCast<Double>())
                        arrayOf(firstPerc, 100 - firstPerc)
                    } else {
                        arrayOf(0, 100)
                    }
                }
            }
            splitJs = KVManager.splitjs(arrayOf(getElement()?.firstChild, getElement()?.lastChild), obj {
                this.sizes = sizes
                this.direction = splitJsDirection
                this.gutter = {
                    splitter.getElement()
                }
                this.gutterSize = gutterSize
                if (gutterAlign != null) this.gutterAlign = gutterAlign?.align
                this.minSize = minSize
                if (maxSize != null) this.maxSize = maxSize
                if (expandToMin != null) this.expandToMin = expandToMin
                this.snapOffset = snapOffset
                if (dragInterval != null) this.dragInterval = dragInterval
                this.onDrag = { sizes: Array<Int> ->
                    val e = obj {
                        this.sizes = sizes
                    }
                    self.dispatchEvent("dragSplitPanel", obj { detail = e })
                }
                this.onDragStart = { sizes: Array<Int> ->
                    val e = obj {
                        this.sizes = sizes
                    }
                    self.dispatchEvent("dragStartSplitPanel", obj { detail = e })
                }
                this.onDragEnd = { sizes: Array<Int> ->
                    val e = obj {
                        this.sizes = sizes
                    }
                    if (horizontal) {
                        (children!![0] as? StyledComponent)?.height = sizes[0] to perc
                        (children!![1] as? StyledComponent)?.height = sizes[1] to perc
                    } else {
                        (children!![0] as? StyledComponent)?.width = sizes[0] to perc
                        (children!![1] as? StyledComponent)?.width = sizes[1] to perc
                    }
                    self.dispatchEvent("dragEndSplitPanel", obj { detail = e })
                }
            })
        }
    }

    /**
     * Get split panel sizes.
     */
    open fun getSizes(): Array<Int>? {
        @Suppress("UnsafeCastFromDynamic")
        return splitJs?.getSizes()
    }

    /**
     * Set split panel sizes.
     */
    open fun setSizes(sizes: Array<Int>) {
        splitJs?.setSizes(sizes)
    }

    override fun afterDestroy() {
        if (splitJs != null) {
            splitJs.destroy(false, true)
            splitJs = null
        }
    }

    override fun childrenVNodes(): Array<VNode> {
        return if (children?.size == 2) {
            arrayOf(children!![0].renderVNode(), splitter.renderVNode(), children!![1].renderVNode())
        } else {
            arrayOf()
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.splitPanel(
    direction: Direction = Direction.VERTICAL,
    className: String? = null,
    init: (SplitPanel.() -> Unit)? = null
): SplitPanel {
    val splitPanel = SplitPanel(direction, className, init)
    this.add(splitPanel)
    return splitPanel
}

internal class Splitter(private val splitPanel: SplitPanel, direction: Direction) : SimplePanel(
    "splitter-" + direction.dir
) {
    init {
        useSnabbdomDistinctKey()
    }

    override fun afterInsert(node: VNode) {
        splitPanel.afterInsertSplitter()
    }
}
