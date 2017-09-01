package pl.treksoft.kvision.core

import com.github.snabbdom.*
import pl.treksoft.kvision.require
import pl.treksoft.kvision.routing.routing
import kotlin.browser.document
import kotlin.dom.clear

object KVManager {
    private val bootstrap_webpack = require("bootstrap-webpack")
    private val font_awesome_webpack = require("font-awesome-webpack")

    private val sdPatch = Snabbdom.init(arrayOf(classModule, attributesModule, propsModule, styleModule, eventListenersModule, datasetModule))
    private val sdVirtualize = require("snabbdom-virtualize/strings").default

    internal fun patch(id: String, vnode: VNode): VNode {
        val container = document.getElementById(id)
        container?.clear()
        return sdPatch(container, vnode)
    }

    internal fun patch(oldVNode: VNode, newVNode: VNode): VNode {
        return sdPatch(oldVNode, newVNode)
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun virtualize(html: String): VNode {
        return sdVirtualize(html)
    }

    fun shutdown() {
        routing.destroy()
    }
}