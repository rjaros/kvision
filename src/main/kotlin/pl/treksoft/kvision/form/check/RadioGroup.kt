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

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.form.InvalidFeedback
import pl.treksoft.kvision.form.StringFormControl
import pl.treksoft.kvision.form.ValidationStatus
import pl.treksoft.kvision.panel.SimplePanel

/**
 * The form field component rendered as a group of HTML *input type="radio"* elements with the same name attribute.
 *
 * The radio group can be populated directly from *options* parameter or manually by adding
 * [Radio] components to the container.
 *
 * @constructor
 * @param options an optional list of options (label to value pairs) for the group
 * @param value selected option
 * @param name the name attribute of the generated HTML input element
 * @param inline determines if the options are rendered inline
 * @param label label text of the options group
 * @param rich determines if [label] can contain HTML code
 */
@Suppress("TooManyFunctions")
open class RadioGroup(
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    label: String? = null,
    rich: Boolean = false
) : SimplePanel(setOf("form-group")), StringFormControl {

    /**
     * A list of options (label to value pairs) for the group.
     */
    var options by refreshOnUpdate(options) { setChildrenFromOptions() }

    /**
     * A value of the selected option.
     */
    override var value by refreshOnUpdate(value) { setValueToChildren(it) }

    /**
     * Determines if the options are rendered inline.
     */
    var inline by refreshOnUpdate(inline)

    override var disabled
        get() = getDisabledFromChildren()
        set(value) {
            setDisabledToChildren(value)
        }
    /**
     * The label text of the options group.
     */
    var label
        get() = flabel.content
        set(value) {
            flabel.content = value
        }
    /**
     * Determines if [label] can contain HTML code.
     */
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
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
    override var validatorError: String?
        get() = super.validatorError
        set(value) {
            super.validatorError = value
            if (value != null) {
                container.addCssClass("is-invalid")
            } else {
                container.removeCssClass("is-invalid")
            }
        }

    private val idc = "kv_form_radiogroup_$counter"
    final override val input = RadioInput()
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich)
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    internal val container = SimplePanel(setOf("kv-radiogroup-container"))

    init {
        this.addInternal(flabel)
        this.addInternal(container)
        this.addInternal(invalidFeedback)
        setChildrenFromOptions()
        setValueToChildren(value)
        setNameToChildren(name)
        counter++
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (validatorError != null) {
            cl.add("text-danger" to true)
        }
        if (inline) {
            cl.add("kv-radiogroup-inline" to true)
        } else {
            cl.add("kv-radiogroup" to true)
        }
        return cl
    }

    private fun setValueToChildren(value: String?) {
        val radios = container.getChildren().filterIsInstance<Radio>()
        radios.forEach { it.value = false }
        radios.find {
            it.extraValue == value
        }?.value = true
    }

    private fun getDisabledFromChildren(): Boolean {
        return container.getChildren().filterIsInstance<Radio>().firstOrNull()?.disabled ?: false
    }

    private fun setDisabledToChildren(disabled: Boolean) {
        container.getChildren().filterIsInstance<Radio>().forEach { it.disabled = disabled }
    }

    private fun getNameFromChildren(): String? {
        return container.getChildren().filterIsInstance<Radio>().firstOrNull()?.name ?: this.idc
    }

    private fun setNameToChildren(name: String?) {
        val tname = name ?: this.idc
        container.getChildren().filterIsInstance<Radio>().forEach { it.name = tname }
    }

    private fun getSizeFromChildren(): InputSize? {
        return container.getChildren().filterIsInstance<Radio>().firstOrNull()?.size
    }

    private fun setSizeToChildren(size: InputSize?) {
        container.getChildren().filterIsInstance<Radio>().forEach { it.size = size }
        super.size = size
    }

    private fun getValidationStatusFromChildren(): ValidationStatus? {
        return container.getChildren().filterIsInstance<Radio>().firstOrNull()?.validationStatus
    }

    private fun setValidationStatusToChildren(validationStatus: ValidationStatus?) {
        container.getChildren().filterIsInstance<Radio>().forEach { it.validationStatus = validationStatus }
    }

    private fun setChildrenFromOptions() {
        val currentName = this.name
        container.removeAll()
        options?.let {
            val tname = currentName ?: this.idc
            val tinline = this.inline
            val c = it.map {
                Radio(false, extraValue = it.first, label = it.second).apply {
                    inline = tinline
                    name = tname
                    eventTarget = this@RadioGroup
                    setEventListener<Radio> {
                        change = {
                            this@RadioGroup.value = self.extraValue
                        }
                    }
                }
            }
            container.addAll(c)
        }
    }

    override fun focus() {
        container.getChildren().filterIsInstance<Radio>().firstOrNull()?.focus()
    }

    override fun blur() {
        container.getChildren().filterIsInstance<Radio>().firstOrNull()?.blur()
    }

    override fun styleForHorizontalFormPanel() {
        addCssClass("row")
        flabel.addCssClass("col-sm-2")
        flabel.addCssClass("col-form-label")
        container.addCssClass("col-sm-10")
        invalidFeedback.addCssClass("offset-sm-2")
        invalidFeedback.addCssClass("col-sm-10")
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
fun Container.radioGroup(
    options: List<StringPair>? = null, value: String? = null, name: String? = null, inline: Boolean = false,
    label: String? = null, rich: Boolean = false, init: (RadioGroup.() -> Unit)? = null
): RadioGroup {
    val radioGroup = RadioGroup(options, value, name, inline, label, rich).apply { init?.invoke(this) }
    this.add(radioGroup)
    return radioGroup
}
