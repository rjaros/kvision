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
package test.io.kvision.onsenui.form

import io.kvision.onsenui.form.OnsRadio
import io.kvision.onsenui.form.onsRadioGroup
import io.kvision.panel.ContainerType
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class OnsRadioGroupSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            val r = root.onsRadioGroup(listOf("1" to "Option 1", "2" to "Option 2"), value = "2", name = "test", label = "label")

            val id = r.container.id
            val inputId1 = (r.getChildren().firstOrNull() as? OnsRadio)?.input?.inputId
            val inputId2 = (r.getChildren().lastOrNull() as? OnsRadio)?.input?.inputId
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"form-group kv-ons-form-group\"><label class=\"control-label\" for=\"$id\">label</label><div id=\"$id\" class=\"kv-radiogroup-container\"><div class=\"form-group kv-ons-form-group kv-ons-checkbox\"><ons-radio type=\"radio\" name=\"test\" value=\"1\" input-id=\"$inputId1\"></ons-radio><label for=\"$inputId1\">Option 1</label></div><div class=\"form-group kv-ons-form-group kv-ons-checkbox\"><ons-radio type=\"radio\" name=\"test\" value=\"2\" input-id=\"$inputId2\"></ons-radio><label for=\"$inputId2\">Option 2</label></div></div></div>",
                element?.innerHTML,
                "Should render Onsen UI radio group form component"
            )
        }
    }
}
