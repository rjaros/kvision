package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.CheckBoxInput
import pl.treksoft.kvision.form.TEXTINPUTTYPE
import pl.treksoft.kvision.form.TextInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckBoxInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ci = CheckBoxInput(value = true, name = "name", id = "idti", disabled = true)
            root.add(ci)
            val element = document.getElementById("test")
            assertEquals("<input id=\"idti\" type=\"checkbox\" checked=\"\" name=\"name\" disabled=\"\">", element?.innerHTML, "Should render correct checkbox field")
        }
    }

}