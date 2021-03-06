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

import com.github.snabbdom.VNode
import io.kvision.jquery.JQuery
import io.kvision.jquery.JQueryEventObject
import io.kvision.core.Container
import io.kvision.core.StyledComponent
import io.kvision.core.UNIT
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.obj
import io.kvision.utils.set

/**
 * Split panel direction.
 */
enum class Direction(internal val dir: String) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical")
}

/**
 * The container with draggable splitter.
 *
 * It is required to have exactly two children, for both sides of the splitter. Otherwise it will be
 * rendered as empty.
 *
 * @constructor
 * @param direction direction of the splitter
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class SplitPanel(
    private val direction: Direction = Direction.VERTICAL,
    classes: Set<String> = setOf(), init: (SplitPanel.() -> Unit)? = null
) : SimplePanel(classes + ("splitpanel-" + direction.dir)) {

    @Suppress("LeakingThis")
    internal val splitter = Splitter(this, direction)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun afterInsertSplitter() {
        if (children.size == 2) {
            val horizontal = direction == Direction.HORIZONTAL
            val px = UNIT.px
            val self = this
            children[0].getElementJQueryD()?.resizable(obj {
                handleSelector = "#" + splitter.id
                resizeWidth = !horizontal
                resizeHeight = horizontal
                onDrag = lok@{ e: JQueryEventObject, _: JQuery, newWidth: Int, newHeight: Int, _: dynamic ->
                    e.asDynamic()["newWidth"] = newWidth
                    e.asDynamic()["newHeight"] = newHeight
                    self.dispatchEvent("dragSplitPanel", obj { detail = e })
                    return@lok !e.isDefaultPrevented()
                }
                onDragEnd = { e: JQueryEventObject, el: JQuery, _: dynamic ->
                    if (horizontal) {
                        (children[0] as? StyledComponent)?.height = el.height().toInt() to px
                    } else {
                        (children[0] as? StyledComponent)?.width = el.width().toInt() to px
                    }
                    self.dispatchEvent("dragEndSplitPanel", obj { detail = e })
                }
            })
        }
    }

    override fun childrenVNodes(): Array<VNode> {
        return if (children.size == 2) {
            arrayOf(children[0].renderVNode(), splitter.renderVNode(), children[1].renderVNode())
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
    classes: Set<String>? = null,
    className: String? = null,
    init: (SplitPanel.() -> Unit)? = null
): SplitPanel {
    val splitPanel = SplitPanel(direction, classes ?: className.set, init)
    this.add(splitPanel)
    return splitPanel
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.splitPanel(
    state: ObservableState<S>,
    direction: Direction = Direction.VERTICAL,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SplitPanel.(S) -> Unit)
) = splitPanel(direction, classes, className).bind(state, true, init)

internal class Splitter(private val splitPanel: SplitPanel, direction: Direction) : Tag(
    TAG.DIV,
    classes = setOf("splitter-" + direction.dir)
) {
    private val idc = "kv_splitter_$counter"

    init {
        this.id = idc
        counter++
    }

    override fun afterInsert(node: VNode) {
        splitPanel.afterInsertSplitter()
    }

    companion object {
        internal var counter = 0
    }
}
