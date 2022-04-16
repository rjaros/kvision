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
package io.kvision.window

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.Overflow
import io.kvision.core.Position
import io.kvision.core.Resize
import io.kvision.core.UNIT
import io.kvision.html.Icon
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.modal.CloseIcon
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode
import io.kvision.utils.height
import io.kvision.utils.obj
import io.kvision.utils.offsetLeft
import io.kvision.utils.offsetTop
import io.kvision.utils.px
import org.w3c.dom.Element
import org.w3c.dom.TouchEvent
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.get

internal const val DEFAULT_Z_INDEX = 900
internal const val WINDOW_HEADER_HEIGHT = 40
internal const val WINDOW_CONTENT_MARGIN_BOTTOM = 11

/**
 * Floating window container.
 *
 * @constructor
 * @param caption window title
 * @param contentWidth window content width
 * @param contentHeight window content height
 * @param isResizable determines if the window is resizable
 * @param isDraggable determines if the window is draggable
 * @param closeButton determines if Close button is visible
 * @param maximizeButton determines if Maximize button is visible
 * @param minimizeButton determines if Minimize button is visible
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Window(
    caption: String? = null,
    contentWidth: CssSize? = CssSize(0, UNIT.auto),
    contentHeight: CssSize? = CssSize(0, UNIT.auto),
    isResizable: Boolean = true,
    isDraggable: Boolean = true,
    closeButton: Boolean = false,
    maximizeButton: Boolean = false,
    minimizeButton: Boolean = false,
    icon: String? = null,
    className: String? = null,
    init: (Window.() -> Unit)? = null
) :
    SimplePanel((className?.let { "$it " } ?: "") + "modal-content kv-window") {

    /**
     * Window caption text.
     */
    var caption
        get() = captionContainer.content
        set(value) {
            captionContainer.content = value
            checkHeaderVisibility()
        }

    /**
     * Window content width.
     */
    var contentWidth
        get() = content.width
        set(value) {
            content.width = value
        }

    /**
     * Window content height.
     */
    var contentHeight
        get() = content.height
        set(value) {
            content.height = value
        }

    /**
     * Window content overflow.
     */
    var contentOverflow
        get() = content.overflow
        set(value) {
            content.overflow = value
        }

    /**
     * Determines if the window is resizable.
     */
    var isResizable by refreshOnUpdate(isResizable) { checkIsResizable() }

    /**
     * Determines if the window is draggable.
     */
    var isDraggable by refreshOnUpdate(isDraggable) { checkIsDraggable(); checkHeaderVisibility() }

    /**
     * Determines if Close button is visible.
     */
    var closeButton
        get() = closeIcon.visible
        set(value) {
            closeIcon.visible = value
            checkHeaderVisibility()
        }

    /**
     * Determines if Maximize button is visible.
     */
    var maximizeButton
        get() = maximizeIcon.visible
        set(value) {
            maximizeIcon.visible = value
            checkHeaderVisibility()
        }

    /**
     * Determines if Maximize button is visible.
     */
    var minimizeButton
        get() = minimizeIcon.visible
        set(value) {
            minimizeIcon.visible = value
            checkHeaderVisibility()
        }

    /**
     * Window icon.
     */
    var icon
        get() = if (windowIcon.icon == "") null else windowIcon.icon
        set(value) {
            windowIcon.icon = value ?: ""
            windowIcon.visible = (value != null && value != "")
        }

    /**
     * The header of the window.
     */
    protected val header = SimplePanel("modal-header")

    /**
     * The content of the window.
     */
    protected val content = SimplePanel().apply {
        this.height = contentHeight
        this.width = contentWidth
        this.overflow = Overflow.AUTO
    }

    /**
     * The close icon.
     */
    protected val closeIcon = CloseIcon()

    /**
     * The maximize icon.
     */
    protected val maximizeIcon = MaximizeIcon()

    /**
     * The minimize icon.
     */
    protected val minimizeIcon = MinimizeIcon()

    /**
     * The caption container.
     */
    protected val captionContainer = Tag(TAG.H5, caption, className = "modal-title")

    /**
     * The icons container.
     */
    protected val iconsContainer = SimplePanel("kv-window-icons-container")

    /**
     * The window icon.
     */
    protected val windowIcon = Icon(icon ?: "").apply {
        addCssClass("window-icon")
        visible = (icon != null && icon != "")
    }

    private var isResizeEvent = false

    init {
        id = "kv_window_$counter"
        @Suppress("LeakingThis")
        position = Position.ABSOLUTE
        @Suppress("LeakingThis")
        overflow = Overflow.HIDDEN
        @Suppress("LeakingThis")
        width = contentWidth
        @Suppress("LeakingThis")
        zIndex = ++zIndexCounter
        header.add(captionContainer)
        captionContainer.add(windowIcon)
        header.add(iconsContainer)
        minimizeIcon.visible = minimizeButton
        minimizeIcon.setEventListener<MinimizeIcon> {
            click = { _ ->
                @Suppress("UnsafeCastFromDynamic")
                if (this@Window.dispatchEvent("minimizeWindow", obj {}) != false) {
                    toggleMinimize()
                }
            }
            mousedown = { e ->
                e.stopPropagation()
            }
        }
        iconsContainer.add(minimizeIcon)
        maximizeIcon.visible = maximizeButton
        maximizeIcon.setEventListener<MaximizeIcon> {
            click = { _ ->
                @Suppress("UnsafeCastFromDynamic")
                if (this@Window.dispatchEvent("maximizeWindow", obj {}) != false) {
                    toggleMaximize()
                }
            }
            mousedown = { e ->
                e.stopPropagation()
            }
        }
        iconsContainer.add(maximizeIcon)
        closeIcon.visible = closeButton
        closeIcon.setEventListener<CloseIcon> {
            click = { _ ->
                @Suppress("UnsafeCastFromDynamic")
                if (this@Window.dispatchEvent("closeWindow", obj {}) != false) {
                    close()
                }
            }
            mousedown = { e ->
                e.stopPropagation()
            }
        }
        iconsContainer.add(closeIcon)
        checkHeaderVisibility()
        addPrivate(header)
        addPrivate(content)
        checkIsDraggable()
        if (isResizable) {
            @Suppress("LeakingThis")
            resize = Resize.BOTH
            content.marginBottom = WINDOW_CONTENT_MARGIN_BOTTOM.px
        }
        @Suppress("LeakingThis")
        setEventListener<Window> {
            click = {
                toFront()
                focus()
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
        windows.add(this)
        counter++
    }

    /**
     * Hides od shows the header based on contitions.
     */
    protected open fun checkHeaderVisibility() {
        @Suppress("ComplexCondition")
        if (!closeButton && !maximizeButton && !minimizeButton && caption == null && !isDraggable) {
            header.hide()
        } else {
            header.show()
        }
    }

    private fun checkIsDraggable() {
        var isDrag: Boolean
        var dragStartDispatched = false
        if (isDraggable) {
            fun processDragStart(e: Event) {
                if (e is MouseEvent && e.button.toInt() == 0 || e is TouchEvent) {
                    isDrag = true
                    val dragStartX = this@Window.getElement()?.offsetLeft() ?: 0
                    val dragStartY = this@Window.getElement()?.offsetTop() ?: 0
                    val (dragMouseX, dragMouseY) = if (e is MouseEvent) {
                        e.pageX.toInt() to e.pageY.toInt()
                    } else {
                        e.unsafeCast<TouchEvent>().touches[0]!!.pageX to e.unsafeCast<TouchEvent>().touches[0]!!.pageY
                    }
                    val moveCallback = { me: Event ->
                        if (isDrag) {
                            if (!dragStartDispatched) {
                                @Suppress("UnsafeCastFromDynamic")
                                this@Window.dispatchEvent("dragStartWindow", obj {
                                    detail = me
                                })
                                dragStartDispatched = true
                            }
                            val (mouseX, mouseY) = if (me is MouseEvent) {
                                me.pageX.toInt() to me.pageY.toInt()
                            } else {
                                me.unsafeCast<TouchEvent>().touches[0]!!.pageX to me.unsafeCast<TouchEvent>().touches[0]!!.pageY
                            }
                            this@Window.left = (dragStartX + mouseX - dragMouseX).px
                            this@Window.top = (dragStartY + mouseY - dragMouseY).px
                            if (me is TouchEvent) {
                                me.preventDefault()
                            }
                        }
                    }
                    kotlinx.browser.window.addEventListener("mousemove", moveCallback)
                    kotlinx.browser.window.addEventListener("touchmove", moveCallback, obj { passive = false })
                    var upCallback: ((Event) -> Unit)? = null
                    upCallback = {
                        isDrag = false
                        if (dragStartDispatched) {
                            @Suppress("UnsafeCastFromDynamic")
                            this@Window.dispatchEvent("dragEndWindow", obj {
                                detail = e
                            })
                            dragStartDispatched = false
                        }
                        kotlinx.browser.window.removeEventListener("mousemove", moveCallback)
                        kotlinx.browser.window.removeEventListener("touchmove", moveCallback)
                        kotlinx.browser.window.removeEventListener("mouseup", upCallback)
                        kotlinx.browser.window.removeEventListener("touchend", upCallback)
                    }
                    kotlinx.browser.window.addEventListener("mouseup", upCallback)
                    kotlinx.browser.window.addEventListener("touchend", upCallback)
                }
            }

            header.setEventListener<SimplePanel> {
                mousedown = {
                    processDragStart(it)
                }
                touchstart = {
                    processDragStart(it.unsafeCast<TouchEvent>())
                }
            }
        } else {
            isDrag = false
            header.removeEventListeners()
        }
    }

    private fun checkIsResizable() {
        checkResizablEventHandler()
        val headerHeight = if (header.visible) WINDOW_HEADER_HEIGHT else 0
        if (isResizable) {
            resize = Resize.BOTH
            val intHeight = getElement()?.height() ?: 0
            content.height = (intHeight - headerHeight - WINDOW_CONTENT_MARGIN_BOTTOM).px
            content.marginBottom = WINDOW_CONTENT_MARGIN_BOTTOM.px
        } else {
            resize = Resize.NONE
            val intHeight = getElement()?.height() ?: 0
            content.height = (intHeight - headerHeight).px
            content.marginBottom = 0.px
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun checkResizablEventHandler() {
        if (isResizable) {
            var resizeStartDispatched = false
            var initialResizeEntry = true
            var resizeTimeoutId: Int? = null
            getElement()?.unsafeCast<Element>()?.let {
                if (!isResizeEvent) {
                    isResizeEvent = true
                    resizeObserver.observe(it)
                    resizeCallbacks[it] = { entry ->
                        val borderSize = entry.borderBoxSize[0]
                        val contentSize = entry.contentBoxSize[0]
                        width = borderSize.inlineSize.toInt().px
                        height = borderSize.blockSize.toInt().px
                        val headerHeight = if (header.visible) WINDOW_HEADER_HEIGHT else 0
                        contentWidth = contentSize.inlineSize.toInt().px
                        contentHeight =
                            (contentSize.blockSize.toInt() - headerHeight - WINDOW_CONTENT_MARGIN_BOTTOM).px
                        if (initialResizeEntry) {
                            initialResizeEntry = false
                        } else {
                            if (!resizeStartDispatched) {
                                this.dispatchEvent("resizeStartWindow", obj {
                                    detail = obj {
                                        this.width = borderSize.inlineSize
                                        this.height = borderSize.blockSize
                                        this.resizeObserverEntry = entry
                                    }
                                })
                                resizeStartDispatched = true
                            }
                            resizeTimeoutId?.let {
                                kotlinx.browser.window.clearTimeout(it)
                            }
                            resizeTimeoutId = kotlinx.browser.window.setTimeout({
                                this.dispatchEvent("resizeWindow", obj {
                                    detail = obj {
                                        this.width = borderSize.inlineSize
                                        this.height = borderSize.blockSize
                                        this.resizeObserverEntry = entry
                                    }
                                })
                                resizeStartDispatched = false
                            }, 200)
                        }
                    }
                }
            }
        } else if (isResizeEvent) {
            getElement()?.unsafeCast<Element>()?.let {
                resizeCallbacks.remove(it)
                resizeObserver.unobserve(it)
            }
            isResizeEvent = false
        }
    }

    override fun add(child: Component): Window {
        content.add(child)
        return this
    }

    override fun add(position: Int, child: Component): Window {
        content.add(position, child)
        return this
    }

    override fun addAll(children: List<Component>): Window {
        content.addAll(children)
        return this
    }

    override fun remove(child: Component): Window {
        content.remove(child)
        return this
    }

    override fun removeAt(position: Int): Window {
        content.removeAt(position)
        return this
    }

    override fun removeAll(): Window {
        content.removeAll()
        return this
    }

    override fun disposeAll(): Window {
        content.disposeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return content.getChildren()
    }

    override fun afterCreate(node: VNode) {
        checkResizablEventHandler()
    }

    override fun afterDestroy() {
        if (isResizeEvent) {
            getElement()?.unsafeCast<Element>()?.let {
                resizeCallbacks.remove(it)
                resizeObserver.unobserve(it)
            }
            isResizeEvent = false
        }
    }

    /**
     * Moves the current window to the front.
     */
    open fun toFront() {
        if ((zIndex ?: 0) < zIndexCounter) zIndex = ++zIndexCounter
    }

    /**
     * Returns true if the window is active (on front).
     */
    open fun isActive(): Boolean {
        return getActiveWindow() == this
    }

    /**
     * Close the window.
     */
    open fun close() {
        hide()
    }

    /**
     * Maximize or restore the window size.
     */
    open fun toggleMaximize() {
    }

    /**
     * Minimize or restore the window size.
     */
    open fun toggleMinimize() {
    }

    override fun dispose() {
        windows.remove(this)
        super.dispose()
    }

    companion object {
        internal var counter = 0
        internal var zIndexCounter = DEFAULT_Z_INDEX
        internal val windows: MutableList<Window> = mutableListOf()

        internal val resizeCallbacks = mutableMapOf<Element, (ResizeObserverEntry) -> Unit>()

        internal val resizeObserver = ResizeObserver { entries, _ ->
            entries.forEach {
                resizeCallbacks[it.target]?.invoke(it)
            }
        }

        /**
         * Get active window (a window on front).
         */
        fun getActiveWindow(): Window? {
            return windows.filter { it.visible }.maxByOrNull { it.zIndex ?: 0 }
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.window(
    caption: String? = null,
    contentWidth: CssSize? = CssSize(0, UNIT.auto),
    contentHeight: CssSize? = CssSize(0, UNIT.auto),
    isResizable: Boolean = true,
    isDraggable: Boolean = true,
    closeButton: Boolean = false,
    maximizeButton: Boolean = false,
    minimizeButton: Boolean = false,
    icon: String? = null,
    className: String? = null,
    init: (Window.() -> Unit)? = null
): Window {
    val window =
        Window(
            caption,
            contentWidth,
            contentHeight,
            isResizable,
            isDraggable,
            closeButton,
            maximizeButton,
            minimizeButton,
            icon,
            className,
            init
        )
    this.add(window)
    return window
}
