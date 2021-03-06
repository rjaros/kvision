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
import io.kvision.core.ExperimentalNonDslContainer
import io.kvision.core.StyledComponent
import io.kvision.core.UNIT
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.jquery.JQuery
import io.kvision.jquery.JQueryEventObject
import io.kvision.utils.obj

/**
 * The container with draggable splitter.
 *
 * It is required to have exactly two children, for both sides of the splitter. Otherwise it will be
 * rendered as empty.
 *
 * This container is not annotated with a @WidgetMarker.
 * It should be used only as a base class for other components.
 *
 * @constructor
 * @param direction direction of the splitter
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@ExperimentalNonDslContainer
open class BasicSplitPanel(
    private val direction: Direction = Direction.VERTICAL,
    classes: Set<String> = setOf(), init: (BasicSplitPanel.() -> Unit)? = null
) : BasicPanel(classes + ("splitpanel-" + direction.dir)) {

    @Suppress("LeakingThis")
    internal val splitter = BasicSplitter(this, direction)

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

@ExperimentalNonDslContainer
internal class BasicSplitter(private val splitPanel: BasicSplitPanel, direction: Direction) : Tag(
    TAG.DIV,
    classes = setOf("splitter-" + direction.dir)
) {
    private val idc = "kv_basicsplitter_$counter"

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
