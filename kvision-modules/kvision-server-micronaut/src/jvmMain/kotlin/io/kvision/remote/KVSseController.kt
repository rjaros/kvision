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
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.sse.Event
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

/**
 * Controller for handling automatic routes.
 */
@Controller("/kvsse")
open class KVSseController {

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

    @ExecuteOn(TaskExecutors.IO)
    @Get("/{+path}", produces = [MediaType.TEXT_EVENT_STREAM])
    fun getSse(@PathVariable path: String?, request: HttpRequest<*>): Publisher<Event<String>> =
        handleSse("/rpcsse/$path", request)

    private fun handleSse(path: String, request: HttpRequest<*>): Publisher<Event<String>> {
        val handler = kvManagers.services.asSequence().mapNotNull {
            it.sseRequests[path]
        }.firstOrNull() ?: return Flux.empty()
        return handler(request, RequestHolder.threadLocalRequest, applicationContext)
    }
}
