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

import kotlinx.browser.window
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.w3c.dom.url.URLSearchParams
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise

internal external fun delete(p: dynamic): Boolean

/**
 * HTTP methods.
 */
enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    OPTIONS,
    HEAD
}

/**
 * HTTP response body types.
 */
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
data class RestResponse<T>(val data: T, val textStatus: String, val response: Response)

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

open class RemoteRequestException(
    val code: Short,
    val url: String,
    val method: HttpMethod,
    message: String,
    val response: Response? = null,
) :
    Exception(message) {

    override fun toString(): String = "${this::class.simpleName}($code) [${method.name} $url] $message"

    companion object {
        fun create(
            code: Short,
            url: String,
            method: HttpMethod,
            message: String,
            response: Response? = null
        ): RemoteRequestException =
            when (code) {
                XHR_ERROR -> XHRError(url, method, message, response)
                HTTP_BAD_REQUEST -> BadRequest(url, method, message, response)
                HTTP_UNAUTHORIZED -> Unauthorized(url, method, message, response)
                HTTP_FORBIDDEN -> Forbidden(url, method, message, response)
                HTTP_NOT_FOUND -> NotFound(url, method, message, response)
                HTTP_NOT_ALLOWED -> NotAllowed(url, method, message, response)
                HTTP_SERVER_ERROR -> ServerError(url, method, message, response)
                HTTP_NOT_IMPLEMENTED -> NotImplemented(url, method, message, response)
                HTTP_BAD_GATEWAY -> BadGateway(url, method, message, response)
                HTTP_SERVICE_UNAVAILABLE -> ServiceUnavailable(url, method, message, response)
                else -> RemoteRequestException(code, url, method, message, response)
            }
    }
}

/**
 * Code 0 does not represent any http status, it represent XHR error (e.g. network error, CORS failure).
 */
class XHRError(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(XHR_ERROR, url, method, message, response)

class BadRequest(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_BAD_REQUEST, url, method, message, response)

class Unauthorized(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_UNAUTHORIZED, url, method, message, response)

class Forbidden(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_FORBIDDEN, url, method, message, response)

class NotFound(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_NOT_FOUND, url, method, message, response)

class NotAllowed(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_NOT_ALLOWED, url, method, message, response)

class ServerError(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_SERVER_ERROR, url, method, message, response)

class NotImplemented(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_NOT_IMPLEMENTED, url, method, message, response)

class BadGateway(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_BAD_GATEWAY, url, method, message, response)

class ServiceUnavailable(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_SERVICE_UNAVAILABLE, url, method, message, response)

/**
 * REST Client configuration
 */
class RestClientConfig {
    /**
     * Optional serialization module.
     */
    var serializersModule: SerializersModule? = null

    /**
     * A function returning a list of HTTP headers.
     */
    var headers: (() -> List<Pair<String, String>>)? = null

    /**
     * A request filtering function.
     */
    var requestFilter: (RequestInit.() -> Unit)? = null

    /**
     * Base URL address.
     */
    var baseUrl: String? = null
}

/**
 * REST request configuration
 */
class RestRequestConfig<T : Any, V : Any> {
    /**
     * Data to send.
     */
    var data: V? = null

    /**
     * An HTTP method.
     */
    var method: HttpMethod = HttpMethod.GET

    /**
     * Request content type.
     */
    var contentType: String? = "application/json"

    /**
     * Response body type.
     */
    var responseBodyType: ResponseBodyType = ResponseBodyType.JSON

    /**
     * A function returning a list of HTTP headers.
     */
    var headers: (() -> List<Pair<String, String>>)? = null

    /**
     * A request filtering function.
     */
    var requestFilter: (RequestInit.() -> Unit)? = null

    /**
     * An optional transformation function, to modify result received from the server before deserialization.
     */
    var resultTransform: ((dynamic) -> dynamic)? = null

    /**
     * Request data serializer.
     */
    var serializer: SerializationStrategy<V>? = null

    /**
     * Response data deserializer.
     */
    var deserializer: DeserializationStrategy<T>? = null
}

/**
 * A HTTP REST client
 */
open class RestClient(block: (RestClientConfig.() -> Unit) = {}) {

    protected val restClientConfig = RestClientConfig().apply(block)

    protected val Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        restClientConfig.serializersModule?.let { serializersModule = it }
    }

    /**
     * Makes a http request to the server, returning the response object.
     * @param url an URL address
     * @param block an optional block for configuring the request
     * @return a promise of the response
     */
    fun <T : Any, V : Any> receive(
        url: String,
        block: RestRequestConfig<T, V>.() -> Unit = {}
    ): Promise<RestResponse<T>> {
        val restRequestConfig = RestRequestConfig<T, V>().apply(block)
        val requestInit = RequestInit()
        requestInit.method = restRequestConfig.method.name
        if (restRequestConfig.data != null &&
            restRequestConfig.method != HttpMethod.GET && restRequestConfig.method != HttpMethod.HEAD
        ) {
            requestInit.body = when (restRequestConfig.contentType) {
                "application/json" -> {
                    if (restRequestConfig.serializer != null) {
                        Json.encodeToString(restRequestConfig.serializer!!, restRequestConfig.data!!)
                    } else {
                        JSON.stringify(restRequestConfig.data!!)
                    }
                }
                "application/x-www-form-urlencoded" -> {
                    val dataSer = if (restRequestConfig.serializer != null) {
                        restRequestConfig.data!!.toObj(restRequestConfig.serializer!!)
                    } else {
                        restRequestConfig.data!!
                    }
                    URLSearchParams(removeNulls(dataSer)).toString()
                }
                else -> {
                    if (restRequestConfig.serializer != null) {
                        restRequestConfig.data!!.toObj(restRequestConfig.serializer!!)
                    } else {
                        restRequestConfig.data!!
                    }
                }
            }
        }
        val dataUrl = if (restRequestConfig.method == HttpMethod.GET && restRequestConfig.data != null) {
            val dataSer = if (restRequestConfig.serializer != null) {
                restRequestConfig.data!!.toObj(restRequestConfig.serializer!!)
            } else {
                restRequestConfig.data!!
            }
            url + "?" + URLSearchParams(removeNulls(dataSer)).toString()
        } else {
            url
        }
        val fetchUrl = if (restClientConfig.baseUrl != null) restClientConfig.baseUrl + dataUrl else dataUrl
        requestInit.headers = js("{}")
        if (restRequestConfig.contentType != null) {
            requestInit.headers["Content-Type"] = restRequestConfig.contentType
        }
        restClientConfig.headers?.invoke()?.forEach {
            requestInit.headers[it.first] = it.second
        }
        restRequestConfig.headers?.invoke()?.forEach {
            requestInit.headers[it.first] = it.second
        }
        restClientConfig.requestFilter?.invoke(requestInit)
        restRequestConfig.requestFilter?.invoke(requestInit)
        return Promise { resolve, reject ->
            window.fetch(fetchUrl, requestInit).then { response ->
                if (response.ok) {
                    val statusText = response.statusText
                    if (restRequestConfig.responseBodyType == ResponseBodyType.READABLE_STREAM) {
                        val transformed = if (restRequestConfig.resultTransform != null) {
                            restRequestConfig.resultTransform?.let { t -> t(response.body) }
                        } else {
                            response.body
                        }
                        val result = if (restRequestConfig.deserializer != null) {
                            Json.decodeFromString(restRequestConfig.deserializer!!, JSON.stringify(transformed))
                        } else {
                            transformed
                        }
                        resolve(RestResponse(result, statusText, response))
                    } else {
                        val body = when (restRequestConfig.responseBodyType) {
                            ResponseBodyType.JSON -> response.json()
                            ResponseBodyType.TEXT -> response.text()
                            ResponseBodyType.BLOB -> response.blob()
                            ResponseBodyType.FORM_DATA -> response.formData()
                            ResponseBodyType.ARRAY_BUFFER -> response.arrayBuffer()
                            ResponseBodyType.READABLE_STREAM -> throw IllegalStateException() // not possible
                        }
                        body.then {
                            val transformed = if (restRequestConfig.resultTransform != null) {
                                restRequestConfig.resultTransform?.let { t -> t(it) }
                            } else {
                                it
                            }
                            val result = if (restRequestConfig.deserializer != null) {
                                Json.decodeFromString(restRequestConfig.deserializer!!, JSON.stringify(transformed))
                            } else {
                                transformed
                            }
                            resolve(RestResponse(result, statusText, response))
                        }.catch {
                            reject(
                                RemoteRequestException.create(
                                    XHR_ERROR,
                                    fetchUrl,
                                    restRequestConfig.method,
                                    it.message ?: "Incorrect body type",
                                    response
                                )
                            )
                        }
                    }
                } else {
                    reject(
                        RemoteRequestException.create(
                            response.status,
                            fetchUrl,
                            restRequestConfig.method,
                            response.statusText,
                            response
                        )
                    )
                }
            }.catch {
                reject(
                    RemoteRequestException.create(
                        XHR_ERROR,
                        fetchUrl,
                        restRequestConfig.method,
                        it.message ?: "Connection error"
                    )
                )
            }
        }
    }

    /**
     * An extension function to convert Serializable object to JS dynamic object
     * @param serializer a serializer for T
     */
    protected fun <T> T.toObj(serializer: SerializationStrategy<T>): dynamic {
        return JSON.parse(Json.encodeToString(serializer, this))
    }

    /**
     * Removes all null values from JS object
     */
    protected fun removeNulls(data: dynamic): dynamic {
        for (key in js("Object").keys(data)) {
            if (data[key] == null) {
                delete(data[key])
            }
        }
        return data
    }
}

/**
 * Makes a http request to the server, returning the response object.
 * @param url an URL address
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
fun RestClient.requestDynamic(
    url: String,
    block: RestRequestConfig<dynamic, dynamic>.() -> Unit = {}
): Promise<RestResponse<dynamic>> {
    return receive<dynamic, dynamic>(url) {
        block.invoke(this)
    }
}

/**
 * Makes a http request to the server, returning the response object.
 * @param url an URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
inline fun <reified V : Any> RestClient.requestDynamic(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<dynamic, V>.() -> Unit = {}
): Promise<RestResponse<dynamic>> {
    return receive<dynamic, V>(url) {
        block.invoke(this)
        this.data = data
        this.serializer = serializer()
    }
}

/**
 * Makes a http request to the server, returning the response object.
 * @param url an URL address
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
inline fun <reified T : Any> RestClient.request(
    url: String,
    crossinline block: RestRequestConfig<T, dynamic>.() -> Unit = {}
): Promise<RestResponse<T>> {
    return receive<T, dynamic>(url) {
        block.invoke(this)
        this.deserializer = serializer()
    }
}

/**
 * Makes a http request to the server, returning the response object.
 * @param url an URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
inline fun <reified T : Any, reified V : Any> RestClient.request(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<T, V>.() -> Unit = {}
): Promise<RestResponse<T>> {
    return receive<T, V>(url) {
        block.invoke(this)
        this.data = data
        this.serializer = serializer()
        this.deserializer = serializer()
    }
}

/**
 * Makes a http request to the server, returning data directly.
 * @param url an URL address
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
fun RestClient.callDynamic(
    url: String,
    block: RestRequestConfig<dynamic, dynamic>.() -> Unit = {}
): Promise<dynamic> {
    return requestDynamic(url, block).then { it.data }
}

/**
 * Makes a http request to the server, returning data directly.
 * @param url an URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
inline fun <reified V : Any> RestClient.callDynamic(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<dynamic, V>.() -> Unit = {}
): Promise<dynamic> {
    return requestDynamic(url, data, block).then { it.data }
}

/**
 * Makes a http request to the server, returning data directly.
 * @param url an URL address
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
inline fun <reified T : Any> RestClient.call(
    url: String,
    crossinline block: RestRequestConfig<T, dynamic>.() -> Unit = {}
): Promise<T> {
    return request(url, block).then { it.data }
}

/**
 * Makes a http request to the server, returning data directly.
 * @param url an URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
inline fun <reified T : Any, reified V : Any> RestClient.call(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<T, V>.() -> Unit = {}
): Promise<T> {
    return request(url, data, block).then { it.data }
}

/**
 * Makes a http POST request to the server, returning data directly.
 * @param url an URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
inline fun <reified V : Any> RestClient.postDynamic(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<dynamic, V>.() -> Unit = {}
): Promise<dynamic> {
    return requestDynamic(url, data) {
        block.invoke(this)
        method = HttpMethod.POST
    }.then { it.data }
}

/**
 * Makes a http POST request to the server, returning data directly.
 * @param url an URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return a promise of the data
 */
inline fun <reified T : Any, reified V : Any> RestClient.post(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<T, V>.() -> Unit = {}
): Promise<T> {
    return request<T, V>(url, data) {
        block.invoke(this)
        method = HttpMethod.POST
    }.then { it.data }
}
