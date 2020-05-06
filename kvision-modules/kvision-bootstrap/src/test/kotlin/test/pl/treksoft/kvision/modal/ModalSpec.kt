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
package test.pl.treksoft.kvision.modal

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.modal.Modal
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ModalSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val modal = Modal("Modal")
            modal.show()
            val content = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            assertEquals("Modal", content, "Should render correct modal")
            modal.hide()
        }
    }

    @Test
    fun toggle() {
        run {
            Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val modal = Modal("Modal")
            modal.toggle()
            val content = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            assertEquals("Modal", content, "Should show modal after toggle")
            modal.toggle()
            val content2 = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            assertNull(content2, "Should hide modal after second toggle")
            modal.hide()
        }
    }

}