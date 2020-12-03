package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.core.ClassSetBuilderImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ClassSetBuilderImplSpec {
    @Test
    fun addNothing_returnsEmptySet() {
        // execution
        val actual = ClassSetBuilderImpl().classes

        // evaluation
        assertEquals(actual.sorted().joinToString(), "")
    }

    @Test
    fun add_addsValueToSet() {
        // execution
        val actual = ClassSetBuilderImpl().also {
            it.add("value1")
            it.add("value2")
        }.classes

        // evaluation
        assertEquals("value1,value2", actual.sorted().joinToString(","))
    }

    @Test
    fun addAll_addsValuesToSet() {
        // execution
        val actual = ClassSetBuilderImpl().also {
            it.addAll(listOf("value1", "value2"))
        }.classes

        // evaluation
        assertEquals("value1,value2", actual.sorted().joinToString(","))
    }

    @Test
    fun addAfterQueryingValue_doesNotChanceValue() {
        // setup
        val builder = ClassSetBuilderImpl()
        builder.add("value1")

        // execution
        val actual = builder.classes
        builder.add("value2")

        // evaluation
        assertEquals("value1", actual.sorted().joinToString(","))
    }
}
