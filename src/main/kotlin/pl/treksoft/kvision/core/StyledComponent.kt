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

import pl.treksoft.kvision.utils.asString
import kotlin.Unit
import kotlin.reflect.KProperty

/**
 * Base class for components supporting CSS styling.
 */
abstract class StyledComponent : Component {
    private val propertyValues: MutableMap<String, Any?> = mutableMapOf()

    /**
     * Width of the current component.
     */
    open var width: CssSize? by refreshOnUpdate()
    /**
     * Minimal width of the current component.
     */
    var minWidth: CssSize? by refreshOnUpdate()
    /**
     * Maximal width of the current component.
     */
    var maxWidth: CssSize? by refreshOnUpdate()
    /**
     * Height of the current component.
     */
    var height: CssSize? by refreshOnUpdate()
    /**
     * Minimal height of the current component.
     */
    var minHeight: CssSize? by refreshOnUpdate()
    /**
     * Maximal height of the current component.
     */
    var maxHeight: CssSize? by refreshOnUpdate()
    /**
     * CSS position of the current component.
     */
    var position: Position? by refreshOnUpdate()
    /**
     * Top edge of the current component.
     */
    var top: CssSize? by refreshOnUpdate()
    /**
     * Left edge of the current component.
     */
    var left: CssSize? by refreshOnUpdate()
    /**
     * Right edge of the current component.
     */
    var right: CssSize? by refreshOnUpdate()
    /**
     * Bottom edge of the current component.
     */
    var bottom: CssSize? by refreshOnUpdate()
    /**
     * Z-index of the current component.
     */
    var zIndex: Int? by refreshOnUpdate()
    /**
     * Border of the current component.
     */
    var border: Border? by refreshOnUpdate()
    /**
     * Top border of the current component.
     */
    var borderTop: Border? by refreshOnUpdate()
    /**
     * Right border of the current component.
     */
    var borderRight: Border? by refreshOnUpdate()
    /**
     * Bottom border of the current component.
     */
    var borderBottom: Border? by refreshOnUpdate()
    /**
     * Left border of the current component.
     */
    var borderLeft: Border? by refreshOnUpdate()
    /**
     * Margin of the current component.
     */
    var margin: CssSize? by refreshOnUpdate()
    /**
     * Top margin of the current component.
     */
    var marginTop: CssSize? by refreshOnUpdate()
    /**
     * Right margin of the current component.
     */
    var marginRight: CssSize? by refreshOnUpdate()
    /**
     * Bottom margin of the current component.
     */
    var marginBottom: CssSize? by refreshOnUpdate()
    /**
     * Left margin of the current component.
     */
    var marginLeft: CssSize? by refreshOnUpdate()
    /**
     * Padding of the current component.
     */
    var padding: CssSize? by refreshOnUpdate()
    /**
     * Top padding of the current component.
     */
    var paddingTop: CssSize? by refreshOnUpdate()
    /**
     * Right padding of the current component.
     */
    var paddingRight: CssSize? by refreshOnUpdate()
    /**
     * Bottom padding of the current component.
     */
    var paddingBottom: CssSize? by refreshOnUpdate()
    /**
     * Left padding of the current component.
     */
    var paddingLeft: CssSize? by refreshOnUpdate()
    /**
     * Text color for the current component.
     */
    var color: Color? by refreshOnUpdate()
    /**
     * Text color for the current component given in hex format (write only).
     *
     * This property gives a convenient way to set the value of [color] property e.g.:
     *
     * c.colorHex = 0x00ff00
     *
     * The value read from this property is always null.
     */
    var colorHex: Int?
        get() = null
        set(value) {
            color = if (value != null) Color(value) else null
        }
    /**
     * Text color for the current component given with named constant (write only).
     *
     * This property gives a convenient way to set the value of [color] property e.g.:
     *
     * c.colorName = Col.GREEN
     *
     * The value read from this property is always null.
     */
    var colorName: Col?
        get() = null
        set(value) {
            color = if (value != null) Color(value) else null
        }
    /**
     * Opacity of the current component.
     */
    var opacity: Double? by refreshOnUpdate()
    /**
     * Background of the current component.
     */
    var background: Background? by refreshOnUpdate()

    private var snStyleCache: List<StringPair>? = null

    /**
     * @suppress
     * Internal function
     * Re-renders the current component.
     * @return current component
     */
    open fun refresh(): StyledComponent {
        snStyleCache = null
        return this
    }

    internal fun getSnStyleInternal(): List<StringPair> {
        return snStyleCache ?: {
            val s = getSnStyle()
            snStyleCache = s
            s
        }()
    }

    /**
     * Returns the list of String pairs defining CSS style attributes and their values.
     * @return the list of attributes and their values
     */
    @Suppress("ComplexMethod", "LongMethod")
    protected open fun getSnStyle(): List<StringPair> {
        val snstyle = mutableListOf<StringPair>()
        width?.let {
            snstyle.add("width" to it.asString())
        }
        minWidth?.let {
            snstyle.add("min-width" to it.asString())
        }
        maxWidth?.let {
            snstyle.add("max-width" to it.asString())
        }
        height?.let {
            snstyle.add("height" to it.asString())
        }
        minHeight?.let {
            snstyle.add("min-height" to it.asString())
        }
        maxHeight?.let {
            snstyle.add("max-height" to it.asString())
        }
        position?.let {
            snstyle.add("position" to it.position)
        }
        top?.let {
            snstyle.add("top" to it.asString())
        }
        left?.let {
            snstyle.add("left" to it.asString())
        }
        right?.let {
            snstyle.add("right" to it.asString())
        }
        bottom?.let {
            snstyle.add("bottom" to it.asString())
        }
        zIndex?.let {
            snstyle.add("z-index" to it.toString())
        }
        border?.let {
            snstyle.add("border" to it.asString())
        }
        borderTop?.let {
            snstyle.add("border-top" to it.asString())
        }
        borderRight?.let {
            snstyle.add("border-right" to it.asString())
        }
        borderBottom?.let {
            snstyle.add("border-bottom" to it.asString())
        }
        borderLeft?.let {
            snstyle.add("border-left" to it.asString())
        }
        margin?.let {
            snstyle.add("margin" to it.asString())
        }
        marginTop?.let {
            snstyle.add("margin-top" to it.asString())
        }
        marginRight?.let {
            snstyle.add("margin-right" to it.asString())
        }
        marginBottom?.let {
            snstyle.add("margin-bottom" to it.asString())
        }
        marginLeft?.let {
            snstyle.add("margin-left" to it.asString())
        }
        padding?.let {
            snstyle.add("padding" to it.asString())
        }
        paddingTop?.let {
            snstyle.add("padding-top" to it.asString())
        }
        paddingRight?.let {
            snstyle.add("padding-right" to it.asString())
        }
        paddingBottom?.let {
            snstyle.add("padding-bottom" to it.asString())
        }
        paddingLeft?.let {
            snstyle.add("padding-left" to it.asString())
        }
        color?.let {
            snstyle.add("color" to it.asString())
        }
        opacity?.let {
            snstyle.add("opacity" to it.toString())
        }
        background?.let {
            snstyle.add("background" to it.asString())
        }
        return snstyle
    }

    internal fun <T> refreshOnUpdate(refreshFunction: ((T) -> Unit) = { this.refresh() }) =
        RefreshDelegateProvider<T>(null, refreshFunction)

    internal fun <T> refreshOnUpdate(initialValue: T, refreshFunction: ((T) -> Unit) = { this.refresh() }) =
        RefreshDelegateProvider(initialValue, refreshFunction)

    internal inner class RefreshDelegateProvider<T>(
        private val initialValue: T?, private val refreshFunction: (T) -> Unit
    ) {
        operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): RefreshDelegate<T> {
            if (initialValue != null) propertyValues[prop.name] = initialValue
            return RefreshDelegate(refreshFunction)
        }
    }

    internal inner class RefreshDelegate<T>(private val refreshFunction: ((T) -> Unit)) {
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
}
