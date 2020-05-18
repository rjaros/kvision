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
package pl.treksoft.kvision.remote

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.stringify
import org.w3c.dom.get
import pl.treksoft.jquery.JQueryAjaxSettings
import pl.treksoft.jquery.JQueryXHR
import pl.treksoft.jquery.jQuery
import kotlin.browser.window
import kotlin.js.Promise
import kotlin.js.JSON as NativeJSON

/**
 * HTTP status unauthorized (401).
 */
const val HTTP_UNAUTHORIZED = 401

/**
 * An agent responsible for remote calls.
 */
open class CallAgent {

    private val kvUrlPrefix = window["kv_remote_url_prefix"]
    private val urlPrefix: String = if (kvUrlPrefix != undefined) "$kvUrlPrefix/" else ""
    private var counter = 1

    /**
     * Makes an JSON-RPC call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @return a promise of the result
     */
    @OptIn(ImplicitReflectionSerializer::class)
    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    fun jsonRpcCall(
        url: String,
        data: List<String?> = listOf(),
        method: HttpMethod = HttpMethod.POST
    ): Promise<String> {
        val jsonRpcRequest = JsonRpcRequest(counter++, url, data)
        val jsonData = if (method == HttpMethod.GET) {
            obj { id = jsonRpcRequest.id }
        } else {
            JSON.plain.stringify(jsonRpcRequest)
        }
        return Promise { resolve, reject ->
            jQuery.ajax(urlPrefix + url.drop(1), obj {
                this.contentType = "application/json"
                this.data = jsonData
                this.method = method.name
                this.success =
                    { data: dynamic, _: Any, _: Any ->
                        when {
                            data.id != jsonRpcRequest.id -> reject(Exception("Invalid response ID"))
                            data.error != null -> {
                                if (data.exceptionType == "pl.treksoft.kvision.remote.ServiceException") {
                                    reject(ServiceException(data.error.toString()))
                                } else {
                                    reject(Exception(data.error.toString()))
                                }
                            }
                            data.result != null -> resolve(data.result)
                            else -> reject(Exception("Invalid response"))
                        }
                    }
                this.error =
                    { xhr: JQueryXHR, _: String, errorText: String ->
                        val message = if (xhr.responseJSON != null && xhr.responseJSON != undefined) {
                            NativeJSON.stringify(xhr.responseJSON)
                        } else if (xhr.responseText != undefined) {
                            xhr.responseText
                        } else {
                            errorText
                        }
                        if (xhr.status.toInt() == HTTP_UNAUTHORIZED) {
                            reject(SecurityException(message))
                        } else {
                            reject(Exception(message))
                        }
                    }
                this.xhrFields = obj {
                    this.withCredentials = true
                }
            })
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @return a promise of the result
     */
    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    fun remoteCall(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<dynamic> {
        return Promise { resolve, reject ->
            jQuery.ajax(urlPrefix + url.drop(1), obj {
                this.contentType = if (contentType != "multipart/form-data") contentType else false
                this.data = data
                this.method = method.name
                this.processData = if (contentType != "multipart/form-data") undefined else false
                this.success =
                    { data: dynamic, _: Any, _: Any ->
                        resolve(data)
                    }
                this.error =
                    { xhr: JQueryXHR, _: String, errorText: String ->
                        val message = if (xhr.responseJSON != null && xhr.responseJSON != undefined) {
                            NativeJSON.stringify(xhr.responseJSON)
                        } else if (xhr.responseText != undefined) {
                            xhr.responseText
                        } else {
                            errorText
                        }
                        if (xhr.status.toInt() == HTTP_UNAUTHORIZED) {
                            reject(SecurityException(message))
                        } else {
                            reject(Exception(message))
                        }
                    }
                this.beforeSend = beforeSend
                this.xhrFields = obj {
                    this.withCredentials = true
                }
            })
        }
    }
}
