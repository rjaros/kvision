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
package test.io.kvision.toast

import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.toast.ToastContainer
import io.kvision.toast.ToastContainerPosition
import kotlinx.browser.document
import kotlin.test.Test

class ToastContainerSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val toastContainer = ToastContainer(ToastContainerPosition.TOPLEFT)
            toastContainer.showToast("Test message")
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"position-relative\" aria-live=\"polite\" aria-atomic=\"true\"><div class=\"toast-container position-fixed p-3 top-0 start-0\"><div class=\"toast fade show showing\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\" data-bs-autohide=\"true\" data-bs-delay=\"5000\" data-bs-animation=\"true\"><div class=\"d-flex\"><div class=\"toast-body\">Test message</div><button class=\"me-2 m-auto btn-close\" data-bs-dismiss=\"toast\" type=\"button\" aria-label=\"Close\"></button></div></div></div></div>",
                element?.innerHTML,
                "Should render correct toast message in the container"
            )
        }
    }

}
