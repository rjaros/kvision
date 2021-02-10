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
import io.kvision.remote.KVServiceMgr

/**
 * Form field typeahead component connected to the multiplatform service.
 *
 * @constructor
 * @param serviceManager multiplatform service manager
 * @param function multiplatform service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param items the max number of items to display in the dropdown
 * @param minLength the minimum character length needed before triggering dropdown
 * @param delay a delay between lookups
 * @param type text input type (default "text")
 * @param value text input value
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
open class TypeaheadRemote<T : Any>(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    private val stateFunction: (() -> String)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false
) : AbstractText(label, rich) {

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

    final override val input: TypeaheadRemoteInput<T> =
        TypeaheadRemoteInput(serviceManager, function, stateFunction, items, minLength, delay, type, value).apply {
            this.id = idc
            this.name = name
        }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T : Any> Container.typeaheadRemote(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false, init: (TypeaheadRemote<T>.() -> Unit)? = null
): TypeaheadRemote<T> {
    val typeaheadRemote = TypeaheadRemote(
        serviceManager,
        function,
        stateFunction,
        items,
        minLength,
        delay,
        type,
        value,
        name,
        label,
        rich
    ).apply {
        init?.invoke(this)
    }
    this.add(typeaheadRemote)
    return typeaheadRemote
}
