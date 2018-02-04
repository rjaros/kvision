package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.html.Label
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class LabelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val label = Label("This is a label")
            root.add(label)
            val element = document.getElementById("test")
            assertEquals("<span>This is a label</span>", element?.innerHTML, "Should render correct label")
        }
    }

}
