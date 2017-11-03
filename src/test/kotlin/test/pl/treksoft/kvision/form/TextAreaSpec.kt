package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.TextArea
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class TextAreaSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ti = TextArea(cols = 5, rows = 2, value = "abc", label = "Label").apply {
                placeholder = "place"
                name = "name"
                maxlength = 15
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            assertEquals("<div class=\"form-group\"><label for=\"$id\">Label</label><textarea class=\"form-control\" id=\"$id\" placeholder=\"place\" name=\"name\" maxlength=\"15\" disabled=\"\" cols=\"5\" rows=\"2\">abc</textarea></div>", element?.innerHTML, "Should render correct input form field")
        }
    }

}