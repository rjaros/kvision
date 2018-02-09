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
import pl.treksoft.kvision.form.select.SELECTWIDTHTYPE
import pl.treksoft.kvision.form.select.Select
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class SelectSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val select = Select(listOf("test1" to "Test 1", "test2" to "Test 2"), "test1", true, null, "Label").apply {
                liveSearch = true
                placeholder = "Choose ..."
                selectWidthType = SELECTWIDTHTYPE.FIT
                emptyOption = true
            }
            root.add(select)
            val element = document.getElementById("test")
            val id = select.input.id
            assertTrue(
                true == element?.innerHTML?.startsWith("<div class=\"form-group\"><label class=\"control-label\" for=\"$id\">Label</label>"),
                "Should render correct select form control"
            )
            assertTrue(
                true == element?.innerHTML?.endsWith("<select class=\"form-control selectpicker\" id=\"$id\" multiple=\"\" data-live-search=\"true\" title=\"Choose ...\" data-style=\"btn-default\" data-width=\"fit\" tabindex=\"-98\"><option value=\"#kvnull\"></option><option value=\"test1\">Test 1</option><option value=\"test2\">Test 2</option></select></div></div>"),
                "Should render correct select form control"
            )
        }
    }

}