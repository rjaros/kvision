package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.html.BUTTONSIZE
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class ButtonSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val button = Button("Cancel", "fa-bars", BUTTONSTYLE.PRIMARY)
            button.size = BUTTONSIZE.LARGE
            button.block = true
            root.add(button)
            val element = document.getElementById("test")
            assertEquals("<button class=\"btn btn-primary btn-lg btn-block\" type=\"button\"><i class=\"fa fa-bars fa-lg\"></i> Cancel</button>", element?.innerHTML, "Should render correct html button")
        }
    }

}