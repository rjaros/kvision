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
package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import com.github.snabbdom.h
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.panel.SimplePanel

/**
 * HTML list types.
 */
enum class LISTTYPE(internal val tagName: String) {
    UL("ul"),
    OL("ol"),
    UNSTYLED("ul"),
    INLINE("ul"),
    DL("dl"),
    DL_HORIZ("dl")
}

/**
 * HTML list component.
 *
 * The list component can be populated directly from *elements* parameter or manually by adding
 * any [Component] to the container.
 *
 * @constructor
 * @param type list type
 * @param elements optional list of elements
 * @param rich determines if [elements] can contain HTML code
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class ListTag(
    type: LISTTYPE, elements: List<String>? = null, rich: Boolean = false,
    classes: Set<String> = setOf(), init: (ListTag.() -> Unit)? = null
) : SimplePanel(classes) {
    /**
     * List type.
     */
    var type = type
        set(value) {
            field = value
            refresh()
        }
    /**
     * List of elements.
     */
    var elements = elements
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if [elements] can contain HTML code.
     */
    var rich = rich
        set(value) {
            field = value
            refresh()
        }

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        val childrenElements = when (type) {
            LISTTYPE.UL, LISTTYPE.OL, LISTTYPE.UNSTYLED, LISTTYPE.INLINE -> elements?.map { el ->
                element("li", el, rich)
            }
            LISTTYPE.DL, LISTTYPE.DL_HORIZ -> elements?.mapIndexed { index, el ->
                element(if (index % 2 == 0) "dt" else "dd", el, rich)
            }
        }?.toTypedArray()
        return if (childrenElements != null) {
            render(type.tagName, childrenElements + childrenVNodes())
        } else {
            render(type.tagName, childrenVNodes())
        }
    }

    override fun childrenVNodes(): Array<VNode> {
        val childrenElements = children.filter { it.visible }
        val res = when (type) {
            LISTTYPE.UL, LISTTYPE.OL, LISTTYPE.UNSTYLED, LISTTYPE.INLINE -> childrenElements.map { v ->
                if (v is Tag && v.type == TAG.LI) {
                    v.renderVNode()
                } else {
                    h("li", arrayOf(v.renderVNode()))
                }
            }
            LISTTYPE.DL, LISTTYPE.DL_HORIZ -> childrenElements.mapIndexed { index, v ->
                if (v is Tag && v.type == TAG.LI) {
                    v.renderVNode()
                } else {
                    h(if (index % 2 == 0) "dt" else "dd", arrayOf(v.renderVNode()))
                }
            }
        }
        return res.toTypedArray()
    }

    private fun element(name: String, value: String, rich: Boolean): VNode {
        return if (rich) {
            h(name, arrayOf(KVManager.virtualize("<span>$value</span>")))
        } else {
            h(name, value)
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (type) {
            LISTTYPE.UNSTYLED -> cl.add("list-unstyled" to true)
            LISTTYPE.INLINE -> cl.add("list-inline" to true)
            LISTTYPE.DL_HORIZ -> cl.add("dl-horizontal" to true)
        }
        return cl
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.listTag(
            type: LISTTYPE, elements: List<String>? = null, rich: Boolean = false,
            classes: Set<String> = setOf(), init: (ListTag.() -> Unit)? = null
        ): ListTag {
            val listTag = ListTag(type, elements, rich, classes, init)
            this.add(listTag)
            return listTag
        }
    }
}
