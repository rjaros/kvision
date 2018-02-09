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

import pl.treksoft.kvision.html.Label
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.panel.DockPanel
import pl.treksoft.kvision.panel.SIDE
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class DockPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val dockPanel = DockPanel()
            root.add(dockPanel)
            dockPanel.add(Label("abc"), SIDE.UP)
            dockPanel.add(Label("def"), SIDE.RIGHT)
            dockPanel.add(Label("ghi"), SIDE.DOWN)
            dockPanel.add(Label("jkl"), SIDE.LEFT)
            dockPanel.add(Label("mno"), SIDE.CENTER)
            val element = document.getElementById("test")
            assertEquals(
                "<div><div style=\"display: flex; flex-direction: column; justify-content: space-between; align-items: stretch;\"><div style=\"order: 2;\"><div style=\"display: flex; justify-content: space-between; align-items: center;\"><div style=\"order: 3;\"><span>def</span></div><div style=\"order: 1;\"><span>jkl</span></div><div style=\"order: 2;\"><span>mno</span></div></div></div><div style=\"order: 1; align-self: center;\"><span>abc</span></div><div style=\"order: 3; align-self: center;\"><span>ghi</span></div></div></div>",
                element?.innerHTML,
                "Should render correct dock panel"
            )
        }
    }
}