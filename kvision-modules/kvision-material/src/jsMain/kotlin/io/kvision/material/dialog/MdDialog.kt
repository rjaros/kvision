/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2024 Maanrifa Bacar Ali <dev.manrif@gmail.com>
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
package io.kvision.material.dialog

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.util.requireElementD
import io.kvision.material.slot.HasActionsSlot
import io.kvision.material.slot.HasContentSlot
import io.kvision.material.slot.HasHeadlineSlot
import io.kvision.material.slot.HasIconSlot
import io.kvision.material.widget.MdWidget
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.snabbdom.VNode
import kotlin.js.Promise

enum class DialogType(internal val value: String) {
    Alert("alert")
}

/**
 * Dialogs provide important prompts in a user flow.
 *
 * See https://material-web.dev/components/dialog/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdDialog(
    returnValue: String? = null,
    type: DialogType? = null,
    className: String? = null,
    init: (MdDialog.() -> Unit)? = null
) : MdWidget(
    tag = "md-dialog",
    className = className
), HasActionsSlot,
    HasContentSlot,
    HasHeadlineSlot,
    HasIconSlot {

    /**
     * The type of dialog for accessibility. Set this to alert to announce a dialog as an alert
     * dialog.
     */
    var type by refreshOnUpdate(type)

    /**
     * Gets or sets the dialog's return value, usually to indicate which button a user pressed to
     * close it.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLDialogElement/returnValue
     */
    var returnValue by refreshOnUpdate(returnValue)

    /**
     * Gets the opening animation for a dialog. Set to a new function to customize the animation.
     */
    var openAnimation by syncOnUpdate<(() -> dynamic)?>(null)

    /**
     * Gets the closing animation for a dialog. Set to a new function to customize the animation.
     */
    var closeAnimation by syncOnUpdate<(() -> dynamic)?>(null)

    /**
     * Indicates that the dialog is in an open state.
     */
    val open: Boolean
        get() = getElementD()?.open?.unsafeCast<Boolean?>() ?: false

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        getElementD().returnValue = returnValue
        openAnimation?.let { getElementD().getOpenAnimation = it }
        closeAnimation?.let { getElementD().getCloseAnimation = it }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        type?.let {
            attributeSetBuilder.add("type", it.value)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Slots
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Sets the headline of the dialog.
     */
    override fun actions(component: Component?) {
        Slot.Actions(component)
    }

    /**
     * Sets the content of the dialog.
     */
    override fun content(component: Component?) {
        Slot.Content(component)
    }

    /**
     * Sets the actions of the dialog.
     */
    override fun headline(component: Component?) {
        Slot.Headline(component)
    }

    /**
     * Sets the icon of the dialog.
     */
    override fun icon(component: Component?) {
        Slot.Icon(component)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Display
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Opens the dialog and fires a cancelable open event.
     * After a dialog's animation, an opened event is fired.
     * Add an autocomplete attribute to a child of the dialog that should receive focus after
     * opening.
     */
    @JsName("showDialog")
    fun open(): Promise<Unit> {
        if (!visible) {
            return Promise.reject(IllegalStateException("Dialog is not visible"))
        }

        return requireElementD().show() as Promise<Unit>
    }

    /**
     * Closes the dialog and fires a cancelable close event.
     * After a dialog's animation, a closed event is fired.
     */
    @JsName("closeDialog")
    fun close(returnValue: String? = this.returnValue): Promise<Unit> {
        if (!visible) {
            return Promise.reject(IllegalStateException("Dialog is not visible"))
        }

        return requireElementD().close(returnValue) as Promise<Unit>
    }
}

@ExperimentalMaterialApi
fun Container.dialog(
    returnValue: String? = null,
    type: DialogType? = null,
    className: String? = null,
    init: (MdDialog.() -> Unit)? = null
) = MdDialog(
    returnValue = returnValue,
    type = type,
    className = className,
    init = init
).also(this::add)
