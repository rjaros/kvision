package pl.treksoft.kvision.core

import com.github.snabbdom.VNode
import pl.treksoft.kvision.snabbdom.StringBoolPair

class Root(id: String, private val fluid: Boolean = false) : Container() {
    private var rootVnode: VNode = render()

    init {
        rootVnode = KVManager.patch(id, this.render())
        this.id = id
    }

    override fun render(): VNode {
        return kvh("div#" + id, childrenVNodes())
    }

    override fun getSnClass(): List<StringBoolPair> {
        val css = if (fluid) "container-fluid" else "container"
        return super.getSnClass() + (css to true)
    }

    override fun refresh() {
        rootVnode = KVManager.patch(rootVnode, render())
    }

}
