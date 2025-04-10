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
package io.kvision.form.select

import dev.kilua.rpc.CallAgent
import dev.kilua.rpc.HttpMethod
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.rpc.SimpleRemoteOption
import io.kvision.core.Container
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import web.http.RequestInit

/**
 * The Select control connected to the fullstack service.
 *
 * @constructor
 * @param value selected value
 * @param serviceManager fullstack service manager
 * @param function fullstack service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param emptyOption determines if an empty option is automatically generated
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param selectSize the number of visible options
 * @param requestFilter a request filtering function
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class SelectRemoteInput<out T : Any>(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    stateFunction: (() -> String)? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    className: String? = null,
    init: (SelectRemoteInput<T>.() -> Unit)? = null
) : SelectInput(null, value, emptyOption, multiple, selectSize, className) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        val (url, _) = serviceManager.requireCall(function)
        scope.launch {
            val callAgent = CallAgent()
            val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
            val result = callAgent.jsonRpcCall(
                url,
                listOf(state),
                HttpMethod.POST, requestFilter = requestFilter?.let { requestFilterParam ->
                    {
                        val self = this.unsafeCast<RequestInit>()
                        self.requestFilterParam()
                    }
                }
            )
            options = RpcSerialization.plain.decodeFromString(
                ListSerializer(SimpleRemoteOption.serializer()), result
            ).map {
                it.value to (it.text ?: it.value)
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T : Any> Container.selectRemoteInput(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    stateFunction: (() -> String)? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    className: String? = null,
    init: (SelectRemoteInput<T>.() -> Unit)? = null
): SelectRemoteInput<T> {
    val selectRemoteInput =
        SelectRemoteInput(
            serviceManager,
            function,
            stateFunction,
            value,
            emptyOption,
            multiple,
            selectSize,
            requestFilter,
            className,
            init
        )
    this.add(selectRemoteInput)
    return selectRemoteInput
}
