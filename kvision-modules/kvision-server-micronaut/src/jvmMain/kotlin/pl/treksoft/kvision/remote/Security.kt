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

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest

fun <T> HttpRequest<T>.matches(vararg services: KVServiceManager<*>): Boolean {
    return when (this.method) {
        HttpMethod.GET -> services.flatMap { it.getRequests.keys }.contains(this.uri.path)
        HttpMethod.POST -> services.flatMap { it.postRequests.keys }.contains(this.uri.path)
        HttpMethod.PUT -> services.flatMap { it.putRequests.keys }.contains(this.uri.path)
        HttpMethod.DELETE -> services.flatMap { it.deleteRequests.keys }.contains(this.uri.path)
        HttpMethod.OPTIONS -> services.flatMap { it.optionsRequests.keys }.contains(this.uri.path)
        else -> false
    }
}
