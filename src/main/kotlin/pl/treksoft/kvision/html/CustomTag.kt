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
package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.set

/**
 * HTML custom tag component.
 *
 * @constructor
 * @param elementName element name
 * @param content element text
 * @param rich determines if [content] can contain HTML code
 * @param align content align
 * @param classes a set of CSS class names
 * @param attributes a map of additional attributes
 * @param init an initializer extension function
 */
open class CustomTag(
    elementName: String,
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String> = setOf(), attributes: Map<String, String>? = null,
    init: (CustomTag.() -> Unit)? = null
) :
    Tag(TAG.DIV, content, rich, align, classes, attributes) {

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
    classes: Set<String>? = null,
    className: String? = null,
    attributes: Map<String, String>? = null,
    init: (CustomTag.() -> Unit)? = null
): CustomTag {
    val customTag =
        CustomTag(elementName, content, rich, align, classes ?: className.set, attributes).apply { init?.invoke(this) }
    this.add(customTag)
    return customTag
}

/**
 * DSL builder extension function for observable state for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.customTag(
    state: ObservableState<S>,
    elementName: String,
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    attributes: Map<String, String>? = null,
    init: (CustomTag.(S) -> Unit)
) = customTag(elementName, content, rich, align, classes, className, attributes).bind(state, true, init)
