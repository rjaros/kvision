package pl.treksoft.kvision.core

import com.github.snabbdom.VNode
import pl.treksoft.kvision.modal.Modal
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringBoolPair

class Root(id: String, private val fixed: Boolean = false) : SimplePanel() {
    private val modals: MutableList<Modal> = mutableListOf()
    private var rootVnode: VNode = renderVNode()

    internal var renderDisabled = false

    init {
        rootVnode = KVManager.patch(id, this.renderVNode())
        this.id = id
        roots.add(this)
    }

    override fun render(): VNode {
        return kvh("div#" + id, childrenVNodes() + modalsVNodes())
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
