/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.table

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.table.Cell.Companion.cell
import pl.treksoft.kvision.table.Row.Companion.row
import pl.treksoft.kvision.table.Table
import pl.treksoft.kvision.table.TableType
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class TableSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val table = Table(listOf("a", "b")) {
                row {
                    cell("A")
                    cell("B")
                }
            }
            root.add(table)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<table class=\"table\"><thead><tr><th>a</th><th>b</th></tr></thead><tbody><tr><td>A</td><td>B</td></tr></tbody></table>",
                element?.innerHTML,
                "Should render correct table"
            )
            table.caption = "Caption"
            table.responsive = true
            table.types = setOf(TableType.BORDERED)
            val element2 = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"table-responsive\"><table class=\"table table-bordered\"><caption>Caption</caption><thead><tr><th>a</th><th>b</th></tr></thead><tbody><tr><td>A</td><td>B</td></tr></tbody></table></div>",
                element2?.innerHTML,
                "Should render correct responsive table"
            )
        }
    }

}
