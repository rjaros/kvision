/*
 * Copyright (c) 2017-present Robert Jaros
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
package io.kvision.form.check

import io.kvision.core.Container

/**
 * The basic input Bootstrap checkbox component rendered as a switch.
 *
 * @constructor
 * @param value selection state
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class SwitchInput(
    value: Boolean = false,
    className: String? = null,
    init: (SwitchInput.() -> Unit)? = null
) : CheckBoxInput(value, className) {

    init {
        @Suppress("LeakingThis")
        addSurroundingCssClass("form-switch")
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.switchInput(
    value: Boolean = false,
    className: String? = null,
    init: (SwitchInput.() -> Unit)? = null
): SwitchInput {
    val switchInput = SwitchInput(value, className, init)
    this.add(switchInput)
    return switchInput
}
