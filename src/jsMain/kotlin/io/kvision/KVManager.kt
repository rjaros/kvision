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
package io.kvision

import io.kvision.snabbdom.VNode
import io.kvision.snabbdom.attributesModule
import io.kvision.snabbdom.classModule
import io.kvision.snabbdom.eventListenersModule
import io.kvision.snabbdom.init
import io.kvision.snabbdom.propsModule
import io.kvision.snabbdom.styleModule
import kotlinx.browser.document
import kotlinx.dom.clear
import org.w3c.dom.HTMLElement

@JsModule("split.js")
internal external val splitjsModule: dynamic

@JsModule("fecha")
internal external val fechaModule: dynamic

@JsModule("@rjaros/snabbdom-virtualize/strings.js")
internal external val snabbdomVirtualizeModule: dynamic

/**
 * Singleton object which initializes and configures KVision framework.
 */
object KVManager {
    internal val splitjs = splitjsModule
    internal val fecha = fechaModule
    private val sdPatch = init(
        arrayOf(
            classModule, attributesModule, propsModule, styleModule, eventListenersModule
        )
    )
    private val sdVirtualize = snabbdomVirtualizeModule.default

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

    private val virtualizeCache = js("{}")

    /**
     * @suppress
     * Internal function.
     */
    @Suppress("UnsafeCastFromDynamic")
    fun virtualize(html: String): VNode {
        val node = virtualizeCache[html]
        return if (node != null) {
            JSON.parse<dynamic>(JSON.stringify(node))
        } else {
            val newNode = sdVirtualize(html)
            virtualizeCache[html] = newNode
            newNode
        }
    }
}
