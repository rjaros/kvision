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
package io.kvision.material.select

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.slot.HasHeadlineSlot
import io.kvision.material.slot.HasLeadingSlot
import io.kvision.material.slot.HasSupportingTextSlot
import io.kvision.material.slot.HasTrailingSlot
import io.kvision.material.util.addBool
import io.kvision.material.widget.MdItemWidget
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.snabbdom.VNode

/**
 * Select options are element that belongs to a select.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdSelectOption(
    value: String? = null,
    disabled: Boolean = false,
    selected: Boolean = false,
    typeaheadText: String? = null,
    className: String? = null,
    init: (MdSelectOption.() -> Unit)? = null
) : MdItemWidget(
    tag = "md-select-option",
    className = className
), HasHeadlineSlot,
    HasLeadingSlot,
    HasSupportingTextSlot,
    HasTrailingSlot {

    /**
     * Disables the item and makes it non-selectable and non-interactive.
     */
    var disabled by refreshOnUpdate(disabled)

    /**
     * Sets the item in the selected visual state when a submenu is opened.
     */
    var selected by refreshOnUpdate(selected)

    /**
     * Form value of the option.
     */
    var value by refreshOnUpdate(value)

    /**
     * Option type.
     */
    val type: String?
        get() = getElementD()?.type?.unsafeCast<String?>()

    /**
     * Typeahead text of the item.
     */
    var typeaheadText by syncOnUpdate(typeaheadText)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        typeaheadText?.let { getElementD().typeaheadText = translate(it) }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (disabled) {
            attributeSetBuilder.addBool("disabled")
        }

        if (selected) {
            attributeSetBuilder.addBool("selected")
        }

        value?.let {
            attributeSetBuilder.add("value", it)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Slots
    ///////////////////////////////////////////////////////////////////////////

    override fun headline(component: Component?) {
        Slot.Headline(component)
    }

    override fun leading(component: Component?) {
        Slot.Leading(component)
    }

    override fun supportingText(component: Component?) {
        Slot.SupportingText(component)
    }

    override fun trailing(component: Component?) {
        Slot.Trailing(component)
    }
}

@ExperimentalMaterialApi
fun MdSelect.selectOption(
    value: String? = null,
    disabled: Boolean = false,
    selected: Boolean = false,
    typeaheadText: String? = null,
    className: String? = null,
    init: (MdSelectOption.() -> Unit)? = null
) = MdSelectOption(
    value = value,
    disabled = disabled,
    selected = selected,
    typeaheadText = typeaheadText,
    className = className,
    init = init
).also(this::add)

