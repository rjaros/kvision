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
package io.kvision.material.iconbutton

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.widget.LinkTarget
import io.kvision.core.Container

/**
 * Filled icon buttons have higher visual impact and are best for high emphasis actions.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdFilledIconButton(
    disabled: Boolean = false,
    flipIconInRtl: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    ariaLabelSelected: String? = null,
    toggle: Boolean = false,
    selected: Boolean = false,
    type: IconButtonType = IconButtonType.Submit,
    value: String? = null,
    name: String? = null,
    className: String? = null,
    init: (MdFilledIconButton.() -> Unit)? = null
) : MdIconButton(
    tag = "md-filled-icon-button",
    disabled = disabled,
    flipIconInRtl = flipIconInRtl,
    href = href,
    target = target,
    ariaLabelSelected = ariaLabelSelected,
    toggle = toggle,
    selected = selected,
    type = type,
    value = value,
    name = name,
    className = className,
) {
    init {
        init?.let { this.it() }
    }
}

@ExperimentalMaterialApi
fun Container.filledIconButton(
    disabled: Boolean = false,
    flipIconInRtl: Boolean = false,
    href: String? = null,
    target: LinkTarget? = null,
    ariaLabelSelected: String? = null,
    toggle: Boolean = false,
    selected: Boolean = false,
    type: IconButtonType = IconButtonType.Submit,
    value: String? = null,
    name: String? = null,
    className: String? = null,
    init: (MdFilledIconButton.() -> Unit)? = null
) = MdFilledIconButton(
    disabled = disabled,
    flipIconInRtl = flipIconInRtl,
    href = href,
    target = target,
    ariaLabelSelected = ariaLabelSelected,
    toggle = toggle,
    selected = selected,
    type = type,
    value = value,
    name = name,
    className = className,
    init = init
).also(this::add)
