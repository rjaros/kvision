package pl.treksoft.kvision.remote

import org.testng.annotations.Test

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

class NameGeneratorKtTest {
    @Test
    fun createNameGenerator_generatesNamesWithPrefixAndCounter() {
        // setup
        val generator1 = createNameGenerator("some prefix")
        val generator2 = createNameGenerator("other prefix")

        // execution
        val actual1 = Array(3) { generator1() }
        val actual2 = Array(3) { generator2() }

        // evaluation
        assertThat(actual1, arrayContaining("some prefix0", "some prefix1", "some prefix2"))
        assertThat(actual2, arrayContaining("other prefix0", "other prefix1", "other prefix2"))
    }
}
