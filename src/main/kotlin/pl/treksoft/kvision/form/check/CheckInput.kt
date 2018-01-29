package pl.treksoft.kvision.form.check

import com.github.snabbdom.VNode
import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

enum class CHECKINPUTTYPE(val type: String) {
    CHECKBOX("checkbox"),
    RADIO("radio")
}

open class CheckInput(
    type: CHECKINPUTTYPE = CHECKINPUTTYPE.CHECKBOX, value: Boolean = false,
    classes: Set<String> = setOf()
) : Widget(classes) {

    init {
        this.setInternalEventListener<CheckInput> {
            click = {
                val v = getElementJQuery()?.prop("checked") as Boolean?
                self.value = (v == true)
            }
            change = {
                val v = getElementJQuery()?.prop("checked") as Boolean?
                self.value = (v == true)
            }
        }
    }

    var value: Boolean = value
        set(value) {
            field = value
            refreshState()
        }
    var startValue: Boolean = value
        set(value) {
            field = value
            this.value = value
            refresh()
        }
    var type: CHECKINPUTTYPE = type
        set(value) {
            field = value
            refresh()
        }
    var name: String? = null
        set(value) {
            field = value
            refresh()
        }
    var disabled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    var extraValue: String? = null
        set(value) {
            field = value
            refresh()
        }
    var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return kvh("input")
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("type" to type.type)
        if (startValue) {
            sn.add("checked" to "true")
        }
        name?.let {
            sn.add("name" to it)
        }
        if (disabled) {
            sn.add("disabled" to "true")
        }
        extraValue?.let {
            sn.add("value" to it)
        }
        return sn
    }

    override fun afterInsert(node: VNode) {
        refreshState()
    }

    override fun afterPostpatch(node: VNode) {
        refreshState()
    }

    private fun refreshState() {
        val v = getElementJQuery()?.prop("checked") as Boolean?
        if (this.value != v) {
            getElementJQuery()?.prop("checked", this.value)
        }
    }

    open fun onClick(handler: CheckInput.(MouseEvent) -> Unit): CheckInput {
        this.setEventListener<CheckInput> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }
}
