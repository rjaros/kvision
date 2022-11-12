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

package io.kvision.form.select.js

import org.w3c.dom.HTMLElement

@JsModule("tom-select")
@JsNonModule
@JsName("TomSelect")
external class TomSelectJs(element: HTMLElement, options: dynamic) {
    fun addOption(value: dynamic, user_created: Boolean = definedExternally)
    fun addOptions(value: Array<dynamic>, user_created: Boolean = definedExternally)
    fun updateOption(value: String, data: dynamic)
    fun removeOption(value: String)
    fun getOption(value: String, create: Boolean = definedExternally): dynamic
    fun refreshOptions(triggerDropdown: Boolean = definedExternally)
    fun clearOptions(clearFilter: (dynamic) -> Boolean = definedExternally)

    fun clear(silent: Boolean = definedExternally)
    fun getItem(value: String)
    fun addItem(value: String, silent: Boolean = definedExternally)
    fun removeItem(value: String, silent: Boolean = definedExternally)
    fun createItem(value: String, callback: (dynamic) -> Unit = definedExternally)
    fun refreshItems()

    fun addOptionGroup(id: String, data: dynamic)
    fun removeOptionGroup(id: String)
    fun clearOptionGroups()

    fun on(event: String, handler: dynamic)
    fun off(event: String, handler: dynamic)
    fun off(event: String)
    fun trigger(event: String)

    fun open()
    fun close()
    fun positionDropdown()

    fun load(query: String)
    fun focus()
    fun blur()
    fun lock()
    fun unlock()
    fun disable()
    fun enable()
    fun getValue(): Any?
    fun setValue(value: Any?, silent: Boolean = definedExternally)
    fun setCaret(index: Int)
    fun isFull(): Boolean
    fun clearCache()
    fun setTextboxValue(value: String)
    fun sync()
    fun destroy()
}
