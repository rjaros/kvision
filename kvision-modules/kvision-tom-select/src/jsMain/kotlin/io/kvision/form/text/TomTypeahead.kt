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
import io.kvision.form.select.TomSelectCallbacks
import io.kvision.html.InputType

/**
 * Form field typeahead component based on Tom Select.
 *
 * @constructor
 * @param options a static list of options
 * @param type text input type (default "text")
 * @param value text input value
 * @param tsCallbacks Tom Select callbacks
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
open class TomTypeahead(
    options: List<String>? = null, type: InputType = InputType.TEXT, value: String? = null,
    tsCallbacks: TomSelectCallbacks? = null, name: String? = null,
    label: String? = null, rich: Boolean = false, init: (TomTypeahead.() -> Unit)? = null
) : AbstractText(label, rich, false) {

    /**
     * A static list of options for a typeahead control
     */
    var options
        get() = input.options
        set(value) {
            input.options = value
        }

    /**
     * Tom Select callbacks
     */
    var tsCallbacks
        get() = input.tsCallbacks
        set(value) {
            input.tsCallbacks = value
        }

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

    final override val input: TomTypeaheadInput =
        TomTypeaheadInput(options, type, value, tsCallbacks).apply {
            this.id = this@TomTypeahead.idc
            this.name = name
        }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.tomTypeahead(
    options: List<String>? = null, type: InputType = InputType.TEXT, value: String? = null,
    tsCallbacks: TomSelectCallbacks? = null, name: String? = null,
    label: String? = null, rich: Boolean = false, init: (TomTypeahead.() -> Unit)? = null
): TomTypeahead {
    val tomTypeahead =
        TomTypeahead(
            options,
            type,
            value,
            tsCallbacks,
            name,
            label,
            rich,
            init
        )
    this.add(tomTypeahead)
    return tomTypeahead
}
