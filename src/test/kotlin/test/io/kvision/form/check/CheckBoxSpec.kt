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
package test.io.kvision.form.check

import io.kvision.form.check.CheckBox
import io.kvision.form.check.CheckBoxStyle
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class CheckBoxSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val ci = CheckBox(value = true, label = "Label").apply {
                name = "name"
                style = CheckBoxStyle.DANGER
                disabled = true
                circled = true
                inline = true
            }
            root.add(ci)
            val element = document.getElementById("test")
            val id = ci.input.id
            assertEqualsHtml(
                "<div class=\"form-check abc-checkbox abc-checkbox-danger abc-checkbox-circle form-check-inline\"><input class=\"form-check-input\" id=\"$id\" type=\"checkbox\" checked=\"checked\" name=\"name\" disabled=\"disabled\"><label class=\"form-check-label\" for=\"$id\">Label<span></span></label></div>",
                element?.innerHTML,
                "Should render correct checkbox form control"
            )
            ci.style = CheckBoxStyle.INFO
            ci.circled = false
            ci.inline = false
            assertEqualsHtml(
                "<div class=\"form-check abc-checkbox abc-checkbox-info\"><input class=\"form-check-input\" id=\"$id\" type=\"checkbox\" checked=\"checked\" name=\"name\" disabled=\"disabled\"><label class=\"form-check-label\" for=\"$id\">Label<span></span></label></div>",
                element?.innerHTML,
                "Should render correct checkbox form control"
            )
        }
    }

}