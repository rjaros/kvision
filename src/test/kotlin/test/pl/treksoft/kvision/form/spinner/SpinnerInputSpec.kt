package test.pl.treksoft.kvision.form.spinner

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.spinner.SpinnerInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class SpinnerInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val si = SpinnerInput(value = 13).apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(si)
            val value = si.getElementJQuery()?.`val`()
            assertEquals("13", value, "Should render spinner input with correct value")
        }
    }

    @Test
    fun spinUp() {
        run {
            val root = Root("test")
            val si = SpinnerInput(value = 13).apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinUp")
            si.spinUp()
            assertEquals(14, si.value, "Should return changed value after spinUp")
        }
    }

    @Test
    fun spinDown() {
        run {
            val root = Root("test")
            val si = SpinnerInput(value = 13).apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinDown")
            si.spinDown()
            assertEquals(12, si.value, "Should return changed value after spinDown")
        }
    }
}