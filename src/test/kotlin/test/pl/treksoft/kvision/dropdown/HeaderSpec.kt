/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.dropdown.Header
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class HeaderSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val h = Header("Test")
            root.add(h)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<li class=\"dropdown-header\">Test</li>",
                element?.innerHTML,
                "Should render correct drop down header"
            )
        }
    }
}