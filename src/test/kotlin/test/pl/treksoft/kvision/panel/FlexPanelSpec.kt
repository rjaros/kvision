package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.html.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.panel.FLEXDIR
import pl.treksoft.kvision.panel.FLEXJUSTIFY
import pl.treksoft.kvision.panel.FlexPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class FlexPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val flexPanel = FlexPanel(FLEXDIR.ROWREV, justify = FLEXJUSTIFY.SPACEEVENLY)
            root.add(flexPanel)
            flexPanel.add(Label("abc"), 1)
            flexPanel.add(Label("def"), 2)
            flexPanel.add(Label("ghi"), 3)
            val element = document.getElementById("test")
            assertEquals(
                "<div style=\"display: flex; flex-direction: row-reverse; justify-content: space-evenly;\"><div style=\"order: 1;\"><span>abc</span></div><div style=\"order: 2;\"><span>def</span></div><div style=\"order: 3;\"><span>ghi</span></div></div>",
                element?.innerHTML,
                "Should render correct flex panel"
            )
        }
    }
}