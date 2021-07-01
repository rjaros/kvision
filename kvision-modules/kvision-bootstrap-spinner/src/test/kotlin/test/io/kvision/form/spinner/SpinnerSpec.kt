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
package test.io.kvision.form.spinner

import io.kvision.form.spinner.Spinner
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class SpinnerSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val ti = Spinner(value = 13, label = "Label").apply {
                placeholder = "place"
                name = "name"
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            assertEqualsHtml(
                "<div class=\"form-group mb-3\"><label class=\"form-label\" for=\"$id\">Label</label><div class=\"input-group kv-spinner kv-spinner-btn-vertical\"><span><div class=\"input-group  bootstrap-touchspin bootstrap-touchspin-injected\"><input class=\"form-control\" id=\"$id\" type=\"text\" value=\"13\" placeholder=\"place\" name=\"name\" disabled=\"disabled\"><span class=\"input-group-btn-vertical\"><button class=\"btn btn-secondary bootstrap-touchspin-up \" type=\"button\">▲</button><button class=\"btn btn-secondary bootstrap-touchspin-down \" type=\"button\">▼</button></span></div></span></div></div>",
                element?.innerHTML,
                "Should render correct spinner input form control"
            )
            ti.validatorError = "Validation Error"
            assertEqualsHtml(
                "<div class=\"form-group mb-3 text-danger\"><label class=\"form-label\" for=\"$id\">Label</label><div class=\"input-group kv-spinner kv-spinner-btn-vertical is-invalid\"><span><div class=\"input-group  bootstrap-touchspin bootstrap-touchspin-injected\"><input class=\"form-control is-invalid\" id=\"$id\" type=\"text\" value=\"13\" placeholder=\"place\" name=\"name\" disabled=\"disabled\"><span class=\"input-group-btn-vertical\"><button class=\"btn btn-secondary bootstrap-touchspin-up \" type=\"button\">▲</button><button class=\"btn btn-secondary bootstrap-touchspin-down \" type=\"button\">▼</button></span></div></span></div><div class=\"invalid-feedback\">Validation Error</div></div>",
                element?.innerHTML,
                "Should render correct spinner input form control with validation error"
            )
        }
    }

    @Test
    fun spinUp() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
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
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val si = Spinner(value = 13)
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinDown")
            si.spinDown()
            assertEquals(12, si.value, "Should return changed value after spinDown")
        }
    }
}