package pl.treksoft.kvision.core

import com.github.snabbdom.VNode
import com.github.snabbdom.VNodeData
import com.github.snabbdom.h
import org.w3c.dom.Node
import pl.treksoft.jquery.JQuery
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.snabbdom.SnOn
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair
import pl.treksoft.kvision.snabbdom.hooks
import pl.treksoft.kvision.snabbdom.on
import pl.treksoft.kvision.snabbdom.snAttrs
import pl.treksoft.kvision.snabbdom.snClasses
import pl.treksoft.kvision.snabbdom.snOpt
import pl.treksoft.kvision.snabbdom.snStyle

@Suppress("TooManyFunctions")
open class Widget(classes: Set<String> = setOf()) : KVObject {

    val classes = classes.toMutableSet()
    val listeners = mutableListOf<SnOn<Widget>.() -> Unit>()

    var parent: Widget? = null
        internal set

    var visible: Boolean = true
        set(value) {
            val oldField = field
            field = value
            if (oldField != field) refresh()
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

    private var vnode: VNode? = null

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
            attrs = snAttrs(getSnAttrs())
            style = snStyle(getSnStyle())
            `class` = snClasses(getSnClass())
            on = getSnOn()
            hook = getSnHooks()
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

    protected open fun getSnOn(): com.github.snabbdom.On? {
        if (listeners.size > 0) {
            val handlers = on(this)
            listeners.forEach { l -> (handlers::apply)(l) }
            return handlers
        } else {
            return null
        }
    }

    protected open fun getSnHooks(): com.github.snabbdom.Hooks? {
        val hooks = hooks()
        hooks.apply {
            insert = { v ->
                vnode = v
                afterInsert(v)
            }
            postpatch = { ov, v ->
                vnode = v
                if (ov.elm !== v.elm) afterInsert(v)
            }
            destroy = { _ ->
                vnode = null
                afterDestroy()
            }
        }
        return hooks
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

    open fun getElement(): Node? {
        return this.vnode?.elm
    }

    open fun getElementJQuery(): JQuery? {
        return getElement()?.let { jQuery(it) }
    }

    open fun getElementJQueryD(): dynamic {
        return getElement()?.let { jQuery(it).asDynamic() }
    }

    internal fun clearParent() {
        this.parent = null
    }

    protected open fun refresh() {
        this.parent?.refresh()
    }

    protected open fun afterInsert(node: VNode) {
    }

    protected open fun afterDestroy() {
    }

    internal open fun getRoot(): Root? {
        return this.parent?.getRoot()
    }
}
