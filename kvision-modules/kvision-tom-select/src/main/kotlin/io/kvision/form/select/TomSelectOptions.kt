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

import io.kvision.core.StringPair
import io.kvision.utils.obj

data class TomSelectOptions(
    val create: Boolean? = null,
    val createFun: ((input: String, callback: (StringPair) -> Unit) -> Unit)? = null,
    val createOnBlur: Boolean? = null,
    val createFilter: String? = null,
    val highlight: Boolean? = null,
    val persist: Boolean? = null,
    val openOnFocus: Boolean? = null,
    val maxItems: Int? = null,
    val hideSelected: Boolean? = null,
    val closeAfterSelect: Boolean? = null,
    val loadThrottle: Int? = 300,
    val loadingClass: String? = null,
    val hidePlaceholder: Boolean? = null,
    val preload: Boolean? = null,
    val addPrecedence: Boolean? = null,
    val selectOnTab: Boolean? = null,
    val duplicates: Boolean? = null,
    val caretPosition: Boolean? = null,
    val checkboxOptions: Boolean? = null,
    val clearButtonTitle: String? = null,
    val dropdownHeaderTitle: String? = null,
    val dropdownInput: Boolean? = null,
    val inputAutogrow: Boolean? = null,
    val noActiveItems: Boolean? = null,
    val noBackspaceDelete: Boolean? = null,
    val removeButtonTitle: String? = null,
    val restoreOnBackspace: Boolean? = null,
    val options: List<dynamic>? = null,
    val dataAttr: String? = null,
    val valueField: String? = null,
    val labelField: String? = null,
    val disabledField: String? = null,
    val sortField: String? = null,
    val searchField: List<String>? = null,
    val searchConjunction: String? = null
)

fun TomSelectOptions.toJs(emptyOption: Boolean): dynamic {
    val createTemp: dynamic = if (createFun != null) {
        { input: String, callback: (dynamic) -> Unit ->
            createFun.invoke(input) {
                callback(obj {
                    this.value = it.first
                    this.text = it.second
                })
            }
        }
    } else {
        create
    }
    val plugins = obj {
        this.change_listener = obj {}
        if (caretPosition != null) {
            this.caret_position = obj {}
        }
        if (checkboxOptions != null) {
            this.checkbox_options = obj {}
        }
        if (clearButtonTitle != null) {
            this.clear_button = obj {
                title = clearButtonTitle
            }
        }
        if (dropdownHeaderTitle != null) {
            this.dropdown_header = obj {
                title = dropdownHeaderTitle
            }
        }
        if (dropdownInput != null) {
            this.dropdown_input = obj {}
        }
        if (inputAutogrow != null) {
            this.input_autogrow = obj {}
        }
        if (noActiveItems != null) {
            this.no_active_items = obj {}
        }
        if (noBackspaceDelete != null) {
            this.no_backspace_delete = obj {}
        }
        if (removeButtonTitle != null) {
            this.remove_button = obj {
                title = removeButtonTitle
            }
        }
        if (restoreOnBackspace != null) {
            this.restore_on_backspace = obj {}
        }
    }
    return obj {
        if (createTemp != null) this.create = createTemp
        if (createOnBlur != null) this.createOnBlur = createOnBlur
        if (createFilter != null) this.createFilter = createFilter
        if (highlight != null) this.highlight = highlight
        if (persist != null) this.persist = persist
        if (openOnFocus != null) this.openOnFocus = openOnFocus
        if (maxItems != null) this.maxItems = maxItems
        if (hideSelected != null) this.hideSelected = hideSelected
        if (closeAfterSelect != null) this.closeAfterSelect = closeAfterSelect
        this.loadThrottle = loadThrottle
        if (loadingClass != null) this.loadingClass = loadingClass
        if (hidePlaceholder != null) this.hidePlaceholder = hidePlaceholder
        if (preload != null) this.preload = preload
        if (addPrecedence != null) this.addPrecedence = addPrecedence
        if (selectOnTab != null) this.selectOnTab = selectOnTab
        if (duplicates != null) this.duplicates = duplicates
        this.plugins = plugins
        if (options != null) this.options = if (emptyOption) arrayOf(obj {
            this.value = ""
            this.text = "\u00a0"
        }) + options.toTypedArray() else options.toTypedArray()
        if (dataAttr != null) this.dataAttr = dataAttr
        if (valueField != null) this.valueField = valueField
        if (labelField != null) this.labelField = labelField
        if (disabledField != null) this.disabledField = disabledField
        if (sortField != null) this.sortField = sortField
        if (searchField != null) this.searchField = searchField.toTypedArray()
        if (searchConjunction != null) this.searchConjunction = searchConjunction
    }
}
