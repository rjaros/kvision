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

import com.copperleaf.ballast.BallastViewModel
import io.kvision.core.Component
import io.kvision.panel.SimplePanel

/**
 * An extension function which binds the widget to the given ballast view model.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param W the widget type
 * @param viewModel the ballast view model instance
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, W : Component> W.bind(
    viewModel: BallastViewModel<Inputs, Events, State>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(State) -> Unit)
): W {
    return bind(viewModel.observeStates(), removeChildren, runImmediately, factory)
}

/**
 * An extension function which binds the widget to the given ballast view model using the sub extractor function.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param T the sub state type
 * @param W the widget type
 * @param viewModel the ballast view model instance
 * @param sub an extractor function
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, T, W : Component> W.bind(
    viewModel: BallastViewModel<Inputs, Events, State>,
    sub: (State) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(T) -> Unit)
): W {
    return bind(viewModel.observeStates(), sub, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given ballast view model
 * when the given condition is true.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param W the container type
 * @param viewModel the ballast view model instance
 * @param condition the condition predicate
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, W : SimplePanel> W.insertWhen(
    viewModel: BallastViewModel<Inputs, Events, State>,
    condition: (State) -> Boolean,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(State) -> Unit
): SimplePanel {
    return insertWhen(viewModel.observeStates(), condition, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given ballast view model
 * using the sub extractor function when the given condition is true.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param T the sub state type
 * @param W the container type
 * @param viewModel the ballast view model instance
 * @param sub an extractor function
 * @param condition the condition predicate
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, T, W : SimplePanel> W.insertWhen(
    viewModel: BallastViewModel<Inputs, Events, State>,
    sub: (State) -> T,
    condition: (T) -> Boolean,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    return insertWhen(viewModel.observeStates(), sub, condition, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given ballast view model
 * using the sub extractor function when the sub state value is not null.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param T the sub state type
 * @param W the container type
 * @param viewModel the ballast view model instance
 * @param sub an extractor function
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, T, W : SimplePanel> W.insertNotNull(
    viewModel: BallastViewModel<Inputs, Events, State>,
    sub: (State) -> T?,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    return insertNotNull(viewModel.observeStates(), sub, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given ballast view model.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param W the container type
 * @param viewModel the ballast view model instance
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, W : SimplePanel> W.insert(
    viewModel: BallastViewModel<Inputs, Events, State>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(State) -> Unit
): SimplePanel {
    return insert(viewModel.observeStates(), removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given ballast view model
 * using the sub extractor function.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param T the sub state type
 * @param W the container type
 * @param viewModel the ballast view model instance
 * @param sub an extractor function
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, T, W : SimplePanel> W.insert(
    viewModel: BallastViewModel<Inputs, Events, State>,
    sub: (State) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    return insert(viewModel.observeStates(), sub, removeChildren, runImmediately, factory)
}

/**
 * An extension function which binds the widget to the given ballast view model synchronously.
 * It's less efficient than [bind], but fully compatible with KVision 4 state bindings.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param W the widget type
 * @param viewModel the ballast view model instance
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, W : Component> W.bindSync(
    viewModel: BallastViewModel<Inputs, Events, State>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(State) -> Unit)
): W {
    return bindSync(viewModel.observeStates(), removeChildren, runImmediately, factory)
}

/**
 * An extension function which binds the widget to the given ballast view model synchronously using the sub extractor function.
 * It's less efficient than [bind], but fully compatible with KVision 4 state bindings.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param T the sub state type
 * @param W the widget type
 * @param viewModel the ballast view model instance
 * @param sub an extractor function
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, T, W : Component> W.bindSync(
    viewModel: BallastViewModel<Inputs, Events, State>,
    sub: (State) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(T) -> Unit)
): W {
    return bindSync(viewModel.observeStates(), sub, removeChildren, runImmediately, factory)
}

/**
 * An extension function which binds the container to the given ballast view model of a list of items.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param W the container type
 * @param viewModel the ballast view model instance
 * @param equalizer optional custom equalizer function
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State, W : SimplePanel> W.bindEach(
    viewModel: BallastViewModel<Inputs, Events, List<State>>,
    equalizer: ((State, State) -> Boolean)? = null,
    factory: (SimplePanel.(State) -> Unit)
): W {
    return bindEach(viewModel.observeStates(), equalizer, factory)
}

/**
 * An extension function which binds the container to the given ballast view model using the
 * sub extractor function to get a list of items.
 *
 * @param Inputs the inputs type
 * @param Events the events type
 * @param State the state type
 * @param T the sub state type
 * @param W the container type
 * @param viewModel the ballast view model instance
 * @param sub an extractor function
 * @param equalizer optional custom equalizer function
 * @param factory a function which re-creates the view based on the given state
 */
fun <Inputs : Any, Events : Any, State : Any, T, W : SimplePanel> W.bindEach(
    viewModel: BallastViewModel<Inputs, Events, State>,
    sub: (State) -> List<T>,
    equalizer: ((T, T) -> Boolean)? = null,
    factory: (SimplePanel.(T) -> Unit)
): W {
    return bindEach(viewModel.observeStates(), sub, equalizer, factory)
}
