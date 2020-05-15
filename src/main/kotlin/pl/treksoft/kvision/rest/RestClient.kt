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
package pl.treksoft.kvision.rest

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.DynamicObjectParser
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.modules.serializersModuleOf
import kotlinx.serialization.serializer
import pl.treksoft.jquery.JQueryAjaxSettings
import pl.treksoft.jquery.JQueryXHR
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.types.DateSerializer
import pl.treksoft.kvision.utils.JSON
import pl.treksoft.kvision.utils.JSON.toObj
import pl.treksoft.kvision.utils.obj
import kotlin.js.Date
import kotlin.js.Promise
import kotlin.js.JSON as NativeJSON

enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    OPTIONS
}

/**
 * A response wrapper
 */
data class Response<T>(val data: T, val textStatus: String, val jqXHR: JQueryXHR)

/**
 * HTTP status unauthorized (401).
 */
const val HTTP_UNAUTHORIZED = 401

/**
 * An agent responsible for remote calls.
 */
open class RestClient {

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
        return remoteRequest(url, data, method, contentType, beforeSend).then { it.data }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    fun <T : Any> remoteCall(
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
            DynamicObjectParser(context = serializersModuleOf(Date::class, DateSerializer)).parse(
                transformed,
                deserializer
            )
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @return a promise of the result
     */
    fun <V : Any> remoteCall(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<dynamic> {
        val dataSer = if (method == HttpMethod.GET) data.toObj(serializer) else JSON.plain.stringify(serializer, data)
        return remoteCall(url, dataSer, method, contentType, beforeSend)
    }


    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    fun <T : Any, V : Any> remoteCall(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        val dataSer = if (method == HttpMethod.GET) data.toObj(serializer) else JSON.plain.stringify(serializer, data)
        return remoteCall(
            url,
            dataSer,
            method,
            contentType,
            beforeSend
        ).then { result: dynamic ->
            val transformed = if (transform != null) {
                transform(result)
            } else {
                result
            }
            DynamicObjectParser(context = serializersModuleOf(Date::class, DateSerializer)).parse(
                transformed,
                deserializer
            )
        }
    }

    /**
     * Helper inline function to automatically get deserializer for the result value with dynamic data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    @OptIn(ImplicitReflectionSerializer::class)
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
     * @param beforeSend a function to set request parameters
     * @return a promise of the result
     */
    @OptIn(ImplicitReflectionSerializer::class)
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
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    @OptIn(ImplicitReflectionSerializer::class)
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
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    @OptIn(ImplicitReflectionSerializer::class)
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
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    @OptIn(ImplicitReflectionSerializer::class)
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

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @return a promise of the response
     */
    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    fun remoteRequest(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<Response<dynamic>> {
        return Promise { resolve, reject ->
            jQuery.ajax(url, obj {
                this.contentType = if (contentType != "multipart/form-data") contentType else false
                this.data = data
                this.method = method.name
                this.processData = if (contentType != "multipart/form-data") undefined else false
                this.success =
                    { data: dynamic, textStatus: String, jqXHR: JQueryXHR ->
                        resolve(Response(data, textStatus, jqXHR))
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
                        reject(Exception(message))
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
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    fun <T : Any> remoteRequest(
        url: String,
        data: dynamic = null,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(url, data, method, contentType, beforeSend).then { result: Response<dynamic> ->
            val transformed = if (transform != null) {
                transform(result.data)
            } else {
                result.data
            }
            Response(
                DynamicObjectParser(context = serializersModuleOf(Date::class, DateSerializer)).parse(
                    transformed,
                    deserializer
                ), result.textStatus, result.jqXHR
            )
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @return a promise of the response
     */
    fun <V : Any> remoteRequest(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<Response<dynamic>> {
        val dataSer = if (method == HttpMethod.GET) data.toObj(serializer) else JSON.plain.stringify(serializer, data)
        return remoteRequest(url, dataSer, method, contentType, beforeSend)
    }


    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    fun <T : Any, V : Any> remoteRequest(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        val dataSer = if (method == HttpMethod.GET) data.toObj(serializer) else JSON.plain.stringify(serializer, data)
        return remoteRequest(
            url,
            dataSer,
            method,
            contentType,
            beforeSend
        ).then { result: Response<dynamic> ->
            val transformed = if (transform != null) {
                transform(result.data)
            } else {
                result.data
            }
            Response(
                DynamicObjectParser(context = serializersModuleOf(Date::class, DateSerializer)).parse(
                    transformed,
                    deserializer
                ), result.textStatus, result.jqXHR
            )
        }
    }

    /**
     * Helper inline function to automatically get deserializer for the result value with dynamic data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    @OptIn(ImplicitReflectionSerializer::class)
    inline fun <reified T : Any> request(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(url, data, T::class.serializer(), method, contentType, beforeSend, transform)
    }

    /**
     * Helper inline function to automatically get serializer for the data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param beforeSend a function to set request parameters
     * @return a promise of the response
     */
    @OptIn(ImplicitReflectionSerializer::class)
    inline fun <reified V : Any> request(
        url: String,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<Response<dynamic>> {
        return remoteRequest(
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
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    @OptIn(ImplicitReflectionSerializer::class)
    inline fun <T : Any, reified V : Any> request(
        url: String,
        data: V,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(
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
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    @OptIn(ImplicitReflectionSerializer::class)
    inline fun <reified T : Any, V : Any> request(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(
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
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    @OptIn(ImplicitReflectionSerializer::class)
    inline fun <reified T : Any, reified V : Any> request(
        url: String,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        noinline beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(
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
