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
package pl.treksoft.kvision.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.form.check.CheckBox
import pl.treksoft.kvision.form.check.Radio
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.SimplePanel
import kotlin.js.Date
import kotlin.js.Json
import kotlin.reflect.KProperty1

/**
 * Bootstrap form layout options.
 */
enum class FormType(internal val formType: String) {
    INLINE("form-inline"),
    HORIZONTAL("form-horizontal")
}

/**
 * Bootstrap form component.
 *
 * @constructor
 * @param K model class type
 * @param type form layout
 * @param classes set of CSS class names
 * @param modelFactory function transforming a Map<String, Any?> to a data model of class K
 */
open class FormPanel<K>(
    private val type: FormType? = null, classes: Set<String> = setOf(),
    modelFactory: (Map<String, Any?>) -> K
) : SimplePanel(classes) {

    /**
     * Function returning validation message.
     */
    var validatorMessage
        get() = form.validatorMessage
        set(value) {
            form.validatorMessage = value
        }
    /**
     * Validation function.
     */
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

    /**
     * @suppress
     * Internal property.
     */
    @Suppress("LeakingThis")
    protected val form = Form(this, modelFactory)
    /**
     * @suppress
     * Internal property.
     */
    protected val validationAlert = Tag(TAG.H5, classes = setOf("alert", "alert-danger")).apply {
        role = "alert"
        visible = false
    }

    init {
        this.addInternal(validationAlert)
    }

    override fun render(): VNode {
        return render("form", childrenVNodes())
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (type != null) {
            cl.add(type.formType to true)
        }
        return cl
    }

    protected fun <C : FormControl> addInternal(
        key: KProperty1<K, *>, control: C, required: Boolean = false,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): FormPanel<K> {
        if (type == FormType.HORIZONTAL) {
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
        form.addInternal(key, control, required, validatorMessage, validator)
        return this
    }

    /**
     * Adds a string control to the form panel.
     * @param key key identifier of the control
     * @param control the string form control
     * @param required determines if the control is required
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form panel
     */
    open fun <C : StringFormControl> add(
        key: KProperty1<K, String?>, control: C, required: Boolean = false,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): FormPanel<K> {
        return addInternal(key, control, required, validatorMessage, validator)
    }

    /**
     * Adds a boolean control to the form panel.
     * @param key key identifier of the control
     * @param control the boolean form control
     * @param required determines if the control is required
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form panel
     */
    open fun <C : BoolFormControl> add(
        key: KProperty1<K, Boolean?>, control: C, required: Boolean = false,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): FormPanel<K> {
        return addInternal(key, control, required, validatorMessage, validator)
    }

    /**
     * Adds a number control to the form panel.
     * @param key key identifier of the control
     * @param control the number form control
     * @param required determines if the control is required
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form panel
     */
    open fun <C : NumberFormControl> add(
        key: KProperty1<K, Number?>, control: C, required: Boolean = false,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): FormPanel<K> {
        return addInternal(key, control, required, validatorMessage, validator)
    }

    /**
     * Adds a date control to the form panel.
     * @param key key identifier of the control
     * @param control the date form control
     * @param required determines if the control is required
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form panel
     */
    open fun <C : DateFormControl> add(
        key: KProperty1<K, Date?>, control: C, required: Boolean = false,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): FormPanel<K> {
        return addInternal(key, control, required, validatorMessage, validator)
    }

    /**
     * Removes a control from the form panel.
     * @param key key identifier of the control
     * @return current form panel
     */
    open fun remove(key: KProperty1<K, *>): FormPanel<K> {
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

    /**
     * Returns a control of given key.
     * @param key key identifier of the control
     * @return selected control
     */
    open fun getControl(key: KProperty1<K, *>): FormControl? {
        return form.getControl(key)
    }

    /**
     * Returns a value of the control of given key.
     * @param key key identifier of the control
     * @return value of the control
     */
    operator fun get(key: KProperty1<K, *>): Any? {
        return getControl(key)?.getValue()
    }

    /**
     * Sets the values of all the controls from the model.
     * @param model data model
     */
    open fun setData(model: K) {
        form.setData(model)
    }

    /**
     * Sets the values of all controls to null.
     */
    open fun clearData() {
        form.clearData()
    }

    /**
     * Returns current data model.
     * @return data model
     */
    open fun getData(): K {
        return form.getData()
    }

    /**
     * Returns current data model as JSON.
     * @return data model as JSON
     */
    open fun getDataJson(): Json {
        return form.getDataJson()
    }

    /**
     * Invokes validator function and validates the form.
     * @return validation result
     */
    open fun validate(): Boolean {
        return form.validate()
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun <K> Container.formPanel(
            type: FormType? = null, classes: Set<String> = setOf(),
            modelFactory: (Map<String, Any?>) -> K
        ): FormPanel<K> {
            val panel = FormPanel(type, classes, modelFactory)
            this.add(panel)
            return panel
        }
    }
}
