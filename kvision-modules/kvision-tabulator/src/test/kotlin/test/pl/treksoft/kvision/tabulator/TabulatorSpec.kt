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
package test.pl.treksoft.kvision.tabulator

import kotlinx.browser.document
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.tabulator.Tabulator
import pl.treksoft.kvision.tabulator.TabulatorOptions
import pl.treksoft.kvision.utils.obj
import test.pl.treksoft.kvision.DomSpec
import kotlin.test.Test

class TabulatorSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val tabulator = Tabulator<Any>(options = TabulatorOptions(data = arrayOf(obj {
                id = 1
                name = "Name"
                age = 40
            }, obj {
                id = 2
                name = "Name2"
                age = 50
            }), virtualDom = false))
            root.add(tabulator)
            tabulator.redraw(true)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"tabulator\" role=\"grid\" tabulator-layout=\"fitData\"><div class=\"tabulator-header\" style=\"padding-right: 0px;\"><div class=\"tabulator-headers\" style=\"margin-left: 0px;\"><div class=\"tabulator-col tabulator-sortable\" role=\"columnheader\" aria-sort=\"none\" tabulator-field=\"id\" title=\"\" style=\"min-width: 40px;\"><div class=\"tabulator-col-content\"><div class=\"tabulator-col-title-holder\"><div class=\"tabulator-col-title\">id</div><div class=\"tabulator-col-sorter\"><div class=\"tabulator-arrow\"></div></div></div></div><div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div><div class=\"tabulator-col tabulator-sortable\" role=\"columnheader\" aria-sort=\"none\" tabulator-field=\"name\" title=\"\" style=\"min-width: 40px;\"><div class=\"tabulator-col-content\"><div class=\"tabulator-col-title-holder\"><div class=\"tabulator-col-title\">name</div><div class=\"tabulator-col-sorter\"><div class=\"tabulator-arrow\"></div></div></div></div><div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div><div class=\"tabulator-col tabulator-sortable\" role=\"columnheader\" aria-sort=\"none\" tabulator-field=\"age\" title=\"\" style=\"min-width: 40px;\"><div class=\"tabulator-col-content\"><div class=\"tabulator-col-title-holder\"><div class=\"tabulator-col-title\">age</div><div class=\"tabulator-col-sorter\"><div class=\"tabulator-arrow\"></div></div></div></div><div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div></div><div class=\"tabulator-frozen-rows-holder\"></div></div><div class=\"tabulator-tableHolder\" tabindex=\"0\"><div class=\"tabulator-table\"><div class=\"tabulator-row tabulator-selectable tabulator-row-odd\" role=\"row\" style=\"padding-left: 0px;\"><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"id\" title=\"\">1<div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"name\" title=\"\">Name<div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"age\" title=\"\">40<div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div></div><div class=\"tabulator-row tabulator-selectable tabulator-row-even\" role=\"row\" style=\"padding-left: 0px;\"><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"id\" title=\"\">2<div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"name\" title=\"\">Name2<div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div><div class=\"tabulator-cell\" role=\"gridcell\" tabulator-field=\"age\" title=\"\">50<div class=\"tabulator-col-resize-handle\"></div><div class=\"tabulator-col-resize-handle prev\"></div></div></div></div></div></div>",
                element?.innerHTML,
                "Should render correct tabulator table"
            )
        }
    }
}
