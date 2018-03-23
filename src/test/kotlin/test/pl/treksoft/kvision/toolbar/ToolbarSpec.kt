/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.toolbar

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.toolbar.Toolbar
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class ToolbarSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val toolbar = Toolbar()
            root.add(toolbar)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"btn-toolbar\" role=\"toolbar\"></div>",
                element?.innerHTML,
                "Should render correct toolbar"
            )
        }
    }

}
