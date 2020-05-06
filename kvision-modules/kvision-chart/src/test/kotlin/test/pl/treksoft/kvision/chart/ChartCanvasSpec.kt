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
package test.pl.treksoft.kvision.chart

import pl.treksoft.kvision.chart.ChartCanvas
import pl.treksoft.kvision.chart.ChartType
import pl.treksoft.kvision.chart.Configuration
import pl.treksoft.kvision.chart.DataSets
import pl.treksoft.kvision.chart.ChartOptions
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class ChartCanvasSpec : DomSpec {

    @Test
    fun renderResponsive() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val chart = ChartCanvas(
                configuration = Configuration(
                    ChartType.SCATTER,
                    listOf(DataSets(label = "Chart", data = listOf(0, 1)))
                )
            )
            root.add(chart)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<canvas width=\"0\" height=\"0\" class=\"chartjs-render-monitor\" style=\"display: block; width: 0px; height: 0px;\"></canvas>",
                element?.innerHTML,
                "Should render correct responsive chart canvas"
            )
        }
    }

    @Test
    fun renderNotResponsive() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val chart = ChartCanvas(
                300, 600,
                configuration = Configuration(
                    ChartType.SCATTER,
                    listOf(DataSets(label = "Chart", data = listOf(0, 1))),
                    options = ChartOptions(responsive = false)
                )
            )
            root.add(chart)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<canvas width=\"300\" height=\"600\" style=\"display: block;\"></canvas>",
                element?.innerHTML,
                "Should render correct not responsive chart canvas"
            )
        }
    }
}