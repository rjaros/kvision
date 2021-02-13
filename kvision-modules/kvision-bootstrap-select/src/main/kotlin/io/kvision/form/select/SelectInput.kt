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

import com.github.snabbdom.VNode
import io.kvision.KVManagerSelect.KVNULL
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.StringPair
import io.kvision.form.FormInput
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.html.ButtonStyle
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.asString
import io.kvision.utils.obj
import io.kvision.utils.set

/**
 * Select width types. See [Bootstrap Select width](http://silviomoreto.github.io/bootstrap-select/examples/#width).
 */
enum class SelectWidthType(internal val value: String) {
    AUTO("auto"),
    FIT("fit")
}

/**
 * Select dropdown align. See [Bootstrap Select width](http://silviomoreto.github.io/bootstrap-select/examples/#width).
 */
enum class SelectDropdownAlign {
    AUTO,
    LEFT,
    RIGHT
}

/**
 * The basic component for Select control.
 *
 * The select control can be populated directly from *options* parameter or manually by adding
 * [SelectOption] or [SelectOptGroup] components to the container.
 *
 * @constructor
 * @param options an optional list of options (value to label pairs) for the select control
 * @param value selected value
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param ajaxOptions additional options for remote (AJAX) data source
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class SelectInput(
    options: List<StringPair>? = null, value: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null,
    classes: Set<String> = setOf(), init: (SelectInput.() -> Unit)? = null
) : SimplePanel(classes), FormInput, MutableState<String?> {

    protected val observers = mutableListOf<(String?) -> Unit>()

    /**
     * A list of options (value to label pairs) for the select control.
     */
    var options by refreshOnUpdate(options) { setChildrenFromOptions() }

    /**
     * A value of the selected option.
     */
    var value by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(it) } }

    /**
     * The name attribute of the generated HTML select element.
     */
    override var name: String? by refreshOnUpdate()

    /**
     * Determines if multiple value selection is allowed.
     */
    var multiple by refreshOnUpdate(multiple)

    /**
     * Additional options for remote (AJAX) data source.
     */
    var ajaxOptions by refreshOnUpdate(ajaxOptions) {
        if (it != null) {
            liveSearch = true
        }
        refresh()
    }

    /**
     * Maximal number of selected options.
     */
    var maxOptions: Int? by refreshOnUpdate()

    /**
     * Determines if live search is available.
     */
    var liveSearch by refreshOnUpdate(false)

    /**
     * The placeholder for the select control.
     */
    var placeholder: String? by refreshOnUpdate()

    /**
     * The style of the select control.
     */
    var style: ButtonStyle? by refreshOnUpdate()

    /**
     * The width of the select control.
     */
    var selectWidth: CssSize? by refreshOnUpdate()

    /**
     * The width type of the select control.
     */
    var selectWidthType: SelectWidthType? by refreshOnUpdate()

    /**
     * The dropdown align of the select control.
     */
    var dropdownAlign by refreshOnUpdate(SelectDropdownAlign.LEFT)

    /**
     * Determines if an empty option is automatically generated.
     */
    var emptyOption by refreshOnUpdate(false) { setChildrenFromOptions() }

    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false) { refresh(); refreshSelectInput() }

    /**
     * Determines if the select is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * The size of the input.
     */
    override var size: InputSize? by refreshOnUpdate()

    /**
     * The validation status of the input.
     */
    override var validationStatus: ValidationStatus? by refreshOnUpdate()

    /**
     * The index of currently selected option or -1 if none.
     */
    @Suppress("UnsafeCastFromDynamic")
    var selectedIndex: Int
        get() = getElement()?.asDynamic()?.selectedIndex
            ?: value?.let { v ->
                val emptyIndex = if (emptyOption) 1 else 0
                options?.map(StringPair::first)?.indexOf(v)?.let { it + emptyIndex }
            } ?: -1
        set(value) {
            getElement()?.asDynamic()?.selectedIndex = value
            if (value == -1) this.value = null
            options?.getOrNull(value)?.let {
                this.value = it.first
            }
        }

    init {
        setChildrenFromOptions()
        this.setInternalEventListener<SelectInput> {
            change = {
                val v = getElementJQuery()?.`val`()
                self.value = v?.let {
                    calculateValue(it)
                }
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    private fun calculateValue(v: Any): String? {
        return if (this.multiple) {
            @Suppress("UNCHECKED_CAST")
            val arr = v as? Array<String>
            if (arr != null && arr.isNotEmpty()) {
                arr.filter { it != "" }.joinToString(",")
            } else {
                null
            }
        } else {
            val vs = v as String
            if (KVNULL == vs || vs == "") {
                null
            } else {
                vs
            }
        }
    }

    override fun render(): VNode {
        return render("select", childrenVNodes())
    }

    override fun add(child: Component): SimplePanel {
        super.add(child)
        refreshSelectInput()
        return this
    }

    override fun addAll(children: List<Component>): SimplePanel {
        super.addAll(children)
        refreshSelectInput()
        return this
    }

    override fun remove(child: Component): SimplePanel {
        super.remove(child)
        refreshSelectInput()
        return this
    }

    override fun removeAll(): SimplePanel {
        super.removeAll()
        refreshSelectInput()
        return this
    }

    private fun setChildrenFromOptions() {
        if (ajaxOptions == null) {
            super.removeAll()
            if (emptyOption) {
                super.add(SelectOption(KVNULL, ""))
            }
            options?.let {
                val c = it.map {
                    SelectOption(it.first, it.second)
                }
                super.addAll(c)
            }
        }
        this.refreshSelectInput()
    }

    /**
     * Opens dropdown with options.
     */
    open fun showOptions() {
        getElementJQueryD()?.selectpicker("show")
    }

    /**
     * Hides dropdown with options.
     */
    open fun hideOptions() {
        getElementJQueryD()?.selectpicker("hide")
    }

    /**
     * Toggles visibility of dropdown with options.
     */
    open fun toggleOptions() {
        getElementJQueryD()?.selectpicker("toggle")
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add("selectpicker")
        classSetBuilder.add(validationStatus)
        classSetBuilder.add(size)
    }

    protected fun refreshSelectInput() {
        getElementJQueryD()?.selectpicker("refresh")
        refreshState()
        getElementJQueryD()?.trigger("change")?.data("AjaxBootstrapSelect")?.list?.cache = {}
    }

    @Suppress("ComplexMethod")
    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        if (multiple) {
            attributeSetBuilder.add("multiple")
        }
        maxOptions?.let {
            attributeSetBuilder.add("data-max-options", "" + it)
        }
        if (liveSearch) {
            attributeSetBuilder.add("data-live-search", "true")
        }
        placeholder?.let {
            attributeSetBuilder.add("title", translate(it))
        }
        if (autofocus == true) {
            attributeSetBuilder.add("autofocus")
        }
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
        val btnStyle = style?.className ?: "btn-default"
        attributeSetBuilder.add(
            "data-style",
            when (size) {
                InputSize.LARGE -> "$btnStyle btn-lg"
                InputSize.SMALL -> "$btnStyle btn-sm"
                else -> btnStyle
            }
        )

        selectWidthType?.let {
            attributeSetBuilder.add("data-width", it.value)
        } ?: selectWidth?.let {
            attributeSetBuilder.add("data-width", it.asString())
        }
        when (dropdownAlign) {
            SelectDropdownAlign.RIGHT -> {
                attributeSetBuilder.add("data-dropdown-align-right", "true")
            }
            SelectDropdownAlign.AUTO -> {
                attributeSetBuilder.add("data-dropdown-align-right", "auto")
            }
            else -> {
            }
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        ajaxOptions?.let {
            getElementJQueryD()?.selectpicker("render").ajaxSelectPicker(it.toJs(emptyOption))
        } ?: getElementJQueryD()?.selectpicker("render")

        this.getElementJQuery()?.on("show.bs.select") { e, _ ->
            this.dispatchEvent("showBsSelect", obj { detail = e })
        }
        this.getElementJQuery()?.on("shown.bs.select") { e, _ ->
            this.dispatchEvent("shownBsSelect", obj { detail = e })
        }
        this.getElementJQuery()?.on("hide.bs.select") { e, _ ->
            this.dispatchEvent("hideBsSelect", obj { detail = e })
        }
        this.getElementJQuery()?.on("hidden.bs.select") { e, _ ->
            this.dispatchEvent("hiddenBsSelect", obj { detail = e })
        }
        this.getElementJQuery()?.on("loaded.bs.select") { e, _ ->
            this.dispatchEvent("loadedBsSelect", obj { detail = e })
        }
        this.getElementJQuery()?.on("rendered.bs.select") { e, _ ->
            this.dispatchEvent("renderedBsSelect", obj { detail = e })
        }
        this.getElementJQuery()?.on("refreshed.bs.select") { e, _ ->
            this.dispatchEvent("refreshedBsSelect", obj { detail = e })
        }
        this.getElementJQueryD()?.on("changed.bs.select") { e, cIndex: Int ->
            e["clickedIndex"] = cIndex
            this.dispatchEvent("changedBsSelect", obj { detail = e })
        }
        refreshState()
    }

    @Suppress("UnsafeCastFromDynamic")
    protected open fun refreshState() {
        if (ajaxOptions == null) {
            value?.let {
                if (multiple) {
                    getElementJQueryD()?.selectpicker("val", it.split(",").toTypedArray())
                } else {
                    getElementJQueryD()?.selectpicker("val", it)
                }
            } ?: getElementJQueryD()?.selectpicker("val", null)
        } else if (value == null) {
            getElementJQueryD()?.selectpicker("val", null)
        }
    }

    override fun getState(): String? = value

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    override fun setState(state: String?) {
        value = state
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.selectInput(
    options: List<StringPair>? = null, value: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SelectInput.() -> Unit)? = null
): SelectInput {
    val selectInput =
        SelectInput(options, value, multiple, ajaxOptions, classes ?: className.set, init)
    this.add(selectInput)
    return selectInput
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.selectInput(
    state: ObservableState<S>,
    options: List<StringPair>? = null, value: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SelectInput.(S) -> Unit)
) = selectInput(options, value, multiple, ajaxOptions, classes, className).bind(state, true, init)

/**
 * Bidirectional data binding to the MutableState instance.
 * @param state the MutableState instance
 * @return current component
 */
fun SelectInput.bindTo(state: MutableState<String?>): SelectInput {
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
fun SelectInput.bindTo(state: MutableState<String>): SelectInput {
    bind(state, false) {
        if (value != it) value = it
    }
    addBeforeDisposeHook(subscribe {
        state.setState(it ?: "")
    })
    return this
}
