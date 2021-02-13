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
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.set

/**
 * Text input types.
 */
enum class TextInputType(internal val type: String) {
    TEXT("text"),
    PASSWORD("password"),
    EMAIL("email"),
    TEL("tel"),
    COLOR("color"),
    SEARCH("search"),
    URL("url")
}

/**
 * Basic text component.
 *
 * @constructor
 * @param type text input type (default "text")
 * @param value text input value
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class TextInput(
    type: TextInputType = TextInputType.TEXT,
    value: String? = null,
    classes: Set<String> = setOf(),
    init: (TextInput.() -> Unit)? = null
) :
    AbstractTextInput(value, classes + "form-control") {

    /**
     * Text input type.
     */
    var type by refreshOnUpdate(type)

    /**
     * Determines if autocomplete is enabled for the input element.
     */
    var autocomplete: Boolean? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("input")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", type.type)
        startValue?.let {
            attributeSetBuilder.add("value", it)
        }
        autocomplete?.let {
            attributeSetBuilder.add("autocomplete", if (it) "on" else "off")
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.textInput(
    type: TextInputType = TextInputType.TEXT, value: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (TextInput.() -> Unit)? = null
): TextInput {
    val textInput = TextInput(type, value, classes ?: className.set, init)
    this.add(textInput)
    return textInput
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.textInput(
    state: ObservableState<S>,
    type: TextInputType = TextInputType.TEXT, value: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (TextInput.(S) -> Unit)
) = textInput(type, value, classes, className).bind(state, true, init)
