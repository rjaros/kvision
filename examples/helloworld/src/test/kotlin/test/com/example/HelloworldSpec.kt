package test.com.example

import com.example.Helloworld
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class HelloworldSpec : DomSpec {

    @Test
    fun render() {
        run {
            Helloworld().start(mapOf())
            val element = document.getElementById("helloworld")
            assertTrue(
                element?.innerHTML?.contains("Hello world!") ?: false,
                "Application should render Hello world! text"
            )
        }
    }
}
