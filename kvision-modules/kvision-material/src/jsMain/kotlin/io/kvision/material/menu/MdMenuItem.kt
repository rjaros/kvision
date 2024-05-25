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
package io.kvision.material.menu

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.slot.HasHeadlineSlot
import io.kvision.material.slot.HasLeadingSlot
import io.kvision.material.slot.HasSupportingTextSlot
import io.kvision.material.slot.HasTrailingSlot
import io.kvision.material.util.addBool
import io.kvision.material.widget.LinkTarget
import io.kvision.material.widget.MdItemWidget
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.snabbdom.VNode

enum class MenuItemType(internal val value: String) {
    MenuItem("menuitem"),
    Option("option"),
    Button("button"),
    Link("link")
}

/**
 * Menu items are element that belongs to a menu.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdMenuItem(
    disabled: Boolean = false,
    type: MenuItemType = MenuItemType.MenuItem,
    href: String? = null,
    target: LinkTarget? = null,
    keepOpen: Boolean = false,
    selected: Boolean = false,
    typeaheadText: String? = null,
    className: String? = null,
    init: (@MaterialMenuDsl MdMenuItem.() -> Unit)? = null
) : MdItemWidget(
    tag = "md-menu-item",
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
     * Sets the behavior and role of the menu item.
     */
    var type by refreshOnUpdate(type)

    /**
     * The URL that the item points to.
     */
    var href by refreshOnUpdate(href)

    /**
     * Where to display the linked href URL for a link button.
     * Common options include _blank to open in a new tab.
     */
    var target by refreshOnUpdate(target)

    /**
     * Keeps the menu open if clicked or keyboard selected.
     */
    var keepOpen by refreshOnUpdate(keepOpen)

    /**
     * Sets the item in the selected visual state when a submenu is opened.
     */
    var selected by syncOnUpdate(selected)

    /**
     * The text that is selectable via typeahead. If not set, defaults to the innerText of the item
     * slotted into the `headline` slot.
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
        attributeSetBuilder.add("type", type.value)

        if (disabled) {
            attributeSetBuilder.addBool("disabled")
        }

        href?.let {
            attributeSetBuilder.add("href", it)
        }

        target?.let {
            attributeSetBuilder.add("target", it.value)
        }

        if (keepOpen) {
            attributeSetBuilder.addBool("keep-open")
        }

        if (selected) {
            attributeSetBuilder.addBool("selected")
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
fun MdMenu.menuItem(
    disabled: Boolean = false,
    type: MenuItemType = MenuItemType.MenuItem,
    href: String? = null,
    target: LinkTarget? = null,
    keepOpen: Boolean = false,
    selected: Boolean = false,
    typeaheadText: String? = null,
    className: String? = null,
    init: (@MaterialMenuDsl MdMenuItem.() -> Unit)? = null
) = MdMenuItem(
    disabled = disabled,
    type = type,
    href = href,
    target = target,
    keepOpen = keepOpen,
    selected = selected,
    typeaheadText = typeaheadText,
    className = className,
    init = init
).also(this::add)
