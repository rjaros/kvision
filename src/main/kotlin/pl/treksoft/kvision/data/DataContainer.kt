package pl.treksoft.kvision.data

import com.github.snabbdom.VNode
import com.lightningkite.kotlin.observable.list.ObservableList
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.panel.VPanel

class DataContainer<M : DataComponent, C : Widget>(
    val model: ObservableList<M>,
    private val binding: (Int) -> C,
    private val child: Container = VPanel()
) :
    Widget(setOf()), Container, DataUpdatable {

    override var visible
        get() = child.visible
        set(value) {
            child.visible = value
        }

    internal var onUpdateHandler: (() -> Unit)? = null

    init {
        child.parent = this
        model.onUpdate += { _ ->
            update()
        }
        update()
    }

    override fun add(child: Component): Container {
        this.child.add(child)
        return this
    }

    override fun addAll(children: List<Component>): Container {
        this.child.addAll(children)
        return this
    }

    override fun remove(child: Component): Container {
        this.child.remove(child)
        return this
    }

    override fun removeAll(): Container {
        this.child.removeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return this.child.getChildren()
    }

    override fun renderVNode(): VNode {
        return this.child.renderVNode()
    }

    fun get(index: Int): M = model[index]

    override fun update() {
        model.forEach { it.container = this }
        singleRender {
            child.removeAll()
            child.addAll(model.mapIndexed { index, _ -> binding(index) })
        }
        onUpdateHandler?.invoke()
    }

    fun onUpdate(handler: () -> Unit): DataContainer<M, C> {
        onUpdateHandler = handler
        return this
    }

    fun clearOnUpdate(): DataContainer<M, C> {
        onUpdateHandler = null
        return this
    }
}
