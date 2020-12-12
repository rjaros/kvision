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

import pl.treksoft.kvision.form.time.DateTime
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.types.toStringF
import pl.treksoft.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.js.Date
import kotlin.test.Test

class DateTimeSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val data = Date()
            val ti = DateTime(value = data, label = "Label").apply {
                placeholder = "place"
                name = "name"
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.input.id
            val datastr = data.toStringF(ti.format)
            assertEqualsHtml(
                "<div class=\"form-group\"><label class=\"control-label\" for=\"$id\">Label</label><div class=\"input-group date\"><input class=\"form-control\" id=\"$id\" placeholder=\"place\" name=\"name\" disabled=\"\" type=\"text\" value=\"$datastr\"><div class=\"input-group-append\"><span class=\"input-group-text datepickerbutton\"><span class=\"fas fa-calendar-alt\"></span></span></div></div></div>",
                element?.innerHTML,
                "Should render correct date time input form control"
            )
            ti.validatorError = "Validation Error"
            assertEqualsHtml(
                "<div class=\"form-group text-danger\"><label class=\"control-label\" for=\"$id\">Label</label><div class=\"input-group date is-invalid\"><input class=\"form-control is-invalid\" id=\"$id\" placeholder=\"place\" name=\"name\" disabled=\"\" type=\"text\" value=\"$datastr\"><div class=\"input-group-append\"><span class=\"input-group-text datepickerbutton\"><span class=\"fas fa-calendar-alt\"></span></span></div></div><div class=\"invalid-feedback\">Validation Error</div></div>",
                element?.innerHTML,
                "Should render correct date time input form control with validation error"
            )
        }
    }

}