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
package io.kvision.event

import io.kvision.core.Widget
import io.kvision.core.onEvent
import io.kvision.state.MutableState
import io.kvision.state.ObservableState
import io.kvision.state.ObservableValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.w3c.dom.events.Event

/**
 * Extension property returning Flow<Pair<Widget, Event>> for a given event
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline fun <reified T : Widget> T.eventFlow(event: String): Flow<Pair<T, Event>> = callbackFlow {
    val id = onEvent {
        this.asDynamic()[event] = { e: Event ->
            offer(self to e)
        }
    }
    awaitClose {
        removeEventListener(id)
    }
}

/**
 * Extension property returning Flow<Widget> for a click event.
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline val <reified T : Widget> T.clickFlow: Flow<T>
    get() = callbackFlow {
        val id = onEvent {
            click = {
                offer(self)
            }
        }
        awaitClose {
            removeEventListener(id)
        }
    }

/**
 * Extension property returning Flow<Widget> for an input event.
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline val <reified T : Widget> T.inputFlow: Flow<T>
    get() = callbackFlow {
        val id = onEvent {
            input = {
                offer(self)
            }
        }
        awaitClose {
            removeEventListener(id)
        }
    }

/**
 * Extension property returning Flow<Widget> for a change event.
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline val <reified T : Widget> T.changeFlow: Flow<T>
    get() = callbackFlow {
        val id = onEvent {
            change = {
                offer(self)
            }
        }
        awaitClose {
            removeEventListener(id)
        }
    }

/**
 * Extension property returning a StateFlow<S> for an ObservableState<S>.
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline val <S> ObservableState<S>.stateFlow: StateFlow<S>
    get() = MutableStateFlow(getState()).apply {
        this@stateFlow.subscribe { this.value = it }
    }

/**
 * Extension property returning a MutableStateFlow<S> for a MutableState<S>.
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline val <S> MutableState<S>.mutableStateFlow: MutableStateFlow<S>
    get() = MutableStateFlow(getState()).apply {
        this@mutableStateFlow.subscribe { this.value = it }
        this.onEach {
            this@mutableStateFlow.setState(it)
        }.launchIn(GlobalScope)
    }

/**
 * Extension property returning an ObservableState<S> for a StateFlow<S>.
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline val <S>StateFlow<S>.observableState: ObservableState<S>
    get() = object : ObservableValue<S>(this.value) {

        var job: Job? = null

        override fun subscribe(observer: (S) -> Unit): () -> Unit {
            observers += observer
            observer(value)
            if (job == null) {
                job = this@observableState.onEach {
                    this.value = it
                }.launchIn(GlobalScope)
            }
            return {
                observers -= observer
                if (observers.isEmpty()) {
                    job?.cancel()
                    job = null
                }
            }
        }
    }

/**
 * Extension property returning a MutableState<S> for a MutableStateFlow<S>.
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline val <S>MutableStateFlow<S>.mutableState: MutableState<S>
    get() = object : ObservableValue<S>(this.value) {

        var job: Job? = null

        override fun subscribe(observer: (S) -> Unit): () -> Unit {
            observers += observer
            observer(value)
            if (job == null) {
                job = this@mutableState.onEach {
                    this.value = it
                }.launchIn(GlobalScope)
            }
            return {
                observers -= observer
                if (observers.isEmpty()) {
                    job?.cancel()
                    job = null
                }
            }
        }

        override fun setState(state: S) {
            super.setState(state)
            this@mutableState.value = state
        }
    }

/**
 * Extension property returning an ObservableState<S?> for a Flow<S>.
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline val <S>Flow<S>.observableState: ObservableState<S?>
    get() = object : ObservableValue<S?>(null) {

        var job: Job? = null

        override fun subscribe(observer: (S?) -> Unit): () -> Unit {
            observers += observer
            observer(value)
            if (job == null) {
                job = this@observableState.onEach {
                    this.value = it
                }.launchIn(GlobalScope)
            }
            return {
                observers -= observer
                if (observers.isEmpty()) {
                    job?.cancel()
                    job = null
                }
            }
        }
    }
