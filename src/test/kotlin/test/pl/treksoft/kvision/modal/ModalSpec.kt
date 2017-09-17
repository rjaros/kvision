package test.pl.treksoft.kvision.modal

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.modal.Modal
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ModalSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test")
            val modal = Modal("Modal")
            modal.show()
            val content = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            modal.hide()
            assertEquals("Modal", content, "Should render correct modal")
        }
    }

    @Test
    fun toggle() {
        run {
            Root("test")
            val modal = Modal("Modal")
            modal.toggle()
            val content = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            assertEquals("Modal", content, "Should show modal after toggle")
            modal.toggle()
            val content2 = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            assertNull(content2, "Should hide modal after second toggle")
        }
    }

}