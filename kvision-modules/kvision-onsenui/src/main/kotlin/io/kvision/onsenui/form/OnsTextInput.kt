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

package io.kvision.onsenui.form

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.form.text.TextInput
import io.kvision.form.text.TextInputType
import io.kvision.utils.set

/**
 * OnsenUI text input component.
 *
 * @constructor Creates a text input component.
 * @param type text input type (default "text")
 * @param value text input value
 * @param placeholder the placeholder for the text input
 * @param floatLabel whether the placeholder will be animated in Material Design
 * @param inputId the ID of the input element
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsTextInput(
    type: TextInputType = TextInputType.TEXT,
    value: String? = null,
    placeholder: String? = null,
    floatLabel: Boolean? = null,
    inputId: String? = null,
    classes: Set<String> = setOf(),
    init: (OnsTextInput.() -> Unit)? = null
) : TextInput(type, value, classes + "kv-ons-form-control") {

    /**
     * Whether the placeholder will be animated in Material Design.
     */
    var floatLabel: Boolean? by refreshOnUpdate(floatLabel)

    /**
     * The ID of the input element.
     */
    var inputId: String? by refreshOnUpdate(inputId)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        this.placeholder = placeholder
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return if (type == TextInputType.SEARCH) {
            render("ons-search-input")
        } else {
            render("ons-input")
        }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        if (floatLabel == true) {
            attributeSetBuilder.add("float")
        }
        inputId?.let {
            attributeSetBuilder.add("input-id", it)
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsTextInput(
    type: TextInputType = TextInputType.TEXT,
    value: String? = null,
    placeholder: String? = null,
    floatLabel: Boolean? = null,
    inputId: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsTextInput.() -> Unit)? = null
): OnsTextInput {
    val onsTextInput = OnsTextInput(type, value, placeholder, floatLabel, inputId, classes ?: className.set, init)
    this.add(onsTextInput)
    return onsTextInput
}
