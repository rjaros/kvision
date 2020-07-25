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
import pl.treksoft.kvision.panel.Direction
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.panel.SplitPanel
import test.pl.treksoft.kvision.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class SplitPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val splitPanel = SplitPanel(Direction.VERTICAL)
            root.add(splitPanel)
            val label1 = Span("abc")
            val label2 = Span("def")
            splitPanel.add(label1)
            splitPanel.add(label2)
            val element = document.getElementById("test")
            val id = splitPanel.splitter.id
            assertEqualsHtml(
                "<div class=\"splitpanel-vertical\"><span class=\"resizable\">abc</span><div class=\"splitter-vertical\" id=\"$id\" style=\"touch-action: none;\"></div><span>def</span></div>",
                element?.innerHTML,
                "Should render correct split panel"
            )
        }
    }
}