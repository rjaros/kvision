package test.pl.treksoft.kvision.form.check

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.check.CHECKINPUTTYPE
import pl.treksoft.kvision.form.check.CheckInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ci = CheckInput(value = true).apply {
                name = "name"
                id = "idti"
                disabled = true
            }
            root.add(ci)
            val element = document.getElementById("test")
            assertEquals(
                "<input id=\"idti\" type=\"checkbox\" checked=\"\" name=\"name\" disabled=\"\">",
                element?.innerHTML,
                "Should render correct checkbox control"
            )
        }
    }

    @Test
    fun renderAsRadio() {
        run {
            val root = Root("test")
            val ci = CheckInput(type = CHECKINPUTTYPE.RADIO, value = true).apply {
                name = "name"
                id = "idti"
                extraValue = "abc"
            }
            root.add(ci)
            val element = document.getElementById("test")
            assertEquals(
                "<input id=\"idti\" type=\"radio\" checked=\"\" name=\"name\" value=\"abc\">",
                element?.innerHTML,
                "Should render correct radio button control"
            )
        }
    }

}