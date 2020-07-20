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

@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.events

import kotlin.js.Promise

@JsModule("events")
@JsNonModule
external open class internal : pl.treksoft.kvision.electron.NodeJS.EventEmitter {
    open class EventEmitter : internal {
        override fun addListener(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun addListener(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun on(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun on(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun once(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun once(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun prependListener(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun prependListener(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun removeListener(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun removeListener(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun off(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun off(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
        override fun removeAllListeners(event: String): EventEmitter /* this */
        override fun removeAllListeners(event: Any): EventEmitter /* this */
        override fun setMaxListeners(n: Number): EventEmitter /* this */
        override fun getMaxListeners(): Number
        override fun listeners(event: String): Array<Function<*>>
        override fun listeners(event: Any): Array<Function<*>>
        override fun rawListeners(event: String): Array<Function<*>>
        override fun rawListeners(event: Any): Array<Function<*>>
        override fun emit(event: String, vararg args: Any): Boolean
        override fun emit(event: Any, vararg args: Any): Boolean
        override fun eventNames(): Array<dynamic /* String | Any */>
        override fun listenerCount(type: String): Number
        override fun listenerCount(type: Any): Number

        companion object {
            fun listenerCount(emitter: EventEmitter, event: String): Number
            fun listenerCount(emitter: EventEmitter, event: Any): Number
            var defaultMaxListeners: Number
        }
    }

    companion object {
        fun once(emitter: NodeEventTarget, event: String): Promise<Array<Any>>
        fun once(emitter: NodeEventTarget, event: Any): Promise<Array<Any>>
        fun once(emitter: DOMEventTarget, event: String): Promise<Array<Any>>
    }
}

external interface NodeEventTarget {
    fun once(event: String, listener: (args: Any) -> Unit): NodeEventTarget /* this */
    fun once(event: Any, listener: (args: Any) -> Unit): NodeEventTarget /* this */
}

external interface `T$17` {
    var once: Boolean
}

external interface DOMEventTarget {
    fun addEventListener(event: String, listener: (args: Any) -> Unit, opts: `T$17` = definedExternally): Any
}