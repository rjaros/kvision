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
package io.kvision.form.check

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.core.Widget
import io.kvision.form.BoolFormControl
import io.kvision.form.FieldLabel
import io.kvision.form.FieldLabelCheck
import io.kvision.form.FormHorizontalRatio
import io.kvision.form.InvalidFeedback
import io.kvision.html.span
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.utils.SnOn
import org.w3c.dom.events.MouseEvent

/**
 * Radio style options.
 */
enum class RadioStyle(override val className: String) : CssClass {
    PRIMARY("abc-radio-primary"),
    SUCCESS("abc-radio-success"),
    INFO("abc-radio-info"),
    WARNING("abc-radio-warning"),
    DANGER("abc-radio-danger"),
}

/**
 * The form field component rendered as HTML *input type="radio"*.
 *
 * @constructor
 * @param value selection state
 * @param extraValue the additional String value used for the radio button group
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
open class Radio(
    value: Boolean = false, extraValue: String? = null, name: String? = null, label: String? = null,
    rich: Boolean = false, init: (Radio.() -> Unit)? = null
) : SimplePanel(className = "form-check"), BoolFormControl, MutableState<Boolean> {

    /**
     * The selection state of the radio button.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }

    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the input selection state.
     */
    var startValue
        get() = input.startValue
        set(value) {
            input.startValue = value
        }

    /**
     * The additional String value used for the radio button group.
     */
    var extraValue
        get() = input.extraValue
        set(value) {
            input.extraValue = value
        }

    /**
     * The label text bound to the input element.
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

    /**
     * The style (one of Bootstrap standard colors) of the input.
     */
    var style: RadioStyle? by refreshOnUpdate()

    /**
     * Determines if the radio button is rendered as a square.
     */
    var squared by refreshOnUpdate(false)

    /**
     * Determines if the radio button is rendered inline.
     */
    var inline by refreshOnUpdate(false)

    private val idc = "kv_form_radio_$counter"
    final override val input: RadioInput = RadioInput(value, className = "form-check-input").apply {
        this.id = this@Radio.idc
        this.extraValue = extraValue
        this.name = name
    }
    final override val flabel: FieldLabel = FieldLabelCheck(idc, label, rich, className = "form-check-label") {
        span()
    }
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this.eventTarget ?: this
        this.addPrivate(input)
        this.addPrivate(flabel)
        this.addPrivate(invalidFeedback)
        counter++
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Int {
        return input.setEventListener(block)
    }

    override fun removeEventListener(id: Int): Widget {
        input.removeEventListener(id)
        return this
    }

    override fun removeEventListeners(): Widget {
        input.removeEventListeners()
        return this
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (!squared) {
            classSetBuilder.add("abc-radio")
            classSetBuilder.add(style)
        } else {
            classSetBuilder.add("abc-checkbox")
            style?.let {
                classSetBuilder.add(it.className.replace("radio", "checkbox"))
            }
        }
        if (inline) {
            classSetBuilder.add("form-check-inline")
        }
        if (validatorError != null) {
            classSetBuilder.add("text-danger")
        }
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: Radio.(MouseEvent) -> Unit): Radio {
        this.setEventListener<Radio> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }

    override fun focus() {
        input.focus()
    }

    override fun blur() {
        input.blur()
    }

    override fun styleForHorizontalFormPanel(horizontalRatio: FormHorizontalRatio) {
        addCssClass("form-group")
        addCssClass("mb-3")
        addSurroundingCssClass("row")
        addCssClass("offset-sm-${horizontalRatio.labels}")
        addCssClass("col-sm-${horizontalRatio.fields}")
    }

    override fun styleForInlineFormPanel() {
        addCssClass("form-group")
    }

    override fun styleForVerticalFormPanel() {
        addCssClass("form-group")
        addCssClass("mb-3")
    }

    override fun getState(): Boolean = input.getState()

    override fun subscribe(observer: (Boolean) -> Unit): () -> Unit {
        return input.subscribe(observer)
    }

    override fun setState(state: Boolean) {
        input.setState(state)
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
fun Container.radio(
    value: Boolean = false, extraValue: String? = null, name: String? = null, label: String? = null,
    rich: Boolean = false, init: (Radio.() -> Unit)? = null
): Radio {
    val radio = Radio(value, extraValue, name, label, rich, init)
    this.add(radio)
    return radio
}
