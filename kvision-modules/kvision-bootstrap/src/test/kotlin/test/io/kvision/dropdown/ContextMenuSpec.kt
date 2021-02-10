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
package test.io.kvision.dropdown

import kotlinx.browser.document
import io.kvision.dropdown.ContextMenu
import io.kvision.dropdown.setContextMenu
import io.kvision.html.link
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.utils.obj
import kotlin.test.Test

class ContextMenuSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val m = ContextMenu {
                link("a", "b")
                link("c", "d")
            }
            root.setContextMenu(m)
            m.show()
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"dropdown-menu\" style=\"display: block;\"><a href=\"b\">a</a><a href=\"d\">c</a></div>",
                element?.innerHTML,
                "Should render correct context menu"
            )
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    @Test
    fun positionMenu() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val m = ContextMenu {
                link("a", "b")
                link("c", "d")
            }
            root.setContextMenu(m)
            m.positionMenu(obj {
                pageX = 40
                pageY = 50
            })
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"dropdown-menu\" style=\"display: block; top: 50px; left: 40px;\"><a href=\"b\">a</a><a href=\"d\">c</a></div>",
                element?.innerHTML,
                "Should place context menu in the correct position"
            )
        }
    }
}