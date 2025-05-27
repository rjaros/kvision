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
package test.io.kvision.core

import io.kvision.core.Widget
import io.kvision.panel.Root
import io.kvision.test.WSpec
import kotlinx.browser.document
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
            widget.setEventListener<Widget> { click = { } }
            assertTrue("Element should have one event listener") { widget.listenersMap!!["click"]?.size == 1 }
            widget.setEventListener<Widget> { click = { } }
            assertTrue("Element should have two event listeners") { widget.listenersMap!!["click"]?.size == 2 }
        }
    }

    @Test
    fun removeEventListener() {
        runW { widget, _ ->
            val id = widget.setEventListener<Widget> { click = { } }
            widget.setEventListener<Widget> { click = { } }
            widget.removeEventListener(id)
            assertTrue("One of event listeners is removed") { widget.listenersMap!!["click"]?.size == 1 }
        }
    }

    @Test
    fun removeEventListeners() {
        runW { widget, _ ->
            widget.setEventListener<Widget> { click = { } }
            widget.setEventListener<Widget> { click = { } }
            widget.removeEventListeners()
            assertTrue("Element should not have any event listeners") { widget.listenersMap!!.isEmpty() }
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
    fun getRoot() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val widget = Widget()
            root.add(widget)
            val r = widget.getRoot()
            assertTrue("Should return correct root element") { r == root }
        }
    }
}