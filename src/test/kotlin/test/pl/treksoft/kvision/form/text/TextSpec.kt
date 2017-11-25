package test.pl.treksoft.kvision.form.text

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.text.Text
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class TextSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ti = Text(value = "abc", label = "Label").apply {
                placeholder = "place"
                name = "name"
                maxlength = 15
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            assertEquals("<div class=\"form-group\"><label class=\"control-label\" for=\"$id\">Label</label><input class=\"form-control\" id=\"$id\" placeholder=\"place\" name=\"name\" maxlength=\"15\" disabled=\"\" type=\"text\" value=\"abc\"></div>", element?.innerHTML, "Should render correct input form control")
        }
    }

}