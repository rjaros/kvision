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

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import pl.treksoft.kvision.KVManagerRedux
import pl.treksoft.kvision.utils.JSON
import redux.RAction
import redux.Reducer
import redux.Store
import redux.WrapperAction
import redux.rEnhancer

typealias Dispatch<A> = (A) -> WrapperAction
typealias GetState<S> = () -> S
typealias ActionCreator<A, S> = (Dispatch<A>, GetState<S>) -> Unit

/**
 * An inline helper function for creating Redux store.
 *
 * @param reducer a reducer function
 * @param initialState an initial state
 * @param middlewares a list of optional Redux JS middlewares
 */
@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified S : Any, A : RAction> createReduxStore(
    noinline reducer: Reducer<S, A>,
    initialState: S,
    vararg middlewares: dynamic
): ReduxStore<S, A> {
    @Suppress("SpreadOperator")
    return ReduxStore(reducer, initialState, S::class.serializer(), *middlewares)
}

/**
 * A class implementing redux pattern backed by the original Redux JS library.
 * It requires @Serializable state.
 *
 * @constructor Creates a Redux store with given reducer function and initial state.
 * @param S redux state type (@Serializable)
 * @param A redux action type
 * @param reducer a reducer function
 * @param initialState an initial state
 * @param stateSerializer a serializer for the state type
 * @param middlewares a list of optional Redux JS middlewares
 */
class ReduxStore<S : Any, A : RAction>(
    reducer: Reducer<S, A>,
    initialState: S,
    val stateSerializer: KSerializer<S>,
    vararg middlewares: dynamic
) {
    private val store: Store<String, dynamic, WrapperAction>

    init {
        @Suppress("UnsafeCastFromDynamic")
        store = KVManagerRedux.createStore(
            { s: String, a: RAction ->
                @Suppress("UnsafeCastFromDynamic")
                if (a == undefined || (a.asDynamic().type is String && a.asDynamic().type.startsWith("@@"))) {
                    s
                } else {
                    @Suppress("UNCHECKED_CAST")
                    JSON.plain.stringify(stateSerializer, reducer(JSON.plain.parse(stateSerializer, s), a as A))
                }
            },
            JSON.plain.stringify(stateSerializer, initialState),
            @Suppress("SpreadOperator")
            KVManagerRedux.compose(KVManagerRedux.applyMiddleware(KVManagerRedux.reduxThunk, *middlewares), rEnhancer())
        )
    }

    /**
     * Returns the current state.
     */
    fun getState(): S {
        return JSON.plain.parse(stateSerializer, store.getState())
    }

    /**
     * Dispatches a synchronous action object.
     */
    fun dispatch(action: A): WrapperAction {
        return store.dispatch(action)
    }

    /**
     * Dispatches an asynchronous action function.
     */
    fun dispatch(actionCreator: ActionCreator<dynamic, S>): WrapperAction {
        return store.dispatch({ reduxDispatch: Dispatch<dynamic>, reduxGetState: GetState<String> ->
            val newDispatch: Dispatch<dynamic> = { elem ->
                @Suppress("UnsafeCastFromDynamic")
                if (js("typeof elem === 'function'")) {
                    dispatch(actionCreator = elem)
                } else {
                    reduxDispatch(elem)
                }
            }
            actionCreator(newDispatch, { JSON.plain.parse(stateSerializer, reduxGetState()) })
        })
    }

    /**
     * Subscribes a client for the change state notifications.
     */
    fun subscribe(listener: (S) -> Unit): () -> Unit {
        return store.subscribe {
            listener(getState())
        }
    }
}
