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
package pl.treksoft.kvision.utils

/*
 * Copyright 2018 Nazmul Idris All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This is a LRU cache that has no performance impact for cache insertions
 * once the capacity of the cache has been reached. For cache hit,
 * performance is O(1) and for cache eviction, it is O(1).
 */
class Cache<K, V>(private val capacity: Int = 50) {
    private val cache = HashMap<K, V>()
    private val insertionOrder = LinkedList<K>()

    /**
     * [HashMap] put and remove is O(1).
     * More info: https://stackoverflow.com/a/4578039/2085356
     */
    operator fun set(key: K, value: V): K? {
        var evictedKey: K? = null
        if (cache.size >= capacity) {
            evictedKey = getKeyToEvict()
            cache.remove(evictedKey)
        }
        cache[key] = value
        insertionOrder.append(key)
        return evictedKey
    }

    /**
     * [HashMap] get is O(1).
     * More info: https://stackoverflow.com/a/4578039/2085356
     */
    operator fun get(key: K): V? = cache[key]

    /**
     * The head of the [insertionOrder] is removed, which is O(1), since this
     * is a linked list, and it's inexpensive to remove an item from head.
     * More info: https://stackoverflow.com/a/42849573/2085356
     */
    private fun getKeyToEvict(): K? = insertionOrder.removeAtIndex(0)

    override fun toString() = cache.toString()
}

/**
 * Created by gazollajunior on 07/04/16.
 */
class Node<T>(var value: T) {
    var next: Node<T>? = null
    var previous: Node<T>? = null
}

class LinkedList<T> {

    private var head: Node<T>? = null

    var isEmpty: Boolean = head == null

    fun first(): Node<T>? = head

    fun last(): Node<T>? {
        var node = head
        return if (node != null) {
            while (node?.next != null) {
                node = node.next
            }
            node
        } else {
            null
        }
    }

    fun count(): Int {
        var node = head
        return if (node != null) {
            var counter = 1
            while (node?.next != null) {
                node = node.next
                counter += 1
            }
            counter
        } else {
            0
        }
    }

    fun nodeAtIndex(index: Int): Node<T>? {
        if (index >= 0) {
            var node = head
            var i = index
            while (node != null) {
                if (i == 0) return node
                i -= 1
                node = node.next
            }
        }
        return null
    }

    fun append(value: T) {
        val newNode = Node(value)

        val lastNode = this.last()
        if (lastNode != null) {
            newNode.previous = lastNode
            lastNode.next = newNode
        } else {
            head = newNode
        }
    }

    fun removeAll() {
        head = null
    }

    fun removeNode(node: Node<T>): T {
        val prev = node.previous
        val next = node.next

        if (prev != null) {
            prev.next = next
        } else {
            head = next
        }
        next?.previous = prev

        node.previous = null
        node.next = null

        return node.value
    }

    fun removeLast(): T? {
        val last = this.last()
        return if (last != null) {
            removeNode(last)
        } else {
            null
        }
    }

    fun removeAtIndex(index: Int): T? {
        val node = nodeAtIndex(index)
        return if (node != null) {
            removeNode(node)
        } else {
            null
        }
    }

    override fun toString(): String {
        var s = "["
        var node = head
        while (node != null) {
            s += "${node.value}"
            node = node.next
            if (node != null) {
                s += ", "
            }
        }
        return "$s]"
    }
}
