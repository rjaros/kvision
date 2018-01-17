package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.panel.FLEXJUSTIFY
import pl.treksoft.kvision.panel.VPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class VPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val vPanel = VPanel(justify = FLEXJUSTIFY.SPACEBETWEEN)
            root.add(vPanel)
            vPanel.add(Label("abc"), 1)
            vPanel.add(Label("def"), 2)
            vPanel.add(Label("ghi"), 3)
            val element = document.getElementById("test")
            assertEquals(
                "<div style=\"display: flex; flex-direction: column; justify-content: space-between;\"><div style=\"order: 1;\"><span>abc</span></div><div style=\"order: 2;\"><span>def</span></div><div style=\"order: 3;\"><span>ghi</span></div></div>",
                element?.innerHTML,
                "Should render correct vertical panel"
            )
        }
    }
}