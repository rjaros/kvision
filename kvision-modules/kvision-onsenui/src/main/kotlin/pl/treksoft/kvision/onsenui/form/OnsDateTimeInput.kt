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

package pl.treksoft.kvision.onsenui.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FormInput
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.form.ValidationStatus
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.types.toDateF
import pl.treksoft.kvision.types.toStringF
import pl.treksoft.kvision.utils.set
import kotlin.js.Date

/**
 * Data/time input modes.
 */
enum class DateTimeMode(internal val format: String) {
    DATE("YYYY-MM-DD"),
    TIME("HH:mm"),
    DATETIME("YYYY-MM-DDTHH:mm")
}

/**
 * OnsenUI date/time input component.
 *
 * @constructor Creates a date/time input component.
 * @param value date/time input value
 * @param mode date/time input mode
 * @param min minimal value
 * @param max maximal value
 * @param step step value
 * @param inputId the ID of the input element
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsDateTimeInput(
    value: Date? = null,
    mode: DateTimeMode = DateTimeMode.DATETIME,
    min: Date? = null,
    max: Date? = null,
    step: Number? = null,
    inputId: String? = null,
    classes: Set<String> = setOf(),
    init: (OnsDateTimeInput.() -> Unit)? = null
) : Widget(classes + "kv-ons-form-control"), FormInput, ObservableState<Date?> {

    protected val observers = mutableListOf<(Date?) -> Unit>()

    /**
     * Date/time input value.
     */
    var value by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(it) } }

    /**
     * Date/time mode.
     */
    var mode by refreshOnUpdate(mode)

    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the date/time input value.
     */
    var startValue by refreshOnUpdate(value) { this.value = it; refresh() }

    /**
     * Minimal date/time value.
     */
    var min by refreshOnUpdate(min)

    /**
     * Maximal date/time value.
     */
    var max by refreshOnUpdate(max)

    /**
     * Step value.
     */
    var step by refreshOnUpdate(step)

    /**
     * The ID of the input element.
     */
    var inputId: String? by refreshOnUpdate(inputId)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()

    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false)

    /**
     * Determines if the date/time input is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * Determines if the date/time input is read-only.
     */
    var readonly: Boolean? by refreshOnUpdate()

    /**
     * The size of the input.
     */
    override var size: InputSize? by refreshOnUpdate()

    /**
     * The validation status of the input.
     */
    override var validationStatus: ValidationStatus? by refreshOnUpdate()

    /**
     * Determines if autocomplete is enabled for the input element.
     */
    var autocomplete: Boolean? by refreshOnUpdate()

    init {
        this.setInternalEventListener<OnsDateTimeInput> {
            input = {
                self.changeValue()
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-input")
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        validationStatus?.let {
            cl.add(it.className to true)
        }
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        when (mode) {
            DateTimeMode.DATE -> sn.add("type" to "date")
            DateTimeMode.TIME -> sn.add("type" to "time")
            DateTimeMode.DATETIME -> sn.add("type" to "datetime-local")
        }
        startValue?.let {
            sn.add("value" to it.toStringF(mode.format))
        }
        min?.let {
            sn.add("min" to it.toStringF(mode.format))
        }
        max?.let {
            sn.add("max" to it.toStringF(mode.format))
        }
        step?.let {
            sn.add("step" to it.toString())
        }
        inputId?.let {
            sn.add("input-id" to it)
        }
        modifier?.let {
            sn.add("modifier" to it)
        }
        name?.let {
            sn.add("name" to it)
        }
        autofocus?.let {
            if (it) {
                sn.add("autofocus" to "autofocus")
            }
        }
        readonly?.let {
            if (it) {
                sn.add("readonly" to "readonly")
            }
        }
        if (disabled) {
            sn.add("disabled" to "disabled")
        }
        autocomplete?.let {
            if (it) {
                sn.add("autocomplete" to "on")
            } else {
                sn.add("autocomplete" to "off")
            }
        }
        return sn
    }

    override fun afterInsert(node: VNode) {
        refreshState()
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun refreshState() {
        value?.let {
            getElementJQuery()?.`val`(it.toStringF(mode.format))
        } ?: getElementJQueryD()?.`val`(null)
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun changeValue() {
        val v = getElementJQuery()?.`val`() as String?
        if (v != null && v.isNotEmpty()) {
            this.value = v.toDateF(mode.format)
        } else {
            this.value = null
        }
    }

    /**
     * Returns the value of the date/time input as a String.
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.toStringF(mode.format)
    }

    /**
     * Makes the input element focused.
     */
    override fun focus() {
        getElementJQuery()?.focus()
    }

    /**
     * Makes the input element blur.
     */
    override fun blur() {
        getElementJQuery()?.blur()
    }

    override fun getState(): Date? = value

    override fun subscribe(observer: (Date?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsDateTimeInput(
    value: Date? = null,
    mode: DateTimeMode = DateTimeMode.DATETIME,
    min: Date? = null,
    max: Date? = null,
    step: Number? = null,
    inputId: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsDateTimeInput.() -> Unit)? = null
): OnsDateTimeInput {
    val onsDateTimeInput =
        OnsDateTimeInput(value, mode, min, max, step, inputId, classes ?: className.set, init)
    this.add(onsDateTimeInput)
    return onsDateTimeInput
}
