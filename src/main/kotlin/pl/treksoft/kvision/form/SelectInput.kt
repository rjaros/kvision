package pl.treksoft.kvision.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

private val _aKVNULL = "#kvnull"

enum class SELECTWIDTHTYPE(val value: String) {
    AUTO("auto"),
    FIT("fit")
}

class SelectInput(options: List<StringPair>? = null, override var value: String? = null,
                  multiple: Boolean = false, classes: Set<String> = setOf()) : SimplePanel(classes), StringFormField {

    private var options = options
        set(value) {
            field = value
            setChildrenFromOptions()
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

    private fun setChildrenFromOptions() {
        this.removeAll()
        if (emptyOption) {
            this.add(SelectOption(_aKVNULL, ""))
        }
        options?.let {
            val c = it.map {
                SelectOption(it.first, it.second)
            }
            this.addAll(c)
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("selectpicker" to true)
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

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
        value?.let {
            if (multiple) {
                getElementJQueryD()?.selectpicker("val", it.split(",").toTypedArray())
            } else {
                getElementJQueryD()?.selectpicker("val", it)
            }
        }
    }
}
