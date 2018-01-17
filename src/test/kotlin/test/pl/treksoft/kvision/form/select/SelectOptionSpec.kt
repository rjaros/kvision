package test.pl.treksoft.kvision.form.select

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.select.SelectOption
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectOptionSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val selectOption = SelectOption("testValue", "testLabel")
            root.add(selectOption)
            val element = document.getElementById("test")
            assertEquals(
                "<option value=\"testValue\">testLabel</option>",
                element?.innerHTML,
                "Should render correct select option"
            )
            selectOption.icon = "fa-flag"
            assertEquals(
                "<option value=\"testValue\" data-icon=\"fa fa-flag\">testLabel</option>",
                element?.innerHTML,
                "Should render correct select option with icon"
            )
            selectOption.divider = true
            assertEquals(
                "<option data-divider=\"true\"></option>",
                element?.innerHTML,
                "Should render correct divider option"
            )
        }
    }

}