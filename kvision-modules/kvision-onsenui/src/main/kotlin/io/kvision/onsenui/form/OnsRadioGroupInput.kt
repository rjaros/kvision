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
package io.kvision.onsenui.form

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.utils.obj

/**
 * The input component rendered as a group of Onsen UI radio buttons with the same name attribute.
 *
 * The radio group can be populated directly from *options* parameter or manually by adding
 * [OnsRadio] components to the container.
 *
 * @constructor
 * @param options an optional list of options (label to value pairs) for the group
 * @param value selected option
 * @param name the name attribute of the generated HTML input element
 */
open class OnsRadioGroupInput(
    options: List<StringPair>? = null, value: String? = null, name: String? = null,
    className: String? = null,
    init: (OnsRadioGroupInput.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "form-group mb-3 kv-ons-form-group"), GenericFormComponent<String?>,
    FormInput,
    MutableState<String?> {

    protected val observers = mutableListOf<(String?) -> Unit>()

    /**
     * A list of options (label to value pairs) for the group.
     */
    var options by refreshOnUpdate(options) { setChildrenFromOptions() }

    /**
     * A value of the selected option.
     */
    override var value by refreshOnUpdate(value) {
        setValueToChildren(it)
        observers.forEach { ob -> ob(it) }
        @Suppress("UnsafeCastFromDynamic")
        dispatchEvent("change", obj { detail = obj { data = it } })
    }

    override var disabled
        get() = getDisabledFromChildren()
        set(value) {
            setDisabledToChildren(value)
        }

    override var name: String?
        get() = getNameFromChildren()
        set(value) {
            setNameToChildren(value)
        }

    override var validationStatus
        get() = getValidationStatusFromChildren()
        set(value) {
            setValidationStatusToChildren(value)
        }

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier
        get() = getModifierFromChildren()
        set(value) {
            setModifierToChildren(value)
        }

    override var size: InputSize? by refreshOnUpdate()

    private val idc = "kv_ons_form_radiogroup_$counter"

    init {
        setChildrenFromOptions()
        setValueToChildren(value)
        setNameToChildren(name)
        @Suppress("LeakingThis")
        init?.invoke(this)
        counter++
    }

    private fun setValueToChildren(value: String?) {
        val radios = getChildren().filterIsInstance<OnsRadio>()
        radios.forEach { it.value = false }
        radios.find {
            it.extraValue == value
        }?.value = true
    }

    private fun getDisabledFromChildren(): Boolean {
        return getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.disabled ?: false
    }

    private fun setDisabledToChildren(disabled: Boolean) {
        getChildren().filterIsInstance<OnsRadio>().forEach { it.disabled = disabled }
    }

    private fun getNameFromChildren(): String {
        return getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.name ?: this.idc
    }

    private fun setNameToChildren(name: String?) {
        val tname = name ?: this.idc
        getChildren().filterIsInstance<OnsRadio>().forEach { it.name = tname }
    }

    private fun getValidationStatusFromChildren(): ValidationStatus? {
        return getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.validationStatus
    }

    private fun setValidationStatusToChildren(validationStatus: ValidationStatus?) {
        getChildren().filterIsInstance<OnsRadio>().forEach { it.validationStatus = validationStatus }
    }

    private fun getModifierFromChildren(): String? {
        return getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.modifier
    }

    private fun setModifierToChildren(modifier: String?) {
        getChildren().filterIsInstance<OnsRadio>().forEach { it.modifier = modifier }
    }

    private fun setChildrenFromOptions() {
        val currentName = this.name
        super.removeAll()
        options?.let {
            val tname = currentName ?: this.idc
            val c = it.map {
                OnsRadio(false, extraValue = it.first, label = it.second).apply {
                    name = tname
                    eventTarget = this@OnsRadioGroupInput
                    setEventListener<OnsRadio> {
                        change = { ev ->
                            this@OnsRadioGroupInput.value = self.extraValue
                            ev.stopPropagation()
                        }
                    }
                }
            }
            super.addAll(c)
        }
    }

    override fun add(child: Component): SimplePanel {
        if (child is OnsRadio) {
            child.eventTarget = this
            child.name = name
            child.setEventListener<OnsRadio> {
                change = { ev ->
                    this@OnsRadioGroupInput.value = self.extraValue
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
        getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.focus()
    }

    override fun blur() {
        getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.blur()
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

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsRadioGroupInput(
    options: List<StringPair>? = null, value: String? = null, name: String? = null,
    className: String? = null,
    init: (OnsRadioGroupInput.() -> Unit)? = null
): OnsRadioGroupInput {
    val onsRadioGroupInput = OnsRadioGroupInput(options, value, name, className, init)
    this.add(onsRadioGroupInput)
    return onsRadioGroupInput
}
