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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.js.Date

internal fun initWidgetScope(widget: Widget) {
    if (widget.kvscope == null) {
        widget.kvscope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        widget.addBeforeDisposeHook {
            if (widget.kvscope != null) {
                widget.kvscope.unsafeCast<CoroutineScope>().cancel()
                widget.kvscope = null
            }
        }
    }
}

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
fun <S, W : Component> W.bind(
    stateFlow: StateFlow<S>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(S) -> Unit)
): W {
    initWidgetScope(this.unsafeCast<Widget>())
    if (runImmediately) {
        this.singleRenderAsync {
            if (removeChildren) (this as? Container)?.disposeAll()
            factory(stateFlow.value)
        }
    }
    var skip = true
    stateFlow.onEach {
        if (!skip) {
            this.singleRenderAsync {
                if (this.unsafeCast<Widget>().kvscope != null) {
                    if (removeChildren) (this as? Container)?.disposeAll()
                    factory(it)
                }
            }
        } else {
            skip = false
        }
    }.launchIn(this.unsafeCast<Widget>().kvscope.unsafeCast<CoroutineScope>())
    return this
}

/**
 * An extension function which binds the widget to the given state flow using the sub flow extractor.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the widget type
 * @param stateFlow the StateFlow instance
 * @param sub an extractor function for sub flow
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : Component> W.bind(
    stateFlow: StateFlow<S>,
    sub: (S) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(T) -> Unit)
): W {
    initWidgetScope(this.unsafeCast<Widget>())
    return this.bind(
        stateFlow.subFlow(this.unsafeCast<Widget>().kvscope.unsafeCast<CoroutineScope>(), sub),
        removeChildren,
        runImmediately,
        factory
    )
}

/**
 * An extension function which inserts child component and binds it to the given state flow
 * when the given condition is true.
 *
 * @param S the state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param condition the condition predicate
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, W : SimplePanel> W.insertWhen(
    stateFlow: StateFlow<S>,
    condition: (S) -> Boolean,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(S) -> Unit
): SimplePanel {
    return simplePanel {
        display = Display.CONTENTS
    }.bind(stateFlow, removeChildren, runImmediately) { state ->
        if (condition(state)) {
            factory(state)
            this.show()
        } else {
            this.hide()
        }
    }
}

/**
 * An extension function which inserts child component and binds it to the given state flow using the sub flow extractor
 * when the given condition is true.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param sub an extractor function for sub flow
 * @param condition the condition predicate
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : SimplePanel> W.insertWhen(
    stateFlow: StateFlow<S>,
    sub: (S) -> T,
    condition: (T) -> Boolean,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    val simplePanel = simplePanel {
        display = Display.CONTENTS
        initWidgetScope(this)
    }
    simplePanel.bind(
        stateFlow.subFlow(simplePanel.kvscope.unsafeCast<CoroutineScope>(), sub),
        removeChildren,
        runImmediately
    ) { state ->
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
fun <S, W : SimplePanel> W.insertNotNull(
    stateFlow: StateFlow<S?>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(S) -> Unit
): SimplePanel {
    return insertWhen(stateFlow, { it != null }, removeChildren, runImmediately) {
        factory(it!!)
    }
}

/**
 * An extension function which inserts child component and binds it to the given state flow using the sub flow extractor
 * when the state value is not null.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param sub an extractor function for sub flow
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : SimplePanel> W.insertNotNull(
    stateFlow: StateFlow<S>,
    sub: (S) -> T?,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    return insertWhen(stateFlow, sub, { it != null }, removeChildren, runImmediately) {
        factory(it!!)
    }
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
fun <S, W : SimplePanel> W.insert(
    stateFlow: StateFlow<S>,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(S) -> Unit
): SimplePanel {
    return insertWhen(stateFlow, { true }, removeChildren, runImmediately, factory)
}

/**
 * An extension function which inserts child component and binds it to the given state flow using the sub flow extractor.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param sub an extractor function for sub flow
 * @param removeChildren remove all children of the child component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : SimplePanel> W.insert(
    stateFlow: StateFlow<S>,
    sub: (S) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: SimplePanel.(T) -> Unit
): SimplePanel {
    return insertWhen(stateFlow, sub, { true }, removeChildren, runImmediately, factory)
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
    initWidgetScope(this.unsafeCast<Widget>())
    if (runImmediately) {
        this.singleRender {
            if (removeChildren) (this as? Container)?.disposeAll()
            factory(stateFlow.value)
        }
    }
    var skip = true
    stateFlow.onEach {
        if (!skip) {
            this.singleRender {
                if (this.unsafeCast<Widget>().kvscope != null) {
                    if (removeChildren) (this as? Container)?.disposeAll()
                    factory(it)
                }
            }
        } else {
            skip = false
        }
    }.launchIn(this.unsafeCast<Widget>().kvscope.unsafeCast<CoroutineScope>())
    return this
}

/**
 * An extension function which binds the widget to the given state flow synchronously using the sub flow extractor.
 * It's less efficient than [bind], but fully compatible with KVision 4 state bindings.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the widget type
 * @param stateFlow the StateFlow instance
 * @param sub an extractor function for sub flow
 * @param removeChildren remove all children of the component
 * @param runImmediately whether to run factory function immediately with the current state
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : Component> W.bindSync(
    stateFlow: StateFlow<S>,
    sub: (S) -> T,
    removeChildren: Boolean = true,
    runImmediately: Boolean = true,
    factory: (W.(T) -> Unit)
): W {
    initWidgetScope(this.unsafeCast<Widget>())
    return this.bindSync(
        stateFlow.subFlow(this.unsafeCast<Widget>().kvscope.unsafeCast<CoroutineScope>(), sub),
        removeChildren,
        runImmediately,
        factory
    )
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
    fun getSingleComponent(state: S): Component {
        val tempContainer = SimplePanel {
            display = Display.CONTENTS
            factory(state)
        }
        return if (tempContainer.getChildren().size == 1) {
            val child = tempContainer.getChildren().first()
            tempContainer.removeAll()
            tempContainer.dispose()
            child
        } else {
            tempContainer
        }
    }
    initWidgetScope(this)
    this._archivedState = null
    stateFlow.onEach {
        this.singleRender {
            if (this.unsafeCast<Widget>().kvscope != null) {
                val previousState = _archivedState?.unsafeCast<List<S>>() ?: emptyList()
                val patch = diff(previousState, it, equalizer)
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
                                this.add(position + i, getSingleComponent(line))
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
                                this.add(position + i, getSingleComponent(line))
                            }
                        }
                        is EqualDelta -> {
                        }
                    }
                }
                _archivedState = it.toList()
            }
        }
    }.launchIn(this.kvscope.unsafeCast<CoroutineScope>())
    this.addBeforeDisposeHook {
        this._archivedState = null
    }
    return this
}

/**
 * An extension function which binds the container to the given state flow using the sub flow extractor to get a list of items.
 *
 * @param S the state type
 * @param T the sub state type
 * @param W the container type
 * @param stateFlow the StateFlow instance
 * @param sub an extractor function for sub flow
 * @param equalizer optional custom equalizer function
 * @param factory a function which re-creates the view based on the given state
 */
fun <S, T, W : SimplePanel> W.bindEach(
    stateFlow: StateFlow<S>,
    sub: (S) -> List<T>,
    equalizer: ((T, T) -> Boolean)? = null,
    factory: (SimplePanel.(T) -> Unit)
): W {
    initWidgetScope(this)
    return bindEach(stateFlow.subFlow(this.kvscope.unsafeCast<CoroutineScope>(), sub), equalizer, factory)
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <S, T : GenericFormComponent<S>> T.bindTo(mutableStateFlow: MutableStateFlow<S>): T {
    bindSync(mutableStateFlow, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        mutableStateFlow.value = it
    })
    return this
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<String?>> T.bindTo(mutableStateFlow: MutableStateFlow<String>): T {
    bindSync(mutableStateFlow, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        mutableStateFlow.value = it ?: ""
    })
    return this
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(mutableStateFlow: MutableStateFlow<Int?>): T {
    bindSync(mutableStateFlow, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        mutableStateFlow.value = it?.toInt()
    })
    return this
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(mutableStateFlow: MutableStateFlow<Int>): T {
    bindSync(mutableStateFlow, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        mutableStateFlow.value = it?.toInt() ?: 0
    })
    return this
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(mutableStateFlow: MutableStateFlow<Double?>): T {
    bindSync(mutableStateFlow, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        mutableStateFlow.value = it?.toDouble()
    })
    return this
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Number?>> T.bindTo(mutableStateFlow: MutableStateFlow<Double>): T {
    bindSync(mutableStateFlow, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        mutableStateFlow.value = it?.toDouble() ?: 0.0
    })
    return this
}

/**
 * Bidirectional data binding to the MutableStateFlow instance.
 * @param mutableStateFlow the MutableStateFlow instance
 * @return current component
 */
fun <T : GenericFormComponent<Date?>> T.bindTo(mutableStateFlow: MutableStateFlow<Date>): T {
    bindSync(mutableStateFlow, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        mutableStateFlow.value = it ?: Date()
    })
    return this
}
