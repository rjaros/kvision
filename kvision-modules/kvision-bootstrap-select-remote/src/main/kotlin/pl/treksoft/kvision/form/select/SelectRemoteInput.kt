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
package pl.treksoft.kvision.form.select

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asDeferred
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.list
import kotlinx.serialization.stringify
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.remote.CallAgent
import pl.treksoft.kvision.remote.HttpMethod
import pl.treksoft.kvision.remote.JsonRpcRequest
import pl.treksoft.kvision.remote.KVServiceManager
import pl.treksoft.kvision.remote.RemoteOption
import pl.treksoft.kvision.utils.JSON
import pl.treksoft.kvision.utils.obj

external fun decodeURIComponent(encodedURI: String): String

/**
 * The Select control connected to the multiplatform service.
 *
 * @constructor
 * @param value selected value
 * @param serviceManager multiplatform service manager
 * @param function multiplatform service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param ajaxOptions additional options for remote data source
 * @param classes a set of CSS class names
 */
@UseExperimental(ImplicitReflectionSerializer::class)
open class SelectRemoteInput<T : Any>(
    value: String? = null,
    serviceManager: KVServiceManager<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    multiple: Boolean = false,
    ajaxOptions: AjaxOptions? = null,
    classes: Set<String> = setOf()
) : SelectInput(null, value, multiple, null, classes) {
    init {
        val (url, method) =
            serviceManager.getCalls()[function.toString().replace("\\s".toRegex(), "")]
                ?: throw IllegalStateException("Function not specified!")
        val data = obj {
            q = "{{{q}}}"
        }
        val tempAjaxOptions = ajaxOptions ?: AjaxOptions()
        this.ajaxOptions = tempAjaxOptions.copy(url = url, preprocessData = {
            @Suppress("UnsafeCastFromDynamic")
            JSON.plain.parse(RemoteOption.serializer().list, it.result as String).map {
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
        }, data = data, beforeSend = { _, b ->
            @Suppress("UnsafeCastFromDynamic")
            val q = decodeURIComponent(b.data.substring(2))
            val state = stateFunction?.invoke()
            b.data = JSON.plain.stringify(JsonRpcRequest(0, url, listOf(q, this.value, state)))
            true
        }, httpType = HttpType.valueOf(method.name), cache = false, preserveSelected = true)
        if (value != null) {
            GlobalScope.launch {
                val callAgent = CallAgent()
                val state = stateFunction?.invoke()
                val initials = callAgent.remoteCall(
                    url,
                    JSON.plain.stringify(JsonRpcRequest(0, url, listOf(null, value, state))),
                    HttpMethod.POST
                ).asDeferred().await()
                JSON.plain.parse(RemoteOption.serializer().list, initials.result as String).map {
                    add(SelectOption(it.value, it.text, selected = true))
                }
                this@SelectRemoteInput.refreshSelectInput()
            }
        }
        if (this.ajaxOptions?.emptyRequest == true) {
            this.setInternalEventListener<SelectRemote<*>> {
                shownBsSelect = {
                    val input = self.getElementJQuery()?.parent()?.find("input")
                    input?.trigger("keyup", null)
                    input?.hide(0)
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
fun <T : Any> Container.selectRemoteInput(
    value: String? = null,
    serviceManager: KVServiceManager<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    multiple: Boolean = false,
    ajaxOptions: AjaxOptions? = null,
    classes: Set<String> = setOf(), init: (SelectRemoteInput<T>.() -> Unit)? = null
): SelectRemoteInput<T> {
    val selectRemoteInput =
        SelectRemoteInput(value, serviceManager, function, stateFunction, multiple, ajaxOptions, classes).apply {
            init?.invoke(this)
        }
    this.add(selectRemoteInput)
    return selectRemoteInput
}
