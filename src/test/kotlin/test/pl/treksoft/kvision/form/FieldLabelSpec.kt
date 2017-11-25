package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.FieldLabel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldLabelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val fl = FieldLabel("input", "Label")
            root.add(fl)
            val element = document.getElementById("test")
            assertEquals("<label class=\"control-label\" for=\"input\">Label</label>", element?.innerHTML, "Should render correct label")
        }
    }

}