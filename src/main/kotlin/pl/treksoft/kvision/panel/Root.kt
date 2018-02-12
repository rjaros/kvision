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
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.modal.Modal

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
class Root(id: String, private val fixed: Boolean = false, init: (Root.() -> Unit)? = null) : SimplePanel() {
    private val modals: MutableList<Modal> = mutableListOf()
    private var rootVnode: VNode = renderVNode()

    internal var renderDisabled = false

    init {
        rootVnode = KVManager.patch(id, this.renderVNode())
        this.id = id
        roots.add(this)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("div#" + id, childrenVNodes() + modalsVNodes())
    }

    internal fun addModal(modal: Modal) {
        modals.add(modal)
        modal.parent = this
        refresh()
    }

    private fun modalsVNodes(): Array<VNode> {
        return modals.filter { it.visible }.map { it.renderVNode() }.toTypedArray()
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

    override fun getRoot(): Root? {
        return this
    }

    companion object {
        private val roots: MutableList<Root> = mutableListOf()

        internal fun getLastRoot(): Root? {
            return if (roots.size > 0)
                roots[roots.size - 1]
            else
                null
        }
    }
}
