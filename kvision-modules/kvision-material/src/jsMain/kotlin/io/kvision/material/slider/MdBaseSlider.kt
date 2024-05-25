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
package io.kvision.material.slider

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.form.MdFormLabelWidget
import io.kvision.material.util.add
import io.kvision.material.util.addBool
import io.kvision.core.AttributeSetBuilder

/**
 * Base class for sliders.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
abstract class MdBaseSlider<V> internal constructor(
    min: Number,
    max: Number,
    step: Number,
    disabled: Boolean,
    labeled: Boolean,
    ticks: Boolean,
    name: String?,
    value: V,
    className: String?,
) : MdFormLabelWidget<V>(
    tag = "md-slider",
    disabled = disabled,
    value = value,
    name = name,
    className = className,
) {

    /**
     * Whether or not to show a value range.
     */
    abstract val range: Boolean

    /**
     * The slider minimum value
     */
    var min by refreshOnUpdate(min)

    /**
     * The slider maximum value
     */
    var max by refreshOnUpdate(max)

    /**
     * The step between values.
     */
    var step by refreshOnUpdate(step)

    /**
     * Whether or not to show tick marks.
     */
    var ticks by refreshOnUpdate(ticks)

    /**
     * Whether or not to show a value label when activated.
     */
    var labeled by refreshOnUpdate(labeled)

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        attributeSetBuilder.add("min", min)
        attributeSetBuilder.add("max", max)

        attributeSetBuilder.add("step", step)

        if (ticks) {
            attributeSetBuilder.addBool("ticks")
        }

        if (labeled) {
            attributeSetBuilder.addBool("labeled")
        }

        if (range) {
            attributeSetBuilder.addBool("range")
        }
    }
}
