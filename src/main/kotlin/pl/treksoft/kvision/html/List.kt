package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import com.github.snabbdom.h
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.snabbdom.StringBoolPair

enum class LIST(val tagName: String) {
    UL("ul"),
    OL("ol"),
    UNSTYLED("ul"),
    INLINE("ul"),
    DL("dl"),
    DL_HORIZ("dl")
}

open class ListTag(type: LIST, elements: List<String>? = null, rich: Boolean = false,
                   classes: Set<String> = setOf()) : Container(classes) {
    var type = type
        set(value) {
            field = value
            refresh()
        }
    var elements = elements
        set(value) {
            field = value
            refresh()
        }
    var rich = rich
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        val childrenElements = when (type) {
            LIST.UL, LIST.OL, LIST.UNSTYLED, LIST.INLINE -> elements?.map { el -> element("li", el, rich) }
            LIST.DL, LIST.DL_HORIZ -> elements?.mapIndexed { index, el ->
                element(if (index % 2 == 0) "dt" else "dd", el, rich)
            }
        }?.toTypedArray()
        if (childrenElements != null) {
            return kvh(type.tagName, childrenElements + childrenVNodes())
        } else {
            return kvh(type.tagName, childrenVNodes())
        }
    }

    override fun childrenVNodes(): Array<VNode> {
        val childrenElements = children.filter { it.visible }
        val res = when (type) {
            LIST.UL, LIST.OL, LIST.UNSTYLED, LIST.INLINE -> childrenElements.map { v ->
                if (v is Tag && v.type == TAG.LI) {
                    v.render()
                } else {
                    h("li", arrayOf(v.render()))
                }
            }
            LIST.DL, LIST.DL_HORIZ -> childrenElements.mapIndexed { index, v ->
                if (v is Tag && v.type == TAG.LI) {
                    v.render()
                } else {
                    h(if (index % 2 == 0) "dt" else "dd", arrayOf(v.render()))
                }
            }
        }
        return res.toTypedArray()
    }

    private fun element(name: String, value: String, rich: Boolean): VNode {
        if (rich) {
            return h(name, arrayOf(KVManager.virtualize("<span>$value</span>")))
        } else {
            return h(name, value)
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (type == LIST.UNSTYLED) {
            cl.add("list-unstyled" to true)
        } else if (type == LIST.INLINE) {
            cl.add("list-inline" to true)
        } else if (type == LIST.DL_HORIZ) {
            cl.add("dl-horizontal" to true)
        }
        return cl
    }
}
