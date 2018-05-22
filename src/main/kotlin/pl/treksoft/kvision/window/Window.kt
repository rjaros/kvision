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
package pl.treksoft.kvision.window

import com.github.snabbdom.VNode
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.Overflow
import pl.treksoft.kvision.core.Position
import pl.treksoft.kvision.core.Resize
import pl.treksoft.kvision.core.UNIT
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.modal.CloseIcon
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.px

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
    var isResizable by refreshOnUpdate(isResizable, { checkIsResizable() })
    /**
     * Determines if the window is draggable.
     */
    var isDraggable by refreshOnUpdate(isDraggable, { checkIsDraggable(); checkHeaderVisibility() })
    /**
     * Determines if Close button is visible.
     */
    var closeButton
        get() = closeIcon.visible
        set(value) {
            closeIcon.visible = value
            checkHeaderVisibility()
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
    private val captionTag = Tag(TAG.H4, caption, classes = setOf("modal-title"))

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
        closeIcon.visible = closeButton
        closeIcon.setEventListener {
            click = {
                hide()
            }
        }
        header.add(closeIcon)
        header.add(captionTag)
        checkHeaderVisibility()
        addInternal(header)
        addInternal(content)
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
        if (!closeButton && caption == null && !isDraggable) {
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
                        kotlin.browser.window.addEventListener("mousemove", moveCallback)
                        var upCallback: ((Event) -> Unit)? = null
                        upCallback = { _ ->
                            isDrag = false
                            kotlin.browser.window.removeEventListener("mousemove", moveCallback)
                            kotlin.browser.window.removeEventListener("mouseup", upCallback)
                        }
                        kotlin.browser.window.addEventListener("mouseup", upCallback)
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
            val intHeight = (getElementJQuery()?.height()?.toInt() ?: 0) + 2
            this.height = (intHeight + WINDOW_CONTENT_MARGIN_BOTTOM - 1).px
            content.marginBottom = WINDOW_CONTENT_MARGIN_BOTTOM.px
        } else {
            resize = Resize.NONE
            val intHeight = (getElementJQuery()?.height()?.toInt() ?: 0) + 2
            this.height = (intHeight - WINDOW_CONTENT_MARGIN_BOTTOM - 1).px
            content.marginBottom = 0.px
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun checkResizablEventHandler() {
        if (isResizable) {
            isResizeEvent = true
            KVManager.setResizeEvent(this) {
                val eid = getElementJQuery()?.attr("id")
                if (eid == id) {
                    val intWidth = (getElementJQuery()?.width()?.toInt() ?: 0) + 2
                    val intHeight = (getElementJQuery()?.height()?.toInt() ?: 0) + 2
                    isResizable = false
                    width = intWidth.px
                    height = intHeight.px
                    isResizable = true
                    content.width = (intWidth - 2).px
                    content.height = (intHeight - WINDOW_HEADER_HEIGHT - WINDOW_CONTENT_MARGIN_BOTTOM - 1 - 2).px
                    this.dispatchEvent("resizeWindow", obj {
                        detail = obj {
                            this.width = intWidth
                            this.height = intHeight
                        }
                    })
                }
            }
        } else if (isResizeEvent) {
            KVManager.clearResizeEvent(this)
        }
    }

    override fun add(child: Component): SimplePanel {
        content.add(child)
        return this
    }

    override fun addAll(children: List<Component>): SimplePanel {
        content.addAll(children)
        return this
    }

    override fun remove(child: Component): SimplePanel {
        content.remove(child)
        return this
    }

    override fun removeAll(): SimplePanel {
        content.removeAll()
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
            KVManager.clearResizeEvent(this)
        }
    }

    /**
     * Moves the current window to the front.
     */
    open fun toFront() {
        if ((zIndex ?: 0) < zIndexCounter) zIndex = ++zIndexCounter
    }

    /**
     * Makes the current window focused.
     */
    open fun focus() {
        getElementJQuery()?.focus()
    }

    companion object {
        internal var counter = 0
        internal var zIndexCounter = DEFAULT_Z_INDEX

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
            classes: Set<String> = setOf(),
            init: (Window.() -> Unit)? = null
        ): Window {
            val window =
                Window(caption, contentWidth, contentHeight, isResizable, isDraggable, closeButton, classes, init)
            this.add(window)
            return window
        }
    }
}
