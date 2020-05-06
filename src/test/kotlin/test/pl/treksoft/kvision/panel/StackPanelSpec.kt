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
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.panel.StackPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class StackPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val stackPanel = StackPanel()
            root.add(stackPanel)
            val label1 = Span("abc")
            val label2 = Span("def")
            stackPanel.add(label1)
            stackPanel.add(label2)
            val element = document.getElementById("test")
            assertEqualsHtml("<div><span>def</span></div>", element?.innerHTML, "Should render correct stack panel")
        }
    }

    @Test
    fun renderNotActivateLast() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val stackPanel = StackPanel(activateLast = false)
            root.add(stackPanel)
            val label1 = Span("abc")
            val label2 = Span("def")
            stackPanel.add(label1)
            stackPanel.add(label2)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><span>abc</span></div>",
                element?.innerHTML,
                "Should render correct stack panel with activateLast = false"
            )
        }
    }

    @Test
    fun remove() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val stackPanel = StackPanel(activateLast = false)
            root.add(stackPanel)
            val label1 = Span("abc")
            val label2 = Span("def")
            stackPanel.add(label1)
            stackPanel.add(label2)
            stackPanel.remove(label1)
            val element = document.getElementById("test")
            assertEqualsHtml("<div><span>def</span></div>", element?.innerHTML, "Should remove correct child widget")
        }
    }

    @Test
    fun removeAll() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val stackPanel = StackPanel(activateLast = false)
            root.add(stackPanel)
            val label1 = Span("abc")
            val label2 = Span("def")
            stackPanel.add(label1)
            stackPanel.add(label2)
            stackPanel.removeAll()
            val element = document.getElementById("test")
            assertEqualsHtml("<div></div>", element?.innerHTML, "Should remove all child widgets")
        }
    }
}