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

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.set

/**
 * Simple component rendered as *figcaption*.
 *
 * @constructor
 * @param content element text
 * @param rich determines if [content] can contain HTML code
 * @param align content align
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Figcaption(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String> = setOf(),
    init: (Figcaption.() -> Unit)? = null
) :
    Tag(TAG.FIGCAPTION, content, rich, align, classes) {

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.figcaption(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Figcaption.() -> Unit)? = null
): Figcaption {
    val figcaption = Figcaption(content, rich, align, classes ?: className.set).apply { init?.invoke(this) }
    this.add(figcaption)
    return figcaption
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.figcaption(
    state: ObservableState<S>,
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Figcaption.(S) -> Unit)
) = figcaption(content, rich, align, classes, className).bind(state, true, init)
