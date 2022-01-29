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
package io.kvision.remote

import kotlinx.browser.window
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import org.w3c.dom.get
import org.w3c.dom.url.URLSearchParams
import org.w3c.fetch.INCLUDE
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import kotlin.js.Promise

/**
 * HTTP status unauthorized (401).
 */
const val HTTP_UNAUTHORIZED = 401

/**
 * HTTP response body types.
 */
enum class ResponseBodyType {
    JSON,
    TEXT,
    READABLE_STREAM
}

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
     * @param requestFilter a request filtering function
     * @return a promise of the result
     */
    @OptIn(ExperimentalSerializationApi::class)
    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    fun jsonRpcCall(
        url: String,
        data: List<String?> = listOf(),
        method: HttpMethod = HttpMethod.POST,
        requestFilter: (RequestInit.() -> Unit)? = null
    ): Promise<String> {
        val requestInit = RequestInit()
        requestInit.method = method.name
        requestInit.credentials = RequestCredentials.INCLUDE
        val jsonRpcRequest = JsonRpcRequest(counter++, url, data)
        val urlAddr = urlPrefix + url.drop(1)
        val fetchUrl = if (method == HttpMethod.GET) {
            urlAddr + "?" + URLSearchParams(obj { id = jsonRpcRequest.id }).toString()
        } else {
            requestInit.body = RemoteSerialization.plain.encodeToString(jsonRpcRequest)
            urlAddr
        }
        requestInit.headers = js("{}")
        requestInit.headers["Content-Type"] = "application/json"
        requestInit.headers["X-Requested-With"] = "XMLHttpRequest"
        requestFilter?.invoke(requestInit)
        return Promise { resolve, reject ->
            window.fetch(fetchUrl, requestInit).then { response ->
                if (response.ok) {
                    response.json().then { data: dynamic ->
                        when {
                            data.id != jsonRpcRequest.id -> reject(Exception("Invalid response ID"))
                            data.error != null -> {
                                if (data.exceptionType == "io.kvision.remote.ServiceException") {
                                    reject(ServiceException(data.error.toString()))
                                } else {
                                    reject(Exception(data.error.toString()))
                                }
                            }
                            data.result != null -> resolve(data.result)
                            else -> reject(Exception("Invalid response"))
                        }
                    }
                } else {
                    if (response.status.toInt() == HTTP_UNAUTHORIZED) {
                        reject(SecurityException(response.statusText))
                    } else {
                        reject(Exception(response.statusText))
                    }
                }
            }.catch {
                reject(Exception(it.message))
            }
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType response body type
     * @param requestFilter a request filtering function
     * @return a promise of the result
     */
    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    fun remoteCall(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        requestFilter: (RequestInit.() -> Unit)? = null
    ): Promise<dynamic> {
        val requestInit = RequestInit()
        requestInit.method = method.name
        requestInit.credentials = RequestCredentials.INCLUDE
        val urlAddr = urlPrefix + url.drop(1)
        val fetchUrl = if (method == HttpMethod.GET) {
            urlAddr + "?" + URLSearchParams(data).toString()
        } else {
            requestInit.body = when (contentType) {
                "application/json" -> if (data is String) data else kotlin.js.JSON.stringify(data)
                "application/x-www-form-urlencoded" -> URLSearchParams(data).toString()
                else -> data
            }
            urlAddr
        }
        requestInit.headers = js("{}")
        requestInit.headers["Content-Type"] = contentType
        requestInit.headers["X-Requested-With"] = "XMLHttpRequest"
        requestFilter?.invoke(requestInit)
        return Promise { resolve, reject ->
            window.fetch(fetchUrl, requestInit).then { response ->
                if (response.ok) {
                    when (responseBodyType) {
                        ResponseBodyType.JSON -> response.json().then { resolve(it) }
                        ResponseBodyType.TEXT -> response.text().then { resolve(it) }
                        ResponseBodyType.READABLE_STREAM -> resolve(response.body)
                    }
                } else {
                    if (response.status.toInt() == HTTP_UNAUTHORIZED) {
                        reject(SecurityException(response.statusText))
                    } else {
                        reject(Exception(response.statusText))
                    }
                }
            }.catch {
                reject(Exception(it.message))
            }
        }
    }
}
