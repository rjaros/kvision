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
package io.kvision.form

import io.kvision.snabbdom.VNode
import io.kvision.KVManager

/**
 * Helper class for HTML label element for checkbox/radiobutton.
 *
 * @constructor
 * @param forId the value of *for* attribute
 * @param content the text of the label
 * @param rich determines if [content] can contain HTML code
 * @param className CSS class names
 * @param init an initializer extension function
 */
internal class FieldLabelCheck(
    forId: String, content: String? = null, rich: Boolean = false,
    className: String? = null,
    init: (FieldLabelCheck.() -> Unit)? = null
) : FieldLabel(forId, content, rich, className) {

    init {
        init?.invoke(this)
    }

    override fun render(): VNode {
        return if (content != null) {
            val translatedContent = content?.let { translate(it) }
            if (rich) {
                render(
                    type.tagName,
                    arrayOf(KVManager.virtualize("<span style=\"display: contents;\">$translatedContent</span>")) + childrenVNodes()
                )
            } else {
                render(type.tagName, arrayOf(translatedContent) + childrenVNodes())
            }
        } else {
            render(type.tagName, childrenVNodes())
        }
    }
}
