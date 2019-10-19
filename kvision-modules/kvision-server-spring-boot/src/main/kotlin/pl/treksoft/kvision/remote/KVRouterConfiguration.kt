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

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.io.Resource
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

@Configuration
open class KVRouterConfiguration {
    @Value("classpath:/public/index.html")
    private lateinit var indexHtml: Resource

    @Bean
    open fun kvRoutes(kvHandler: KVHandler) = coRouter {
        GET("/kv/**", kvHandler::handle)
        POST("/kv/**", kvHandler::handle)
        PUT("/kv/**", kvHandler::handle)
        DELETE("/kv/**", kvHandler::handle)
        OPTIONS("/kv/**", kvHandler::handle)
    }

    @Bean
    open fun indexRouter() = router {
        GET("/") {
            ok().contentType(TEXT_HTML).bodyValue(indexHtml)
        }
    }
}

@Component
open class KVHandler(var services: List<KVServiceManager<*>>, var applicationContext: ApplicationContext) {

    open suspend fun handle(request: ServerRequest): ServerResponse {
        val routeUrl = request.path()
        val handler = services.mapNotNull {
            when (request.method()?.name) {
                "GET" -> it.getRequests[routeUrl]
                "POST" -> it.postRequests[routeUrl]
                "PUT" -> it.putRequests[routeUrl]
                "DELETE" -> it.deleteRequests[routeUrl]
                "OPTIONS" -> it.optionsRequests[routeUrl]
                else -> null
            }
        }.firstOrNull()
        return if (handler != null) {
            handler(request, applicationContext as GenericApplicationContext)
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }
}
