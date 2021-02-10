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
package test.io.kvision.html

import io.kvision.html.TextNode
import io.kvision.html.bold
import io.kvision.html.div
import io.kvision.html.span
import io.kvision.html.textNode
import io.kvision.panel.Root
import io.kvision.utils.px
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class TextNodeSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val textNode = TextNode("This is some text")
            root.add(textNode)
            val element = document.getElementById("test")
            assertEqualsHtml("This is some text", element?.innerHTML, "Should render correct text")
        }
    }

    @Test
    fun complexMarkup() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                div {
                    bold("Some bold text")
                    textNode(" and now normal text ") {
                        margin = 20.px
                    }
                    span("and some text inside span")
                    textNode("and <i>text</i> with formatting", rich = true)
                    textNode(".")
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><b>Some bold text</b> and now normal text <span>and some text inside span</span><span>and <i>text</i> with formatting</span>.</div>",
                element?.innerHTML,
                "Should render complex markup"
            )
        }
    }
}
