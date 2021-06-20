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

package io.kvision.onsenui.dialog

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.core.getElementJQuery
import io.kvision.onsenui.BackButtonEvent
import io.kvision.onsenui.FloatDirection
import io.kvision.panel.Root
import io.kvision.panel.Root.Companion.addModal
import io.kvision.panel.SimplePanel
import io.kvision.utils.obj
import io.kvision.utils.set
import kotlin.js.Promise

/**
 * A popover component.
 *
 * @constructor Creates a popover component.
 * @param direction a direction of the popover
 * @param cancelable whether the popover can be canceled
 * @param animation determines if the transitions are animated
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Popover(
    direction: FloatDirection? = null,
    cancelable: Boolean? = null,
    animation: Boolean? = null,
    classes: Set<String> = setOf(),
    init: (Popover.() -> Unit)? = null
) : SimplePanel(classes) {

    override var parent: Container? = Root.getFirstRoot()

    /**
     * The direction of the popover.
     */
    var direction: FloatDirection? by refreshOnUpdate(direction)

    /**
     * Whether the popover can be canceled.
     */
    var cancelable: Boolean? by refreshOnUpdate(cancelable)

    /**
     * Determines if the transitions are animated.
     */
    var animation: Boolean? by refreshOnUpdate(animation)

    /**
     * Color of the background mask.
     */
    var maskColor: String? by refreshOnUpdate()

    /**
     * Whether the popover is disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    /**
     * Whether the popover covers the target component.
     */
    var coverTarget: Boolean? by refreshOnUpdate()

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * A dynamic property returning whether the popover is visible.
     */
    @Suppress("UnsafeCastFromDynamic")
    val isVisible: Boolean?
        get() = getElement()?.asDynamic()?.visible

    /**
     * Device back button event listener function.
     */
    protected var onDeviceBackButtonCallback: ((BackButtonEvent) -> Unit)? = null

    init {
        this.hide()
        @Suppress("LeakingThis")
        addModal(this)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-popover", childrenVNodes())
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        if (onDeviceBackButtonCallback != null) {
            getElement()?.asDynamic()?.onDeviceBackButton = onDeviceBackButtonCallback
        }
        this.getElementJQuery()?.on("preshow") { e, _ ->
            this.dispatchEvent("onsPreshow", obj { detail = e })
        }
        this.getElementJQuery()?.on("postshow") { e, _ ->
            this.dispatchEvent("onsPostshow", obj { detail = e })
        }
        this.getElementJQuery()?.on("prehide") { e, _ ->
            this.dispatchEvent("onsPrehide", obj { detail = e })
        }
        this.getElementJQuery()?.on("posthide") { e, _ ->
            this.hide()
            this.dispatchEvent("onsPosthide", obj { detail = e })
        }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add(direction)
        if (cancelable == true) {
            attributeSetBuilder.add("cancelable")
        }
        if (animation == false) {
            attributeSetBuilder.add("animation", "none")
        }
        maskColor?.let {
            attributeSetBuilder.add("mask-color", it)
        }
        if (disabled == true) {
            attributeSetBuilder.add("disabled")
        }
        if (coverTarget == true) {
            attributeSetBuilder.add("cover-target")
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    /**
     * Shows the popover.
     * @param target a target component
     * @param options a parameter object
     */
    open fun showOnsPopover(target: Widget, options: dynamic = undefined): Promise<Unit>? {
        this.show()
        @Suppress("UnsafeCastFromDynamic")
        return getElement()?.asDynamic()?.show(target.getElement(), options)
    }

    /**
     * Hides the popover.
     * @param options a parameter object
     */
    open fun hideOnsPopover(options: dynamic = undefined): Promise<Unit>? {
        @Suppress("UnsafeCastFromDynamic")
        return getElement()?.asDynamic()?.hide(options)
    }

    /**
     * Sets device back button event listener.
     * @param callback an event listener
     */
    open fun onDeviceBackButton(callback: (event: BackButtonEvent) -> Unit) {
        onDeviceBackButtonCallback = callback
        getElement()?.asDynamic()?.onDeviceBackButton = callback
    }

    /**
     * Clears device back button event listener.
     */
    open fun onDeviceBackButtonClear() {
        onDeviceBackButtonCallback = null
        getElement()?.asDynamic()?.onDeviceBackButton = undefined
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
        Root.removeModal(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
@Suppress("unused")
fun Container.popover(
    direction: FloatDirection? = null,
    cancelable: Boolean? = null,
    animation: Boolean? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Popover.() -> Unit)? = null
): Popover {
    return Popover(direction, cancelable, animation, classes ?: className.set, init)
}
