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

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.encodeToDynamic
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import pl.treksoft.kvision.i18n.I18n.trans
import pl.treksoft.kvision.types.DateSerializer
import pl.treksoft.kvision.types.KFile
import pl.treksoft.kvision.types.toDateF
import pl.treksoft.kvision.types.toStringF
import pl.treksoft.kvision.utils.JSON
import pl.treksoft.kvision.utils.JSON.toObj
import kotlin.js.Date
import kotlin.js.Json
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.js.JSON as NativeJSON

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
@OptIn(ExperimentalSerializationApi::class)
@Suppress("TooManyFunctions", "UnsafeCastFromDynamic")
class Form<K : Any>(
    private val panel: FormPanel<K>? = null,
    private val serializer: KSerializer<K>? = null,
    private val customSerializers: Map<KClass<*>, KSerializer<*>>? = null
) {

    val modelFactory: ((Map<String, Any?>) -> K)?
    val jsonFactory: ((K) -> dynamic)?
    val fields: MutableMap<String, FormControl> = mutableMapOf()
    internal val fieldsParams: MutableMap<String, Any> = mutableMapOf()
    internal var validatorMessage: ((Form<K>) -> String?)? = null
    internal var validator: ((Form<K>) -> Boolean?)? = null

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
                kotlinx.serialization.json.Json {
                    serializersModule = SerializersModule {
                        contextual(Date::class, DateSerializer)
                        customSerializers?.forEach { (kclass, serializer) ->
                            contextual(kclass.unsafeCast<KClass<Any>>(), serializer.unsafeCast<KSerializer<Any>>())
                        }
                    }
                }.decodeFromString(serializer, kotlin.js.JSON.stringify(json))
            }
        }
        jsonFactory = serializer?.let {
            {
                kotlinx.serialization.json.Json {
                    serializersModule = SerializersModule {
                        contextual(Date::class, DateSerializer)
                        customSerializers?.forEach { (kclass, serializer) ->
                            contextual(kclass.unsafeCast<KClass<Any>>(), serializer.unsafeCast<KSerializer<Any>>())
                        }
                    }
                }.encodeToDynamic(serializer, it)
            }
        }
    }

    internal fun <C : FormControl> addInternal(
        key: KProperty1<K, *>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): Form<K> {
        this.fields[key.name] = control
        this.fieldsParams[key.name] = FieldParams(required, requiredMessage, validatorMessage, validator)
        return this
    }

    /**
     * Adds a string control to the form.
     * @param key key identifier of the control
     * @param control the string form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form
     */
    fun <C : StringFormControl> add(
        key: KProperty1<K, String?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): Form<K> {
        return addInternal(key, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a string control to the form bound to custom field type.
     * @param key key identifier of the control
     * @param control the string form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form
     */
    fun <C : StringFormControl> addCustom(
        key: KProperty1<K, Any?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): Form<K> {
        return addInternal(key, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a boolean control to the form.
     * @param key key identifier of the control
     * @param control the boolean form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form
     */
    fun <C : BoolFormControl> add(
        key: KProperty1<K, Boolean?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): Form<K> {
        return addInternal(key, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a number control to the form.
     * @param key key identifier of the control
     * @param control the number form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form
     */
    fun <C : NumberFormControl> add(
        key: KProperty1<K, Number?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): Form<K> {
        return addInternal(key, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a date control to the form.
     * @param key key identifier of the control
     * @param control the date form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form
     */
    fun <C : DateFormControl> add(
        key: KProperty1<K, Date?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): Form<K> {
        return addInternal(key, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Adds a files control to the form.
     * @param key key identifier of the control
     * @param control the files form control
     * @param required determines if the control is required
     * @param requiredMessage optional required validation message
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form
     */
    fun <C : KFilesFormControl> add(
        key: KProperty1<K, List<KFile>?>, control: C, required: Boolean = false, requiredMessage: String? = null,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): Form<K> {
        return addInternal(key, control, required, requiredMessage, validatorMessage, validator)
    }

    /**
     * Removes a control from the form.
     * @param key key identifier of the control
     * @return current form
     */
    fun remove(key: KProperty1<K, *>): Form<K> {
        this.fields.remove(key.name)
        return this
    }

    /**
     * Removes all controls from the form.
     * @return current form
     */
    fun removeAll(): Form<K> {
        this.fields.clear()
        return this
    }

    /**
     * Returns a control of given key.
     * @param key key identifier of the control
     * @return selected control
     */
    fun getControl(key: KProperty1<K, *>): FormControl? {
        return this.fields[key.name]
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
    fun setData(model: K) {
        fields.forEach { it.value.setValue(null) }
        val json = jsonFactory?.invoke(model) ?: throw IllegalStateException("Serializer not defined")
        for (key in js("Object").keys(json)) {
            val jsonValue = json[key]
            if (jsonValue != null) {
                when (val formField = fields[key]) {
                    is DateFormControl -> formField.value = (jsonValue.unsafeCast<String>()).toDateF()
                    is KFilesFormControl -> {
                        formField.value = JSON.plain.decodeFromString(
                            ListSerializer(KFile.serializer()),
                            kotlin.js.JSON.stringify(jsonValue)
                        )
                    }
                    else -> formField?.setValue(jsonValue)
                }
            }
        }
    }

    /**
     * Sets the values of all controls to null.
     */
    fun clearData() {
        fields.forEach { it.value.setValue(null) }
    }

    /**
     * Returns current data model.
     * @return data model
     */
    fun getData(): K {
        val map = fields.entries.associateBy({ it.key }, { it.value.getValue() })
        return modelFactory?.invoke(map.withDefault { null }) ?: throw IllegalStateException("Serializer not defined")
    }

    /**
     * Returns current data model as JSON.
     * @return data model as JSON
     */
    fun getDataJson(): Json {
        return if (serializer != null) {
            NativeJSON.parse(
                kotlinx.serialization.json.Json {
                    encodeDefaults = true
                    serializersModule = SerializersModule {
                        contextual(Date::class, DateSerializer)
                        customSerializers?.map {
                            contextual(it.key.unsafeCast<KClass<Any>>(), it.value.unsafeCast<KSerializer<Any>>())
                        }
                    }
                }.encodeToString(
                    serializer,
                    getData()
                )
            )
        } else {
            NativeJSON.parse("{}")
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
