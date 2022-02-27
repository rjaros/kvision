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

import kotlinx.serialization.Serializable

enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    OPTIONS,
    ;

    companion object {
        fun fromStringOrNull(txt: String): HttpMethod? =
            try {
                valueOf(txt)
            } catch (e: IllegalArgumentException) {
                null
            }
    }
}

@Serializable
abstract class AbstractServiceException: Exception("AbstractServiceException")

class ServiceException(message: String) : Exception(message)

@Serializable
data class RemoteOption(
    val value: String? = null,
    val text: String? = null,
    val className: String? = null,
    val subtext: String? = null,
    val icon: String? = null,
    val content: String? = null,
    val disabled: Boolean = false,
    val divider: Boolean = false
)

@Serializable
data class SimpleRemoteOption(
    val value: String,
    val text: String? = null
)

@Serializable
@Suppress("ConstructorParameterNaming")
data class RemoteData<T>(val data: List<T> = listOf(), val last_page: Int = 0)

@Serializable
data class RemoteFilter(val field: String, val type: String, val value: String?)

@Serializable
data class RemoteSorter(val field: String, val dir: String)
