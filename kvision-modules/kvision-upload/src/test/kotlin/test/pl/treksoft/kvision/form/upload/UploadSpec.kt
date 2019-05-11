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
package test.pl.treksoft.kvision.form.upload

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.form.upload.Upload
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class UploadSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", fixed = true)
            val upi = Upload(multiple = true)
            val id = upi.input.id
            root.add(upi)
            val content = document.getElementById("test")?.let { jQuery(it).find("input.form-control")[0]?.outerHTML }
            assertEqualsHtml(
                "<input class=\"form-control\" id=\"$id\" type=\"file\" multiple=\"true\">",
                content,
                "Should render correct file input control for multiple files"
            )
            upi.multiple = false
            val content2 = document.getElementById("test")?.let { jQuery(it).find("input.form-control")[0]?.outerHTML }
            assertEqualsHtml(
                "<input class=\"form-control\" id=\"$id\" type=\"file\">",
                content2,
                "Should render correct file input control for single file"
            )
        }
    }

}
