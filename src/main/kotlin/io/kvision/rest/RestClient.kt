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
package io.kvision.rest

import io.kvision.core.StringPair
import io.kvision.types.DateSerializer
import kotlinx.browser.window
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.w3c.dom.url.URL
import org.w3c.dom.url.URLSearchParams
import org.w3c.fetch.RequestInit
import kotlin.js.Date
import kotlin.js.Promise

enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    OPTIONS,
    HEAD
}

enum class ResponseBodyType {
    JSON,
    TEXT,
    BLOB,
    FORM_DATA,
    ARRAY_BUFFER,
    READABLE_STREAM
}

/**
 * A response wrapper
 */
data class Response<T>(val data: T, val textStatus: String, val response: org.w3c.fetch.Response)

const val XHR_ERROR: Short = 0
const val HTTP_BAD_REQUEST: Short = 400
const val HTTP_UNAUTHORIZED: Short = 401
const val HTTP_FORBIDDEN: Short = 403
const val HTTP_NOT_FOUND: Short = 404
const val HTTP_NOT_ALLOWED: Short = 405
const val HTTP_SERVER_ERROR: Short = 500
const val HTTP_NOT_IMPLEMENTED: Short = 501
const val HTTP_BAD_GATEWAY: Short = 502
const val HTTP_SERVICE_UNAVAILABLE: Short = 503

open class RemoteRequestException(val code: Short, val url: String, val method: HttpMethod, message: String) :
    Exception(message) {

    override fun toString(): String = "${this::class.simpleName}($code) [${method.name} $url] $message"

    companion object {
        fun create(code: Short, url: String, method: HttpMethod, message: String): RemoteRequestException =
            when (code) {
                XHR_ERROR -> XHRError(url, method, message)
                HTTP_BAD_REQUEST -> BadRequest(url, method, message)
                HTTP_UNAUTHORIZED -> Unauthorized(url, method, message)
                HTTP_FORBIDDEN -> Forbidden(url, method, message)
                HTTP_NOT_FOUND -> NotFound(url, method, message)
                HTTP_NOT_ALLOWED -> NotAllowed(url, method, message)
                HTTP_SERVER_ERROR -> ServerError(url, method, message)
                HTTP_NOT_IMPLEMENTED -> NotImplemented(url, method, message)
                HTTP_BAD_GATEWAY -> BadGateway(url, method, message)
                HTTP_SERVICE_UNAVAILABLE -> ServiceUnavailable(url, method, message)
                else -> RemoteRequestException(code, url, method, message)
            }
    }
}

/**
 * Code 0 does not represent any http status, it represent XHR error (e.g. network error, CORS failure).
 */
class XHRError(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(XHR_ERROR, url, method, message)

class BadRequest(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_BAD_REQUEST, url, method, message)

class Unauthorized(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_UNAUTHORIZED, url, method, message)

class Forbidden(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_FORBIDDEN, url, method, message)

class NotFound(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_NOT_FOUND, url, method, message)

class NotAllowed(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_NOT_ALLOWED, url, method, message)

class ServerError(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_SERVER_ERROR, url, method, message)

class NotImplemented(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_NOT_IMPLEMENTED, url, method, message)

class BadGateway(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_BAD_GATEWAY, url, method, message)

class ServiceUnavailable(url: String, method: HttpMethod, message: String) :
    RemoteRequestException(HTTP_SERVICE_UNAVAILABLE, url, method, message)

/**
 * A HTTP REST client
 */
open class RestClient(
    protected val module: SerializersModule? = null,
    protected val headers: (() -> List<StringPair>)? = null,
    protected val requestModifier: (RequestInit.() -> Unit)? = null
) {

    protected val Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        serializersModule = SerializersModule {
            contextual(Date::class, DateSerializer)
            module?.let { this.include(it) }
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @return a promise of the result
     */
    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    fun remoteCall(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        beforeSend: (() -> RequestInit)? = null,
    ): Promise<dynamic> {
        return remoteRequest(url, data, method, contentType, responseBodyType, beforeSend).then { it.data }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
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
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        beforeSend: (() -> RequestInit)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(url, data, method, contentType, responseBodyType, beforeSend).then { result: dynamic ->
            val transformed = if (transform != null) {
                transform(result)
            } else {
                result
            }
            @Suppress("UnsafeCastFromDynamic")
            Json.decodeFromString(deserializer, JSON.stringify(transformed))
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @return a promise of the result
     */
    fun <V : Any> remoteCall(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        beforeSend: (() -> RequestInit)? = null,
    ): Promise<dynamic> {
        val dataSer =
            if (method == HttpMethod.GET) data.toObj(serializer) else Json.encodeToString(serializer, data)
        return remoteCall(url, dataSer, method, contentType, responseBodyType, beforeSend)
    }


    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
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
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        beforeSend: (() -> RequestInit)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        val dataSer =
            if (method == HttpMethod.GET) data.toObj(serializer) else Json.encodeToString(serializer, data)
        return remoteCall(
            url,
            dataSer,
            method,
            contentType,
            responseBodyType,
            beforeSend
        ).then { result: dynamic ->
            val transformed = if (transform != null) {
                transform(result)
            } else {
                result
            }
            @Suppress("UnsafeCastFromDynamic")
            Json.decodeFromString(deserializer, JSON.stringify(transformed))
        }
    }

    /**
     * Helper inline function to automatically get deserializer for the result value with dynamic data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    inline fun <reified T : Any> call(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(url, data, serializer(), method, contentType, responseBodyType, beforeSend, transform)
    }

    /**
     * Helper inline function to automatically get serializer for the data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @return a promise of the result
     */
    inline fun <reified V : Any> call(
        url: String,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null
    ): Promise<dynamic> {
        return remoteCall(
            url,
            serializer(),
            data,
            method,
            contentType,
            responseBodyType,
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
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    inline fun <T : Any, reified V : Any> call(
        url: String,
        data: V,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(
            url,
            serializer(),
            data,
            deserializer,
            method,
            contentType,
            responseBodyType,
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
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    inline fun <reified T : Any, V : Any> call(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(
            url,
            serializer,
            data,
            serializer(),
            method,
            contentType,
            responseBodyType,
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
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the result
     */
    inline fun <reified T : Any, reified V : Any> call(
        url: String,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<T> {
        return remoteCall(
            url,
            serializer(),
            data,
            serializer(),
            method,
            contentType,
            responseBodyType,
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
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @return a promise of the response
     */
    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    fun remoteRequest(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        beforeSend: (() -> RequestInit)? = null
    ): Promise<Response<dynamic>> {
        val requestInit = beforeSend?.invoke() ?: RequestInit()
        requestInit.method = method.name
        if (method != HttpMethod.GET && method != HttpMethod.HEAD) {
            requestInit.body = if (contentType == "application/json") JSON.stringify(data) else data
        }
        val fetchUrl = if (method == HttpMethod.GET && data != null) {
            val uri = URL(url)
            uri.search = "?" + URLSearchParams(data).toString()
            uri
        } else {
            URL(url)
        }
        requestInit.headers = js("{}")
        requestInit.headers["Content-Type"] = contentType
        headers?.invoke()?.forEach {
            requestInit.headers[it.first] = it.second
        }
        this.requestModifier?.invoke(requestInit)
        return Promise { resolve, reject ->
            window.fetch(fetchUrl, requestInit).then { response ->
                if (response.ok) {
                    val statusText = response.statusText
                    if (responseBodyType == ResponseBodyType.READABLE_STREAM) {
                        resolve(Response(response.body, statusText, response))
                    } else {
                        val body = when (responseBodyType) {
                            ResponseBodyType.JSON -> response.json()
                            ResponseBodyType.TEXT -> response.text()
                            ResponseBodyType.BLOB -> response.blob()
                            ResponseBodyType.FORM_DATA -> response.formData()
                            ResponseBodyType.ARRAY_BUFFER -> response.arrayBuffer()
                            ResponseBodyType.READABLE_STREAM -> throw IllegalStateException() // not possible
                        }
                        body.then {
                            resolve(Response(it, statusText, response))
                        }.catch {
                            reject(
                                RemoteRequestException.create(
                                    XHR_ERROR,
                                    url,
                                    method,
                                    it.message ?: "Incorrect body type"
                                )
                            )
                        }
                    }
                } else {
                    reject(RemoteRequestException.create(response.status, url, method, response.statusText))
                }
            }.catch {
                reject(RemoteRequestException.create(XHR_ERROR, url, method, it.message ?: "Connection error"))
            }
        }
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
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
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        beforeSend: (() -> RequestInit)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(
            url,
            data,
            method,
            contentType,
            responseBodyType,
            beforeSend
        ).then { result: Response<dynamic> ->
            val transformed = if (transform != null) {
                transform(result.data)
            } else {
                result.data
            }
            Response(
                @Suppress("UnsafeCastFromDynamic")
                Json.decodeFromString(deserializer, JSON.stringify(transformed)),
                result.textStatus, result.response
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
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @return a promise of the response
     */
    fun <V : Any> remoteRequest(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        beforeSend: (() -> RequestInit)? = null
    ): Promise<Response<dynamic>> {
        val dataSer =
            if (method == HttpMethod.GET) data.toObj(serializer) else Json.encodeToString(serializer, data)
        return remoteRequest(url, dataSer, method, contentType, responseBodyType, beforeSend)
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param serializer for the data
     * @param data data to be sent
     * @param deserializer a deserializer for the result value
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
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
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        beforeSend: (() -> RequestInit)? = null,
        transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        val dataSer =
            if (method == HttpMethod.GET) data.toObj(serializer) else Json.encodeToString(serializer, data)
        return remoteRequest(
            url,
            dataSer,
            method,
            contentType,
            responseBodyType,
            beforeSend
        ).then { result: Response<dynamic> ->
            val transformed = if (transform != null) {
                transform(result.data)
            } else {
                result.data
            }
            Response(
                @Suppress("UnsafeCastFromDynamic")
                Json.decodeFromString(deserializer, JSON.stringify(transformed)),
                result.textStatus, result.response
            )
        }
    }

    /**
     * Helper inline function to automatically get deserializer for the result value with dynamic data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    inline fun <reified T : Any> request(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(url, data, serializer(), method, contentType, responseBodyType, beforeSend, transform)
    }

    /**
     * Helper inline function to automatically get serializer for the data.
     * @param url an URL address
     * @param data data to be sent
     * @param method a HTTP method
     * @param contentType a content type of the request
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @return a promise of the response
     */
    inline fun <reified V : Any> request(
        url: String,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
    ): Promise<Response<dynamic>> {
        return remoteRequest(
            url,
            serializer(),
            data,
            method,
            contentType,
            responseBodyType,
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
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    inline fun <T : Any, reified V : Any> request(
        url: String,
        data: V,
        deserializer: DeserializationStrategy<T>,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(
            url,
            serializer(),
            data,
            deserializer,
            method,
            contentType,
            responseBodyType,
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
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    inline fun <reified T : Any, V : Any> request(
        url: String,
        serializer: SerializationStrategy<V>,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(
            url,
            serializer,
            data,
            serializer(),
            method,
            contentType,
            responseBodyType,
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
     * @param responseBodyType type of the response data
     * @param beforeSend a function to set request parameters
     * @param transform a function to transform the result of the call
     * @return a promise of the response
     */
    inline fun <reified T : Any, reified V : Any> request(
        url: String,
        data: V,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        responseBodyType: ResponseBodyType = ResponseBodyType.JSON,
        noinline beforeSend: (() -> RequestInit)? = null,
        noinline transform: ((dynamic) -> dynamic)? = null
    ): Promise<Response<T>> {
        return remoteRequest(
            url,
            serializer(),
            data,
            serializer(),
            method,
            contentType,
            responseBodyType,
            beforeSend,
            transform
        )
    }

    /**
     * An extension function to convert Serializable object to JS dynamic object
     * @param serializer a serializer for T
     */
    protected fun <T> T.toObj(serializer: SerializationStrategy<T>): dynamic {
        return JSON.parse(Json.encodeToString(serializer, this))
    }

}
