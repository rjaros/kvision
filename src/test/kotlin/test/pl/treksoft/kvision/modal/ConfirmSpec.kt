package test.pl.treksoft.kvision.modal

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.modal.Confirm
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ConfirmSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test")
            Confirm.show("Confirm caption", "Confirm content")
            val confirm = document.getElementById("test")?.let { jQuery(it).find(".modal")[0] }
            assertNotNull(confirm, "Should show confirm window")
            val title = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            assertEquals("Confirm caption", title, "Should render confirm window with correct caption")
            val body = document.getElementById("test")?.let { jQuery(it).find(".modal-body").html() }
            assertEquals("<div>Confirm content</div>", body, "Should render confirm window with correct content")
            val buttons = document.getElementById("test")?.let { jQuery(it).find(".modal-footer").find("button") }
            assertEquals(2, buttons?.length, "Should render confirm window with two buttons")
            val button = document.getElementById("test")?.let { jQuery(it).find(".modal-footer").find("button")[0] }
            button?.click()
            val confirm2 = document.getElementById("test")?.let { jQuery(it).find(".modal")[0] }
            assertNull(confirm2, "Should hide confirm  window after clicking YES")
        }
    }
}