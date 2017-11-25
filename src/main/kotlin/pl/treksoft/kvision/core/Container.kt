package pl.treksoft.kvision.core

import com.github.snabbdom.VNode

interface Container {
    var parent: Component?
    var visible: Boolean
    fun renderVNode(): VNode
    fun add(child: Component): Container
    fun addAll(children: List<Component>): Container
    fun remove(child: Component): Container
    fun removeAll(): Container
    fun getChildren(): List<Component>
}
