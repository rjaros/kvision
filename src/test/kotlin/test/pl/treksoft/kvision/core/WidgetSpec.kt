package test.pl.treksoft.kvision.core

import test.pl.treksoft.kvision.WSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class WidgetSpec : WSpec {

    @Test
    fun render() {
        runW { widget, element ->
            widget.addCssClass("testClass")
            widget.title = "test_title"
            val vnode = widget.render()
            assertTrue("VNode has correct class attribute") { vnode.data.`class`.testClass == true }
            assertTrue("VNode has correct id attribute") { vnode.data.attrs.id == "test_id" }
            assertTrue("VNode has correct title attribute") { vnode.data.attrs.title == "test_title" }
        }
    }

    @Test
    fun render_ToDom() {
        runW { widget, element ->
            widget.addCssClass("testClass")
            widget.title = "test_title"
            assertTrue("New element should be created") { element != null }
            assertTrue("New element should have correct css class") { element?.className == "testClass" }
            assertTrue("New element should have correct id attribute") { element?.id == "test_id" }
            assertTrue("New element should have correct title attribute") { element?.getAttribute("title") == "test_title" }
        }
    }

    @Test
    fun hide() {
        runW { widget, element ->
            widget.hide()
            val element = document.getElementById("test_id")
            assertTrue("Hidden element should not be accessible") { element == null }
        }
    }

    @Test
    fun show() {
        runW { widget, element ->
            widget.hide()
            widget.show()
            val element = document.getElementById("test_id")
            assertTrue("Visible element should be accessible") { element != null }
        }
    }

    @Test
    fun addCssClass() {
        runW { widget, element ->
            widget.addCssClass("test_class1")
            widget.addCssClass("test_class2")
            assertTrue("Element should have correct css classes") { element?.className == "test_class1 test_class2" }
        }
    }

    @Test
    fun removeCssClass() {
        runW { widget, element ->
            widget.addCssClass("test_class1")
            widget.addCssClass("test_class2")
            widget.removeCssClass("test_class1")
            assertTrue("Element should have correct css class") { element?.className == "test_class2" }
        }
    }

    @Test
    fun setEventListener() {
        runW { widget, element ->
            widget.setEventListener { click = { _ -> } }
            assertTrue("Element should have an event listener") { widget.listeners.size == 1 }
        }
    }

    @Test
    fun removeEventListener() {
        runW { widget, element ->
            widget.setEventListener { click = { _ -> } }
            widget.removeEventListeners()
            assertTrue("Element should not have any event listener") { widget.listeners.size == 0 }
        }
    }

}