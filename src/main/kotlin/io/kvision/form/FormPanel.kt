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
package io.kvision.form

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.DomAttribute
import io.kvision.form.FormPanel.Companion.create
import io.kvision.html.Div
import io.kvision.panel.FieldsetPanel
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode
import io.kvision.types.KFile
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import org.w3c.dom.HTMLFormElement
import kotlin.js.Date
import kotlin.js.Json
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Bootstrap form layout options.
 */
enum class FormType(internal val formType: String) {
    INLINE("form-inline"),
    HORIZONTAL("form-horizontal")
}

/**
 * Proportions for horizontal form layout.
 */
enum class FormHorizontalRatio(val labels: Int, val fields: Int) {
    RATIO_2(2, 10),
    RATIO_3(3, 9),
    RATIO_4(4, 8),
    RATIO_5(5, 7),
    RATIO_6(6, 6),
    RATIO_7(7, 5),
    RATIO_8(8, 4),
    RATIO_9(9, 3),
    RATIO_10(10, 2)
}

/**
 * Form methods.
 */
enum class FormMethod(override val attributeValue: String) : DomAttribute {
    GET("get"),
    POST("post"),
    ;

    override val attributeName: String
        get() = "method"
}

/**
 * Form encoding types.
 */
enum class FormEnctype(internal val enctype: String) {
    URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART("multipart/form-data"),
    PLAIN("text/plain")
}

/**
 * Form targets.
 */
enum class FormTarget(override val attributeValue: String) : DomAttribute {
    BLANK("_blank"),
    SELF("_self"),
    PARENT("_parent"),
    TOP("_top"),
    ;

    override val attributeName: String
        get() = "target"
}

/**
 * Bootstrap form component.
 *
 * @constructor
 * @param K model class type
 * @param method HTTP method
 * @param action the URL address to send data
 * @param enctype form encoding type
 * @param type form layout
 * @param condensed  determines if the form is condensed.
 * @param horizRatio  horizontal form layout ratio
 * @param classes set of CSS class names
 * @param serializer a serializer for model type
 * @param customSerializers a map of custom serializers for model type
 */
@Suppress("TooManyFunctions")
open class FormPanel<K : Any>(
    method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
    private val type: FormType? = null, condensed: Boolean = false,
    horizRatio: FormHorizontalRatio = FormHorizontalRatio.RATIO_2, className: String? = null,
    serializer: KSerializer<K>? = null, customSerializers: Map<KClass<*>, KSerializer<*>>? = null
) : SimplePanel(className) {

    /**
     * HTTP method.
     */
    var method by refreshOnUpdate(method)

    /**
     * The URL address to send data.
     */
    var action by refreshOnUpdate(action)

    /**
     * The form encoding type.
     */
    var enctype by refreshOnUpdate(enctype)

    /**
     * The form name.
     */
    var name: String? by refreshOnUpdate()

    /**
     * The form target.
     */
    var target: FormTarget? by refreshOnUpdate()

    /**
     * Determines if the form is not validated.
     */
    var novalidate: Boolean? by refreshOnUpdate()

    /**
     * Determines if the form should have autocomplete.
     */
    var autocomplete: Boolean? by refreshOnUpdate()

    /**
     * Determines if the form is condensed.
     */
    var condensed by refreshOnUpdate(condensed)

    /**
     * Horizontal form layout ratio.
     */
    var horizRatio by refreshOnUpdate(horizRatio)

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
        get() = validationAlert.content
        set(value) {
            validationAlert.content = value
            validationAlert.visible = value != null
            refresh()
        }

    /**
     * @suppress
     * Internal property.
     */
    @Suppress("LeakingThis")
    val form = Form(this, serializer, customSerializers)

    /**
     * @suppress
     * Internal property.
     */
    protected val validationAlert = Div(className = "alert alert-danger").apply {
        role = "alert"
        visible = false
    }

    private var currentFieldset: FieldsetPanel? = null

    init {
        this.addPrivate(validationAlert)
    }

    override fun render(): VNode {
        return render("form", childrenVNodes())
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (type != null) {
            classSetBuilder.add(type.formType)
            if (type == FormType.HORIZONTAL) classSetBuilder.add("container-fluid")
        }
        if (condensed) classSetBuilder.add("kv-form-condensed")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add(method)
        action?.let {
            attributeSetBuilder.add("action", it)
        }
        enctype?.let {
            attributeSetBuilder.add("enctype", it.enctype)
        }
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        attributeSetBuilder.add(target)
        if (autocomplete == false) {
            attributeSetBuilder.add("autocomplete", "off")
        }
        if (novalidate == true) {
            attributeSetBuilder.add("novalidate")
        }
    }

    /**
     * Adds a form control to the form panel bound to a dynamic field type.
     * @param key key identifier of the control
     * @param control the form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param legend put this control inside a fieldset with given legend
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    open fun <C : FormControl> add(
        key: String, control: C, required: Boolean = false, requiredMessage: String? = null,
        legend: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        when (type) {
            FormType.INLINE -> control.styleForInlineFormPanel()
            FormType.HORIZONTAL -> control.styleForHorizontalFormPanel(horizRatio)
            else -> control.styleForVerticalFormPanel()
        }
        if (required) control.flabel.addCssClass("required-label")
        if (legend == null) {
            super.add(control)
        } else if (currentFieldset == null || currentFieldset?.legend != legend) {
            currentFieldset = FieldsetPanel(legend) {
                add(control)
            }
            super.add(currentFieldset!!)
        } else {
            currentFieldset?.add(control)
        }
        form.add(key, control, required, requiredMessage, validatorMessage, validator)
    }

    override fun add(child: Component) {
        if (child is FormControl) {
            when (type) {
                FormType.INLINE -> child.styleForInlineFormPanel()
                FormType.HORIZONTAL -> child.styleForHorizontalFormPanel(horizRatio)
                else -> child.styleForVerticalFormPanel()
            }
        }
        super.add(child)
    }

    /**
     * Adds a string control to the form panel.
     * @param key key identifier of the control
     * @param control the string form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param legend put this control inside a fieldset with given legend
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    open fun <C : StringFormControl> add(
        key: KProperty1<K, String?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        legend: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, legend, validatorMessage, validator)
    }

    /**
     * Adds a string control to the form panel bound to custom field type.
     * @param key key identifier of the control
     * @param control the string form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param legend put this control inside a fieldset with given legend
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    open fun <C : StringFormControl> addCustom(
        key: KProperty1<K, Any?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        legend: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, legend, validatorMessage, validator)
    }

    /**
     * Adds a boolean control to the form panel.
     * @param key key identifier of the control
     * @param control the boolean form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param legend put this control inside a fieldset with given legend
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    open fun <C : BoolFormControl> add(
        key: KProperty1<K, Boolean?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        legend: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, legend, validatorMessage, validator)
    }

    /**
     * Adds a nullable boolean control to the form panel.
     * @param key key identifier of the control
     * @param control the boolean form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param legend put this control inside a fieldset with given legend
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    open fun <C : TriStateFormControl> add(
        key: KProperty1<K, Boolean?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        legend: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, legend, validatorMessage, validator)
    }

    /**
     * Adds a number control to the form panel.
     * @param key key identifier of the control
     * @param control the number form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param legend put this control inside a fieldset with given legend
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    open fun <C : NumberFormControl> add(
        key: KProperty1<K, Number?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        legend: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, legend, validatorMessage, validator)
    }

    /**
     * Adds a date control to the form panel.
     * @param key key identifier of the control
     * @param control the date form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param legend put this control inside a fieldset with given legend
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    open fun <C : DateFormControl> add(
        key: KProperty1<K, Date?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        legend: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, legend, validatorMessage, validator)
    }

    /**
     * Adds a files control to the form panel.
     * @param key key identifier of the control
     * @param control the files form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param legend put this control inside a fieldset with given legend
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    open fun <C : KFilesFormControl> add(
        key: KProperty1<K, List<KFile>?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        legend: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, legend, validatorMessage, validator)
    }

    /**
     * Bind a control to the form panel with a dynamic key.
     * @param key key identifier of the control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param layoutType style control for given form layout
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return the control itself
     */
    open fun <C : FormControl> C.bind(
        key: String, required: Boolean = false, requiredMessage: String? = null,
        layoutType: FormType? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): C {
        when (layoutType ?: type) {
            FormType.INLINE -> this.styleForInlineFormPanel()
            FormType.HORIZONTAL -> this.styleForHorizontalFormPanel(horizRatio)
            else -> this.styleForVerticalFormPanel()
        }
        if (required) this.flabel.addCssClass("required-label")
        form.add(key, this, required, requiredMessage, validatorMessage, validator)
        return this
    }

    /**
     * Bind a string control to the form panel.
     * @param key key identifier of the control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param layoutType style control for given form layout
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return the control itself
     */
    open fun <C : StringFormControl> C.bind(
        key: KProperty1<K, String?>, required: Boolean = false, requiredMessage: String? = null,
        layoutType: FormType? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): C {
        return bind(key.name, required, requiredMessage, layoutType, validatorMessage, validator)
    }

    /**
     * Bind a string control to the form panel bound to custom field type.
     * @param key key identifier of the control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param layoutType style control for given form layout
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return the control itself
     */
    open fun <C : StringFormControl> C.bindCustom(
        key: KProperty1<K, Any?>, required: Boolean = false, requiredMessage: String? = null,
        layoutType: FormType? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): C {
        return bind(key.name, required, requiredMessage, layoutType, validatorMessage, validator)
    }


    /**
     * Bind a boolean control to the form panel.
     * @param key key identifier of the control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param layoutType style control for given form layout
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return the control itself
     */
    open fun <C : BoolFormControl> C.bind(
        key: KProperty1<K, Boolean?>, required: Boolean = false, requiredMessage: String? = null,
        layoutType: FormType? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): C {
        return bind(key.name, required, requiredMessage, layoutType, validatorMessage, validator)
    }

    /**
     * Bind a nullable boolean control to the form panel.
     * @param key key identifier of the control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param layoutType style control for given form layout
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return the control itself
     */
    open fun <C : TriStateFormControl> C.bind(
        key: KProperty1<K, Boolean?>, required: Boolean = false, requiredMessage: String? = null,
        layoutType: FormType? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): C {
        return bind(key.name, required, requiredMessage, layoutType, validatorMessage, validator)
    }

    /**
     * Bind a number control to the form panel.
     * @param key key identifier of the control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param layoutType style control for given form layout
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return the control itself
     */
    open fun <C : NumberFormControl> C.bind(
        key: KProperty1<K, Number?>, required: Boolean = false, requiredMessage: String? = null,
        layoutType: FormType? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): C {
        return bind(key.name, required, requiredMessage, layoutType, validatorMessage, validator)
    }

    /**
     * Bind a date control to the form panel.
     * @param key key identifier of the control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param layoutType style control for given form layout
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return the control itself
     */
    open fun <C : DateFormControl> C.bind(
        key: KProperty1<K, Date?>, required: Boolean = false, requiredMessage: String? = null,
        layoutType: FormType? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): C {
        return bind(key.name, required, requiredMessage, layoutType, validatorMessage, validator)
    }

    /**
     * Bind a files control to the form panel.
     * @param key key identifier of the control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param layoutType style control for given form layout
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return the control itself
     */
    open fun <C : KFilesFormControl> C.bind(
        key: KProperty1<K, List<KFile>?>, required: Boolean = false, requiredMessage: String? = null,
        layoutType: FormType? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): C {
        return bind(key.name, required, requiredMessage, layoutType, validatorMessage, validator)
    }

    /**
     * Removes a control from the form panel.
     * @param key key identifier of the control
     */
    open fun remove(key: KProperty1<K, *>) {
        remove(key.name)
    }

    /**
     * Removes a control from the form panel with a dynamic key.
     * @param key key identifier of the control
     */
    open fun remove(key: String) {
        form.getControl(key)?.let {
            val parent = it.parent
            if (parent is FieldsetPanel) {
                parent.remove(it)
            } else {
                super.remove(it)
            }
        }
        form.remove(key)
    }

    override fun removeAll() {
        super.removeAll()
        form.removeAll()
    }

    /**
     * Unbind a control from the form panel.
     * @param key key identifier of the control
     */
    open fun unbind(key: KProperty1<K, *>) {
        unbind(key.name)
    }

    /**
     * Unbind a control from the form panel with a dynamic key.
     * @param key key identifier of the control
     */
    open fun unbind(key: String) {
        form.remove(key)
    }

    /**
     * Returns a control of given key.
     * @param key key identifier of the control
     * @return selected control
     */
    open fun getControl(key: KProperty1<K, *>): FormControl? {
        return getControl(key.name)
    }

    /**
     * Returns a control of given dynamic key.
     * @param key key identifier of the control
     * @return selected control
     */
    open fun getControl(key: String): FormControl? {
        return form.getControl(key)
    }

    /**
     * Returns a value of the control of given key.
     * @param key key identifier of the control
     * @return value of the control
     */
    operator fun <V> get(key: KProperty1<K, V>): V? {
        return get(key.name)
    }

    /**
     * Returns a value of the control of given dynamic key.
     * @param key key identifier of the control
     * @return value of the control
     */
    operator fun <V> get(key: String): V? {
        return getControl(key)?.getValue()?.unsafeCast<V>()
    }

    /**
     * Sets the values of all the controls from the model.
     * @param model data model
     */
    open fun setData(model: K) {
        singleRender {
            form.setData(model)
        }
    }

    /**
     * Sets the values of all controls to null.
     */
    open fun clearData() {
        singleRender {
            form.clearData()
        }
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
     * @param markFields determines if form fields should be labeled with error messages
     * @return validation result
     */
    open fun validate(markFields: Boolean = true): Boolean {
        return singleRender {
            form.validate(markFields)
        }
    }

    /**
     * Clear validation information from all fields.
     */
    open fun clearValidation() {
        singleRender {
            form.clearValidation()
        }
    }

    /**
     * Submit the html form.
     */
    open fun submit() {
        (getElement() as? HTMLFormElement)?.submit()
    }

    /**
     * Reset the html form.
     */
    open fun reset() {
        (getElement() as? HTMLFormElement)?.reset()
    }

    /**
     * Check validity of the html form.
     */
    open fun checkValidity(): Boolean {
        return (getElement() as? HTMLFormElement)?.checkValidity() ?: false
    }

    /**
     * Report validity of the html form.
     */
    open fun reportValidity(): Boolean {
        return (getElement() as? HTMLFormElement)?.checkValidity() ?: false
    }

    override fun focus() {
        form.getFirstControl()?.focus()
    }

    companion object {

        inline fun <reified K : Any> create(
            method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
            type: FormType? = null, condensed: Boolean = false,
            horizRatio: FormHorizontalRatio = FormHorizontalRatio.RATIO_2, className: String? = null,
            customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
            noinline init: (FormPanel<K>.() -> Unit)? = null
        ): FormPanel<K> {
            val formPanel =
                FormPanel(
                    method,
                    action,
                    enctype,
                    type,
                    condensed,
                    horizRatio,
                    className,
                    serializer<K>(),
                    customSerializers
                )
            init?.invoke(formPanel)
            return formPanel
        }

    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
inline fun <reified K : Any> Container.formPanel(
    method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
    type: FormType? = null, condensed: Boolean = false,
    horizRatio: FormHorizontalRatio = FormHorizontalRatio.RATIO_2,
    className: String? = null,
    customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
    noinline init: (FormPanel<K>.() -> Unit)? = null
): FormPanel<K> {
    val formPanel =
        create<K>(
            method,
            action,
            enctype,
            type,
            condensed,
            horizRatio,
            className,
            customSerializers
        )
    init?.invoke(formPanel)
    this.add(formPanel)
    return formPanel
}

/**
 * DSL builder extension function.
 *
 * Simplified version of formPanel container without data model support.
 */
fun Container.form(
    method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
    type: FormType? = null, condensed: Boolean = false,
    horizRatio: FormHorizontalRatio = FormHorizontalRatio.RATIO_2,
    className: String? = null,
    init: (FormPanel<Map<String, Any?>>.() -> Unit)? = null
): FormPanel<Map<String, Any?>> {
    val formPanel =
        FormPanel<Map<String, Any?>>(
            method,
            action,
            enctype,
            type,
            condensed,
            horizRatio,
            className
        )
    init?.invoke(formPanel)
    this.add(formPanel)
    return formPanel
}
