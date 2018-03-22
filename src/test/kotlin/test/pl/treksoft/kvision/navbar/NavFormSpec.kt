/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.navbar

import pl.treksoft.kvision.navbar.NavForm
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class NavFormSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val navf = NavForm()
            root.add(navf)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<form class=\"navbar-form navbar-left\"></form>",
                element?.innerHTML,
                "Should render correct nav form"
            )
            navf.rightAlign = true
            assertEqualsHtml(
                "<form class=\"navbar-form navbar-right\"></form>",
                element?.innerHTML,
                "Should render correct right aligned nav form"
            )

        }
    }

}
