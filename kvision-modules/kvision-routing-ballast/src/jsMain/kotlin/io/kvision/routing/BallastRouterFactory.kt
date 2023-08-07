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
 * A router factory for Ballast routing module.
 * @param root the root path of the application
 * @param useHash whether to use hash based routing
 * @param notFoundHandler a function called when no route matches
 */
class BallastRouterFactory(
    val root: String? = null,
    val useHash: Boolean = true,
    val notFoundHandler: ((String) -> Any)? = null,
) : RouterFactory {

    private var routing: KVRouter? = null

    override fun getRouter(): KVRouter {
        return routing ?: throw IllegalStateException("Routing not initialized")
    }

    override fun initRouter() {
        if (routing == null) {
            routing = Routing(root, useHash, notFoundHandler)
        }
    }

    override fun shutdownRouter() {
        routing?.kvDestroy()
        routing = null
    }
}
