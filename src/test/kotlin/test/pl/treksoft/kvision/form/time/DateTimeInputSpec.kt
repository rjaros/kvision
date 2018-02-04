package test.pl.treksoft.kvision.form.time

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.time.DateTimeInput
import pl.treksoft.kvision.utils.toStringF
import test.pl.treksoft.kvision.DomSpec
import kotlin.js.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val data = Date()
            val dti = DateTimeInput(value = data).apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(dti)
            val value = dti.getElementJQuery()?.`val`()
            assertEquals(
                data.toStringF(dti.format),
                value,
                "Should render date time input with correctly formatted value"
            )
        }
    }

}