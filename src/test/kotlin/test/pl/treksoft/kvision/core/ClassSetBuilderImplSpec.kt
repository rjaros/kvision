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

import pl.treksoft.kvision.core.ClassSetBuilderImpl
import pl.treksoft.kvision.core.buildClassSet
import pl.treksoft.kvision.test.toKeyValuePairString
import kotlin.test.Test
import kotlin.test.assertEquals

class ClassSetBuilderImplSpec {
    @Test
    fun addNothing_returnsEmptySet() {
        // execution
        val actual = ClassSetBuilderImpl().classes

        // evaluation
        assertEquals("", toKeyValuePairString(actual), "")
    }

    @Test
    fun add_addsValueToSet() {
        // execution
        val actual = ClassSetBuilderImpl().also {
            it.add("value1")
            it.add("value2")
        }.classes

        // evaluation
        assertEquals("value1=true,value2=true", toKeyValuePairString(actual))
    }

    @Test
    fun addAll_addsValuesToSet() {
        // execution
        val actual = ClassSetBuilderImpl().also {
            it.addAll(listOf("value1", "value2"))
        }.classes

        // evaluation
        assertEquals("value1=true,value2=true", toKeyValuePairString(actual))
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
        assertEquals("value1=true", toKeyValuePairString(actual))
    }

    @Test
    fun buildClassSet_buildsClassesUsingGivenFunction() {
        // execution
        val actual = buildClassSet {
            it.add("value1")
            it.add("value2")
        }

        // evaluation
        assertEquals("value1=true,value2=true", toKeyValuePairString(actual))
    }
}
