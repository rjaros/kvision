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

/**
 * Simple component rendered as *div*.
 *
 * @constructor
 * @param text element text
 * @param rich determines if [text] can contain HTML code
 */
open class Div(
    text: String,
    rich: Boolean = false,
    align: ALIGN? = null,
    classes: Set<String> = setOf(),
    init: (Tag.() -> Unit)? = null
) :
    Tag(TAG.DIV, text, rich, align, classes, init) {
    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.div(
            text: String,
            rich: Boolean = false,
            align: ALIGN? = null,
            classes: Set<String> = setOf(),
            init: (Div.() -> Unit)? = null
        ): Div {
            val div = Div(text, rich, align, classes).apply { init?.invoke(this) }
            this.add(div)
            return div
        }
    }
}
