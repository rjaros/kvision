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

data class TomSelectRenders(
    val option: ((data: dynamic, escape: (String) -> String) -> String)? = null,
    val item: ((data: dynamic, escape: (String) -> String) -> String)? = null,
    val optionCreate: ((data: dynamic, escape: (String) -> String) -> String)? = null,
    val noResults: ((data: dynamic, escape: (String) -> String) -> String)? = null,
    val notLoading: ((data: dynamic, escape: (String) -> String) -> String)? = null,
    val optGroup: ((data: dynamic) -> String)? = null,
    val optGroupHeader: ((dynamic, escape: (String) -> String) -> String)? = null,
    val loading: ((data: dynamic, escape: (String) -> String) -> String)? = null,
    val dropdown: (() -> String)? = null,
)

fun TomSelectRenders.toJs(): dynamic {
    return obj {
        if (option != null) this.option = option
        if (item != null) this.item = item
        if (optionCreate != null) this.option_create = optionCreate
        this.no_results = noResults
        if (notLoading != null) this.not_loading = notLoading
        if (optGroup != null) this.optgroup = optGroup
        if (optGroupHeader != null) this.optgroup_header = optGroupHeader
        if (loading != null) this.loading = loading
        if (dropdown != null) this.dropdown = dropdown
    }
}
