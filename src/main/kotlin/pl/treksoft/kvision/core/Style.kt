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
package pl.treksoft.kvision.core

import kotlin.reflect.KProperty

/**
 * CSS style object.
 *
 * @constructor
 * @param className optional name of the CSS class, it will be generated if not specified
 * @param parentStyle parent CSS style object
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Style(className: String? = null, parentStyle: Style? = null, init: (Style.() -> Unit)? = null) :
    StyledComponent() {
    private val propertyValues: MutableMap<String, Any?> = mutableMapOf()

    private val newClassName: String = if (parentStyle == null) {
        className ?: "kv_styleclass_${counter++}"
    } else {
        "${parentStyle.className} " + (className ?: ".kv_styleclass_${counter++}")
    }

    /**
     * The name of the CSS class.
     */
    var className: String by refreshOnUpdate(newClassName)

    init {
        @Suppress("LeakingThis")
        styles.add(this)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    internal fun generateStyle(): String {
        val styles = getSnStyleInternal()
        return ".$className {\n" + styles.joinToString("\n") {
            "${it.first}: ${it.second};"
        } + "\n}"
    }

    protected fun <T> refreshOnUpdate(refreshFunction: ((T) -> Unit) = { this.refresh() }) =
        RefreshDelegateProvider<T>(null, refreshFunction)

    protected fun <T> refreshOnUpdate(initialValue: T, refreshFunction: ((T) -> Unit) = { this.refresh() }) =
        RefreshDelegateProvider(initialValue, refreshFunction)

    protected inner class RefreshDelegateProvider<T>(
        private val initialValue: T?, private val refreshFunction: (T) -> Unit
    ) {
        operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): RefreshDelegate<T> {
            if (initialValue != null) propertyValues[prop.name] = initialValue
            return RefreshDelegate(refreshFunction)
        }
    }

    protected inner class RefreshDelegate<T>(private val refreshFunction: ((T) -> Unit)) {
        @Suppress("UNCHECKED_CAST")
        operator fun getValue(thisRef: StyledComponent, property: KProperty<*>): T {
            val value = propertyValues[property.name]
            return if (value != null) {
                value as T
            } else {
                null as T
            }
        }

        operator fun setValue(thisRef: StyledComponent, property: KProperty<*>, value: T) {
            propertyValues[property.name] = value
            refreshFunction(value)
        }
    }

    companion object {
        internal var counter = 0
        internal var styles = mutableListOf<Style>()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Widget.style(className: String? = null, init: (Style.() -> Unit)? = null): Style {
    val style = Style(className, null, init)
    this.addCssStyle(style)
    return style
}

/**
 * DSL builder extension function for cascading styles.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Style.style(className: String? = null, init: (Style.() -> Unit)? = null): Style {
    return Style(className, this, init)
}
