package pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

abstract class AbstractTextInput(override var value: String? = null,
                                 classes: Set<String> = setOf()) : Widget(classes + "form-control"), StringFormField {
    init {
        this.setInternalEventListener {
            input = {
                val v = getElementJQuery()?.`val`() as String?
                if (v != null && v.isNotEmpty()) {
                    value = v
                } else {
                    value = null
                }
            }
        }
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
}
