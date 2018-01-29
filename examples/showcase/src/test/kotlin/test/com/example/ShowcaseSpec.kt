package test.com.example

import com.example.Showcase
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class ShowcaseSpec : DomSpec {

    @Test
    fun render() {
        run {
            Showcase().start(mapOf())
            val element = document.getElementById("showcase")
            assertTrue(true, "True"
            )
        }
    }
}
