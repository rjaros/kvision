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

package pl.treksoft.kvision.onsenui.grid

import pl.treksoft.kvision.core.AttributeSetBuilder
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.CustomTag
import pl.treksoft.kvision.onsenui.GridVerticalAlign
import pl.treksoft.kvision.utils.asString
import pl.treksoft.kvision.utils.set

/**
 * A column component.
 *
 * @constructor Creates a column component.
 * @param content the content of the column.
 * @param rich whether [content] can contain HTML code
 * @param align text align
 * @param colWidth a column width
 * @param colVerticalAlign vertical align of the column content
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Col(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    colWidth: CssSize? = null,
    colVerticalAlign: GridVerticalAlign? = null,
    classes: Set<String> = setOf(),
    init: (Col.() -> Unit)? = null
) : CustomTag("ons-col", content, rich, align, classes) {

    /**
     *  The column width.
     */
    var colWidth: CssSize? by refreshOnUpdate(colWidth)

    /**
     *  The vertical align of the column content.
     */
    var colVerticalAlign: GridVerticalAlign? by refreshOnUpdate(colVerticalAlign)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        colWidth?.let {
            attributeSetBuilder.add("width", it.asString())
        }
        colVerticalAlign?.let {
            attributeSetBuilder.add("vertical-align", it.attributeValue)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Row.col(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    colWidth: CssSize? = null,
    colVerticalAlign: GridVerticalAlign? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Col.() -> Unit)? = null
): Col {
    val col = Col(content, rich, align, colWidth, colVerticalAlign, classes ?: className.set, init)
    this.add(col)
    return col
}
