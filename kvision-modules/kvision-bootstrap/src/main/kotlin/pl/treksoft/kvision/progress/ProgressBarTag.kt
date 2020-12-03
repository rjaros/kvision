/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
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

package pl.treksoft.kvision.progress

import pl.treksoft.kvision.core.AttributeDelegate
import pl.treksoft.kvision.core.BsBgColor
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.html.Div
import pl.treksoft.kvision.utils.perc

/**
 * A base class for the progress bar tag. This class only exposes utility methods in order to set the typical
 * attributes used by the bootstrap progress bar in a type-safe manner.
 */
abstract class ProgressBarTag<T>(classes: Set<String> = setOf(), bgColor: BsBgColor? = null) :
    Div(classes = classes) {
    init {
        role = "progressbar"
    }

    /**
     * The style of the progress bar.
     */
    var style by refreshOnUpdate(bgColor)

    /**
     * Determines if the progress bar is striped.
     */
    var striped by refreshOnUpdate(false)

    /**
     * Determines if the progress bar is animated.
     */
    var animated by refreshOnUpdate(false)

    protected var ariaValue by AttributeDelegate("aria-valuenow")
    protected var ariaMin by AttributeDelegate("aria-valuemin")
    protected var ariaMax by AttributeDelegate("aria-valuemax")

    /**
     * Abstract value for the progress bar.
     */
    abstract var value: T

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("progress-bar" to true)
        style?.let {
            cl.add(it.className to true)
        }
        if (striped || animated) {
            cl.add("progress-bar-striped" to true)
        }
        if (animated) {
            cl.add("progress-bar-animated" to true)
        }
        return cl
    }

    protected fun setFraction(fraction: Double) {
        setPercentage(fraction * 100)
    }

    protected fun setPercentage(percentage: Double) {
        width = percentage.perc
    }
}
