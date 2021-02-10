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
package io.kvision.panel

import io.kvision.core.AlignItems
import io.kvision.core.Container
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.set

/**
 * The container with vertical layout.
 *
 * This is a special case of the flexbox layout.
 *
 * @constructor
 * @param justify flexbox content justification
 * @param alignItems flexbox items alignment
 * @param spacing spacing between columns/rows
 * @param noWrappers do not use additional div wrappers for child items
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class VPanel(
    justify: JustifyContent? = null, alignItems: AlignItems? = null, spacing: Int? = null,
    noWrappers: Boolean = false,
    classes: Set<String> = setOf(), init: (VPanel.() -> Unit)? = null
) : FlexPanel(
    FlexDirection.COLUMN,
    null, justify, alignItems, null, spacing, noWrappers, classes
) {
    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.vPanel(
    justify: JustifyContent? = null, alignItems: AlignItems? = null, spacing: Int? = null,
    noWrappers: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (VPanel.() -> Unit)? = null
): VPanel {
    val vpanel = VPanel(justify, alignItems, spacing, noWrappers, classes ?: className.set, init)
    this.add(vpanel)
    return vpanel
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.vPanel(
    state: ObservableState<S>,
    justify: JustifyContent? = null, alignItems: AlignItems? = null, spacing: Int? = null,
    noWrappers: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (VPanel.(S) -> Unit)
) = vPanel(justify, alignItems, spacing, noWrappers, classes, className).bind(state, true, init)