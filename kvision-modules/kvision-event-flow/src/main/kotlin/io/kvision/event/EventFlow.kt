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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.w3c.dom.events.Event

/**
 * Extension property returning Flow<Pair<Widget, Event>> for a given event
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline fun <reified T : Widget> T.eventFlow(event: String): Flow<Pair<T, Event>> = callbackFlow {
    val id = onEvent {
        this.asDynamic()[event] = { e: Event ->
            trySend(self to e)
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
                trySend(self)
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
                trySend(self)
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
                trySend(self)
            }
        }
        awaitClose {
            removeEventListener(id)
        }
    }
