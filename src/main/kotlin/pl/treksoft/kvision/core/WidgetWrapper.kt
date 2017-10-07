package pl.treksoft.kvision.core

import com.github.snabbdom.VNode

open class WidgetWrapper(internal var delegate: Widget?, classes: Set<String> = setOf()) : Widget(classes) {

    override var visible
        get() = delegate?.visible == true
        set(value) {
            delegate?.visible = value
        }

    init {
        @Suppress("LeakingThis")
        delegate?.parent = this
    }

    override fun render(): VNode {
        return delegate?.let {
            kvh("div", arrayOf(it.renderVNode()))
        } ?: kvh("div")
    }

    override fun dispose() {
        delegate?.clearParent()
        delegate = null
    }

}
