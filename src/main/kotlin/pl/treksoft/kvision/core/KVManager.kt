package pl.treksoft.kvision.core

import com.github.snabbdom.Snabbdom
import com.github.snabbdom.VNode
import com.github.snabbdom.attributesModule
import com.github.snabbdom.classModule
import com.github.snabbdom.datasetModule
import com.github.snabbdom.eventListenersModule
import com.github.snabbdom.propsModule
import com.github.snabbdom.styleModule
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.require
import pl.treksoft.kvision.routing.routing
import kotlin.browser.document
import kotlin.dom.clear

object KVManager {
    private val bootstrapWebpack = require("bootstrap-webpack")
    private val fontAwesomeWebpack = require("font-awesome-webpack")

    private val sdPatch = Snabbdom.init(arrayOf(classModule, attributesModule, propsModule, styleModule,
            eventListenersModule, datasetModule))
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

    fun init() {
        jQuery(document).off(".data-api")
    }

    fun shutdown() {
        routing.destroy()
    }
}
