package test.pl.treksoft.kvision.form.text

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.text.TextAreaInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class TextAreaInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ti = TextAreaInput(cols = 5, rows = 2, value = "abc").apply {
                placeholder = "place"
                name = "name"
                maxlength = 15
                id = "idti"
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            assertEquals("<textarea class=\"form-control\" id=\"idti\" placeholder=\"place\" name=\"name\" maxlength=\"15\" disabled=\"\" cols=\"5\" rows=\"2\">abc</textarea>", element?.innerHTML, "Should render correct input field")
        }
    }

}