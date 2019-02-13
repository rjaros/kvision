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

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.DynamicObjectParser
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer
import kotlinx.serialization.stringify
import pl.treksoft.jquery.JQueryAjaxSettings
import pl.treksoft.jquery.JQueryXHR
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.utils.JSON
import pl.treksoft.kvision.utils.JSON.toObj
import pl.treksoft.kvision.utils.obj
import kotlin.js.Promise

/**
 * HTTP status unauthorized (401).
 */
const val HTTP_UNAUTHORIZED = 401

/**
 * An agent responsible for remote calls.
 */
open class CallAgent {

    private var counter = 1

    /**
     * Makes an JSON-RPC call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @return a promise of the result
     */
    @UseExperimental(ImplicitReflectionSerializer::class)
    @Suppress("UnsafeCastFromDynamic")
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
            jQuery.ajax(url, obj {
                this.contentType = "application/json"
                this.data = jsonData
                this.method = method.name
                this.success =
                    { data: dynamic, _: Any, _: Any ->
                        when {
                            data.id != jsonRpcRequest.id -> reject(Exception("Invalid response ID"))
                            data.error != null -> reject(Exception(data.error.toString()))
                            data.result != null -> resolve(data.result)
                            else -> reject(Exception("Invalid response"))
                        }
                    }
                this.error =
                    { xhr: JQueryXHR, _: String, errorText: String ->
                        val message = if (xhr.responseJSON != null && xhr.responseJSON != undefined) {
                            xhr.responseJSON.toString()
                        } else {
                            errorText
                        }
                        if (xhr.status.toInt() == HTTP_UNAUTHORIZED) {
                            reject(SecurityException(message))
                        } else {
                            reject(Exception(message))
                        }
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
     * @param beforeSend a content type of the request
     * @return a promise of the result
     */
    @Suppress("UnsafeCastFromDynamic")
    fun remoteCall(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<dynamic> {
        return Promise { resolve, reject ->
            jQuery.ajax(url, obj {
                this.contentType = contentType
                this.data = data
                this.method = method.name
                this.success =
                    { data: dynamic, _: Any, _: Any ->
                        resolve(data)
                    }
                this.error =
                    { xhr: JQueryXHR, _: String, errorText: String ->
                        val message = if (xhr.responseJSON != null && xhr.responseJSON != undefined) {
                            xhr.responseJSON.toString()
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
            })
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a content type of the request
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    fun <T> remoteCall(
        url: String,
        data: dynamic = null,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(url, data, method, contentType, beforeSend).then { result: dynamic ->
            val transformed = if (transform != null) {
                transform(result)
            } else {
                result
            }
            DynamicObjectParser().parse(transformed, deserializer)
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a content type of the request
     * @return a promise of the result
     */
    fun <V> remoteCall(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<dynamic> {
        return remoteCall(url, data.toObj(serializer), method, contentType, beforeSend)
    }


    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a content type of the request
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    fun <T, V> remoteCall(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(url, data.toObj(serializer), method, contentType, beforeSend).then { result: dynamic ->
            val transformed = if (transform != null) {
                transform(result)
            } else {
                result
            }
            DynamicObjectParser().parse(transformed, deserializer)
        }
    }

    /**
     * Helper inline function to automatically get deserializer for the result value with dynamic data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a content type of the request
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    @UseExperimental(ImplicitReflectionSerializer::class)
    inline fun <reified T : Any> call(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(url, data, T::class.serializer(), method, contentType, beforeSend, transform)
    }

    /**
     * Helper inline function to automatically get serializer for the data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a content type of the request
     * @return a promise of the result
     */
    @UseExperimental(ImplicitReflectionSerializer::class)
    inline fun <reified V : Any> call(
        url: String,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<dynamic> {
        return remoteCall(
            url,
            V::class.serializer(),
            data,
            method,
            contentType,
            beforeSend
        )
    }

    /**
     * Helper inline function to automatically get serializer for the data.
     * @param url an URL address
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a content type of the request
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    @UseExperimental(ImplicitReflectionSerializer::class)
    inline fun <T : Any, reified V : Any> call(
        url: String,
        data: V,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(
            url,
            V::class.serializer(),
            data,
            deserializer,
            method,
            contentType,
            beforeSend,
            transform
        )
    }

    /**
     * Helper inline function to automatically deserializer for the result value with typed data.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a content type of the request
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    @UseExperimental(ImplicitReflectionSerializer::class)
    inline fun <reified T : Any, V : Any> call(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(
            url,
            serializer,
            data,
            T::class.serializer(),
            method,
            contentType,
            beforeSend,
            transform
        )
    }

    /**
     * Helper inline function to automatically get serializer for the data and deserializer for the result value.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a content type of the request
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    @UseExperimental(ImplicitReflectionSerializer::class)
    inline fun <reified T : Any, reified V : Any> call(
        url: String,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(
            url,
            V::class.serializer(),
            data,
            T::class.serializer(),
            method,
            contentType,
            beforeSend,
            transform
        )
    }
}
