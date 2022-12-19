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

/**
 * A helper class for Navigo JavaScript router.
 * @param root the root path of the application
 * @param useHash whether to use hash based routing
 * @param hash a string used as a hash marker
 */
open class Routing(
    root: String? = null,
    useHash: Boolean = true,
    hash: String = "#"
) : Navigo(root, useHash, hash), KVRouter {

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
         * Initialize Navigo routing module.
         * @param root the root path of the application
         * @param useHash whether to use hash based routing
         * @param hash a string used as a hash marker
         */
        fun init(
            root: String? = null,
            useHash: Boolean = true,
            hash: String = "#"
        ) {
            RoutingManager.routerFactory = NavigoRouterFactory(root, useHash, hash)
            routing = Routing(root, useHash, hash).also { it.resolve() }
        }
    }
}

/**
 * A router factory for Navigo routing module.
 * @param root the root path of the application
 * @param useHash whether to use hash based routing
 * @param hash a string used as a hash marker
 */
class NavigoRouterFactory(
    val root: String? = null,
    val useHash: Boolean = true,
    val hash: String = "#"
) : RouterFactory {

    override fun getRouter(): KVRouter {
        return routing
    }

    override fun initRouter() {
        routing = Routing(root, useHash, hash)
    }

    override fun shutdownRouter() {
        routing.destroy()
    }
}

/**
 * Default Navigo JavaScript router.
 */
lateinit var routing: Routing
