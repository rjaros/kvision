package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.modal.Modal
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class RootSpec : DomSpec {

    @Test
    fun getSnClass() {
        run {
            Root("test")
            val rootElem = document.getElementById("test")
            assertTrue("Standard root container has correct css class") { rootElem?.className == "container-fluid" }
        }
    }

    @Test
    fun getSnClass_Fluid() {
        run {
            Root("test", fixed = true)
            val rootElem = document.getElementById("test")
            assertTrue("Fluid root container has correct css class") { rootElem?.className == "container" }
        }
    }

    @Test
    fun getRoot() {
        run {
            val root = Root("test")
            val r = root.getRoot()
            assertTrue("Should return self") { r == root }
        }
    }

    @Test
    fun addModal() {
        run {
            val root = Root("test")
            val modal = Modal("test")
            modal.id = "test_modal"
            root.addModal(modal)
            modal.show()
            val elem = document.getElementById("test_modal")
            assertTrue("Should render standard modal") { elem != null }
            modal.hide()
        }
    }
}