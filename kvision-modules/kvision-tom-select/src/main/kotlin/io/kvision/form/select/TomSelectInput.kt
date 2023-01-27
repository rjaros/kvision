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

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.Widget
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.form.select.js.TomSelectJs
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode
import io.kvision.state.MutableState
import io.kvision.utils.obj
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.asList

/**
 * The basic component for a select control based on Tom Select.
 *
 * @constructor
 * @param options a static list of options
 * @param value text input value
 * @param emptyOption determines if an empty option is automatically generated
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param selectSize the number of visible options
 * @param tsOptions Tom Select options
 * @param tsCallbacks Tom Select callbacks
 * @param tsRenders Tom Select render functions
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class TomSelectInput(
    options: List<StringPair>? = null, value: String? = null, emptyOption: Boolean = false,
    multiple: Boolean = false, selectSize: Int? = null, tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null, tsRenders: TomSelectRenders? = null,
    className: String? = null, init: (TomSelectInput.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "form-select"), GenericFormComponent<String?>, FormInput,
    MutableState<String?> {

    protected val observers = mutableListOf<(String?) -> Unit>()

    /**
     * A static list of options for a typeahead control
     */
    var options by refreshOnUpdate(options)

    /**
     * Text input value.
     */
    override var value by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(it) } }

    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()

    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false)

    /**
     * Determines if an empty option is automatically generated.
     */
    var emptyOption by refreshOnUpdate(emptyOption)

    /**
     * Determines if multiple value selection is allowed.
     */
    var multiple by refreshOnUpdate(multiple)

    /**
     * The number of visible options.
     */
    var selectSize: Int? by refreshOnUpdate(selectSize)

    /**
     * Tom Select options
     */
    var tsOptions by refreshOnUpdate(tsOptions)

    /**
     * Tom Select callbacks
     */
    var tsCallbacks by refreshOnUpdate(tsCallbacks)

    /**
     * Tom Select render functions
     */
    var tsRenders by refreshOnUpdate(tsRenders)

    /**
     * The placeholder for the input element.
     */
    var placeholder: String? by refreshOnUpdate()

    /**
     * Determines if the text input is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * The size of the input.
     */
    override var size: InputSize? by refreshOnUpdate()

    /**
     * The validation status of the input.
     */
    override var validationStatus: ValidationStatus? by refreshOnUpdate()

    /**
     * The native TomSelect object instance
     */
    var tomSelectJs: TomSelectJs? = null

    /**
     * The label of the currently selected option.
     */
    val selectedLabel: String?
        get() = getElement()?.unsafeCast<HTMLSelectElement>()?.options?.asList()?.find {
            it.unsafeCast<HTMLOptionElement>().value == this.value
        }?.textContent

    init {
        useSnabbdomDistinctKey()
        this.setInternalEventListener<TomSelectInput> {
            change = {
                val v = if (multiple) {
                    tomSelectJs?.getValue()?.unsafeCast<Array<String>>()?.joinToString(",")
                } else {
                    tomSelectJs?.getValue()?.unsafeCast<String>()
                }
                self.value = v?.ifBlank { null }
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("select", childrenVNodes())
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(validationStatus)
        size?.className?.let { classSetBuilder.add(it.replace("control", "select")) }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        if (multiple) {
            attributeSetBuilder.add("multiple")
        }
        selectSize?.let {
            attributeSetBuilder.add("size", "$it")
        }
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
        placeholder?.let {
            attributeSetBuilder.add("placeholder", it)
        }
        autofocus?.let {
            if (it) {
                attributeSetBuilder.add("autofocus")
            }
        }
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
            this.maxItems = if (!multiple) 1 else null
            this.maxOptions = selectSize
            this.allowEmptyOption = emptyOption
            if (options != null) {
                val optionsWithEmpty = if (emptyOption) {
                    listOf(StringPair("", "\u00a0")) + options!!
                } else {
                    options!!
                }
                this.options = optionsWithEmpty.map {
                    obj {
                        this.value = it.first
                        this.text = translate(it.second)
                    }
                }.toTypedArray()
                if (placeholder == null) this.controlInput = null
            }
            if (tsOptions != null) {
                val optionsObj = tsOptions!!.toJs(emptyOption)
                js("Object").assign(this, optionsObj)
            } else {
                this.plugins = arrayOf("change_listener")
            }
            if (tsCallbacks != null) {
                val callbackObj = tsCallbacks!!.toJs()
                js("Object").assign(this, callbackObj)
                if (tsCallbacks!!.load != null) {
                    this.load = { query: String, callback: (dynamic) -> Unit ->
                        tsCallbacks!!.load!!(query) { options ->
                            if (emptyOption) {
                                callback(arrayOf(obj {
                                    this.value = ""
                                    this.text = "\u00a0"
                                }) + options)
                            } else {
                                callback(options)
                            }
                        }
                    }
                }
            }
            if (tsRenders != null) {
                this.render = tsRenders!!.toJs()
            } else {
                this.render = obj {
                    no_results = null
                }
            }
        }
    }

    open fun refreshState() {
        if (tomSelectJs != null) {
            if (multiple) {
                if (value != null) {
                    tomSelectJs?.setValue(value!!.split(",").toTypedArray(), true)
                } else {
                    tomSelectJs?.clear(true)
                }
            } else {
                if (value != null) {
                    tomSelectJs?.setValue(value, true)
                } else {
                    tomSelectJs?.clear(true)
                }
            }
        }
    }

    protected open fun refreshTomSelect() {
        tomSelectJs?.destroy()
        tomSelectJs = null
        getElement()?.let {
            tomSelectJs = TomSelectJs(it.unsafeCast<HTMLElement>(), getSettingsObj())
            refreshState()
        }
    }

    override fun refresh() {
        super.refresh()
        window.setTimeout({ refreshTomSelect() }, 0)
    }

    override fun getState(): String? = value

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    override fun setState(state: String?) {
        value = state
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.tomSelectInput(
    options: List<StringPair>? = null, value: String? = null, emptyOption: Boolean = false,
    multiple: Boolean = false, selectSize: Int? = null, tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null, tsRenders: TomSelectRenders? = null,
    className: String? = null, init: (TomSelectInput.() -> Unit)? = null
): TomSelectInput {
    val tomSelectInput =
        TomSelectInput(
            options,
            value,
            emptyOption,
            multiple,
            selectSize,
            tsOptions,
            tsCallbacks,
            tsRenders,
            className,
            init
        )
    this.add(tomSelectInput)
    return tomSelectInput
}
