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

import com.github.snabbdom.VNode
import io.kvision.KVManagerBootstrap
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.Overflow
import io.kvision.core.Position
import io.kvision.core.Resize
import io.kvision.core.UNIT
import io.kvision.core.getElementJQuery
import io.kvision.html.Icon
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.modal.CloseIcon
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.obj
import io.kvision.utils.px
import io.kvision.utils.set
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

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
 * @param classes a set of CSS class names
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
    classes: Set<String> = setOf(),
    init: (Window.() -> Unit)? = null
) :
    SimplePanel(classes + setOf("modal-content", "kv-window")) {

    /**
     * Window caption text.
     */
    var caption
        get() = captionTag.content
        set(value) {
            captionTag.content = value
            checkHeaderVisibility()
        }

    /**
     * Window content width.
     */
    var contentWidth
        get() = width
        set(value) {
            width = value
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
     * Window content height.
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

    private val header = SimplePanel(setOf("modal-header"))

    /**
     * @suppress
     * Internal property.
     */
    protected val content = SimplePanel().apply {
        this.height = contentHeight
        this.overflow = Overflow.AUTO
    }
    private val closeIcon = CloseIcon()
    private val maximizeIcon = MaximizeIcon()
    private val minimizeIcon = MinimizeIcon()
    private val captionTag = Tag(TAG.H5, caption, classes = setOf("modal-title"))
    private val iconsContainer = SimplePanel(setOf("kv-window-icons-container"))
    private val windowIcon = Icon(icon ?: "").apply {
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
        header.add(captionTag)
        captionTag.add(windowIcon)
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
        counter++
    }

    private fun checkHeaderVisibility() {
        @Suppress("ComplexCondition")
        if (!closeButton && !maximizeButton && !minimizeButton && caption == null && !isDraggable) {
            header.hide()
        } else {
            header.show()
        }
    }

    private fun checkIsDraggable() {
        var isDrag: Boolean
        if (isDraggable) {
            header.setEventListener<SimplePanel> {
                mousedown = { e ->
                    if (e.button.toInt() == 0) {
                        isDrag = true
                        val dragStartX = this@Window.getElementJQuery()?.position()?.left?.toInt() ?: 0
                        val dragStartY = this@Window.getElementJQuery()?.position()?.top?.toInt() ?: 0
                        val dragMouseX = e.pageX
                        val dragMouseY = e.pageY
                        val moveCallback = { me: Event ->
                            if (isDrag) {
                                this@Window.left = (dragStartX + (me as MouseEvent).pageX - dragMouseX).toInt().px
                                this@Window.top = (dragStartY + (me).pageY - dragMouseY).toInt().px
                            }
                        }
                        kotlinx.browser.window.addEventListener("mousemove", moveCallback)
                        var upCallback: ((Event) -> Unit)? = null
                        upCallback = {
                            isDrag = false
                            kotlinx.browser.window.removeEventListener("mousemove", moveCallback)
                            kotlinx.browser.window.removeEventListener("mouseup", upCallback)
                        }
                        kotlinx.browser.window.addEventListener("mouseup", upCallback)
                    }
                }
            }
        } else {
            isDrag = false
            header.removeEventListeners()
        }
    }

    private fun checkIsResizable() {
        checkResizablEventHandler()
        if (isResizable) {
            resize = Resize.BOTH
            val intHeight = (getElementJQuery()?.height()?.toInt() ?: 0)
            content.height = (intHeight - WINDOW_HEADER_HEIGHT - WINDOW_CONTENT_MARGIN_BOTTOM).px
            content.marginBottom = WINDOW_CONTENT_MARGIN_BOTTOM.px
        } else {
            resize = Resize.NONE
            val intHeight = (getElementJQuery()?.height()?.toInt() ?: 0)
            content.height = (intHeight - WINDOW_HEADER_HEIGHT).px
            content.marginBottom = 0.px
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun checkResizablEventHandler() {
        if (isResizable) {
            if (!isResizeEvent) {
                isResizeEvent = true
                KVManagerBootstrap.setResizeEvent(this) {
                    val eid = getElementJQuery()?.attr("id")
                    if (isResizable && eid == id) {
                        val outerWidth = (getElementJQuery()?.outerWidth()?.toInt() ?: 0)
                        val outerHeight = (getElementJQuery()?.outerHeight()?.toInt() ?: 0)
                        val intWidth = (getElementJQuery()?.width()?.toInt() ?: 0)
                        val intHeight = (getElementJQuery()?.height()?.toInt() ?: 0)
                        content.width = intWidth.px
                        content.height = (intHeight - WINDOW_HEADER_HEIGHT - WINDOW_CONTENT_MARGIN_BOTTOM).px
                        width = outerWidth.px
                        height = outerHeight.px
                        this.dispatchEvent("resizeWindow", obj {
                            detail = obj {
                                this.width = outerWidth
                                this.height = outerHeight
                            }
                        })
                    }
                }
            }
        } else if (isResizeEvent) {
            KVManagerBootstrap.clearResizeEvent(this)
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
            KVManagerBootstrap.clearResizeEvent(this)
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

    companion object {
        internal var counter = 0
        internal var zIndexCounter = DEFAULT_Z_INDEX
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
    classes: Set<String>? = null,
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
            classes ?: className.set,
            init
        )
    this.add(window)
    return window
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.window(
    state: ObservableState<S>,
    caption: String? = null,
    contentWidth: CssSize? = CssSize(0, UNIT.auto),
    contentHeight: CssSize? = CssSize(0, UNIT.auto),
    isResizable: Boolean = true,
    isDraggable: Boolean = true,
    closeButton: Boolean = false,
    maximizeButton: Boolean = false,
    minimizeButton: Boolean = false,
    icon: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Window.(S) -> Unit)
) = window(
    caption,
    contentWidth,
    contentHeight,
    isResizable,
    isDraggable,
    closeButton,
    maximizeButton,
    minimizeButton,
    icon,
    classes,
    className
).bind(state, true, init)
