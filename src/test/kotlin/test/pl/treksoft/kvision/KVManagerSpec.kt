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

import com.github.snabbdom.h
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.utils.snAttrs
import pl.treksoft.kvision.utils.snOpt
import pl.treksoft.kvision.utils.snStyle
import pl.treksoft.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class KVManagerSpec : DomSpec {

    @Test
    fun patchById() {
        run {
            val vnode = h("span", snOpt {
                attrs = snAttrs(listOf("id" to "test_new"))
                style = snStyle(listOf("fontWeight" to "bold", "fontStyle" to "italic"))
            })
            KVManager.patch("test", vnode)
            assertTrue("New child should exist") { document.getElementById("test_new") != null }
        }
    }

    @Test
    fun patchByVnode() {
        run {
            val vnode1 = h("span", snOpt {
                attrs = snAttrs(listOf("id" to "test2"))
                style = snStyle(listOf("fontWeight" to "bold", "fontStyle" to "italic"))
            })
            val vnode2 = KVManager.patch("test", vnode1)
            val vnode3 = h("span", snOpt {
                attrs = snAttrs(listOf("id" to "test3"))
                style = snStyle(listOf("fontWeight" to "bold", "fontStyle" to "italic"))
            })
            KVManager.patch(vnode2, vnode3)
            assertTrue("Third child should exist") { document.getElementById("test3") != null }
        }
    }

    @Test
    fun virtualize() {
        run {
            val node = KVManager.virtualize("<div id=\"virtual\"><p>Virtual node</p></div>")
            KVManager.patch("test", node)
            val v = document.getElementById("virtual")
            assertTrue("New child should exist") { v != null }
            assertTrue("New child should have one child") { v?.children?.length == 1 }
        }
    }
}
