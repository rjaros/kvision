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
package io.kvision.offcanvas

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.core.createBsInstance
import io.kvision.core.getBsInstance
import io.kvision.html.H5
import io.kvision.modal.CloseIcon
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode
import io.kvision.utils.obj

/**
 * The offcanvas placement.
 */
enum class OffPlacement(override val className: String) : CssClass {
    START("offcanvas-start"),
    END("offcanvas-end"),
    TOP("offcanvas-top"),
    BOTTOM("offcanvas-bottom")
}

/**
 * The offcanvas responsive types.
 */
enum class OffResponsiveType(override val className: String) : CssClass {
    RESPONSIVESM("offcanvas-sm"),
    RESPONSIVEMD("offcanvas-md"),
    RESPONSIVELG("offcanvas-lg"),
    RESPONSIVEXL("offcanvas-xl"),
    RESPONSIVEXXL("offcanvas-xxl")
}

/**
 * The Bootstrap offcanvas component.
 *
 * @constructor
 * @param caption the offcanvas caption
 * @param placement the offcanvas placement
 * @param closeButton determines if Close button is visible
 * @param dark dark mode
 * @param responsiveType the offcanvas responsive type
 * @param scrollableBody determines if the page body is scrollable
 * @param backdrop determines if the backdrop is visible
 * @param escape determines if the offcanvas can be closed with the Esc key
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Offcanvas(
    caption: String? = null, placement: OffPlacement = OffPlacement.START,
    closeButton: Boolean = true, dark: Boolean = false,
    responsiveType: OffResponsiveType? = null,
    private val scrollableBody: Boolean = false, private val backdrop: Boolean = true,
    private val escape: Boolean = true,
    className: String? = null, init: (Offcanvas.() -> Unit)? = null
) : SimplePanel(className) {

    /**
     * The offcanvas caption text.
     */
    var caption
        get() = captionTag.content
        set(value) {
            captionTag.content = value
            checkHeaderVisibility()
        }

    /**
     * The offcanvas placement.
     */
    var placement by refreshOnUpdate(placement)

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
     * Dark mode.
     */
    var dark by refreshOnUpdate(dark)

    /**
     * The offcanvas responsive type.
     */
    var responsiveType by refreshOnUpdate(responsiveType)

    /**
     * The offcanvas header component.
     */
    val headerTag = SimplePanel("offcanvas-header")

    /**
     * The offcanvas caption component.
     */
    val captionTag = H5(caption, className = "offcanvas-title").apply {
        id = "kv_offcanvas_${counter}_title"
        headerTag.add(this)
    }

    /**
     * @suppress
     * Internal property.
     */
    protected val closeIcon = CloseIcon().apply {
        setAttribute("data-bs-dismiss", "offcanvas")
        setAttribute("data-bs-target", "#kv_offcanvas_${counter}")
        this.visible = closeButton
        headerTag.add(this)
    }

    /**
     * The offcanvas body component.
     */
    val body = SimplePanel("offcanvas-body")

    init {
        useSnabbdomDistinctKey()
        this.hide()
        id = "kv_offcanvas_${counter}"
        this.tabindex = -1
        this.setAttribute("aria-labelledby", "kv_offcanvas_${counter}_title")
        this.addPrivate(headerTag)
        this.addPrivate(body)
        checkHeaderVisibility()
        @Suppress("LeakingThis")
        init?.invoke(this)
        counter++
    }

    private fun checkHeaderVisibility() {
        if (!closeButton && caption == null) {
            headerTag.hide()
        } else {
            headerTag.show()
        }
    }

    override fun add(child: Component) {
        body.add(child)
    }

    override fun add(position: Int, child: Component) {
        body.add(position, child)
    }

    override fun addAll(children: List<Component>) {
        body.addAll(children)
    }

    override fun remove(child: Component) {
        body.remove(child)
    }

    override fun removeAt(position: Int) {
        body.removeAt(position)
    }

    override fun removeAll() {
        body.removeAll()
    }

    override fun disposeAll() {
        body.disposeAll()
    }

    override fun getChildren(): List<Component> {
        return body.getChildren()
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        if (dark) attributeSetBuilder.add("data-bs-theme", "dark")
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (responsiveType == null) {
            classSetBuilder.add("offcanvas")
        } else {
            classSetBuilder.add(responsiveType)
        }
        classSetBuilder.add(placement)
    }

    override fun afterInsert(node: VNode) {
        createBsInstance({ Offcanvas }, obj {
            this.scroll = scrollableBody
            this.keyboard = escape
            this.backdrop = if (backdrop && !escape) "static" else backdrop
        })
        this.getElement()?.addEventListener("hidden.bs.offcanvas", { _ ->
            if (this.visible) {
                this.visible = false
                hide()
            }
        })
        showBootstrap()
    }

    override fun hide() {
        if (visible) hideBootstrap()
        super.hide()
    }

    /**
     * Toggle offcanvas visibility.
     */
    open fun toggle() {
        if (visible)
            hide()
        else
            show()
    }

    /**
     * Show offcanvas with Bootstrap function.
     */
    fun showBootstrap() {
        getBsInstance { Offcanvas }?.show()
    }

    /**
     * Hide offcanvas with Bootstrap function.
     */
    fun hideBootstrap() {
        getBsInstance { Offcanvas }?.hide()
    }

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.offcanvas(
    caption: String? = null, placement: OffPlacement = OffPlacement.START,
    closeButton: Boolean = true, dark: Boolean = false,
    responsiveType: OffResponsiveType? = null,
    scrollableBody: Boolean = false, backdrop: Boolean = true, escape: Boolean = true,
    className: String? = null,
    init: (Offcanvas.() -> Unit)? = null
): Offcanvas {
    val offcanvas = Offcanvas(
        caption, placement, closeButton, dark, responsiveType, scrollableBody, backdrop, escape,
        className, init
    )
    this.add(offcanvas)
    return offcanvas
}
