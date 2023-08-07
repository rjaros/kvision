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
package test.io.kvision.form.text

import io.kvision.form.text.TomTypeaheadInput
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class TomTypeaheadInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tomTypeaheadInput = TomTypeaheadInput(listOf("Test 1", "Test 2"), value = "Test 3").apply {
                placeholder = "Enter data ..."
            }
            root.add(tomTypeaheadInput)
            val element = document.getElementById("test")
            val id = document.querySelector("input.form-control")?.id
            assertEqualsHtml(
                """<input class="kv-typeahead form-control tomselected ts-hidden-accessible" placeholder="Enter data ..." type="text" value="Test 3" autocomplete="off" id="$id" tabindex="-1"><div class="ts-wrapper kv-typeahead form-control single plugin-restore_on_backspace plugin-change_listener input-hidden full has-items"><div class="ts-control"><div data-value="Test 3" class="item" data-ts-item="">Test 3</div><input type="text" autocomplete="off" size="1" tabindex="0" role="combobox" aria-haspopup="listbox" aria-expanded="false" aria-controls="$id-ts-dropdown" id="$id-ts-control" placeholder="Enter data ..." aria-activedescendant="$id-opt-3"></div><div class="ts-dropdown single plugin-restore_on_backspace plugin-change_listener" style="display: none;"><div role="listbox" tabindex="-1" class="ts-dropdown-content" id="$id-ts-dropdown"><div data-selectable="" data-value="Test 1" class="option" role="option" id="$id-opt-1">Test 1</div><div data-selectable="" data-value="Test 2" class="option" role="option" id="$id-opt-2">Test 2</div><div data-selectable="" data-value="Test 3" class="option selected active" role="option" id="$id-opt-3" aria-selected="true">Test 3</div></div></div></div>""",
                element?.innerHTML,
                "Should render correct tom-select typeahead input"
            )
        }
    }
}
