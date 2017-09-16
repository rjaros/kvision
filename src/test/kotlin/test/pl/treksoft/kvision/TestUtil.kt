package test.pl.treksoft.kvision

import org.w3c.dom.Element
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.core.Widget
import kotlin.browser.document

interface TestSpec {
    fun beforeTest()

    fun afterTest()

    fun run(code: () -> Unit) {
        beforeTest()
        code()
        afterTest()
    }
}

interface DomSpec : TestSpec {

    override fun beforeTest() {
        val fixture = "<div style=\"display: none\"><div id=\"test\"></div></div>"
        document.body?.insertAdjacentHTML("afterbegin", fixture)
    }

    override fun afterTest() {
        val div = document.getElementById("test")
        div?.remove()
    }

}

interface WSpec : DomSpec {

    fun runW(code: (widget: Widget, element: Element?) -> Unit) {
        run {
            val root = Root("test")
            val widget = Widget()
            widget.id = "test_id"
            root.add(widget)
            val element = document.getElementById("test_id")
            code(widget, element)
        }
    }

}