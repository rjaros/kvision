package test.pl.treksoft.kvision.form.check

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.check.Radio
import pl.treksoft.kvision.form.check.RadioGroup
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class RadioGroupSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ci = RadioGroup(options = listOf("a" to "A", "b" to "B"), value = "a", label = "Label").apply {
                disabled = true
                inline = true
            }
            root.add(ci)
            val element = document.getElementById("test")
            val id = ci.flabel.forId
            val rid1 = ci.getChildren().filterIsInstance<Radio>().first().input.id
            val rid2 = ci.getChildren().filterIsInstance<Radio>().last().input.id
            assertEquals("<div class=\"form-group kv-radiogroup-inline\"><label class=\"control-label\" for=\"$id\">Label</label><div class=\"radio\"><input id=\"$rid1\" type=\"radio\" name=\"$id\" disabled=\"\" value=\"a\"><label for=\"$rid1\">A</label></div><div class=\"radio\"><input id=\"$rid2\" type=\"radio\" name=\"$id\" disabled=\"\" value=\"b\"><label for=\"$rid2\">B</label></div></div>", element?.innerHTML, "Should render correct radio button group form control")
        }
    }

}