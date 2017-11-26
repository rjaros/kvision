package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.HelpBlock
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class HelpBlockSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val fl = HelpBlock("Form Error")
            root.add(fl)
            val element = document.getElementById("test")
            assertEquals("<span class=\"help-block small\">Form Error</span>", element?.innerHTML, "Should render correct help block")
        }
    }

}