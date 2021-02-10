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
package io.kvision.redux

import org.reduxkotlin.ActionTypes
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store
import org.reduxkotlin.Thunk
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.createStore
import org.reduxkotlin.createThunkMiddleware
import io.kvision.state.ObservableState

interface RAction
typealias ReducerFun<S, A> = (S, A) -> S
typealias Dispatch<A> = (A) -> Unit
typealias GetState<S> = () -> S
typealias ActionCreator<A, S> = (Dispatch<A>, GetState<S>) -> Unit

/**
 * An inline helper function for creating Redux store.
 *
 * @param reducer a reducer function
 * @param initialState an initial state
 * @param middlewares a list of optional Redux JS middlewares
 */
fun <S : Any, A : RAction> createReduxStore(
    reducer: ReducerFun<S, A>,
    initialState: S,
    vararg middlewares: Middleware<S>
): ReduxStore<S, A> {
    @Suppress("SpreadOperator")
    return ReduxStore(reducer, initialState, *middlewares)
}

/**
 * A class implementing redux pattern backed by the Redux Kotlin library.
 *
 * @constructor Creates a Redux store with given reducer function and initial state.
 * @param S redux state type
 * @param A redux action type
 * @param reducer a reducer function
 * @param initialState an initial state
 * @param middlewares a list of optional Redux Kotlin middlewares
 */
class ReduxStore<S : Any, A : RAction>(
    reducer: ReducerFun<S, A>,
    initialState: S,
    vararg middlewares: Middleware<S>
) : ObservableState<S> {
    @Suppress("UNCHECKED_CAST", "SpreadOperator")
    private val store: Store<S> = createStore({ s: S, a: Any ->
        if (a == ActionTypes.INIT || a == ActionTypes.REPLACE) {
            s
        } else {
            reducer(s, a as A)
        }
    }, initialState, applyMiddleware(createThunkMiddleware(), *middlewares))

    override fun getState(): S {
        return store.getState()
    }

    /**
     * Dispatches a synchronous action object.
     */
    fun dispatch(action: A) {
        store.dispatch(action)
    }

    /**
     * Dispatches an asynchronous action function.
     */
    fun dispatch(actionCreator: ActionCreator<A, S>) {
        @Suppress("UNCHECKED_CAST")
        val thunk: Thunk<S> = { dispatch, getState, _ -> actionCreator(dispatch as ((A) -> Unit), getState) }
        store.dispatch(thunk)
    }

    override fun subscribe(observer: (S) -> Unit): () -> Unit {
        observer(getState())
        return store.subscribe {
            observer(getState())
        }
    }
}
