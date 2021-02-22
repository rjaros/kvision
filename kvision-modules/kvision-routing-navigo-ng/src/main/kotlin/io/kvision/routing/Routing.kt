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
import io.kvision.navigo.ResolveOptions
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
internal fun calculateOptions(useHash: Boolean, strategy: Strategy, noMatchWarning: Boolean): ResolveOptions {
    return obj {
        this.hash = useHash
        this.strategy = strategy.name
        this.noMatchWarning = noMatchWarning
    }.unsafeCast<ResolveOptions>()
}

/**
 * A helper class for Navigo 8+ JavaScript router.
 * @param root the root path of the application
 * @param useHash whether to use hash based routing
 * @param strategy a routing strategy
 * @param noMatchWarning do not show warnings when there is no matching route
 */
open class Routing(
    root: String? = null,
    useHash: Boolean = true,
    strategy: Strategy = Strategy.ONE,
    noMatchWarning: Boolean = false
) :
    Navigo(calculateRoot(root, useHash), calculateOptions(useHash, strategy, noMatchWarning)), KVRouter {

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
         */
        fun init(
            root: String? = null,
            useHash: Boolean = true,
            strategy: Strategy = Strategy.ONE,
            noMatchWarning: Boolean = false
        ) {
            RoutingManager.routerFactory = NavigoRouterFactory(root, useHash, strategy, noMatchWarning)
            routing = Routing(root, useHash, strategy, noMatchWarning)
        }
    }
}

/**
 * A router factory for Navigo 8+ routing module.
 * @param root the root path of the application
 * @param useHash whether to use hash based routing
 * @param strategy a routing strategy
 * @param noMatchWarning do not show warnings when there is no matching route
 */
class NavigoRouterFactory(
    val root: String? = null,
    val useHash: Boolean = true,
    val strategy: Strategy = Strategy.ONE,
    val noMatchWarning: Boolean = false
) : RouterFactory {

    override fun getRouter(): KVRouter {
        return routing
    }

    override fun initRouter() {
        routing = Routing(root, useHash, strategy, noMatchWarning)
    }

    override fun shutdownRouter() {
        routing.destroy()
    }
}

/**
 * Default Navigo 8+ JavaScript router.
 */
lateinit var routing: Routing
