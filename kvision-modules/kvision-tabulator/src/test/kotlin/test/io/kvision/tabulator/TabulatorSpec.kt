/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package test.io.kvision.tabulator

import io.kvision.panel.Root
import io.kvision.tabulator.RenderType
import io.kvision.tabulator.Tabulator
import io.kvision.tabulator.TabulatorOptions
import io.kvision.test.DomSpec
import io.kvision.utils.obj
import kotlinx.browser.document
import kotlin.test.Test

class TabulatorSpec : DomSpec {

    @Test
    fun render() = runAsync { resolve, _ ->
        val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
        val element = document.getElementById("test")

        @Suppress("UnsafeCastFromDynamic")
        val tabulator = Tabulator<Any>(
            options = TabulatorOptions(
                data = arrayOf(obj {
                    id = 1
                    name = "Name"
                    age = 40
                }, obj {
                    id = 2
                    name = "Name2"
                    age = 50
                }),
                renderVertical = RenderType.BASIC
            )
        )
        root.add(tabulator)
        tabulator.jsTabulator?.on("tableBuilt") {
            tabulator.redraw(true)
            assertEqualsHtml(
                "<div class=\"tabulator\" role=\"grid\" tabulator-layout=\"fitData\"><div class=\"tabulator-header\" role=\"rowgroup\" style=\"padding-right: 0px;\"><div class=\"tabulator-headers\" role=\"row\" style=\"margin-left: 0px;\"><div class=\"tabulator-col tabulator-sortable\" role=\"columnheader\" aria-sort=\"none\" tabulator-field=\"id\" style=\"min-width: 40px;\"><div class=\"tabulator-col-content\"><div class=\"tabulator-col-title-holder\"><div class=\"tabulator-col-title\">id</div><div class=\"tabulator-col-sorter\"><div class=\"tabulator-arrow\"></div></div></div></div></div><span class=\"tabulator-col-resize-handle\"></span><div class=\"tabulator-col tabulator-sortable\" role=\"columnheader\" aria-sort=\"none\" tabulator-field=\"name\" style=\"min-width: 40px;\"><div class=\"tabulator-col-content\"><div class=\"tabulator-col-title-holder\"><div class=\"tabulator-col-title\">name</div><div class=\"tabulator-col-sorter\"><div class=\"tabulator-arrow\"></div></div></div></div></div><span class=\"tabulator-col-resize-handle\"></span><div class=\"tabulator-col tabulator-sortable\" role=\"columnheader\" aria-sort=\"none\" tabulator-field=\"age\" style=\"min-width: 40px;\"><div class=\"tabulator-col-content\"><div class=\"tabulator-col-title-holder\"><div class=\"tabulator-col-title\">age</div><div class=\"tabulator-col-sorter\"><div class=\"tabulator-arrow\"></div></div></div></div></div><span class=\"tabulator-col-resize-handle\"></span></div><div class=\"tabulator-frozen-rows-holder\"></div></div><div class=\"tabulator-tableholder\" tabindex=\"0\" role=\"rowgroup\" style=\"height: 0px;\"><div class=\"tabulator-table\" role=\"rowgroup\"><div class=\"tabulator-row tabulator-selectable tabulator-row-odd\" role=\"row\" style=\"padding-left: 0px;\"><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"id\">1</div><span class=\"tabulator-col-resize-handle\"></span><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"name\">Name</div><span class=\"tabulator-col-resize-handle\"></span><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"age\">40</div><span class=\"tabulator-col-resize-handle\"></span></div><div class=\"tabulator-row tabulator-selectable tabulator-row-even\" role=\"row\" style=\"padding-left: 0px;\"><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"id\">2</div><span class=\"tabulator-col-resize-handle\"></span><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"name\">Name2</div><span class=\"tabulator-col-resize-handle\"></span><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"age\">50</div><span class=\"tabulator-col-resize-handle\"></span></div></div></div></div>",
                element?.innerHTML,
                "Should render correct tabulator table"
            )
            resolve()
        }
    }
}
