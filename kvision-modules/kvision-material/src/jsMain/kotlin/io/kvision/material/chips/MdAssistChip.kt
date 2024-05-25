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
import io.kvision.material.widget.LinkTarget
import io.kvision.core.AttributeSetBuilder

/**
 * Assist chips represent smart or automated actions that can span multiple apps,
 * such as opening a calendar event from the home screen.
 *
 * Assist chips function as though the user asked an assistant to complete the action.
 * They should appear dynamically and contextually in a UI.
 *
 * https://material-web.dev/components/chip/#assist-chip-example
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdAssistChip internal constructor(
    tag: String,
    label: String,
    elevated: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    disabled: Boolean = false,
    alwaysFocusable: Boolean = false,
    className: String? = null
) : MdChip(
    tag = tag,
    label = label,
    disabled = disabled,
    alwaysFocusable = alwaysFocusable,
    className = className
) {

    /**
     * Assist chip constructor.
     */
    constructor(
        label: String,
        elevated: Boolean = false,
        href: String? = null,
        target: LinkTarget? = null,
        disabled: Boolean = false,
        alwaysFocusable: Boolean = false,
        className: String? = null,
        init: (MdAssistChip.() -> Unit)? = null
    ) : this(
        tag = "md-assist-chip",
        label = label,
        elevated = elevated,
        href = href,
        target = target,
        disabled = disabled,
        alwaysFocusable = alwaysFocusable,
        className = className
    ) {
        init?.let { this.it() }
    }

    /**
     * Determines if the chip is elevated.
     */
    var elevated by refreshOnUpdate(elevated)

    /**
     * The URL that the chip points to.
     */
    var href by refreshOnUpdate(href)

    /**
     * Where to display the linked href URL for a link chip.
     */
    var target by refreshOnUpdate(target)

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (elevated) {
            attributeSetBuilder.addBool("elevated")
        }

        href?.let {
            attributeSetBuilder.add("href", it)
        }

        target?.let {
            attributeSetBuilder.add("target", it.value)
        }
    }
}

@ExperimentalMaterialApi
fun MdChipSet.assistChip(
    label: String,
    elevated: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    disabled: Boolean = false,
    alwaysFocusable: Boolean = false,
    className: String? = null,
    init: (MdAssistChip.() -> Unit)? = null
) = MdAssistChip(
    label = label,
    elevated = elevated,
    href = href,
    target = target,
    disabled = disabled,
    alwaysFocusable = alwaysFocusable,
    className = className,
    init = init
).also(this::add)
