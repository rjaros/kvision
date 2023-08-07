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
package test.io.kvision.panel

import kotlinx.browser.document
import io.kvision.html.Span
import io.kvision.html.span
import io.kvision.panel.GridPanel
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlin.test.Test

class GridPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val gridPanel = GridPanel()
            root.add(gridPanel)
            gridPanel.add(Span("abc"), 1, 1)
            gridPanel.add(Span("def"), 2, 2)
            gridPanel.add(Span("ghi"), 3, 3)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div style=\"display: grid;\"><span style=\"grid-column-start: 1; grid-row-start: 1;\">abc</span><span style=\"grid-column-start: 2; grid-row-start: 2;\">def</span><span style=\"grid-column-start: 3; grid-row-start: 3;\">ghi</span></div>",
                element?.innerHTML?.replace("  ", " "),
                "Should render correct grid panel"
            )
        }
    }

    @Test
    fun renderWithDSL() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val gridPanel = GridPanel {
                options(1, 1) {
                    span("abc")
                }
                options(2, 2) {
                    span("def")
                }
                options(3, 3) {
                    span("ghi")
                }
            }
            root.add(gridPanel)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div style=\"display: grid;\"><span style=\"grid-column-start: 1; grid-row-start: 1;\">abc</span><span style=\"grid-column-start: 2; grid-row-start: 2;\">def</span><span style=\"grid-column-start: 3; grid-row-start: 3;\">ghi</span></div>",
                element?.innerHTML?.replace("  ", " "),
                "Should render correct grid panel with DSL"
            )
        }
    }
}