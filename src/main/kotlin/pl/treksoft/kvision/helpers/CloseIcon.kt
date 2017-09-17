package pl.treksoft.kvision.helpers

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

open class CloseIcon(classes: Set<String> = setOf()) : Widget(classes) {

    override fun render(): VNode {
        return kvh("button", arrayOf(KVManager.virtualize("<span aria-hidden='true'>&times;</span>")))
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("close" to true)
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("type" to "button", "aria-label" to "Close")
    }

}
