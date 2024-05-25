/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2024 Maanrifa Bacar Ali <dev.manrif@gmail.com>
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
package io.kvision.material.widget

import io.kvision.core.Container

/**
 * Widget that only accepts child of type [T].
 * It is a simpler replacement for [Container] which accept any kind of child.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
interface MdListWidgetContainer<T: MdItemWidget> {

    /**
     * Adds given [item] to the current widget.
     */
    fun add(item: T)

    /**
     * Adds given [item] to the current widget at the given [position].
     */
    fun add(position: Int, item: T)

    /**
     * Adds a list of [items] to the current widget.
     */
    fun addAll(items: List<T>)

    /**
     * Removes given [item] from the current widget.
     */
    fun remove(item: T)

    /**
     * Removes the item from the current widget at the given [position].
     */
    fun removeAt(position: Int)

    /**
     * Removes all items from the current widget.
     */
    fun removeAll()

    /**
     * Removes all items from the current widget and disposes them.
     */
    fun disposeAll()
}
