package test.pl.treksoft.kvision.panel

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.modal.Alert
import pl.treksoft.kvision.panel.DIRECTION
import pl.treksoft.kvision.panel.SplitPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SplitPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val splitPanel = SplitPanel(DIRECTION.VERTICAL)
            root.add(splitPanel)
            val label1 = Label("abc")
            val label2 = Label("def")
            splitPanel.add(label1)
            splitPanel.add(label2)
            val element = document.getElementById("test")
            val id = splitPanel.splitter.id
            assertEquals("<div class=\"splitpanel-vertical\"><span class=\"resizable\">abc</span><div class=\"splitter-vertical\" id=\"$id\" style=\"touch-action: none;\"></div><span>def</span></div>", element?.innerHTML, "Should render correct split panel")
        }
    }
}