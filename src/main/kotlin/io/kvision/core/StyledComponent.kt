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
package io.kvision.core

import io.kvision.utils.asString
import io.kvision.utils.delete
import kotlin.reflect.KProperty

/**
 * Base class for components supporting CSS styling.
 */
@Suppress("LargeClass")
abstract class StyledComponent {
    private val propertyValues = js("{}")
    private val propertyStyles = js("{}")

    private var snStyleCache: List<StringPair>? = null

    private fun setStyleProperty(key: String, value: String?) {
        if (value != null) {
            propertyStyles[key] = value
        } else {
            delete(propertyStyles, key)
        }
    }

    /**
     * Width of the current component.
     */
    open var width: CssSize? by refreshOnUpdate {
        setStyleProperty("width", it?.asString())
    }

    /**
     * Minimal width of the current component.
     */
    open var minWidth: CssSize? by refreshOnUpdate {
        setStyleProperty("min-width", it?.asString())
    }

    /**
     * Maximal width of the current component.
     */
    open var maxWidth: CssSize? by refreshOnUpdate {
        setStyleProperty("max-width", it?.asString())
    }

    /**
     * Height of the current component.
     */
    open var height: CssSize? by refreshOnUpdate {
        setStyleProperty("height", it?.asString())
    }

    /**
     * Minimal height of the current component.
     */
    open var minHeight: CssSize? by refreshOnUpdate {
        setStyleProperty("min-height", it?.asString())
    }

    /**
     * Maximal height of the current component.
     */
    open var maxHeight: CssSize? by refreshOnUpdate {
        setStyleProperty("max-height", it?.asString())
    }

    /**
     * CSS display of the current component.
     */
    open var display: Display? by refreshOnUpdate {
        setStyleProperty("display", it?.display)
    }

    /**
     * CSS position of the current component.
     */
    open var position: Position? by refreshOnUpdate {
        setStyleProperty("position", it?.position)
    }

    /**
     * Top edge of the current component.
     */
    open var top: CssSize? by refreshOnUpdate {
        setStyleProperty("top", it?.asString())
    }

    /**
     * Left edge of the current component.
     */
    open var left: CssSize? by refreshOnUpdate {
        setStyleProperty("left", it?.asString())
    }

    /**
     * Right edge of the current component.
     */
    open var right: CssSize? by refreshOnUpdate {
        setStyleProperty("right", it?.asString())
    }

    /**
     * Bottom edge of the current component.
     */
    open var bottom: CssSize? by refreshOnUpdate {
        setStyleProperty("bottom", it?.asString())
    }

    /**
     * Z-index of the current component.
     */
    open var zIndex: Int? by refreshOnUpdate {
        setStyleProperty("z-index", it?.let { "$it" })
    }

    /**
     * CSS overflow of the current component.
     */
    open var overflow: Overflow? by refreshOnUpdate {
        setStyleProperty("overflow", it?.overflow)
    }

    /**
     * CSS overflow-x of the current component.
     */
    open var overflowX: Overflow? by refreshOnUpdate {
        setStyleProperty("overflow-x", it?.overflow)
    }

    /**
     * CSS overflow-y of the current component.
     */
    open var overflowY: Overflow? by refreshOnUpdate {
        setStyleProperty("overflow-y", it?.overflow)
    }

    /**
     * CSS overflow-wrap of the current component.
     */
    open var overflowWrap: OverflowWrap? by refreshOnUpdate {
        setStyleProperty("overflow-wrap", it?.overflowWrap)
    }

    /**
     * CSS resize of the current component.
     */
    open var resize: Resize? by refreshOnUpdate {
        setStyleProperty("resize", it?.resize)
    }

    /**
     * Border of the current component.
     */
    open var border: Border? by refreshOnUpdate {
        setStyleProperty("border", it?.asString())
    }

    /**
     * Top border of the current component.
     */
    open var borderTop: Border? by refreshOnUpdate {
        setStyleProperty("border-top", it?.asString())
    }

    /**
     * Right border of the current component.
     */
    open var borderRight: Border? by refreshOnUpdate {
        setStyleProperty("border-right", it?.asString())
    }

    /**
     * Bottom border of the current component.
     */
    open var borderBottom: Border? by refreshOnUpdate {
        setStyleProperty("border-bottom", it?.asString())
    }

    /**
     * Left border of the current component.
     */
    open var borderLeft: Border? by refreshOnUpdate {
        setStyleProperty("border-left", it?.asString())
    }

    /**
     * Margin of the current component.
     */
    open var margin: CssSize? by refreshOnUpdate {
        setStyleProperty("margin", it?.asString())
    }

    /**
     * Top margin of the current component.
     */
    open var marginTop: CssSize? by refreshOnUpdate {
        setStyleProperty("margin-top", it?.asString())
    }

    /**
     * Right margin of the current component.
     */
    open var marginRight: CssSize? by refreshOnUpdate {
        setStyleProperty("margin-right", it?.asString())
    }

    /**
     * Bottom margin of the current component.
     */
    open var marginBottom: CssSize? by refreshOnUpdate {
        setStyleProperty("margin-bottom", it?.asString())
    }

    /**
     * Left margin of the current component.
     */
    open var marginLeft: CssSize? by refreshOnUpdate {
        setStyleProperty("margin-left", it?.asString())
    }

    /**
     * Padding of the current component.
     */
    open var padding: CssSize? by refreshOnUpdate {
        setStyleProperty("padding", it?.asString())
    }

    /**
     * Top padding of the current component.
     */
    open var paddingTop: CssSize? by refreshOnUpdate {
        setStyleProperty("padding-top", it?.asString())
    }

    /**
     * Right padding of the current component.
     */
    open var paddingRight: CssSize? by refreshOnUpdate {
        setStyleProperty("padding-right", it?.asString())
    }

    /**
     * Bottom padding of the current component.
     */
    open var paddingBottom: CssSize? by refreshOnUpdate {
        setStyleProperty("padding-bottom", it?.asString())
    }

    /**
     * Left padding of the current component.
     */
    open var paddingLeft: CssSize? by refreshOnUpdate {
        setStyleProperty("padding-left", it?.asString())
    }

    /**
     * Text color for the current component.
     */
    open var color: Color? by refreshOnUpdate {
        setStyleProperty("color", it?.asString())
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
    open var colorHex: Int?
        get() = null
        set(value) {
            color = if (value != null) Color.hex(value) else null
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
    open var colorName: Col?
        get() = null
        set(value) {
            color = if (value != null) Color.name(value) else null
        }

    /**
     * Opacity of the current component.
     */
    open var opacity: Double? by refreshOnUpdate {
        setStyleProperty("opacity", it?.let { "$it" })
    }

    /**
     * Background of the current component.
     */
    open var background: Background? by refreshOnUpdate {
        setStyleProperty("background", it?.asString())
    }

    /**
     * CSS Text direction of the current component.
     */
    open var textDirection: Direction? by refreshOnUpdate {
        setStyleProperty("text-direction", it?.direction)
    }

    /**
     * CSS Text letter spacing of the current component.
     */
    open var letterSpacing: CssSize? by refreshOnUpdate {
        setStyleProperty("letter-spacing", it?.asString())
    }

    /**
     * CSS Text line height of the current component.
     */
    open var lineHeight: CssSize? by refreshOnUpdate {
        setStyleProperty("line-height", it?.asString())
    }

    /**
     * CSS Text align of the current component.
     */
    open var textAlign: TextAlign? by refreshOnUpdate {
        setStyleProperty("text-align", it?.textAlign)
    }

    /**
     * CSS Text decoration of the current component.
     */
    open var textDecoration: TextDecoration? by refreshOnUpdate {
        setStyleProperty("text-decoration", it?.asString())
    }

    /**
     * CSS Text indent of the current component.
     */
    open var textIndent: CssSize? by refreshOnUpdate {
        setStyleProperty("text-indent", it?.asString())
    }

    /**
     * CSS Text shadow of the current component.
     */
    open var textShadow: TextShadow? by refreshOnUpdate {
        setStyleProperty("text-shadow", it?.asString())
    }

    /**
     * CSS Text transform of the current component.
     */
    open var textTransform: TextTransform? by refreshOnUpdate {
        setStyleProperty("text-transform", it?.textTransform)
    }

    /**
     * CSS Text overflow of the current component.
     */
    open var textOverflow: TextOverflow? by refreshOnUpdate {
        setStyleProperty("text-overflow", it?.textOverflow)
    }

    /**
     * CSS Text unicode-bidi of the current component.
     */
    open var unicodeBidi: UnicodeBidi? by refreshOnUpdate {
        setStyleProperty("unicode-bidi", it?.unicodeBidi)
    }

    /**
     * CSS Text vertical align of the current component.
     */
    open var verticalAlign: VerticalAlign? by refreshOnUpdate {
        setStyleProperty("vertical-align", it?.verticalAlign)
    }

    /**
     * CSS Text white space of the current component.
     */
    open var whiteSpace: WhiteSpace? by refreshOnUpdate {
        setStyleProperty("white-space", it?.whiteSpace)
    }

    /**
     * CSS Text word spacing of the current component.
     */
    open var wordSpacing: CssSize? by refreshOnUpdate {
        setStyleProperty("word-spacing", it?.asString())
    }

    /**
     * CSS font family of the current component.
     */
    open var fontFamily: String? by refreshOnUpdate {
        setStyleProperty("font-family", it)
    }

    /**
     * CSS font size of the current component.
     */
    open var fontSize: CssSize? by refreshOnUpdate {
        setStyleProperty("font-size", it?.asString())
    }

    /**
     * CSS font style of the current component.
     */
    open var fontStyle: FontStyle? by refreshOnUpdate {
        setStyleProperty("font-style", it?.fontStyle)
    }

    /**
     * CSS font weight of the current component.
     */
    open var fontWeight: FontWeight? by refreshOnUpdate {
        setStyleProperty("font-weight", it?.fontWeight)
    }

    /**
     * CSS font variant of the current component.
     */
    open var fontVariant: FontVariant? by refreshOnUpdate {
        setStyleProperty("font-variant", it?.fontVariant)
    }

    /**
     * CSS position float of the current component.
     */
    open var float: PosFloat? by refreshOnUpdate {
        setStyleProperty("float", it?.posFloat)
    }

    /**
     * CSS clear float of the current component.
     */
    open var clear: Clear? by refreshOnUpdate {
        setStyleProperty("clear", it?.clear)
    }

    /**
     * CSS word break of the current component.
     */
    open var wordBreak: WordBreak? by refreshOnUpdate {
        setStyleProperty("word-break", it?.wordBreak)
    }

    /**
     * CSS line break of the current component.
     */
    open var lineBreak: LineBreak? by refreshOnUpdate {
        setStyleProperty("line-break", it?.lineBreak)
    }

    /**
     * CSS cursor shape over the current component.
     */
    open var cursor: Cursor? by refreshOnUpdate {
        setStyleProperty("cursor", it?.cursor)
    }

    /**
     * CSS flexbox direction.
     */
    open var flexDirection: FlexDirection? by refreshOnUpdate {
        setStyleProperty("flex-direction", it?.dir)
    }

    /**
     * CSS flexbox wrap mode.
     */
    open var flexWrap: FlexWrap? by refreshOnUpdate {
        setStyleProperty("flex-wrap", it?.wrap)
    }

    /**
     * CSS grid items justification.
     */
    open var justifyItems: JustifyItems? by refreshOnUpdate {
        setStyleProperty("justify-items", it?.justify)
    }

    /**
     * CSS flexbox/grid content justification.
     */
    open var justifyContent: JustifyContent? by refreshOnUpdate {
        setStyleProperty("justify-content", it?.justifyContent)
    }

    /**
     * CSS flexbox/grid items alignment.
     */
    open var alignItems: AlignItems? by refreshOnUpdate {
        setStyleProperty("align-items", it?.alignItems)
    }

    /**
     * CSS flexbox/grid content alignment.
     */
    open var alignContent: AlignContent? by refreshOnUpdate {
        setStyleProperty("align-content", it?.alignContent)
    }

    /**
     * CSS flexbox item order.
     */
    open var order: Int? by refreshOnUpdate {
        setStyleProperty("order", it?.let { "$it" })
    }

    /**
     * CSS flexbox item grow.
     */
    open var flexGrow: Int? by refreshOnUpdate {
        setStyleProperty("flex-grow", it?.let { "$it" })
    }

    /**
     * CSS flexbox item shrink.
     */
    open var flexShrink: Int? by refreshOnUpdate {
        setStyleProperty("flex-shrink", it?.let { "$it" })
    }

    /**
     * CSS flexbox item basis.
     */
    open var flexBasis: CssSize? by refreshOnUpdate {
        setStyleProperty("flex-basis", it?.asString())
    }

    /**
     * CSS flexbox items self-alignment.
     */
    open var alignSelf: AlignItems? by refreshOnUpdate {
        setStyleProperty("align-self", it?.alignItems)
    }

    /**
     * CSS grid items self-justification.
     */
    open var justifySelf: JustifyItems? by refreshOnUpdate {
        setStyleProperty("justify-self", it?.justify)
    }

    /**
     * CSS grid auto columns.
     */
    open var gridAutoColumns: String? by refreshOnUpdate {
        setStyleProperty("grid-auto-columns", it)
    }

    /**
     * CSS grid auto rows.
     */
    open var gridAutoRows: String? by refreshOnUpdate {
        setStyleProperty("grid-auto-rows", it)
    }

    /**
     * CSS grid auto flow.
     */
    open var gridAutoFlow: GridAutoFlow? by refreshOnUpdate {
        setStyleProperty("grid-auto-flow", it?.flow)
    }

    /**
     * CSS grid columns template.
     */
    open var gridTemplateColumns: String? by refreshOnUpdate {
        setStyleProperty("grid-template-columns", it)
    }

    /**
     * CSS grid rows template.
     */
    open var gridTemplateRows: String? by refreshOnUpdate {
        setStyleProperty("grid-template-rows", it)
    }

    /**
     * CSS grid areas template.
     */
    open var gridTemplateAreas: List<String>? by refreshOnUpdate {
        setStyleProperty("grid-template-areas", it?.joinToString("\n"))
    }

    /**
     * CSS grid column gap.
     */
    open var gridColumnGap: Int? by refreshOnUpdate {
        setStyleProperty("grid-column-gap", it?.let { "${it}px" })
    }

    /**
     * CSS grid row gap.
     */
    open var gridRowGap: Int? by refreshOnUpdate {
        setStyleProperty("grid-row-gap", it?.let { "${it}px" })
    }

    /**
     * CSS grid column start.
     */
    open var gridColumnStart: Int? by refreshOnUpdate {
        setStyleProperty("grid-column-start", it?.let { "$it" })
    }

    /**
     * CSS grid row start.
     */
    open var gridRowStart: Int? by refreshOnUpdate {
        setStyleProperty("grid-row-start", it?.let { "$it" })
    }

    /**
     * CSS grid column end.
     */
    open var gridColumnEnd: String? by refreshOnUpdate {
        setStyleProperty("grid-column-end", it)
    }

    /**
     * CSS grid row end.
     */
    open var gridRowEnd: String? by refreshOnUpdate {
        setStyleProperty("grid-row-end", it)
    }

    /**
     * CSS grid area.
     */
    open var gridArea: String? by refreshOnUpdate {
        setStyleProperty("grid-area", it)
    }

    /**
     * Outline of the current component.
     */
    open var outline: Outline? by refreshOnUpdate {
        setStyleProperty("outline", it?.asString())
    }

    /**
     * Box shadow of the current component.
     */
    open var boxShadow: BoxShadow? by refreshOnUpdate {
        if (it != null && boxShadowList != null) boxShadowList = null
        setStyleProperty("box-shadow", it?.asString())
        setStyleProperty("-webkit-box-shadow", it?.asString())
    }

    /**
     * List of box shadows of the current component.
     */
    open var boxShadowList: List<BoxShadow>? by refreshOnUpdate {
        if (it != null && boxShadow != null) boxShadow = null
        val value = it?.joinToString { s -> s.asString() }
        setStyleProperty("box-shadow", value)
        setStyleProperty("-webkit-box-shadow", value)
    }

    /**
     * CSS transition effect for the current component.
     */
    open var transition: Transition? by refreshOnUpdate {
        if (it != null && transitionList != null) transitionList = null
        setStyleProperty("transition", it?.asString())
    }

    /**
     * List of CSS transition effects for the current component.
     */
    open var transitionList: List<Transition>? by refreshOnUpdate {
        if (it != null && transition != null) transition = null
        setStyleProperty("transition", it?.joinToString { t -> t.asString() })
    }

    /**
     * CSS border radius.
     */
    open var borderRadius: CssSize? by refreshOnUpdate {
        if (it != null && borderRadiusList != null) borderRadiusList = null
        setStyleProperty("border-radius", it?.asString())
        setStyleProperty("-webkit-border-radius", it?.asString())
    }

    /**
     * List of CSS border radius values.
     */
    open var borderRadiusList: List<CssSize>? by refreshOnUpdate {
        if (it != null && borderRadius != null) borderRadius = null
        val value = it?.joinToString(" ") { s -> s.asString() }
        setStyleProperty("border-radius", value)
        setStyleProperty("-webkit-border-radius", value)
    }

    /**
     * List style of the current component.
     */
    open var listStyle: ListStyle? by refreshOnUpdate {
        setStyleProperty("list-style", it?.asString())
    }

    /**
     * Returns CSS style attributes.
     */
    open fun getSnStyle(): dynamic {
        return snStyleCache ?: run {
            val s = js("Object").assign(js("{}"), propertyStyles)
            snStyleCache = s
            s
        }
    }

    /**
     * Returns the value of a custom CSS style.
     * @param name the name of the style
     * @return the value of the style
     */
    fun getStyle(name: String): String? {
        @Suppress("UnsafeCastFromDynamic")
        return this.propertyStyles[name]
    }

    /**
     * Sets the value of a custom CSS style.
     * @param name the name of the style
     * @param value the value of the style
     */
    fun setStyle(name: String, value: String) {
        this.propertyStyles[name] = value
        refresh()
    }

    /**
     * Removes the value of a custom CSS style.
     * @param name the name of the style
     */
    fun removeStyle(name: String) {
        delete(this.propertyStyles, name)
        refresh()
    }

    /**
     * @suppress
     * Internal function
     * Re-renders the current component.
     */
    open fun refresh() {
        snStyleCache = null
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun <T> refreshOnUpdate(noinline refreshFunction: ((T) -> Unit)? = null) =
        RefreshDelegateProvider(refreshFunction)

    private inner class RefreshDelegateProvider<T>(
        private val refreshFunction: ((T) -> Unit)?
    ) {
        operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): RefreshDelegate<T> {
            return RefreshDelegate(refreshFunction)
        }
    }

    private inner class RefreshDelegate<T>(private val refreshFunction: ((T) -> Unit)?) {
        @Suppress("UNCHECKED_CAST")
        operator fun getValue(thisRef: StyledComponent, property: KProperty<*>): T {
            val value = propertyValues[property.name]
            return if (value != null) {
                value.unsafeCast<T>()
            } else {
                null.unsafeCast<T>()
            }
        }

        operator fun setValue(thisRef: StyledComponent, property: KProperty<*>, value: T) {
            val oldValue = propertyValues[property.name]
            if (value == null) {
                delete(propertyValues, property.name)
            } else {
                propertyValues[property.name] = value
            }
            if (oldValue != value) {
                refreshFunction?.invoke(value)
                refresh()
            }
        }
    }
}
