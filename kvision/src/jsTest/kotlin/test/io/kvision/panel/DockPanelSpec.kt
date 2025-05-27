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

import io.kvision.html.Span
import io.kvision.panel.DockPanel
import io.kvision.panel.Root
import io.kvision.panel.Side
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class DockPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val dockPanel = DockPanel()
            root.add(dockPanel)
            dockPanel.add(Span("abc"), Side.UP)
            dockPanel.add(Span("def"), Side.RIGHT)
            dockPanel.add(Span("ghi"), Side.DOWN)
            dockPanel.add(Span("jkl"), Side.LEFT)
            dockPanel.add(Span("mno"), Side.CENTER)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><div style=\"display: flex; flex-direction: column; justify-content: space-between; align-items: stretch; width: 100%; height: 100%;\"><div style=\"order: 2; flex-grow: 1; flex-basis: 0%;\"><div style=\"display: flex; justify-content: space-between; align-items: stretch; width: 100%; height: 100%;\"><div style=\"order: 3; flex-basis: 0%;\"><span>def</span></div><div style=\"order: 1; flex-basis: 0%;\"><span>jkl</span></div><div style=\"order: 2; flex-grow: 1; flex-basis: 0%;\"><span>mno</span></div></div></div><div style=\"order: 1; flex-basis: 0%;\"><span>abc</span></div><div style=\"order: 3; flex-basis: 0%;\"><span>ghi</span></div></div></div>",
                element?.innerHTML,
                "Should render correct dock panel"
            )
        }
    }
}