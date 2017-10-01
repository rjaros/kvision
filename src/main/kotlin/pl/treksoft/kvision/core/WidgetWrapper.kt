package pl.treksoft.kvision.core

import com.github.snabbdom.VNode

open class WidgetWrapper(private var delegate: Widget, classes: Set<String> = setOf()) : Widget(classes) {

    override fun render(): VNode {
        val children = if (delegate.visible) {
            arrayOf(delegate.render())
        } else {
            arrayOf()
        }
        return kvh("div", children)
    }

}
