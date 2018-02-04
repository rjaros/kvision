package test.pl.treksoft.kvision.modal

import pl.treksoft.kvision.modal.CloseIcon
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class CloseIconSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ci = CloseIcon()
            root.add(ci)
            val element = document.getElementById("test")
            assertEquals(
                "<button class=\"close\" type=\"button\" aria-label=\"Close\"><span aria-hidden=\"true\">×</span></button>",
                element?.innerHTML,
                "Should render correct close icon"
            )
        }
    }

}