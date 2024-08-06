/*
 * Copyright (c) 2019. Robert Jaros
 */
package io.kvision.state

import io.github.petertrr.diffutils.algorithm.myers.MyersDiff
import io.github.petertrr.diffutils.diff
import io.github.petertrr.diffutils.patch.ChangeDelta
import io.github.petertrr.diffutils.patch.DeleteDelta
import io.github.petertrr.diffutils.patch.EqualDelta
import io.github.petertrr.diffutils.patch.InsertDelta
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.Display
import io.kvision.core.Widget
import io.kvision.form.GenericFormComponent
import io.kvision.panel.SimplePanel
import io.kvision.panel.simplePanel
import kotlin.js.Date

/**
 * An extension function which binds the widget to the observable state.
 *
 * @param S the state type
 * @param W the widget type
 * @param observableState the state
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Component> W.bind(
    observableState: ObservableState<S>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(S) -> Unit)
): W {
    var skip = !runImmediately
    this.addBeforeDisposeHook(observableState.subscribe {
        if (!skip) {
            this.singleRenderAsync {
                if (removeChildren) (this as? Container)?.disposeAll()
                factory(it)
            }
        } else {
            skip = false
        }
    })
    return this
}

/**
 * An extension function which binds the widget to the observable state using the sub state extractor.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the widget type
 * @param observableState the state
 * @param sub an extractor function for sub state
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : Component> W.bind(
    observableState: ObservableState<S>,
    sub: (S) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(T) -> Unit)
): W {
    return this.bind(
        observableState.sub(this.unsafeCast<Widget>(), sub),
        removeChildren,
        runImmediately,
        factory
    )
}

/**
 * An extension function which inserts child component and binds it to the observable state
 * when the given condition is true.
 *
 * @param S the state type
 * @param W the container type
 * @param observableState the state
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : SimplePanel> W.insertWhen(
    observableState: ObservableState<S>,
    condition: (S) -> Boolean,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(S) -> Unit
): SimplePanel {
    return simplePanel {
        display = Display.CONTENTS
    }.bind(observableState, removeChildren, runImmediately) { state ->
        if (condition(state)) {
            factory(state)
            this.show()
        } else {
            this.hide()
        }
    }
}

/**
 * An extension function which inserts child component and binds it to the observable state using the sub state extractor
 * when the given condition is true.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the container type
 * @param observableState the state
 * @param sub an extractor function for sub state
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : SimplePanel> W.insertWhen(
    observableState: ObservableState<S>,
    sub: (S) -> T,
    condition: (T) -> Boolean,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    val simplePanel = simplePanel {
        display = Display.CONTENTS
    }
    simplePanel.bind(observableState.sub(simplePanel, sub), removeChildren, runImmediately) { state ->
        if (condition(state)) {
            factory(state)
            this.show()
        } else {
            this.hide()
        }
    }
    return simplePanel
}

/**
 * An extension function which inserts child component and binds it to the observable state
 * when the state value is not null.
 *
 * @param S the state type
 * @param W the container type
 * @param observableState the state
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : SimplePanel> W.insertNotNull(
    observableState: ObservableState<S?>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(S) -> Unit
): SimplePanel {
    return insertWhen(observableState, { it != null }, removeChildren, runImmediately) {
        factory(it!!)
    }
}

/**
 * An extension function which inserts child component and binds it to the observable state using the sub state extractor
 * when the state value is not null.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the container type
 * @param observableState the state
 * @param sub an extractor function for sub state
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : SimplePanel> W.insertNotNull(
    observableState: ObservableState<S>,
    sub: (S) -> T?,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    return insertWhen(observableState, sub, { it != null }, removeChildren, runImmediately) {
        factory(it!!)
    }
}

/**
 * An extension function which inserts child component and binds it to the observable state.
 *
 * @param S the state type
 * @param W the container type
 * @param observableState the state
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : SimplePanel> W.insert(
    observableState: ObservableState<S>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(S) -> Unit
): SimplePanel {
    return insertWhen(observableState, { true }, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the observable state using the sub state extractor.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the container type
 * @param observableState the state
 * @param sub an extractor function for sub state
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : SimplePanel> W.insert(
    observableState: ObservableState<S>,
    sub: (S) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    return insertWhen(observableState, sub, { true }, removeChildren, runImmediately, factory)
}

/**
 * An extension function which binds the widget to the observable state synchronously.
 * It's less efficient than [bind], but fully compatible with KVision 4 state bindings.
 *
 * @param S the state type
 * @param W the widget type
 * @param observableState the state
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : Component> W.bindSync(
    observableState: ObservableState<S>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(S) -> Unit)
): W {
    var skip = !runImmediately
    this.addBeforeDisposeHook(observableState.subscribe {
        if (!skip) {
            this.singleRender {
                if (removeChildren) (this as? Container)?.disposeAll()
                factory(it)
            }
        } else {
            skip = false
        }
    })
    return this
}

/**
 * An extension function which binds the widget to the observable state synchronously using the sub state extractor.
 * It's less efficient than [bind], but fully compatible with KVision 4 state bindings.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the widget type
 * @param observableState the state
 * @param sub an extractor function for sub state
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : Component> W.bindSync(
    observableState: ObservableState<S>,
    sub: (S) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(T) -> Unit)
): W {
    return this.bindSync(
        observableState.sub(this.unsafeCast<Widget>(), sub),
        removeChildren,
        runImmediately,
        factory
    )
}

/**
 * An extension function which binds the container to the observable state with a list of items.
 *
 * @param S the state type
 * @param W the container type
 * @param observableState the state
 * @param equalizer optional custom equalizer function
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : SimplePanel> W.bindEach(
    observableState: ObservableState<List<S>>,
    equalizer: ((S, S) -> Boolean)? = null,
    factory: (W.(S) -> Unit)
): W {
    fun addSingleComponent(state: S) {
        val previousChildrenSize = this.getChildren().size
        factory(state)
        val newChildrenSize = this.getChildren().size
        val newChildren = this.getChildren()
        if (newChildrenSize != previousChildrenSize + 1) {
            simplePanel {
                display = Display.CONTENTS
                for (i in newChildrenSize - 1 downTo previousChildrenSize) {
                    val child = newChildren[i]
                    this@bindEach.removeAt(i)
                    add(0, child)
                }
            }
        }
    }

    fun getSingleComponent(state: S): Component {
        val previousChildrenSize = this.getChildren().size
        factory(state)
        val newChildrenSize = this.getChildren().size
        val newChildren = this.getChildren()
        return if (newChildrenSize == previousChildrenSize + 1) {
            val child = newChildren.last()
            removeAt(newChildrenSize - 1)
            child
        } else {
            SimplePanel {
                display = Display.CONTENTS
                for (i in newChildrenSize - 1 downTo previousChildrenSize) {
                    val child = newChildren[i]
                    this@bindEach.removeAt(i)
                    add(0, child)
                }
            }
        }
    }

    this._archivedState = null
    val unsubscribe = observableState.subscribe {
        this.singleRender {
            val previousState = _archivedState?.unsafeCast<List<S>>() ?: emptyList()
            val myersDiff = if (equalizer != null) {
                MyersDiff(equalizer)
            } else MyersDiff()
            val patch = diff(previousState, it, myersDiff)
            val deltas = patch.deltas
            val iterator = deltas.listIterator(deltas.size)
            while (iterator.hasPrevious()) {
                when (val delta = iterator.previous()) {
                    is ChangeDelta -> {
                        val position: Int = delta.source.position
                        val size: Int = delta.source.size()
                        for (i in 0 until size) {
                            val component = this.getChildren()[position]
                            this.removeAt(position)
                            component.dispose()
                        }
                        delta.target.lines.forEachIndexed { i, line ->
                            if (position + i == this.getChildren().size) {
                                addSingleComponent(line)
                            } else {
                                this.add(position + i, getSingleComponent(line))
                            }
                        }
                    }

                    is DeleteDelta -> {
                        val position = delta.source.position
                        for (i in 0 until delta.source.size()) {
                            val component = this.getChildren()[position]
                            this.removeAt(position)
                            component.dispose()
                        }
                    }

                    is InsertDelta -> {
                        val position = delta.source.position
                        delta.target.lines.forEachIndexed { i, line ->
                            if (position + i == this.getChildren().size) {
                                addSingleComponent(line)
                            } else {
                                this.add(position + i, getSingleComponent(line))
                            }
                        }
                    }

                    is EqualDelta -> {
                    }
                }
            }
            _archivedState = it.toList()
        }
    }
    this.addBeforeDisposeHook {
        this._archivedState = null
        unsubscribe()
    }
    return this
}

/**
 * An extension function which binds the container to the observable state with a list of items using the sub state extractor.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the container type
 * @param observableState the state
 * @param sub an extractor function for sub state
 * @param equalizer optional custom equalizer function
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : SimplePanel> W.bindEach(
    observableState: ObservableState<S>,
    sub: (S) -> List<T>,
    equalizer: ((T, T) -> Boolean)? = null,
    factory: (SimplePanel.(T) -> Unit)
): W {
    return bindEach(observableState.sub(this, sub), equalizer, factory)
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun <S, T : GenericFormComponent<S>> T.bindTo(state: MutableState<S>): T {
    bindSync(state, false) {
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
    bindSync(state, false) {
        if (value != it && !(value == null && it == "")) value = it
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
    bindSync(state, false) {
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
    bindSync(state, false) {
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
    bindSync(state, false) {
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
    bindSync(state, false) {
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
    bindSync(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it ?: Date())
    })
    return this
}
