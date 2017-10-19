package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.Password
import pl.treksoft.kvision.form.Text
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ti = Password(placeholder = "place", value = "abc", name = "name",
                    maxlength = 15, disabled = true, label = "Label")
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            assertEquals("<div class=\"form-group\"><label for=\"$id\">Label</label><input class=\"form-control\" id=\"$id\" type=\"password\" placeholder=\"place\" value=\"abc\" name=\"name\" maxlength=\"15\" disabled=\"\"></div>", element?.innerHTML, "Should render correct input form field")
        }
    }

}