package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.panel.StackPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class StackPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val stackPanel = StackPanel()
            root.add(stackPanel)
            val label1 = Label("abc")
            val label2 = Label("def")
            stackPanel.add(label1)
            stackPanel.add(label2)
            val element = document.getElementById("test")
            assertEquals("<div><span>def</span></div>", element?.innerHTML, "Should render correct stack panel")
        }
    }

    @Test
    fun renderNotActivateLast() {
        run {
            val root = Root("test")
            val stackPanel = StackPanel(activateLast = false)
            root.add(stackPanel)
            val label1 = Label("abc")
            val label2 = Label("def")
            stackPanel.add(label1)
            stackPanel.add(label2)
            val element = document.getElementById("test")
            assertEquals(
                "<div><span>abc</span></div>",
                element?.innerHTML,
                "Should render correct stack panel with activateLast = false"
            )
        }
    }

    @Test
    fun remove() {
        run {
            val root = Root("test")
            val stackPanel = StackPanel(activateLast = false)
            root.add(stackPanel)
            val label1 = Label("abc")
            val label2 = Label("def")
            stackPanel.add(label1)
            stackPanel.add(label2)
            stackPanel.remove(label1)
            val element = document.getElementById("test")
            assertEquals("<div><span>def</span></div>", element?.innerHTML, "Should remove correct child widget")
        }
    }

    @Test
    fun removeAll() {
        run {
            val root = Root("test")
            val stackPanel = StackPanel(activateLast = false)
            root.add(stackPanel)
            val label1 = Label("abc")
            val label2 = Label("def")
            stackPanel.add(label1)
            stackPanel.add(label2)
            stackPanel.removeAll()
            val element = document.getElementById("test")
            assertEquals("<div></div>", element?.innerHTML, "Should remove all child widgets")
        }
    }
}