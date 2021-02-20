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
package io.kvision.form.select

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.Widget
import io.kvision.form.FieldLabel
import io.kvision.form.InvalidFeedback
import io.kvision.form.StringFormControl
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.SnOn

/**
 * The form field component for Select control.
 *
 * The select control can be populated directly from *options* parameter or manually by adding
 * [SelectOption] or [SelectOptGroup] components to the container.
 *
 * @constructor
 * @param options an optional list of options (value to label pairs) for the select control
 * @param value selected value
 * @param name the name attribute of the generated HTML input element
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param ajaxOptions additional options for remote (AJAX) data source
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Select(
    options: List<StringPair>? = null, value: String? = null, name: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null, label: String? = null,
    rich: Boolean = false, init: (Select.() -> Unit)? = null
) : SimplePanel(setOf("form-group")), StringFormControl, MutableState<String?> {

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
     * Determines if multiple value selection is allowed.
     */
    var multiple
        get() = input.multiple
        set(value) {
            input.multiple = value
        }

    /**
     * Additional options for remote (AJAX) data source.
     */
    var ajaxOptions
        get() = input.ajaxOptions
        set(value) {
            input.ajaxOptions = value
        }

    /**
     * Maximal number of selected options.
     */
    var maxOptions
        get() = input.maxOptions
        set(value) {
            input.maxOptions = value
        }

    /**
     * Determines if live search is available.
     */
    var liveSearch
        get() = input.liveSearch
        set(value) {
            input.liveSearch = value
        }

    /**
     * The placeholder for the select control.
     */
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }

    /**
     * The style of the select control.
     */
    var style
        get() = input.style
        set(value) {
            input.style = value
        }

    /**
     * The width of the select control.
     */
    var selectWidth
        get() = input.selectWidth
        set(value) {
            input.selectWidth = value
        }

    /**
     * The width type of the select control.
     */
    var selectWidthType
        get() = input.selectWidthType
        set(value) {
            input.selectWidthType = value
        }

    /**
     * The dropdown align of the select control.
     */
    var dropdownAlign
        get() = input.dropdownAlign
        set(value) {
            input.dropdownAlign = value
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

    private val idc = "kv_form_select_$counter"
    final override val input: SelectInput = SelectInput(
        options, value, multiple, ajaxOptions,
        setOf("form-control")
    ).apply {
        this.id = this@Select.idc
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
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (validatorError != null) {
            classSetBuilder.add("text-danger")
            classSetBuilder.add("select-parent")
        }
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

    override fun disposeAll(): Container {
        input.disposeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return input.getChildren()
    }

    /**
     * Opens dropdown with options.
     */
    open fun showOptions() {
        input.showOptions()
    }

    /**
     * Hides dropdown with options.
     */
    open fun hideOptions() {
        input.hideOptions()
    }

    /**
     * Toggles visibility of dropdown with options.
     */
    open fun toggleOptions() {
        input.toggleOptions()
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

    override fun setState(state: String?) {
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
fun Container.select(
    options: List<StringPair>? = null, value: String? = null, name: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null, label: String? = null,
    rich: Boolean = false, init: (Select.() -> Unit)? = null
): Select {
    val select = Select(options, value, name, multiple, ajaxOptions, label, rich, init)
    this.add(select)
    return select
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.select(
    state: ObservableState<S>,
    options: List<StringPair>? = null, value: String? = null, name: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null, label: String? = null,
    rich: Boolean = false, init: (Select.(S) -> Unit)
) = select(options, value, name, multiple, ajaxOptions, label, rich).bind(state, true, init)

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun Select.bindTo(state: MutableState<String?>): Select {
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
fun Select.bindTo(state: MutableState<String>): Select {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it ?: "")
    })
    return this
}
