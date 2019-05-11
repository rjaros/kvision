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
package test.pl.treksoft.kvision.form.spinner

import pl.treksoft.kvision.form.spinner.Spinner
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class SpinnerSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", fixed = true)
            val ti = Spinner(value = 13, label = "Label").apply {
                placeholder = "place"
                name = "name"
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            assertEqualsHtml(
                "<div class=\"form-group\"><label class=\"control-label\" for=\"$id\">Label</label><div class=\"input-group kv-spinner-btn-vertical\"><span><div class=\"input-group  bootstrap-touchspin bootstrap-touchspin-injected\"><input class=\"form-control\" id=\"$id\" type=\"text\" value=\"13\" placeholder=\"place\" name=\"name\" disabled=\"disabled\"><span class=\"input-group-btn-vertical\"><button class=\"btn btn-default bootstrap-touchspin-up \" type=\"button\"><i class=\"fa fa-caret-up\"></i></button><button class=\"btn btn-default bootstrap-touchspin-down \" type=\"button\"><i class=\"fa fa-caret-down\"></i></button></span></div></span></div></div>",
                element?.innerHTML,
                "Should render correct spinner input form control"
            )
            ti.validatorError = "Validation Error"
            assertEqualsHtml(
                "<div class=\"form-group has-error\"><label class=\"control-label\" for=\"$id\">Label</label><div class=\"input-group kv-spinner-btn-vertical\"><span><div class=\"input-group  bootstrap-touchspin bootstrap-touchspin-injected\"><input class=\"form-control\" id=\"$id\" type=\"text\" value=\"13\" placeholder=\"place\" name=\"name\" disabled=\"disabled\"><span class=\"input-group-btn-vertical\"><button class=\"btn btn-default bootstrap-touchspin-up \" type=\"button\"><i class=\"fa fa-caret-up\"></i></button><button class=\"btn btn-default bootstrap-touchspin-down \" type=\"button\"><i class=\"fa fa-caret-down\"></i></button></span></div></span></div><span class=\"help-block small\">Validation Error</span></div>",
                element?.innerHTML,
                "Should render correct spinner input form control with validation error"
            )
        }
    }

    @Test
    fun spinUp() {
        run {
            val root = Root("test", fixed = true)
            val si = Spinner(value = 13)
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinUp")
            si.spinUp()
            assertEquals(14, si.value, "Should return changed value after spinUp")
        }
    }

    @Test
    fun spinDown() {
        run {
            val root = Root("test", fixed = true)
            val si = Spinner(value = 13)
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinDown")
            si.spinDown()
            assertEquals(12, si.value, "Should return changed value after spinDown")
        }
    }
}