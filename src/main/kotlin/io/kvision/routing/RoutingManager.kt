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

/**
 * Basic router interface.
 */
interface KVRouter {
    /**
     * Navigate to the given path.
     */
    fun kvNavigate(route: String)

    /**
     * Add handler for a route.
     */
    fun kvOn(route: String, handler: (Any) -> Unit): KVRouter

    /**
     * Remove handler.
     */
    fun kvOff(handler: (Any) -> Unit)

    /**
     * Resolve current route.
     */
    fun kvResolve()

    /**
     * Shutdown router.
     */
    fun kvDestroy()
}

/**
 * Base router factory interface.
 */
interface RouterFactory {
    /**
     * Initialize new router.
     */
    fun initRouter()

    /**
     * Shutdown current router.
     */
    fun shutdownRouter()

    /**
     * Return current router.
     */
    fun getRouter(): KVRouter
}

/**
 * Empty router implementation used when no routing module is initialized.
 */
class DummyRouter : KVRouter {
    override fun kvNavigate(route: String) {}
    override fun kvOn(route: String, handler: (Any) -> Unit): KVRouter = this
    override fun kvOff(handler: (Any) -> Unit) {}
    override fun kvResolve() {}
    override fun kvDestroy() {}
}

/**
 * Dummy router factory used when no routing module is initialized.
 */
class DummyRouterFactory : RouterFactory {
    private val router = DummyRouter()

    override fun getRouter(): KVRouter {
        return router
    }

    override fun initRouter() {}

    override fun shutdownRouter() {}
}

/**
 * The routing manager object, which holds the reference to the actual router factory.
 */
object RoutingManager {
    /**
     * Actual router factory.
     */
    var routerFactory: RouterFactory = DummyRouterFactory()

    /**
     * Initialize new router.
     */
    fun initRouter() {
        routerFactory.initRouter()
    }

    /**
     * Shutdown current router.
     */
    fun shutdownRouter() {
        routerFactory.shutdownRouter()
    }

    /**
     * Return current router.
     */
    fun getRouter(): KVRouter {
        return routerFactory.getRouter()
    }
}
