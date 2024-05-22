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
package io.kvision.material.select

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.menu.DEFAULT_TYPEAHEAD_DELAY
import io.kvision.core.Container

/**
 * Outlined select type.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdOutlinedSelect(
    label: String? = null,
    quick: Boolean = false,
    disabled: Boolean = false,
    required: Boolean = false,
    errorText: String? = null,
    supportingText: String? = null,
    error: Boolean = false,
    menuPositioning: SelectMenuPositioning = SelectMenuPositioning.Popover,
    typeaheadDelay: Int = DEFAULT_TYPEAHEAD_DELAY,
    selectedIndex: Int = -1,
    name: String? = null,
    value: String? = null,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdOutlinedSelect.() -> Unit)? = null
) : MdSelect(
    tag = "md-outlined-select",
    quick = quick,
    disabled = disabled,
    required = required,
    errorText = errorText,
    label = label,
    supportingText = supportingText,
    error = error,
    menuPositioning = menuPositioning,
    typeaheadDelay = typeaheadDelay,
    selectedIndex = selectedIndex,
    value = value,
    name = name,
    validationMessage = validationMessage,
    className = className
) {

    init {
        init?.let { this.it() }
    }
}

@ExperimentalMaterialApi
fun Container.outlinedSelect(
    label: String? = null,
    quick: Boolean = false,
    disabled: Boolean = false,
    required: Boolean = false,
    errorText: String? = null,
    supportingText: String? = null,
    error: Boolean = false,
    menuPositioning: SelectMenuPositioning = SelectMenuPositioning.Popover,
    typeaheadDelay: Int = DEFAULT_TYPEAHEAD_DELAY,
    selectedIndex: Int = -1,
    name: String? = null,
    value: String? = null,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdOutlinedSelect.() -> Unit)? = null
) = MdOutlinedSelect(
    quick = quick,
    value = value,
    label = label,
    disabled = disabled,
    required = required,
    errorText = errorText,
    supportingText = supportingText,
    error = error,
    menuPositioning = menuPositioning,
    typeaheadDelay = typeaheadDelay,
    selectedIndex = selectedIndex,
    name = name,
    validationMessage = validationMessage,
    className = className,
    init = init
).also(this::add)
