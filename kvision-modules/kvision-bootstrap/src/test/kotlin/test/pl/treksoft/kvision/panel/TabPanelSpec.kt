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

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.html.Span
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.panel.TabPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class TabPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", fixed = true)
            val tabs = TabPanel()
            root.add(tabs)
            val label1 = Span("abc")
            val label2 = Span("def")
            tabs.addTab("ABC", label1)
            tabs.addTab("DEF", label2)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\">ABC</a></li><li class=\"nav-item\"><a class=\"nav-link\" href=\"#\">DEF</a></li></ul><div><span>abc</span></div></div>",
                element?.innerHTML,
                "Should render correct tabs"
            )
        }
    }

    @Test
    fun setActiveIndex() {
        run {
            val root = Root("test", fixed = true)
            val tabs = TabPanel()
            root.add(tabs)
            val label1 = Span("abc")
            val label2 = Span("def")
            tabs.addTab("ABC", label1)
            tabs.addTab("DEF", label2)
            tabs.activeIndex = 1
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link\" href=\"#\">ABC</a></li><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\">DEF</a></li></ul><div><span>def</span></div></div>",
                element?.innerHTML,
                "Should change selected tab"
            )
        }
    }

    @Test
    fun removeTab() {
        run {
            val root = Root("test", fixed = true)
            val tabs = TabPanel()
            root.add(tabs)
            val label1 = Span("abc")
            val label2 = Span("def")
            tabs.addTab("ABC", label1)
            tabs.addTab("DEF", label2)
            tabs.activeIndex = 1
            tabs.removeTab(1)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\">ABC</a></li></ul><div><span>abc</span></div></div>",
                element?.innerHTML,
                "Should remove tab"
            )
        }
    }


    @Test
    fun tabClick() {
        run {
            val root = Root("test", fixed = true)
            val tabs = TabPanel()
            root.add(tabs)
            val label1 = Span("abc")
            val label2 = Span("def")
            tabs.addTab("ABC", label1)
            tabs.addTab("DEF", label2)
            tabs.removeTab(0)
            val label3 = Span("ghi")
            tabs.addTab("GHI", label3)
            jQuery("#test a")[1]?.click()
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link\" href=\"#\">DEF</a></li><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\">GHI</a></li></ul><div><span>ghi</span></div></div>",
                element?.innerHTML,
                "Should select correct tab by clicking"
            )
        }
    }
}