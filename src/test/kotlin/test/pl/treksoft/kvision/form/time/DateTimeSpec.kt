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
package test.pl.treksoft.kvision.form.time

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.time.DateTime
import pl.treksoft.kvision.utils.toStringF
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.js.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val data = Date()
            val ti = DateTime(value = data, label = "Label").apply {
                placeholder = "place"
                name = "name"
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            val datastr = data.toStringF(ti.format)
            assertEquals(
                "<div class=\"form-group\"><label class=\"control-label\" for=\"$id\">Label</label><input class=\"form-control\" id=\"$id\" type=\"text\" placeholder=\"place\" name=\"name\" disabled=\"\" value=\"$datastr\"></div>",
                element?.innerHTML,
                "Should render correct date time input form control"
            )
            ti.validatorError = "Validation Error"
            assertEquals(
                "<div class=\"form-group has-error\"><label class=\"control-label\" for=\"$id\">Label</label><input class=\"form-control\" id=\"$id\" type=\"text\" placeholder=\"place\" name=\"name\" disabled=\"\" value=\"$datastr\"><span class=\"help-block small\">Validation Error</span></div>",
                element?.innerHTML,
                "Should render correct date time input form control with validation error"
            )
        }
    }

}