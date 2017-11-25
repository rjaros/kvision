package pl.treksoft.kvision.panel

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Component

open class StackPanel(private val activateLast: Boolean = true,
                      classes: Set<String> = setOf()) : SimplePanel(classes) {
    var activeIndex = -1
        set(value) {
            field = value
            refresh()
        }

    override fun childrenVNodes(): Array<VNode> {
        return if (activeIndex >= 0 && activeIndex < children.size) {
            arrayOf(children[activeIndex].renderVNode())
        } else {
            arrayOf()
        }
    }

    override fun add(child: Component): StackPanel {
        super.add(child)
        if (activateLast) activeIndex = children.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun addAll(children: List<Component>): StackPanel {
        super.addAll(children)
        if (activateLast) activeIndex = this.children.size - 1
        else if (activeIndex == -1) activeIndex = 0
        return this
    }

    override fun remove(child: Component): StackPanel {
        super.remove(child)
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }

    override fun removeAll(): StackPanel {
        super.removeAll()
        if (activeIndex > children.size - 1) activeIndex = children.size - 1
        return this
    }
}
