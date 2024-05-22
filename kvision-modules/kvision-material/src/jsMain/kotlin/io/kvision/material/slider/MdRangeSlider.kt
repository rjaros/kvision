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
import io.kvision.material.util.add
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.snabbdom.VNode
import org.w3c.dom.events.Event

/**
 * Range Sliders expand upon [MdRangeSlider] using the same concepts but allow the user to select 2
 * values.
 *
 * The two values are still bounded by the value range but they also cannot cross each other.
 *
 * See https://material-web.dev/components/slider/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdRangeSlider(
    disabled: Boolean = false,
    min: Number = 0,
    max: Number = 100,
    valueStart: Number? = null,
    valueEnd: Number? = null,
    step: Number = 1,
    ticks: Boolean = false,
    labeled: Boolean = false,
    valueLabelStart: String? = null,
    valueLabelEnd: String? = null,
    ariaLabelStart: String? = null,
    ariaValueTextStart: String? = null,
    ariaLabelEnd: String? = null,
    ariaValueTextEnd: String? = null,
    name: String? = null,
    nameStart: String? = null,
    nameEnd: String? = null,
    className: String? = null,
    init: (MdRangeSlider.() -> Unit)? = null
) : MdBaseSlider<Nothing?>(
    min = min,
    max = max,
    step = step,
    disabled = disabled,
    labeled = labeled,
    ticks = ticks,
    name = name,
    value = null,
    className = className
) {

    override val range: Boolean
        get() = true

    /**
     * The range slider start value.
     */
    var valueStart by syncOnUpdate(valueStart ?: rangeSliderInitialStartValue(min, max))

    /**
     * The rangeSlider end value.
     */
    var valueEnd by syncOnUpdate(valueEnd ?: rangeSliderInitialEndValue(min, max))

    /**
     * An optional label for the range slider's start value.
     * If not set, the label is the [valueStart] itself.
     */
    var valueLabelStart by refreshOnUpdate(valueLabelStart)

    /**
     * An optional label for the range slider's end value.
     * If not set, the label is the [valueEnd] itself.
     */
    var valueLabelEnd by refreshOnUpdate(valueLabelEnd)

    /**
     * Aria label for the range slider's start handle.
     */
    var ariaLabelStart by refreshOnUpdate(ariaLabelStart)

    /**
     * Aria value text for the range slider's start value.
     */
    var ariaValueTextStart by refreshOnUpdate(ariaValueTextStart)

    /**
     * Aria label for the range slider's end handle.
     */
    var ariaLabelEnd by refreshOnUpdate(ariaLabelEnd)

    /**
     * Aria value text for the range slider's end value.
     */
    var ariaValueTextEnd by refreshOnUpdate(ariaValueTextEnd)

    /**
     * The HTML name to use in form submission for a range slider's starting value.
     * Use [name] instead if both the start and end values should use the same name.
     */
    var nameStart by syncOnUpdate(nameStart)

    /**
     * The HTML name to use in form submission for a range slider's ending value.
     * Use [name] instead if both the start and end values should use the same name.
     */
    var nameEnd by syncOnUpdate(nameEnd)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        nameStart?.let { getElementD().nameStart = it }
        nameEnd?.let { getElementD().nameEnd = it }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("value-start", valueStart)
        attributeSetBuilder.add("value-end", valueEnd)

        valueLabelStart?.let {
            attributeSetBuilder.add("value-label-start", it)
        }

        valueLabelEnd?.let {
            attributeSetBuilder.add("value-label-end", it)
        }

        ariaLabelStart?.let {
            attributeSetBuilder.add("aria-label-start", it)
        }

        ariaValueTextStart?.let {
            attributeSetBuilder.add("aria-valuetext-start", it)
        }

        ariaLabelEnd?.let {
            attributeSetBuilder.add("aria-label-end", it)
        }

        ariaValueTextEnd?.let {
            attributeSetBuilder.add("aria-valuetext-end", it)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    override fun onChange(event: Event) {
        valueStart = getElementD().valueStart.unsafeCast<Number>()
        valueEnd = getElementD().valueEnd.unsafeCast<Number>()
    }
}

@ExperimentalMaterialApi
fun Container.rangeSlider(
    disabled: Boolean = false,
    min: Number = 0,
    max: Number = 100,
    valueStart: Number? = null,
    valueEnd: Number? = null,
    step: Number = 1,
    ticks: Boolean = false,
    labeled: Boolean = false,
    valueLabelStart: String? = null,
    valueLabelEnd: String? = null,
    ariaLabelStart: String? = null,
    ariaValueTextStart: String? = null,
    ariaLabelEnd: String? = null,
    ariaValueTextEnd: String? = null,
    name: String? = null,
    nameStart: String? = null,
    nameEnd: String? = null,
    className: String? = null,
    init: (MdRangeSlider.() -> Unit)? = null
) = MdRangeSlider(
    disabled = disabled,
    min = min,
    max = max,
    valueStart = valueStart,
    valueEnd = valueEnd,
    valueLabelStart = valueLabelStart,
    valueLabelEnd = valueLabelEnd,
    ariaLabelStart = ariaLabelStart,
    ariaValueTextStart = ariaValueTextStart,
    ariaLabelEnd = ariaLabelEnd,
    ariaValueTextEnd = ariaValueTextEnd,
    step = step,
    ticks = ticks,
    labeled = labeled,
    name = name,
    nameStart = nameStart,
    nameEnd = nameEnd,
    className = className,
    init = init
).also(this::add)

/**
 * Computes the initial start value of a range slider.
 */
@Suppress("UNUSED_PARAMETER")
private fun rangeSliderInitialStartValue(min: Number, max: Number): Number {
    return js("(min + max) / 3").unsafeCast<Number>()
}

/**
 * Computes the initial end value of a range slider.
 */
@Suppress("UNUSED_PARAMETER")
private fun rangeSliderInitialEndValue(min: Number, max: Number): Number {
    return js("((min + max) / 3) * 2").unsafeCast<Number>()
}
