package pl.treksoft.kvision.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair
import pl.treksoft.kvision.snabbdom.obj

private val _aKVNULL = "#kvnull"

enum class SELECTWIDTHTYPE(val value: String) {
    AUTO("auto"),
    FIT("fit")
}

class SelectInput(options: List<StringPair>? = null, value: String? = null,
                  multiple: Boolean = false, classes: Set<String> = setOf()) : SimplePanel(classes), StringFormField {

    internal var options = options
        set(value) {
            field = value
            setChildrenFromOptions()
        }

    @Suppress("LeakingThis")
    override var value: String? = value
        set(value) {
            field = value
            refreshState()
        }

    @Suppress("LeakingThis")
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
    override var disabled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    var autofocus: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    override var size: INPUTSIZE? = null
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
                    if (self.multiple) {
                        @Suppress("UNCHECKED_CAST")
                        val arr = it as? Array<String>
                        if (arr != null && arr.isNotEmpty()) {
                            arr.joinToString()
                        } else {
                            null
                        }
                    } else {
                        val vs = it as String
                        if (_aKVNULL == vs) {
                            null
                        } else {
                            vs
                        }
                    }
                }
            }
        }
    }

    override fun render(): VNode {
        return kvh("select", childrenVNodes())
    }

    override fun add(child: Widget): SimplePanel {
        super.add(child)
        refreshSelectInput()
        return this
    }

    override fun addAll(children: List<Widget>): SimplePanel {
        super.addAll(children)
        refreshSelectInput()
        return this
    }

    override fun remove(child: Widget): SimplePanel {
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
        super.removeAll()
        if (emptyOption) {
            super.add(SelectOption(_aKVNULL, ""))
        }
        options?.let {
            val c = it.map {
                SelectOption(it.first, it.second)
            }
            super.addAll(c)
        }
        this.refreshSelectInput()
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
            sn.add("data-width" to it.first.toString() + it.second.unit)
        }
        return sn
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        getElementJQueryD()?.selectpicker("render")
        this.getElementJQuery()?.on("show.bs.select", { e, _ ->
            this.dispatchEvent("showBsSelect", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("shown.bs.select", { e, _ ->
            this.dispatchEvent("shownBsSelect", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("hide.bs.select", { e, _ ->
            this.dispatchEvent("hideBsSelect", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("hidden.bs.select", { e, _ ->
            this.dispatchEvent("hiddenBsSelect", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("loaded.bs.select", { e, _ ->
            this.dispatchEvent("loadedBsSelect", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("rendered.bs.select", { e, _ ->
            this.dispatchEvent("renderedBsSelect", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("refreshed.bs.select", { e, _ ->
            this.dispatchEvent("refreshedBsSelect", obj({ detail = e }))
        })
        this.getElementJQueryD()?.on("changed.bs.select", { e, cIndex: Int ->
            e["clickedIndex"] = cIndex
            this.dispatchEvent("changedBsSelect", obj({ detail = e }))
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
