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

import kotlin.js.Date
import kotlin.js.Json

/**
 * Internal data class containing form field parameters.
 */
internal data class FieldParams<in F : FormControl>(
    val required: Boolean = false,
    val validatorMessage: ((F) -> String?)? = null,
    val validator: ((F) -> Boolean?)? = null
)

/**
 * The form definition class. Can be used directly or indirectly inside a [FormPanel].
 *
 * @constructor Creates a form with a given modelFactory function
 * @param K model class type
 * @param panel optional instance of [FormPanel]
 * @param modelFactory function transforming a Map<String, Any?> to a data model of class K
 */
class Form<K>(private val panel: FormPanel<K>? = null, private val modelFactory: (Map<String, Any?>) -> K) {

    internal val fields: MutableMap<String, FormControl> = mutableMapOf()
    internal val fieldsParams: MutableMap<String, Any> = mutableMapOf()
    internal var validatorMessage: ((Form<K>) -> String?)? = null
    internal var validator: ((Form<K>) -> Boolean?)? = null

    /**
     * Adds a control to the form.
     * @param key key identifier of the control
     * @param control the form control
     * @param required determines if the control is required
     * @param validatorMessage optional function returning validation message
     * @param validator optional validation function
     * @return current form
     */
    fun <C : FormControl> add(
        key: String, control: C, required: Boolean = false,
        validatorMessage: ((C) -> String?)? = null,
        validator: ((C) -> Boolean?)? = null
    ): Form<K> {
        this.fields[key] = control
        this.fieldsParams[key] = FieldParams(required, validatorMessage, validator)
        return this
    }

    /**
     * Removes a control from the form.
     * @param key key identifier of the control
     * @return current form
     */
    fun remove(key: String): Form<K> {
        this.fields.remove(key)
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
    fun getControl(key: String): FormControl? {
        return this.fields[key]
    }

    /**
     * Returns a value of the control of given key.
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
        fields.forEach { it.value.setValue(null) }
        val map = model.asDynamic().map as? Map<String, Any?>
        if (map != null) {
            map.forEach {
                fields[it.key]?.setValue(it.value)
            }
        } else {
            for (key in js("Object").keys(model)) {
                @Suppress("UnsafeCastFromDynamic")
                fields[key]?.setValue(model.asDynamic()[key])
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
        return modelFactory(map.withDefault { null })
    }

    /**
     * Returns current data model as JSON.
     * @return data model as JSON
     */
    fun getDataJson(): Json {
        return fields.entries.associateBy({ it.key }, { it.value.getValue() }).asJson()
    }

    /**
     * Invokes validator function and validates the form.
     * @return validation result
     */
    fun validate(): Boolean {
        val fieldWithError = fieldsParams.mapNotNull { entry ->
            fields[entry.key]?.let { control ->
                @Suppress("UNCHECKED_CAST")
                val fieldsParams = (entry.value as? FieldParams<FormControl>)
                val required = fieldsParams?.required ?: false
                val requiredError = control.getValue() == null && required
                if (requiredError) {
                    control.validatorError = "Value is required"
                    true
                } else {
                    val validatorPassed = fieldsParams?.validator?.invoke(control) ?: true
                    control.validatorError = if (!validatorPassed) {
                        fieldsParams?.validatorMessage?.invoke(control) ?: "Invalid value"
                    } else {
                        null
                    }
                    !validatorPassed
                }
            }
        }.find { it }
        val validatorPassed = validator?.invoke(this) ?: true
        panel?.validatorError = if (!validatorPassed) {
            validatorMessage?.invoke(this) ?: "Invalid form data"
        } else {
            null
        }
        return fieldWithError == null && validatorPassed
    }
}

/**
 * Returns given value from the map as a String.
 */
fun Map<String, Any?>.string(key: String): String? = this[key] as? String

/**
 * Returns given value from the map as a Number.
 */
fun Map<String, Any?>.number(key: String): Number? = this[key] as? Number

/**
 * Returns given value from the map as a Boolean.
 */
fun Map<String, Any?>.bool(key: String): Boolean? = this[key] as? Boolean

/**
 * Returns given value from the map as a Date.
 */
fun Map<String, Any?>.date(key: String): Date? = this[key] as? Date

/**
 * Returns map values in JSON format.
 */
fun Map<String, Any?>.asJson(): Json {
    val array = this.entries.map { it.component1() to it.component2() }.toTypedArray()
    @Suppress("SpreadOperator")
    return kotlin.js.json(*array)
}
