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
package test.pl.treksoft.kvision.window

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.window.Window
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class WindowSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val window = Window("Window title", isResizable = false)
            root.add(window)
            val id = window.id
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"modal-content kv-window\" id=\"$id\" style=\"width: auto; position: absolute; z-index: 901; overflow: hidden;\"><div class=\"modal-header\"><h4 class=\"modal-title\">Window title</h4></div><div style=\"height: auto; overflow: auto;\"></div></div>",
                element?.innerHTML,
                "Should render floating window without resizable handler"
            )
            window.isResizable = true
            assertEqualsHtml(
                "<div class=\"modal-content kv-window\" id=\"$id\" style=\"width: auto; position: absolute; z-index: 901; overflow: hidden; resize: both; height: 12px;\"><div class=\"modal-header\"><h4 class=\"modal-title\">Window title</h4></div><div style=\"height: auto; overflow: auto; margin-bottom: 11px;\"></div><object style=\"display: block; position: absolute; top: 0; left: 0; height: 100%; width: 100%; overflow: hidden; pointer-events: none; z-index: -1; opacity: 0;\" class=\"resize-sensor\" type=\"text/html\" data=\"about:blank\"></object></div>",
                element?.innerHTML,
                "Should render floating window with resizable handler"
            )
        }
    }

}