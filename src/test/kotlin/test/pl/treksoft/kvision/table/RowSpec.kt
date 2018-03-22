/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.table

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.table.Cell.Companion.cell
import pl.treksoft.kvision.table.Row
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class RowSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val row = Row {
                cell("A")
            }
            root.add(row)
            val element = document.getElementById("test")
            assertEqualsHtml("<tr><td>A</td></tr>", element?.innerHTML, "Should render correct table row")
        }
    }

}
