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
package io.kvision.material.tabs

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.util.addBool
import io.kvision.core.AttributeSetBuilder

/**
 * Primary tabs are placed at the top of the content pane under a top app bar.
 * They display the main content destinations.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdPrimaryTab(
    text: String,
    inlineIcon: Boolean = false,
    active: Boolean = false,
    className: String? = null,
    init: (MdPrimaryTab.() -> Unit)? = null
) : MdTab(
    tag = "md-primary-tab",
    text = text,
    active = active,
    className = className
) {

    /**
     * Whether or not the icon renders inline with label or stacked vertically.
     */
    var inlineIcon by refreshOnUpdate(inlineIcon)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (inlineIcon) {
            attributeSetBuilder.addBool("inline-icon")
        }
    }
}

@ExperimentalMaterialApi
fun MdTabs.primaryTab(
    text: String,
    inlineIcon: Boolean = false,
    active: Boolean = false,
    className: String? = null,
    init: (MdPrimaryTab.() -> Unit)? = null
) = MdPrimaryTab(
    text = text,
    inlineIcon = inlineIcon,
    active = active,
    className = className,
    init = init
).also(this::add)
