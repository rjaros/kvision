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

import org.springframework.http.HttpMethod
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers

/**
 * A function to gather paths for spring security matchers.
 */
fun ServerHttpSecurity.AuthorizeExchangeSpec.serviceMatchers(vararg services: KVServiceManager<*>): ServerHttpSecurity.AuthorizeExchangeSpec.Access {
    val matchers = mutableListOf<ServerWebExchangeMatcher>()
    val getPaths = services.flatMap { it.getRequests.keys }.toTypedArray()
    if (getPaths.isNotEmpty()) matchers.add(ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, *getPaths))
    val postPaths = services.flatMap { it.postRequests.keys }.toTypedArray()
    if (postPaths.isNotEmpty()) matchers.add(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, *postPaths))
    val putPaths = services.flatMap { it.putRequests.keys }.toTypedArray()
    if (putPaths.isNotEmpty()) matchers.add(ServerWebExchangeMatchers.pathMatchers(HttpMethod.PUT, *putPaths))
    val deletePaths = services.flatMap { it.deleteRequests.keys }.toTypedArray()
    if (deletePaths.isNotEmpty()) matchers.add(ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE, *deletePaths))
    val optionsPaths = services.flatMap { it.optionsRequests.keys }.toTypedArray()
    if (optionsPaths.isNotEmpty()) matchers.add(
        ServerWebExchangeMatchers.pathMatchers(
            HttpMethod.OPTIONS,
            *optionsPaths
        )
    )
    return this.matchers(*matchers.toTypedArray())
}
