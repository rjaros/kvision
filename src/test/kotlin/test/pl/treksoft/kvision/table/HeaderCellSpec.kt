/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.table

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.table.HeaderCell
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class HeaderCellSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val cell = HeaderCell("This is a header cell")
            root.add(cell)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<th>This is a header cell</th>",
                element?.innerHTML,
                "Should render correct table header cell"
            )
        }
    }

}
