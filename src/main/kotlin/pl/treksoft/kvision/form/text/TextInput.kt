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

/**
 * Text input types.
 */
enum class TEXTINPUTTYPE(internal val type: String) {
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
 */
open class TextInput(type: TEXTINPUTTYPE = TEXTINPUTTYPE.TEXT, value: String? = null, classes: Set<String> = setOf()) :
    AbstractTextInput(value, classes + "form-control") {

    /**
     * Text input type.
     */
    var type: TEXTINPUTTYPE = type
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if autocomplete is enabled for the input element.
     */
    var autocomplete: Boolean? = null
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return render("input")
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("type" to type.type)
        startValue?.let {
            sn.add("value" to it)
        }
        autocomplete?.let {
            if (it) {
                sn.add("autocomplete" to "on")
            } else {
                sn.add("autocomplete" to "off")
            }
        }
        return sn
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.textInput(
            type: TEXTINPUTTYPE = TEXTINPUTTYPE.TEXT, value: String? = null, classes: Set<String> = setOf(),
            init: (TextInput.() -> Unit)? = null
        ): TextInput {
            val textInput = TextInput(type, value, classes).apply { init?.invoke(this) }
            this.add(textInput)
            return textInput
        }
    }
}
