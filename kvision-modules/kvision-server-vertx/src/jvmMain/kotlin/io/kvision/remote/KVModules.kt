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

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler
import kotlinx.serialization.modules.SerializersModule

const val KV_INJECTOR_KEY = "io.kvision.injector.key"

/**
 * Initialization function for Vert.x server.
 */
fun Vertx.kvisionInit(router: Router, vararg modules: Module) = kvisionInit(true, router, *modules)

/**
 * Initialization function for Vert.x server.
 * @param initStaticResources initialize default static resources
 */
fun Vertx.kvisionInit(
    initStaticResources: Boolean = true,
    router: Router,
    vararg modules: Module
): Injector {
    if (initStaticResources) router.initStaticResources()

    router.route("/kv/*").handler(BodyHandler.create(false))
    @Suppress("SpreadOperator")
    val injector = Guice.createInjector(MainModule(this), *modules)

    router.route("/kv/*").handler { rctx ->
        rctx.put(KV_INJECTOR_KEY, injector.createChildInjector(RoutingContextModule(rctx)))
        rctx.next()
    }
    return injector
}

/**
 * Initialization function for Vert.x server with support for WebSockets.
 */
fun Vertx.kvisionInit(
    router: Router,
    server: HttpServer,
    wsServiceManagers: List<KVServiceManager<*>> = emptyList(),
    serializersModules: List<SerializersModule>? = null,
    vararg modules: Module
) = kvisionInit(true, router, server, wsServiceManagers, serializersModules, *modules)

/**
 * Initialization function for Vert.x server with support for WebSockets.
 * @param initStaticResources initialize default static resources
 */
fun Vertx.kvisionInit(
    initStaticResources: Boolean = true,
    router: Router,
    server: HttpServer,
    wsServiceManagers: List<KVServiceManager<*>> = emptyList(),
    serializersModules: List<SerializersModule>? = null,
    vararg modules: Module
): Injector {
    if (initStaticResources) router.initStaticResources()

    router.route("/kv/*").handler(BodyHandler.create(false))

    @Suppress("SpreadOperator")
    val injector = Guice.createInjector(MainModule(this), *modules)

    router.route("/kv/*").handler { rctx ->
        rctx.put(KV_INJECTOR_KEY, injector.createChildInjector(RoutingContextModule(rctx)))
        rctx.next()
    }
    wsServiceManagers.forEach { serviceManager ->
        if (serviceManager.webSocketRequests.isNotEmpty()) {
            serviceManager.deSerializer = kotlinxObjectDeSerializer(serializersModules)
            server.webSocketHandler { webSocket ->
                serviceManager.webSocketRequests[webSocket.path()]?.let {
                    it(injector, webSocket)
                }
            }
            server.webSocketHandshakeHandler { serverWebSocketHandshake ->
                serviceManager.webSocketRequests[serverWebSocketHandshake.path()]?.let {
                    serverWebSocketHandshake.accept()
                } ?: serverWebSocketHandshake.reject()
            }
        }
    }
    return injector
}

/**
 * Initialize default static resources for Vert.x server.
 */
fun Router.initStaticResources() {
    route("/*").last().handler(StaticHandler.create())
}

internal class MainModule(private val vertx: Vertx) : AbstractModule() {
    override fun configure() {
        bind(Vertx::class.java).toInstance(vertx)
    }
}

internal class RoutingContextModule(private val rctx: RoutingContext) : AbstractModule() {
    override fun configure() {
        bind(RoutingContext::class.java).toInstance(rctx)
    }
}
