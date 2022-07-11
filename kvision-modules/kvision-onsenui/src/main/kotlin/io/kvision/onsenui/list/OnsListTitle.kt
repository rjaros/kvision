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

package io.kvision.onsenui.list

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.html.Align
import io.kvision.html.CustomTag

/**
 * An Onsen UI list title component.
 *
 * @constructor Creates a list title component.
 * @param content the content of the component.
 * @param rich whether [content] can contain HTML code
 * @param align text align
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class OnsListTitle(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    className: String? = null,
    init: (OnsListTitle.() -> Unit)? = null
) : CustomTag("ons-list-title", content, rich, align, className) {

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsListTitle(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    className: String? = null,
    init: (OnsListTitle.() -> Unit)? = null
): OnsListTitle {
    val onsListTitle = OnsListTitle(content, rich, align, className, init)
    this.add(onsListTitle)
    return onsListTitle
}
