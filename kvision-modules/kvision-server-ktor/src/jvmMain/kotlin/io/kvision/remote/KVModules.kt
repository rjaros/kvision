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
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

private const val DEFAULT_INIT_STATIC_RESOURCES = true

/**
 * Initialization function for Ktor server.
 */
fun Application.kvisionInit(vararg modules: Module) = kvisionInit(DEFAULT_INIT_STATIC_RESOURCES, DefaultJson, *modules)

/**
 * Initialization function for Ktor server with custom JsonSerializer.
 * @param json custom or default JsonSerializer
 */
fun Application.kvisionInit(json: Json, vararg modules: Module) =
    kvisionInit(DEFAULT_INIT_STATIC_RESOURCES, json, *modules)

/**
 * Initialization function for Ktor server.
 * @param initStaticResources initialize default static resources
 */
fun Application.kvisionInit(initStaticResources: Boolean, vararg modules: Module) =
    kvisionInit(initStaticResources, DefaultJson, *modules)

/**
 * Initialization function for Ktor server.
 * @param initStaticResources initialize default static resources
 * @param json custom or default JsonSerializer
 */
fun Application.kvisionInit(initStaticResources: Boolean, json: Json, vararg modules: Module): Injector {
    install(ContentNegotiation) {
        json(json)
    }

    if (initStaticResources) initStaticResources()

    @Suppress("SpreadOperator")
    val injector = Guice.createInjector(MainModule(this), *modules)

    intercept(ApplicationCallPipeline.Plugins) {
        call.attributes.put(injectorKey, injector.createChildInjector(CallModule(call)))
    }
    return injector
}

/**
 * Initialize default static resources for Ktor server.
 */
fun Application.initStaticResources() {
    routing {
        staticResources("/", "assets", "index.html")
    }
}

val injectorKey = AttributeKey<Injector>("injector")

val ApplicationCall.injector: Injector get() = attributes[injectorKey]

internal class CallModule(private val call: ApplicationCall) : AbstractModule() {
    override fun configure() {
        bind(ApplicationCall::class.java).toInstance(call)
    }
}

internal class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(Application::class.java).toInstance(application)
    }
}

/**
 * @suppress internal class
 */
class WsSessionModule(private val webSocketSession: WebSocketServerSession) :
    AbstractModule() {
    override fun configure() {
        bind(WebSocketServerSession::class.java).toInstance(webSocketSession)
    }
}

/**
 * @suppress internal class
 */
class DummyWsSessionModule : AbstractModule() {
    override fun configure() {
        bind(WebSocketServerSession::class.java).toInstance(DummyWebSocketServerSession())
    }
}

/**
 * @suppress internal class
 */
@Suppress("UNUSED_PARAMETER", "OverridingDeprecatedMember")
class DummyWebSocketServerSession : WebSocketServerSession {
    override val call: ApplicationCall
        get() = throw UnsupportedOperationException()
    override val coroutineContext: CoroutineContext
        get() = throw UnsupportedOperationException()

    override val extensions: List<WebSocketExtension<*>>
        get() = throw UnsupportedOperationException()
    override val incoming: ReceiveChannel<Frame>
        get() = throw UnsupportedOperationException()
    override var masking: Boolean
        get() = throw UnsupportedOperationException()
        set(value) {
            throw UnsupportedOperationException()
        }
    override var maxFrameSize: Long
        get() = throw UnsupportedOperationException()
        set(value) {
            throw UnsupportedOperationException()
        }
    override val outgoing: SendChannel<Frame>
        get() = throw UnsupportedOperationException()

    override suspend fun flush() {
        throw UnsupportedOperationException()
    }

    @Deprecated("Use cancel() instead.", replaceWith = ReplaceWith("cancel()", "kotlinx.coroutines.cancel"))
    override fun terminate() {
        throw UnsupportedOperationException()
    }
}
