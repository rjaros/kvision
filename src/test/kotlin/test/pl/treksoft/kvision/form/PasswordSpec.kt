package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.Password
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ti = Password(value = "abc", label = "Label").apply {
                placeholder = "place"
                name = "name"
                maxlength = 15
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            assertEquals("<div class=\"form-group\"><label for=\"$id\">Label</label><input class=\"form-control\" id=\"$id\" placeholder=\"place\" name=\"name\" maxlength=\"15\" disabled=\"\" type=\"password\" value=\"abc\"></div>", element?.innerHTML, "Should render correct input form field")
        }
    }

}