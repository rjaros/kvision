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

import org.springframework.http.HttpMethod
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers

/**
 * A function to gather paths for spring security matchers.
 */
@Suppress("SpreadOperator", "MaxLineLength")
fun ServerHttpSecurity.AuthorizeExchangeSpec.serviceMatchers(vararg services: KVServiceManager<*>): ServerHttpSecurity.AuthorizeExchangeSpec.Access {
    return this.matchers(*getServerWebExchangeMatcher(*services))
}

/**
 * A function to gather paths for spring security matchers.
 */
@Suppress("SpreadOperator")
fun serviceMatchers(vararg services: KVServiceManager<*>): ServerWebExchangeMatcher {
    return ServerWebExchangeMatchers.matchers(*getServerWebExchangeMatcher(*services))
}

/**
 * A function to gather paths for spring security matchers.
 */
@Suppress("SpreadOperator")
fun getServerWebExchangeMatcher(vararg services: KVServiceManager<*>): Array<ServerWebExchangeMatcher> =
    io.kvision.remote.HttpMethod
        .values()
        .asSequence()
        .mapNotNull { method ->
            val paths = services.asSequence().flatMap { service ->
                service.routeMapRegistry.asSequence(method).map(RouteMapEntry<*>::path)
            }.toList()
            if (paths.isEmpty()) {
                null
            } else {
                HttpMethod.resolve(method.name)?.let { springMethod ->
                    ServerWebExchangeMatchers.pathMatchers(springMethod, *paths.toTypedArray())
                }
            }
        }.toList().toTypedArray()
