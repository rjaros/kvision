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
package test.pl.treksoft.kvision.onsenui.grid

import pl.treksoft.kvision.onsenui.GridVerticalAlign
import pl.treksoft.kvision.onsenui.grid.col
import pl.treksoft.kvision.onsenui.grid.row
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.utils.perc
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class ColSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            root.row(GridVerticalAlign.CENTER) {
                col("column 1")
                col("column 2", colWidth = 30.perc)
                col("column 3", colVerticalAlign = GridVerticalAlign.TOP)
            }

            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ons-row vertical-align=\"center\"><ons-col>column 1</ons-col><ons-col width=\"30%\">column 2</ons-col><ons-col vertical-align=\"top\">column 3</ons-col></ons-row>",
                element?.innerHTML,
                "Should render Onsen UI grid column component"
            )
        }
    }
}
