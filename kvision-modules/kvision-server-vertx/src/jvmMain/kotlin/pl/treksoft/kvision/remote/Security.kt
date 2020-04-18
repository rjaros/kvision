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

import io.vertx.core.Handler
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

fun Router.serviceRoute(service: KVServiceManager<*>, handler: Handler<RoutingContext>) {
    service.getRequests.keys.forEach {
        this.route(HttpMethod.GET, it).handler(handler)
    }
    service.postRequests.keys.forEach {
        this.route(HttpMethod.POST, it).handler(handler)
    }
    service.putRequests.keys.forEach {
        this.route(HttpMethod.PUT, it).handler(handler)
    }
    service.deleteRequests.keys.forEach {
        this.route(HttpMethod.DELETE, it).handler(handler)
    }
    service.optionsRequests.keys.forEach {
        this.route(HttpMethod.OPTIONS, it).handler(handler)
    }
}
