package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Root
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