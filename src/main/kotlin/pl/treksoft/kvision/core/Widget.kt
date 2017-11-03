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

@Suppress("TooManyFunctions", "LargeClass")
open class Widget(classes: Set<String> = setOf()) : StyledComponent() {

    internal val classes = classes.toMutableSet()
    internal val internalListeners = mutableListOf<SnOn<Widget>.() -> Unit>()
    internal val listeners = mutableListOf<SnOn<Widget>.() -> Unit>()

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

    private var vnode: VNode? = null

    private var snAttrsCache: List<StringPair>? = null
    private var snClassCache: List<StringBoolPair>? = null
    private var snOnCache: com.github.snabbdom.On? = null
    private var snHooksCache: com.github.snabbdom.Hooks? = null

    protected fun <T> singleRender(block: () -> T): T {
        getRoot()?.renderDisabled = true
        val t = block()
        getRoot()?.renderDisabled = false
        getRoot()?.reRender()
        return t
    }

    open fun renderVNode(): VNode {
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

    protected open fun getSnClass(): List<StringBoolPair> {
        return classes.map { c -> c to true } + if (visible) listOf() else listOf("hidden" to true)
    }

    protected open fun getSnAttrs(): List<StringPair> {
        val snattrs = mutableListOf<StringPair>()
        id?.let {
            snattrs.add("id" to it)
        }
        title?.let {
            snattrs.add("title" to it)
        }
        role?.let {
            snattrs.add("role" to it)
        }
        return snattrs
    }

    protected open fun getSnOn(): com.github.snabbdom.On? {
        return if (internalListeners.size > 0 || listeners.size > 0) {
            val internalHandlers = on(this)
            internalListeners.forEach { l -> (internalHandlers::apply)(l) }
            val handlers = on(this)
            listeners.forEach { l -> (handlers::apply)(l) }
            if (internalHandlers.click != null) {
                if (handlers.click == null) {
                    handlers.click = internalHandlers.click
                } else {
                    val intc = internalHandlers.click
                    val c = handlers.click
                    handlers.click = { e ->
                        intc?.invoke(e)
                        c?.invoke(e)
                    }
                }
            }
            if (internalHandlers.change != null) {
                if (handlers.change == null) {
                    handlers.change = internalHandlers.change
                } else {
                    val intc = internalHandlers.change
                    val c = handlers.change
                    handlers.change = { e ->
                        intc?.invoke(e)
                        c?.invoke(e)
                    }
                }
            }
            if (internalHandlers.input != null) {
                if (handlers.input == null) {
                    handlers.input = internalHandlers.input
                } else {
                    val intc = internalHandlers.input
                    val c = handlers.input
                    handlers.input = { e ->
                        intc?.invoke(e)
                        c?.invoke(e)
                    }
                }
            }
            handlers
        } else {
            null
        }
    }

    protected open fun getSnHooks(): com.github.snabbdom.Hooks? {
        val hooks = hooks()
        hooks.apply {
            create = { _, v ->
                vnode = v
                afterCreate(v)
            }
            insert = { v ->
                vnode = v
                afterInsert(v)
            }
            postpatch = { ov, v ->
                vnode = v
                if (ov.elm !== v.elm) {
                    afterInsert(v)
                } else {
                    afterPostpatch(v)
                }
            }
            destroy = { _ ->
                vnode = null
                afterDestroy()
            }
        }
        return hooks
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T : Widget> setInternalEventListener(block: SnOn<T>.() -> Unit): Widget {
        internalListeners.add(block as SnOn<Widget>.() -> Unit)
        refresh()
        return this
    }

    protected fun setInternalEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        internalListeners.add(block)
        refresh()
        return this
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

    override fun refresh(): Widget {
        super.refresh()
        snAttrsCache = null
        snClassCache = null
        snOnCache = null
        snHooksCache = null
        getRoot()?.reRender()
        return this
    }

    protected open fun afterCreate(node: VNode) {
    }

    protected open fun afterInsert(node: VNode) {
    }

    protected open fun afterPostpatch(node: VNode) {
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
