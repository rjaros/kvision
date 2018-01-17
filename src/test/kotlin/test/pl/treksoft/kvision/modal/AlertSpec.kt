package test.pl.treksoft.kvision.modal

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.modal.Alert
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AlertSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test")
            Alert.show("Alert caption", "Alert content")
            val alert = document.getElementById("test")?.let { jQuery(it).find(".modal")[0] }
            assertNotNull(alert, "Should show alert window")
            val title = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            assertEquals("Alert caption", title, "Should render alert window with correct caption")
            val body = document.getElementById("test")?.let { jQuery(it).find(".modal-body").html() }
            assertEquals("<span>Alert content</span>", body, "Should render alert window with correct content")
            val footer = document.getElementById("test")?.let { jQuery(it).find(".modal-footer").html() }
            assertEquals(
                "<button class=\"btn btn-primary\" type=\"button\"><span class=\"glyphicon glyphicon-ok\"></span> OK</button>",
                footer,
                "Should render alert window with correct footer"
            )
            val button = document.getElementById("test")?.let { jQuery(it).find(".modal-footer").find("button") }
            button?.click()
            val alert2 = document.getElementById("test")?.let { jQuery(it).find(".modal")[0] }
            assertNull(alert2, "Should hide alert window after clicking OK")
        }
    }
}
