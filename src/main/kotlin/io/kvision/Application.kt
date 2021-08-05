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
package io.kvision

import io.kvision.panel.Root
import io.kvision.routing.RoutingManager
import kotlinx.browser.document
import kotlinx.browser.window

/**
 * Base class for KVision applications.
 */
abstract class Application {

    /**
     * Starting point for an application.
     */
    open fun start() {}

    /**
     * Starting point for an application with the state managed by Hot Module Replacement (HMR).
     * @param state Initial state for Hot Module Replacement (HMR).
     */
    open fun start(state: Map<String, Any>) {
        start()
    }

    /**
     * Ending point for an application.
     * @return final state for Hot Module Replacement (HMR).
     */
    open fun dispose(): Map<String, Any> {
        return mapOf()
    }
}

/**
 * Main function for creating KVision applications.
 * Initialize KVision modules.
 * @param builder application builder function
 * @param hot HMR module
 * @param moduleInitializer modules initializer objects
 * @param panelsCompatibilityMode whether to use component wrappers in Flex/H/V/Grid panels, set to *true* to use KVision 4 compatibility mode
 */
fun startApplication(
    builder: () -> Application,
    hot: Hot? = null,
    vararg moduleInitializer: ModuleInitializer,
    panelsCompatibilityMode: Boolean = false
) {
    @Suppress("UnsafeCastFromDynamic")
    if (window.asDynamic().__karma__) return

    KVManager.panelsCompatibilityMode = panelsCompatibilityMode
    moduleInitializer.forEach {
        it.initialize()
    }

    fun start(state: dynamic): Application {
        if (state?.appState != undefined) {
            RoutingManager.initRouter()
        }
        val application = builder()
        @Suppress("UnsafeCastFromDynamic")
        application.start(state?.appState ?: emptyMap())
        return application
    }

    var application: Application? = null

    val state: dynamic = hot?.let {
        it.accept()

        it.dispose { data ->
            Root.disposeAllRoots()
            RoutingManager.shutdownRouter()
            data.appState = application?.dispose()
            application = null
        }

        it.data
    }

    if (document.body != null) {
        application = start(state)
    } else {
        application = null
        document.addEventListener("DOMContentLoaded", { application = start(state) })
    }
}
