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

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.Widget
import io.kvision.form.FieldLabel
import io.kvision.form.InvalidFeedback
import io.kvision.form.StringFormControl
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableState
import io.kvision.utils.SnOn
import io.kvision.utils.set

/**
 * Onsen UI form field select component.
 *
 * @constructor Creates a form field select component.
 * @param options an optional list of options (label to value pairs) for the group
 * @param value selected option
 * @param emptyOption determines if an empty option is automatically generated
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param selectSize the number of visible options
 * @param name the name attribute of the generated HTML input element
 * @param label label text of the options group
 * @param rich determines if [label] can contain HTML code
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsSelect(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    classes: Set<String> = setOf(),
    init: (OnsSelect.() -> Unit)? = null
) : SimplePanel(classes + setOf("form-group", "kv-ons-form-group")), StringFormControl, ObservableState<String?> {
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
     * A modifier attribute to specify custom styles.
     */
    var modifier
        get() = input.modifier
        set(value) {
            input.modifier = value
        }

    private val idc = "kv_ons_form_select_$counter"
    final override val input: OnsSelectInput =
        OnsSelectInput(options, value, emptyOption, multiple, selectSize, idc).apply {
            modifier = "underbar"
            this.name = name
            this.eventTarget = this@OnsSelect
        }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, setOf("control-label"))
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        @Suppress("LeakingThis")
        init?.invoke(this)
        counter++
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (validatorError != null) {
            classSetBuilder.add("text-danger")
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

    override fun disposeAll(): SimplePanel {
        input.disposeAll()
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
fun Container.onsSelect(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsSelect.() -> Unit)? = null
): OnsSelect {
    val onsSelect =
        OnsSelect(options, value, emptyOption, multiple, selectSize, name, label, rich, classes ?: className.set, init)
    this.add(onsSelect)
    return onsSelect
}
