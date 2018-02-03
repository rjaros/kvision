package test.pl.treksoft.kvision.panel

import pl.treksoft.kvision.html.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.panel.DockPanel
import pl.treksoft.kvision.panel.SIDE
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class DockPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val dockPanel = DockPanel()
            root.add(dockPanel)
            dockPanel.add(Label("abc"), SIDE.UP)
            dockPanel.add(Label("def"), SIDE.RIGHT)
            dockPanel.add(Label("ghi"), SIDE.DOWN)
            dockPanel.add(Label("jkl"), SIDE.LEFT)
            dockPanel.add(Label("mno"), SIDE.CENTER)
            val element = document.getElementById("test")
            assertEquals(
                "<div><div style=\"display: flex; flex-direction: column; justify-content: space-between; align-items: stretch;\"><div style=\"order: 2;\"><div style=\"display: flex; justify-content: space-between; align-items: center;\"><div style=\"order: 3;\"><span>def</span></div><div style=\"order: 1;\"><span>jkl</span></div><div style=\"order: 2;\"><span>mno</span></div></div></div><div style=\"order: 1; align-self: center;\"><span>abc</span></div><div style=\"order: 3; align-self: center;\"><span>ghi</span></div></div></div>",
                element?.innerHTML,
                "Should render correct dock panel"
            )
        }
    }
}