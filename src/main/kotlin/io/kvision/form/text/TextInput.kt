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

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.html.Autocomplete
import io.kvision.html.InputType
import io.kvision.snabbdom.VNode

/**
 * Basic text component.
 *
 * @constructor
 * @param type text input type (default "text")
 * @param value text input value
 * @param maxlength maximum length of the text input
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class TextInput(
    type: InputType = InputType.TEXT,
    value: String? = null,
    maxlength: Int? = null,
    className: String? = null,
    init: (TextInput.() -> Unit)? = null
) :
    AbstractTextInput(value, maxlength, (className?.let { "$it " } ?: "") + "form-control") {

    /**
     * Text input type.
     */
    var type by refreshOnUpdate(type)

    /**
     * Specifies the auto complete mode of the input element
     */
    var autocomplete: Autocomplete? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("input")
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (type == InputType.COLOR) classSetBuilder.add("form-control-color")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", type.type)
        startValue?.let {
            attributeSetBuilder.add("value", it)
        }
        autocomplete?.let {
            attributeSetBuilder.add("autocomplete", it.type)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.textInput(
    type: InputType = InputType.TEXT, value: String? = null,
    maxlength: Int? = null,
    className: String? = null,
    init: (TextInput.() -> Unit)? = null
): TextInput {
    val textInput = TextInput(type, value, maxlength, className, init)
    this.add(textInput)
    return textInput
}
