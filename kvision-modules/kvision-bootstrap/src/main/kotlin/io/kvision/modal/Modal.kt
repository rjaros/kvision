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
package io.kvision.modal

import com.github.snabbdom.VNode
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.core.Widget
import io.kvision.html.Button
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.panel.Root
import io.kvision.panel.Root.Companion.addModal
import io.kvision.panel.Root.Companion.removeModal
import io.kvision.panel.SimplePanel
import io.kvision.utils.obj

/**
 * Modal window sizes.
 */
enum class ModalSize(override val className: String) : CssClass {
    XLARGE("modal-xl"),
    LARGE("modal-lg"),
    SMALL("modal-sm")
}

/**
 * Configurable modal window based on Bootstrap modal.
 *
 * @constructor
 * @param caption window title
 * @param closeButton determines if Close button is visible
 * @param size modal window size
 * @param animation determines if animations are used
 * @param centered determines if modal dialog is vertically centered
 * @param scrollable determines if modal dialog content is scrollable
 * @param escape determines if dialog can be closed with Esc key
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Modal(
    caption: String? = null, closeButton: Boolean = true,
    size: ModalSize? = null, animation: Boolean = true, centered: Boolean = false,
    scrollable: Boolean = false, private val escape: Boolean = true,
    classes: Set<String> = setOf(), init: (Modal.() -> Unit)? = null
) : SimplePanel(classes) {

    override var parent: Container? = Root.getFirstRoot()

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
     * Determines if Close button is visible.
     */
    var closeButton
        get() = closeIcon.visible
        set(value) {
            closeIcon.visible = value
            checkHeaderVisibility()
        }

    /**
     * Window size.
     */
    var size
        get() = dialog.size
        set(value) {
            dialog.size = value
        }

    /**
     * Determines if animations are used.
     */
    var animation by refreshOnUpdate(animation)

    /**
     * Determines if modal dialog is vertically centered.
     */
    var centered
        get() = dialog.centered
        set(value) {
            dialog.centered = value
        }

    /**
     * Determines if modal body is scrollable.
     */
    var scrollable
        get() = dialog.scrollable
        set(value) {
            dialog.scrollable = value
        }

    private val dialog = ModalDialog(size, centered, scrollable)
    private val header = SimplePanel(setOf("modal-header"))

    /**
     * @suppress
     * Internal property.
     */
    protected val closeIcon = CloseIcon()
    private val captionTag = Tag(TAG.H5, caption, classes = setOf("modal-title"))

    /**
     * @suppress
     * Internal property.
     */
    protected val body = SimplePanel(setOf("modal-body"))
    private val footer = SimplePanel(setOf("modal-footer"))

    init {
        this.hide()
        this.role = "dialog"
        this.tabindex = -1
        this.addPrivate(dialog)
        val content = SimplePanel(setOf("modal-content"))
        dialog.role = "document"
        dialog.add(content)
        closeIcon.visible = closeButton
        closeIcon.setEventListener<CloseIcon> {
            click = {
                hide()
            }
        }
        header.add(captionTag)
        header.add(closeIcon)
        checkHeaderVisibility()
        content.add(header)
        content.add(body)
        content.add(footer)
        @Suppress("LeakingThis")
        addModal(this)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    private fun checkHeaderVisibility() {
        if (!closeButton && caption == null) {
            header.hide()
        } else {
            header.show()
        }
    }

    override fun add(child: Component): Modal {
        body.add(child)
        return this
    }

    override fun add(position: Int, child: Component): Modal {
        body.add(position, child)
        return this
    }

    override fun addAll(children: List<Component>): Modal {
        body.addAll(children)
        return this
    }

    override fun remove(child: Component): Modal {
        body.remove(child)
        return this
    }

    override fun removeAt(position: Int): Modal {
        body.removeAt(position)
        return this
    }

    override fun removeAll(): Modal {
        body.removeAll()
        return this
    }

    override fun disposeAll(): Modal {
        body.disposeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return body.getChildren()
    }

    /**
     * Adds given button to the bottom section of dialog window.
     * @param button a [Button] component
     * @return this modal
     */
    open fun addButton(button: Button): Modal {
        footer.add(button)
        return this
    }

    /**
     * Removes given button from the bottom section of dialog window.
     * @param button a [Button] component
     * @return this modal
     */
    open fun removeButton(button: Button): Modal {
        footer.remove(button)
        return this
    }

    /**
     * Removes all buttons from the bottom section of dialog window.
     * @return this modal
     */
    open fun removeAllButtons(): Modal {
        footer.removeAll()
        return this
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add("modal")
        if (animation) {
            classSetBuilder.add("fade")
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        getElementJQueryD()?.modal(obj {
            keyboard = escape
            backdrop = if (escape) "true" else "static"
        })
        this.getElementJQuery()?.on("show.bs.modal") { e, _ ->
            this.dispatchEvent("showBsModal", obj { detail = e })
        }
        this.getElementJQuery()?.on("shown.bs.modal") { e, _ ->
            this.dispatchEvent("shownBsModal", obj { detail = e })
        }
        this.getElementJQuery()?.on("hide.bs.modal") { e, _ ->
            this.dispatchEvent("hideBsModal", obj { detail = e })
        }
        this.getElementJQuery()?.on("hidden.bs.modal") { e, _ ->
            this.visible = false
            hide()
            this.dispatchEvent("hiddenBsModal", obj { detail = e })
        }
    }

    override fun hide(): Widget {
        if (visible) hideInternal()
        return super.hide()
    }

    /**
     * Toggle modal window visibility.
     */
    open fun toggle() {
        if (visible)
            hide()
        else
            show()
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun showInternal() {
        getElementJQueryD()?.modal("show")
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun hideInternal() {
        getElementJQueryD()?.modal("hide")
    }

    override fun clearParent(): Widget {
        this.parent = null
        return this
    }

    override fun getRoot(): Root? {
        return this.parent?.getRoot()
    }

    override fun dispose() {
        super.dispose()
        removeModal(this)
    }
}

/**
 * Internal helper class for modal content.
 *
 * @constructor
 * @param size modal window size
 * @param centered determines if modal dialog is vertically centered
 * @param scrollable determines if modal dialog content is scrollable
 */
internal class ModalDialog(size: ModalSize?, centered: Boolean = false, scrollable: Boolean = false) :
    SimplePanel(setOf("modal-dialog")) {

    /**
     * Modal window size.
     */
    var size by refreshOnUpdate(size)

    /**
     * Determines if modal dialog is vertically centered.
     */
    var centered by refreshOnUpdate(centered)

    /**
     * Determines if content is scrollable.
     */
    var scrollable by refreshOnUpdate(scrollable)

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(size)
        if (centered) {
            classSetBuilder.add("modal-dialog-centered")
        }
        if (scrollable) {
            classSetBuilder.add("modal-dialog-scrollable")
        }
    }
}
