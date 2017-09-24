package pl.treksoft.kvision.core

import com.github.snabbdom.VNode

open class Container(classes: Set<String> = setOf()) : Widget(classes) {
    internal val children: MutableList<Widget> = mutableListOf()

    override fun render(): VNode {
        return kvh("div", childrenVNodes())
    }

    protected open fun childrenVNodes(): Array<VNode> {
        return children.filter { it.visible }.map { it.render() }.toTypedArray()
    }

    protected fun addInternal(child: Widget): Container {
        children.add(child)
        child.parent = this
        refresh()
        return this
    }

    open fun add(child: Widget): Container {
        return addInternal(child)
    }

    open fun addAll(children: List<Widget>): Container {
        this.children.addAll(children)
        children.map { it.parent = this }
        refresh()
        return this
    }

    open fun remove(child: Widget): Container {
        children.remove(child)
        child.clearParent()
        refresh()
        return this
    }

    open fun removeAt(index: Int): Container {
        children.removeAt(index).clearParent()
        refresh()
        return this
    }

    open fun removeAll(): Container {
        children.map { it.clearParent() }
        children.clear()
        refresh()
        return this
    }

    open fun getChildren(): List<Widget> {
        return ArrayList(children)
    }
}
