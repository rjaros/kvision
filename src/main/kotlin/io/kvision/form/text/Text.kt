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

import io.kvision.core.Container

/**
 * Form field text component.
 *
 * @constructor
 * @param type text input type (default "text")
 * @param value text input value
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param floating use floating label
 * @param init an initializer extension function
 */
open class Text(
    type: TextInputType = TextInputType.TEXT, value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false, floating: Boolean = false, init: (Text.() -> Unit)? = null
) : AbstractText(label, rich, floating) {

    /**
     * Text input type.
     */
    var type
        get() = input.type
        set(value) {
            input.type = value
        }

    /**
     * Determines if autocomplete is enabled for the input element.
     */
    var autocomplete
        get() = input.autocomplete
        set(value) {
            input.autocomplete = value
        }

    final override val input: TextInput = TextInput(type, value).apply {
        this.id = this@Text.idc
        this.name = name
    }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        if (!floating) {
            this.addPrivate(flabel)
            this.addPrivate(input)
        } else {
            this.addPrivate(input)
            this.addPrivate(flabel)
        }
        this.addPrivate(invalidFeedback)
        @Suppress("LeakingThis")
        init?.invoke(this)
        floatingPlaceholder()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.text(
    type: TextInputType = TextInputType.TEXT, value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false, floating: Boolean = false, init: (Text.() -> Unit)? = null
): Text {
    val text = Text(type, value, name, label, rich, floating, init)
    this.add(text)
    return text
}
