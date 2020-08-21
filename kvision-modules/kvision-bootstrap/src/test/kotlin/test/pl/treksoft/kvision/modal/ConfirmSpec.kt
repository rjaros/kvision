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
import pl.treksoft.jquery.invoke
import pl.treksoft.jquery.get
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.modal.Confirm
import test.pl.treksoft.kvision.DomSpec
import kotlinx.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ConfirmSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            Confirm.show("Confirm caption", "Confirm content")
            val confirm = document.getElementById("test")?.let { jQuery(it).find(".modal")[0] }
            assertNotNull(confirm, "Should show confirm window")
            val title = document.getElementById("test")?.let { jQuery(it).find(".modal-title").html() }
            assertEquals("Confirm caption", title, "Should render confirm window with correct caption")
            val body = document.getElementById("test")?.let { jQuery(it).find(".modal-body").html() }
            assertEquals("<div>Confirm content</div>", body, "Should render confirm window with correct content")
            val buttons = document.getElementById("test")?.let { jQuery(it).find(".modal-footer").find("button") }
            assertEquals(2, buttons?.length, "Should render confirm window with two buttons")
            val button = document.getElementById("test")?.let { jQuery(it).find(".modal-footer").find("button")[0] }
            button?.click()
            val confirm2 = document.getElementById("test")?.let { jQuery(it).find(".modal")[0] }
            assertNull(confirm2, "Should hide confirm  window after clicking YES")
        }
    }
}