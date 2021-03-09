/*
 * Copyright (c) 2019. Robert Jaros
 */
package io.kvision.state

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.form.GenericFormComponent
import kotlin.js.Date

/**
 * An extension function which binds the widget to the observable state.
 *
 * @param S the state type
 * @param W the widget type
 * @param observableState the state
 * @param removeChildren remove all children of the component
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Component> W.bind(
    observableState: ObservableState<S>,
    removeChildren: Boolean = true,
    factory: (W.(S) -> Unit)
): W {
    this.addBeforeDisposeHook(observableState.subscribe {
        this.singleRender {
            if (removeChildren) (this as? Container)?.disposeAll()
            factory(it)
        }
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun <S, T : GenericFormComponent<S>> T.bindTo(state: MutableState<S>): T {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it)
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun <T : GenericFormComponent<String?>> T.bindTo(state: MutableState<String>): T {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it ?: "")
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(state: MutableState<Int?>): T {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it?.toInt())
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(state: MutableState<Int>): T {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it?.toInt() ?: 0)
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(state: MutableState<Double?>): T {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it?.toDouble())
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(state: MutableState<Double>): T {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it?.toDouble() ?: 0.0)
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun <T : GenericFormComponent<Date?>> T.bindTo(state: MutableState<Date>): T {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it ?: Date())
    })
    return this
}
