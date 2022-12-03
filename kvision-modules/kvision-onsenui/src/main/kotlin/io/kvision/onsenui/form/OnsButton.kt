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

package io.kvision.onsenui.form

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.html.Align
import io.kvision.html.CustomTag
import org.w3c.dom.events.MouseEvent

/**
 * Onsen UI button types.
 */
enum class OnsButtonType(internal val type: String) {
    OUTLINE("outline"),
    LIGHT("light"),
    CTA("cta"),
    QUIET("quiet"),
    FLAT("flat")
}

/**
 * A button component.
 *
 * @constructor Creates a button component.
 * @param content the content of the button.
 * @param rich whether [content] can contain HTML code
 * @param align text align
 * @param icon an icon placed on the button
 * @param buttonType a type of the button
 * @param large whether the button is large
 * @param ripple specify if the button will have a ripple effect
 * @param disabled specify if the button should be disabled
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class OnsButton(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    icon: String? = null,
    buttonType: OnsButtonType? = null,
    large: Boolean? = null,
    ripple: Boolean? = null,
    disabled: Boolean? = null,
    className: String? = null,
    init: (OnsButton.() -> Unit)? = null
) : CustomTag("ons-button", content, rich, align, className) {

    /**
     *  The icon placed on the toolbar button.
     */
    var icon: String? by refreshOnUpdate(icon)

    /**
     * A type of the button.
     */
    var buttonType: OnsButtonType? by refreshOnUpdate(buttonType)

    /**
     * Whether the button is large.
     */
    var large: Boolean? by refreshOnUpdate(large)

    /**
     *  Specify if the button will have a ripple effect.
     */
    var ripple: Boolean? by refreshOnUpdate(ripple)

    /**
     *  Specify if the button should be disabled.
     */
    var disabled: Boolean? by refreshOnUpdate(disabled)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * A dynamic property returning current state of the component.
     */
    val isDisabled: Boolean?
        @Suppress("UnsafeCastFromDynamic")
        get() = getElement()?.asDynamic()?.disabled

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (content != null) {
            classSetBuilder.add("kv-button-with-text")
        }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        icon?.let {
            attributeSetBuilder.add("icon", it)
        }
        if (ripple == true) {
            attributeSetBuilder.add("ripple")
        }
        if (disabled == true) {
            attributeSetBuilder.add("disabled")
        }
        val modifiers = mutableListOf<String>()
        buttonType?.let {
            modifiers.add(it.type)
        }
        if (large == true) {
            modifiers.add("large")
        }
        modifier?.let {
            modifiers.add(it)
        }
        if (modifiers.isNotEmpty()) {
            attributeSetBuilder.add("modifier", modifiers.joinToString(" "))
        }
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: OnsButton.(MouseEvent) -> Unit) {
        this.setEventListener<OnsButton> {
            click = { e ->
                self.handler(e)
            }
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsButton(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    icon: String? = null,
    buttonType: OnsButtonType? = null,
    large: Boolean? = null,
    ripple: Boolean? = null,
    disabled: Boolean? = null,
    className: String? = null,
    init: (OnsButton.() -> Unit)? = null
): OnsButton {
    val onsButton =
        OnsButton(content, rich, align, icon, buttonType, large, ripple, disabled, className, init)
    this.add(onsButton)
    return onsButton
}
