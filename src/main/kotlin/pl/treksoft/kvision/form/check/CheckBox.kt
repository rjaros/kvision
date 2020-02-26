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

import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.BoolFormControl
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.FormHorizontalRatio
import pl.treksoft.kvision.form.InvalidFeedback
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.SnOn

/**
 * Checkbox style options.
 */
enum class CheckBoxStyle(internal val className: String) {
    PRIMARY("abc-checkbox-primary"),
    SUCCESS("abc-checkbox-success"),
    INFO("abc-checkbox-info"),
    WARNING("abc-checkbox-warning"),
    DANGER("abc-checkbox-danger"),
}

/**
 * The form field component rendered as HTML *input type="checkbox"*.
 *
 * @constructor
 * @param value selection state
 * @param name the name of the input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
open class CheckBox(
    value: Boolean = false, name: String? = null, label: String? = null,
    rich: Boolean = false
) : SimplePanel(setOf("form-check", "abc-checkbox")), BoolFormControl {

    /**
     * The selection state of the checkbox.
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
    var style: CheckBoxStyle? by refreshOnUpdate()
    /**
     * Determines if the checkbox is rendered as a circle.
     */
    var circled by refreshOnUpdate(false)
    /**
     * Determines if the checkbox is rendered inline.
     */
    var inline by refreshOnUpdate(false)

    private val idc = "kv_form_checkbox_$counter"
    final override val input: CheckBoxInput = CheckBoxInput(value, classes = setOf("form-check-input")).apply {
        this.id = idc
        this.name = name
    }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, classes = setOf("form-check-label"))
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(input)
        this.addInternal(flabel)
        this.addInternal(invalidFeedback)
        counter++
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Int {
        return input.setEventListener(block)
    }

    @Deprecated(
        "Use onEvent extension function instead.",
        ReplaceWith("onEvent(block)", "pl.treksoft.kvision.core.onEvent")
    )
    override fun setEventListener(block: SnOn<Widget>.() -> Unit): Int {
        @Suppress("DEPRECATION")
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

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        style?.let {
            cl.add(it.className to true)
        }
        if (circled) {
            cl.add("abc-checkbox-circle" to true)
        }
        if (inline) {
            cl.add("form-check-inline" to true)
        }
        if (validatorError != null) {
            cl.add("text-danger" to true)
        }
        return cl
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: CheckBox.(MouseEvent) -> Unit): CheckBox {
        this.setEventListener<CheckBox> {
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
        addSurroundingCssClass("row")
        addCssClass("offset-sm-${horizontalRatio.labels}")
        addCssClass("col-sm-${horizontalRatio.fields}")
    }

    override fun styleForInlineFormPanel() {
        addCssClass("form-group")
    }

    override fun styleForVerticalFormPanel() {
        addCssClass("form-group")
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
fun Container.checkBox(
    value: Boolean = false, name: String? = null, label: String? = null,
    rich: Boolean = false, init: (CheckBox.() -> Unit)? = null
): CheckBox {
    val checkBox = CheckBox(value, name, label, rich).apply { init?.invoke(this) }
    this.add(checkBox)
    return checkBox
}
