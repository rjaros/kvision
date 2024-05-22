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
import io.kvision.core.Widget

/**
 * Implementation of [MdListWidgetContainer].
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
internal class MdListWidgetContainerDelegate<T : MdItemWidget>(
    private val widget: Widget
) : MdListWidgetContainer<T> {

    val items = mutableListOf<T>()
    var onAdded: ((item: T) -> Unit)? = null
    var onRemoved: ((item: T) -> Unit)? = null

    /**
     * Sets the new parent to all the items.
     */
    fun updateParent(parent: Container?) {
        items.forEach { it.parent = parent }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Items
    ///////////////////////////////////////////////////////////////////////////

    override fun add(item: T) {
        items.add(item)
        item.parent?.remove(item)
        item.parent = widget.parent
        onAdded?.invoke(item)
        widget.refresh()
    }

    override fun add(position: Int, item: T) {
        items.add(position, item)
        item.parent?.remove(item)
        item.parent = widget.parent
        onAdded?.invoke(item)
        widget.refresh()
    }

    override fun addAll(items: List<T>) {
        this.items.addAll(items)

        this.items.forEach { item ->
            item.parent?.remove(item)
            item.parent = widget.parent
            onAdded?.invoke(item)
        }

        widget.refresh()
    }

    override fun remove(item: T) {
        if (items.remove(item)) {
            item.clearParent()
            onRemoved?.invoke(item)
            widget.refresh()
        }
    }

    override fun removeAt(position: Int) {
        items
            .getOrNull(position)
            ?.also { item ->
                items.removeAt(position)
                onRemoved?.invoke(item)
            }
            ?.clearParent()
            ?.also { widget.refresh() }
    }

    override fun removeAll() {
        items
            .onEach { item ->
                item.clearParent()
                onRemoved?.invoke(item)
            }
            .clear()

        widget.refresh()
    }

    override fun disposeAll() {
        items.onEach { it.dispose() }
        removeAll()
    }
}
