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

import io.kvision.form.select.TomSelect
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test


class TomSelectSpec : DomSpec {
    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val select =
                TomSelect(listOf("test1" to "Test 1", "test2" to "Test 2"), "test1", true, label = "Label").apply {
                    placeholder = "Choose ..."
                    emptyOption = true
                }
            root.add(select)
            val id = select.input.id
            assertEqualsHtml(
                """<div class="form-group kv-mb-3"><label class="form-label" for="$id-ts-control" id="$id-ts-label">Label</label><select class="form-control form-select tomselected ts-hidden-accessible" id="$id" placeholder="Choose ..." tabindex="-1" aria-label="Label"><option value=""></option><option value="test1">Test 1</option></select><div class="ts-wrapper form-control form-select single plugin-change_listener input-hidden full has-items"><div class="ts-control"><div data-value="test1" class="item" data-ts-item="">Test 1</div><input type="text" autocomplete="off" size="1" tabindex="0" role="combobox" aria-haspopup="listbox" aria-expanded="false" aria-controls="$id-ts-dropdown" id="$id-ts-control" aria-labelledby="$id-ts-label" placeholder="Choose ..." aria-activedescendant="$id-opt-2"></div><div class="ts-dropdown single plugin-change_listener" style="display: none;"><div role="listbox" tabindex="-1" class="ts-dropdown-content" id="$id-ts-dropdown" aria-labelledby="$id-ts-label"><div data-selectable="" data-value="" class="option" role="option" id="$id-opt-1">&nbsp;</div><div data-selectable="" data-value="test1" class="option selected active" role="option" id="$id-opt-2" aria-selected="true">Test 1</div><div data-selectable="" data-value="test2" class="option" role="option" id="$id-opt-3">Test 2</div></div></div></div></div>""",
                document.getElementById("test")?.innerHTML,
                "Should render correct tom-select form control"
            )
        }
    }
}
