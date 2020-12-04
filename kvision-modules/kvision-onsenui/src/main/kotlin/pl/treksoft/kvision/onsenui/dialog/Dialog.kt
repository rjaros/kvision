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

package pl.treksoft.kvision.onsenui.dialog

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.AttributeSetBuilder
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Display
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.onsenui.BackButtonEvent
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.panel.Root.Companion.addModal
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set
import kotlin.js.Promise

/**
 * A dialog component.
 *
 * @constructor Creates a dialog component.
 * @param cancelable whether the dialog can be canceled
 * @param animation determines if the transitions are animated
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Dialog(
    cancelable: Boolean? = null,
    animation: Boolean? = null,
    classes: Set<String> = setOf(),
    init: (Dialog.() -> Unit)? = null
) : SimplePanel(classes) {

    override var parent: Container? = Root.getFirstRoot()

    /**
     * Whether the dialog can be canceled.
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
     * Whether the dialog is disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * A dynamic property returning whether the dialog is visible.
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
        return render("ons-dialog", childrenVNodes())
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

    override fun buildAttributesSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributesSet(attributeSetBuilder)
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
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    /**
     * Shows the dialog.
     * @param options a parameter object
     */
    open fun showDialog(options: dynamic = undefined): Promise<Unit>? {
        this.display = Display.NONE
        this.show()
        @Suppress("UnsafeCastFromDynamic")
        return getElement()?.asDynamic()?.show(options)
    }

    /**
     * Hides the dialog.
     * @param options a parameter object
     */
    open fun hideDialog(options: dynamic = undefined): Promise<Unit>? {
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
        Root.removeModal(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
@Suppress("unused")
fun Container.dialog(
    cancelable: Boolean? = null,
    animation: Boolean? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Dialog.() -> Unit)? = null
): Dialog {
    return Dialog(cancelable, animation, classes ?: className.set, init)
}
