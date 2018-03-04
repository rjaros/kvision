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
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.form.StringFormControl
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.SnOn

/**
 * The form field component for Select control.
 *
 * The select control can be populated directly from *options* parameter or manually by adding
 * [SelectOption] or [SelectOptGroup] components to the container.
 *
 * @constructor
 * @param options an optional list of options (label to value pairs) for the select control
 * @param value selected value
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param ajaxOptions additional options for remote (AJAX) data source
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
@Suppress("TooManyFunctions")
open class Select(
    options: List<StringPair>? = null, value: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null, label: String? = null,
    rich: Boolean = false
) : SimplePanel(setOf("form-group")), StringFormControl {

    /**
     * A list of options (label to value pairs) for the select control.
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
     * The name attribute of the generated HTML select element.
     */
    var name
        get() = input.name
        set(value) {
            input.name = value
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
     * Determines if an empty option is automatically generated.
     */
    var emptyOption
        get() = input.emptyOption
        set(value) {
            input.emptyOption = value
        }
    override var disabled
        get() = input.disabled
        set(value) {
            input.disabled = value
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
    override var size
        get() = input.size
        set(value) {
            input.size = value
        }

    private val idc = "kv_form_select_$counter"
    final override val input: SelectInput = SelectInput(
        options, value, multiple, ajaxOptions,
        setOf("form-control")
    ).apply { id = idc }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich)
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(flabel)
        this.addInternal(input)
        this.addInternal(validationInfo)
        counter++
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (validatorError != null) {
            cl.add("has-error" to true)
        }
        return cl
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Widget {
        input.setEventListener(block)
        return this
    }

    override fun setEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        input.setEventListener(block)
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

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.select(
            options: List<StringPair>? = null, value: String? = null,
            multiple: Boolean = false, ajaxOptions: AjaxOptions? = null, label: String? = null,
            rich: Boolean = false, init: (Select.() -> Unit)? = null
        ): Select {
            val select = Select(options, value, multiple, ajaxOptions, label, rich).apply { init?.invoke(this) }
            this.add(select)
            return select
        }
    }
}
