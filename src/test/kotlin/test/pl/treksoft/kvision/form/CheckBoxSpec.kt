package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.CHECKBOXSTYLE
import pl.treksoft.kvision.form.CheckBox
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckBoxSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ci = CheckBox(value = true, name = "name", style = CHECKBOXSTYLE.DANGER, disabled = true, circled = true,
                    inline = true, label = "Label")
            root.add(ci)
            val element = document.getElementById("test")
            val id = ci.input.id
            assertEquals("<div class=\"checkbox checkbox-danger checkbox-circle checkbox-inline\"><input class=\"styled\" id=\"$id\" type=\"checkbox\" checked=\"\" name=\"name\" disabled=\"\"><label for=\"$id\">Label</label></div>", element?.innerHTML, "Should render correct checkbox form field")
            ci.style = CHECKBOXSTYLE.INFO
            ci.circled = false
            ci.inline = false
            assertEquals("<div class=\"checkbox checkbox-info\"><input class=\"styled\" id=\"$id\" type=\"checkbox\" checked=\"\" name=\"name\" disabled=\"\"><label for=\"$id\">Label</label></div>", element?.innerHTML, "Should render correct checkbox form field")
        }
    }

}