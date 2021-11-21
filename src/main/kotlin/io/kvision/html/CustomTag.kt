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
import io.kvision.core.Container

/**
 * HTML custom tag component.
 *
 * @constructor
 * @param elementName element name
 * @param content element text
 * @param rich determines if [content] can contain HTML code
 * @param align content align
 * @param className CSS class names
 * @param attributes a map of additional attributes
 * @param init an initializer extension function
 */
open class CustomTag(
    elementName: String,
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    className: String? = null, attributes: Map<String, String>? = null,
    init: (CustomTag.() -> Unit)? = null
) :
    Tag(TAG.DIV, content, rich, align, className, attributes) {

    /**
     * HTML element name.
     */
    var elementName by refreshOnUpdate(elementName)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(elementName: String, children: Array<dynamic>): VNode {
        return super.render(this.elementName, children)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.customTag(
    elementName: String,
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    className: String? = null,
    attributes: Map<String, String>? = null,
    init: (CustomTag.() -> Unit)? = null
): CustomTag {
    val customTag =
        CustomTag(elementName, content, rich, align, className, attributes, init)
    this.add(customTag)
    return customTag
}
