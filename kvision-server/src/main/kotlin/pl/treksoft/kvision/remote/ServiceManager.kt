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

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.runBlocking
import org.jooby.Status

/**
 * Multiplatform service manager.
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
actual open class ServiceManager<out T> actual constructor(val service: T?) {

    protected val routes: MutableList<JoobyServer.() -> Unit> = mutableListOf()
    val mapper = jacksonObjectMapper()

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     */
    protected actual inline fun <reified RET> bind(
        route: String,
        noinline function: T.(Request?) -> Deferred<RET>
    ) {
        routes.add({
            post("$SERVICE_PREFIX/$route") { req, res ->
                if (service != null) {
                    try {
                        res.send(runBlocking { function.invoke(service, req).await() })
                    } catch (e: Exception) {
                        e.printStackTrace()
                        res.status(500).send(e.message ?: "Error")
                    }
                } else {
                    res.status(Status.BAD_REQUEST)
                }
            }
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     */
    protected actual inline fun <reified PAR, reified RET> bind(
        route: String,
        noinline function: T.(PAR, Request?) -> Deferred<RET>
    ) {
        routes.add({
            post("$SERVICE_PREFIX/$route") { req, res ->
                val param = try {
                    req.body(PAR::class.java)
                } catch (e: Exception) {
                    null as PAR
                }
                if (service != null) {
                    try {
                        res.send(runBlocking { function.invoke(service, param, req).await() })
                    } catch (e: Exception) {
                        e.printStackTrace()
                        res.status(500).send(e.message ?: "Error")
                    }
                } else {
                    res.status(Status.BAD_REQUEST)
                }
            }
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified RET> bind(
        route: String,
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<RET>
    ) {
        routes.add({
            post("$SERVICE_PREFIX/$route") { req, res ->
                val str = req.body(String::class.java)
                val tree = mapper.readTree(str)
                if (tree.size() == 2 && service != null) {
                    val p1 = mapper.treeToValue(tree[0], PAR1::class.java)
                    val p2 = mapper.treeToValue(tree[1], PAR2::class.java)
                    try {
                        res.send(runBlocking { function.invoke(service, p1, p2, req).await() })
                    } catch (e: Exception) {
                        e.printStackTrace()
                        res.status(500).send(e.message ?: "Error")
                    }
                } else {
                    res.status(Status.BAD_REQUEST)
                }
            }
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET> bind(
        route: String,
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<RET>
    ) {
        routes.add({
            post("$SERVICE_PREFIX/$route") { req, res ->
                val str = req.body(String::class.java)
                val tree = mapper.readTree(str)
                if (tree.size() == 3 && service != null) {
                    val p1 = mapper.treeToValue(tree[0], PAR1::class.java)
                    val p2 = mapper.treeToValue(tree[1], PAR2::class.java)
                    val p3 = mapper.treeToValue(tree[2], PAR3::class.java)
                    try {
                        res.send(runBlocking { function.invoke(service, p1, p2, p3, req).await() })
                    } catch (e: Exception) {
                        e.printStackTrace()
                        res.status(500).send(e.message ?: "Error")
                    }
                } else {
                    res.status(Status.BAD_REQUEST)
                }
            }
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET> bind(
        route: String,
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<RET>
    ) {
        routes.add({
            post("$SERVICE_PREFIX/$route") { req, res ->
                val str = req.body(String::class.java)
                val tree = mapper.readTree(str)
                if (tree.size() == 4 && service != null) {
                    val p1 = mapper.treeToValue(tree[0], PAR1::class.java)
                    val p2 = mapper.treeToValue(tree[1], PAR2::class.java)
                    val p3 = mapper.treeToValue(tree[2], PAR3::class.java)
                    val p4 = mapper.treeToValue(tree[3], PAR4::class.java)
                    try {
                        res.send(runBlocking { function.invoke(service, p1, p2, p3, p4, req).await() })
                    } catch (e: Exception) {
                        e.printStackTrace()
                        res.status(500).send(e.message ?: "Error")
                    }
                } else {
                    res.status(Status.BAD_REQUEST)
                }
            }
        })
    }

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     */
    protected actual inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5, reified RET> bind(
        route: String,
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, PAR5, Request?) -> Deferred<RET>
    ) {
        routes.add({
            post("$SERVICE_PREFIX/$route") { req, res ->
                val str = req.body(String::class.java)
                val tree = mapper.readTree(str)
                if (tree.size() == 5 && service != null) {
                    val p1 = mapper.treeToValue(tree[0], PAR1::class.java)
                    val p2 = mapper.treeToValue(tree[1], PAR2::class.java)
                    val p3 = mapper.treeToValue(tree[2], PAR3::class.java)
                    val p4 = mapper.treeToValue(tree[3], PAR4::class.java)
                    val p5 = mapper.treeToValue(tree[4], PAR5::class.java)
                    try {
                        res.send(runBlocking { function.invoke(service, p1, p2, p3, p4, p5, req).await() })
                    } catch (e: Exception) {
                        e.printStackTrace()
                        res.status(500).send(e.message ?: "Error")
                    }
                } else {
                    res.status(Status.BAD_REQUEST)
                }
            }
        })
    }

    /**
     * Applies all defined routes to the given server.
     * @param k a Jooby server
     */
    actual fun applyRoutes(k: JoobyServer) {
        routes.forEach {
            it.invoke(k)
        }
    }

    /**
     * Returns the list of defined bindings.
     * Not used on the jvm platform.
     */
    actual fun getCalls(): Map<String, String> = mapOf()
}
