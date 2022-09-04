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

import io.kvision.html.Autocomplete
import io.kvision.html.InputType
import io.kvision.onsenui.form.onsTextInput
import io.kvision.panel.ContainerType
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class OnsTextInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            root.onsTextInput(InputType.PASSWORD, placeholder = "password") {
                this.autocomplete = Autocomplete.OFF
            }

            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ons-input class=\"kv-ons-form-control form-control\" placeholder=\"password\" type=\"password\" autocomplete=\"off\"></ons-input>",
                element?.innerHTML,
                "Should render Onsen UI text input component"
            )
        }
    }
}
