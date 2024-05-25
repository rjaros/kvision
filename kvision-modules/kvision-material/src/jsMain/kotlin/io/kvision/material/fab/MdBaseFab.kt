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
package io.kvision.material.fab

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.widget.MdWidget
import io.kvision.material.widget.TouchTarget
import io.kvision.material.util.addBool
import io.kvision.material.slot.HasIconSlot
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component

enum class FabSize(internal val value: String) {
    Small("small"),
    Medium("medium"),
    Large("large")
}

/**
 * FAB represents the most important action on a screen. It puts key actions within reach.
 *
 * Extended FABs help people take primary actions.
 * They're wider than FABs to accommodate a text label and larger target area.
 *
 * See https://material-web.dev/components/fab
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
abstract class MdBaseFab internal constructor(
    tag: String,
    label: String?,
    size: FabSize,
    lowered: Boolean,
    touchTarget: TouchTarget?,
    className: String?
) : MdWidget(
    tag = tag,
    className = className
), HasIconSlot {

    /**
     * The text to display on the FAB.
     */
    var label by refreshOnUpdate(label)

    /**
     * The size of the FAB.
     * NOTE: Branded FABs cannot be sized to small, and Extended FABs do not have different sizes.
     */
    var size by refreshOnUpdate(size)

    /**
     * Lowers the FAB's elevation.
     */
    var lowered by refreshOnUpdate(lowered)

    /**
     * Touch target of (small size) FAB.
     */
    var touchTarget by refreshOnUpdate(touchTarget)

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("size", size.value)

        label?.let {
            attributeSetBuilder.add("label", translate(it))
        }

        if (lowered) {
            attributeSetBuilder.addBool("lowered")
        }

        touchTarget?.let {
            attributeSetBuilder.add("touch-target", it.value)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Slots
    ///////////////////////////////////////////////////////////////////////////

    override fun icon(component: Component?) {
        Slot.Icon(component)
    }
}
