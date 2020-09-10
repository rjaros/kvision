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

package pl.treksoft.kvision.onsenui.visual

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.html.Div
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.set

/**
 * A card component.
 *
 * @constructor Creates a card component.
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Card(
    classes: Set<String> = setOf(),
    init: (Card.() -> Unit)? = null
) : SimplePanel(classes) {

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

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        modifier?.let {
            sn.add("modifier" to it)
        }
        return sn
    }

    /**
     * DSL builder function to add title section of the card.
     * @param content the content of the title section
     * @param rich whether [content] can contain HTML code
     * @param builder a builder extension function
     */
    open fun title(content: String? = null, rich: Boolean = false, builder: (Div.() -> Unit)? = null): Div {
        val titleDiv = Div(content, rich, classes = setOf("title"))
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
        val contentDiv = Div(content, rich, classes = setOf("content"))
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
    classes: Set<String>? = null,
    className: String? = null,
    init: (Card.() -> Unit)? = null
): Card {
    val card = Card(classes ?: className.set, init)
    this.add(card)
    return card
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.card(
    state: ObservableState<S>,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Card.(S) -> Unit)
) = card(classes, className).bind(state, true, init)
