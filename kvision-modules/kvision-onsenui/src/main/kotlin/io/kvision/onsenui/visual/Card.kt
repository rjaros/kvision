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

package io.kvision.onsenui.visual

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.panel.SimplePanel

/**
 * A card component.
 *
 * @constructor Creates a card component.
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Card(
    className: String? = null,
    init: (Card.() -> Unit)? = null
) : SimplePanel(className) {

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-card", childrenVNodes())
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    /**
     * DSL builder function to add title section of the card.
     * @param content the content of the title section
     * @param rich whether [content] can contain HTML code
     * @param builder a builder extension function
     */
    open fun title(content: String? = null, rich: Boolean = false, builder: (Div.() -> Unit)? = null): Div {
        val titleDiv = Div(content, rich, className = "title")
        builder?.invoke(titleDiv)
        add(titleDiv)
        return titleDiv
    }

    /**
     * DSL builder function to add content section of the card.
     * @param content the content of the content section
     * @param rich whether [content] can contain HTML code
     * @param builder a builder extension function
     */
    open fun content(content: String? = null, rich: Boolean = false, builder: (Div.() -> Unit)? = null): Div {
        val contentDiv = Div(content, rich, className = "content")
        builder?.invoke(contentDiv)
        add(contentDiv)
        return contentDiv
    }

}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.card(
    className: String? = null,
    init: (Card.() -> Unit)? = null
): Card {
    val card = Card(className, init)
    this.add(card)
    return card
}
