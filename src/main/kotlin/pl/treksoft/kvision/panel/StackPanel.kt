package pl.treksoft.kvision.panel

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget

open class StackPanel(private val activateLast: Boolean = true,
                      classes: Set<String> = setOf()) : Container(classes) {
    var activeIndex = -1
        set(value) {
            field = value
            refresh()
        }

    override fun childrenVNodes(): Array<VNode> {
        return if (activeIndex >= 0 && activeIndex < children.size) {
            arrayOf(children[activeIndex].render())
        } else {
            arrayOf()
        }
    }

    override fun add(child: Widget): Container {
        super.add(child)
        if (activateLast) activeIndex = children.size - 1
        return this
    }

    override fun addAll(children: List<Widget>): Container {
        super.addAll(children)
        if (activateLast) activeIndex = this.children.size - 1
        return this
    }

    override fun remove(child: Widget): Container {
        super.remove(child)
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }

    override fun removeAt(index: Int): Container {
        super.removeAt(index)
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }

    override fun removeAll(): Container {
        super.removeAll()
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }
}
