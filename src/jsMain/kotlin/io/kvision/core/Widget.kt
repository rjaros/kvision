/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kvision.core

import io.kvision.KVManager
import io.kvision.i18n.I18n
import io.kvision.i18n.I18n.trans
import io.kvision.panel.Root
import io.kvision.snabbdom.Attrs
import io.kvision.snabbdom.Classes
import io.kvision.snabbdom.VNode
import io.kvision.snabbdom.VNodeData
import io.kvision.snabbdom.VNodeStyle
import io.kvision.snabbdom.h
import io.kvision.utils.*
import org.w3c.dom.CustomEventInit
import org.w3c.dom.DragEvent
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import kotlin.reflect.KProperty

/**
 * Base widget class. The parent of all component classes.
 *
 * A simple widget is rendered as HTML DIV element.
 *
 * @constructor Creates basic Widget with given CSS class names.
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions", "LargeClass")
open class Widget(internal val className: String? = null, init: (Widget.() -> Unit)? = null) : StyledComponent(),
    Component {
    private val propertyValues = js("{}")

    internal var classes: MutableSet<String>? = null
    internal var surroundingClasses: MutableSet<String>? = null
    internal var attributes: MutableMap<String, String>? = null
    internal var internalListenersMap: MutableMap<String, MutableMap<Int, SnOn<Widget>.() -> Unit>>? = null
    internal var listenersMap: MutableMap<String, MutableMap<Int, SnOn<Widget>.() -> Unit>>? = null
    internal var listenerCounter: Int = 0
    protected var jqueryListenersMap: MutableMap<String, MutableMap<Int, (Any) -> Unit>>? = null

    override var parent: Container? = null

    override var visible: Boolean = true
        set(value) {
            val oldField = field
            field = value
            if (oldField != field) refresh()
        }

    /**
     * A title attribute of generated HTML element.
     */
    var title: String? by refreshOnUpdate()

    /**
     * An ID attribute of generated HTML element.
     */
    var id: String? by refreshOnUpdate()

    /**
     * A role attribute of generated HTML element.
     */
    var role: String? by refreshOnUpdate()

    /**
     * A tabindex attribute of generated HTML element.
     */
    var tabindex: Int? by refreshOnUpdate()

    /**
     * Determines if the current widget is draggable.
     */
    var draggable: Boolean? by refreshOnUpdate()

    var tooltipOptions: dynamic = null
    var tooltipHooksActive = false
    var popoverOptions: dynamic = null
    var popoverHooksActive = false

    var kvscope: dynamic = null

    var eventTarget: Widget? = null

    protected var vnkey: String? = undefined
    protected var vnode: VNode? = null

    private var snAttrsCache: SingleObjectCache<Attrs> =
        LazyCache { buildAttributeSet(this::buildAttributeSet) }
            .clearOn { lastLanguage != null && lastLanguage != I18n.language }

    private var snClassCache: SingleObjectCache<Classes> = LazyCache { buildClassSet(this::buildClassSet) }
    private var snOnCache: io.kvision.snabbdom.On? = null
    private var snHooksCache: io.kvision.snabbdom.Hooks? = null

    protected var lastLanguage: String? = null

    protected var afterInsertHooks: MutableList<(VNode) -> Unit>? = null
    protected var afterDestroyHooks: MutableList<() -> Unit>? = null
    private var beforeDisposeHooks: MutableList<() -> Unit>? = null

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }


    /**
     * Makes the component use distinct snabbdom key.
     */
    protected fun useSnabbdomDistinctKey() {
        vnkey = "kv_widget_${counter++}"
    }

    /**
     * The supplied function is called before the widget is disposed.
     */
    override fun addBeforeDisposeHook(hook: () -> Unit) = (beforeDisposeHooks ?: run {
        beforeDisposeHooks = mutableListOf()
        beforeDisposeHooks!!
    }).add(hook)

    /**
     * The supplied function is called after the widget is removed from the DOM.
     */
    override fun addAfterDestroyHook(hook: () -> Unit) = (afterDestroyHooks ?: run {
        useSnabbdomDistinctKey()
        afterDestroyHooks = mutableListOf()
        afterDestroyHooks!!
    }).add(hook)

    /**
     * The supplied function is called after the widget is inserted into the DOM.
     */
    override fun addAfterInsertHook(hook: (VNode) -> Unit) = (afterInsertHooks ?: run {
        useSnabbdomDistinctKey()
        afterInsertHooks = mutableListOf()
        afterInsertHooks!!
    }).add(hook)

    override fun <T> singleRender(block: () -> T): T {
        val root = getRoot()
        return if (root != null) {
            root.singleRender(block)
        } else {
            // fallback
            block()
        }
    }

    override fun singleRenderAsync(block: () -> Unit) {
        val root = getRoot()
        if (root != null) {
            root.singleRenderAsync(block)
        } else {
            // fallback
            block()
        }
    }

    override fun renderVNode(): VNode {
        return if (surroundingClasses == null) {
            render()
        } else {
            val opt = snOpt {
                key = vnkey
                `class` = snClasses(surroundingClasses!!.map { c -> c to true })
            }
            h("div", opt, arrayOf(render()))
        }
    }

    /**
     * Translates given text with I18n trans function and sets lastLanguage marker.
     * @param text a text marked for a dynamic translation
     * @return translated text
     */
    open fun translate(text: String): String {
        lastLanguage = I18n.language
        return trans(text)
    }

    /**
     * Translates given text with I18n trans function and sets lastLanguage marker.
     * @param text a text marked for a dynamic translation
     * @return translated text
     */
    open fun translate(text: String?): String? {
        return text?.let {
            translate(it)
        }
    }

    /**
     * Renders current component as a Snabbdom vnode.
     * @return Snabbdom vnode
     */
    protected open fun render(): VNode {
        return render("div")
    }

    /**
     * Renders current component as a Snabbdom vnode.
     * @param elementName HTML element name
     * @return Snabbdom vnode
     */
    protected open fun render(elementName: String): VNode {
        return h(elementName, getSnOpt())
    }

    /**
     * Renders current component as a Snabbdom vnode.
     * @param elementName HTML element name
     * @param children array of children nodes
     * @return Snabbdom vnode
     */
    protected open fun render(elementName: String, children: Array<dynamic>): VNode {
        return h(elementName, getSnOpt(), children)
    }

    /**
     * Generates VNodeData to creating Snabbdom VNode.
     *
     * Optimizes creating process by keeping configuration attributes in a cache.
     */
    private fun getSnOpt(): VNodeData {
        return snOpt {
            key = vnkey
            attrs = snAttrsCache.value
            style = getSnStyle().unsafeCast<VNodeStyle>()
            `class` = snClassCache.value
            on = getSnOn()
            hook = getSnHooksInternal()
        }
    }

    /**
     * Generates VNodeData to creating Snabbdom VNode with unique key id.
     */
    protected fun getSnOptSimple(): VNodeData {
        return snOpt {
            key = vnkey?.let { "kv_s_$it" } ?: undefined
        }
    }

    /**
     * Generates VNodeData to creating Snabbdom wrapper VNode with unique key id.
     */
    protected fun getSnOptContents(): VNodeData {
        return snOpt {
            style = obj { this.display = "contents" }.unsafeCast<VNodeStyle>()
            key = vnkey?.let { "kv_s_$it" } ?: undefined
        }
    }

    private fun getSnHooksInternal(): io.kvision.snabbdom.Hooks? =
        snHooksCache ?: getSnHooks().also { snHooksCache = it }

    /**
     * Builds a list of CSS class names for the current widget with a delegated ClassSetBuilder.
     * @param classSetBuilder a delegated builder
     */
    protected open fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        if (classes == null && className != null) {
            classSetBuilder.addAll(className.set)
        } else if (classes != null) {
            classSetBuilder.addAll(classes!!)
        }
        if (!visible) {
            classSetBuilder.add("hidden")
        }
    }

    /**
     * Builds a list of element attributes for the current widget with a delegated AttributeSetBuilder.
     * @param attributeSetBuilder a delegated builder
     */
    protected open fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        if (attributes == null) attributes = mutableMapOf()
        attributeSetBuilder.addAll(attributes!!)
        id?.let {
            attributeSetBuilder.add("id", it)
        }
        title?.let {
            attributeSetBuilder.add("title", translate(it))
        }
        role?.let {
            attributeSetBuilder.add("role", it)
        }
        tabindex?.let {
            attributeSetBuilder.add("tabindex", it.toString())
        }
        if (draggable == true) {
            attributeSetBuilder.add("draggable", "true")
        }
    }

    /**
     * Returns list of event handlers in the form of a Snabbdom *On* object.
     * @return list of event handlers
     */
    protected open fun getSnOn(): io.kvision.snabbdom.On? {
        if (internalListenersMap == null && listenersMap == null) return null
        val map = internalListenersMap?.filter { it.key != "self" && it.value.isNotEmpty() }?.map {
            val internalListeners = mutableMapOf<Int, SnOn<Widget>.() -> Unit>()
            internalListeners.putAll(it.value)
            it.key to internalListeners
        }?.toMap()?.toMutableMap() ?: mutableMapOf()
        listenersMap?.filter { it.key != "self" && it.value.isNotEmpty() }
            ?.forEach { (event, listeners) ->
                val internalListeners = map[event]
                if (internalListeners != null) {
                    internalListeners.putAll(listeners)
                } else {
                    map[event] = listeners
                }
            }
        return if (map.isNotEmpty()) {
            val handlers = emptyOn()
            map.forEach { (event, listeners) ->
                handlers.asDynamic()[event] = if (listeners.size == 1) {
                    listeners.values.first()
                } else {
                    listeners.map { arrayOf(it.value) }.toTypedArray()
                }
            }
            handlers
        } else {
            null
        }
    }

    /**
     * Returns list of hooks in the form of a Snabbdom *Hooks* object.
     * @return list of hooks
     */
    protected open fun getSnHooks(): io.kvision.snabbdom.Hooks? {
        val hooks = hooks()
        hooks.apply {
            create = { _, v ->
                vnode = v
                afterCreate(v)
            }
            insert = { v ->
                vnode = v
                afterInsertInternal(v)
                afterInsert(v)
                bindAllJQueryListeners()
                afterInsertHooks?.forEach { it(v) }
            }
            postpatch = { _, v ->
                vnode = v
                afterPatch(v)
            }
            destroy = {
                afterDestroyInternal()
                afterDestroy()
                afterDestroyHooks?.forEach { it() }
                vnode = null
                vnode
            }
        }
        return hooks
    }

    /**
     * @suppress
     * Internal function
     */
    @Suppress("UNCHECKED_CAST", "UnsafeCastFromDynamic")
    protected fun <T : Widget> setInternalEventListener(block: SnOn<T>.() -> Unit): Int {
        if (internalListenersMap == null) internalListenersMap = mutableMapOf()
        val handlerCounter = listenerCounter++
        val blockAsWidget = block as SnOn<Widget>.() -> Unit
        val handlers = on(this)
        (handlers::apply)(blockAsWidget)
        for (key: String in js("Object").keys(handlers)) {
            if (key != "self") {
                val handler = handlers.asDynamic()[key]
                val map = internalListenersMap!![key]
                if (map != null) {
                    map[handlerCounter] = handler
                } else {
                    internalListenersMap!![key] = mutableMapOf(handlerCounter to handler)
                }
            }
        }
        refresh()
        return handlerCounter
    }

    /**
     * Sets an event listener for current widget, keeping the actual type of component.
     * @param T widget type
     * @param block event handler
     * @return id of the handler
     *
     * Example:
     *
     *      button.setEventListener<Button> {
     *          dblclick = {
     *              Alert.show("Button double clicked!")
     *              // self is of type Button here
     *          }
     *      }
     */
    @Suppress("UNCHECKED_CAST", "UnsafeCastFromDynamic")
    open fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Int {
        if (listenersMap == null) listenersMap = mutableMapOf()
        if (jqueryListenersMap == null) jqueryListenersMap = mutableMapOf()
        removeAllJQueryListeners()
        val handlerCounter = listenerCounter++
        val blockAsWidget = block as SnOn<Widget>.() -> Unit
        val handlers = on(eventTarget ?: this)
        (handlers::apply)(blockAsWidget)
        for (key: String in js("Object").keys(handlers)) {
            if (key != "self") {
                val handler = handlers.asDynamic()[key]
                if (!key.startsWith(KV_JQUERY_EVENT_PREFIX)) {
                    val map = listenersMap!![key]
                    if (map != null) {
                        map[handlerCounter] = handler
                    } else {
                        listenersMap!![key] = mutableMapOf(handlerCounter to handler)
                    }
                } else {
                    val jqueryKey = key.substring(KV_JQUERY_EVENT_PREFIX.length)
                    val map = jqueryListenersMap!![jqueryKey]
                    if (map != null) {
                        map[handlerCounter] = handler
                    } else {
                        jqueryListenersMap!![jqueryKey] = mutableMapOf(handlerCounter to handler)
                    }
                }
            }
        }
        refresh()
        bindAllJQueryListeners()
        return handlerCounter
    }

    /**
     * Removes event listener from current widget.
     * @param id the id of the handler returned by onEvent
     */
    open fun removeEventListener(id: Int) {
        listenersMap?.forEach { it.value.remove(id) }
        removeAllJQueryListeners()
        jqueryListenersMap?.forEach { it.value.remove(id) }
        bindAllJQueryListeners()
        refresh()
    }

    /**
     * Removes all event listeners from current widget.
     */
    open fun removeEventListeners() {
        listenersMap?.clear()
        removeAllJQueryListeners()
        jqueryListenersMap?.clear()
        refresh()
    }

    /**
     * @suppress internal function
     * Binds all jQuery event listeners.
     */
    protected open fun bindAllJQueryListeners() {
    }

    /**
     * @suppress internal function
     * Removes all jQuery event listeners.
     */
    protected open fun removeAllJQueryListeners() {
    }

    /**
     * Makes current widget visible.
     */
    open fun show() {
        visible = true
    }

    /**
     * Makes current widget invisible.
     */
    open fun hide() {
        visible = false
    }

    /**
     * Toggles visibility of current widget.
     */
    open fun toggleVisible() {
        if (visible) hide() else show()
    }

    override fun addCssClass(css: String) {
        if (classes == null) classes = className.mutableSet
        classes!!.add(css)
        refresh()
    }

    override fun removeCssClass(css: String) {
        if (classes == null) classes = className.mutableSet
        classes!!.remove(css)
        refresh()
    }

    override fun hasCssClass(css: String): Boolean = this.classes?.contains(css) ?: false

    override fun addSurroundingCssClass(css: String) {
        if (this.surroundingClasses == null) this.surroundingClasses = mutableSetOf()
        this.surroundingClasses!!.add(css)
        refresh()
    }

    override fun removeSurroundingCssClass(css: String) {
        if (this.surroundingClasses == null) this.surroundingClasses = mutableSetOf()
        this.surroundingClasses!!.remove(css)
        refresh()
    }

    override fun addCssStyle(css: Style) {
        addCssClass(css.cssClassName)
    }

    override fun removeCssStyle(css: Style) {
        removeCssClass(css.cssClassName)
    }

    override fun addSurroundingCssStyle(css: Style) {
        addSurroundingCssClass(css.cssClassName)
    }

    override fun removeSurroundingCssStyle(css: Style) {
        removeSurroundingCssClass(css.cssClassName)
    }

    override fun getAttribute(name: String): String? {
        return this.attributes?.get(name)
    }

    override fun setAttribute(name: String, value: String) {
        if (attributes == null) attributes = mutableMapOf()
        attributes!![name] = value
        refresh()
    }

    override fun removeAttribute(name: String) {
        if (attributes == null) attributes = mutableMapOf()
        attributes!!.remove(name)
        refresh()
    }

    override fun getElement(): HTMLElement? {
        return this.vnode?.elm?.unsafeCast<HTMLElement>()
    }

    override fun getElementD(): dynamic {
        return getElement()?.asDynamic()
    }

    override fun clearParent() {
        this.parent = null
    }

    override fun refresh() {
        super.refresh()
        snAttrsCache.clear()
        snClassCache.clear()
        snOnCache = null
        snHooksCache = null
        getRoot()?.reRender()
    }

    /**
     * Method called after creating Snabbdom vnode.
     */
    protected open fun afterCreate(node: VNode) {
    }

    /**
     * Internal method called after inserting Snabbdom vnode into the DOM.
     */
    internal open fun afterInsertInternal(node: VNode) {
    }

    /**
     * Method called after inserting Snabbdom vnode into the DOM.
     */
    protected open fun afterInsert(node: VNode) {
    }

    /**
     * Method called after patching Snabbdom vnode.
     */
    protected open fun afterPatch(node: VNode) {
    }

    /**
     * Internal method called after destroying Snabbdom vnode.
     */
    internal open fun afterDestroyInternal() {
    }

    /**
     * Method called after destroying Snabbdom vnode.
     */
    protected open fun afterDestroy() {
    }

    override fun getRoot(): Root? {
        return this.parent?.getRoot()
    }

    /**
     * Sets D&D data for the current widget. It also makes it draggable.
     * @param format D&D data format
     * @param data D&D data transferred to a drop target
     */
    open fun setDragDropData(format: String, data: String) {
        draggable = true
        setEventListener<Widget> {
            dragstart = { e ->
                e.dataTransfer?.setData(format, data)
            }
        }
    }

    /**
     * Clears D&D data for the current widget. It also makes it not draggable.
     */
    open fun clearDragDropData() {
        draggable = false
        setEventListener<Widget> {
            dragstart = {
            }
        }
    }

    /**
     * Sets the current widget as a D&D drop target with helper callback accepting String data.
     * @param format accepted D&D data format
     * @param callback a callback function accepting String data called after any drop event
     */
    open fun setDropTargetData(format: String, callback: (String?) -> Unit) {
        setDropTarget(format) { e ->
            callback(e.dataTransfer?.getData(format))
        }
    }

    /**
     * Sets the current widget as a D&D drop target.
     * @param format accepted D&D data format
     * @param callback a callback function accepting event object called after any drop event
     */
    open fun setDropTarget(format: String, callback: (DragEvent) -> Unit) {
        setDropTarget(setOf(format), callback)
    }

    /**
     * Sets the current widget as a D&D drop target.
     * @param formats a set of accepted D&D data formats
     * @param callback a callback function accepting event object called after any drop event
     */
    open fun setDropTarget(formats: Set<String>? = null, callback: (DragEvent) -> Unit) {
        setEventListener<Widget> {
            dragover = { e ->
                val types = e.dataTransfer?.types?.toSet() ?: setOf()
                if (formats == null || formats.intersect(types).isNotEmpty()) {
                    e.preventDefault()
                }
            }
            drop = { e ->
                e.preventDefault()
                e.stopPropagation()
                callback(e)
            }
        }
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun createLabelWithIcon(
        label: String, icon: String? = null,
        image: ResString? = null,
        separator: String? = null
    ): Array<out Any> {
        val translatedLabel = translate(label)
        return if (icon != null) {
            val iconClasses = icon.split(" ").toSet()
            val iconOpt = snOpt {
                `class` = snClasses(iconClasses.map { c -> c to true })
            }
            if (separator == null) {
                arrayOf(h("i", iconOpt), " $translatedLabel")
            } else {
                arrayOf(h("i", iconOpt), KVManager.virtualize(separator), translatedLabel)
            }
        } else if (image != null) {
            val imageOpt = snOpt {
                attrs = snAttrs(mapOf("src" to image, "alt" to ""))
            }
            if (separator == null) {
                arrayOf(h("img", imageOpt), " $translatedLabel")
            } else {
                arrayOf(
                    h("img", imageOpt),
                    KVManager.virtualize(separator),
                    translatedLabel
                )
            }
        } else {
            arrayOf(translatedLabel)
        }
    }

    /**
     * Dispatches a custom event.
     */
    open fun dispatchEvent(type: String, eventInitDict: CustomEventInit): Boolean? {
        val event = org.w3c.dom.CustomEvent(type, eventInitDict)
        return this.getElement()?.dispatchEvent(event)
    }

    override fun dispose() {
        afterDestroyInternal()
        afterDestroy()
        afterDestroyHooks?.forEach { it() }
        beforeDisposeHooks?.forEach { it() }
    }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> refreshOnUpdate(noinline refreshFunction: ((T) -> Unit) = { this.refresh() }): RefreshDelegateProvider<T> =
        RefreshDelegateProvider(null, refreshFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> refreshOnUpdate(
        initialValue: T,
        noinline refreshFunction: ((T) -> Unit) = { this.refresh() }
    ): RefreshDelegateProvider<T> =
        RefreshDelegateProvider(initialValue, refreshFunction)

    protected inner class RefreshDelegateProvider<T>(
        private val initialValue: T?, private val refreshFunction: (T) -> Unit
    ) {
        operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): RefreshDelegate<T> {
            if (initialValue != null) propertyValues[prop.name] = initialValue
            return RefreshDelegate(refreshFunction)
        }
    }

    protected inner class RefreshDelegate<T>(private val refreshFunction: ((T) -> Unit)) {
        @Suppress("UNCHECKED_CAST")
        operator fun getValue(thisRef: StyledComponent, property: KProperty<*>): T {
            val value = propertyValues[property.name]
            return if (value != null) {
                value.unsafeCast<T>()
            } else {
                null.unsafeCast<T>()
            }
        }

        operator fun setValue(thisRef: StyledComponent, property: KProperty<*>, value: T) {
            val oldValue = propertyValues[property.name]
            if (value == null) {
                delete(propertyValues, property.name)
            } else {
                propertyValues[property.name] = value
            }
            if (oldValue != value) {
                refreshFunction(value)
            }
        }
    }

    companion object {
        const val KV_JQUERY_EVENT_PREFIX = "KVJQUERYEVENT##"
        private var counter: Int = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.widget(
    className: String? = null,
    init: (Widget.() -> Unit)? = null
): Widget {
    val widget = Widget(className, init)
    this.add(widget)
    return widget
}

/**
 * An extension function for defining event handlers.
 */
inline fun <reified T : Widget> T.onEvent(noinline block: SnOn<T>.() -> Unit): Int {
    return this.setEventListener(block)
}

/**
 * An extension function for defining on click event handlers.
 */
inline fun <reified T : Widget> T.onClick(noinline handler: T.(MouseEvent) -> Unit): Int {
    return this.setEventListener<T> {
        click = { e ->
            self.handler(e)
        }
    }
}

/**
 * An extension function for defining on change event handlers.
 */
inline fun <reified T : Widget> T.onChange(noinline handler: T.(Event) -> Unit): Int {
    return this.setEventListener<T> {
        change = { e ->
            self.handler(e)
        }
    }
}

/**
 * An extension function for defining on change event handlers.
 */
inline fun <reified T : Widget> T.onInput(noinline handler: T.(Event) -> Unit): Int {
    return this.setEventListener<T> {
        input = { e ->
            self.handler(e)
        }
    }
}
