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
package test.pl.treksoft.kvision.form.select

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.select.SelectWidthType
import pl.treksoft.kvision.form.select.SelectInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class SelectInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val selectInput = SelectInput(listOf("test1" to "Test 1", "test2" to "Test 2"), "test1", true).apply {
                liveSearch = true
                placeholder = "Choose ..."
                selectWidthType = SelectWidthType.FIT
                emptyOption = true
            }
            root.add(selectInput)
            val element = document.getElementById("test")
            assertTrue(
                true == element?.innerHTML?.startsWith("<div class=\"dropdown bootstrap-select show-tick fit-width\"><select class=\"selectpicker\" multiple=\"multiple\" data-live-search=\"true\" title=\"Choose ...\" data-style=\"btn-default\" data-width=\"fit\" tabindex=\"-98\"><option value=\"#kvnull\"></option><option value=\"test1\">Test 1</option><option value=\"test2\">Test 2</option></select>"),
                "Should render correct select input"
            )
        }
    }

}