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

import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.renderCurrentDestination
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Auto-detect root path of the application.
 */
internal fun calculateRoot(root: String?, useHash: Boolean): String {
    return root ?: if (useHash) "/" else window.location.pathname
}

/**
 * A helper class for Ballast router.
 * @param root the root path of the application
 * @param useHash whether to use hash based routing
 * @param notFoundHandler a function called when no route matches
 */
open class Routing(
    root: String? = null,
    useHash: Boolean = true,
    notFoundHandler: ((String) -> Any)? = null,
) : KVRouter {

    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val routingTable = KVRoutingTable()
    private val routerViewModel =
        KVRouterViewModel(routingTable, KVRoute("/"), calculateRoot(root, useHash), useHash, coroutineScope)

    init {
        routerViewModel.observeStates().onEach {
            it.backstack.renderCurrentDestination(
                route = { route: KVRoute ->
                    route.handler?.let { handler -> handler(route) }
                },
                notFound = {
                    notFoundHandler?.let { handler -> handler(it) }
                },
            )
        }.launchIn(coroutineScope)
    }

    override fun kvNavigate(route: String) {
        routerViewModel.trySend(RouterContract.Inputs.GoToDestination(route))
    }

    override fun kvOn(route: String, handler: (Any) -> Unit): KVRouter {
        routingTable.addRoute(KVRoute(route, handler))
        return this
    }

    override fun kvOff(handler: (Any) -> Unit) {
        routingTable.removeRouteByHandler(handler)
    }

    override fun kvResolve() {
        // no-op
    }

    override fun kvDestroy() {
        coroutineScope.cancel()
    }

    companion object {
        /**
         * Initialize Ballast routing module.
         * @param root the root path of the application
         * @param useHash whether to use hash based routing
         * @param notFoundHandler a function called when no route matches
         */
        fun init(
            root: String? = null,
            useHash: Boolean = true,
            notFoundHandler: ((String) -> Any)? = null,
        ): Routing {
            RoutingManager.routerFactory = BallastRouterFactory(root, useHash, notFoundHandler)
            RoutingManager.routerFactory.initRouter()
            return RoutingManager.routerFactory.getRouter().unsafeCast<Routing>()
        }
    }
}
