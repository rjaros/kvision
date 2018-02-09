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
import pl.treksoft.kvision.form.select.SelectOptGroup
import pl.treksoft.kvision.form.select.SelectOption
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectOptGroupSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val selectOptGroup = SelectOptGroup("Group", listOf("test1" to "Test 1", "test2" to "Test 2"), 2)
            root.add(selectOptGroup)
            val element = document.getElementById("test")
            assertEquals(
                "<optgroup label=\"Group\" data-max-options=\"2\"><option value=\"test1\">Test 1</option><option value=\"test2\">Test 2</option></optgroup>",
                element?.innerHTML,
                "Should render correct select option group"
            )
            selectOptGroup.add(SelectOption("test3", "Test 3"))
            assertEquals(
                "<optgroup label=\"Group\" data-max-options=\"2\"><option value=\"test1\">Test 1</option><option value=\"test2\">Test 2</option><option value=\"test3\">Test 3</option></optgroup>",
                element?.innerHTML,
                "Should render correct select option group with added option"
            )
        }
    }

}