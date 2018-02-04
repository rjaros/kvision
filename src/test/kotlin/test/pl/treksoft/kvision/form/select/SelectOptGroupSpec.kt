package test.pl.treksoft.kvision.form.select

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.select.SelectOptGroup
import pl.treksoft.kvision.form.select.SelectOption
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectOptGroupSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val selectOptGroup = SelectOptGroup("Group", listOf("test1" to "Test 1", "test2" to "Test 2"), 2)
            root.add(selectOptGroup)
            val element = document.getElementById("test")
            assertEquals(
                "<optgroup label=\"Group\" data-max-options=\"2\"><option value=\"test1\">Test 1</option><option value=\"test2\">Test 2</option></optgroup>",
                element?.innerHTML,
                "Should render correct select option group"
            )
            selectOptGroup.add(SelectOption("test3", "Test 3"))
            assertEquals(
                "<optgroup label=\"Group\" data-max-options=\"2\"><option value=\"test1\">Test 1</option><option value=\"test2\">Test 2</option><option value=\"test3\">Test 3</option></optgroup>",
                element?.innerHTML,
                "Should render correct select option group with added option"
            )
        }
    }

}