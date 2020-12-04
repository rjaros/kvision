package test.pl.treksoft.kvision.core

import com.github.snabbdom.Attrs
import pl.treksoft.kvision.core.AttributeSetBuilderImpl
import pl.treksoft.kvision.core.buildAttributeSet
import test.pl.treksoft.kvision.toKeyValuePairString
import kotlin.test.Test
import kotlin.test.assertEquals

class AttributeSetBuilderImplSpec {
    @Test
    fun addNothing_returnsEmptyMap() {
        // execution
        val actual: Attrs = AttributeSetBuilderImpl().attributes

        // evaluation
        assertEquals("", toKeyValuePairString(actual))
    }

    @Test
    fun add_addsValueToSet() {
        // execution
        val actual: Attrs = AttributeSetBuilderImpl().also {
            it.add("key1")
            it.add("key2", "value2")
        }.attributes

        // evaluation
        assertEquals("key1=key1,key2=value2", toKeyValuePairString(actual))
    }

    @Test
    fun addAll_addsValuesToSet() {
        // execution
        val actual: Attrs = AttributeSetBuilderImpl().also {
            it.addAll(mapOf("key1" to "value1", "key2" to "value2"))
        }.attributes

        // evaluation
        assertEquals("key1=value1,key2=value2", toKeyValuePairString(actual))
    }

    @Test
    fun addAfterQueryingValue_doesNotChanceValue() {
        // setup
        val builder = AttributeSetBuilderImpl()
        builder.add("value1")

        // execution
        val actual: Attrs = builder.attributes
        builder.add("value2")

        // evaluation
        assertEquals("value1=value1", toKeyValuePairString(actual))
    }

    @Test
    fun buildAttributeSet_buildsUsingSuppliedFunction() {
        // execution
        val actual: Attrs = buildAttributeSet { it.add("value") }

        // evaluation
        assertEquals("value=value", toKeyValuePairString(actual))
    }
}


