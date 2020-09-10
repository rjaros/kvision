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
package pl.treksoft.kvision.form.check

import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.form.FormInput
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.form.ValidationStatus
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.obj

/**
 * The input component rendered as a group of HTML *input type="radio"* elements with the same name attribute.
 *
 * The radio group can be populated directly from *options* parameter or manually by adding
 * [Radio] components to the container.
 *
 * @constructor
 * @param options an optional list of options (label to value pairs) for the group
 * @param value selected option
 * @param name the name attribute of the generated HTML input element
 * @param inline determines if the options are rendered inline
 */
@Suppress("TooManyFunctions")
open class RadioGroupInput(
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false
) : SimplePanel(setOf("form-group")), FormInput, ObservableState<String?> {

    protected val observers = mutableListOf<(String?) -> Unit>()

    /**
     * A list of options (label to value pairs) for the group.
     */
    var options by refreshOnUpdate(options) { setChildrenFromOptions() }

    /**
     * A value of the selected option.
     */
    var value by refreshOnUpdate(value) {
        setValueToChildren(it)
        observers.forEach { ob -> ob(it) }
        @Suppress("UnsafeCastFromDynamic")
        dispatchEvent("change", obj { detail = obj { data = it } })
    }

    /**
     * Determines if the options are rendered inline.
     */
    var inline by refreshOnUpdate(inline)

    override var disabled
        get() = getDisabledFromChildren()
        set(value) {
            setDisabledToChildren(value)
        }
    override var name
        get() = getNameFromChildren()
        set(value) {
            setNameToChildren(value)
        }
    override var size
        get() = getSizeFromChildren()
        set(value) {
            setSizeToChildren(value)
        }
    override var validationStatus
        get() = getValidationStatusFromChildren()
        set(value) {
            setValidationStatusToChildren(value)
        }

    private val idc = "kv_form_radiogroup_$counter"

    init {
        setChildrenFromOptions()
        setValueToChildren(value)
        setNameToChildren(name)
        counter++
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (inline) {
            cl.add("kv-radiogroup-inline" to true)
        } else {
            cl.add("kv-radiogroup" to true)
        }
        return cl
    }

    private fun setValueToChildren(value: String?) {
        val radios = getChildren().filterIsInstance<Radio>()
        radios.forEach { it.value = false }
        radios.find {
            it.extraValue == value
        }?.value = true
    }

    private fun getDisabledFromChildren(): Boolean {
        return getChildren().filterIsInstance<Radio>().firstOrNull()?.disabled ?: false
    }

    private fun setDisabledToChildren(disabled: Boolean) {
        getChildren().filterIsInstance<Radio>().forEach { it.disabled = disabled }
    }

    private fun getNameFromChildren(): String? {
        return getChildren().filterIsInstance<Radio>().firstOrNull()?.name ?: this.idc
    }

    private fun setNameToChildren(name: String?) {
        val tname = name ?: this.idc
        getChildren().filterIsInstance<Radio>().forEach { it.name = tname }
    }

    private fun getSizeFromChildren(): InputSize? {
        return getChildren().filterIsInstance<Radio>().firstOrNull()?.size
    }

    private fun setSizeToChildren(size: InputSize?) {
        getChildren().filterIsInstance<Radio>().forEach { it.size = size }
    }

    private fun getValidationStatusFromChildren(): ValidationStatus? {
        return getChildren().filterIsInstance<Radio>().firstOrNull()?.validationStatus
    }

    private fun setValidationStatusToChildren(validationStatus: ValidationStatus?) {
        getChildren().filterIsInstance<Radio>().forEach { it.validationStatus = validationStatus }
    }

    private fun setChildrenFromOptions() {
        val currentName = this.name
        super.removeAll()
        options?.let {
            val tname = currentName ?: this.idc
            val tinline = this.inline
            val c = it.map {
                Radio(false, extraValue = it.first, label = it.second).apply {
                    inline = tinline
                    name = tname
                    eventTarget = this@RadioGroupInput
                    setEventListener<Radio> {
                        change = { ev ->
                            this@RadioGroupInput.value = self.extraValue
                            ev.stopPropagation()
                        }
                    }
                }
            }
            super.addAll(c)
        }
    }

    override fun add(child: Component): SimplePanel {
        if (child is Radio) {
            child.eventTarget = this
            child.name = name
            child.setEventListener<Radio> {
                change = { ev ->
                    this@RadioGroupInput.value = self.extraValue
                    ev.stopPropagation()
                }
            }
        }
        super.add(child)
        return this
    }

    override fun addAll(children: List<Component>): SimplePanel {
        children.forEach { add(it) }
        return this
    }

    override fun focus() {
        getChildren().filterIsInstance<Radio>().firstOrNull()?.focus()
    }

    override fun blur() {
        getChildren().filterIsInstance<Radio>().firstOrNull()?.blur()
    }

    override fun getState(): String? = value

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.radioGroupInput(
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    init: (RadioGroupInput.() -> Unit)? = null
): RadioGroupInput {
    val radioGroupInput = RadioGroupInput(options, value, name, inline).apply { init?.invoke(this) }
    this.add(radioGroupInput)
    return radioGroupInput
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.radioGroupInput(
    state: ObservableState<S>,
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    init: (RadioGroupInput.(S) -> Unit)
) = radioGroupInput(options, value, name, inline).bind(state, true, init)
