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
package io.kvision.toast

import io.kvision.core.BsBgColor
import io.kvision.core.BsColor
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.core.createBsInstance
import io.kvision.core.getBsInstance
import io.kvision.html.strong
import io.kvision.modal.CloseIcon
import io.kvision.panel.Root
import io.kvision.panel.Root.Companion.addModal
import io.kvision.panel.Root.Companion.removeModal
import io.kvision.panel.SimplePanel
import io.kvision.panel.simplePanel
import io.kvision.snabbdom.VNode

/**
 * Bootstrap toast container positions.
 */
enum class ToastContainerPosition(internal val position: Array<String>) {
    TOPRIGHT(arrayOf("top-0", "end-0")),
    TOPLEFT(arrayOf("top-0", "start-0")),
    TOPCENTER(arrayOf("top-0", "start-50", "translate-middle-x")),
    BOTTOMRIGHT(arrayOf("bottom-0", "end-0")),
    BOTTOMLEFT(arrayOf("bottom-0", "start-0")),
    BOTTOMCENTER(arrayOf("bottom-0", "start-50", "translate-middle-x")),
    MIDDLERIGHT(arrayOf("top-50", "end-0", "translate-middle-y")),
    MIDDLELEFT(arrayOf("top-50", "start-0", "translate-middle-y")),
    MIDDLECENTER(arrayOf("top-50", "start-50", "translate-middle"))
}

/**
 * Bootstrap toast container.
 *
 * @constructor
 * @param toastContainerPosition Bootstrap toast container position
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class ToastContainer(
    toastContainerPosition: ToastContainerPosition = ToastContainerPosition.TOPRIGHT,
    className: String? = null, init: (ToastContainer.() -> Unit)? = null
) : SimplePanel(className) {

    override var parent: Container? = Root.getFirstRoot()

    /**
     * Bootstrap toast container position.
     */
    var toastContainerPosition
        get() = bootstrapToastContainer.toastContainerPosition
        set(value) {
            bootstrapToastContainer.toastContainerPosition = value
        }

    private val bootstrapToastContainer = BootstrapToastContainer(toastContainerPosition)

    init {
        useSnabbdomDistinctKey()
        @Suppress("LeakingThis")
        addCssClass("position-relative")
        @Suppress("LeakingThis")
        setAttribute("aria-live", "polite")
        @Suppress("LeakingThis")
        setAttribute("aria-atomic", "true")
        this.addPrivate(bootstrapToastContainer)
        @Suppress("LeakingThis")
        addModal(this)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * Show Bootstrap toast with a given message and options.
     *
     * @param message Bootstrap toast message
     * @param title Bootstrap toast title
     * @param color Bootstrap toast text color
     * @param bgColor Bootstrap toast background color
     * @param autohide Bootstrap toast autohide flag
     * @param delay Bootstrap toast delay in milliseconds
     * @param animation Bootstrap toast animation flag
     * @param className CSS class names
     */
    open fun showToast(
        message: String,
        title: String? = null,
        color: BsColor? = null,
        bgColor: BsBgColor? = null,
        autohide: Boolean = true,
        delay: Int = 5000,
        animation: Boolean = true,
        className: String? = null
    ) {
        bootstrapToastContainer.add(
            BootstrapToast(
                message,
                title,
                color,
                bgColor,
                autohide,
                delay,
                animation,
                className
            )
        )
    }

    override fun add(child: Component): ToastContainer {
        bootstrapToastContainer.add(child)
        return this
    }

    override fun add(position: Int, child: Component): ToastContainer {
        bootstrapToastContainer.add(position, child)
        return this
    }

    override fun addAll(children: List<Component>): ToastContainer {
        bootstrapToastContainer.addAll(children)
        return this
    }

    override fun remove(child: Component): ToastContainer {
        bootstrapToastContainer.remove(child)
        return this
    }

    override fun removeAt(position: Int): ToastContainer {
        bootstrapToastContainer.removeAt(position)
        return this
    }

    override fun removeAll(): ToastContainer {
        bootstrapToastContainer.removeAll()
        return this
    }

    override fun disposeAll(): ToastContainer {
        bootstrapToastContainer.disposeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return bootstrapToastContainer.getChildren()
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
 * Internal helper class for Bootstrap toast container.
 *
 * @constructor
 * @param toastContainerPosition Bootstrap toast container position
 */
internal class BootstrapToastContainer(toastContainerPosition: ToastContainerPosition) :
    SimplePanel("toast-container position-fixed p-3") {

    /**
     * Toast container position.
     */
    var toastContainerPosition by refreshOnUpdate(toastContainerPosition)

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        toastContainerPosition.position.forEach { classSetBuilder.add(it) }
    }
}

/**
 * Internal helper class for Bootstrap toasts.
 *
 * @constructor
 * @param message Bootstrap toast message
 * @param title Bootstrap toast title
 * @param color Bootstrap toast text color
 * @param bgColor Bootstrap toast background color
 * @param autohide Bootstrap toast autohide flag
 * @param delay Bootstrap toast delay in milliseconds
 * @param animation Bootstrap toast animation flag
 * @param className CSS class names
 */
internal class BootstrapToast(
    message: String,
    title: String? = null,
    color: BsColor? = null,
    bgColor: BsBgColor? = null,
    autohide: Boolean = true,
    delay: Int = 5000,
    animation: Boolean = true,
    className: String? = null
) :
    SimplePanel(className) {

    init {
        useSnabbdomDistinctKey()
        addCssClass("toast")
        setAttribute("role", "alert")
        setAttribute("aria-live", "assertive")
        setAttribute("aria-atomic", "true")
        setAttribute("data-bs-autohide", autohide.toString())
        setAttribute("data-bs-delay", delay.toString())
        setAttribute("data-bs-animation", animation.toString())
        if (color != null) addCssClass(color.className)
        if (bgColor != null) addCssClass(bgColor.className)
        if (title != null) {
            simplePanel("toast-header") {
                strong(title, className = "me-auto")
                add(CloseIcon().apply {
                    setAttribute("data-bs-dismiss", "toast")
                })
            }
            simplePanel("toast-body") {
                +message
            }
        } else {
            simplePanel("d-flex") {
                simplePanel("toast-body") {
                    +message
                }
                add(CloseIcon().apply {
                    setAttribute("data-bs-dismiss", "toast")
                    addCssClass("me-2")
                    addCssClass("m-auto")
                })
            }
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        createBsInstance({ Toast }).show()
        this.getElement()?.addEventListener("hidden.bs.toast", { _ ->
            getBsInstance { Toast }?.dispose()
            this.parent?.remove(this)
            this.dispose()
        })
    }
}
