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

import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.ContentTypeException
import dev.kilua.rpc.HTTP_UNAUTHORIZED
import dev.kilua.rpc.HttpMethod
import dev.kilua.rpc.JsonRpcRequest
import dev.kilua.rpc.JsonRpcResponseJs
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.SecurityException
import dev.kilua.rpc.ServiceException
import js.globals.globalThis
import js.objects.unsafeJso
import js.promise.await
import js.reflect.unsafeCast
import js.uri.encodeURIComponent
import web.http.BodyInit
import web.http.Headers
import web.http.RequestInit
import web.http.RequestMethod
import web.http.fetchAsync
import web.url.URLSearchParams

class KVCallAgent {

    @OptIn(ExperimentalWasmJsInterop::class)
    @Suppress("ComplexMethod")
    suspend fun jsonRpcCall(
        url: String,
        data: List<String?> = listOf(),
        method: HttpMethod = HttpMethod.POST,
        requestFilter: (suspend RequestInit.() -> Unit)? = null
    ): String {
        val rpcUrlPrefix = globalThis.get("rpc_url_prefix")
        val urlPrefix: String = if (rpcUrlPrefix != undefined) "$rpcUrlPrefix/" else ""
        val jsonRpcRequest = JsonRpcRequest(1, url, data)
        val body =
            if (method == HttpMethod.GET) null else BodyInit(RpcSerialization.plain.encodeToString(jsonRpcRequest))
        val requestInit = getRequestInit(method, body, "application/json")
        requestFilter?.invoke(requestInit)
        val urlAddr = urlPrefix + url.drop(1)
        val fetchUrl = if (method == HttpMethod.GET) {
            val urlSearchParams = URLSearchParams()
            data.forEachIndexed { index, s ->
                if (s != null) urlSearchParams.append("p$index", encodeURIComponent(s))
            }
            "$urlAddr?$urlSearchParams"
        } else {
            urlAddr
        }
        val response = try {
            fetchAsync(fetchUrl, requestInit).await()
        } catch (e: Throwable) {
            throw Exception("Failed to fetch $fetchUrl: ${e.message}")
        }
        return if (response.ok && response.headers.get("content-type") == "application/json") {
            val jsonRpcResponse = unsafeCast<JsonRpcResponseJs>(response.jsonAsync().await()!!)
            when {
                method != HttpMethod.GET && jsonRpcResponse.id != jsonRpcRequest.id ->
                    throw Exception("Invalid response ID")

                jsonRpcResponse.error != null -> {
                    if (jsonRpcResponse.exceptionType == "dev.kilua.rpc.ServiceException") {
                        throw ServiceException(jsonRpcResponse.error!!)
                    } else if (jsonRpcResponse.exceptionJson != null) {
                        throw RpcSerialization.getJson()
                            .decodeFromString<AbstractServiceException>(jsonRpcResponse.exceptionJson!!)
                    } else {
                        throw Exception(jsonRpcResponse.error)
                    }
                }

                jsonRpcResponse.result != null -> jsonRpcResponse.result!!
                else -> throw Exception("Invalid response")
            }
        } else if (response.ok) {
            throw ContentTypeException("Invalid response content type: ${response.headers.get("content-type")}")
        } else {
            if (response.status.toInt() == HTTP_UNAUTHORIZED) {
                throw SecurityException(response.statusText)
            } else {
                throw Exception(response.statusText)
            }
        }
    }

    private fun getRequestMethod(httpMethod: HttpMethod): RequestMethod = when (httpMethod) {
        HttpMethod.GET -> unsafeCast("GET")
        HttpMethod.POST -> unsafeCast("POST")
        HttpMethod.PUT -> unsafeCast("PUT")
        HttpMethod.DELETE -> unsafeCast("DELETE")
        HttpMethod.OPTIONS -> unsafeCast("OPTIONS")
    }

    @OptIn(ExperimentalWasmJsInterop::class)
    private fun getRequestInit(
        method: HttpMethod,
        body: JsAny?,
        contentType: String
    ): RequestInit {
        val requestMethod = getRequestMethod(method)
        val headers = Headers()
        headers.append("Content-Type", contentType)
        headers.append("X-Requested-With", "XMLHttpRequest")
        return unsafeJso {
            asDynamic().method = requestMethod
            if (body != null) asDynamic().body = body
            asDynamic().headers = headers
            asDynamic().credentials = "include"
        }
    }
}
