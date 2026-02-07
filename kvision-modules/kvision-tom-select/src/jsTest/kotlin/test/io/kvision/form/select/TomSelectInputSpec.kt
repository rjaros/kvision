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
package test.io.kvision.form.select

import io.kvision.form.select.TomSelectInput
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class TomSelectInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tomSelectInput = TomSelectInput(listOf("test1" to "Test 1", "test2" to "Test 2"), "test1", true).apply {
                placeholder = "Choose ..."
                emptyOption = true
            }
            root.add(tomSelectInput)
            val element = document.getElementById("test")
            val id = document.querySelector("select.form-select")?.id
            assertEqualsHtml(
                """<select class="form-select tomselected ts-hidden-accessible" placeholder="Choose ..." id="$id" tabindex="-1"><option value=""></option><option value="test1">Test 1</option></select><div class="ts-wrapper form-select single plugin-change_listener input-hidden full has-items"><div class="ts-control"><div data-value="test1" class="item" data-ts-item="">Test 1</div><input type="text" autocomplete="off" size="1" tabindex="0" role="combobox" aria-label="Label" aria-haspopup="listbox" aria-expanded="false" aria-controls="$id-ts-dropdown" id="$id-ts-control" placeholder="Choose ..." aria-activedescendant="$id-opt-2"></div><div class="ts-dropdown single plugin-change_listener" style="display: none;"><div role="listbox" tabindex="-1" class="ts-dropdown-content" id="$id-ts-dropdown"><div data-selectable="" data-value="" class="option" role="option" id="$id-opt-1">&nbsp;</div><div data-selectable="" data-value="test1" class="option selected active" role="option" id="$id-opt-2" aria-selected="true">Test 1</div><div data-selectable="" data-value="test2" class="option" role="option" id="$id-opt-3">Test 2</div></div></div></div>""",
                element?.innerHTML,
                "Should render correct tom-select input"
            )
        }
    }
}
