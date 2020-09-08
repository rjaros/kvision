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
package pl.treksoft.kvision.form.select

import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.InvalidFeedback
import pl.treksoft.kvision.form.StringFormControl
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.utils.SnOn

/**
 * The form field component for SimpleSelect control.
 *
 * @constructor
 * @param options an optional list of options (value to label pairs) for the select control
 * @param value selected value
 * @param emptyOption determines if an empty option is automatically generated
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param selectSize the number of visible options
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
@Suppress("TooManyFunctions")
open class SimpleSelect(
    options: List<StringPair>? = null, value: String? = null, emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    name: String? = null, label: String? = null, rich: Boolean = false
) : SimplePanel(setOf("form-group")), StringFormControl, ObservableState<String?> {

    /**
     * A list of options (value to label pairs) for the select control.
     */
    var options
        get() = input.options
        set(value) {
            input.options = value
        }

    /**
     * A value of the selected option.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }

    /**
     * The value of the selected child option.
     *
     * This value is placed directly in the generated HTML code, while the [value] property is dynamically
     * bound to the select component.
     */
    var startValue
        get() = input.startValue
        set(value) {
            input.startValue = value
        }

    /**
     * Determines if an empty option is automatically generated.
     */
    var emptyOption
        get() = input.emptyOption
        set(value) {
            input.emptyOption = value
        }

    /**
     * Determines if multiple value selection is allowed.
     */
    var multiple
        get() = input.multiple
        set(value) {
            input.multiple = value
        }

    /**
     * The number of visible options.
     */
    var selectSize
        get() = input.selectSize
        set(value) {
            input.selectSize = value
        }

    /**
     * Determines if the select is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }

    /**
     * The label text bound to the select element.
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
     * The index of currently selected option or -1 if none.
     */
    var selectedIndex
        get() = input.selectedIndex
        set(value) {
            input.selectedIndex = value
        }

    private val idc = "kv_form_simpleselect_$counter"
    final override val input: SimpleSelectInput = SimpleSelectInput(
        options, value, emptyOption, multiple, selectSize
    ).apply {
        this.id = idc
        this.name = name
    }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, setOf("control-label"))
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        counter++
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (validatorError != null) {
            cl.add("text-danger" to true)
        }
        return cl
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

    override fun add(child: Component): SimplePanel {
        input.add(child)
        return this
    }

    override fun addAll(children: List<Component>): SimplePanel {
        input.addAll(children)
        return this
    }

    override fun remove(child: Component): SimplePanel {
        input.remove(child)
        return this
    }

    override fun removeAll(): SimplePanel {
        input.removeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return input.getChildren()
    }

    override fun focus() {
        input.focus()
    }

    override fun blur() {
        input.blur()
    }

    override fun getState(): String? = input.getState()

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        return input.subscribe(observer)
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
fun Container.simpleSelect(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    init: (SimpleSelect.() -> Unit)? = null
): SimpleSelect {
    val simpleSelect =
        SimpleSelect(options, value, emptyOption, multiple, selectSize, name, label, rich).apply { init?.invoke(this) }
    this.add(simpleSelect)
    return simpleSelect
}
