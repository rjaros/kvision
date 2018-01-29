package pl.treksoft.kvision.form.select

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.KVManager.KVNULL
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair
import pl.treksoft.kvision.snabbdom.obj
import pl.treksoft.kvision.utils.asString

enum class SELECTWIDTHTYPE(val value: String) {
    AUTO("auto"),
    FIT("fit")
}

@Suppress("TooManyFunctions")
open class SelectInput(
    options: List<StringPair>? = null, value: String? = null,
    multiple: Boolean = false, ajaxOptions: AjaxOptions? = null,
    classes: Set<String> = setOf()
) : SimplePanel(classes) {

    internal var options = options
        set(value) {
            field = value
            setChildrenFromOptions()
        }

    var value: String? = value
        set(value) {
            field = value
            refreshState()
        }

    var startValue: String? = value
        set(value) {
            field = value
            this.value = value
            refresh()
        }
    var name: String? = null
        set(value) {
            field = value
            refresh()
        }
    var multiple: Boolean = multiple
        set(value) {
            field = value
            refresh()
        }
    var ajaxOptions: AjaxOptions? = ajaxOptions
        set(value) {
            field = value
            if (value != null) liveSearch = true
            refresh()
        }
    var maxOptions: Int? = null
        set(value) {
            field = value
            refresh()
        }
    var liveSearch: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    var placeholder: String? = null
        set(value) {
            field = value
            refresh()
        }
    var style: BUTTONSTYLE? = null
        set(value) {
            field = value
            refresh()
        }
    var selectWidth: CssSize? = null
        set(value) {
            field = value
            refresh()
        }
    var selectWidthType: SELECTWIDTHTYPE? = null
        set(value) {
            field = value
            refresh()
        }
    var emptyOption: Boolean = false
        set(value) {
            field = value
            setChildrenFromOptions()
        }
    var disabled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    var autofocus: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
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
        return kvh("select", childrenVNodes())
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

    open fun showOptions() {
        getElementJQueryD()?.selectpicker("show")
    }

    open fun hideOptions() {
        getElementJQueryD()?.selectpicker("hide")
    }

    open fun toggleOptions() {
        getElementJQueryD()?.selectpicker("toggle")
    }

    open fun deselect() {
        getElementJQueryD()?.selectpicker("deselectAll")
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("selectpicker" to true)
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    fun refreshSelectInput() {
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

}
