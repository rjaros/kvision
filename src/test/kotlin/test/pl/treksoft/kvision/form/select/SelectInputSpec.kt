package test.pl.treksoft.kvision.form.select

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.select.SELECTWIDTHTYPE
import pl.treksoft.kvision.form.select.SelectInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class SelectInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val selectInput = SelectInput(listOf("test1" to "Test 1", "test2" to "Test 2"), "test1", true).apply {
                liveSearch = true
                placeholder = "Choose ..."
                selectWidthType = SELECTWIDTHTYPE.FIT
                emptyOption = true
            }
            root.add(selectInput)
            val element = document.getElementById("test")
            assertTrue(
                true == element?.innerHTML?.endsWith("<select class=\"selectpicker\" multiple=\"\" data-live-search=\"true\" title=\"Choose ...\" data-style=\"btn-default\" data-width=\"fit\" tabindex=\"-98\"><option value=\"#kvnull\"></option><option value=\"test1\">Test 1</option><option value=\"test2\">Test 2</option></select></div>"),
                "Should render correct select input"
            )
        }
    }

}