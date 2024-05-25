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
import io.kvision.material.widget.TouchTarget
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container

enum class FabVariant(internal val value: String) {
    Surface("surface"),
    Primary("primary"),
    Secondary("secondary"),
    Tertiary("tertiary")
}

/**
 * FABs should display a clear and understandable icon.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdFab(
    label: String? = null,
    variant: FabVariant = FabVariant.Surface,
    size: FabSize = FabSize.Medium,
    lowered: Boolean = false,
    touchTarget: TouchTarget? = null,
    className: String? = null,
    init: (MdFab.() -> Unit)? = null
) : MdBaseFab(
    tag = "md-fab",
    label = label,
    size = size,
    lowered = lowered,
    touchTarget = touchTarget,
    className = className
) {

    /**
     * The FAB color variant to render.
     */
    var variant by refreshOnUpdate(variant)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("variant", variant.value)
    }
}

@ExperimentalMaterialApi
fun Container.fab(
    label: String? = null,
    variant: FabVariant = FabVariant.Surface,
    size: FabSize = FabSize.Medium,
    lowered: Boolean = false,
    touchTarget: TouchTarget? = null,
    className: String? = null,
    init: (MdFab.() -> Unit)? = null
) = MdFab(
    label = label,
    variant = variant,
    size = size,
    lowered = lowered,
    touchTarget = touchTarget,
    className = className,
    init = init
).also(this::add)
