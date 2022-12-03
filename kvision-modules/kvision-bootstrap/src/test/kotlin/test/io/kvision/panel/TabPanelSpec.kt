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
import io.kvision.jquery.get
import io.kvision.jquery.invoke
import io.kvision.jquery.jQuery
import io.kvision.html.Span
import io.kvision.html.span
import io.kvision.panel.Root
import io.kvision.panel.Tab
import io.kvision.panel.TabPanel
import io.kvision.panel.tab
import io.kvision.test.DomSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class TabPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tabs = TabPanel()
            root.add(tabs)
            val label1 = Span("abc")
            val label2 = Span("def")
            tabs.addTab("ABC", label1)
            tabs.add(Tab("DEF", label2))
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"kv-tab-panel\"><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\" data-navigo=\"false\">ABC</a></li><li class=\"nav-item\"><a class=\"nav-link\" href=\"#\" data-navigo=\"false\">DEF</a></li></ul><div><span>abc</span></div></div>",
                element?.innerHTML,
                "Should render correct tabs"
            )
        }
    }

    @Test
    fun setActiveIndex() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab("DEF") {
                    span("def")
                }
            }
            root.add(tabs)
            tabs.activeIndex = 1
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"kv-tab-panel\"><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link\" href=\"#\" data-navigo=\"false\">ABC</a></li><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\" data-navigo=\"false\">DEF</a></li></ul><div><span>def</span></div></div>",
                element?.innerHTML,
                "Should change selected tab by index"
            )
        }
    }

    @Test
    fun setActiveTab() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            lateinit var tab: Tab
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab = tab("DEF") {
                    span("def")
                }
            }
            root.add(tabs)
            tabs.activeTab = tab
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"kv-tab-panel\"><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link\" href=\"#\" data-navigo=\"false\">ABC</a></li><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\" data-navigo=\"false\">DEF</a></li></ul><div><span>def</span></div></div>",
                element?.innerHTML,
                "Should change selected tab by Tab component"
            )
        }
    }

    @Test
    fun getSize() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab("DEF") {
                    span("def")
                }
            }
            root.add(tabs)
            assertEquals(2, tabs.getSize(), "Should return number of tabs")
            assertEquals(2, tabs.getTabs().size, "Should return a list of tabs")
        }
    }

    @Test
    fun getTab() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            lateinit var tab: Tab
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab = tab("DEF") {
                    span("def")
                }
            }
            root.add(tabs)
            assertEquals(tab, tabs.getTab(1), "Should get correct tab by index")
        }
    }

    @Test
    fun getTabIndex() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            lateinit var tab: Tab
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab = tab("DEF") {
                    span("def")
                }
            }
            root.add(tabs)
            assertEquals(1, tabs.getTabIndex(tab), "Should return correct index of the given tab")
        }
    }

    @Test
    fun removeTab() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab("DEF") {
                    span("def")
                }
            }
            root.add(tabs)
            tabs.activeIndex = 1
            tabs.removeTab(1)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"kv-tab-panel\"><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\" data-navigo=\"false\">ABC</a></li></ul><div><span>abc</span></div></div>",
                element?.innerHTML,
                "Should remove tab"
            )
        }
    }

    @Test
    fun findTabWithComponent() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            lateinit var span: Span
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab("DEF") {
                    span = span("def")
                }
            }
            root.add(tabs)
            val tab = tabs.findTabWithComponent(span)
            assertEquals(1, tabs.getTabIndex(tab!!), "Should find correct tab with the given child component")
        }
    }

    @Test
    fun moveTab() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab("DEF") {
                    span("def")
                }
            }
            root.add(tabs)
            tabs.activeIndex = 1
            tabs.moveTab(1, 0)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"kv-tab-panel\"><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\" data-navigo=\"false\">DEF</a></li><li class=\"nav-item\"><a class=\"nav-link\" href=\"#\" data-navigo=\"false\">ABC</a></li></ul><div><span>def</span></div></div>",
                element?.innerHTML,
                "Should move the tab to different index"
            )
        }
    }

    @Test
    fun tabClick() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tabs = TabPanel {
                tab("ABC") {
                    span("abc")
                }
                tab("DEF") {
                    span("def")
                }
            }
            root.add(tabs)
            tabs.removeTab(0)
            val label3 = Span("ghi")
            tabs.add(Tab("GHI") { add(label3) })
            jQuery("#test a")[1]?.click()
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"kv-tab-panel\"><ul class=\"nav nav-tabs\"><li class=\"nav-item\"><a class=\"nav-link\" href=\"#\" data-navigo=\"false\">DEF</a></li><li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\" data-navigo=\"false\">GHI</a></li></ul><div><span>ghi</span></div></div>",
                element?.innerHTML,
                "Should select correct tab by clicking"
            )
        }
    }
}
