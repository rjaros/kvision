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

package io.kvision.onsenui.core

import io.kvision.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.html.Align
import io.kvision.html.CustomTag
import io.kvision.html.Div
import org.w3c.dom.events.MouseEvent

/**
 * A back button component designed to be placed inside the toolbar.
 *
 * @constructor Creates a back button component.
 * @param content the content of the button.
 * @param rich whether [content] can contain HTML code
 * @param align text align
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class BackButton(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    className: String? = null,
    init: (BackButton.() -> Unit)? = null
) : CustomTag("ons-back-button", content, rich, align, className) {

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        useSnabbdomDistinctKey()
        init?.invoke(this)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: BackButton.(MouseEvent) -> Unit): Int {
        return this.setEventListener<BackButton> {
            click = { e ->
                self.handler(e)
            }
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Div.backButton(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    className: String? = null,
    init: (BackButton.() -> Unit)? = null
): BackButton {
    val backButton = BackButton(content, rich, align, className, init)
    this.add(backButton)
    return backButton
}
