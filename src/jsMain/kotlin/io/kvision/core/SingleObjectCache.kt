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

package io.kvision.core

interface SingleObjectCache<T : Any> {
    val value: T
    fun clear()

    /**
     * Creates a new cache, that wraps the old cache and automatically calls @see clear before retrieving a value if
     * shouldClear returns true
     */
    fun clearOn(shouldClear: () -> Boolean): SingleObjectCache<T> = AutoClearCache(this, shouldClear)
}

/**
 * A cache that behaves like a resettable `Lazy`: It generates a value from a given initializer lazily, however that
 * cache can be cleared, so that the value will be regenerated when queried next
 */
class LazyCache<T : Any>(val initializer: () -> T) : SingleObjectCache<T> {
    override val value: T
        get() = _value ?: initializer().also { _value = it }

    private var _value: T? = null

    override fun clear() {
        _value = null
    }
}

private class AutoClearCache<T : Any>(val delegate: SingleObjectCache<T>, val shouldClear: () -> Boolean) :
    SingleObjectCache<T> by delegate {
    override val value: T
        get() {
            if (shouldClear()) {
                delegate.clear()
            }
            return delegate.value
        }
}
