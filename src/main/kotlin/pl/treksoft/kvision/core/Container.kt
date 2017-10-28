package pl.treksoft.kvision.core

import com.github.snabbdom.VNode

interface Container {
    var parent: Widget?
    var visible: Boolean
    fun renderVNode(): VNode
    fun add(child: Widget): Container
    fun addAll(children: List<Widget>): Container
    fun remove(child: Widget): Container
    fun removeAll(): Container
    fun getChildren(): List<Widget>
}
