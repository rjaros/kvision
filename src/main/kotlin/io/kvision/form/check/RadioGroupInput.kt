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
import io.kvision.core.StringPair
import io.kvision.state.ObservableState
import io.kvision.state.bind

/**
 * The input component rendered as a group of HTML *input type="radio"* elements with the same name attribute.
 */
typealias RadioGroupInput = GenericRadioGroupInput<String>

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.radioGroupInput(
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    init: (RadioGroupInput.() -> Unit)? = null
): RadioGroupInput {
    val radioGroupInput = RadioGroupInput(options, value, name, inline, init = init)
    this.add(radioGroupInput)
    return radioGroupInput
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.radioGroupInput(
    state: ObservableState<S>,
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    init: (RadioGroupInput.(S) -> Unit)
) = radioGroupInput(options, value, name, inline).bind(state, true, init)
