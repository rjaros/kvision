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
package test.pl.treksoft.kvision.onsenui.form

import pl.treksoft.kvision.onsenui.form.onsSelect
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class OnsSelectSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            val select = root.onsSelect(
                listOf("1" to "Option 1", "2" to "Option 2"),
                value = "2",
                label = "Label",
                name = "test"
            )

            val id = select.input.selectId
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"form-group kv-ons-form-group\"><label class=\"control-label\" for=\"$id\">Label</label><ons-select class=\"kv-ons-form-control form-control select-underbar select--underbar\" name=\"test\" select-id=\"$id\" modifier=\"underbar\"><option value=\"1\">Option 1</option><option value=\"2\" selected=\"selected\">Option 2</option></ons-select></div>",
                element?.innerHTML,
                "Should render Onsen UI select form component"
            )
        }
    }
}
