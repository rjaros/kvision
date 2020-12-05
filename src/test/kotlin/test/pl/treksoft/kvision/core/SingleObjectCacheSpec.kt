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

package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.core.LazyCache
import pl.treksoft.kvision.core.SingleObjectCache
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SingleObjectCacheSpec {
    private var invocationCount = 0
    private lateinit var lazyCache: SingleObjectCache<Int>

    @BeforeTest
    fun setUp() {
        invocationCount = 0
        lazyCache = LazyCache { invocationCount++ + 10 }
    }

    @Test
    fun doesNotGenerateValueIfNotQueried() {
        // execution performed by setUp
        // evaluation
        assertEquals(0, invocationCount, "invocation count")
    }

    @Test
    fun generatesValueIfQueried() {
        // execution
        val value = lazyCache.value

        // evaluation
        assertEquals(value, 10, "returned value")
        assertEquals(invocationCount, 1, "invocation count")
    }

    @Test
    fun doesNotRegenerateValueIfQueriedTwice() {
        // execution
        val value1 = lazyCache.value
        val value2 = lazyCache.value

        // evaluation
        assertEquals(value1, 10, "returned value 1")
        assertEquals(value2, 10, "returned value 2")
        assertEquals(invocationCount, 1, "invocation count")
    }

    @Test
    fun regeneratesValueIfClearedBeforeRead() {
        // execution
        val value1 = lazyCache.value
        lazyCache.clear()
        val value2 = lazyCache.value

        // evaluation
        assertEquals(value1, 10, "returned value 1")
        assertEquals(value2, 11, "returned value 2")
        assertEquals(invocationCount, 2, "invocation count")
    }

    @Test
    fun clear_doesNotGenerateNewValue() {
        // execution
        lazyCache.clear()

        // evaluation
        assertEquals(invocationCount, 0, "invocation count")
    }

    @Test
    fun clearOn_clearsIfFunctionReturnsTrue() {
        // setup
        val value1 = lazyCache.value

        // execution
        val value2 = lazyCache.clearOn { true }.value

        // evaluation
        assertEquals(value1, 10, "returned value 1")
        assertEquals(value2, 11, "returned value 2")
        assertEquals(invocationCount, 2, "invocation count")
    }

    @Test
    fun clearOn_doesNotClearIfFunctionReturnsFalse() {
        // setup
        val value1 = lazyCache.value

        // execution
        val value2 = lazyCache.clearOn { false }.value

        // evaluation
        assertEquals(value1, 10, "returned value 1")
        assertEquals(value2, 10, "returned value 2")
        assertEquals(invocationCount, 1, "invocation count")
    }
}
