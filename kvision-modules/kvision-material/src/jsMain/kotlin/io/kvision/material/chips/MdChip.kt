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
package io.kvision.material.chips

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.slot.HasIconSlot
import io.kvision.material.util.addBool
import io.kvision.material.widget.MdItemWidget
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component

/**
 * Chips help people enter information, make selections, filter content, or trigger actions.
 *
 * While buttons are expected to appear consistently and with familiar calls to action,
 * chips should appear dynamically as a group of multiple interactive elements.
 *
 * See https://material-web.dev/components/chip/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
abstract class MdChip internal constructor(
    tag: String,
    label: String,
    disabled: Boolean,
    alwaysFocusable: Boolean,
    className: String?,
    internal val canBeRemoved: Boolean = false
) : MdItemWidget(
    tag = tag,
    className = className
), HasIconSlot {

    /**
     * Whether or not the chip is disabled.
     * Disabled chips are not focusable, unless always-focusable is set.
     */
    var disabled by refreshOnUpdate(disabled)

    /**
     * The label of the chip.
     */
    var label by refreshOnUpdate(label)

    /**
     * When true, allow disabled chips to be focused with arrow keys.
     * Add this when a chip needs increased visibility when disabled.
     *
     * See https://www.w3.org/WAI/ARIA/apg/practices/keyboard-interface/#kbd_disabled_controls
     * for more guidance on when this is needed.
     */
    var alwaysFocusable by refreshOnUpdate(alwaysFocusable)

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("label", translate(label))

        if (disabled) {
            attributeSetBuilder.addBool("disabled")
        }

        if (alwaysFocusable) {
            attributeSetBuilder.addBool("always-focusable")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Slots
    ///////////////////////////////////////////////////////////////////////////

    override fun icon(component: Component?) {
        Slot.Icon(component)
    }
}
