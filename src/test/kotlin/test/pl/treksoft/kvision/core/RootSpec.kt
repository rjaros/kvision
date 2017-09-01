package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.core.Root
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
            assertTrue("Standard root container has correct css class") { rootElem?.className == "container" }
        }
    }

    @Test
    fun getSnClass_Fluid() {
        run {
            Root("test", fluid = true)
            val rootElem = document.getElementById("test")
            assertTrue("Fluid root container has correct css class") { rootElem?.className == "container-fluid" }
        }
    }

}