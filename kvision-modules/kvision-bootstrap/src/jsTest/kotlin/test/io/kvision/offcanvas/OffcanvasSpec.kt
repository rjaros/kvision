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
package test.io.kvision.offcanvas

import io.kvision.offcanvas.Offcanvas
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class OffcanvasSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val offcanvas = Offcanvas("Offcanvas")
            root.add(offcanvas)
            offcanvas.show()
            val id = offcanvas.id
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"offcanvas offcanvas-start showing\" aria-labelledby=\"${id}_title\" id=\"${id}\" tabindex=\"-1\" aria-modal=\"true\" role=\"dialog\"><div class=\"offcanvas-header\"><h5 class=\"offcanvas-title\" id=\"${id}_title\">Offcanvas</h5><button class=\"btn-close\" data-bs-dismiss=\"offcanvas\" data-bs-target=\"#${id}\" type=\"button\" aria-label=\"Close\"></button></div><div class=\"offcanvas-body\"></div></div><div class=\"offcanvas-backdrop fade show\"></div>",
                element?.innerHTML,
                "Should render correct offcanvas element"
            )
        }
    }
}
