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

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Module
import io.javalin.Javalin
import io.javalin.http.Context
import io.javalin.websocket.WsContext

const val KV_INJECTOR_KEY = "pl.treksoft.kvision.injector.key"

/**
 * Initialization function for Javalin server.
 */
fun Javalin.kvisionInit(vararg modules: Module) {
    config.addStaticFiles("/assets")

    @Suppress("SpreadOperator")
    val injector = Guice.createInjector(MainModule(this), *modules)

    before { ctx ->
        ctx.attribute(KV_INJECTOR_KEY, injector.createChildInjector(ContextModule(ctx), WsContextModule(KVWsContext())))
    }
    wsBefore { ws ->
        ws.onConnect { ctx ->
            ctx.attribute(
                KV_INJECTOR_KEY,
                injector.createChildInjector(ContextModule(KVContext()), WsContextModule(ctx))
            )
        }
    }
}

internal class MainModule(private val javalin: Javalin) : AbstractModule() {
    override fun configure() {
        bind(Javalin::class.java).toInstance(javalin)
    }
}

internal class ContextModule(private val ctx: Context) : AbstractModule() {
    override fun configure() {
        bind(Context::class.java).toInstance(ctx)
    }
}

internal class WsContextModule(private val wsCtx: WsContext) : AbstractModule() {
    override fun configure() {
        bind(WsContext::class.java).toInstance(wsCtx)
    }
}
