package pl.treksoft.kvision.form.select

import com.github.snabbdom.VNode
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.core.StringPair

open class SelectOptGroup(
    label: String, options: List<StringPair>? = null, maxOptions: Int? = null,
    disabled: Boolean = false, classes: Set<String> = setOf()
) : SimplePanel(classes) {

    var label: String = label
        set(value) {
            field = value
            refresh()
        }
    private var options = options
        set(value) {
            field = value
            setChildrenFromOptions()
        }
    var maxOptions: Int? = maxOptions
        set(value) {
            field = value
            refresh()
        }
    var disabled: Boolean = disabled
        set(value) {
            field = value
            refresh()
        }

    init {
        setChildrenFromOptions()
    }

    override fun render(): VNode {
        return kvh("optgroup", childrenVNodes())
    }

    private fun setChildrenFromOptions() {
        this.removeAll()
        options?.let {
            val c = it.map {
                SelectOption(it.first, it.second)
            }
            this.addAll(c)
        }
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("label" to label)
        maxOptions?.let {
            sn.add("data-max-options" to "" + it)
        }
        if (disabled) {
            sn.add("disabled" to "true")
        }
        return sn
    }
}
