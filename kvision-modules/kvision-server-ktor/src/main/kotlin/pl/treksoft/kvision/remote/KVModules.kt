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
import com.google.inject.Injector
import com.google.inject.Module
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.util.AttributeKey
import pl.treksoft.kvision.types.KV_JSON_DATE_FORMAT
import java.text.SimpleDateFormat

fun Application.kvisionInit(vararg modules: Module) {
    install(ContentNegotiation) {
        jackson {
            dateFormat = SimpleDateFormat(KV_JSON_DATE_FORMAT)
        }
    }
    routing {
        static("/") {
            resources("assets")
            defaultResource("assets/index.html")
        }
    }

    @Suppress("SpreadOperator")
    val injector = Guice.createInjector(MainModule(this), *modules)

    intercept(ApplicationCallPipeline.Features) {
        call.attributes.put(InjectorKey, injector.createChildInjector(CallModule(call)))
    }
}

val InjectorKey = AttributeKey<Injector>("injector")

val ApplicationCall.injector: Injector get() = attributes[InjectorKey]

class CallModule(private val call: ApplicationCall) : AbstractModule() {
    override fun configure() {
        bind(ApplicationCall::class.java).toInstance(call)
    }
}

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(Application::class.java).toInstance(application)
    }
}
