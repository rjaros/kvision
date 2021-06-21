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
package io.kvision.form.text

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container

/**
 * Basic textarea component.
 *
 * @constructor
 * @param cols number of columns
 * @param rows number of rows
 * @param value text input value
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class TextAreaInput(
    cols: Int? = null,
    rows: Int? = null,
    value: String? = null,
    className: String? = null,
    init: (TextAreaInput.() -> Unit)? = null
) :
    AbstractTextInput(value, (className?.let { "$it " } ?: "") + "form-control") {

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

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return startValue?.let {
            render("textarea", arrayOf(it))
        } ?: render("textarea")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        cols?.let {
            attributeSetBuilder.add("cols", ("" + it))
        }
        rows?.let {
            attributeSetBuilder.add("rows", ("" + it))
        }
        if (wrapHard) {
            attributeSetBuilder.add("wrap", "hard")
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.textAreaInput(
    cols: Int? = null, rows: Int? = null, value: String? = null,
    className: String? = null,
    init: (TextAreaInput.() -> Unit)? = null
): TextAreaInput {
    val textAreaInput = TextAreaInput(cols, rows, value, className, init)
    this.add(textAreaInput)
    return textAreaInput
}
