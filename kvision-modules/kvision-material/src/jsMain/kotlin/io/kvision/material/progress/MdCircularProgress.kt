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
package io.kvision.material.progress

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.core.Container

/**
 * Circular progress indicator type.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdCircularProgress(
    value: Number = 0,
    max: Number = 1,
    indeterminate: Boolean = false,
    fourColor: Boolean = false,
    className: String? = null,
    init: (MdCircularProgress.() -> Unit)? = null
) : MdProgress(
    tag = "md-circular-progress",
    value = value,
    max = max,
    indeterminate = indeterminate,
    fourColor = fourColor,
    className = className
) {

    init {
        init?.let { this.it() }
    }
}

@ExperimentalMaterialApi
fun Container.circularProgress(
    value: Number = 0,
    max: Number = 1,
    indeterminate: Boolean = false,
    fourColor: Boolean = false,
    className: String? = null,
    init: (MdCircularProgress.() -> Unit)? = null
) = MdCircularProgress(
    value = value,
    max = max,
    indeterminate = indeterminate,
    fourColor = fourColor,
    className = className,
    init = init
).also(this::add)
