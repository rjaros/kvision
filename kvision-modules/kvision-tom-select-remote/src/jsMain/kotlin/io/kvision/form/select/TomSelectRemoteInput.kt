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
import io.kvision.remote.RemoteOption
import io.kvision.snabbdom.VNode
import io.kvision.utils.Serialization
import io.kvision.utils.obj
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import org.w3c.fetch.RequestInit

/**
 * The TomSelectInput control connected to the fullstack service.
 *
 * @constructor
 * @param serviceManager fullstack service manager
 * @param function fullstack service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param value selected value
 * @param emptyOption determines if an empty option is automatically generated
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param maxOptions the number of visible options
 * @param tsOptions Tom Select options
 * @param tsCallbacks Tom Select callbacks
 * @param tsRenders Tom Select render functions
 * @param preload preload all options from remote data source
 * @param openOnFocus open dropdown on input focus
 * @param requestFilter a request filtering function
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class TomSelectRemoteInput<out T : Any>(
    private val serviceManager: KVServiceMgr<T>,
    private val function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    protected val stateFunction: (() -> String)? = null,
    value: String? = null, emptyOption: Boolean = false, multiple: Boolean = false, maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null, tsCallbacks: TomSelectCallbacks? = null, tsRenders: TomSelectRenders? = null,
    protected val preload: Boolean = false, protected val openOnFocus: Boolean = false,
    protected val requestFilter: (suspend RequestInit.() -> Unit)? = null,
    className: String? = null,
    init: (TomSelectRemoteInput<T>.() -> Unit)? = null
) : TomSelectInput(null, value, emptyOption, multiple, maxOptions, tsOptions, tsCallbacks, tsRenders, className) {

    private var initialized = false

    init {
        val (url, method) = serviceManager.requireCall(function)
        val callAgent = CallAgent()
        val loadCallback: ((query: String, callback: (Array<dynamic>) -> Unit) -> Unit)? = if (!preload) {
            { query, callback ->
                tomSelectJs?.clearOptions()
                loadResults(callAgent, url, method, query, this.value, requestFilter, callback)
            }
        } else null
        val shouldLoadCallback = if (openOnFocus) {
            { _: String -> true }
        } else null
        this.tsCallbacks = tsCallbacks?.copy(load = loadCallback, shouldLoad = shouldLoadCallback)
            ?: TomSelectCallbacks(load = loadCallback, shouldLoad = shouldLoadCallback)
        val forcedPreload = if (preload) true else tsOptions?.preload
        val forcedOpenOnFocus = if (openOnFocus) true else tsOptions?.openOnFocus
        this.tsOptions = tsOptions?.copy(preload = forcedPreload, openOnFocus = forcedOpenOnFocus, searchField = emptyList())
            ?: TomSelectOptions(
                preload = forcedPreload,
                openOnFocus = forcedOpenOnFocus,
                searchField = emptyList()
            )
        this.tsRenders = tsRenders?.copy(option = ::renderOption, item = ::renderItem) ?: TomSelectRenders(
            option = ::renderOption,
            item = ::renderItem
        )
        @Suppress("LeakingThis")
        init?.invoke(this)
        if (preload) {
            @Suppress("LeakingThis")
            loadResults(callAgent, url, method, null, null, requestFilter) { results ->
                this.tsOptions = this.tsOptions?.copy(options = results.toList())
                initialized = true
            }
        } else if (value != null) {
            @Suppress("LeakingThis")
            loadResults(callAgent, url, method, null, value, requestFilter) { results ->
                this.tsOptions = this.tsOptions?.copy(options = results.toList())
                initialized = true
            }
        } else {
            initialized = true
        }
    }

    override fun refreshState() {
        if (initialized && value != null && tomSelectJs != null && tomSelectJs!!.asDynamic().options[value!!] == null) {
            val (url, method) = serviceManager.requireCall(function)
            val callAgent = CallAgent()
            loadResults(callAgent, url, method, null, value, requestFilter) { results ->
                this.tomSelectJs?.addOptions(results)
                super.refreshState()
            }
        } else {
            super.refreshState()
        }
    }

    protected open fun installEventListeners() {
        if (openOnFocus) {
            tomSelectJs?.on("focus") {
                if (this.value == null) {
                    tomSelectJs?.load("")
                }
            }
            tomSelectJs?.on("change") { v: dynamic ->
                if (v == "") {
                    tomSelectJs?.load("")
                }
            }
        }
    }

    override fun afterInsert(node: VNode) {
        super.afterInsert(node)
        installEventListeners()
    }

    override fun refreshTomSelect() {
        super.refreshTomSelect()
        installEventListeners()
    }

    @Suppress("UnsafeCastFromDynamic")
    protected open fun renderOption(data: dynamic, escape: (String) -> String): String {
        val text = escape(data.text)
        val className = if (data.className != null) " class=\"${escape(data.className)}\"" else ""
        return if (data.divider) {
            "<div class=\"kv-tom-select-remote-divider\"></div>"
        } else if (data.content != null) {
            "<div${className}>${data.content}</div>"
        } else {
            val subtext = if (data.subtext) " <small class=\"text-body-secondary\">${escape(data.subtext)}</small> " else ""
            val icon = if (data.icon) "<i class=\"${escape(data.icon)}\"></i> " else ""
            "<div${className}>${icon}${text}${subtext}</div>"
        }
    }

    protected open fun renderItem(data: dynamic, escape: (String) -> String): String {
        return "<div>${renderOption(data, escape)}</div"
    }

    protected open fun loadResults(
        callAgent: CallAgent,
        url: String,
        method: HttpMethod,
        query: String?,
        initial: String?,
        requestFilter: (suspend RequestInit.() -> Unit)?,
        callback: (Array<dynamic>) -> Unit
    ) {
        val queryParam = query?.let { JSON.stringify(it) }
        val initialParam = initial?.let { JSON.stringify(it) }
        val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
        callAgent.remoteCall(
            url,
            Serialization.plain.encodeToString(JsonRpcRequest(0, url, listOf(queryParam, initialParam, state))),
            method,
            requestFilter = requestFilter
        ).then { response: dynamic ->
            val result = Serialization.plain.decodeFromString(
                ListSerializer(RemoteOption.serializer()),
                response.result.unsafeCast<String>()
            ).mapIndexed { index, option ->
                obj {
                    if (option.divider) {
                        this.value = "divider${index}"
                        this.divider = true
                        this.disabled = true
                    } else {
                        this.value = option.value
                        if (option.text != null) this.text = option.text
                        if (option.className != null) this.className = option.className
                        if (option.disabled) this.disabled = true
                        if (option.subtext != null) this.subtext = option.subtext
                        if (option.icon != null) this.icon = option.icon
                        if (option.content != null) this.content = option.content
                    }
                }
            }.toTypedArray()
            callback(result)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T : Any> Container.tomSelectRemoteInput(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    value: String? = null, emptyOption: Boolean = false, multiple: Boolean = false, maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null, tsCallbacks: TomSelectCallbacks? = null, tsRenders: TomSelectRenders? = null,
    preload: Boolean = false, openOnFocus: Boolean = false,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    className: String? = null,
    init: (TomSelectRemoteInput<T>.() -> Unit)? = null
): TomSelectRemoteInput<T> {
    val tomSelectRemoteInput =
        TomSelectRemoteInput(
            serviceManager,
            function,
            stateFunction,
            value,
            emptyOption,
            multiple,
            maxOptions,
            tsOptions,
            tsCallbacks,
            tsRenders,
            preload,
            openOnFocus,
            requestFilter,
            className,
            init
        )
    this.add(tomSelectRemoteInput)
    return tomSelectRemoteInput
}
