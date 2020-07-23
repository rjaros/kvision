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
package pl.treksoft.kvision

import com.github.snabbdom.Snabbdom
import com.github.snabbdom.VNode
import com.github.snabbdom.attributesModule
import com.github.snabbdom.classModule
import com.github.snabbdom.datasetModule
import com.github.snabbdom.eventListenersModule
import com.github.snabbdom.propsModule
import com.github.snabbdom.styleModule
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.dom.clear

/**
 * @suppress
 * External function for loading CommonJS modules.
 */
external fun require(name: String): dynamic

/**
 * Internal singleton object which initializes and configures KVision framework.
 */
@Suppress("EmptyCatchBlock", "TooGenericExceptionCaught")
object KVManager {
    init {
        try {
            require("kvision-kvision-bootstrap-css").pl.treksoft.kvision.KVManagerBootstrapCss
        } catch (e: Throwable) {
        }
        try {
            require("kvision-kvision-bootstrap").pl.treksoft.kvision.KVManagerBootstrap
        } catch (e: Throwable) {
        }
        try {
            require("kvision-kvision-fontawesome").pl.treksoft.kvision.KVManagerFontAwesome
        } catch (e: Throwable) {
        }
        try {
            require("kvision-kvision-onsenui-css").pl.treksoft.kvision.KVManagerOnsenuiCss
        } catch (e: Throwable) {
        }
        require("kvision-assets/css/style.css")
        require("jquery-resizable-dom")
    }

    internal val fecha = require("fecha").default
    private val sdPatch = Snabbdom.init(
        arrayOf(
            classModule, attributesModule, propsModule, styleModule,
            eventListenersModule, datasetModule
        )
    )
    private val sdVirtualize = require("snabbdom-virtualize/strings").default

    internal fun patch(id: String, vnode: VNode): VNode {
        val container = document.getElementById(id)
        container?.clear()
        return sdPatch(container, vnode)
    }

    internal fun patch(element: HTMLElement, vnode: VNode): VNode {
        return sdPatch(element, vnode)
    }

    internal fun patch(oldVNode: VNode, newVNode: VNode): VNode {
        return sdPatch(oldVNode, newVNode)
    }

    /**
     * @suppress
     * Internal function.
     */
    @Suppress("UnsafeCastFromDynamic")
    fun virtualize(html: String): VNode {
        return sdVirtualize(html)
    }
}
