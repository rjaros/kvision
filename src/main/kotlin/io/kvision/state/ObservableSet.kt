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
package io.kvision.state

/**
 * Observable set interface.
 */
interface ObservableSet<T> : MutableSet<T>, ObservableState<Set<T>> {
    val onUpdate: MutableCollection<(MutableSet<T>) -> Unit>
}

/**
 * Simple observable set implementation.
 */
class ObservableSetWrapper<T>(val mutableSet: MutableSet<T> = mutableSetOf()) : MutableSet<T>, ObservableSet<T>,
    ObservableState<Set<T>> {

    override fun getState(): Set<T> {
        return this
    }

    override fun subscribe(observer: (Set<T>) -> Unit): () -> Unit {
        onUpdate += observer
        observer(this)
        return {
            onUpdate -= observer
        }
    }

    override val onUpdate: MutableCollection<(MutableSet<T>) -> Unit> = mutableListOf()

    override val size: Int
        get() = mutableSet.size

    override fun contains(element: T): Boolean {
        return mutableSet.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return mutableSet.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return mutableSet.isEmpty()
    }

    override fun iterator(): MutableIterator<T> = object : MutableIterator<T> {
        val inner = mutableSet.iterator()
        override fun hasNext(): Boolean = inner.hasNext()
        override fun next(): T = inner.next()
        override fun remove() {
            inner.remove()
            onUpdate.forEach { it(this@ObservableSetWrapper) }
        }
    }

    override fun add(element: T): Boolean {
        val result = mutableSet.add(element)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val result = mutableSet.addAll(elements)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun clear() {
        mutableSet.clear()
        onUpdate.forEach { it(this) }
    }

    override fun remove(element: T): Boolean {
        val result = mutableSet.remove(element)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val result = mutableSet.removeAll(elements)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val result = mutableSet.retainAll(elements)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }
}

/**
 * Creates an instance of ObservableSet<T>
 */
fun <T> observableSetOf(vararg items: T): ObservableSet<T> = ObservableSetWrapper(items.toMutableSet())
