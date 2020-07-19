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
@file:JsModule("domain")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.domain

import pl.treksoft.kvision.electron.NodeJS.Timer
import pl.treksoft.kvision.electron.events.internal.EventEmitter

external open class Domain : pl.treksoft.kvision.electron.NodeJS.Domain {
    override fun <T> run(fn: (args: Any) -> T, vararg args: Any): T
    open fun add(emitter: EventEmitter)
    override fun add(emitter: pl.treksoft.kvision.electron.NodeJS.EventEmitter)
    override fun add(emitter: Timer)
    open fun remove(emitter: EventEmitter)
    override fun remove(emitter: pl.treksoft.kvision.electron.NodeJS.EventEmitter)
    override fun remove(emitter: Timer)
    override fun <T : Function<*>> bind(cb: T): T
    override fun <T : Function<*>> intercept(cb: T): T
    override fun addListener(event: String, listener: (args: Any) -> Unit): pl.treksoft.kvision.electron.NodeJS.Domain

    override fun on(event: String, listener: (args: Any) -> Unit): pl.treksoft.kvision.electron.NodeJS.Domain

    override fun once(event: String, listener: (args: Any) -> Unit): pl.treksoft.kvision.electron.NodeJS.Domain

    override fun removeListener(
        event: String,
        listener: (args: Any) -> Unit
    ): pl.treksoft.kvision.electron.NodeJS.Domain

    override fun removeAllListeners(event: String): pl.treksoft.kvision.electron.NodeJS.Domain

    open var members: Array<dynamic /* events.EventEmitter | NodeJS.Timer */>
    open fun enter()
    open fun exit()
    override fun add(emitter: Timer)
    override fun remove(emitter: Timer)
}

external fun create(): Domain