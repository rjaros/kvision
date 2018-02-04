package test.pl.treksoft.kvision.form.time

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.time.DateTime
import pl.treksoft.kvision.utils.toStringF
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.js.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val data = Date()
            val ti = DateTime(value = data, label = "Label").apply {
                placeholder = "place"
                name = "name"
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            val datastr = data.toStringF(ti.format)
            assertEquals(
                "<div class=\"form-group\"><label class=\"control-label\" for=\"$id\">Label</label><input class=\"form-control\" id=\"$id\" type=\"text\" placeholder=\"place\" name=\"name\" disabled=\"\" value=\"$datastr\"></div>",
                element?.innerHTML,
                "Should render correct date time input form control"
            )
            ti.validatorError = "Validation Error"
            assertEquals(
                "<div class=\"form-group has-error\"><label class=\"control-label\" for=\"$id\">Label</label><input class=\"form-control\" id=\"$id\" type=\"text\" placeholder=\"place\" name=\"name\" disabled=\"\" value=\"$datastr\"><span class=\"help-block small\">Validation Error</span></div>",
                element?.innerHTML,
                "Should render correct date time input form control with validation error"
            )
        }
    }

}