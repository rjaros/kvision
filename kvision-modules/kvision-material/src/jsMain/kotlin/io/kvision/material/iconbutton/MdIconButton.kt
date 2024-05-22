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
package io.kvision.material.iconbutton

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.form.MdFormLabelWidget
import io.kvision.material.util.addBool
import io.kvision.material.slot.HasIconSlot
import io.kvision.material.slot.HasSelectedSlot
import io.kvision.material.widget.LinkTarget
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import org.w3c.dom.events.Event

enum class IconButtonType(internal val value: String) {
    Button("button"),
    Submit("submit"),
    Reset("reset")
}

/**
 * Icon buttons help people take supplementary actions with a single tap.
 *
 * Standard icon buttons do not have a background or outline, and have the lowest emphasis of the
 * icon buttons.
 *
 * See https://material-web.dev/components/icon-button/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdIconButton internal constructor(
    tag: String,
    disabled: Boolean,
    flipIconInRtl: Boolean,
    href: String?,
    target: LinkTarget?,
    ariaLabelSelected: String?,
    toggle: Boolean,
    selected: Boolean,
    type: IconButtonType,
    value: String?,
    name: String?,
    className: String?,
) : MdFormLabelWidget<String?>(
    tag = tag,
    disabled = disabled,
    value = value,
    name = name,
    className = className
), HasIconSlot,
    HasSelectedSlot {

    /**
     * Standard icon button constructor.
     */
    constructor(
        disabled: Boolean = false,
        flipIconInRtl: Boolean = false,
        href: String? = null,
        target: LinkTarget? = null,
        ariaLabelSelected: String? = null,
        toggle: Boolean = false,
        selected: Boolean = false,
        type: IconButtonType = IconButtonType.Submit,
        value: String? = null,
        name: String? = null,
        className: String? = null,
        init: (MdIconButton.() -> Unit)? = null
    ) : this(
        tag = "md-icon-button",
        disabled = disabled,
        flipIconInRtl = flipIconInRtl,
        href = href,
        target = target,
        ariaLabelSelected = ariaLabelSelected,
        toggle = toggle,
        selected = selected,
        type = type,
        value = value,
        name = name,
        className = className
    ) {
        init?.let { this.it() }
    }

    /**
     * Flips the icon if it is in an RTL context at startup.
     */
    var flipIconInRtl: Boolean by refreshOnUpdate(flipIconInRtl)

    /**
     * The URL that the link iconButton points to.
     */
    var href by refreshOnUpdate(href)

    /**
     * Where to display the linked href URL for a link iconButton.
     * Common options include _blank to open in a new tab.
     */
    var target by refreshOnUpdate(target)

    /**
     * The aria-label of the button when the button is toggleable and selected.
     */
    var ariaLabelSelected by refreshOnUpdate(ariaLabelSelected)

    /**
     * When true, the button will toggle between selected and unselected states.
     */
    var toggle by refreshOnUpdate(toggle)

    /**
     * Sets the selected state.
     * When false, displays the default icon.
     * When true, displays the selected icon, or the default icon if no slot="selected" icon is
     * provided.
     */
    var selected by syncOnUpdate(selected)

    /**
     * Button type.
     */
    var type by refreshOnUpdate(type)

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", type.value)

        if (flipIconInRtl) {
            attributeSetBuilder.addBool("flip-icon-in-rtl")
        }

        href?.let {
            attributeSetBuilder.add("href", it)
        }

        target?.let {
            attributeSetBuilder.add("target", it.value)
        }

        if (toggle) {
            attributeSetBuilder.addBool("toggle")
        }

        if (selected) {
            attributeSetBuilder.addBool("selected")
        }

        ariaLabelSelected?.let {
            attributeSetBuilder.add("aria-label-selected", translate(it))
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Slots
    ///////////////////////////////////////////////////////////////////////////

    override fun icon(component: Component?) {
        Slot.None(component)
    }

    override fun selected(component: Component?) {
        Slot.Selected(component)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    override fun onChange(event: Event) {
        super.onChange(event)
        selected = getElementD()?.selected == true
    }
}

@ExperimentalMaterialApi
fun Container.iconButton(
    disabled: Boolean = false,
    flipIconInRtl: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    ariaLabelSelected: String? = null,
    toggle: Boolean = false,
    selected: Boolean = false,
    type: IconButtonType = IconButtonType.Submit,
    value: String? = null,
    name: String? = null,
    className: String? = null,
    init: (MdIconButton.() -> Unit)? = null
) = MdIconButton(
    disabled = disabled,
    flipIconInRtl = flipIconInRtl,
    href = href,
    target = target,
    ariaLabelSelected = ariaLabelSelected,
    toggle = toggle,
    selected = selected,
    type = type,
    value = value,
    name = name,
    className = className,
    init = init
).also(this::add)
