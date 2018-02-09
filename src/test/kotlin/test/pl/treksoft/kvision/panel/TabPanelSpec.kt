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
import pl.treksoft.kvision.panel.TabPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class TabPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val tabs = TabPanel()
            root.add(tabs)
            val label1 = Label("abc")
            val label2 = Label("def")
            tabs.addTab("ABC", label1)
            tabs.addTab("DEF", label2)
            val element = document.getElementById("test")
            assertEquals(
                "<div><ul class=\"nav nav-tabs\"><li role=\"presentation\" class=\"active\"><a href=\"#\">ABC</a></li><li role=\"presentation\"><a href=\"#\">DEF</a></li></ul><div><span>abc</span></div></div>",
                element?.innerHTML,
                "Should render correct tabs"
            )
        }
    }

    @Test
    fun setActiveIndex() {
        run {
            val root = Root("test")
            val tabs = TabPanel()
            root.add(tabs)
            val label1 = Label("abc")
            val label2 = Label("def")
            tabs.addTab("ABC", label1)
            tabs.addTab("DEF", label2)
            tabs.activeIndex = 1
            val element = document.getElementById("test")
            assertEquals(
                "<div><ul class=\"nav nav-tabs\"><li role=\"presentation\" class=\"\"><a href=\"#\">ABC</a></li><li role=\"presentation\" class=\"active\"><a href=\"#\">DEF</a></li></ul><div><span>def</span></div></div>",
                element?.innerHTML,
                "Should change selected tab"
            )
        }
    }

    @Test
    fun removeTab() {
        run {
            val root = Root("test")
            val tabs = TabPanel()
            root.add(tabs)
            val label1 = Label("abc")
            val label2 = Label("def")
            tabs.addTab("ABC", label1)
            tabs.addTab("DEF", label2)
            tabs.activeIndex = 1
            tabs.removeTab(1)
            val element = document.getElementById("test")
            assertEquals(
                "<div><ul class=\"nav nav-tabs\"><li role=\"presentation\" class=\"\"><a href=\"#\">ABC</a></li></ul><div><span>abc</span></div></div>",
                element?.innerHTML,
                "Should remove tab"
            )
        }
    }
}