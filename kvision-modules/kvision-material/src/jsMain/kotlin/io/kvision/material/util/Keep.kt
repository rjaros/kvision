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
package io.kvision.material.util

import io.kvision.core.Widget
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class WidgetPropertyKeepDelegate<T>(initialValue: T) : ReadWriteProperty<Widget, T> {

    private var lastValue = initialValue

    override fun getValue(thisRef: Widget, property: KProperty<*>): T {
        val element = thisRef.getElementD() ?: return lastValue
        return element[property.name].unsafeCast<T>()
    }

    override fun setValue(thisRef: Widget, property: KProperty<*>, value: T) {
        lastValue = value
        val element = thisRef.getElementD() ?: return
        element[property.name] = value
    }
}

/**
 * Delegate provider that provides a delegate that reads and writes new values directly to the DOM
 * element by their property name.
 *
 * If the DOM element is not available at the time of read, the last set value will be returned
 * instead.
 *
 * The write operation to the DOM element will only be performed if the DOM element is available.
 */
internal value class WidgetPropertyKeepDelegateProvider<T>(private val initialValue: T) {

    operator fun provideDelegate(
        thisRef: Widget?,
        prop: KProperty<*>
    ): ReadWriteProperty<Widget, T> {
        return WidgetPropertyKeepDelegate(initialValue)
    }
}
