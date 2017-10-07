package pl.treksoft.kvision.core

import com.github.snabbdom.VNode
import com.github.snabbdom.VNodeData
import com.github.snabbdom.h
import org.w3c.dom.CustomEventInit
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

    open var visible: Boolean = true
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
    var width: Int? = null
        set(value) {
            field = value
            refresh()
        }
    var widthPercent: Int? = null
        set(value) {
            field = value
            refresh()
        }
    var height: Int? = null
        set(value) {
            field = value
            refresh()
        }

    private var vnode: VNode? = null

    private var snAttrsCache: List<StringPair>? = null
    private var snStyleCache: List<StringPair>? = null
    private var snClassCache: List<StringBoolPair>? = null
    private var snOnCache: com.github.snabbdom.On? = null
    private var snHooksCache: com.github.snabbdom.Hooks? = null

    internal open fun renderVNode(): VNode {
        return render()
    }

    protected open fun render(): VNode {
        return kvh("div")
    }

    protected open fun kvh(s: String): VNode {
        return h(s, getSnOpt())
    }

    protected open fun kvh(s: String, children: Array<dynamic>): VNode {
        return h(s, getSnOpt(), children)
    }

    private fun getSnOpt(): VNodeData {
        return snOpt {
            attrs = snAttrs(getSnAttrsInternal())
            style = snStyle(getSnStyleInternal())
            `class` = snClasses(getSnClassInternal())
            on = getSnOnInternal()
            hook = getSnHooksInternal()
        }
    }

    private fun getSnAttrsInternal(): List<StringPair> {
        return snAttrsCache ?: {
            val s = getSnAttrs()
            snAttrsCache = s
            s
        }()
    }

    private fun getSnStyleInternal(): List<StringPair> {
        return snStyleCache ?: {
            val s = getSnStyle()
            snStyleCache = s
            s
        }()
    }

    private fun getSnClassInternal(): List<StringBoolPair> {
        return snClassCache ?: {
            val s = getSnClass()
            snClassCache = s
            s
        }()
    }

    private fun getSnOnInternal(): com.github.snabbdom.On? {
        return snOnCache ?: {
            val s = getSnOn()
            snOnCache = s
            s
        }()
    }

    private fun getSnHooksInternal(): com.github.snabbdom.Hooks? {
        return snHooksCache ?: {
            val s = getSnHooks()
            snHooksCache = s
            s
        }()
    }

    protected open fun getSnStyle(): List<StringPair> {
        val snstyle = mutableListOf<StringPair>()
        if (width != null) {
            snstyle.add("width" to width.toString() + "px")
        } else if (widthPercent != null) {
            snstyle.add("width" to widthPercent.toString() + "%")
        }
        if (height != null) {
            snstyle.add("height" to height.toString() + "px")
        }
        return snstyle
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
        return if (listeners.size > 0) {
            val handlers = on(this)
            listeners.forEach { l -> (handlers::apply)(l) }
            handlers
        } else {
            null
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
    open fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Widget {
        listeners.add(block as SnOn<Widget>.() -> Unit)
        refresh()
        return this
    }

    open fun setEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        listeners.add(block)
        refresh()
        return this
    }

    open fun removeEventListeners(): Widget {
        listeners.clear()
        refresh()
        return this
    }

    open fun show(): Widget {
        visible = true
        return this
    }

    open fun hide(): Widget {
        visible = false
        return this
    }

    open fun addCssClass(css: String): Widget {
        this.classes.add(css)
        refresh()
        return this
    }

    open fun removeCssClass(css: String): Widget {
        this.classes.remove(css)
        refresh()
        return this
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

    internal fun clearParent(): Widget {
        this.parent = null
        return this
    }

    protected fun refresh(): Widget {
        snAttrsCache = null
        snStyleCache = null
        snClassCache = null
        snOnCache = null
        snHooksCache = null
        getRoot()?.reRender()
        return this
    }

    protected open fun afterInsert(node: VNode) {
    }

    protected open fun afterDestroy() {
    }

    internal open fun getRoot(): Root? {
        return this.parent?.getRoot()
    }

    protected open fun createLabelWithIcon(label: String, icon: String? = null,
                                           image: ResString? = null): Array<out Any> {
        return if (icon != null) {
            if (icon.startsWith("fa-")) {
                arrayOf(KVManager.virtualize("<i class='fa $icon fa-lg'></i>"), " " + label)
            } else {
                arrayOf(KVManager.virtualize("<span class='glyphicon glyphicon-$icon'></span>"), " " + label)
            }
        } else if (image != null) {
            arrayOf(KVManager.virtualize("<img src='$image' alt='' />"), " " + label)
        } else {
            arrayOf(label)
        }
    }

    internal open fun dispatchEvent(type: String, eventInitDict: CustomEventInit): Boolean? {
        val event = org.w3c.dom.CustomEvent(type, eventInitDict)
        return this.getElement()?.dispatchEvent(event)
    }

    open fun dispose() {
    }
}
