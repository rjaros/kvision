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

package io.kvision.progress

import io.kvision.html.Tag
import io.kvision.utils.Intl
import io.kvision.utils.numberFormat

/**
 * Uses a `Intl.NumberFormat` to format the fraction of a value within bounds. E.g. if value = 2, bounds=[1,10], then
 * the fraction is 0.2 which is formatted using a given `numberFormat`.
 *
 * @param numberFormat the number format to be used. Defaults to formatting as percentage
 */
class FormatFractionContentGenerator(
    private val numberFormat: Intl.NumberFormat = numberFormat {
        style = "percent"
    }
) :
    ContentGenerator<Number> {
    override fun generateContent(tag: Tag, value: Number, bounds: Bounds<out Number>) {
        tag.content = numberFormat.format(bounds.fraction(value.toDouble()))
    }
}
