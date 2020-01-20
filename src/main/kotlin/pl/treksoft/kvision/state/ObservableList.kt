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
package pl.treksoft.kvision.state

/**
 * Observable list interface.
 */
interface ObservableList<T> : MutableList<T>, ObservableState<List<T>> {
    val onUpdate: MutableCollection<(MutableList<T>) -> Unit>
}

/**
 * Simple observable list implementation.
 */
@Suppress("TooManyFunctions")
class ObservableListWrapper<T>(val mutableList: MutableList<T> = mutableListOf()) : MutableList<T>, ObservableList<T>,
    ObservableState<List<T>> {

    override fun getState(): List<T> {
        return this
    }

    override fun subscribe(observer: (List<T>) -> Unit): () -> Unit {
        onUpdate += observer
        observer(this)
        return {
            onUpdate -= observer
        }
    }

    override val onUpdate: MutableCollection<(MutableList<T>) -> Unit> = mutableListOf()

    override val size: Int
        get() = mutableList.size

    override fun contains(element: T): Boolean {
        return mutableList.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return mutableList.containsAll(elements)
    }

    override fun get(index: Int): T {
        return mutableList[index]
    }

    override fun indexOf(element: T): Int {
        return mutableList.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return mutableList.isEmpty()
    }

    override fun iterator(): MutableIterator<T> = object : MutableIterator<T> {
        val inner = mutableList.iterator()
        override fun hasNext(): Boolean = inner.hasNext()
        override fun next(): T = inner.next()
        override fun remove() {
            inner.remove()
            onUpdate.forEach { it(this@ObservableListWrapper) }
        }
    }

    override fun lastIndexOf(element: T): Int {
        return mutableList.lastIndexOf(element)
    }

    override fun listIterator(): MutableListIterator<T> = object : MutableListIterator<T> {
        val inner = mutableList.listIterator()

        override fun hasNext() = inner.hasNext()
        override fun next() = inner.next()
        override fun hasPrevious() = inner.hasPrevious()
        override fun nextIndex() = inner.nextIndex()
        override fun previous() = inner.previous()
        override fun previousIndex() = inner.previousIndex()

        override fun add(element: T) {
            inner.add(element)
            onUpdate.forEach { it(this@ObservableListWrapper) }
        }

        override fun set(element: T) {
            inner.set(element)
            onUpdate.forEach { it(this@ObservableListWrapper) }
        }

        override fun remove() {
            inner.remove()
            onUpdate.forEach { it(this@ObservableListWrapper) }
        }
    }

    override fun listIterator(index: Int): MutableListIterator<T> = object : MutableListIterator<T> {
        val inner = mutableList.listIterator(index)

        override fun hasNext() = inner.hasNext()
        override fun next() = inner.next()
        override fun hasPrevious() = inner.hasPrevious()
        override fun nextIndex() = inner.nextIndex()
        override fun previous() = inner.previous()
        override fun previousIndex() = inner.previousIndex()

        override fun add(element: T) {
            inner.add(element)
            onUpdate.forEach { it(this@ObservableListWrapper) }
        }

        override fun set(element: T) {
            inner.set(element)
            onUpdate.forEach { it(this@ObservableListWrapper) }
        }

        override fun remove() {
            inner.remove()
            onUpdate.forEach { it(this@ObservableListWrapper) }
        }
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return mutableList.subList(fromIndex, toIndex)
    }

    override fun add(element: T): Boolean {
        val result = mutableList.add(element)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun add(index: Int, element: T) {
        mutableList.add(index, element)
        onUpdate.forEach { it(this) }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val result = mutableList.addAll(elements)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        val result = mutableList.addAll(index, elements)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun clear() {
        mutableList.clear()
        onUpdate.forEach { it(this) }
    }

    override fun remove(element: T): Boolean {
        val result = mutableList.remove(element)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun removeAt(index: Int): T {
        val result = mutableList.removeAt(index)
        onUpdate.forEach { it(this) }
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val result = mutableList.removeAll(elements)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val result = mutableList.retainAll(elements)
        if (result) {
            onUpdate.forEach { it(this) }
        }
        return result
    }

    override fun set(index: Int, element: T): T {
        val result = mutableList.set(index, element)
        onUpdate.forEach { it(this) }
        return result
    }
}

/**
 * Creates an instance of ObservableList<T>
 */
fun <T> observableListOf(vararg items: T) = ObservableListWrapper(items.toMutableList())
