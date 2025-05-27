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

import io.kvision.KVManager
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.snabbdom.VNode
import org.w3c.dom.HTMLElement

/**
 * Simple component for rendering text DOM node.
 *
 * @constructor
 * @param content node text
 * @param rich determines if [content] can contain HTML code
 * @param init an initializer extension function
 */
@TagMarker
open class TextNode(
    content: String, rich: Boolean = false,
    init: (TextNode.() -> Unit)? = null
) : Widget() {

    /**
     * Text content of the tag.
     */
    var content by refreshOnUpdate(content)

    /**
     * Determines if [content] can contain HTML code.
     */
    var rich by refreshOnUpdate(rich)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getElement(): HTMLElement? {
        return null
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun render(): VNode {
        val translatedContent = translate(content)
        return if (rich) {
            KVManager.virtualize("<span style=\"display: contents;\">$translatedContent</span>")
        } else {
            translatedContent.asDynamic()
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.textNode(
    content: String, rich: Boolean = false,
    init: (TextNode.() -> Unit)? = null
): TextNode {
    val textNode = TextNode(content, rich, init)
    this.add(textNode)
    return textNode
}
