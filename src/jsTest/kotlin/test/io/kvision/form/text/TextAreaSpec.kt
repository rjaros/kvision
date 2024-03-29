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

import io.kvision.form.text.TextArea
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class TextAreaSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val ti = TextArea(cols = 5, rows = 2, value = "abc", label = "Label").apply {
                placeholder = "place"
                name = "name"
                maxlength = 15
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            assertEqualsHtml(
                "<div class=\"form-group kv-mb-3\"><label class=\"form-label\" for=\"$id\">Label</label><textarea class=\"form-control\" id=\"$id\" placeholder=\"place\" name=\"name\" maxlength=\"15\" disabled=\"disabled\" cols=\"5\" rows=\"2\">abc</textarea></div>",
                element?.innerHTML,
                "Should render correct input form control"
            )
        }
    }

}