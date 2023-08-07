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

import io.kvision.ImaskModule
import io.kvision.form.text.ImaskOptions
import io.kvision.form.text.PatternMask
import io.kvision.form.text.TextInput
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import org.w3c.dom.HTMLInputElement
import kotlin.test.Test

class ImaskSpec : DomSpec {

    @Test
    fun render() {
        run {
            ImaskModule.initialize()
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val textInput = TextInput {
                maskOptions = ImaskOptions(pattern = PatternMask("00{-}000", lazy = false, eager = true))
            }
            root.add(textInput)
            val inputValue = document.getElementById("test")?.childNodes?.item(0)?.unsafeCast<HTMLInputElement>()?.value
            assertEqualsHtml(
                "__-___",
                inputValue,
                "Should render text input with correct mask placeholder"
            )
            textInput.value = "93503"
            val inputValue2 = document.getElementById("test")?.childNodes?.item(0)?.unsafeCast<HTMLInputElement>()?.value
            assertEqualsHtml(
                "93-503",
                inputValue2,
                "Should display correctly formatted text input value"
            )
            assertEqualsHtml(
                "93-503",
                textInput.value,
                "Should return correctly formatted text input value"
            )
        }
    }

}
