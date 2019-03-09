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
package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class TagSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val tag = Tag(TAG.H1, "This is <b>h1</b>", rich = false, align = Align.CENTER)
            root.add(tag)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<h1 class=\"text-center\">This is &lt;b&gt;h1&lt;/b&gt;</h1>",
                element?.innerHTML,
                "Should render correct html tag"
            )
        }
    }

    @Test
    fun renderRich() {
        run {
            val root = Root("test", true)
            val tag = Tag(TAG.H1, "This is <b>h1</b>", rich = true, align = Align.RIGHT)
            root.add(tag)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<h1 class=\"text-right\"><span>This is <b>h1</b></span></h1>",
                element?.innerHTML,
                "Should render correct html tag"
            )
        }
    }

    @Test
    fun renderAsContainer() {
        run {
            val root = Root("test", true)
            val tag = Tag(TAG.P, align = Align.RIGHT)
            tag.add(Tag(TAG.DEL, "This is test"))
            tag.add(Link("abc", "/x"))
            root.add(tag)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<p class=\"text-right\"><del>This is test</del><a href=\"/x\">abc</a></p>",
                element?.innerHTML,
                "Should render correct html tag with children"
            )
        }
    }

    @Test
    fun renderUnaryPlus() {
        run {
            val root = Root("test", true)
            val tag = Tag(TAG.H1, rich = true) {
                +"This is <b>h1</b>"
            }
            root.add(tag)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<h1><span>This is <b>h1</b></span></h1>",
                element?.innerHTML,
                "Should render correct html tag with children"
            )
        }
    }
}
