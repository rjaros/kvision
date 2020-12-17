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
        arrayOf("simple string", "simple string"),
        arrayOf("special {[]} chars", "special {[]} chars"),
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
