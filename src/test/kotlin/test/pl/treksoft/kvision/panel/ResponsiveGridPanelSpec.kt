package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.panel.ResponsiveGridPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class ResponsiveGridPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val rgPanel = ResponsiveGridPanel()
            root.add(rgPanel)
            rgPanel.add(Label("abc"), 1, 1)
            rgPanel.add(Label("def"), 2, 2)
            rgPanel.add(Label("ghi"), 3, 3)
            val element = document.getElementById("test")
            assertEquals("<div><div class=\"row\"></div><div class=\"row\"><div class=\"col-md-3\"></div><div class=\"col-md-3\"><span>abc</span></div><div class=\"col-md-3\"></div><div class=\"col-md-3\"></div></div><div class=\"row\"><div class=\"col-md-3\"></div><div class=\"col-md-3\"></div><div class=\"col-md-3\"><span>def</span></div><div class=\"col-md-3\"></div></div><div class=\"row\"><div class=\"col-md-3\"></div><div class=\"col-md-3\"></div><div class=\"col-md-3\"></div><div class=\"col-md-3\"><span>ghi</span></div></div></div>", element?.innerHTML, "Should render correct responsive grid panel")
        }
    }
}