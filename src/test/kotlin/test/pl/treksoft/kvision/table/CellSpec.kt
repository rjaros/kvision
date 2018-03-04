/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.table

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.table.Cell
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class CellSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val cell = Cell("This is a cell")
            root.add(cell)
            val element = document.getElementById("test")
            assertEqualsHtml("<td>This is a cell</td>", element?.innerHTML, "Should render correct table cell")
        }
    }

}
