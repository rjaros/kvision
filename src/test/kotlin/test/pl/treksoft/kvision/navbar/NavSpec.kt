/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.navbar

import pl.treksoft.kvision.navbar.Nav
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class NavSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val nav = Nav()
            root.add(nav)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ul class=\"nav navbar-nav\"></ul>",
                element?.innerHTML,
                "Should render correct nav"
            )
            nav.rightAlign = true
            assertEqualsHtml(
                "<ul class=\"nav navbar-nav navbar-right\"></ul>",
                element?.innerHTML,
                "Should render correct right aligned nav"
            )

        }
    }

}
