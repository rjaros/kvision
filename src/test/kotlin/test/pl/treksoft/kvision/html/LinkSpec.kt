package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.html.Link
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class LinkSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val link = Link("Google", "http://www.google.com")
            root.add(link)
            val element = document.getElementById("test")
            assertEquals(
                "<a href=\"http://www.google.com\">Google</a>",
                element?.innerHTML,
                "Should render correct html link"
            )
        }
    }

}