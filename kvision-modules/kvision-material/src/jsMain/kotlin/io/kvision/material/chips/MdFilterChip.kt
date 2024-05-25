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
import io.kvision.material.util.addBool
import io.kvision.core.AttributeSetBuilder
import io.kvision.snabbdom.VNode

/**
 * Filter chips use tags or descriptive words to filter content.
 * They can be a good alternative to toggle buttons or checkboxes.
 *
 * https://material-web.dev/components/chip/#filter-chip-example
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdFilterChip(
    label: String,
    elevated: Boolean = false,
    removable: Boolean = false,
    selected: Boolean = false,
    disabled: Boolean = false,
    alwaysFocusable: Boolean = false,
    ariaLabelRemove: String? = null,
    className: String? = null,
    init: (MdFilterChip.() -> Unit)? = null
) : MdChip(
    tag = "md-filter-chip",
    label = label,
    disabled = disabled,
    alwaysFocusable = alwaysFocusable,
    className = className,
    canBeRemoved = true
), RemovableChip {

    /**
     * Determines if the chip is elevated.
     */
    var elevated by refreshOnUpdate(elevated)

    /**
     * Indicates that the ship is removable.
     */
    var removable by refreshOnUpdate(removable)

    /**
     * Indicates that the chip is selected.
     */
    var selected by refreshOnUpdate(selected)

    /**
     * The aria label for remove action.
     */
    var ariaLabelRemove by syncOnUpdate(ariaLabelRemove)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        ariaLabelRemove?.let { getElementD().ariaLabelRemove = translate(it) }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (elevated) {
            attributeSetBuilder.addBool("elevated")
        }

        if (removable) {
            attributeSetBuilder.addBool("removable")
        }

        if (selected) {
            attributeSetBuilder.addBool("selected")
        }
    }
}

@ExperimentalMaterialApi
fun MdChipSet.filterChip(
    label: String,
    elevated: Boolean = false,
    removable: Boolean = false,
    selected: Boolean = false,
    disabled: Boolean = false,
    alwaysFocusable: Boolean = false,
    ariaLabelRemove: String? = null,
    className: String? = null,
    init: (MdFilterChip.() -> Unit)? = null
) = MdFilterChip(
    label = label,
    elevated = elevated,
    removable = removable,
    selected = selected,
    disabled = disabled,
    alwaysFocusable = alwaysFocusable,
    ariaLabelRemove = ariaLabelRemove,
    className = className,
    init = init
).also(this::add)
