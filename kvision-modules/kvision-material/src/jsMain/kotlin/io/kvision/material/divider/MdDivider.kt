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
package io.kvision.material.divider

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.util.addBool
import io.kvision.material.widget.MdWidget
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container

/**
 * Dividers are thin lines that group content in lists or other containers.
 *
 * // TODO add link to online doc when available
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdDivider(
    inset: Boolean = false,
    insetStart: Boolean = false,
    insetEnd: Boolean = false,
    className: String? = null,
    init: (MdDivider.() -> Unit)? = null
) : MdWidget(
    tag = "md-divider",
    className = className
) {

    /**
     * Indents the divider with equal padding on both sides.
     */
    var inset by refreshOnUpdate(inset)

    /**
     * Indents the divider with padding on the leading side.
     */
    var insetStart by refreshOnUpdate(insetStart)

    /**
     * Indents the divider with padding on the trailing side.
     */
    var insetEnd by refreshOnUpdate(insetEnd)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (inset) {
            attributeSetBuilder.addBool("inset")
        } else {
            if (insetStart) {
                attributeSetBuilder.addBool("inset-start")
            }

            if (insetEnd) {
                attributeSetBuilder.addBool("inset-end")
            }
        }
    }
}

@ExperimentalMaterialApi
fun Container.divider(
    inset: Boolean = false,
    insetStart: Boolean = false,
    insetEnd: Boolean = false,
    className: String? = null,
    init: (MdDivider.() -> Unit)? = null
) = MdDivider(
    inset = inset,
    insetStart = insetStart,
    insetEnd = insetEnd,
    className = className,
    init = init
).also(this::add)
