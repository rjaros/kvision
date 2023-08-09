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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.promise
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.encodeToString
import org.w3c.dom.get
import org.w3c.dom.url.URLSearchParams
import org.w3c.fetch.INCLUDE
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import kotlin.coroutines.resume
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
 * An exception thrown when the server returns a non-json response for a json-rpc call.
 */
class ContentTypeException(message: String) : Exception(message)

/**
 * An agent responsible for remote calls.
 */
open class CallAgent {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

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
    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    fun jsonRpcCall(
        url: String,
        data: List<String?> = listOf(),
        method: HttpMethod = HttpMethod.POST,
        requestFilter: (suspend RequestInit.() -> Unit)? = null
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
        return scope.promise {
            requestFilter?.invoke(requestInit)
            suspendCancellableCoroutine { cont ->
                window.fetch(fetchUrl, requestInit).then { response ->
                    if (response.ok && response.headers.get("Content-Type") == "application/json") {
                        response.json().then { data: dynamic ->
                            when {
                                data.id != jsonRpcRequest.id -> cont.cancel(Exception("Invalid response ID"))
                                data.error != null -> {
                                    if (data.exceptionType == "io.kvision.remote.ServiceException") {
                                        cont.cancel(ServiceException(data.error.toString()))
                                    } else if (data.exceptionJson != null) {
                                        cont.cancel(
                                            RemoteSerialization.getJson()
                                                .decodeFromString<AbstractServiceException>(data.exceptionJson)
                                        )
                                    } else {
                                        cont.cancel(Exception(data.error.toString()))
                                    }
                                }

                                data.result != null -> cont.resume(data.result)
                                else -> cont.cancel(Exception("Invalid response"))
                            }
                        }
                    } else if (response.ok) {
                        cont.cancel(ContentTypeException("Invalid response content type: ${response.headers.get("Content-Type")}"))
                    } else {
                        if (response.status.toInt() == HTTP_UNAUTHORIZED) {
                            cont.cancel(SecurityException(response.statusText))
                        } else {
                            cont.cancel(Exception(response.statusText))
                        }
                    }
                }.catch {
                    cont.cancel(Exception(it.message))
                }
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
    @Suppress("ComplexMethod")
    fun remoteCall(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        requestFilter: (suspend RequestInit.() -> Unit)? = null
    ): Promise<dynamic> {
        val requestInit = RequestInit()
        requestInit.method = method.name
        requestInit.credentials = RequestCredentials.INCLUDE
        val urlAddr = urlPrefix + url.drop(1)
        val fetchUrl = if (method == HttpMethod.GET) {
            urlAddr + "?" + URLSearchParams(data).toString()
        } else {
            requestInit.body = when (contentType) {
                "application/json" -> if (data is String) data else JSON.stringify(data)
                "application/x-www-form-urlencoded" -> URLSearchParams(data).toString()
                else -> data
            }
            urlAddr
        }
        requestInit.headers = js("{}")
        requestInit.headers["Content-Type"] = contentType
        requestInit.headers["X-Requested-With"] = "XMLHttpRequest"
        return scope.promise {
            requestFilter?.invoke(requestInit)
            suspendCancellableCoroutine { cont ->
                window.fetch(fetchUrl, requestInit).then { response ->
                    if (response.ok) {
                        when (responseBodyType) {
                            ResponseBodyType.JSON -> {
                                if (response.headers.get("Content-Type") == "application/json") {
                                    response.json().then { cont.resume(it) }
                                } else {
                                    cont.cancel(ContentTypeException("Invalid response content type: ${response.headers.get("Content-Type")}"))
                                }
                            }
                            ResponseBodyType.TEXT -> response.text().then { cont.resume(it) }
                            ResponseBodyType.READABLE_STREAM -> cont.resume(response.body)
                        }
                    } else {
                        if (response.status.toInt() == HTTP_UNAUTHORIZED) {
                            cont.cancel(SecurityException(response.statusText))
                        } else {
                            cont.cancel(Exception(response.statusText))
                        }
                    }
                }.catch {
                    cont.cancel(Exception(it.message))
                }
            }
        }
    }
}
