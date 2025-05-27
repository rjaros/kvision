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

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container

/**
 * Simple component rendered as *label*.
 *
 * @constructor
 * @param content the text of the label
 * @param rich determines if [content] can contain HTML code
 * @param forId the ID of the labeled element
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Label(
    content: String? = null, rich: Boolean = false, forId: String? = null,
    className: String? = null,
    init: (Label.() -> Unit)? = null
) : Tag(TAG.LABEL, content, rich, className = className) {

    /**
     * The ID of the labeled element.
     */
    var forId by refreshOnUpdate(forId)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        forId?.let {
            attributeSetBuilder.add("for", it)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.label(
    content: String? = null, rich: Boolean = false, forId: String? = null,
    className: String? = null,
    init: (Label.() -> Unit)? = null
): Label {
    val label = Label(content, rich, forId, className, init)
    this.add(label)
    return label
}
