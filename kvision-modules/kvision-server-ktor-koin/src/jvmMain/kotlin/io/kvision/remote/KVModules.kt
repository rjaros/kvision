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

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.mp.KoinPlatformTools

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

internal val scopeKey = AttributeKey<String>("KoinScopeId")

/**
 * Initialization function for Ktor server.
 * @param initStaticResources initialize default static resources
 * @param json custom or default JsonSerializer
 */
fun Application.kvisionInit(initStaticResources: Boolean, json: Json, vararg modules: Module) {
    install(ContentNegotiation) {
        json(json)
    }

    if (initStaticResources) initStaticResources()

    install(Koin) {
        slf4jLogger()
        modules(ScopeManager.applicationModule(this@kvisionInit), *modules)
    }

    intercept(ApplicationCallPipeline.Plugins) {
        val scopeId = KoinPlatformTools.generateId()
        this@kvisionInit.getKoin().createScope<ApplicationCall>(scopeId)
        call.attributes.put(scopeKey, scopeId)
    }

    intercept(ApplicationCallPipeline.Fallback) {
        val scopeId = call.attributes[scopeKey]
        ScopeManager.applicationCalls.remove(scopeId)
        this@kvisionInit.getKoin().getScope(scopeId).close()
    }

}

/**
 * Initialize default static resources for Ktor server.
 */
fun Application.initStaticResources() {
    routing {
        static("/") {
            resources("assets")
            defaultResource("assets/index.html")
        }
    }
}
