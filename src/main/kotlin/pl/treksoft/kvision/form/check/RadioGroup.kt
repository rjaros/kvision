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
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.form.StringFormControl
import pl.treksoft.kvision.form.select.Select
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
 * @param inline determines if the options are rendered inline
 * @param label label text of the options group
 * @param rich determines if [label] can contain HTML code
 */
open class RadioGroup(
    options: List<StringPair>? = null, value: String? = null, inline: Boolean = false,
    label: String? = null,
    rich: Boolean = false
) : SimplePanel(setOf("form-group")), StringFormControl {

    /**
     * A list of options (label to value pairs) for the group.
     */
    var options by refreshOnUpdate(options, { setChildrenFromOptions() })

    /**
     * A value of the selected option.
     */
    override var value by refreshOnUpdate(value, { setValueToChildren(it) })

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
        get() = flabel.text
        set(value) {
            flabel.text = value
        }
    /**
     * Determines if [label] can contain HTML code.
     */
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
        }
    override var size: InputSize? = null

    private val idc = "kv_form_radiogroup_" + Select.counter
    final override val input = Widget()
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich)
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        setChildrenFromOptions()
        setValueToChildren(value)
        counter++
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (validatorError != null) {
            cl.add("has-error" to true)
        }
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

    private fun setChildrenFromOptions() {
        super.removeAll()
        this.addInternal(flabel)
        options?.let {
            val tidc = this.idc
            val tinline = this.inline
            val c = it.map {
                Radio(false, extraValue = it.first, label = it.second).apply {
                    inline = tinline
                    name = tidc
                    eventTarget = this@RadioGroup
                    setEventListener<Radio> {
                        change = {
                            this@RadioGroup.value = self.extraValue
                        }
                    }
                }
            }
            super.addAll(c)
        }
        this.addInternal(validationInfo)
    }

    override fun focus() {
        getChildren().filterIsInstance<Radio>().firstOrNull()?.focus()
    }

    override fun blur() {
        getChildren().filterIsInstance<Radio>().firstOrNull()?.blur()
    }

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.radioGroup(
            options: List<StringPair>? = null, value: String? = null, inline: Boolean = false,
            label: String? = null, rich: Boolean = false, init: (RadioGroup.() -> Unit)? = null
        ): RadioGroup {
            val radioGroup = RadioGroup(options, value, inline, label, rich).apply { init?.invoke(this) }
            this.add(radioGroup)
            return radioGroup
        }
    }
}
