package pl.treksoft.kvision.core

import com.github.snabbdom.Attrs
import pl.treksoft.kvision.utils.snAttrs

interface AttributeSetBuilder {
    fun add(name: String, value: String = name)
    fun addAll(attributes: Map<String, String>) {
        attributes.forEach { (key, value) -> add(key, value) }
    }
    fun add(attribute: DomAttribute?) {
        if (attribute != null) {
            add(attribute.attributeName, attribute.attributeValue)
        }
    }
}

internal class AttributeSetBuilderImpl : AttributeSetBuilder {
    val attributes: Attrs
        get() = snAttrs(_attributes)

    private val _attributes: MutableMap<String, String> = HashMap()

    override fun add(name: String, value: String) {
        _attributes.put(name, value)
    }

    override fun addAll(attributes: Map<String, String>) {
        this._attributes.putAll(attributes)
    }
}

fun buildAttributeSet(delegate: (builder: AttributeSetBuilder) -> Unit): Attrs =
    AttributeSetBuilderImpl().also { delegate(it) }.attributes
