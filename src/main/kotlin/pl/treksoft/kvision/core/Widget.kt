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
import pl.treksoft.jquery.JQuery
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.utils.SnOn
import pl.treksoft.kvision.utils.hooks
import pl.treksoft.kvision.utils.on
import pl.treksoft.kvision.utils.snAttrs
import pl.treksoft.kvision.utils.snClasses
import pl.treksoft.kvision.utils.snOpt
import pl.treksoft.kvision.utils.snStyle
import kotlin.Unit

/**
 * Base widget class. The parent of all component classes.
 *
 * A simple widget is rendered as HTML DIV element.
 *
 * @constructor Creates basic Widget with given CSS class names.
 * @param classes Set of CSS class names
 */
@Suppress("TooManyFunctions", "LargeClass")
open class Widget(classes: Set<String> = setOf()) : StyledComponent() {

    internal val classes = classes.toMutableSet()
    internal val surroundingClasses: MutableSet<String> = mutableSetOf()
    internal val internalListeners = mutableListOf<SnOn<Widget>.() -> Unit>()
    internal val listeners = mutableListOf<SnOn<Widget>.() -> Unit>()

    override var parent: Component? = null

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
     * Determines if the current widget is draggable.
     */
    var draggable: Boolean? by refreshOnUpdate()

    internal var surroundingSpan by refreshOnUpdate(false)

    internal var eventTarget: Widget? = null

    private var vnode: VNode? = null

    private var snAttrsCache: List<StringPair>? = null
    private var snClassCache: List<StringBoolPair>? = null
    private var snOnCache: com.github.snabbdom.On? = null
    private var snHooksCache: com.github.snabbdom.Hooks? = null

    internal fun <T> singleRender(block: () -> T): T {
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
            snattrs.add("title" to it)
        }
        role?.let {
            snattrs.add("role" to it)
        }
        if (draggable == true) {
            snattrs.add("draggable" to "true")
        }
        return snattrs
    }

    /**
     * Returns list of event handlers in the form of a Snabbdom *On* object.
     * @return list of event handlers
     */
    protected open fun getSnOn(): com.github.snabbdom.On? {
        return if (internalListeners.size > 0 || listeners.size > 0) {
            val internalHandlers = on(this)
            internalListeners.forEach { l -> (internalHandlers::apply)(l) }
            val handlers = on(eventTarget ?: this)
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
                afterDestroy()
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
    @Suppress("UNCHECKED_CAST")
    protected fun <T : Widget> setInternalEventListener(block: SnOn<T>.() -> Unit): Widget {
        internalListeners.add(block as SnOn<Widget>.() -> Unit)
        refresh()
        return this
    }

    /**
     * @suppress
     * Internal function
     */
    protected fun setInternalEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        internalListeners.add(block)
        refresh()
        return this
    }

    /**
     * Sets an event listener for current widget, keeping the actual type of component.
     * @param T widget type
     * @param block event handler
     * @return current widget
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
    @Suppress("UNCHECKED_CAST")
    open fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Widget {
        listeners.add(block as SnOn<Widget>.() -> Unit)
        refresh()
        return this
    }

    /**
     * Sets an event listener for current widget.
     * @param block event handler
     * @return current widget
     *
     * Example:
     *
     *      button.setEventListener {
     *          dblclick = {
     *              Alert.show("Button double clicked!")
     *              // self is of type Widget here
     *          }
     *      }
     */
    open fun setEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        listeners.add(block)
        refresh()
        return this
    }

    /**
     * Removes all event listeners from current widget.
     * @return current widget
     */
    open fun removeEventListeners(): Widget {
        listeners.clear()
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
     * Method called after inserting Snabbdom vnode into the DOM.
     */
    protected open fun afterInsert(node: VNode) {
    }

    /**
     * Method called after updating Snabbdom vnode.
     */
    protected open fun afterPostpatch(node: VNode) {
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
        image: ResString? = null
    ): Array<out Any> {
        return if (icon != null) {
            if (icon.startsWith("fa-")) {
                arrayOf(KVManager.virtualize("<i class='fa $icon'></i>"), " " + label)
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

    override fun dispose() {
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.widget(classes: Set<String> = setOf(), init: (Widget.() -> Unit)? = null): Widget {
            val widget = Widget(classes).apply { init?.invoke(this) }
            this.add(widget)
            return widget
        }
    }
}
