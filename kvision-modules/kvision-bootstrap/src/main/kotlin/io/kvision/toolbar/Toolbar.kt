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
package io.kvision.toolbar

import io.kvision.core.Container
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.set

/**
 * The Bootstrap toolbar.
 *
 * @constructor
 * @param size button groups size
 * @param spacing the spacing between button groups
 * @param vertical determines if button groups are aligned vertically
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Toolbar(
    val size: ButtonGroupSize? = null, val spacing: Int = 5, val vertical: Boolean = false,
    classes: Set<String> = setOf(), init: (Toolbar.() -> Unit)? = null
) : SimplePanel(classes + "btn-toolbar") {

    init {
        role = "toolbar"
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.toolbar(
    size: ButtonGroupSize? = null, spacing: Int = 2, vertical: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Toolbar.() -> Unit)? = null
): Toolbar {
    val toolbar = Toolbar(size, spacing, vertical, classes ?: className.set, init)
    this.add(toolbar)
    return toolbar
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.toolbar(
    state: ObservableState<S>,
    size: ButtonGroupSize? = null, spacing: Int = 2, vertical: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Toolbar.(S) -> Unit)
) = toolbar(size, spacing, vertical, classes, className).bind(state, true, init)
