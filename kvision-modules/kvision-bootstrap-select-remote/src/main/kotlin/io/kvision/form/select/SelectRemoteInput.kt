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
@file:Suppress("DEPRECATION")

package io.kvision.form.select

import io.kvision.core.Container
import io.kvision.core.getElementJQuery
import io.kvision.core.getElementJQueryD
import io.kvision.remote.CallAgent
import io.kvision.remote.HttpMethod
import io.kvision.remote.JsonRpcRequest
import io.kvision.remote.KVServiceMgr
import io.kvision.remote.RemoteOption
import io.kvision.utils.Serialization
import io.kvision.utils.obj
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import org.w3c.dom.get

external fun decodeURIComponent(encodedURI: String): String

/**
 * The Select control connected to the fullstack service.
 *
 * @constructor
 * @param value selected value
 * @param serviceManager fullstack service manager
 * @param function fullstack service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param ajaxOptions additional options for remote data source
 * @param preload preload all options from remote data source
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Deprecated("Use TomSelectRemoteInput instead from the kvision-tom-select-remote module. The library that this component is based on is no longer maintained and this module will be removed in KVision 6.")
open class SelectRemoteInput<out T : Any>(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    private val stateFunction: (() -> String)? = null,
    value: String? = null,
    multiple: Boolean = false,
    ajaxOptions: AjaxOptions? = null,
    private val preload: Boolean = false,
    className: String? = null,
    init: (SelectRemoteInput<T>.() -> Unit)? = null
) : SelectInput(null, value, multiple, null, className) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val kvUrlPrefix = window["kv_remote_url_prefix"]
    private val urlPrefix: String = if (kvUrlPrefix != undefined) "$kvUrlPrefix/" else ""

    private val url: String
    private val labelsCache = mutableMapOf<String, String>()
    private var initRun = false

    init {
        val (_url, method) = serviceManager.requireCall(function)
        this.url = _url
        if (!preload) {
            val data = obj {
                q = "{{{q}}}"
            }
            val tempAjaxOptions = ajaxOptions ?: AjaxOptions()
            this.ajaxOptions = tempAjaxOptions.copy(
                url = urlPrefix + url.drop(1),
                preprocessData = {
                    Serialization.plain.decodeFromString(ListSerializer(RemoteOption.serializer()), it.result as String)
                        .map {
                            obj {
                                this.value = it.value
                                if (it.text != null) this.text = it.text
                                if (it.className != null) this.`class` = it.className
                                if (it.disabled) this.disabled = true
                                if (it.divider) this.divider = true
                                this.data = obj {
                                    if (it.subtext != null) this.subtext = it.subtext
                                    if (it.icon != null) this.icon = it.icon
                                    if (it.content != null) this.content = it.content
                                }
                            }
                        }.toTypedArray()
                },
                data = data,
                beforeSend = { xhr, b ->
                    ajaxOptions?.beforeSend?.invoke(xhr, b)
                    @Suppress("UnsafeCastFromDynamic")
                    val q = JSON.stringify(decodeURIComponent(b.asDynamic().data.substring(2)))
                    val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
                    val svalue = JSON.stringify(this.value)
                    b.data = Serialization.plain.encodeToString(JsonRpcRequest(0, url, listOf(q, svalue, state)))
                    true
                },
                httpType = HttpType.valueOf(method.name),
                cache = false,
                preserveSelected = ajaxOptions?.preserveSelected ?: false
            )
        } else {
            scope.launch {
                val callAgent = CallAgent()
                val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
                val values = callAgent.remoteCall(
                    url,
                    Serialization.plain.encodeToString(JsonRpcRequest(0, url, listOf(null, null, state))),
                    HttpMethod.POST
                ).await()
                Serialization.plain.decodeFromString(ListSerializer(RemoteOption.serializer()), values.result as String)
                    .forEach {
                        add(
                            SelectOption(
                                it.value,
                                it.text,
                                it.subtext,
                                it.icon,
                                it.divider,
                                it.disabled,
                                false,
                                it.className
                            )
                        )
                    }
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun refreshState() {
        value?.let {
            if (multiple) {
                getElementJQueryD()?.selectpicker("val", it.split(",").toTypedArray())
            } else {
                getElementJQueryD()?.selectpicker("val", it)
            }
            val elementValue = getElementJQuery()?.`val`()
            @Suppress("SimplifyBooleanWithConstants")
            if (!preload && (elementValue == null || elementValue == "") && initRun == false) {
                initRun = true
                scope.launch {
                    val labels = labelsCache.getOrPut(it) {
                        val callAgent = CallAgent()
                        val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
                        val svalue = JSON.stringify(it)
                        val initials = callAgent.remoteCall(
                            url,
                            Serialization.plain.encodeToString(JsonRpcRequest(0, url, listOf(null, svalue, state))),
                            HttpMethod.POST
                        ).await()
                        Serialization.plain.decodeFromString(
                            ListSerializer(RemoteOption.serializer()),
                            initials.result as String
                        ).mapNotNull {
                            it.text
                        }.joinToString(", ")
                    }
                    val button = getElementJQuery()?.next()
                    button?.removeClass("bs-placeholder")
                    button?.prop("title", labels)
                    button?.find(".filter-option-inner-inner")?.html(labels)
                    initRun = false
                }
            }
        } ?: getElementJQueryD()?.selectpicker("val", null)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
@Deprecated("Use tomSelectRemoteInput instead from the kvision-tom-select-remote module. The library that this component is based on is no longer maintained and this module will be removed in KVision 6.")
fun <T : Any> Container.selectRemoteInput(
    serviceManager: KVServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    value: String? = null,
    multiple: Boolean = false,
    ajaxOptions: AjaxOptions? = null,
    preload: Boolean = false,
    className: String? = null,
    init: (SelectRemoteInput<T>.() -> Unit)? = null
): SelectRemoteInput<T> {
    val selectRemoteInput =
        SelectRemoteInput(
            serviceManager,
            function,
            stateFunction,
            value,
            multiple,
            ajaxOptions,
            preload,
            className, init
        )
    this.add(selectRemoteInput)
    return selectRemoteInput
}
