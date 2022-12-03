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

import io.kvision.i18n.I18n.trans
import io.kvision.types.DateSerializer
import io.kvision.types.KFile
import io.kvision.types.toDateF
import io.kvision.types.toStringF
import io.kvision.utils.Serialization
import io.kvision.utils.Serialization.toObj
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import kotlinx.serialization.serializer
import kotlin.js.Date
import kotlin.js.Json
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Internal data class containing form field parameters.
 */
internal data class FieldParams<in F : FormControl>(
    val required: Boolean = false,
    val requiredMessage: String? = null,
    val validatorMessage: ((F) -> String?)? = null,
    val validator: ((F) -> Boolean?)? = null
)

/**
 * The form definition class. Can be used directly or indirectly inside a [FormPanel].
 *
 * @constructor Creates a form with a given modelFactory function
 * @param K model class type
 * @param panel optional instance of [FormPanel]
 * @param serializer a serializer for model type
 * @param customSerializers a map of custom serializers for model type
 */
@Suppress("TooManyFunctions", "UnsafeCastFromDynamic")
class Form<K : Any>(
    private val panel: FormPanel<K>? = null,
    private val serializer: KSerializer<K>? = null,
    private val customSerializers: Map<KClass<*>, KSerializer<*>>? = null
) {
    private val dataMap = mutableMapOf<String, Any?>()
    val modelFactory: ((Map<String, Any?>) -> K)?
    private val jsonFactory: ((K) -> dynamic)?
    val fields: LinkedHashMap<String, FormControl> = linkedMapOf()
    internal val fieldsParams: MutableMap<String, Any> = mutableMapOf()
    internal var validatorMessage: ((Form<K>) -> String?)? = null
    internal var validator: ((Form<K>) -> Boolean?)? = null

    @OptIn(ExperimentalSerializationApi::class)
    private val JsonInstance = serializer?.let {
        kotlinx.serialization.json.Json(
            from = (Serialization.customConfiguration ?: kotlinx.serialization.json.Json.Default)
        ) {
            encodeDefaults = true
            explicitNulls = false
            serializersModule = serializersModule.overwriteWith(SerializersModule {
                contextual(Date::class, DateSerializer)
                customSerializers?.forEach { (kclass, serializer) ->
                    contextual(kclass.unsafeCast<KClass<Any>>(), serializer.unsafeCast<KSerializer<Any>>())
                }
            })
        }
    }

    init {
        modelFactory = serializer?.let {
            {
                val json = js("{}")
                it.forEach { (key, value) ->
                    val v = when (value) {
                        is Date -> {
                            value.toStringF()
                        }
                        is List<*> -> {
                            @Suppress("UNCHECKED_CAST", "UnsafeCastFromDynamic")
                            ((value as? List<KFile>)?.toObj(ListSerializer(KFile.serializer())))
                        }
                        else -> value
                    }
                    if (v != null) json[key] = v
                }
                JsonInstance!!.decodeFromString(serializer, JSON.stringify(json))
            }
        }
        jsonFactory = serializer?.let {
            {
                JSON.parse(JsonInstance!!.encodeToString(serializer, it))
            }
        }
    }

    /**
     * Adds a form control to the form with a dynamic keys.
     * @param key key identifier of the control
     * @param control the form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    fun <C : FormControl> add(
        key: String, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        this.fields[key] = control
        this.fieldsParams[key] = FieldParams(required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a string control to the form.
     * @param key key identifier of the control
     * @param control the string form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    fun <C : StringFormControl> add(
        key: KProperty1<K, String?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a string control to the form bound to custom field type.
     * @param key key identifier of the control
     * @param control the string form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    fun <C : StringFormControl> addCustom(
        key: KProperty1<K, Any?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a boolean control to the form.
     * @param key key identifier of the control
     * @param control the boolean form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    fun <C : BoolFormControl> add(
        key: KProperty1<K, Boolean?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a nullable boolean control to the form.
     * @param key key identifier of the control
     * @param control the boolean form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    fun <C : TriStateFormControl> add(
        key: KProperty1<K, Boolean?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a number control to the form.
     * @param key key identifier of the control
     * @param control the number form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    fun <C : NumberFormControl> add(
        key: KProperty1<K, Number?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a date control to the form.
     * @param key key identifier of the control
     * @param control the date form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    fun <C : DateFormControl> add(
        key: KProperty1<K, Date?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a files control to the form.
     * @param key key identifier of the control
     * @param control the files form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     */
    fun <C : KFilesFormControl> add(
        key: KProperty1<K, List<KFile>?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ) {
        add(key.name, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Removes a control from the form.
     * @param key key identifier of the control
     */
    fun remove(key: KProperty1<K, *>) {
        this.fields.remove(key.name)
    }

    /**
     * Removes a control from the form with a dynamic keys.
     * @param key key identifier of the control
     */
    fun remove(key: String) {
        this.fields.remove(key)
    }

    /**
     * Removes all controls from the form.
     */
    fun removeAll() {
        this.fields.clear()
    }

    /**
     * Returns a control with a given key.
     * @param key key identifier of the control
     * @return selected control
     */
    fun getControl(key: KProperty1<K, *>): FormControl? {
        return this.fields[key.name]
    }

    /**
     * Returns a control with a given dynamic key.
     * @param key key identifier of the control
     * @return selected control
     */
    fun getControl(key: String): FormControl? {
        return this.fields[key]
    }

    /**
     * Returns the first control added to the form.
     * @return the first control
     */
    fun getFirstControl(): FormControl? {
        return this.fields.firstNotNullOfOrNull { it.value }
    }

    /**
     * Returns a value of the control with a given key.
     * @param key key identifier of the control
     * @return value of the control
     */
    operator fun get(key: KProperty1<K, *>): Any? {
        return getControl(key)?.getValue()
    }

    /**
     * Returns a value of the control with a given dynamic key.
     * @param key key identifier of the control
     * @return value of the control
     */
    operator fun get(key: String): Any? {
        return getControl(key)?.getValue()
    }

    /**
     * Sets the values of all the controls from the model.
     * @param model data model
     */
    fun setData(model: K) {
        dataMap.clear()
        if (jsonFactory != null) {
            val json = jsonFactory.invoke(model)
            val keys = js("Object").keys(json).unsafeCast<Array<String>>()
            for (key in keys) {
                val jsonValue = json[key]
                if (jsonValue != null) {
                    when (val formField = fields[key]) {
                        is DateFormControl -> formField.value = (jsonValue.unsafeCast<String>()).toDateF()
                        is KFilesFormControl -> {
                            formField.value = Serialization.plain.decodeFromString(
                                ListSerializer(KFile.serializer()),
                                JSON.stringify(jsonValue)
                            )
                        }
                        else -> {
                            if (formField != null) {
                                formField.setValue(jsonValue)
                            } else {
                                dataMap[key] = jsonValue
                            }
                        }
                    }
                } else {
                    fields[key]?.setValue(null)
                }
            }
            fields.forEach { if (!keys.contains(it.key)) it.value.setValue(null) }
        } else {
            val map = model.unsafeCast<Map<String, Any?>>()
            map.forEach { (key, value) ->
                if (value != null) {
                    val formField = fields[key]
                    if (formField != null) {
                        formField.setValue(value)
                    } else {
                        dataMap[key] = value
                    }
                } else {
                    fields[key]?.setValue(null)
                }
            }
            fields.forEach { if (!map.contains(it.key)) it.value.setValue(null) }
        }
    }

    /**
     * Sets the values of all controls to null.
     */
    fun clearData() {
        dataMap.clear()
        fields.forEach { it.value.setValue(null) }
    }

    /**
     * Returns current data model.
     * @return data model
     */
    fun getData(): K {
        val map = dataMap + fields.entries.associateBy({ it.key }, { it.value.getValue() })
        return modelFactory?.invoke(map.withDefault { null }) ?: map.unsafeCast<K>()
    }

    /**
     * Returns current data model as JSON.
     * @return data model as JSON
     */
    fun getDataJson(): Json {
        return if (serializer != null) {
            JSON.parse(
                JsonInstance!!.encodeToString(
                    serializer,
                    getData()
                )
            )
        } else {
            getData().unsafeCast<Map<String, Any?>>().asJson()
        }
    }

    /**
     * Invokes validator function and validates the form.
     * @param markFields determines if form fields should be labeled with error messages
     * @return validation result
     */
    fun validate(markFields: Boolean = true): Boolean {
        val fieldWithError = fieldsParams.mapNotNull { entry ->
            fields[entry.key]?.let { control ->
                @Suppress("UNCHECKED_CAST")
                val fieldsParams = (entry.value as? FieldParams<FormControl>)
                val required = fieldsParams?.required ?: false
                val requiredError = control.getValue() == null && control.visible && required
                if (requiredError) {
                    if (markFields) control.validatorError = trans(fieldsParams?.requiredMessage) ?: "Value is required"
                    true
                } else {
                    val validatorPassed = !control.visible || (fieldsParams?.validator?.invoke(control) ?: true)
                    if (markFields) {
                        control.validatorError = if (!validatorPassed) {
                            trans(fieldsParams?.validatorMessage?.invoke(control)) ?: "Invalid value"
                        } else {
                            null
                        }
                    }
                    !validatorPassed
                }
            }
        }.find { it }
        val validatorPassed = validator?.invoke(this) ?: true
        panel?.validatorError = if (!validatorPassed) {
            trans(validatorMessage?.invoke(this)) ?: "Invalid form data"
        } else {
            null
        }
        return fieldWithError == null && validatorPassed
    }

    /**
     * Clear validation information from all fields.
     */
    fun clearValidation() {
        fieldsParams.forEach { entry ->
            fields[entry.key]?.let { control ->
                control.validatorError = null
            }
        }
        panel?.validatorError = null
    }

    companion object {
        inline fun <reified K : Any> create(
            panel: FormPanel<K>? = null,
            customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
            noinline init: (Form<K>.() -> Unit)? = null
        ): Form<K> {
            val form = Form(panel, serializer(), customSerializers)
            init?.invoke(form)
            return form
        }
    }
}

/**
 * Extension function to convert Map to JSON.
 * @return Json object
 */
fun Map<String, Any?>.asJson(): Json {
    val array = this.entries.map { it.component1() to it.component2() }.toTypedArray()
    @Suppress("SpreadOperator")
    return kotlin.js.json(*array)
}

/**
 * Extension function to convert JSON to Map.
 * @return map object
 */
fun Json.asMap(): Map<String, Any?> {
    val map = mutableMapOf<String, Any?>()
    @Suppress("UnsafeCastFromDynamic")
    for (key in js("Object").keys(this)) map[key] = this[key]
    return map
}
