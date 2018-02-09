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

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.spinner.SpinnerInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class SpinnerInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val si = SpinnerInput(value = 13).apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(si)
            val value = si.getElementJQuery()?.`val`()
            assertEquals("13", value, "Should render spinner input with correct value")
        }
    }

    @Test
    fun spinUp() {
        run {
            val root = Root("test")
            val si = SpinnerInput(value = 13).apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinUp")
            si.spinUp()
            assertEquals(14, si.value, "Should return changed value after spinUp")
        }
    }

    @Test
    fun spinDown() {
        run {
            val root = Root("test")
            val si = SpinnerInput(value = 13).apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinDown")
            si.spinDown()
            assertEquals(12, si.value, "Should return changed value after spinDown")
        }
    }
}