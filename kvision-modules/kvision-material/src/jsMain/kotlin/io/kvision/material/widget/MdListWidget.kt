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

import io.kvision.core.Component
import io.kvision.core.Container

/**
 * Subclass of widgets which only accept child of type [T].
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
abstract class MdListWidget<T : MdItemWidget> internal constructor(
    tag: String,
    className: String?
) : MdWidget(
    tag = tag,
    className = className,
), MdListWidgetContainer<T> {

    internal val listDelegate = MdListWidgetContainerDelegate<T>(@Suppress("LeakingThis") this)

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun onParentChanged(parent: Container?) {
        super.onParentChanged(parent)
        listDelegate.updateParent(parent)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Rendering
    ///////////////////////////////////////////////////////////////////////////

    override fun childComponents(): Collection<Component> {
        return listDelegate.items
    }

    ///////////////////////////////////////////////////////////////////////////
    // Container
    ///////////////////////////////////////////////////////////////////////////

    override fun add(item: T) {
        listDelegate.add(item)
    }

    override fun add(position: Int, item: T) {
        listDelegate.add(position, item)
    }

    override fun addAll(items: List<T>) {
        listDelegate.addAll(items)
    }

    override fun remove(item: T) {
        listDelegate.add(item)
    }

    override fun removeAt(position: Int) {
        listDelegate.removeAt(position)
    }

    override fun removeAll() {
        listDelegate.removeAll()
    }

    override fun disposeAll() {
        listDelegate.disposeAll()
    }
}
