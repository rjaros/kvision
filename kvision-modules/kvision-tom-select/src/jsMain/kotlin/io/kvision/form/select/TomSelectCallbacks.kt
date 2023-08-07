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

package io.kvision.form.select

import io.kvision.utils.obj

data class TomSelectCallbacks(
    val load: ((query: String, callback: (Array<dynamic>) -> Unit) -> Unit)? = null,
    val shouldLoad: ((query: String) -> Boolean)? = null,
    val score: ((search: String) -> (dynamic) -> Int)? = null,
    val onInitialize: (() -> Unit)? = null,
    val onFocus: (() -> Unit)? = null,
    val onBlur: (() -> Unit)? = null,
    val onChange: ((value: dynamic) -> Unit)? = null,
    val onItemAdd: ((value: dynamic, item: dynamic) -> Unit)? = null,
    val onItemRemove: ((value: dynamic) -> Unit)? = null,
    val onClear: (() -> Unit)? = null,
    val onDelete: ((value: dynamic, event: dynamic) -> Unit)? = null,
    val onOptionAdd: ((value: dynamic, data: dynamic) -> Unit)? = null,
    val onOptionRemove: ((value: dynamic) -> Unit)? = null,
    val onDropdownOpen: ((dropdown: dynamic) -> Unit)? = null,
    val onDropdownClose: ((dropdown: dynamic) -> Unit)? = null,
    val onType: ((str: String) -> Unit)? = null,
    val onLoad: ((options: dynamic, optgroup: dynamic) -> Unit)? = null,
)

fun TomSelectCallbacks.toJs(): dynamic {
    return obj {
        if (load != null) this.load = load
        if (shouldLoad != null) this.shouldLoad = shouldLoad
        if (score != null) this.score = score
        if (onInitialize != null) this.onInitialize = onInitialize
        if (onFocus != null) this.onFocus = onFocus
        if (onBlur != null) this.onBlur = onBlur
        if (onChange != null) this.onChange = onChange
        if (onItemAdd != null) this.onItemAdd = onItemAdd
        if (onItemRemove != null) this.onItemRemove = onItemRemove
        if (onClear != null) this.onClear = onClear
        if (onDelete != null) this.onDelete = onDelete
        if (onOptionAdd != null) this.onOptionAdd = onOptionAdd
        if (onOptionRemove != null) this.onOptionRemove = onOptionRemove
        if (onDropdownOpen != null) this.onDropdownOpen = onDropdownOpen
        if (onDropdownClose != null) this.onDropdownClose = onDropdownClose
        if (onType != null) this.onType = onType
        if (onLoad != null) this.onLoad = onLoad
    }
}
