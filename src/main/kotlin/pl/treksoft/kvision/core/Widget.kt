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
package pl.treksoft.kvision.core

import com.github.snabbdom.VNode
import com.github.snabbdom.VNodeData
import com.github.snabbdom.h
import org.w3c.dom.CustomEventInit
import org.w3c.dom.DragEvent
import org.w3c.dom.Node
import org.w3c.dom.events.MouseEvent
import pl.treksoft.jquery.JQuery
import pl.treksoft.jquery.invoke
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.i18n.I18n
import pl.treksoft.kvision.i18n.I18n.trans
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.*
import kotlin.reflect.KProperty

enum class Easing(internal val easing: String) {
    SWING("swing"),
    LINEAR("linear")
}

/**
 * Base widget class. The parent of all component classes.
 *
 * A simple widget is rendered as HTML DIV element.
 *
 * @constructor Creates basic Widget with given CSS class names.
 * @param classes Set of CSS class names
 */
@Suppress("TooManyFunctions", "LargeClass")
open class Widget(classes: Set<String> = setOf()) : StyledComponent(), Component {
    private val propertyValues: MutableMap<String, Any?> = mutableMapOf()

    internal val classes = classes.toMutableSet()
    internal val surroundingClasses: MutableSet<String> = mutableSetOf()
    internal val attributes: MutableMap<String, String> = mutableMapOf()
    internal val internalListenersMap = mutableMapOf<String, MutableMap<Int, SnOn<Widget>.() -> Unit>>()
    internal val listenersMap = mutableMapOf<String, MutableMap<Int, SnOn<Widget>.() -> Unit>>()
    internal var listenerCounter: Int = 0

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

    protected var surroundingSpan by refreshOnUpdate(false)

    private var tooltipSiblings: JQuery? = null
    private var popoverSiblings: JQuery? = null

    protected var tooltipOptions: TooltipOptions? = null
    protected var popoverOptions: PopoverOptions? = null

    var eventTarget: Widget? = null

    private var vnkey = "kv_widget_${counter++}"
    protected var vnode: VNode? = null

    private var snAttrsCache: List<StringPair>? = null
    private var snClassCache: List<StringBoolPair>? = null
    private var snOnCache: com.github.snabbdom.On? = null
    private var snHooksCache: com.github.snabbdom.Hooks? = null

    protected var lastLanguage: String? = null

    @Deprecated("use addAfterInsertHooks instead", ReplaceWith("addAfterInsertHook"))
    var afterInsertHook: ((VNode) -> Unit)
        get() {
            throw UnsupportedOperationException()
        }
        set(value) {
            afterInsertHooks.clear()
            addAfterInsertHook(value)
        }

    @Deprecated("use addAfterDestroyHook instead", ReplaceWith("addAfterDestroyHook"))
    var afterDestroyHook: (() -> Unit)
        get() {
            throw UnsupportedOperationException()
        }
        set(value) {
            afterDestroyHooks.clear()
            addAfterDestroyHook(value)
        }

    @Deprecated("use addAfterDisposeHook instead", ReplaceWith("addAfterDisposeHook"))
    var afterDisposeHook: () -> Unit
        get() {
            throw UnsupportedOperationException()
        }
        set(value) {
            afterDisposeHooks.clear()
            addAfterDisposeHook(value)
        }

    private val afterInsertHooks: MutableList<(VNode) -> Unit> = mutableListOf()
    private val afterDestroyHooks: MutableList<() -> Unit> = mutableListOf()
    private val afterDisposeHooks: MutableList<() -> Unit> = mutableListOf()

    /**
     * The supplied function is called after the widget is disposed.
     */
    fun addAfterDisposeHook(hook: () -> Unit) = afterDisposeHooks.add(hook)
    /**
     * The supplied function is called after the widget is removed from the DOM.
     */
    fun addAfterDestroyHook(hook: () -> Unit) = afterDestroyHooks.add(hook)
    /**
     * The supplied function is called after the widget is inserted into the DOM.
     */
    fun addAfterInsertHook(hook: (VNode) -> Unit) = afterInsertHooks.add(hook)

    protected fun <T> singleRender(block: () -> T): T {
        getRoot()?.renderDisabled = true
        val t = block()
        getRoot()?.renderDisabled = false
        getRoot()?.reRender()
        return t
    }

    override fun renderVNode(): VNode {
        return if (surroundingClasses.isEmpty()) {
            if (surroundingSpan) {
                h("span", arrayOf(render()))
            } else {
                render()
            }
        } else {
            val opt = snOpt {
                `class` = snClasses(surroundingClasses.map { c -> c to true })
            }
            if (surroundingSpan) {
                h("div", opt, arrayOf(h("span", arrayOf(render()))))
            } else {
                h("div", opt, arrayOf(render()))
            }
        }
    }

    /**
     * Translates given text with I18n trans function and sets lastLanguage marker.
     * @param text a text marked for a dynamic translation
     * @return translated text
     */
    protected fun translate(text: String): String {
        lastLanguage = I18n.language
        return trans(text)
    }

    protected fun translate(text: String?): String? {
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
            attrs = snAttrs(getSnAttrsInternal())
            style = snStyle(getSnStyleInternal())
            `class` = snClasses(getSnClassInternal())
            on = getSnOn()
            hook = getSnHooksInternal()
        }
    }

    private fun getSnAttrsInternal(): List<StringPair> =
        snAttrsCache.let { cache ->
            if (cache == null || lastLanguage != null && lastLanguage != I18n.language) {
                getSnAttrs().also { snAttrsCache = it }
            } else {
                cache
            }
        }

    private fun getSnClassInternal(): List<StringBoolPair> = snClassCache ?: getSnClass().also { snClassCache = it }

    private fun getSnHooksInternal(): com.github.snabbdom.Hooks? =
        snHooksCache ?: getSnHooks().also { snHooksCache = it }

    /**
     * Returns list of CSS class names for current widget in the form of a List<StringBoolPair>.
     * @return list of CSS class names
     */
    protected open fun getSnClass(): List<StringBoolPair> {
        return classes.map { c -> c to true } + if (visible) listOf() else listOf("hidden" to true)
    }

    /**
     * Returns list of element attributes in the form of a List<StringPair>.
     * @return list of element attributes
     */
    protected open fun getSnAttrs(): List<StringPair> {
        val snattrs = mutableListOf<StringPair>()
        id?.let {
            snattrs.add("id" to it)
        }
        title?.let {
            snattrs.add("title" to translate(it))
        }
        role?.let {
            snattrs.add("role" to it)
        }
        tabindex?.let {
            snattrs.add("tabindex" to "$it")
        }
        if (draggable == true) {
            snattrs.add("draggable" to "true")
        }
        if (attributes.isNotEmpty()) {
            snattrs += attributes.toList()
        }
        return snattrs
    }

    /**
     * Returns list of event handlers in the form of a Snabbdom *On* object.
     * @return list of event handlers
     */
    protected open fun getSnOn(): com.github.snabbdom.On? {
        val map = internalListenersMap.filter { it.key != "self" && it.value.isNotEmpty() }.map {
            val internalListeners = mutableMapOf<Int, SnOn<Widget>.() -> Unit>()
            internalListeners.putAll(it.value)
            it.key to internalListeners
        }.toMap().toMutableMap()
        listenersMap.filter { it.key != "self" && it.value.isNotEmpty() }
            .forEach { (event, listeners) ->
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
    protected open fun getSnHooks(): com.github.snabbdom.Hooks? {
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
                afterInsertHooks.forEach { it(v) }
            }
            destroy = {
                afterDestroyInternal()
                afterDestroy()
                afterDestroyHooks.forEach { it() }
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
        val handlerCounter = listenerCounter++
        val blockAsWidget = block as SnOn<Widget>.() -> Unit
        val handlers = on(this)
        (handlers::apply)(blockAsWidget)
        for (key: String in js("Object").keys(handlers)) {
            val handler = handlers.asDynamic()[key]
            val map = internalListenersMap[key]
            if (map != null) {
                map[handlerCounter] = handler
            } else {
                internalListenersMap[key] = mutableMapOf(handlerCounter to handler)
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
        val handlerCounter = listenerCounter++
        val blockAsWidget = block as SnOn<Widget>.() -> Unit
        val handlers = on(eventTarget ?: this)
        (handlers::apply)(blockAsWidget)
        for (key: String in js("Object").keys(handlers)) {
            if (key != "self") {
                val handler = handlers.asDynamic()[key]
                val map = listenersMap[key]
                if (map != null) {
                    map[handlerCounter] = handler
                } else {
                    listenersMap[key] = mutableMapOf(handlerCounter to handler)
                }
            }
        }
        refresh()
        return handlerCounter
    }

    /**
     * Removes event listener from current widget.
     * @param id the id of the handler returned by onEvent
     * @return current widget
     */
    open fun removeEventListener(id: Int): Widget {
        listenersMap.forEach { it.value.remove(id) }
        refresh()
        return this
    }

    /**
     * Removes all event listeners from current widget.
     * @return current widget
     */
    open fun removeEventListeners(): Widget {
        listenersMap.clear()
        refresh()
        return this
    }

    /**
     * Makes current widget visible.
     * @return current widget
     */
    open fun show(): Widget {
        visible = true
        return this
    }

    /**
     * Makes current widget invisible.
     * @return current widget
     */
    open fun hide(): Widget {
        visible = false
        return this
    }

    /**
     * Shows current widget with animation effect.
     * @param duration a duration of the animation
     * @param easing an easing function to use
     * @param complete a callback function called after the animation completes
     * @return current widget
     */
    open fun showAnim(
        duration: Int = 400,
        easing: Easing = Easing.SWING,
        complete: (() -> Unit)? = null
    ): Widget {
        this.display = Display.NONE
        this.visible = true
        val jq = getElementJQuery()
        if (jq != null) {
            jq.show(duration, easing.easing) {
                this.display = null
                complete?.invoke()
            }
        } else {
            this.display = null
            complete?.invoke()
        }
        return this
    }

    /**
     * Hides current widget with animation effect.
     * @param duration a duration of the animation
     * @param easing an easing function to use
     * @param complete a callback function called after the animation completes
     * @return current widget
     */
    open fun hideAnim(
        duration: Int = 400,
        easing: Easing = Easing.SWING,
        complete: (() -> Unit)? = null
    ): Widget {
        val jq = getElementJQuery()
        if (jq != null) {
            jq.hide(duration, easing.easing) {
                this.visible = false
                complete?.invoke()
            }
        } else {
            this.visible = false
            complete?.invoke()
        }
        return this
    }

    /**
     * Shows current widget with slide down effect.
     * @param duration a duration of the animation
     * @param easing an easing function to use
     * @param complete a callback function called after the animation completes
     * @return current widget
     */
    open fun slideDown(
        duration: Int = 400,
        easing: Easing = Easing.SWING,
        complete: (() -> Unit)? = null
    ): Widget {
        this.display = Display.NONE
        this.visible = true
        val jq = getElementJQuery()
        if (jq != null) {
            jq.slideDown(duration, easing.easing) {
                this.display = null
                complete?.invoke()
            }
        } else {
            this.display = null
            complete?.invoke()
        }
        return this
    }

    /**
     * Hides current widget with slide up effect.
     * @param duration a duration of the animation
     * @param easing an easing function to use
     * @param complete a callback function called after the animation completes
     * @return current widget
     */
    open fun slideUp(
        duration: Int = 400,
        easing: Easing = Easing.SWING,
        complete: (() -> Unit)? = null
    ): Widget {
        val jq = getElementJQuery()
        if (jq != null) {
            jq.slideUp(duration, easing.easing) {
                this.visible = false
                complete?.invoke()
            }
        } else {
            this.visible = false
            complete?.invoke()
        }
        return this
    }

    /**
     * Shows current widget with fade in effect.
     * @param duration a duration of the animation
     * @param easing an easing function to use
     * @param complete a callback function called after the animation completes
     * @return current widget
     */
    open fun fadeIn(
        duration: Int = 400,
        easing: Easing = Easing.SWING,
        complete: (() -> Unit)? = null
    ): Widget {
        this.display = Display.NONE
        this.visible = true
        val jq = getElementJQuery()
        if (jq != null) {
            jq.fadeIn(duration, easing.easing) {
                this.display = null
                complete?.invoke()
            }
        } else {
            this.display = null
            complete?.invoke()
        }
        return this
    }

    /**
     * Hides current widget with fade out effect.
     * @param duration a duration of the animation
     * @param easing an easing function to use
     * @param complete a callback function called after the animation completes
     * @return current widget
     */
    open fun fadeOut(
        duration: Int = 400,
        easing: Easing = Easing.SWING,
        complete: (() -> Unit)? = null
    ): Widget {
        val jq = getElementJQuery()
        if (jq != null) {
            jq.fadeOut(duration, easing.easing) {
                this.visible = false
                complete?.invoke()
            }
        } else {
            this.visible = false
            complete?.invoke()
        }
        return this
    }

    /**
     * Enables tooltip for the current widget.
     * @param options tooltip options
     * @return current widget
     */
    open fun enableTooltip(options: TooltipOptions = TooltipOptions()): Widget {
        this.tooltipOptions = options
        val tooltipFun = getElementJQueryD()?.tooltip
        if (tooltipFun != undefined) getElementJQueryD()?.tooltip(
            options.copy(title = options.title?.let { translate(it) }).toJs()
        )
        return this
    }

    /**
     * Shows tooltip for the current widget.
     * @return current widget
     */
    open fun showTooltip(): Widget {
        if (this.tooltipOptions != null) {
            val tooltipFun = getElementJQueryD()?.tooltip
            if (tooltipFun != undefined) getElementJQueryD()?.tooltip("show")
        }
        return this
    }

    /**
     * Hides tooltip for the current widget.
     * @return current widget
     */
    open fun hideTooltip(): Widget {
        if (this.tooltipOptions != null) {
            val tooltipFun = getElementJQueryD()?.tooltip
            if (tooltipFun != undefined) getElementJQueryD()?.tooltip("hide")
        }
        return this
    }

    /**
     * Disables tooltip for the current widget.
     * @return current widget
     */
    open fun disableTooltip(): Widget {
        this.tooltipOptions = null
        val tooltipFun = getElementJQueryD()?.tooltip
        if (tooltipFun != undefined) getElementJQueryD()?.tooltip("dispose")
        return this
    }

    /**
     * Enables popover for the current widget.
     * @param options popover options
     * @return current widget
     */
    open fun enablePopover(options: PopoverOptions = PopoverOptions()): Widget {
        this.popoverOptions = options
        val popoverFun = getElementJQueryD()?.popover
        if (popoverFun != undefined) getElementJQueryD()?.popover(
            options.copy(title = options.title?.let { translate(it) },
                content = options.content?.let { translate(it) }).toJs()
        )
        return this
    }

    /**
     * Shows popover for the current widget.
     * @return current widget
     */
    open fun showPopover(): Widget {
        if (this.popoverOptions != null) {
            val popoverFun = getElementJQueryD()?.popover
            if (popoverFun != undefined) getElementJQueryD()?.popover("show")
        }
        return this
    }

    /**
     * Hides popover for the current widget.
     * @return current widget
     */
    open fun hidePopover(): Widget {
        if (this.popoverOptions != null) {
            val popoverFun = getElementJQueryD()?.popover
            if (popoverFun != undefined) getElementJQueryD()?.popover("hide")
        }
        return this
    }

    /**
     * Disables popover for the current widget.
     * @return current widget
     */
    open fun disablePopover(): Widget {
        this.popoverOptions = null
        val popoverFun = getElementJQueryD()?.popover
        if (popoverFun != undefined) getElementJQueryD()?.popover("dispose")
        return this
    }

    /**
     * Toggles visibility of current widget.
     * @return current widget
     */
    open fun toggleVisible(): Widget {
        return if (visible) hide() else show()
    }

    override fun addCssClass(css: String): Widget {
        this.classes.add(css)
        refresh()
        return this
    }

    override fun removeCssClass(css: String): Widget {
        this.classes.remove(css)
        refresh()
        return this
    }

    override fun addSurroundingCssClass(css: String): Widget {
        this.surroundingClasses.add(css)
        refresh()
        return this
    }

    override fun removeSurroundingCssClass(css: String): Widget {
        this.surroundingClasses.remove(css)
        refresh()
        return this
    }

    override fun addCssStyle(css: Style): Widget {
        return addCssClass(css.className)
    }

    override fun removeCssStyle(css: Style): Widget {
        return removeCssClass(css.className)
    }

    override fun addSurroundingCssStyle(css: Style): Widget {
        return addSurroundingCssClass(css.className)
    }

    override fun removeSurroundingCssStyle(css: Style): Widget {
        return removeSurroundingCssClass(css.className)
    }

    override fun getAttribute(name: String): String? {
        return this.attributes[name]
    }

    override fun setAttribute(name: String, value: String): Widget {
        this.attributes[name] = value
        refresh()
        return this
    }

    override fun removeAttribute(name: String): Widget {
        this.attributes.remove(name)
        refresh()
        return this
    }

    override fun getElement(): Node? {
        return this.vnode?.elm
    }

    override fun getElementJQuery(): JQuery? {
        return getElement()?.let { jQuery(it) }
    }

    override fun getElementJQueryD(): dynamic {
        return getElement()?.let { jQuery(it).asDynamic() }
    }

    override fun clearParent(): Widget {
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

    /**
     * Method called after creating Snabbdom vnode.
     */
    protected open fun afterCreate(node: VNode) {
    }

    /**
     * Internal method called after inserting Snabbdom vnode into the DOM.
     */
    internal open fun afterInsertInternal(node: VNode) {
        this.tooltipOptions?.let {
            @Suppress("UnsafeCastFromDynamic")
            val tooltipFun = getElementJQueryD()?.tooltip
            if (tooltipFun != undefined) getElementJQueryD()?.tooltip(
                it.copy(title = it.title?.let { translate(it) }).toJs()
            )
        }
        this.popoverOptions?.let {
            @Suppress("UnsafeCastFromDynamic")
            val popoverFun = getElementJQueryD()?.popover
            if (popoverFun != undefined) getElementJQueryD()?.popover(
                it.copy(title = it.title?.let { translate(it) },
                    content = it.content?.let { translate(it) }).toJs()
            )
        }
    }

    /**
     * Method called after inserting Snabbdom vnode into the DOM.
     */
    protected open fun afterInsert(node: VNode) {
    }

    /**
     * Internal method called after destroying Snabbdom vnode.
     */
    @Suppress("UnsafeCastFromDynamic")
    internal open fun afterDestroyInternal() {
        this.tooltipOptions?.let {
            val tooltipFun = getElementJQueryD()?.tooltip
            if (tooltipFun != undefined) getElementJQueryD()?.tooltip("dispose")
        }
        this.popoverOptions?.let {
            val popoverFun = getElementJQueryD()?.popover
            if (popoverFun != undefined) getElementJQueryD()?.popover("dispose")
        }
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
     * Animate the widget changing CSS properties.
     * @param duration a duration of the animation
     * @param easing an easing function to use
     * @param complete a callback function called after the animation completes
     * @param styles changing properties values
     */
    open fun animate(
        duration: Int = 400,
        easing: Easing = Easing.SWING,
        complete: (() -> Unit)? = null,
        styles: StyledComponent.() -> Unit
    ) {
        val widget = Widget()
        widget.styles()
        val stylesList = widget.getSnStyle()
        val obj = js("{}")
        stylesList.forEach { (key, value) ->
            obj[key.toCamelCase()] = value
        }
        @Suppress("UnsafeCastFromDynamic")
        getElementJQuery()?.animate(obj, duration, easing.easing) {
            widget.dispose()
            this.styles()
            complete?.invoke()
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
            if (separator == null) {
                arrayOf(KVManager.virtualize("<i class='$icon'></i>"), " $translatedLabel")
            } else {
                arrayOf(KVManager.virtualize("<i class='$icon'></i>"), KVManager.virtualize(separator), translatedLabel)
            }
        } else if (image != null) {
            if (separator == null) {
                arrayOf(KVManager.virtualize("<img src='$image' alt='' />"), " $translatedLabel")
            } else {
                arrayOf(
                    KVManager.virtualize("<img src='$image' alt='' />"),
                    KVManager.virtualize(separator),
                    translatedLabel
                )
            }
        } else {
            arrayOf(translatedLabel)
        }
    }

    protected open fun dispatchEvent(type: String, eventInitDict: CustomEventInit): Boolean? {
        val event = org.w3c.dom.CustomEvent(type, eventInitDict)
        return this.getElement()?.dispatchEvent(event)
    }

    override fun dispose() {
        afterDisposeHooks.forEach { it() }
    }

    protected fun <T> refreshOnUpdate(refreshFunction: ((T) -> Unit) = { this.refresh() }): RefreshDelegateProvider<T> =
        RefreshDelegateProvider(null, refreshFunction)

    protected fun <T> refreshOnUpdate(
        initialValue: T,
        refreshFunction: ((T) -> Unit) = { this.refresh() }
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
                value as T
            } else {
                null as T
            }
        }

        operator fun setValue(thisRef: StyledComponent, property: KProperty<*>, value: T) {
            propertyValues[property.name] = value
            refreshFunction(value)
        }
    }

    companion object {
        private var counter: Int = 0

        /**
         * An extension function which binds the widget to the observable state.
         * Used by [pl.treksoft.kvision.state.bind]
         */
        internal fun <S, W : Widget> W.bindState(
            observableState: ObservableState<S>,
            removeChildren: Boolean = true,
            factory: (W.(S) -> Unit)
        ): W {
            this.addAfterDisposeHook(observableState.subscribe {
                singleRender {
                    if (removeChildren) (this as? Container)?.removeAll()
                    factory(it)
                }
            })
            return this
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.widget(
    classes: Set<String>? = null,
    className: String? = null,
    init: (Widget.() -> Unit)? = null
): Widget {
    val widget = Widget(classes ?: className.set).apply { init?.invoke(this) }
    this.add(widget)
    return widget
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.widget(
    state: ObservableState<S>,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Widget.(S) -> Unit)
) = widget(classes, className).bind(state, true, init)

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
