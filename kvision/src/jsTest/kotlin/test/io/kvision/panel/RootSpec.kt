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
package test.io.kvision.panel

import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class RootSpec : DomSpec {

    @Test
    fun getSnClass() {
        run {
            Root("test")
            val rootElem = document.getElementById("test")
            assertTrue("Standard root container has no css class") { rootElem?.className == "" }
        }
    }

    @Test
    fun getSnClassFluid() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val rootElem = document.getElementById("test")
            assertTrue("Fixed root container has correct css class") { rootElem?.className == "container" }
        }
    }

    @Test
    fun getRoot() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val r = root.getRoot()
            assertTrue("Should return self") { r == root }
        }
    }
}