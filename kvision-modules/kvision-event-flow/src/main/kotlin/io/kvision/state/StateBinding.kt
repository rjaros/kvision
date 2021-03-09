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
package io.kvision.state

import io.kvision.core.Widget
import io.kvision.form.GenericFormComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.js.Date

/**
 * An extension function which binds the widget to the given state flow.
 *
 * @param S the state type
 * @param W the widget type
 * @param stateFlow the StateFlow instance
 * @param removeChildren remove all children of the component
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Widget> W.bind(
    stateFlow: StateFlow<S>,
    removeChildren: Boolean = true,
    factory: (W.(S) -> Unit)
): W {
    return this.bind(stateFlow.observableState, removeChildren, factory)
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <S, T : GenericFormComponent<S>> T.bindTo(mutableStateFlow: MutableStateFlow<S>): T {
    return this.bindTo(mutableStateFlow.mutableState)
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<String?>> T.bindTo(mutableStateFlow: MutableStateFlow<String>): T {
    return this.bindTo(mutableStateFlow.mutableState)
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(mutableStateFlow: MutableStateFlow<Int?>): T {
    return this.bindTo(mutableStateFlow.mutableState)
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(mutableStateFlow: MutableStateFlow<Int>): T {
    return this.bindTo(mutableStateFlow.mutableState)
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(mutableStateFlow: MutableStateFlow<Double?>): T {
    return this.bindTo(mutableStateFlow.mutableState)
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(mutableStateFlow: MutableStateFlow<Double>): T {
    return this.bindTo(mutableStateFlow.mutableState)
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Date?>> T.bindTo(mutableStateFlow: MutableStateFlow<Date>): T {
    return this.bindTo(mutableStateFlow.mutableState)
}
