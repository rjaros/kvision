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
package io.kvision.material.form

import io.kvision.material.ExperimentalMaterialApi

/**
 * Subclass of form associated input widgets that can be toggled.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
abstract class MdFormToggleInputWidget internal constructor(
    tag: String,
    disabled: Boolean,
    required: Boolean,
    value: String,
    name: String?,
    validationMessage: String?,
    className: String?,
) : MdFormInputWidget<String>(
    tag = tag,
    disabled = disabled,
    required = required,
    value = value,
    name = name,
    validationMessage = validationMessage,
    className = className
) {

    ///////////////////////////////////////////////////////////////////////////
    // State
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Toggle the checked/selected state of this widget.
     */
    abstract fun toggle()
}
