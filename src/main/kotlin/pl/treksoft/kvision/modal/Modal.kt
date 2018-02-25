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
package pl.treksoft.kvision.modal

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.obj

/**
 * Modal window sizes.
 */
enum class ModalSize(val className: String) {
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
 * @param escape determines if dialog can be closed with Esc key
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Modal(
    caption: String? = null, closeButton: Boolean = true,
    size: ModalSize? = null, animation: Boolean = true, private val escape: Boolean = true,
    classes: Set<String> = setOf(), init: (Modal.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * Window content text.
     */
    var caption
        get() = captionTag.text
        set(value) {
            captionTag.text = value
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

    private val dialog = ModalDialog(size)
    private val header = SimplePanel(setOf("modal-header"))
    /**
     * @suppress
     * Internal property.
     */
    protected val closeIcon = CloseIcon()
    private val captionTag = Tag(TAG.H4, caption, classes = setOf("modal-title"))
    /**
     * @suppress
     * Internal property.
     */
    protected val body = SimplePanel(setOf("modal-body"))
    private val footer = SimplePanel(setOf("modal-footer"))

    init {
        this.hide()
        this.role = "dialog"
        this.addInternal(dialog)
        val content = SimplePanel(setOf("modal-content"))
        dialog.role = "document"
        dialog.add(content)
        closeIcon.visible = closeButton
        closeIcon.setEventListener {
            click = {
                hide()
            }
        }
        header.add(closeIcon)
        header.add(captionTag)
        checkHeaderVisibility()
        content.add(header)
        content.add(body)
        content.add(footer)
        val root = Root.getLastRoot()
        if (root != null) {
            @Suppress("LeakingThis")
            root.addModal(this)
        } else {
            println("At least one Root object is required to create a modal!")
        }
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

    override fun add(child: Component): SimplePanel {
        body.add(child)
        return this
    }

    override fun addAll(children: List<Component>): SimplePanel {
        body.addAll(children)
        return this
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

    override fun getSnAttrs(): List<StringPair> {
        val pr = super.getSnAttrs().toMutableList()
        pr.add("tabindex" to "-1")
        return pr
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("modal" to true)
        if (animation) {
            cl.add("fade" to true)
        }
        return cl
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        getElementJQueryD()?.modal(obj {
            keyboard = escape
            backdrop = if (escape) "true" else "static"
        })
        this.getElementJQuery()?.on("show.bs.modal", { e, _ ->
            this.dispatchEvent("showBsModal", obj { detail = e })
        })
        this.getElementJQuery()?.on("shown.bs.modal", { e, _ ->
            this.dispatchEvent("shownBsModal", obj { detail = e })
        })
        this.getElementJQuery()?.on("hide.bs.modal", { e, _ ->
            this.dispatchEvent("hideBsModal", obj { detail = e })
        })
        this.getElementJQuery()?.on("hidden.bs.modal", { e, _ ->
            this.visible = false
            this.dispatchEvent("hiddenBsModal", obj { detail = e })
        })
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
}

/**
 * Internal helper class for modal content.
 *
 * @constructor
 * @param size modal window size
 */
internal class ModalDialog(size: ModalSize?) : SimplePanel(setOf("modal-dialog")) {

    /**
     * Modal window size.
     */
    var size by refreshOnUpdate(size)

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }
}
