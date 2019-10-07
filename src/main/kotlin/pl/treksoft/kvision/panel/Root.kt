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
package pl.treksoft.kvision.panel

import com.github.snabbdom.VNode
import com.github.snabbdom.h
import org.w3c.dom.HTMLElement
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.Style
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.utils.snClasses
import pl.treksoft.kvision.utils.snOpt

/**
 * Root container.
 *
 * This container is bound to the specific element in the main HTML file of the project.
 * It is always the root of components tree and it is responsible for rendering and updating
 * Snabbdom virtual DOM.
 *
 * @constructor
 * @param id ID attribute of element in the main HTML file
 * @param fixed if false, the container is rendered with Bootstrap "container-fluid" class,
 * otherwise it's rendered with "container" class (default is false)
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
class Root(
    id: String? = null,
    element: HTMLElement? = null,
    private val fixed: Boolean = false,
    init: (Root.() -> Unit)? = null
) : SimplePanel() {
    private val contextMenus: MutableList<Widget> = mutableListOf()
    private var rootVnode: VNode = renderVNode()

    internal var renderDisabled = false

    val isFirstRoot = roots.isEmpty()

    init {
        if (id != null) {
            rootVnode = KVManager.patch(id, this.renderVNode())
            this.id = id
        } else if (element != null) {
            rootVnode = KVManager.patch(element, this.renderVNode())
            this.id = "kv_root_${counter++}"
        } else {
            throw IllegalArgumentException("No root element specified!")
        }
        roots.add(this)
        if (isFirstRoot) {
            modals.forEach { it.parent = this }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return if (!fixed) {
            render("div#$id", arrayOf(h("div", snOpt {
                `class` = snClasses(listOf("row" to true))
            }, stylesVNodes() + childrenVNodes() + modalsVNodes() + contextMenusVNodes())))
        } else {
            render("div#$id", stylesVNodes() + childrenVNodes() + modalsVNodes() + contextMenusVNodes())
        }
    }

    fun addContextMenu(contextMenu: Widget) {
        contextMenus.add(contextMenu)
        contextMenu.parent = this
        this.setInternalEventListener<Root> {
            click = { e ->
                @Suppress("UnsafeCastFromDynamic")
                if (!e.asDynamic().dropDownCM) contextMenu.hide()
            }
        }
        refresh()
    }

    private fun stylesVNodes(): Array<VNode> {
        return if (isFirstRoot) {
            if (Style.styles.isNotEmpty()) {
                val stylesDesc = Style.styles.joinToString("\n") { it.generateStyle() }
                arrayOf(h("style", arrayOf(stylesDesc)))
            } else {
                arrayOf()
            }
        } else {
            arrayOf()
        }
    }

    private fun modalsVNodes(): Array<VNode> {
        return if (isFirstRoot) {
            modals.filter { it.visible }.map { it.renderVNode() }.toTypedArray()
        } else {
            arrayOf()
        }
    }

    private fun contextMenusVNodes(): Array<VNode> {
        return contextMenus.filter { it.visible }.map { it.renderVNode() }.toTypedArray()
    }

    override fun getSnClass(): List<StringBoolPair> {
        val css = if (!fixed) "container-fluid" else "container"
        return super.getSnClass() + (css to true)
    }

    internal fun reRender(): Root {
        if (!renderDisabled) {
            rootVnode = KVManager.patch(rootVnode, renderVNode())
        }
        return this
    }

    internal fun restart() {
        rootVnode = KVManager.patch(rootVnode, h("div"))
        rootVnode = KVManager.patch(rootVnode, renderVNode())
    }

    override fun getRoot(): Root? {
        return this
    }

    override fun dispose() {
        super.dispose()
        roots.remove(this)
        if (isFirstRoot) {
            Style.styles.clear()
            modals.clear()
        }
    }

    companion object {
        internal var counter = 0
        private val modals: MutableList<Widget> = mutableListOf()

        /**
         * @suppress internal function
         */
        fun shutdown() {
            roots.forEach { it.dispose() }
            roots.clear()
        }

        internal val roots: MutableList<Root> = mutableListOf()

        fun getFirstRoot(): Root? {
            return if (roots.isNotEmpty())
                roots[0]
            else
                null
        }

        fun getLastRoot(): Root? {
            return if (roots.isNotEmpty())
                roots[roots.size - 1]
            else
                null
        }

        fun addModal(modal: Widget) {
            modals.add(modal)
        }

        fun removeModal(modal: Widget) {
            modals.remove(modal)
        }
    }
}
