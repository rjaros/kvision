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
package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.html.Span
import pl.treksoft.kvision.panel.FlexJustify
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.panel.VPanel
import test.pl.treksoft.kvision.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class VPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val vPanel = VPanel(justify = FlexJustify.SPACEBETWEEN)
            root.add(vPanel)
            vPanel.add(Span("abc"), 1)
            vPanel.add(Span("def"), 2)
            vPanel.add(Span("ghi"), 3)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div style=\"display: flex; flex-direction: column; justify-content: space-between;\"><div style=\"order: 1;\"><span>abc</span></div><div style=\"order: 2;\"><span>def</span></div><div style=\"order: 3;\"><span>ghi</span></div></div>",
                element?.innerHTML,
                "Should render correct vertical panel"
            )
        }
    }
}