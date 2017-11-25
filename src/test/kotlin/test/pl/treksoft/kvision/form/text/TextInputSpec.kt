package test.pl.treksoft.kvision.form.text

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.text.TEXTINPUTTYPE
import pl.treksoft.kvision.form.text.TextInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class TextInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ti = TextInput(type = TEXTINPUTTYPE.PASSWORD, value = "abc").apply {
                placeholder = "place"
                name = "name"
                maxlength = 15
                id = "idti"
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            assertEquals("<input class=\"form-control\" id=\"idti\" placeholder=\"place\" name=\"name\" maxlength=\"15\" disabled=\"\" type=\"password\" value=\"abc\">", element?.innerHTML, "Should render correct input control")
        }
    }

}