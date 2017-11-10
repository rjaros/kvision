package pl.treksoft.kvision.form.text

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.form.StringFormField
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

abstract class AbstractTextInput(value: String? = null,
                                 classes: Set<String> = setOf()) : Widget(classes), StringFormField {

    init {
        this.setInternalEventListener<AbstractTextInput> {
            input = {
                self.changeValue()
            }
        }
    }

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
    var placeholder: String? = null
        set(value) {
            field = value
            refresh()
        }
    var name: String? = null
        set(value) {
            field = value
            refresh()
        }
    var maxlength: Int? = null
        set(value) {
            field = value
            refresh()
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
    var readonly: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    override var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
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
        placeholder?.let {
            sn.add("placeholder" to it)
        }
        name?.let {
            sn.add("name" to it)
        }
        autofocus?.let {
            if (it) {
                sn.add("autofocus" to "autofocus")
            }
        }
        maxlength?.let {
            sn.add("maxlength" to ("" + it))
        }
        readonly?.let {
            if (it) {
                sn.add("readonly" to "readonly")
            }
        }
        if (disabled) {
            sn.add("disabled" to "true")
        }
        return sn
    }

    protected open fun refreshState() {
        value?.let {
            getElementJQuery()?.`val`(it)
        } ?: getElementJQueryD()?.`val`(null)
    }

    protected open fun changeValue() {
        val v = getElementJQuery()?.`val`() as String?
        if (v != null && v.isNotEmpty()) {
            this.value = v
        } else {
            this.value = null
        }
    }
}
