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
import io.kvision.state.ObservableState
import io.kvision.state.bind

/**
 * Form field typeahead component.
 *
 * @constructor
 * @param options a static list of options
 * @param taAjaxOptions AJAX options for remote data source
 * @param source source function for data source
 * @param items the max number of items to display in the dropdown
 * @param minLength the minimum character length needed before triggering dropdown
 * @param delay a delay between lookups
 * @param type text input type (default "text")
 * @param value text input value
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
open class Typeahead(
    options: List<String>? = null, taAjaxOptions: TaAjaxOptions? = null,
    source: ((String, (Array<String>) -> Unit) -> Unit)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false, init: (Typeahead.() -> Unit)? = null
) : AbstractText(label, rich) {

    /**
     * A static list of options for a typeahead control
     */
    var options
        get() = input.options
        set(value) {
            input.options = value
        }

    /**
     * AJAX options for remote data source
     */
    var taAjaxOptions
        get() = input.taAjaxOptions
        set(value) {
            input.taAjaxOptions = value
        }

    /**
     * Source function for data source
     */
    var source
        get() = input.source
        set(value) {
            input.source = value
        }

    /**
     * The max number of items to display in the dropdown
     */
    var items
        get() = input.items
        set(value) {
            input.items = value
        }

    /**
     * The minimum character length needed before triggering dropdown
     */
    var minLength
        get() = input.minLength
        set(value) {
            input.minLength = value
        }

    /**
     * Determines if hints should be shown as soon as the input gets focus.
     */
    var showHintOnFocus
        get() = input.showHintOnFocus
        set(value) {
            input.showHintOnFocus = value
        }

    /**
     * Determines if the first suggestion is selected automatically.
     */
    var autoSelect
        get() = input.autoSelect
        set(value) {
            input.autoSelect = value
        }

    /**
     * A delay between lookups.
     */
    var delay
        get() = input.taAjaxOptions
        set(value) {
            input.taAjaxOptions = value
        }

    /**
     * Determines if the menu is the same size as the input it is attached to.
     */
    var fitToElement
        get() = input.fitToElement
        set(value) {
            input.fitToElement = value
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

    final override val input: TypeaheadInput =
        TypeaheadInput(options, taAjaxOptions, source, items, minLength, delay, type, value).apply {
            this.id = idc
            this.name = name
        }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
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
fun Container.typeahead(
    options: List<String>? = null, taAjaxOptions: TaAjaxOptions? = null,
    source: ((String, (Array<String>) -> Unit) -> Unit)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false, init: (Typeahead.() -> Unit)? = null
): Typeahead {
    val typeahead =
        Typeahead(options, taAjaxOptions, source, items, minLength, delay, type, value, name, label, rich, init)
    this.add(typeahead)
    return typeahead
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.typeahead(
    state: ObservableState<S>,
    options: List<String>? = null, taAjaxOptions: TaAjaxOptions? = null,
    source: ((String, (Array<String>) -> Unit) -> Unit)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false, init: (Typeahead.(S) -> Unit)
) = typeahead(options, taAjaxOptions, source, items, minLength, delay, type, value, name, label, rich)
    .bind(state, true, init)
