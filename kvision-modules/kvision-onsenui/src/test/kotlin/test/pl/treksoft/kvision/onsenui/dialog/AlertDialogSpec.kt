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
package test.pl.treksoft.kvision.onsenui.dialog

import pl.treksoft.kvision.html.div
import pl.treksoft.kvision.onsenui.dialog.alertDialog
import pl.treksoft.kvision.onsenui.dialog.alertDialogButton
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class AlertDialogSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            val dialog = root.alertDialog("title") {
                div("test")
                alertDialogButton("OK")
            }
            dialog.showDialog()
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ons-alert-dialog style=\"display: block; z-index: 10001;\" ><div class=\"alert-dialog-mask\" style=\"z-index: 20000; opacity: 0;\"></div><div class=\"alert-dialog\" style=\"z-index: 20001; transform: translate3d(-50%, -50%, 0px) scale3d(1.3, 1.3, 1); opacity: 0;\"><div class=\"alert-dialog-container\"><div class=\"alert-dialog-title\">title</div><div class=\"alert-dialog-content\"><div>test</div></div><div class=\"alert-dialog-footer\"><ons-alert-dialog-button>OK</ons-alert-dialog-button></div></div></div></ons-alert-dialog>",
                element?.innerHTML?.replace(Regex("data-device-back-button-handler-id=\"[0-9]+\""), ""),
                "Should render Onsen UI alert dialog component"
            )
            dialog.hideDialog()
        }
    }
}
