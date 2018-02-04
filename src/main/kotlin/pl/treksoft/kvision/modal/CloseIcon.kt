package pl.treksoft.kvision.modal

import com.github.snabbdom.VNode
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair

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
