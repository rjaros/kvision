package pl.treksoft.kvision.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.form.check.CheckBox
import pl.treksoft.kvision.form.check.Radio
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringBoolPair

enum class FORMTYPE(val formType: String) {
    INLINE("form-inline"),
    HORIZONTAL("form-horizontal")
}

open class FormPanel<K>(private val type: FORMTYPE? = null, classes: Set<String> = setOf(),
                        modelFactory: (Map<String, Any?>) -> K) : SimplePanel(classes) {

    var validatorMessage
        get() = form.validatorMessage
        set(value) {
            form.validatorMessage = value
        }
    var validator
        get() = form.validator
        set(value) {
            form.validator = value
        }

    internal var validatorError: String?
        get() = validationAlert.text
        set(value) {
            validationAlert.text = value
            validationAlert.visible = value != null
            refresh()
        }

    @Suppress("LeakingThis")
    protected val form = Form(this, modelFactory)
    protected val validationAlert = Tag(TAG.H5, classes = setOf("alert", "alert-danger")).apply {
        role = "alert"
        visible = false
    }

    init {
        this.addInternal(validationAlert)
    }

    override fun render(): VNode {
        return kvh("form", childrenVNodes())
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (type != null) {
            cl.add(type.formType to true)
        }
        return cl
    }

    open fun <C : FormControl> add(key: String, control: C, required: Boolean = false,
                                   validatorMessage: ((C) -> String?)? = null,
                                   validator: ((C) -> Boolean?)? = null): FormPanel<K> {
        if (type == FORMTYPE.HORIZONTAL) {
            if (control is CheckBox || control is Radio) {
                control.addCssClass("col-sm-offset-2")
                control.addCssClass("col-sm-10")
            } else {
                control.flabel.addCssClass("col-sm-2")
                control.input.addSurroundingCssClass("col-sm-10")
                control.validationInfo.addCssClass("col-sm-offset-2")
                control.validationInfo.addCssClass("col-sm-10")
            }
        }
        super.add(control)
        form.add(key, control, required, validatorMessage, validator)
        return this
    }

    fun remove(key: String): FormPanel<K> {
        form.getControl(key)?.let {
            super.remove(it)
        }
        form.remove(key)
        return this
    }

    override fun removeAll(): FormPanel<K> {
        super.removeAll()
        this.addInternal(validationAlert)
        form.removeAll()
        return this
    }

    open fun setData(data: K) {
        form.setData(data)
    }

    open fun getData(): K {
        return form.getData()
    }

    open fun validate(): Boolean {
        return form.validate()
    }
}
