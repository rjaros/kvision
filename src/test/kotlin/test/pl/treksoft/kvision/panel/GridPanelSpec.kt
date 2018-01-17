package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.panel.GridPanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class GridPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val gridPanel = GridPanel()
            root.add(gridPanel)
            gridPanel.add(Label("abc"), 1, 1)
            gridPanel.add(Label("def"), 2, 2)
            gridPanel.add(Label("ghi"), 3, 3)
            val element = document.getElementById("test")
            assertEquals(
                "<div style=\"display: grid;\"><div style=\"grid-column-start: 1; grid-row-start: 1;\"><span>abc</span></div><div style=\"grid-column-start: 2; grid-row-start: 2;\"><span>def</span></div><div style=\"grid-column-start: 3; grid-row-start: 3;\"><span>ghi</span></div></div>",
                element?.innerHTML,
                "Should render correct grid panel"
            )
        }
    }
}