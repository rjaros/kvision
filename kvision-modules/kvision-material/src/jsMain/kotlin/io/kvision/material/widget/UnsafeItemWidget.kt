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

import io.kvision.utils.delete
import org.w3c.dom.HTMLElement

private const val JS_WIDGET_ITEM_PROPERTY_NAME = "kvMdItemWidget"

/**
 * Stores the [MdItemWidget] into the [HTMLElement] in order to be retrieved later.
 */
internal var HTMLElement.mdItemWidget: MdItemWidget?
    get() = asDynamic()[JS_WIDGET_ITEM_PROPERTY_NAME] as? MdItemWidget
    set(value) {
        if (value == null) {
            delete(asDynamic(), JS_WIDGET_ITEM_PROPERTY_NAME)
        } else {
            asDynamic()[JS_WIDGET_ITEM_PROPERTY_NAME] = value
        }
    }

/**
 * Retrieves and casts the [MdItemWidget] of type [T] from [target].
 * Returns null if [target] is null or does not contain a valid [MdItemWidget].
 */
internal fun <T : MdItemWidget> toItemWidget(target: dynamic): T? {
    if (target == null) {
        return null
    }

    return (target as? HTMLElement)?.mdItemWidget?.unsafeCast<T?>()
}

/**
 * Maps and casts array of [MdItemWidget] of type [T] from [target]. [target] is expected to be an
 * array of [HTMLElement].
 *
 * Returns an empty array if mapping could not succeed.
 *
 * No type checking is done regarding the items themselves.
 */
internal fun <T : MdItemWidget> toItemWidgetArray(target: dynamic): Array<T> {
    if (target == null || target !is Array<*>) {
        return emptyArray()
    }

    return target
        .map { item: HTMLElement -> item.mdItemWidget.unsafeCast<T>() }
        .unsafeCast<Array<T>>()
}


/**
 * Maps and casts array of [MdItemWidget] of type [T] from [target]. [target] is expected to be an
 * array of [HTMLElement].
 *
 * Returns an array computed by  if mapping could not succeed.
 *
 * No type checking is done regarding the items themselves.
 */
internal inline fun <T : MdItemWidget> toItemWidgetArrayOrDefault(
    target: dynamic,
    default: () -> Collection<T>
): Array<T> = toItemWidgetArray<T>(target)
    .takeIf(Array<*>::isNotEmpty)
    ?: default().toTypedArray()
