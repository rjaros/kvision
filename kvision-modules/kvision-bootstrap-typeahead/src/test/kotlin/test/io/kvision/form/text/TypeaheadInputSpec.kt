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
@file:Suppress("DEPRECATION")

package test.io.kvision.form.text

import io.kvision.core.getElementJQuery
import io.kvision.form.text.TypeaheadInput
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeaheadInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val si = TypeaheadInput(listOf("test1", "test2"), value = "test").apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(si)
            val value = si.getElementJQuery()?.`val`()
            assertEquals("test", value, "Should render typeahead input with correct value")
            val element = document.getElementById("test")
            val ariaId = element?.childNodes?.item(0)?.unsafeCast<HTMLElement>()?.attributes?.getNamedItem("aria-controls")?.value
            assertEqualsHtml(
                "<input class=\"form-control\" id=\"idti\" placeholder=\"place\" type=\"text\" value=\"test\" autocomplete=\"off\" aria-autocomplete=\"list\" aria-controls=\"$ariaId\"><ul class=\"typeahead dropdown-menu\" role=\"listbox\" aria-label=\"Search results\" id=\"$ariaId\" style=\"top: 0px; left: 0px; display: none;\"></ul><div class=\"sr-only\" role=\"status\" aria-live=\"polite\"></div>",
                element?.innerHTML,
                "Should render correct input control"
            )
        }
    }
}
