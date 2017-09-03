package pl.treksoft.kvision.core

import com.github.snabbdom.On
import com.github.snabbdom.VNode
import com.github.snabbdom.VNodeData
import com.github.snabbdom.h
import pl.treksoft.kvision.snabbdom.*

open class Widget(classes: Set<String> = setOf()) : KVObject {

    val classes = classes.toMutableSet()
    val listeners = mutableListOf<SnOn<Widget>.() -> Unit>()

    var parent: Widget? = null
        internal set

    var visible: Boolean = true
        set(value) {
            field = value
            refresh()
        }
    var title: String? = null
        set(value) {
            field = value
            refresh()
        }
    var id: String? = null
        set(value) {
            field = value
            refresh()
        }
    var role: String? = null
        set(value) {
            field = value
            refresh()
        }

    internal open fun render(): VNode {
        return kvh("div")
    }

    protected open fun kvh(s: String): VNode {
        return h(s, getSnOpt())
    }

    protected open fun kvh(s: String, children: Array<dynamic>): VNode {
        return h(s, getSnOpt(), children)
    }

    protected open fun getSnOpt(): VNodeData {
        return snOpt {
            attrs = snAttrs(* getSnAttrs().toTypedArray())
            style = snStyle(* getSnStyle().toTypedArray())
            `class` = snClasses(* getSnClass().toTypedArray())
            on = getSnOn()
        }
    }

    protected open fun getSnStyle(): List<StringPair> {
        return listOf()
    }

    protected open fun getSnClass(): List<StringBoolPair> {
        return classes.map { c -> c to true } + if (visible) listOf() else listOf("hidden" to true)
    }


    protected open fun getSnAttrs(): List<StringPair> {
        val snattrs = mutableListOf<StringPair>()
        if (id != null) {
            snattrs.add("id" to id.orEmpty())
        }
        if (title != null) {
            snattrs.add("title" to title.orEmpty())
        }
        if (role != null) {
            snattrs.add("role" to role.orEmpty())
        }
        return snattrs
    }

    protected open fun getSnOn(): On {
        val handlers = On(this)
        listeners.forEach { on -> (handlers::apply)(on) }
        return handlers
    }

    @Suppress("UNCHECKED_CAST")
    open fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit) {
        listeners.add(block as SnOn<Widget>.() -> Unit)
        refresh()
    }

    open fun setEventListener(block: SnOn<Widget>.() -> Unit) {
        listeners.add(block)
        refresh()
    }

    open fun removeEventListeners() {
        listeners.clear()
        refresh()
    }

    open fun show() {
        visible = true
    }

    open fun hide() {
        visible = false
    }

    open fun addCssClass(css: String) {
        this.classes.add(css)
        refresh()
    }

    open fun removeCssClass(css: String) {
        this.classes.remove(css)
        refresh()
    }

    internal fun clearParent() {
        this.parent = null
    }

    protected open fun refresh() {
        this.parent?.refresh()
    }
}