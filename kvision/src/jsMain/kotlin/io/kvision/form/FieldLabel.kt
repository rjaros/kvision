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

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.html.TAG
import io.kvision.html.Tag

/**
 * Helper class for HTML label element.
 *
 * @constructor
 * @param forId the value of *for* attribute
 * @param content the text of the label
 * @param rich determines if [content] can contain HTML code
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class FieldLabel(
    internal val forId: String, content: String? = null, rich: Boolean = false,
    className: String? = null,
    init: (FieldLabel.() -> Unit)? = null
) : Tag(TAG.LABEL, content, rich, className = className) {

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("for", forId)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.fieldLabel(
    forId: String, content: String? = null, rich: Boolean = false,
    className: String? = null,
    init: (FieldLabel.() -> Unit)? = null
): FieldLabel {
    val fieldLabel = FieldLabel(forId, content, rich, className, init)
    this.add(fieldLabel)
    return fieldLabel
}
