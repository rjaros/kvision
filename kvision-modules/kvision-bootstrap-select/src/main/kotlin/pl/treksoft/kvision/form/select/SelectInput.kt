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

import com.github.snabbdom.VNode
import pl.treksoft.kvision.KVManagerSelect.KVNULL
import pl.treksoft.kvision.core.ClassSetBuilder
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.form.FormInput
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.form.ValidationStatus
import pl.treksoft.kvision.html.ButtonStyle
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.asString
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set

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
 */
@Suppress("TooManyFunctions")
open class SelectInput(
    options: List<StringPair>? = null, value: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null,
    classes: Set<String> = setOf()
) : SimplePanel(classes), FormInput, ObservableState<String?> {

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
    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        name?.let {
            sn.add("name" to it)
        }
        if (multiple) {
            sn.add("multiple" to "multiple")
        }
        maxOptions?.let {
            sn.add("data-max-options" to "" + it)
        }
        if (liveSearch) {
            sn.add("data-live-search" to "true")
        }
        placeholder?.let {
            sn.add("title" to translate(it))
        }
        autofocus?.let {
            if (it) {
                sn.add("autofocus" to "autofocus")
            }
        }
        if (disabled) {
            sn.add("disabled" to "disabled")
        }
        val btnStyle = style?.className ?: "btn-default"
        when (size) {
            InputSize.LARGE -> {
                sn.add("data-style" to "$btnStyle btn-lg")
            }
            InputSize.SMALL -> {
                sn.add("data-style" to "$btnStyle btn-sm")
            }
            else -> {
                sn.add("data-style" to btnStyle)
            }
        }
        selectWidthType?.let {
            sn.add("data-width" to it.value)
        } ?: selectWidth?.let {
            sn.add("data-width" to it.asString())
        }
        when (dropdownAlign) {
            SelectDropdownAlign.RIGHT -> {
                sn.add("data-dropdown-align-right" to "true")
            }
            SelectDropdownAlign.AUTO -> {
                sn.add("data-dropdown-align-right" to "auto")
            }
            else -> {
            }
        }
        return sn
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

    override fun getState(): String? = value

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
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
fun Container.selectInput(
    options: List<StringPair>? = null, value: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SelectInput.() -> Unit)? = null
): SelectInput {
    val selectInput =
        SelectInput(options, value, multiple, ajaxOptions, classes ?: className.set).apply { init?.invoke(this) }
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
