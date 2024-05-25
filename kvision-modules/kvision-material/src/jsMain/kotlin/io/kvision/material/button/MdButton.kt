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
package io.kvision.material.button

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.form.MdFormWidget
import io.kvision.material.slot.HasIconSlot
import io.kvision.material.util.addBool
import io.kvision.material.widget.LinkTarget
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.snabbdom.VNode

enum class ButtonType(internal val value: String) {
    Button("button"),
    Submit("submit"),
    Reset("reset")
}

/**
 * Buttons help people initiate actions, from sending an email, to sharing a document,
 * to liking a post.
 *
 * See https://material-web.dev/components/button/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
abstract class MdButton internal constructor(
    tag: String,
    text: String? = null,
    disabled: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    trailingIcon: Boolean = false,
    type: ButtonType = ButtonType.Submit,
    value: String? = null,
    name: String? = null,
    className: String? = null,
    init: (MdButton.() -> Unit)? = null
) : MdFormWidget<String?>(
    tag = tag,
    disabled = disabled,
    value = value,
    name = name,
    className = className
), HasIconSlot {

    /**
     * Button text.
     */
    var text: String? by refreshOnUpdate(text)

    /**
     * The URL that the link button points to.
     */
    var href by refreshOnUpdate(href)

    /**
     * Where to display the linked href URL for a link button.
     * Common options include _blank to open in a new tab.
     */
    var target by refreshOnUpdate(target)

    /**
     * Whether to render the icon at the inline end of the label rather than the inline start.
     * Note: Link buttons cannot have trailing icons.
     */
    var trailingIcon by refreshOnUpdate(trailingIcon)

    /**
     * Button type.
     */
    var type by refreshOnUpdate(type)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Rendering
    ///////////////////////////////////////////////////////////////////////////

    override fun render(): VNode {
        return renderWithTranslatableText(text)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", type.value)

        href?.let {
            attributeSetBuilder.add("href", it)
        }

        target?.let {
            attributeSetBuilder.add("target", it.value)
        }

        if (trailingIcon) {
            attributeSetBuilder.addBool("trailing-icon")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Slots
    ///////////////////////////////////////////////////////////////////////////

    override fun icon(component: Component?) {
        Slot.Icon(component)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    override fun hasChangeEvent(): Boolean = false

    override fun hasInputEvent(): Boolean = false
}
