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
package pl.treksoft.kvision.remote

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class JacksonObjectDeSerializerKtTest {
    private val deSerializer = jacksonObjectDeSerializer()

    @Test(dataProvider = "provide_obj_expectedString")
    fun serialize_serializesAsExpected(obj: Any?, expectedString: String?) {
        // execution
        val actual = deSerializer.serializeNullableToString(obj)

        // evaluation
        assertThat(actual, equalTo(expectedString))
    }

    @Test(dataProvider = "provide_string_type_expectedObject")
    fun deserialize_deserializesAsExpected(str: String?, type: Class<*>, expectedObject: Any?) {
        // execution
        val actual = deSerializer.deserialize(str, type)

        // evaluation
        assertThat(actual, equalTo(expectedObject))
    }
    
    @DataProvider
    fun provide_obj_expectedString(): Array<Array<Any?>> = arrayOf(
        arrayOf("simple string", "\"simple string\""),
        arrayOf("special {[]} chars", "\"special {[]} chars\""),
        arrayOf(42, "42"),
        arrayOf("firstValue" to "secondValue", """{"first":"firstValue","second":"secondValue"}"""),
        arrayOf(null, null)
    )

    @DataProvider
    fun provide_string_type_expectedObject(): Array<Array<Any?>> = arrayOf(
        arrayOf("simple string", String::class.java, "simple string"),
        arrayOf("special {[]} chars", String::class.java, "special {[]} chars"),
        arrayOf("42", Integer::class.java, 42),
        arrayOf("""{"first":"firstValue","second":"secondValue"}""", Pair::class.java, "firstValue" to "secondValue"),
        arrayOf(null, String::class.java, null),
        arrayOf(null, Integer::class.java, null),
    )
}
