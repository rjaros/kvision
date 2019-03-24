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
package pl.treksoft.kvision.redux

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import redux.RAction

/**
 * A class which binds the redux store with the given container.
 *
 * @constructor Creates StateBinding which binds the redux store with the given container.
 * @param S redux state type
 * @param A redux action type
 * @param CONT container type
 * @param store a redux store
 * @param container a container
 * @param factory a function which re-creates the view based on the given state
 */
class StateBinding<S : Any, A : RAction, CONT : Container, CONTENT>(
    store: ReduxStore<S, A>,
    private val container: CONT,
    private val factory: (CONT.(S) -> CONTENT)
) : Widget(setOf()) {

    init {
        update(store.getState())
        store.subscribe { update(it) }
    }

    private var updateState: ((S, CONTENT) -> Unit)? = null
    private var content: CONTENT? = null

    /**
     * Updates view from the current state.
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

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun <S : Any, A : RAction, CONT : Container> CONT.stateBinding(
            store: ReduxStore<S, A>,
            factory: (CONT.(S) -> Unit)
        ): StateBinding<S, A, CONT, Unit> {
            return StateBinding(store, this, factory)
        }

        /**
         * DSL builder extension function for updateable redux content.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun <S : Any, A : RAction, CONT : Container, CONTENT> CONT.stateUpdate(
            store: ReduxStore<S, A>,
            factory: (CONT.(S) -> CONTENT)
        ): Updateable<S, CONTENT> {
            return Updateable(StateBinding(store, this, factory)::setUpdateState)
        }
    }
}

/**
 * A helper class for updateable redux content.
 */
class Updateable<S : Any, CONTENT>(private val setUpdateState: ((S, CONTENT) -> Unit) -> Unit) {
    infix fun updateWith(updateState: (S, CONTENT) -> Unit) {
        setUpdateState(updateState)
    }
}
