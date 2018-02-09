/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.WSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class WidgetSpec : WSpec {

    @Test
    fun renderVNode() {
        runW { widget, _ ->
            widget.addCssClass("testClass")
            widget.title = "test_title"
            val vnode = widget.renderVNode()
            assertTrue("VNode has correct class attribute") { vnode.data.`class`.testClass == true }
            assertTrue("VNode has correct id attribute") { vnode.data.attrs.id == "test_id" }
            assertTrue("VNode has correct title attribute") { vnode.data.attrs.title == "test_title" }
        }
    }

    @Test
    fun renderToDom() {
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
        runW { widget, _ ->
            widget.hide()
            val element = document.getElementById("test_id")
            assertTrue("Hidden element should not be accessible") { element == null }
        }
    }

    @Test
    fun show() {
        runW { widget, _ ->
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
        runW { widget, _ ->
            widget.setEventListener { click = { _ -> } }
            assertTrue("Element should have an event listener") { widget.listeners.size == 1 }
        }
    }

    @Test
    fun removeEventListener() {
        runW { widget, _ ->
            widget.setEventListener { click = { _ -> } }
            widget.removeEventListeners()
            assertTrue("Element should not have any event listener") { widget.listeners.size == 0 }
        }
    }

    @Test
    fun getElement() {
        runW { widget, element ->
            val e = widget.getElement()
            assertTrue("Should return correct dom element") { e == element }
        }
    }

    @Test
    fun getElementJQuery() {
        runW { widget, element ->
            val j = widget.getElementJQuery()
            assertTrue("Should return correct jQuery object") { j != null }
            val e = j?.get()?.get(0)
            assertTrue("Should return correct dom element from jQuery object") { e == element }
        }
    }

    @Test
    fun getRoot() {
        run {
            val root = Root("test")
            val widget = Widget()
            root.add(widget)
            val r = widget.getRoot()
            assertTrue("Should return correct root element") { r == root }
        }
    }
}