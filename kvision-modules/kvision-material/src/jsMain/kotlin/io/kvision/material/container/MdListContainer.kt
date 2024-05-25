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
package io.kvision.material.container

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.util.requireElementD
import io.kvision.material.widget.MdItemWidget
import io.kvision.material.widget.toItemWidget
import io.kvision.material.widget.toItemWidgetArray

/**
 * Subclass of container which accepts any kind of child but provides access for those of type [T].
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
abstract class MdListContainer<T : MdItemWidget> internal constructor(
    tag: String,
    className: String?
) : MdContainer(
    tag = tag,
    className = className
) {

    /**
     * Gets the direct items in this list.
     */
    val items: Array<T>
        get() = toItemWidgetArray(getElementD()?.items)

    ///////////////////////////////////////////////////////////////////////////
    // Items
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Activates the next item in the list.
     * If at the end of the list, the first item will be activated.
     *
     * If the list is empty, null will be returned.
     */
    fun activateNextItem(): T? {
        return toItemWidget(requireElementD()?.activateNextItem())
    }

    /**
     * Activates the previous item in the list.
     * If at the start of the list, the last item will be activated.
     *
     * If the list is empty, null will be returned.
     */
    fun activatePreviousItem(): T? {
        return toItemWidget(requireElementD()?.activatePreviousItem())
    }
}
