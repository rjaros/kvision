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
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import org.w3c.dom.events.Event

/**
 * Sliders allow users to make selections from a range of values..
 * They're ideal for adjusting settings such as volume and brightness, or for applying image filters.
 *
 * Sliders can use icons or labels to represent a numeric or relative scale.
 *
 * See https://material-web.dev/components/slider/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdSlider(
    disabled: Boolean = false,
    min: Number = 0,
    max: Number = 100,
    value: Number? = null,
    step: Number = 1,
    valueLabel: String? = null,
    ticks: Boolean = false,
    labeled: Boolean = false,
    name: String? = null,
    className: String? = null,
    init: (MdSlider.() -> Unit)? = null
) : MdBaseSlider<Number>(
    min = min,
    max = max,
    step = step,
    disabled = disabled,
    labeled = labeled,
    ticks = ticks,
    name = name,
    value = value ?: sliderInitialValue(min, max),
    className = className
) {

    override val range: Boolean
        get() = false

    /**
     * An optional label for the slider's value.
     * If not set, the label is the [value] itself.
     */
    var valueLabel by refreshOnUpdate(valueLabel)


    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        valueLabel?.let {
            attributeSetBuilder.add("value-label", it)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    override fun onChange(event: Event) {
        value = getElementD().value.unsafeCast<Number>()
    }
}

@ExperimentalMaterialApi
fun Container.slider(
    disabled: Boolean = false,
    min: Number = 0,
    max: Number = 100,
    value: Number? = null,
    step: Number = 1,
    valueLabel: String? = null,
    ticks: Boolean = false,
    labeled: Boolean = false,
    name: String? = null,
    className: String? = null,
    init: (MdSlider.() -> Unit)? = null
) = MdSlider(
    disabled = disabled,
    min = min,
    max = max,
    value = value,
    step = step,
    valueLabel = valueLabel,
    ticks = ticks,
    labeled = labeled,
    name = name,
    className = className,
    init = init
).also(this::add)

/**
 * Computes the initial value of a slider.
 */
@Suppress("UNUSED_PARAMETER")
private fun sliderInitialValue(min: Number, max: Number): Number {
    return js("(min + max) / 2").unsafeCast<Number>()
}
