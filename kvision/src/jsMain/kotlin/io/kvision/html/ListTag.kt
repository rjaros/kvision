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
package io.kvision.html

import io.kvision.snabbdom.VNode
import io.kvision.snabbdom.h
import io.kvision.KVManager
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.panel.SimplePanel
import io.kvision.utils.snClasses
import io.kvision.utils.snOpt

/**
 * HTML list types.
 */
enum class ListType(internal val tagName: String) {
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
 * @param className CSS class names
 * @param init an initializer extension function
 */
@TagMarker
open class ListTag(
    type: ListType, elements: List<String>? = null, rich: Boolean = false,
    className: String? = null, init: (ListTag.() -> Unit)? = null
) : SimplePanel(className) {
    /**
     * List type.
     */
    var type by refreshOnUpdate(type)

    /**
     * List of elements.
     */
    var elements by refreshOnUpdate(elements)

    /**
     * Determines if [elements] can contain HTML code.
     */
    var rich by refreshOnUpdate(rich)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        val childrenElements = when (type) {
            ListType.UL, ListType.OL, ListType.UNSTYLED, ListType.INLINE -> elements?.map { el ->
                element("li", el, rich, type == ListType.INLINE)
            }
            ListType.DL, ListType.DL_HORIZ -> elements?.mapIndexed { index, el ->
                element(if (index % 2 == 0) "dt" else "dd", el, rich, false)
            }
        }?.toTypedArray()
        return if (childrenElements != null) {
            render(type.tagName, childrenElements + childrenVNodes())
        } else {
            render(type.tagName, childrenVNodes())
        }
    }

    @Suppress("ComplexCondition", "ComplexMethod")
    override fun childrenVNodes(): Array<VNode> {
        val childrenElements = getChildren().filter { it.visible }
        val res = when (type) {
            ListType.UL, ListType.OL, ListType.UNSTYLED, ListType.INLINE -> childrenElements.map { v ->
                if ((v is Tag && (v.type == TAG.LI || v.type == TAG.OL || v.type == TAG.UL)) || v is ListTag) {
                    v.renderVNode()
                } else {
                    if (type == ListType.INLINE) {
                        val opt = snOpt {
                            `class` = snClasses(listOf("list-inline-item" to true))
                        }
                        h("li", opt, arrayOf(v.renderVNode()))
                    } else {
                        h("li", arrayOf(v.renderVNode()))
                    }
                }
            }
            ListType.DL, ListType.DL_HORIZ -> childrenElements.mapIndexed { index, v ->
                if (v is Tag && ((v.type == TAG.LI || v.type == TAG.OL || v.type == TAG.UL) || v is ListTag)) {
                    v.renderVNode()
                } else {
                    h(if (index % 2 == 0) "dt" else "dd", arrayOf(v.renderVNode()))
                }
            }
        }
        return res.toTypedArray()
    }

    private fun element(name: String, value: String, rich: Boolean, inline: Boolean): VNode {
        val translatedValue = translate(value)
        val opt = if (inline) {
            snOpt {
                `class` = snClasses(listOf("list-inline-item" to true))
            }
        } else {
            snOpt {}
        }
        return if (rich) {
            h(name, opt, arrayOf(KVManager.virtualize("<span style=\"display: contents;\">$translatedValue</span>")))
        } else {
            h(name, opt, translatedValue)
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        when (type) {
            ListType.UNSTYLED -> classSetBuilder.add("list-unstyled")
            ListType.INLINE -> classSetBuilder.add("list-inline")
            ListType.DL_HORIZ -> classSetBuilder.add("dl-horizontal")
            else -> {}
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.listTag(
    type: ListType, elements: List<String>? = null, rich: Boolean = false,
    className: String? = null,
    init: (ListTag.() -> Unit)? = null
): ListTag {
    val listTag = ListTag(type, elements, rich, className, init)
    this.add(listTag)
    return listTag
}
