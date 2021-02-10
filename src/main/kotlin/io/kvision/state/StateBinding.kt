/*
 * Copyright (c) 2019. Robert Jaros
 */
package io.kvision.state

import io.kvision.core.Widget
import io.kvision.core.Widget.Companion.bindState

/**
 * An extension function which binds the widget to the observable state.
 *
 * @param S the state type
 * @param W the widget type
 * @param observableState the state
 * @param removeChildren remove all children of the component
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Widget> W.bind(
    observableState: ObservableState<S>,
    removeChildren: Boolean = true,
    factory: (W.(S) -> Unit)
): W {
    return this.bindState(observableState, removeChildren, factory)
}
