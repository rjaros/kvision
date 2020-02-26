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
import pl.treksoft.kvision.Application
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.Style
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.core.onEvent
import pl.treksoft.kvision.utils.snClasses
import pl.treksoft.kvision.utils.snOpt
import kotlin.browser.document

/**
 * Root container.
 *
 * This container is bound to the specific element in the main HTML file of the project.
 * It is always the root of components tree and it is responsible for rendering and updating
 * Snabbdom virtual DOM.
 */
@Suppress("TooManyFunctions")
class Root : SimplePanel {

    private val fixed: Boolean
    private val contextMenus: MutableList<Widget> = mutableListOf()
    private var rootVnode: VNode? = null

    internal var renderDisabled = false

    val isFirstRoot = roots.isEmpty()

    /**
     * @constructor
     * @param id ID attribute of element in the main HTML file
     * @param fixed if false, the container is rendered with Bootstrap "container-fluid" class,
     * otherwise it's rendered with "container" class (default is false)
     * @param init an initializer extension function
     */
    constructor(id: String, fixed: Boolean = false, init: (Root.() -> Unit)? = null) : super() {
        this.fixed = fixed
        if (document.getElementById(id) != null) {
            rootVnode = KVManager.patch(id, this.renderVNode())
        }
        this.id = id
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * @constructor
     * @param element HTML element in the DOM tree
     * @param fixed if false, the container is rendered with Bootstrap "container-fluid" class,
     * otherwise it's rendered with "container" class (default is false)
     * @param init an initializer extension function
     */
    constructor(element: HTMLElement, fixed: Boolean = false, init: (Root.() -> Unit)? = null) : super() {
        this.fixed = fixed
        rootVnode = KVManager.patch(element, this.renderVNode())
        this.id = "kv_root_${counter++}"
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    init {
        roots.add(this)
        if (isFirstRoot) {
            modals.forEach { it.parent = this }
        }
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
        this.onEvent {
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
        if (!renderDisabled && rootVnode != null) {
            rootVnode = KVManager.patch(rootVnode!!, renderVNode())
        }
        return this
    }

    internal fun restart() {
        if (rootVnode != null) {
            rootVnode = KVManager.patch(rootVnode!!, h("div"))
            rootVnode = KVManager.patch(rootVnode!!, renderVNode())
        }
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
        fun disposeAllRoots() {
            roots.forEach { it.dispose() }
            roots.clear()
        }

        internal val roots: MutableList<Root> = mutableListOf()

        /**
         * @suppress internal function
         */
        fun getFirstRoot(): Root? {
            return if (roots.isNotEmpty())
                roots[0]
            else
                null
        }

        /**
         * @suppress internal function
         */
        fun getLastRoot(): Root? {
            return if (roots.isNotEmpty())
                roots[roots.size - 1]
            else
                null
        }

        /**
         * @suppress internal function
         */
        fun addModal(modal: Widget) {
            modals.add(modal)
        }

        /**
         * @suppress internal function
         */
        fun removeModal(modal: Widget) {
            modals.remove(modal)
        }
    }
}

/**
 * Create new Root container based on ID
 * @param id ID attribute of element in the main HTML file
 * @param fixed if false, the container is rendered with Bootstrap "container-fluid" class,
 * otherwise it's rendered with "container" class (default is false)
 * @param init an initializer extension function
 * @return the created Root container
 */
@Suppress("unused")
fun Application.root(id: String, fixed: Boolean = false, init: Root.() -> Unit): Root {
    return Root(id, fixed, init)
}

/**
 * Create new Root container based on HTML element
 * @param element HTML element in the DOM tree
 * @param fixed if false, the container is rendered with Bootstrap "container-fluid" class,
 * otherwise it's rendered with "container" class (default is false)
 * @param init an initializer extension function
 * @return the created Root container
 */
@Suppress("unused")
fun Application.root(element: HTMLElement, fixed: Boolean = false, init: Root.() -> Unit): Root {
    return Root(element, fixed, init)
}
