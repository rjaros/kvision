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
import io.kvision.form.FormHorizontalRatio
import io.kvision.form.InvalidFeedback
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.SnOn
import org.w3c.dom.events.MouseEvent

/**
 * Checkbox style options.
 */
enum class CheckBoxStyle(override val className: String) : CssClass {
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
 * @param init an initializer extension function
 */
open class CheckBox(
    value: Boolean = false, name: String? = null, label: String? = null,
    rich: Boolean = false,
    init: (CheckBox.() -> Unit)? = null
) : SimplePanel(setOf("form-check", "abc-checkbox")), BoolFormControl, MutableState<Boolean> {

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
        this.id = this@CheckBox.idc
        this.name = name
    }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, classes = setOf("form-check-label"))
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
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
        classSetBuilder.add(style)
        if (circled) {
            classSetBuilder.add("abc-checkbox-circle")
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
fun Container.checkBox(
    value: Boolean = false, name: String? = null, label: String? = null,
    rich: Boolean = false, init: (CheckBox.() -> Unit)? = null
): CheckBox {
    val checkBox = CheckBox(value, name, label, rich, init)
    this.add(checkBox)
    return checkBox
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.checkBox(
    state: ObservableState<S>,
    value: Boolean = false, name: String? = null, label: String? = null,
    rich: Boolean = false, init: (CheckBox.(S) -> Unit)
) = checkBox(value, name, label, rich).bind(state, true, init)

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun CheckBox.bindTo(state: MutableState<Boolean>): CheckBox {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it)
    })
    return this
}
