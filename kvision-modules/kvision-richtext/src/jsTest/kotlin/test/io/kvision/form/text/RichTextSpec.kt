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
package test.io.kvision.form.text

import io.kvision.jquery.jQuery
import io.kvision.jquery.invoke
import io.kvision.jquery.get
import io.kvision.form.text.RichText
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class RichTextSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val hai = RichText(value = "abc", label = "Field").apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(hai)
            val iid = hai.input.id
            val content = document.getElementById("test")?.let { jQuery(it).find("trix-editor")[0]?.outerHTML }
            assertTrue(
                content?.startsWith("<trix-editor") == true,
                "Should render correct html area form control"
            )
            val label = document.getElementById("test")?.let { jQuery(it).find("label")[0]?.outerHTML }
            assertEqualsHtml(
                "<label class=\"form-label\" for=\"$iid\">Field</label>",
                label,
                "Should render correct label for html area form control"
            )
        }
    }

}