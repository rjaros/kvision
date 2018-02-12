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
import pl.treksoft.kvision.KVManager.KVNULL
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.asString
import pl.treksoft.kvision.utils.obj

/**
 * Select width types. See [Bootstrap Select width](http://silviomoreto.github.io/bootstrap-select/examples/#width).
 */
enum class SELECTWIDTHTYPE(internal val value: String) {
    AUTO("auto"),
    FIT("fit")
}

/**
 * The basic component for Select control.
 *
 * The select control can be populated directly from *options* parameter or manually by adding
 * [SelectOption] or [SelectOptGroup] components to the container.
 *
 * @constructor
 * @param options an optional list of options (label to value pairs) for the select control
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
) : SimplePanel(classes) {

    /**
     * A list of options (label to value pairs) for the select control.
     */
    internal var options = options
        set(value) {
            field = value
            setChildrenFromOptions()
        }
    /**
     * A value of the selected option.
     */
    var value: String? = value
        set(value) {
            field = value
            refreshState()
        }
    /**
     * The name attribute of the generated HTML select element.
     */
    var name: String? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if multiple value selection is allowed.
     */
    var multiple: Boolean = multiple
        set(value) {
            field = value
            refresh()
        }
    /**
     * Additional options for remote (AJAX) data source.
     */
    var ajaxOptions: AjaxOptions? = ajaxOptions
        set(value) {
            field = value
            if (value != null) liveSearch = true
            refresh()
        }
    /**
     * Maximal number of selected options.
     */
    var maxOptions: Int? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if live search is available.
     */
    var liveSearch: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    /**
     * The placeholder for the select control.
     */
    var placeholder: String? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * The style of the select control.
     */
    var style: BUTTONSTYLE? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * The width of the select control.
     */
    var selectWidth: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * The width type of the select control.
     */
    var selectWidthType: SELECTWIDTHTYPE? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if an empty option is automatically generated.
     */
    var emptyOption: Boolean = false
        set(value) {
            field = value
            setChildrenFromOptions()
        }
    /**
     * Determines if the field is disabled.
     */
    var disabled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the select is automatically focused.
     */
    var autofocus: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * The size of the input.
     */
    var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
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
                arr.joinToString()
            } else {
                null
            }
        } else {
            val vs = v as String
            if (KVNULL == vs || vs.isEmpty()) {
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

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("selectpicker" to true)
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    private fun refreshSelectInput() {
        getElementJQueryD()?.selectpicker("refresh")
        refreshState()
    }

    @Suppress("ComplexMethod")
    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        name?.let {
            sn.add("name" to it)
        }
        if (multiple) {
            sn.add("multiple" to "true")
        }
        maxOptions?.let {
            sn.add("data-max-options" to "" + it)
        }
        if (liveSearch) {
            sn.add("data-live-search" to "true")
        }
        placeholder?.let {
            sn.add("title" to it)
        }
        autofocus?.let {
            if (it) {
                sn.add("autofocus" to "autofocus")
            }
        }
        if (disabled) {
            sn.add("disabled" to "true")
        }
        val btnStyle = style?.className ?: "btn-default"
        when (size) {
            INPUTSIZE.LARGE -> {
                sn.add("data-style" to "$btnStyle btn-lg")
            }
            INPUTSIZE.SMALL -> {
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
        return sn
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        ajaxOptions?.let {
            getElementJQueryD()?.selectpicker("render").ajaxSelectPicker(it.toJs(emptyOption))
        } ?: getElementJQueryD()?.selectpicker("render")

        this.getElementJQuery()?.on("show.bs.select", { e, _ ->
            this.dispatchEvent("showBsSelect", obj { detail = e })
        })
        this.getElementJQuery()?.on("shown.bs.select", { e, _ ->
            this.dispatchEvent("shownBsSelect", obj { detail = e })
        })
        this.getElementJQuery()?.on("hide.bs.select", { e, _ ->
            this.dispatchEvent("hideBsSelect", obj { detail = e })
        })
        this.getElementJQuery()?.on("hidden.bs.select", { e, _ ->
            this.dispatchEvent("hiddenBsSelect", obj { detail = e })
        })
        this.getElementJQuery()?.on("loaded.bs.select", { e, _ ->
            this.dispatchEvent("loadedBsSelect", obj { detail = e })
        })
        this.getElementJQuery()?.on("rendered.bs.select", { e, _ ->
            this.dispatchEvent("renderedBsSelect", obj { detail = e })
        })
        this.getElementJQuery()?.on("refreshed.bs.select", { e, _ ->
            this.dispatchEvent("refreshedBsSelect", obj { detail = e })
        })
        this.getElementJQueryD()?.on("changed.bs.select", { e, cIndex: Int ->
            e["clickedIndex"] = cIndex
            this.dispatchEvent("changedBsSelect", obj { detail = e })
        })
        refreshState()
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun refreshState() {
        value?.let {
            if (multiple) {
                getElementJQueryD()?.selectpicker("val", it.split(",").toTypedArray())
            } else {
                getElementJQueryD()?.selectpicker("val", it)
            }
        } ?: getElementJQueryD()?.selectpicker("val", null)
    }


    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.selectInput(
            options: List<StringPair>? = null, value: String? = null,
            multiple: Boolean = false, ajaxOptions: AjaxOptions? = null,
            classes: Set<String> = setOf(), init: (SelectInput.() -> Unit)? = null
        ): SelectInput {
            val selectInput = SelectInput(options, value, multiple, ajaxOptions, classes).apply { init?.invoke(this) }
            this.add(selectInput)
            return selectInput
        }
    }
}
