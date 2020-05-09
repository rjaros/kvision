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
package pl.treksoft.kvision.form.text

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.utils.set

/**
 * Basic textarea component.
 *
 * @constructor
 * @param cols number of columns
 * @param rows number of rows
 * @param value text input value
 * @param classes a set of CSS class names
 */
open class TextAreaInput(cols: Int? = null, rows: Int? = null, value: String? = null, classes: Set<String> = setOf()) :
    AbstractTextInput(value, classes + "form-control") {

    /**
     * Number of columns.
     */
    var cols by refreshOnUpdate(cols)

    /**
     * Number of rows.
     */
    var rows by refreshOnUpdate(rows)

    /**
     * Determines if hard wrapping is enabled for the textarea element.
     */
    var wrapHard by refreshOnUpdate(false)

    override fun render(): VNode {
        return startValue?.let {
            render("textarea", arrayOf(it))
        } ?: render("textarea")
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        cols?.let {
            sn.add("cols" to ("" + it))
        }
        rows?.let {
            sn.add("rows" to ("" + it))
        }
        if (wrapHard) {
            sn.add("wrap" to "hard")
        }
        return sn
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.textAreaInput(
    cols: Int? = null, rows: Int? = null, value: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (TextAreaInput.() -> Unit)? = null
): TextAreaInput {
    val textAreaInput = TextAreaInput(cols, rows, value, classes ?: className.set).apply { init?.invoke(this) }
    this.add(textAreaInput)
    return textAreaInput
}
