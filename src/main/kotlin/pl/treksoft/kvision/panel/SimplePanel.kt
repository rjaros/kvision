package pl.treksoft.kvision.panel

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Widget

open class SimplePanel(classes: Set<String> = setOf()) : Widget(classes), Container {
    internal val children: MutableList<Component> = mutableListOf()

    override fun render(): VNode {
        return kvh("div", childrenVNodes())
    }

    protected open fun childrenVNodes(): Array<VNode> {
        return children.filter { it.visible }.map { it.renderVNode() }.toTypedArray()
    }

    protected fun addInternal(child: Component): SimplePanel {
        children.add(child)
        child.parent = this
        refresh()
        return this
    }

    override fun add(child: Component): SimplePanel {
        return addInternal(child)
    }

    override fun addAll(children: List<Component>): SimplePanel {
        this.children.addAll(children)
        children.map { it.parent = this }
        refresh()
        return this
    }

    override fun remove(child: Component): SimplePanel {
        if (children.remove(child)) {
            child.clearParent()
            refresh()
        }
        return this
    }

    override fun removeAll(): SimplePanel {
        children.map { it.clearParent() }
        children.clear()
        refresh()
        return this
    }

    override fun getChildren(): List<Component> {
        return ArrayList(children)
    }

    override fun dispose() {
        children.forEach { it.dispose() }
        removeAll()
    }
}
