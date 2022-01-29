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

import kotlinx.serialization.modules.SerializersModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.core.io.Resource
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router
import javax.annotation.PostConstruct

/**
 * Default Spring Boot routes
 */
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

/**
 * Default Spring Boot handler
 */
@Component
open class KVHandler(val services: List<KVServiceManager<*>>, val applicationContext: ApplicationContext) {

    @Autowired(required = false)
    val serializersModules: List<SerializersModule>? = null

    private val threadLocalRequest = ThreadLocal<ServerRequest>()

    @PostConstruct
    open fun init() {
        services.forEach { it.deSerializer = kotlinxObjectDeSerializer(serializersModules) }
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    open fun serverRequest(): ServerRequest {
        return threadLocalRequest.get() ?: KVServerRequest()
    }

    open suspend fun handle(request: ServerRequest): ServerResponse {

        fun getHandler(): RequestHandler? {
            val springMethod = request.method() ?: return null
            val kvMethod = HttpMethod.fromStringOrNull(springMethod.name) ?: return null
            val routeUrl = request.path()
            return services.asSequence()
                .mapNotNull { it.routeMapRegistry.findHandler(kvMethod, routeUrl) }
                .firstOrNull()
        }
        return (getHandler() ?: return ServerResponse.notFound().buildAndAwait())(
            request,
            threadLocalRequest,
            applicationContext
        )
    }
}
