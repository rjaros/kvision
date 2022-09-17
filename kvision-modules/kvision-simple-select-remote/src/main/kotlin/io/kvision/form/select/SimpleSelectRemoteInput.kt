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

import io.kvision.core.Container
import io.kvision.remote.CallAgent
import io.kvision.remote.HttpMethod
import io.kvision.remote.JsonRpcRequest
import io.kvision.remote.KVServiceMgr
import io.kvision.remote.SimpleRemoteOption
import io.kvision.utils.Serialization
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString

/**
 * The SimpleSelect control connected to the fullstack service.
 *
 * @constructor
 * @param value selected value
 * @param serviceManager fullstack service manager
 * @param function fullstack service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param emptyOption determines if an empty option is automatically generated
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param selectSize the number of visible options
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class SimpleSelectRemoteInput<out T : Any>(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    private val stateFunction: (() -> String)? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    className: String? = null,
    init: (SimpleSelectRemoteInput<T>.() -> Unit)? = null
) : SimpleSelectInput(null, value, emptyOption, multiple, selectSize, className) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        val (url, _) = serviceManager.requireCall(function)
        scope.launch {
            val callAgent = CallAgent()
            val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
            val values = callAgent.remoteCall(
                url,
                Serialization.plain.encodeToString(JsonRpcRequest(0, url, listOf(state))),
                HttpMethod.POST
            ).await()
            options =
                Serialization.plain.decodeFromString(ListSerializer(SimpleRemoteOption.serializer()), values.result as String)
                    .map {
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
fun <T : Any> Container.simpleSelectRemoteInput(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    stateFunction: (() -> String)? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    className: String? = null,
    init: (SimpleSelectRemoteInput<T>.() -> Unit)? = null
): SimpleSelectRemoteInput<T> {
    val simpleSelectRemoteInput =
        SimpleSelectRemoteInput(
            serviceManager,
            function,
            stateFunction,
            value,
            emptyOption,
            multiple,
            selectSize,
            className,
            init
        )
    this.add(simpleSelectRemoteInput)
    return simpleSelectRemoteInput
}
