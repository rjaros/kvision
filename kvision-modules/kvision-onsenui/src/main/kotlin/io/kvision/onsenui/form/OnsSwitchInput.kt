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

package io.kvision.onsenui.form

import io.kvision.snabbdom.VNode
import io.kvision.core.Container
import io.kvision.utils.set

/**
 * OnsenUI switch input component.
 *
 * @constructor Creates a switch input component.
 * @param value switch input value
 * @param inputId the ID of the input element
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class OnsSwitchInput(
    value: Boolean = false,
    inputId: String? = null,
    className: String? = null,
    init: (OnsSwitchInput.() -> Unit)? = null
) : OnsCheckBoxInput(value, inputId, className) {

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-switch")
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsSwitchInput(
    value: Boolean = false,
    inputId: String? = null,
    className: String? = null,
    init: (OnsSwitchInput.() -> Unit)? = null
): OnsSwitchInput {
    val onsSwitchInput = OnsSwitchInput(value, inputId, className, init)
    this.add(onsSwitchInput)
    return onsSwitchInput
}
