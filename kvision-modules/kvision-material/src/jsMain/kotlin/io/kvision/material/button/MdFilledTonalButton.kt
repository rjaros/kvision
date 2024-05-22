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
package io.kvision.material.button

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.widget.LinkTarget
import io.kvision.core.Container

/**
 * A filled tonal button is an alternative middle ground between filled and outlined buttons.
 * They're useful in contexts where a lower-priority button requires slightly more emphasis than an
 * outline would give, such as "Next" in an onboarding flow.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdFilledTonalButton(
    text: String? = null,
    disabled: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    trailingIcon: Boolean = false,
    type: ButtonType = ButtonType.Submit,
    value: String? = null,
    name: String? = null,
    className: String? = null,
    init: (MdFilledTonalButton.() -> Unit)? = null
) : MdButton(
    tag = "md-filled-tonal-button",
    text = text,
    disabled = disabled,
    href = href,
    target = target,
    trailingIcon = trailingIcon,
    type = type,
    value = value,
    name = name,
    className = className
) {

    init {
        init?.let { this.it() }
    }
}

@ExperimentalMaterialApi
fun Container.filledTonalButton(
    text: String? = null,
    disabled: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    trailingIcon: Boolean = false,
    hasIcon: Boolean = trailingIcon,
    type: ButtonType = ButtonType.Submit,
    value: String? = null,
    name: String? = null,
    className: String? = null,
    init: (MdFilledTonalButton.() -> Unit)? = null
) = MdFilledTonalButton(
    text = text,
    disabled = disabled,
    href = href,
    target = target,
    trailingIcon = trailingIcon,
    type = type,
    value = value,
    name = name,
    className = className,
    init = init
).also(this::add)
