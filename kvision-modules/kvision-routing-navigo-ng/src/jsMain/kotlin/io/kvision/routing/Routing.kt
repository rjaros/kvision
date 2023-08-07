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

package io.kvision.routing

import io.kvision.navigo.Navigo
import io.kvision.navigo.RouterOptions
import io.kvision.utils.obj
import kotlinx.browser.window

/**
 * Routing strategy.
 */
enum class Strategy {
    ONE,
    ALL
}

/**
 * Auto-detect root path of the application.
 */
internal fun calculateRoot(root: String?, useHash: Boolean): String {
    return root ?: if (useHash) "/" else window.location.pathname
}

/**
 * Generate ResolveOptions JS native object.
 */
internal fun calculateOptions(
    useHash: Boolean,
    strategy: Strategy,
    noMatchWarning: Boolean,
    linksSelector: String?
): RouterOptions {
    return obj {
        this.hash = useHash
        this.strategy = strategy.name
        this.noMatchWarning = noMatchWarning
        if (linksSelector != null) this.linksSelector = linksSelector
    }.unsafeCast<RouterOptions>()
}

/**
 * A helper class for Navigo 8+ JavaScript router.
 * @param root the root path of the application
 * @param useHash whether to use hash based routing
 * @param strategy a routing strategy
 * @param noMatchWarning do not show warnings when there is no matching route
 * @param linksSelector CSS links selector
 */
open class Routing(
    root: String? = null,
    useHash: Boolean = true,
    strategy: Strategy = Strategy.ONE,
    noMatchWarning: Boolean = false,
    linksSelector: String? = null
) :
    Navigo(calculateRoot(root, useHash), calculateOptions(useHash, strategy, noMatchWarning, linksSelector)), KVRouter {

    override fun kvNavigate(route: String) {
        navigate(route)
    }

    override fun kvOn(route: String, handler: (Any) -> Unit): KVRouter {
        on(route, handler)
        return this
    }

    override fun kvOff(handler: (Any) -> Unit) {
        off(handler)
    }

    override fun kvResolve() {
        resolve()
    }

    override fun kvDestroy() {
        destroy()
    }

    companion object {
        /**
         * Initialize Navigo 8+ routing module.
         * @param root the root path of the application
         * @param useHash whether to use hash based routing
         * @param strategy a routing strategy
         * @param noMatchWarning do not show warnings when there is no matching route
         * @param linksSelector CSS links selector
         */
        fun init(
            root: String? = null,
            useHash: Boolean = true,
            strategy: Strategy = Strategy.ONE,
            noMatchWarning: Boolean = false,
            linksSelector: String? = null
        ): Routing {
            RoutingManager.routerFactory = NavigoRouterFactory(root, useHash, strategy, noMatchWarning, linksSelector)
            RoutingManager.initRouter()
            return RoutingManager.getRouter().unsafeCast<Routing>()
        }
    }
}

/**
 * A router factory for Navigo 8+ routing module.
 * @param root the root path of the application
 * @param useHash whether to use hash based routing
 * @param strategy a routing strategy
 * @param noMatchWarning do not show warnings when there is no matching route
 * @param linksSelector CSS links selector
 */
class NavigoRouterFactory(
    val root: String? = null,
    val useHash: Boolean = true,
    val strategy: Strategy = Strategy.ONE,
    val noMatchWarning: Boolean = false,
    val linksSelector: String? = null
) : RouterFactory {

    private var routing: Routing? = null

    override fun getRouter(): KVRouter {
        return routing ?: throw IllegalStateException("Routing not initialized")
    }

    override fun initRouter() {
        if (routing == null) {
            routing = Routing(root, useHash, strategy, noMatchWarning, linksSelector)
        }
    }

    override fun shutdownRouter() {
        routing?.destroy()
        routing = null
    }
}
