package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair

enum class IMAGESHAPE(val className: String) {
    ROUNDED("img-rounded"),
    CIRCLE("img-circle"),
    THUMBNAIL("img-thumbnail")
}

open class Image(
    src: ResString, alt: String? = null, responsive: Boolean = false, shape: IMAGESHAPE? = null,
    centered: Boolean = false, classes: Set<String> = setOf()
) : Widget(classes) {
    internal var src = src
        set(value) {
            field = value
            refresh()
        }
    private var alt = alt
        set(value) {
            field = value
            refresh()
        }
    private var responsive = responsive
        set(value) {
            field = value
            refresh()
        }
    private var shape = shape
        set(value) {
            field = value
            refresh()
        }
    private var centered = centered
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return kvh("img")
    }

    override fun getSnAttrs(): List<StringPair> {
        val pr = super.getSnAttrs().toMutableList()
        pr.add("src" to src)
        alt?.let {
            pr.add("alt" to it)
        }
        return pr
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (responsive) {
            cl.add("img-responsive" to true)
        }
        if (centered) {
            cl.add("center-block" to true)
        }
        shape?.let {
            cl.add(it.className to true)
        }
        return cl
    }
}
