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
package io.kvision.core

import io.kvision.html.Button
import io.kvision.utils.event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

val KVScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

/**
 * An extension function for defining on click suspending event handlers.
 */
inline fun <reified T : Widget> T.onClickLaunch(
    coroutineScope: CoroutineScope = KVScope,
    noinline handler: suspend T.(MouseEvent) -> Unit
): Int {
    return this.setEventListener<T> {
        click = { e ->
            coroutineScope.launch {
                self.handler(e)
            }
        }
    }
}

/**
 * An extension function for defining on click suspending event handlers for Buttons.
 */
inline fun <reified T : Button> T.onClickLaunch(
    coroutineScope: CoroutineScope = KVScope,
    noinline handler: suspend T.(MouseEvent) -> Unit
): T {
    this.setEventListener<T> {
        click = { e ->
            coroutineScope.launch {
                self.handler(e)
            }
        }
    }
    return this
}

/**
 * An extension function for defining on change suspending event handlers.
 */
inline fun <reified T : Widget> T.onInputLaunch(
    coroutineScope: CoroutineScope = KVScope,
    noinline handler: suspend T.(Event) -> Unit
): Int {
    return this.setEventListener<T> {
        input = { e ->
            coroutineScope.launch {
                self.handler(e)
            }
        }
    }
}

/**
 * An extension function for defining on change suspending event handlers.
 */
inline fun <reified T : Widget> T.onChangeLaunch(
    coroutineScope: CoroutineScope = KVScope,
    noinline handler: suspend T.(Event) -> Unit
): Int {
    return this.setEventListener<T> {
        change = { e ->
            coroutineScope.launch {
                self.handler(e)
            }
        }
    }
}

/**
 * An extension function for defining on change suspending event handlers.
 */
inline fun <reified T : Widget> T.onEventLaunch(
    event: String,
    coroutineScope: CoroutineScope = KVScope,
    noinline handler: suspend T.(Event) -> Unit
): Int {
    return this.setEventListener<T> {
        event(event) { e ->
            coroutineScope.launch {
                self.handler(e)
            }
        }
    }
}
