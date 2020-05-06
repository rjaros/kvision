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
import pl.treksoft.kvision.modal.Alert
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AlertSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            Alert.show("Alert caption", "Alert content", animation = false)
            val alert = document.getElementById("test")?.let { jQuery(it).parent().parent().find(".modal")[0] }
            assertNotNull(alert, "Should show alert window")
            val title = document.getElementById("test")?.let { jQuery(it).parent().parent().find(".modal-title").html() }
            assertEquals("Alert caption", title, "Should render alert window with correct caption")
            val body = document.getElementById("test")?.let { jQuery(it).parent().parent().find(".modal-body").html() }
            assertEquals("<div>Alert content</div>", body, "Should render alert window with correct content")
            val footer = document.getElementById("test")?.let { jQuery(it).parent().parent().find(".modal-footer").html() }
            assertEqualsHtml(
                "<button class=\"btn btn-primary\" type=\"button\"><i class=\"fas fa-check\"></i> OK</button>",
                footer,
                "Should render alert window with correct footer"
            )
            val button = document.getElementById("test")?.let { jQuery(it).parent().parent().find(".modal-footer").find("button") }
            button?.click()
            val alert2 = document.getElementById("test")?.let { jQuery(it).parent().parent().find(".modal")[0] }
            assertNull(alert2, "Should hide alert window after clicking OK")
        }
    }
}
