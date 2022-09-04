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
package test.io.kvision.onsenui.form

import io.kvision.html.InputType
import io.kvision.onsenui.form.onsText
import io.kvision.panel.ContainerType
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class OnsTextSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            val text = root.onsText(InputType.EMAIL, placeholder = "password", label = "Label") {
                this.autofocus = true
            }
            val id = text.input.inputId
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"kv-ons-form-group form-group kv-mb-3\"><label class=\"form-label\" for=\"${id}\">Label</label><ons-input class=\"kv-ons-form-control form-control\" placeholder=\"password\" autofocus=\"autofocus\" type=\"email\" input-id=\"${id}\" modifier=\"underbar\"></ons-input></div>",
                element?.innerHTML,
                "Should render Onsen UI text form component"
            )
        }
    }
}
