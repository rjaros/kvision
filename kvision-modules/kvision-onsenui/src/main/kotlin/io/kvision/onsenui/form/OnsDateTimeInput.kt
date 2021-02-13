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

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.FormInput
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.state.MutableState
import io.kvision.state.bind
import io.kvision.types.toDateF
import io.kvision.types.toStringF
import io.kvision.utils.set
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
) : Widget(classes + "kv-ons-form-control"), FormInput, MutableState<Date?> {

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

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(validationStatus)
        classSetBuilder.add(size)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add(
            "type",
            when (mode) {
                DateTimeMode.DATE -> "date"
                DateTimeMode.TIME -> "time"
                DateTimeMode.DATETIME -> "datetime-local"
            }
        )
        startValue?.let {
            attributeSetBuilder.add("value", it.toStringF(mode.format))
        }
        min?.let {
            attributeSetBuilder.add("min", it.toStringF(mode.format))
        }
        max?.let {
            attributeSetBuilder.add("max", it.toStringF(mode.format))
        }
        step?.let {
            attributeSetBuilder.add("step", "$it")
        }
        inputId?.let {
            attributeSetBuilder.add("input-id", it)
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        autofocus?.let {
            if (it) {
                attributeSetBuilder.add("autofocus")
            }
        }
        readonly?.let {
            if (it) {
                attributeSetBuilder.add("readonly")
            }
        }
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
        autocomplete?.let {
            attributeSetBuilder.add("autocomplete", if (it) "on" else "off")
        }
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
        if (v != null && v != "") {
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

    override fun getState(): Date? = value

    override fun subscribe(observer: (Date?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    override fun setState(state: Date?) {
        value = state
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

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun OnsDateTimeInput.bindTo(state: MutableState<Date?>): OnsDateTimeInput {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it)
    })
    return this
}

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun OnsDateTimeInput.bindTo(state: MutableState<Date>): OnsDateTimeInput {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it ?: Date())
    })
    return this
}
