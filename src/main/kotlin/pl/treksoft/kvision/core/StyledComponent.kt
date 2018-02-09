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

/**
 * Base class for components supporting CSS styling.
 */
abstract class StyledComponent : Component {
    /**
     * Width of the current component.
     */
    open var width: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Minimal width of the current component.
     */
    var minWidth: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Maximal width of the current component.
     */
    var maxWidth: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Height of the current component.
     */
    var height: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Minimal height of the current component.
     */
    var minHeight: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Maximal height of the current component.
     */
    var maxHeight: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Border of the current component.
     */
    var border: Border? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Top border of the current component.
     */
    var borderTop: Border? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Right border of the current component.
     */
    var borderRight: Border? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Bottom border of the current component.
     */
    var borderBottom: Border? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Left border of the current component.
     */
    var borderLeft: Border? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Margin of the current component.
     */
    var margin: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Top margin of the current component.
     */
    var marginTop: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Right margin of the current component.
     */
    var marginRight: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Bottom margin of the current component.
     */
    var marginBottom: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Left margin of the current component.
     */
    var marginLeft: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Padding of the current component.
     */
    var padding: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Top padding of the current component.
     */
    var paddingTop: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Right padding of the current component.
     */
    var paddingRight: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Bottom padding of the current component.
     */
    var paddingBottom: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Left padding of the current component.
     */
    var paddingLeft: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Text color for the current component.
     */
    var color: Color? = null
        set(value) {
            field = value
            refresh()
        }
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
     * c.colorName = COLOR.GREEN
     *
     * The value read from this property is always null.
     */
    var colorName: COLOR?
        get() = null
        set(value) {
            color = if (value != null) Color(value) else null
        }
    /**
     * Opacity of the current component.
     */
    var opacity: Double? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Background of the current component.
     */
    var background: Background? = null
        set(value) {
            field = value
            refresh()
        }


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
}
