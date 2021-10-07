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

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.GenericFormComponent
import io.kvision.panel.SimplePanel
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
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Widget> W.bind(
    stateFlow: StateFlow<S>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(S) -> Unit)
): W {
    return this.bind(stateFlow.observableState, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given state flow
 * when the given condition is true.
 *
 * @param S the state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Container> W.insertWhen(
    stateFlow: StateFlow<S>,
    condition: (S) -> Boolean,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: Container.(S) -> Unit
) {
    return this.insertWhen(stateFlow.observableState, condition, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given state flow
 * when the state value is not null.
 *
 * @param S the state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Container> W.insertNotNull(
    stateFlow: StateFlow<S?>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: Container.(S) -> Unit
) {
    return this.insertNotNull(stateFlow.observableState, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given state flow.
 *
 * @param S the state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Container> W.insert(
    stateFlow: StateFlow<S>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: Container.(S) -> Unit
) {
    return this.insert(stateFlow.observableState, removeChildren, runImmediately, factory)
}

/**
 * An extension function which binds the widget to the given state flow synchronously.
 * It's less efficient than [bind], but fully compatible with KVision 4 state bindings.
 *
 * @param S the state type
 * @param W the widget type
 * @param stateFlow the StateFlow instance
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Component> W.bindSync(
    stateFlow: StateFlow<S>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(S) -> Unit)
): W {
    return this.bindSync(stateFlow.observableState, removeChildren, runImmediately, factory)
}

/**
 * An extension function which binds the container to the given state flow of a list of items.
 *
 * @param S the state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param equalizer optional custom equalizer function
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : SimplePanel> W.bindEach(
    stateFlow: StateFlow<List<S>>,
    equalizer: ((S, S) -> Boolean)? = null,
    factory: (SimplePanel.(S) -> Unit)
): W {
    return this.bindEach(stateFlow.observableState, equalizer, factory)
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
