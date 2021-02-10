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

import io.kvision.form.check.Radio
import io.kvision.form.check.RadioGroupInput
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class RadioGroupInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val ci = RadioGroupInput(options = listOf("a" to "A", "b" to "B"), value = "a").apply {
                disabled = true
                inline = true
            }
            root.add(ci)
            val element = document.getElementById("test")
            val name = ci.getChildren().filterIsInstance<Radio>().first().input.name
            val rid1 = ci.getChildren().filterIsInstance<Radio>().first().input.id
            val rid2 = ci.getChildren().filterIsInstance<Radio>().last().input.id
            assertEqualsHtml(
                "<div class=\"form-group kv-radiogroup-inline\"><div class=\"form-check abc-radio\"><input class=\"form-check-input\" id=\"$rid1\" type=\"radio\" name=\"$name\" disabled=\"disabled\" value=\"a\"><label class=\"form-check-label\" for=\"$rid1\">A</label></div><div class=\"form-check abc-radio\"><input class=\"form-check-input\" id=\"$rid2\" type=\"radio\" name=\"$name\" disabled=\"disabled\" value=\"b\"><label class=\"form-check-label\" for=\"$rid2\">B</label></div></div>",
                element?.innerHTML,
                "Should render correct radio button group form control"
            )
        }
    }

}