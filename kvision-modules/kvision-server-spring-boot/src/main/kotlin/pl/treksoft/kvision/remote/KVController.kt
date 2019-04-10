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

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Controller for handling automatic routes.
 */
@Controller
open class KVController {

    @Autowired
    lateinit var services: List<KVServiceManager<*>>

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @RequestMapping(
        "/kv/**",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS]
    )
    open fun kVMapping(req: HttpServletRequest, res: HttpServletResponse) {
        val routeUrl = req.requestURI
        val handler = services.mapNotNull {
            when (req.method) {
                "GET" -> it.getRequests[routeUrl]
                "POST" -> it.postRequests[routeUrl]
                "PUT" -> it.putRequests[routeUrl]
                "DELETE" -> it.deleteRequests[routeUrl]
                "OPTIONS" -> it.optionsRequests[routeUrl]
                else -> null
            }
        }.firstOrNull()
        if (handler != null) {
            handler.invoke(req, res, applicationContext)
        } else {
            res.status = HttpServletResponse.SC_NOT_FOUND
        }
    }
}
