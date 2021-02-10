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

import kotlinx.browser.window
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import org.w3c.dom.get
import io.kvision.core.Container
import io.kvision.remote.JsonRpcRequest
import io.kvision.remote.KVServiceMgr
import io.kvision.utils.JSON
import io.kvision.utils.set
import kotlin.reflect.KFunction

/**
 * The Typeahead control connected to the multiplatform service.
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
 * @param taAjaxOptions AJAX options for remote data source
 * @param classes a set of CSS class names
 */
open class TypeaheadRemoteInput<T : Any>(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    private val stateFunction: (() -> String)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null,
    taAjaxOptions: TaAjaxOptions? = null,
    classes: Set<String> = setOf()
) : TypeaheadInput(null, null, null, items, minLength, delay, type, value, classes) {

    private val kvUrlPrefix = window["kv_remote_url_prefix"]
    private val urlPrefix: String = if (kvUrlPrefix != undefined) "$kvUrlPrefix/" else ""

    init {
        val (url, method) =
            serviceManager.getCalls()[(function as? KFunction<*>)?.name ?: function.toString()
                .replace("\\s".toRegex(), "")]
                ?: throw IllegalStateException("Function not specified!")
        val tempAjaxOptions = taAjaxOptions ?: TaAjaxOptions()
        this.taAjaxOptions = tempAjaxOptions.copy(
            url = urlPrefix + url.drop(1),
            preprocessQuery = { query ->
                val squery = kotlin.js.JSON.stringify(query)
                val state = stateFunction?.invoke()?.let { kotlin.js.JSON.stringify(it) }
                JSON.plain.encodeToString(JsonRpcRequest(0, url, listOf(squery, state)))
            },
            preprocessData = {
                JSON.plain.decodeFromString(ListSerializer(String.serializer()), it.result.unsafeCast<String>())
                    .toTypedArray()
            },
            httpType = HttpType.valueOf(method.name)
        )
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T : Any> Container.typeaheadRemoteInput(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null,
    taAjaxOptions: TaAjaxOptions? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (TypeaheadRemoteInput<T>.() -> Unit)? = null
): TypeaheadRemoteInput<T> {
    val typeaheadRemoteInput =
        TypeaheadRemoteInput(
            serviceManager,
            function,
            stateFunction,
            items,
            minLength,
            delay,
            type,
            value,
            taAjaxOptions,
            classes ?: className.set
        ).apply {
            init?.invoke(this)
        }
    this.add(typeaheadRemoteInput)
    return typeaheadRemoteInput
}
