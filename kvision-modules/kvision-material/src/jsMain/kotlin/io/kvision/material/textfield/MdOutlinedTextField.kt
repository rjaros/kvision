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
package io.kvision.material.textfield

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.core.Container
import io.kvision.html.Autocomplete

/**
 * Outlined text field type.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdOutlinedTextField(
    disabled: Boolean = false,
    error: Boolean = false,
    errorText: String? = null,
    label: String? = null,
    required: Boolean = false,
    value: String? = null,
    prefixText: String? = null,
    suffixText: String? = null,
    supportingText: String? = null,
    rows: Int = 2,
    cols: Int = 20,
    inputMode: TextFieldInputMode? = null,
    max: TextFieldRangeConstraint? = null,
    maxLength: Int = -1,
    min: TextFieldRangeConstraint? = null,
    minLength: Int = -1,
    pattern: String? = null,
    placeholder: String? = null,
    readOnly: Boolean = false,
    multiple: Boolean = false,
    step: Number? = null,
    type: TextFieldInputType = TextFieldInputType.Text,
    autoComplete: Autocomplete? = null,
    name: String? = null,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdOutlinedTextField.() -> Unit)? = null
) : MdTextField(
    tag = "md-outlined-text-field",
    disabled = disabled,
    error = error,
    errorText = errorText,
    label = label,
    required = required,
    value = value,
    prefixText = prefixText,
    suffixText = suffixText,
    supportingText = supportingText,
    rows = rows,
    cols = cols,
    inputMode = inputMode,
    max = max,
    maxLength = maxLength,
    min = min,
    minLength = minLength,
    pattern = pattern,
    placeholder = placeholder,
    readOnly = readOnly,
    multiple = multiple,
    step = step,
    type = type,
    autoComplete = autoComplete,
    name = name,
    validationMessage = validationMessage,
    className = className
) {

    init {
        init?.let { this.it() }
    }
}

@ExperimentalMaterialApi
fun Container.outlinedTextField(
    disabled: Boolean = false,
    error: Boolean = false,
    errorText: String? = null,
    label: String? = null,
    required: Boolean = false,
    value: String? = null,
    prefixText: String? = null,
    suffixText: String? = null,
    supportingText: String? = null,
    rows: Int = 2,
    cols: Int = 20,
    inputMode: TextFieldInputMode? = null,
    max: TextFieldRangeConstraint? = null,
    maxLength: Int = -1,
    min: TextFieldRangeConstraint? = null,
    minLength: Int = -1,
    pattern: String? = null,
    placeholder: String? = null,
    readOnly: Boolean = false,
    multiple: Boolean = false,
    step: Number? = null,
    type: TextFieldInputType = TextFieldInputType.Text,
    autoComplete: Autocomplete? = null,
    name: String? = null,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdOutlinedTextField.() -> Unit)? = null
) = MdOutlinedTextField(
    disabled = disabled,
    error = error,
    errorText = errorText,
    label = label,
    required = required,
    value = value,
    prefixText = prefixText,
    suffixText = suffixText,
    supportingText = supportingText,
    rows = rows,
    cols = cols,
    inputMode = inputMode,
    max = max,
    maxLength = maxLength,
    min = min,
    minLength = minLength,
    pattern = pattern,
    placeholder = placeholder,
    readOnly = readOnly,
    multiple = multiple,
    step = step,
    type = type,
    autoComplete = autoComplete,
    name = name,
    validationMessage = validationMessage,
    className = className,
    init = init
).also(this::add)
