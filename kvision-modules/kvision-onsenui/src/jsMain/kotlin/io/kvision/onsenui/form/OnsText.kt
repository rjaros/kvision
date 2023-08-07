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

import io.kvision.core.Container
import io.kvision.core.Display
import io.kvision.form.text.AbstractText
import io.kvision.html.InputType
import io.kvision.onsenui.OnsenUi

/**
 * Onsen UI form field text component.
 *
 * @constructor Creates a form field text component.
 * @param type text input type (default "text")
 * @param value text input value
 * @param placeholder the placeholder for the text input
 * @param floatLabel whether the placeholder will be animated in Material Design
 * @param name the name attribute of the generated HTML input element
 * @param maxlength maximum length of the text input
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class OnsText(
    type: InputType = InputType.TEXT,
    value: String? = null,
    placeholder: String? = null,
    floatLabel: Boolean? = null,
    name: String? = null,
    maxlength: Int? = null,
    label: String? = null,
    rich: Boolean = false,
    className: String? = null,
    init: (OnsText.() -> Unit)? = null
) : AbstractText(label, rich, false, (className?.let { "$it " } ?: "") + "kv-ons-form-group") {

    /**
     * Text input type.
     */
    var type
        get() = input.type
        set(value) {
            input.type = value
        }

    /**
     * Whether the placeholder will be animated in Material Design.
     */
    var floatLabel
        get() = input.floatLabel
        set(value) {
            input.floatLabel = value
            if (input.floatLabel == true && OnsenUi.isAndroid()) {
                flabel.display = Display.NONE
            } else {
                flabel.display = if (flabel.content != null) null else Display.NONE
            }
        }

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier
        get() = input.modifier
        set(value) {
            input.modifier = value
        }

    /**
     * Determines if autocomplete is enabled for the input element.
     */
    var autocomplete
        get() = input.autocomplete
        set(value) {
            input.autocomplete = value
        }

    final override val input: OnsTextInput = OnsTextInput(type, value, placeholder, floatLabel, idc, maxlength).apply {
        modifier = "underbar"
        this.name = name
        this.eventTarget = this@OnsText
    }

    init {
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        if (input.floatLabel == true && OnsenUi.isAndroid()) flabel.display = Display.NONE
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsText(
    type: InputType = InputType.TEXT,
    value: String? = null,
    placeholder: String? = null,
    floatLabel: Boolean? = null,
    name: String? = null,
    maxlength: Int? = null,
    label: String? = null,
    rich: Boolean = false,
    className: String? = null,
    init: (OnsText.() -> Unit)? = null
): OnsText {
    val onsText =
        OnsText(type, value, placeholder, floatLabel, name, maxlength, label, rich, className, init)
    this.add(onsText)
    return onsText
}
