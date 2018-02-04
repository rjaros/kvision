package test.pl.treksoft.kvision.form.check

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.check.CHECKBOXSTYLE
import pl.treksoft.kvision.form.check.CheckBox
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckBoxSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ci = CheckBox(value = true, label = "Label").apply {
                name = "name"
                style = CHECKBOXSTYLE.DANGER
                disabled = true
                circled = true
                inline = true
            }
            root.add(ci)
            val element = document.getElementById("test")
            val id = ci.input.id
            assertEquals(
                "<div class=\"checkbox checkbox-danger checkbox-circle checkbox-inline\"><input class=\"styled\" id=\"$id\" type=\"checkbox\" checked=\"\" name=\"name\" disabled=\"\"><label for=\"$id\">Label</label></div>",
                element?.innerHTML,
                "Should render correct checkbox form control"
            )
            ci.style = CHECKBOXSTYLE.INFO
            ci.circled = false
            ci.inline = false
            assertEquals(
                "<div class=\"checkbox checkbox-info\"><input class=\"styled\" id=\"$id\" type=\"checkbox\" checked=\"\" name=\"name\" disabled=\"\"><label for=\"$id\">Label</label></div>",
                element?.innerHTML,
                "Should render correct checkbox form control"
            )
        }
    }

}