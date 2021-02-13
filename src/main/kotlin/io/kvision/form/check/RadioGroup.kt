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
import io.kvision.form.StringFormControl
import io.kvision.state.MutableState
import io.kvision.state.ObservableState
import io.kvision.state.bind

/**
 * The form field component rendered as a group of HTML *input type="radio"* elements with the same name attribute.
 *
 * The radio group can be populated directly from *options* parameter or manually by adding
 * [Radio] components to the container.
 *
 * @constructor
 * @param options an optional list of options (label to value pairs) for the group
 * @param value selected option
 * @param name the name attribute of the generated HTML input element
 * @param inline determines if the options are rendered inline
 * @param label label text of the options group
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
open class RadioGroup(
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    label: String? = null,
    rich: Boolean = false,
    init: (RadioGroup.() -> Unit)? = null
) : GenericRadioGroup<String>(options, value, name, inline, label, rich), StringFormControl {

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getValue(): String? = value
    override fun setValue(v: Any?) {
        value = v as? String ?: v?.toString()
    }

    override fun getValueAsString(): String? = value
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.radioGroup(
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    label: String? = null, rich: Boolean = false, init: (RadioGroup.() -> Unit)? = null
): RadioGroup {
    val radioGroup = RadioGroup(options, value, name, inline, label, rich, init)
    this.add(radioGroup)
    return radioGroup
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.radioGroup(
    state: ObservableState<S>,
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    label: String? = null, rich: Boolean = false, init: (RadioGroup.(S) -> Unit)
) = radioGroup(options, value, name, inline, label, rich).bind(state, true, init)

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun RadioGroup.bindTo(state: MutableState<String?>): RadioGroup {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it)
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun RadioGroup.bindTo(state: MutableState<String>): RadioGroup {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it ?: "")
    })
    return this
}
