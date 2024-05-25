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
import io.kvision.core.WidgetRefreshDelegate
import kotlin.reflect.KProperty

/**
 * Delegate provider that synchronise the delegated property value with the DOM element property
 * value by their property name.
 *
 * Properties names between kotlin object and JS one must match for magic to operate.
 * No type checking will be done regarding the JS object.
 */
internal value class WidgetPropertySyncDelegateProvider<T>(
    private val refreshDelegateProvider: (
        thisRef: Widget?,
        prop: KProperty<*>,
        refreshFunction: (T) -> Unit
    ) -> WidgetRefreshDelegate<T>
) {

    operator fun provideDelegate(thisRef: Widget?, prop: KProperty<*>): WidgetRefreshDelegate<T> {
        return refreshDelegateProvider(thisRef, prop) change@{ newValue ->
            val element = thisRef?.getElementD() ?: return@change
            val elementValue = element[prop.name]?.unsafeCast<T>()

            if (elementValue != newValue) {
                element[prop.name] = newValue
            }
        }
    }
}

/**
 * Delegate provider that synchronise the delegated property value with the DOM element property
 * value by their property name.
 *
 * The value is first transformed using [transform] before it is compared and assigned to the JS
 * object.
 *
 * Properties names between kotlin object and JS one must match for magic to operate.
 * No type checking will be done regarding the JS object.
 */
internal class WidgetPropertySyncTransformDelegateProvider<T, U>(
    private val transform: (T) -> U,
    private val refreshDelegateProvider: (
        thisRef: Widget?,
        prop: KProperty<*>,
        refreshFunction: (T) -> Unit
    ) -> WidgetRefreshDelegate<T>
) {

    operator fun provideDelegate(thisRef: Widget?, prop: KProperty<*>): WidgetRefreshDelegate<T> {
        return refreshDelegateProvider(thisRef, prop) change@{ newValue ->
            val element = thisRef?.getElementD() ?: return@change
            val elementValue = element[prop.name]?.unsafeCast<U>()
            val transformedValue = transform(newValue)

            if (elementValue != transformedValue) {
                element[prop.name] = transformedValue
            }
        }
    }
}
