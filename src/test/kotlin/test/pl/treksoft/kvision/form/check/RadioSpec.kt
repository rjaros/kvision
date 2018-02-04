package test.pl.treksoft.kvision.form.check

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.check.RADIOSTYLE
import pl.treksoft.kvision.form.check.Radio
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class RadioSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ci = Radio(value = true, label = "Label", extraValue = "abc").apply {
                name = "name"
                style = RADIOSTYLE.DANGER
                disabled = true
                inline = true
            }
            root.add(ci)
            val element = document.getElementById("test")
            val id = ci.input.id
            assertEquals(
                "<div class=\"radio radio-danger radio-inline\"><input id=\"$id\" type=\"radio\" checked=\"\" name=\"name\" disabled=\"\" value=\"abc\"><label for=\"$id\">Label</label></div>",
                element?.innerHTML,
                "Should render correct radio button form control"
            )
            ci.style = RADIOSTYLE.INFO
            ci.squared = true
            ci.inline = false
            assertEquals(
                "<div class=\"checkbox kv-radio-checkbox checkbox-info\"><input id=\"$id\" type=\"radio\" checked=\"\" name=\"name\" disabled=\"\" value=\"abc\"><label for=\"$id\">Label</label></div>",
                element?.innerHTML,
                "Should render correct radio button form control"
            )
        }
    }

}