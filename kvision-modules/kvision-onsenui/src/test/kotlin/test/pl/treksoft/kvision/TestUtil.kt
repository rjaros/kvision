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
package test.pl.treksoft.kvision

import org.w3c.dom.Element
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.panel.Root
import kotlin.browser.document
import kotlin.test.assertEquals
import kotlin.test.assertTrue

interface TestSpec {
    fun beforeTest()

    fun afterTest()

    fun run(code: () -> Unit) {
        beforeTest()
        code()
        afterTest()
    }
}

interface SimpleSpec : TestSpec {

    override fun beforeTest() {
    }

    override fun afterTest() {
    }

}

interface DomSpec : TestSpec {

    override fun beforeTest() {
        val fixture = "<div style=\"display: none\" id=\"pretest\">" +
                "<div id=\"test\"></div></div>"
        document.body?.insertAdjacentHTML("afterbegin", fixture)
    }

    override fun afterTest() {
        val div = document.getElementById("pretest")
        div?.let { jQuery(it).remove() }
        jQuery(".modal-backdrop").remove()
        Root.disposeAllRoots()
    }

    fun assertEqualsHtml(expected: String?, actual: String?, message: String?) {
        if (expected != null && actual != null) {
            val exp = jQuery(expected.replace("position: ;","position: absolute;"))
            val act = jQuery(actual.replace("position: ;","position: absolute;"))
            val result = exp[0]?.isEqualNode(act[0])
            if (result == true) {
                assertTrue(result == true, message)
            } else {
                assertEquals(expected, actual, message)
            }
        } else {
            assertEquals(expected, actual, message)
        }
    }
}

interface WSpec : DomSpec {

    fun runW(code: (widget: Widget, element: Element?) -> Unit) {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val widget = Widget()
            widget.id = "test_id"
            root.add(widget)
            val element = document.getElementById("test_id")
            code(widget, element)
        }
    }

}

external fun require(name: String): dynamic
