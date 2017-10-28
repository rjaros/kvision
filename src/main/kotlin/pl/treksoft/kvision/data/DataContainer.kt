package pl.treksoft.kvision.data

import com.github.snabbdom.VNode
import com.lightningkite.kotlin.observable.list.ObservableList
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.panel.VPanel

class DataContainer<M : DataComponent, C : Widget>(val model: ObservableList<M>,
                                                        private val binding: (M, Int) -> C,
                                                        private val child: Container = VPanel()) :
        Widget(setOf()), Container, DataUpdatable {

    override var visible
        get() = child.visible
        set(value) {
            child.visible = value
        }

    init {
        child.parent = this
        model.onUpdate += { _ ->
            update()
        }
        update()
    }

    override fun add(child: Widget): Container {
        return this.child.add(child)
    }

    override fun addAll(children: List<Widget>): Container {
        return this.child.addAll(children)
    }

    override fun remove(child: Widget): Container {
        return this.child.remove(child)
    }

    override fun removeAll(): Container {
        return this.child.removeAll()
    }

    override fun getChildren(): List<Widget> {
        return this.child.getChildren()
    }

    override fun renderVNode(): VNode {
        return this.child.renderVNode()
    }

    override fun update() {
        model.forEach { it.container = this }
        singleRender {
            child.removeAll()
            child.addAll(model.mapIndexed { index, m -> binding(m, index) })
        }
    }

}
