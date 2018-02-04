package pl.treksoft.kvision.core

import pl.treksoft.kvision.utils.asString

abstract class StyledComponent : Component {

    open var width: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var minWidth: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var maxWidth: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var height: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var minHeight: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var maxHeight: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var border: Border? = null
        set(value) {
            field = value
            refresh()
        }
    var borderTop: Border? = null
        set(value) {
            field = value
            refresh()
        }
    var borderRight: Border? = null
        set(value) {
            field = value
            refresh()
        }
    var borderBottom: Border? = null
        set(value) {
            field = value
            refresh()
        }
    var borderLeft: Border? = null
        set(value) {
            field = value
            refresh()
        }
    var margin: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var marginTop: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var marginRight: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var marginBottom: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var marginLeft: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var padding: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var paddingTop: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var paddingRight: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var paddingBottom: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var paddingLeft: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var color: Color? = null
        set(value) {
            field = value
            refresh()
        }
    var colorHex: Int?
        get() = null
        set(value) {
            color = if (value != null) Color(value) else null
        }
    var colorName: COLOR?
        get() = null
        set(value) {
            color = if (value != null) Color(value) else null
        }
    var opacity: Double? = null
        set(value) {
            field = value
            refresh()
        }
    var background: Background? = null
        set(value) {
            field = value
            refresh()
        }


    private var snStyleCache: List<StringPair>? = null

    open fun refresh(): StyledComponent {
        snStyleCache = null
        return this
    }

    protected fun getSnStyleInternal(): List<StringPair> {
        return snStyleCache ?: {
            val s = getSnStyle()
            snStyleCache = s
            s
        }()
    }

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
