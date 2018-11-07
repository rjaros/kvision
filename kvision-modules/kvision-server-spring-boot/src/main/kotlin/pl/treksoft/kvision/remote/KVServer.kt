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

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import org.pac4j.core.context.J2EContext
import org.pac4j.core.context.session.J2ESessionStore
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.profile.ProfileManager
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import kotlinx.coroutines.async as coroutinesAsync

/**
 * A Spring boot based server.
 */
actual open class KVServer(val services: List<SpringServiceManager<*>>)

/**
 * A server request.
 */
actual typealias Request = HttpServletRequest

/**
 * A user profile.
 */
actual typealias Profile = CommonProfile

/**
 * A helper extension function for asynchronous processing.
 */
fun <RESP> async(block: () -> RESP): Deferred<RESP> =
    GlobalScope.coroutinesAsync {
        block()
    }

/**
 * A helper extension function for asynchronous processing with user profile.
 */
fun <RESP> asyncAuth(block: (Profile) -> RESP): Deferred<RESP> {
    val profile = try {
        val requestAttributes = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes)
        val req = requestAttributes.request
        val resp = requestAttributes.response
        ProfileManager<CommonProfile>(J2EContext(req, resp, J2ESessionStore())).get(true).get()
    } catch (e: Exception) {
        null
    }
    return profile?.let {
        GlobalScope.coroutinesAsync {
            block(it)
        }
    } ?: throw IllegalStateException("Profile not set!")
}
