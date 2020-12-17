/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
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

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

const val GET_PATH_A = "get path A"
const val GET_PATH_B = "get path B"
const val POST_PATH = "post path"
const val GET_HANDLER_A = "get handler A"
const val GET_HANDLER_B = "get handler B"
const val POST_HANDLER = "post handler"

class RouteMapRegistryKtTest {
    private lateinit var registry: RouteMapRegistry<String>

    @BeforeMethod
    fun setUp() {
        registry = createRouteMapRegistry()
        registry.addRoute(HttpMethod.GET, GET_PATH_A, GET_HANDLER_A)
        registry.addRoute(HttpMethod.GET, GET_PATH_B, GET_HANDLER_B)
        registry.addRoute(HttpMethod.POST, POST_PATH, POST_HANDLER)
    }

    @Test(dataProvider = "provide_method_path_expected")
    fun findHandler_findsExpectedHandler(method: HttpMethod, path: String, expected: String?) {
        // execution
        val actual = registry.findHandler(method, path)

        // evaluation
        assertThat(actual, equalTo(expected))
    }

    @DataProvider
    fun provide_method_path_expected(): Array<Array<Any?>> {
        fun args(method: HttpMethod, path: String, expected: String?): Array<Any?> = arrayOf(method, path, expected)

        return arrayOf(
            // Get existing path
            args(HttpMethod.GET, GET_PATH_A, GET_HANDLER_A),
            // Get path that does not exist at all (should return null)
            args(HttpMethod.OPTIONS, "does not exist", null),
            // Get path that exists with different method (should return null)
            args(HttpMethod.GET, POST_PATH, null),
        )
    }

    @Test
    fun asSequence_returnsAllEntries() {
        // execution
        val actual = registry.asSequence().toList()

        // evaluation
        assertThat(actual, containsInAnyOrder(
            RouteMapEntry(HttpMethod.GET, GET_PATH_A, GET_HANDLER_A),
            RouteMapEntry(HttpMethod.GET, GET_PATH_B, GET_HANDLER_B),
            RouteMapEntry(HttpMethod.POST, POST_PATH, POST_HANDLER),
        ))
    }

    @Test(dataProvider = "provide_method_expected")
    fun forEach_runsForEachHandlerWithRequestedMethod(method: HttpMethod, expected: Array<RouteMapEntry<String>>) {
        // execution
        val actual = registry.asSequence(method).toList()

        // evaluation
        assertThat(actual, containsInAnyOrder(*expected))
    }

    @DataProvider
    fun provide_method_expected(): Array<Array<Any>> {
        fun args(method: HttpMethod, expected: Array<RouteMapEntry<String>>): Array<Any> = arrayOf(method, expected)

        return arrayOf(
            args(
                HttpMethod.GET,
                arrayOf(
                    RouteMapEntry(HttpMethod.GET, GET_PATH_A, GET_HANDLER_A),
                    RouteMapEntry(HttpMethod.GET, GET_PATH_B, GET_HANDLER_B)
                )
            ),
            args(HttpMethod.POST, arrayOf(RouteMapEntry(HttpMethod.POST, POST_PATH, POST_HANDLER))),
            args(HttpMethod.DELETE, kotlin.emptyArray())
        )
    }
}
