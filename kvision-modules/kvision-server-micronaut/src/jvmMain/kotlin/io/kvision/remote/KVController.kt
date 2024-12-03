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

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Options
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject

/**
 * Controller for handling automatic routes.
 */
@Controller("/kv")
open class KVController {

    @Inject
    lateinit var kvManagers: KVManagers

    @Inject
    lateinit var applicationContext: ApplicationContext

    @PostConstruct
    fun init() {
        kvManagers.services.forEach {
            it.deSerializer = kotlinxObjectDeSerializer(kvManagers.serializersModules)
        }
    }

    @Get("/{+path}")
    suspend fun get(@PathVariable path: String?, request: HttpRequest<*>): HttpResponse<String> =
        handle(HttpMethod.GET, "/kv/$path", request)

    @Suppress("UNUSED_PARAMETER")
    @Post("/{+path}")
    suspend fun post(
        @PathVariable path: String?,
        request: HttpRequest<*>,
        @Body body: JsonRpcRequest
    ): HttpResponse<String> = handle(HttpMethod.POST, "/kv/$path", request)

    @Suppress("UNUSED_PARAMETER")
    @Put("/{+path}")
    suspend fun put(
        @PathVariable path: String?,
        request: HttpRequest<*>,
        @Body body: JsonRpcRequest
    ): HttpResponse<String> = handle(HttpMethod.PUT, "/kv/$path", request)

    @Suppress("UNUSED_PARAMETER")
    @Delete("/{+path}")
    suspend fun delete(
        @PathVariable path: String?,
        request: HttpRequest<*>,
        @Body body: JsonRpcRequest
    ): HttpResponse<String> = handle(HttpMethod.DELETE, "/kv/$path", request)

    @Suppress("UNUSED_PARAMETER")
    @Options("/{+path}")
    suspend fun options(
        @PathVariable path: String?,
        request: HttpRequest<*>,
        @Body body: JsonRpcRequest
    ): HttpResponse<String> = handle(HttpMethod.OPTIONS, "/kv/$path", request)

    private suspend fun handle(method: HttpMethod, path: String, request: HttpRequest<*>): HttpResponse<String> {
        val handler = kvManagers.services.asSequence().mapNotNull {
            it.routeMapRegistry.findHandler(method, path)
        }.firstOrNull() ?: return HttpResponse.notFound()
        return handler(
            request,
            RequestHolder.threadLocalRequest,
            ResponseMutatorHolder.threadLocalResponseMutator,
            applicationContext
        )
    }
}
