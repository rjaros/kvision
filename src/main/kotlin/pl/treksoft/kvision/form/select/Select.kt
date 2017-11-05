package pl.treksoft.kvision.form.select

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.StringFormField
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.SnOn
import pl.treksoft.kvision.snabbdom.StringPair

open class Select(options: List<StringPair>? = null, value: String? = null,
                  multiple: Boolean = false, label: String? = null,
                  rich: Boolean = false) : SimplePanel(setOf("form-group")), StringFormField {

    var options
        get() = input.options
        set(value) {
            input.options = value
        }
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }
    var startValue
        get() = input.startValue
        set(value) {
            input.startValue = value
        }
    var name
        get() = input.name
        set(value) {
            input.name = value
        }
    var multiple
        get() = input.multiple
        set(value) {
            input.multiple = value
        }
    var maxOptions
        get() = input.maxOptions
        set(value) {
            input.maxOptions = value
        }
    var liveSearch
        get() = input.liveSearch
        set(value) {
            input.liveSearch = value
        }
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }
    var style
        get() = input.style
        set(value) {
            input.style = value
        }
    var selectWidth
        get() = input.selectWidth
        set(value) {
            input.selectWidth = value
        }
    var selectWidthType
        get() = input.selectWidthType
        set(value) {
            input.selectWidthType = value
        }
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
    var label
        get() = flabel.text
        set(value) {
            flabel.text = value
        }
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

    private val idc = "kv_form_select_" + counter
    val input: SelectInput = SelectInput(options, value, multiple, null, setOf("form-control")).apply { id = idc }
    val flabel: FieldLabel = FieldLabel(idc, label, rich)

    init {
        this.addInternal(flabel)
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(input)
        counter++
    }

    companion object {
        var counter = 0
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

    override fun add(child: Widget): SimplePanel {
        input.add(child)
        return this
    }

    override fun addAll(children: List<Widget>): SimplePanel {
        input.addAll(children)
        return this
    }

    override fun remove(child: Widget): SimplePanel {
        input.remove(child)
        return this
    }

    override fun removeAll(): SimplePanel {
        input.removeAll()
        return this
    }

    override fun getChildren(): List<Widget> {
        return input.getChildren()
    }

    open fun showOptions() {
        input.showOptions()
    }

    open fun hideOptions() {
        input.hideOptions()
    }

    open fun toggleOptions() {
        input.toggleOptions()
    }
}
