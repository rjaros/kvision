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
import io.kvision.remote.KVServiceMgr
import org.w3c.fetch.RequestInit

/**
 * The form field component for TomTypeaheadRemote control.
 *
 * @constructor
 * @param value selected value
 * @param serviceManager fullstack service manager
 * @param function fullstack service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param type text input type (default "text")
 * @param value text input value
 * @param tsCallbacks Tom Select callbacks
 * @param requestFilter a request filtering function
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
open class TomTypeaheadRemote<out T : Any>(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)? = null,
    type: InputType = InputType.TEXT, value: String? = null, tsCallbacks: TomSelectCallbacks? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    name: String? = null, label: String? = null, rich: Boolean = false,
    init: (TomTypeaheadRemote<T>.() -> Unit)? = null
) : AbstractText(label, rich, false) {

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

    final override val input: TomTypeaheadRemoteInput<T> = TomTypeaheadRemoteInput(
        serviceManager, function, stateFunction, type, value, tsCallbacks, requestFilter
    ).apply {
        this.id = this@TomTypeaheadRemote.idc
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
fun <T : Any> Container.tomTypeaheadRemote(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)? = null,
    type: InputType = InputType.TEXT, value: String? = null, tsCallbacks: TomSelectCallbacks? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    name: String? = null, label: String? = null, rich: Boolean = false, init: (TomTypeaheadRemote<T>.() -> Unit)? = null
): TomTypeaheadRemote<T> {
    val tomTypeaheadRemote =
        TomTypeaheadRemote(
            serviceManager, function, stateFunction,
            type, value, tsCallbacks, requestFilter,
            name, label, rich, init
        )
    this.add(tomTypeaheadRemote)
    return tomTypeaheadRemote
}
