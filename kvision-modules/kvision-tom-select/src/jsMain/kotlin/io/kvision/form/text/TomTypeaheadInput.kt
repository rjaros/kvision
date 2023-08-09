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

package io.kvision.form.text

import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.select.TomSelectCallbacks
import io.kvision.form.select.js.TomSelectJs
import io.kvision.form.select.toJs
import io.kvision.html.Autocomplete
import io.kvision.html.InputType
import io.kvision.snabbdom.VNode
import io.kvision.utils.obj
import kotlinx.browser.window
import org.w3c.dom.HTMLElement

/**
 * The basic component for typeahead control based on Tom Select.
 *
 * @constructor
 * @param options a static list of options
 * @param type text input type (default "text")
 * @param value text input value
 * @param tsCallbacks Tom Select callbacks
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class TomTypeaheadInput(
    options: List<String>? = null, type: InputType = InputType.TEXT, value: String? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    className: String? = null, init: (TomTypeaheadInput.() -> Unit)? = null
) : TextInput(type, value, null, (className?.let { "$it " } ?: "") + "kv-typeahead") {

    /**
     * A static list of options for a typeahead control
     */
    var options by refreshOnUpdate(options)

    /**
     * Tom Select callbacks
     */
    var tsCallbacks by refreshOnUpdate(tsCallbacks)

    /**
     * The native TomSelect object instance
     */
    var tomSelectJs: TomSelectJs? = null

    init {
        useSnabbdomDistinctKey()
        autocomplete = Autocomplete.OFF
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun afterInsert(node: VNode) {
        tomSelectJs = TomSelectJs(getElement().unsafeCast<HTMLElement>(), getSettingsObj())
        refreshState()
        super.afterInsert(node)
    }

    override fun afterDestroy() {
        tomSelectJs?.destroy()
        tomSelectJs = null
        super.afterDestroy()
    }

    protected open fun getSettingsObj(): dynamic {
        return obj {
            this.maxItems = 1
            this.create = { input: String ->
                val oldValue = tomSelectJs?.getValue()?.unsafeCast<String>()?.ifBlank { null }?.let { "$it " } ?: ""
                obj {
                    this.value = "$oldValue$input"
                    this.text = "$oldValue$input"
                    this.created = true
                }
            }
            this.createOnBlur = true
            this.persist = true
            this.openOnFocus = true
            this.duplicates = true
            if (options != null) {
                this.options = options!!.map {
                    obj {
                        this.value = it
                        this.text = translate(it)
                    }
                }.toTypedArray()
            }
            this.render = obj {
                this.option_create = { _: dynamic, _: (dynamic) -> String ->
                    ""
                }
                this.no_results = null
            }
            this.plugins = arrayOf("restore_on_backspace", "change_listener")
            if (tsCallbacks != null) {
                val callbackObj = tsCallbacks!!.toJs()
                js("Object").assign(this, callbackObj)
                if (tsCallbacks!!.load != null) {
                    this.load = { query: String, callback: (dynamic) -> Unit ->
                        tsCallbacks!!.load!!(query) { options ->
                            callback(options.map {
                                obj {
                                    this.value = it
                                    this.text = it
                                }
                            })
                        }
                    }
                }
            }
            this.onOptionAdd = { value: dynamic, _: dynamic ->
                tomSelectJs?.clearOptions { option: dynamic ->
                    option.created != true || option.value == value
                }
            }
            this.onFocus = {
                if (tomSelectJs?.getValue() != "") {
                    tomSelectJs?.removeItem(tomSelectJs?.getValue()?.unsafeCast<String>()!!)
                }
            }
        }
    }

    override fun refreshState() {
        super.refreshState()
        if (tomSelectJs != null) {
            if (value != null) {
                val existingOption = tomSelectJs?.getOption(value!!)
                if (existingOption == null) {
                    tomSelectJs?.clearOptions { option: dynamic ->
                        option.created != true || option.value == value
                    }
                    tomSelectJs?.addOption(obj {
                        this.value = value
                        this.text = value
                        this.created = true
                    })
                }
                tomSelectJs?.setValue(value)
            } else {
                tomSelectJs?.clear()
            }
        }
    }

    protected open fun refreshTomTypeahead() {
        if (tomSelectJs != null) {
            tomSelectJs?.destroy()
            tomSelectJs = null
            afterDestroyHooks?.forEach { it() }
        }
        getElement()?.let {
            tomSelectJs = TomSelectJs(it, getSettingsObj())
            refreshState()
            bindAllJQueryListeners()
            afterInsertHooks?.forEach { it(vnode!!) }
        }
    }

    override fun refresh() {
        super.refresh()
        window.setTimeout({ refreshTomTypeahead() }, 0)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.tomTypeaheadInput(
    options: List<String>? = null, type: InputType = InputType.TEXT, value: String? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    className: String? = null, init: (TomTypeaheadInput.() -> Unit)? = null
): TomTypeaheadInput {
    val tomTypeaheadInput = TomTypeaheadInput(options, type, value, tsCallbacks, className, init)
    this.add(tomTypeaheadInput)
    return tomTypeaheadInput
}
