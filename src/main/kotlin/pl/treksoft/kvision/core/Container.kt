package pl.treksoft.kvision.core

import com.github.snabbdom.VNode

open class Container(classes: Set<String> = setOf()) : Widget(classes) {
    private val children: MutableList<Widget> = mutableListOf()

    override fun render(): VNode {
        return kvh("div", childrenVNodes())
    }

    protected open fun childrenVNodes(): Array<VNode> {
        return children.filter { it.visible }.map { it.render() }.toTypedArray()
    }

    open fun add(child: Widget) {
        children.add(child)
        child.parent = this
        refresh()
    }

    open fun remove(child: Widget) {
        children.remove(child)
        child.clearParent()
        refresh()
    }

    open fun removeAt(index: Int) {
        children.removeAt(index).clearParent()
        refresh()
    }
}