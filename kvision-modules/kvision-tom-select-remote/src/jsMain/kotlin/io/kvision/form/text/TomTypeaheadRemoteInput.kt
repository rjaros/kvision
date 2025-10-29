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

import dev.kilua.rpc.HttpMethod
import dev.kilua.rpc.RpcServiceMgr
import io.kvision.core.Container
import io.kvision.core.KVScope
import io.kvision.form.select.TomSelectCallbacks
import io.kvision.html.InputType
import io.kvision.remote.KVCallAgent
import io.kvision.utils.Serialization
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import web.http.RequestInit

/**
 * The TomTypeaheadInput control connected to the fullstack service.
 *
 * @constructor
 * @param serviceManager fullstack service manager
 * @param function fullstack service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param type text input type (default "text")
 * @param value text input value
 * @param tsCallbacks Tom Select callbacks
 * @param requestFilter a request filtering function
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class TomTypeaheadRemoteInput<out T : Any>(
    private val serviceManager: RpcServiceMgr<T>,
    private val function: suspend T.(String?, String?) -> List<String>,
    protected val stateFunction: (() -> String)? = null,
    type: InputType = InputType.TEXT, value: String? = null, tsCallbacks: TomSelectCallbacks? = null,
    protected val requestFilter: (suspend RequestInit.() -> Unit)? = null,
    className: String? = null,
    init: (TomTypeaheadRemoteInput<T>.() -> Unit)? = null
) : TomTypeaheadInput(null, type, value, tsCallbacks, className) {

    init {
        val (url, method) = serviceManager.requireCall(function)
        val callAgent = KVCallAgent()
        val loadCallback: (query: String, callback: (Array<dynamic>) -> Unit) -> Unit = { query, callback ->
            loadResults(callAgent, url, method, query, requestFilter, callback)
        }
        this.tsCallbacks = tsCallbacks?.copy(load = loadCallback) ?: TomSelectCallbacks(load = loadCallback)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    protected open fun loadResults(
        callAgent: KVCallAgent,
        url: String,
        method: HttpMethod,
        query: String?,
        requestFilter: (suspend RequestInit.() -> Unit)?,
        callback: (Array<dynamic>) -> Unit
    ) {
        val queryParam = query?.let { JSON.stringify(it) }
        val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
        KVScope.launch {
            val result = callAgent.jsonRpcCall(
                url,
                listOf(queryParam, state),
                method,
                requestFilter = requestFilter?.let { requestFilterParam ->
                    {
                        val self = this.unsafeCast<RequestInit>()
                        self.requestFilterParam()
                    }
                }
            )
            val options = Serialization.plain.decodeFromString(
                ListSerializer(String.serializer()),
                result
            ).toTypedArray()
            callback(options)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T : Any> Container.tomTypeaheadRemoteInput(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)? = null,
    type: InputType = InputType.TEXT, value: String? = null, tsCallbacks: TomSelectCallbacks? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    className: String? = null,
    init: (TomTypeaheadRemoteInput<T>.() -> Unit)? = null
): TomTypeaheadRemoteInput<T> {
    val tomTypeaheadRemoteInput =
        TomTypeaheadRemoteInput(
            serviceManager,
            function,
            stateFunction,
            type,
            value,
            tsCallbacks,
            requestFilter,
            className,
            init
        )
    this.add(tomTypeaheadRemoteInput)
    return tomTypeaheadRemoteInput
}
