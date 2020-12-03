package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.core.LazyCache
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LazyCacheSpec {
    private var invocationCount = 0
    private lateinit var lazyCache: LazyCache<Int>

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
}
