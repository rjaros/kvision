package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.panel.FLEXJUSTIFY
import pl.treksoft.kvision.panel.HPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class HPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val hPanel = HPanel(justify = FLEXJUSTIFY.SPACEBETWEEN)
            root.add(hPanel)
            hPanel.add(Label("abc"), 1)
            hPanel.add(Label("def"), 2)
            hPanel.add(Label("ghi"), 3)
            val element = document.getElementById("test")
            assertEquals("<div style=\"display: flex; justify-content: space-between;\"><div style=\"order: 1;\"><span>abc</span></div><div style=\"order: 2;\"><span>def</span></div><div style=\"order: 3;\"><span>ghi</span></div></div>", element?.innerHTML, "Should render correct horizontal panel")
        }
    }
}