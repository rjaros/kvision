/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
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

package io.kvision.progress

import io.kvision.core.Container
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableState
import io.kvision.state.ObservableValue
import io.kvision.state.bind

/**
 * The Bootstrap progress
 *
 * @constructor
 * @param T the type that describes the progress, typically @see Number
 * @param bounds bounds of the progress
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
class Progress<T>(
    bounds: Bounds<T>,
    classes: Set<String> = setOf(),
    init: (Progress<T>.() -> Unit)? = null
) : SimplePanel(classes + "progress") {

    /**
     * @constructor
     * @param min the minimal value
     * @param max the maximal value
     * @param classes a set of CSS class names
     * @param init an initializer extension function
     */
    constructor(min: T, max: T, classes: Set<String> = setOf(), init: (Progress<T>.() -> Unit)? = null) : this(
        Bounds(
            min,
            max
        ), classes, init
    )

    val bounds = ObservableValue(bounds)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    fun setBounds(min: T, max: T) {
        bounds.value = Bounds(min, max)
    }

    /**
     * Returns the first progress bar.
     */
    fun getFirstProgressBar(): ProgressBarTag<T>? {
        return getChildren().find { it is ProgressBarTag<*> }?.let {
            it.unsafeCast<ProgressBarTag<T>>()
        }
    }

    /**
     * Returns the list of all progress bars.
     */
    fun getProgressBars(): List<ProgressBarTag<T>> {
        return getChildren().filterIsInstance<ProgressBarTag<T>>()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T> Container.progress(
    bounds: Bounds<T>,
    classes: Set<String> = setOf(),
    init: (Progress<T>.() -> Unit)? = null
): Progress<T> = Progress(bounds, classes, init).also { this.add(it) }

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.progress(
    min: Number = 0,
    max: Number = 100,
    classes: Set<String> = setOf(),
    init: (Progress<Number>.() -> Unit)? = null
): Progress<Number> = Progress(Bounds(min, max), classes, init).also { this.add(it) }

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T, S> Container.progress(
    state: ObservableState<S>,
    bounds: Bounds<T>,
    classes: Set<String> = setOf(),
    init: Progress<T>.(S) -> Unit
) = progress(bounds, classes).bind(state, true, init)

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.progress(
    state: ObservableState<S>,
    min: Number = 0,
    max: Number = 100,
    classes: Set<String> = setOf(),
    init: Progress<Number>.(S) -> Unit
) = progress(min, max, classes).bind(state, true, init)
