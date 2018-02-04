package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.core.UNIT
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.core.WidgetWrapper
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class WidgetWrapperSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val widget = Widget()
            val wrapper = WidgetWrapper(widget)
            wrapper.width = 100 to UNIT.em
            root.add(wrapper)
            val element = document.getElementById("test")
            assertEquals(
                "<div style=\"width: 100em;\"><div></div></div>",
                element?.innerHTML,
                "Should render widget inside custom wrapper"
            )
            widget.hide()
            assertEquals("", element?.innerHTML, "Should not render wrapper when widget is hidden")
        }
    }
}