package pl.treksoft.kvision.form

import kotlin.js.Date

data class FieldParams<in F : FormControl>(val required: Boolean = false,
                                           val validatorMessage: ((F) -> String?)? = null,
                                           val validator: ((F) -> Boolean?)? = null)

open class Form<K>(private val panel: FormPanel<K>? = null, private val modelFactory: (Map<String, Any?>) -> K) {

    internal val fields: MutableMap<String, FormControl> = mutableMapOf()
    internal val fieldsParams: MutableMap<String, Any> = mutableMapOf()
    internal var validatorMessage: ((Form<K>) -> String?)? = null
    internal var validator: ((Form<K>) -> Boolean?)? = null

    open fun <C : FormControl> add(key: String, control: C, required: Boolean = false,
                                   validatorMessage: ((C) -> String?)? = null,
                                   validator: ((C) -> Boolean?)? = null): Form<K> {
        this.fields.put(key, control)
        this.fieldsParams.put(key, FieldParams(required, validatorMessage, validator))
        return this
    }

    open fun remove(key: String): Form<K> {
        this.fields.remove(key)
        return this
    }

    open fun removeAll(): Form<K> {
        this.fields.clear()
        return this
    }

    open fun getControl(key: String): FormControl? {
        return this.fields[key]
    }

    operator fun get(key: String): Any? {
        return getControl(key)?.getValue()
    }

    open fun setData(data: K) {
        fields.forEach { it.value.setValue(null) }
        val map = data.asDynamic().map as? Map<String, Any?>
        if (map != null) {
            map.forEach {
                fields[it.key]?.setValue(it.value)
            }
        } else {
            for (key in js("Object").keys(data)) {
                @Suppress("UnsafeCastFromDynamic")
                fields[key]?.setValue(data.asDynamic()[key])
            }
        }
    }

    open fun getData(): K {
        val map = fields.entries.associateBy({ it.key }, { it.value.getValue() })
        return modelFactory(map.withDefault { null })
    }

    open fun validate(): Boolean {
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
            panel?.validatorMessage?.invoke(this) ?: "Invalid form data"
        } else {
            null
        }
        return fieldWithError == null && validatorPassed
    }
}

fun Map<String, Any?>.string(key: String): String? = this[key] as? String
fun Map<String, Any?>.number(key: String): Number? = this[key] as? Number
fun Map<String, Any?>.bool(key: String): Boolean? = this[key] as? Boolean
fun Map<String, Any?>.date(key: String): Date? = this[key] as? Date
