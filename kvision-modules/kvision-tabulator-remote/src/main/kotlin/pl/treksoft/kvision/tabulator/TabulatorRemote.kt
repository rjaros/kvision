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
package pl.treksoft.kvision.tabulator

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.stringify
import org.w3c.dom.get
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.remote.CallAgent
import pl.treksoft.kvision.remote.HttpMethod
import pl.treksoft.kvision.remote.JsonRpcRequest
import pl.treksoft.kvision.remote.KVServiceManager
import pl.treksoft.kvision.remote.RemoteData
import pl.treksoft.kvision.remote.RemoteFilter
import pl.treksoft.kvision.remote.RemoteSorter
import pl.treksoft.kvision.table.TableType
import pl.treksoft.kvision.utils.JSON
import pl.treksoft.kvision.utils.set
import kotlin.browser.window

/**
 * Tabulator component connected to the multiplatform service.
 *
 * @constructor
 * @param T type of row data
 * @param E type of service manager
 * @param serviceManager multiplatform service manager
 * @param function multiplatform service method returning tabulator rows data
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param options tabulator options
 * @param types a set of table types
 * @param classes a set of CSS class names
 */
@OptIn(ImplicitReflectionSerializer::class)
open class TabulatorRemote<T : Any, E : Any>(
    serviceManager: KVServiceManager<E>,
    function: suspend E.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<T>,
    stateFunction: (() -> String)? = null,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    classes: Set<String> = setOf()
) : Tabulator<T>(null, false, options, types, classes) {

    private val kvUrlPrefix = window["kv_remote_url_prefix"]
    private val urlPrefix: String = if (kvUrlPrefix != undefined) kvUrlPrefix else ""

    init {
        val (url, method) =
            serviceManager.getCalls()[function.toString().replace("\\s".toRegex(), "")]
                ?: throw IllegalStateException("Function not specified!")

        val callAgent = CallAgent()
        options.ajaxURL = urlPrefix + url
        options.ajaxRequestFunc = { _, _, params ->
            val page = params.page
            val size = params.size

            @Suppress("UnsafeCastFromDynamic")
            val filters = if (params.filters != null) {
                kotlin.js.JSON.stringify(params.filters)
            } else {
                null
            }

            @Suppress("UnsafeCastFromDynamic")
            val sorters = if (params.sorters != null) {
                kotlin.js.JSON.stringify(params.sorters)
            } else {
                null
            }
            val state = stateFunction?.invoke()

            @Suppress("UnsafeCastFromDynamic")
            val data = JSON.plain.stringify(JsonRpcRequest(0, url, listOf(page, size, filters, sorters, state)))
            callAgent.remoteCall(url, data, method = HttpMethod.valueOf(method.name)).then { r: dynamic ->
                val result = kotlin.js.JSON.parse<dynamic>(r.result as String)
                @Suppress("UnsafeCastFromDynamic")
                if (page != null) {
                    result
                } else {
                    result.data
                }
            }
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T : Any, E : Any> Container.tabulatorRemote(
    serviceManager: KVServiceManager<E>,
    function: suspend E.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<T>,
    stateFunction: (() -> String)? = null,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    classes: Set<String>? = null,
    className: String? = null,
    init: (TabulatorRemote<T, E>.() -> Unit)? = null
): TabulatorRemote<T, E> {
    val tabulatorRemote =
        TabulatorRemote(serviceManager, function, stateFunction, options, types, classes ?: className.set)
    init?.invoke(tabulatorRemote)
    this.add(tabulatorRemote)
    return tabulatorRemote
}
