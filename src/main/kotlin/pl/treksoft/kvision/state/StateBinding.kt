/*
 * Copyright (c) 2019. Robert Jaros
 */
package pl.treksoft.kvision.state

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget

/**
 * A class which binds the given container to the observable state.
 *
 * @constructor Creates StateBinding which binds the given container to the observable state.
 * @param S the state type
 * @param CONT container type
 * @param observableState the state
 * @param container the container
 * @param factory a function which re-creates the view based on the given state
 */
class StateBinding<S : Any, CONT : Container, CONTENT>(
    observableState: ObservableState<S>,
    private val container: CONT,
    private val factory: (CONT.(S) -> CONTENT)
) : Widget(setOf()) {

    private val unsubscribe: () -> Unit

    init {
        unsubscribe = observableState.subscribe { update(it) }
    }

    private var updateState: ((S, CONTENT) -> Unit)? = null
    private var content: CONTENT? = null

    /**
     * Updates view based on the current state.
     */
    @Suppress("ComplexMethod")
    fun update(state: S) {
        singleRender {
            if (updateState == null || content == null) {
                container.removeAll()
                container.add(this)
                content = container.factory(state)
            } else {
                content?.let {
                    updateState?.invoke(state, it)
                }
            }
        }
    }

    private fun setUpdateState(updateState: (S, CONTENT) -> Unit) {
        this.updateState = updateState
    }

    override fun dispose() {
        unsubscribe()
        super.dispose()
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun <S : Any, CONT : Container> CONT.stateBinding(
            observableState: ObservableState<S>,
            factory: (CONT.(S) -> Unit)
        ): StateBinding<S, CONT, Unit> {
            return StateBinding(observableState, this, factory)
        }

        /**
         * DSL builder extension function for updateable redux content.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun <S : Any, CONT : Container, CONTENT> CONT.stateUpdate(
            observableState: ObservableState<S>,
            factory: (CONT.(S) -> CONTENT)
        ): Updateable<S, CONTENT> {
            return Updateable(StateBinding(observableState, this, factory)::setUpdateState)
        }
    }
}

/**
 * A helper class for updateable content.
 */
class Updateable<S : Any, CONTENT>(private val setUpdateState: ((S, CONTENT) -> Unit) -> Unit) {
    infix fun updateWith(updateState: (S, CONTENT) -> Unit) {
        setUpdateState(updateState)
    }
}
