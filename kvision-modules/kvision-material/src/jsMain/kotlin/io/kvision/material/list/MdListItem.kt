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
package io.kvision.material.list

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.slot.HasHeadlineSlot
import io.kvision.material.slot.HasLeadingSlot
import io.kvision.material.slot.HasSupportingTextSlot
import io.kvision.material.slot.HasTrailingSlot
import io.kvision.material.slot.HasTrailingSupportingTextSlot
import io.kvision.material.util.addBool
import io.kvision.material.widget.LinkTarget
import io.kvision.material.widget.MdItemWidget
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.snabbdom.VNode

enum class ListItemType(internal val value: String) {
    Text("text"),
    Button("button"),
    Link("link")
}

/**
 * List items are element that belongs to a List.
 *
 * See https://material-web.dev/components/list/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdListItem(
    text: String? = null,
    type: ListItemType = ListItemType.Text,
    disabled: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    className: String? = null,
    init: (MdListItem.() -> Unit)? = null
) : MdItemWidget(
    tag = "md-list-item",
    className = className
), HasHeadlineSlot,
    HasLeadingSlot,
    HasSupportingTextSlot,
    HasTrailingSlot,
    HasTrailingSupportingTextSlot {

    /**
     * Text of this item.
     */
    var text by refreshOnUpdate(text)

    /**
     * Sets the behavior of the list item.
     */
    var type: ListItemType by refreshOnUpdate(type)

    /**
     * Disables the item and makes it non-selectable and non-interactive.
     */
    var disabled by refreshOnUpdate(disabled)

    /**
     * The URL that the item points to.
     */
    var href by refreshOnUpdate(href)

    /**
     * Where to display the linked href URL.
     */
    var target by refreshOnUpdate(target)

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

        if (disabled) {
            attributeSetBuilder.addBool("disabled")
        }

        href?.let {
            attributeSetBuilder.add("href", it)
        }

        target?.let {
            attributeSetBuilder.add("target", it.value)
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

    override fun trailingSupportingText(component: Component?) {
        Slot.TrailingSupportingText(component)
    }
}

@ExperimentalMaterialApi
fun Container.listItem(
    text: String? = null,
    type: ListItemType = ListItemType.Text,
    disabled: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    className: String? = null,
    init: (MdListItem.() -> Unit)? = null
) = MdListItem(
    text = text,
    type = type,
    disabled = disabled,
    href = href,
    target = target,
    className = className,
    init = init
).also(this::add)
