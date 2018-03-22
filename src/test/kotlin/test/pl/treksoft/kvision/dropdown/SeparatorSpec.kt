/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.dropdown.Separator
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class SeparatorSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val s = Separator()
            root.add(s)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<li class=\"divider\" role=\"separator\"></li>",
                element?.innerHTML,
                "Should render correct drop down separator"
            )
        }
    }
}