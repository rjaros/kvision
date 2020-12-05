/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
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


