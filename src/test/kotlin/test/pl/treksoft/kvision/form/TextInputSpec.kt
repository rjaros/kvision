package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.TEXTINPUTTYPE
import pl.treksoft.kvision.form.TextInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class TextInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ti = TextInput(type = TEXTINPUTTYPE.PASSWORD, placeholder = "place", value = "abc", name = "name",
                    maxlength = 15, id = "idti", disabled = true)
            root.add(ti)
            val element = document.getElementById("test")
            assertEquals("<input class=\"form-control\" id=\"idti\" type=\"password\" placeholder=\"place\" value=\"abc\" name=\"name\" maxlength=\"15\" disabled=\"\">", element?.innerHTML, "Should render correct input field")
        }
    }

}