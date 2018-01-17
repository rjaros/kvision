package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import com.github.snabbdom.h
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringBoolPair

enum class LIST(val tagName: String) {
    UL("ul"),
    OL("ol"),
    UNSTYLED("ul"),
    INLINE("ul"),
    DL("dl"),
    DL_HORIZ("dl")
}

open class ListTag(
    type: LIST, elements: List<String>? = null, rich: Boolean = false,
    classes: Set<String> = setOf()
) : SimplePanel(classes) {
    var type = type
        set(value) {
            field = value
            refresh()
        }
    private var elements = elements
        set(value) {
            field = value
            refresh()
        }
    private var rich = rich
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
        return if (childrenElements != null) {
            kvh(type.tagName, childrenElements + childrenVNodes())
        } else {
            kvh(type.tagName, childrenVNodes())
        }
    }

    override fun childrenVNodes(): Array<VNode> {
        val childrenElements = children.filter { it.visible }
        val res = when (type) {
            LIST.UL, LIST.OL, LIST.UNSTYLED, LIST.INLINE -> childrenElements.map { v ->
                if (v is Tag && v.type == TAG.LI) {
                    v.renderVNode()
                } else {
                    h("li", arrayOf(v.renderVNode()))
                }
            }
            LIST.DL, LIST.DL_HORIZ -> childrenElements.mapIndexed { index, v ->
                if (v is Tag && v.type == TAG.LI) {
                    v.renderVNode()
                } else {
                    h(if (index % 2 == 0) "dt" else "dd", arrayOf(v.renderVNode()))
                }
            }
        }
        return res.toTypedArray()
    }

    private fun element(name: String, value: String, rich: Boolean): VNode {
        return if (rich) {
            h(name, arrayOf(KVManager.virtualize("<span>$value</span>")))
        } else {
            h(name, value)
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (type) {
            LIST.UNSTYLED -> cl.add("list-unstyled" to true)
            LIST.INLINE -> cl.add("list-inline" to true)
            LIST.DL_HORIZ -> cl.add("dl-horizontal" to true)
        }
        return cl
    }
}
