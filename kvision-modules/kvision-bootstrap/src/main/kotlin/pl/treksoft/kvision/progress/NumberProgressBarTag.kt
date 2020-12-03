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

import pl.treksoft.kvision.core.BsBgColor

/**
 * An implementation of the @see ProgressBarTag, which works with plain old numbers
 */
class NumberProgressBarTag(
    private val progress: Progress<out Number>,
    initialValue: Number = 0,
    private val contentGenerator: ContentGenerator<Number>,
    bgColor: BsBgColor? = null,
    classes: Set<String> = setOf(),
    init: (NumberProgressBarTag.() -> Unit)? = null
) : ProgressBarTag<Number>(classes, bgColor) {

    init {
        init?.invoke(this)
    }

    override var value: Number = initialValue
        set(value) {
            field = value
            update()
        }

    private val unsubscribe: () -> Unit

    init {
        unsubscribe = progress.bounds.subscribe { update() }
        update()
    }

    private fun update() {
        val bounds = progress.bounds.value
        val fraction = bounds.fraction(value.toDouble())
        setFraction(fraction)
        ariaMax = bounds.max.toString()
        ariaMin = bounds.min.toString()
        ariaValue = value.toString()
        contentGenerator.generateContent(this, value, bounds)
    }

    override fun dispose() {
        super.dispose()
        unsubscribe()
    }
}

fun Progress<out Number>.progressNumeric(
    initialValue: Number = 0,
    contentGenerator: ContentGenerator<Number> = ContentGenerator { _, _, _ -> },
    bgColor: BsBgColor? = null,
    classes: Set<String> = setOf(),
    init: (NumberProgressBarTag.() -> Unit)? = null
) = NumberProgressBarTag(this, initialValue, contentGenerator, bgColor, classes, init).also { add(it) }
