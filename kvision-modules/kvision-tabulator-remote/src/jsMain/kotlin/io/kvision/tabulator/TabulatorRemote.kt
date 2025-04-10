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
package io.kvision.tabulator

import dev.kilua.rpc.CallAgent
import dev.kilua.rpc.RemoteData
import dev.kilua.rpc.RemoteFilter
import dev.kilua.rpc.RemoteSorter
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import io.kvision.core.Container
import io.kvision.core.KVScope
import io.kvision.utils.Serialization
import kotlinx.browser.window
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import org.w3c.dom.get
import web.http.RequestInit
import kotlin.reflect.KClass

/**
 * Tabulator component connected to the fullstack service.
 *
 * @constructor
 * @param T type of row data
 * @param E type of service manager
 * @param serviceManager fullstack service manager
 * @param function fullstack service method returning tabulator rows data
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param options tabulator options
 * @param types a set of table types
 * @param className CSS class names
 * @param kClass Kotlin class
 * @param serializer the serializer for type T
 * @param module optional serialization module with custom serializers
 * @param requestFilter a request filtering function
 */
open class TabulatorRemote<T : Any, out E : Any>(
    serviceManager: RpcServiceMgr<E>,
    function: suspend E.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<T>,
    stateFunction: (() -> String)? = null,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    className: String? = null,
    kClass: KClass<T>? = null,
    serializer: KSerializer<T>? = null,
    module: SerializersModule? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null
) : Tabulator<T>(null, false, options, types, className, kClass, serializer, module) {

    override val jsonHelper = if (serializer != null) Json(
        from = (RpcSerialization.customConfiguration ?: Serialization.customConfiguration ?: Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    ) {
        serializersModule = SerializersModule {
            include(RpcSerialization.plain.serializersModule)
            module?.let { this.include(it) }
        }.overwriteWith(serializersModule)
    } else null

    private val kvUrlPrefix = window["kv_remote_url_prefix"]
    private val urlPrefix: String = if (kvUrlPrefix != undefined) "$kvUrlPrefix/" else ""

    init {
        val (url, method) = serviceManager.requireCall(function)

        val callAgent = CallAgent()

        options.ajaxURL = urlPrefix + url.drop(1)
        options.ajaxRequestFunc = { _, _, params: dynamic ->
            val page = if (params.page != null) "" + params.page else null
            val size = if (params.size != null) "" + params.size else null

            val filters = if (params.filter != null) {
                JSON.stringify(params.filter)
            } else {
                null
            }

            val sorters = if (params.sort != null) {
                JSON.stringify(params.sort)
            } else {
                null
            }
            val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
            KVScope.async {
                val r = callAgent.jsonRpcCall(
                    url,
                    listOf(page, size, filters, sorters, state),
                    method = method,
                    requestFilter = requestFilter?.let { requestFilterParam ->
                        {
                            val self = this.unsafeCast<RequestInit>()
                            self.requestFilterParam()
                        }
                    })
                val result = JSON.parse<dynamic>(r)
                if (page != null) {
                    if (result.data == undefined) {
                        result.data = js("[]")
                    }
                    result
                } else if (result.data == undefined) {
                    js("[]")
                } else {
                    result.data
                }
            }.asPromise()
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
inline fun <reified T : Any, E : Any> Container.tabulatorRemote(
    serviceManager: RpcServiceMgr<E>,
    noinline function: suspend E.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<T>,
    noinline stateFunction: (() -> String)? = null,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    className: String? = null,
    serializer: KSerializer<T>? = null,
    module: SerializersModule? = null,
    noinline requestFilter: (suspend RequestInit.() -> Unit)? = null,
    noinline init: (TabulatorRemote<T, E>.() -> Unit)? = null
): TabulatorRemote<T, E> {
    val tabulatorRemote =
        TabulatorRemote(
            serviceManager,
            function,
            stateFunction,
            options,
            types,
            className,
            T::class,
            serializer,
            module,
            requestFilter
        )
    init?.invoke(tabulatorRemote)
    this.add(tabulatorRemote)
    return tabulatorRemote
}
