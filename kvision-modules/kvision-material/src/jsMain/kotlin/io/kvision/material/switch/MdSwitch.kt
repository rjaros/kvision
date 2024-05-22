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
package io.kvision.material.switch

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.form.MdFormToggleInputWidget
import io.kvision.material.util.addBool
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import org.w3c.dom.events.Event

/**
 * Switches toggle the state of an item on or off.
 *
 * See https://material-web.dev/components/switch/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdSwitch(
    selected: Boolean = false,
    disabled: Boolean = false,
    icons: Boolean = false,
    showOnlySelectedIcon: Boolean = false,
    required: Boolean = false,
    value: String = "on",
    name: String? = null,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdSwitch.() -> Unit)? = null
) : MdFormToggleInputWidget(
    tag = "md-switch",
    disabled = disabled,
    required = required,
    value = value,
    name = name,
    validationMessage = validationMessage,
    className = className
) {

    /**
     * Puts the switch in the selected state and sets the form submission value to the value
     * property.
     */
    var selected by syncOnUpdate(selected)

    /**
     * Shows both the selected and deselected icons.
     */
    var icons by refreshOnUpdate(icons)

    /**
     * Shows only the selected icon, and not the deselected icon.
     * If true, overrides the behavior of the icons property.
     */
    var showOnlySelectedIcon by refreshOnUpdate(showOnlySelectedIcon)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (icons) {
            attributeSetBuilder.addBool("icons")
        }

        if (selected) {
            attributeSetBuilder.addBool("selected")
        }

        if (showOnlySelectedIcon) {
            attributeSetBuilder.addBool("show-only-selected-icon")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // State
    ///////////////////////////////////////////////////////////////////////////

    override fun toggle() {
        selected = !selected
    }

    override fun onChange(event: Event) {
        super.onChange(event)
        selected = getElementD().selected == true
    }
}

@ExperimentalMaterialApi
fun Container.switch(
    selected: Boolean = false,
    disabled: Boolean = false,
    icons: Boolean = false,
    showOnlySelectedIcon: Boolean = false,
    required: Boolean = false,
    value: String = "on",
    name: String? = null,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdSwitch.() -> Unit)? = null
) = MdSwitch(
    selected = selected,
    disabled = disabled,
    icons = icons,
    showOnlySelectedIcon = showOnlySelectedIcon,
    required = required,
    value = value,
    name = name,
    validationMessage = validationMessage,
    className = className,
    init = init
).also(this::add)
