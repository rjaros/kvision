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

import io.kvision.html.Align
import io.kvision.html.Link
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class TagSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tag = Tag(TAG.H1, "This is <b>h1</b>", rich = false, align = Align.CENTER)
            root.add(tag)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<h1 class=\"kv-text-center\">This is &lt;b&gt;h1&lt;/b&gt;</h1>",
                element?.innerHTML,
                "Should render correct html tag"
            )
        }
    }

    @Test
    fun renderRich() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tag = Tag(TAG.H1, "This is <b>h1</b>", rich = true, align = Align.RIGHT)
            root.add(tag)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<h1 class=\"kv-text-right\"><span style=\"display: contents;\">This is <b>h1</b></span></h1>",
                element?.innerHTML,
                "Should render correct html tag"
            )
        }
    }

    @Test
    fun renderAsContainer() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tag = Tag(TAG.P, align = Align.RIGHT)
            tag.add(Tag(TAG.DEL, "This is test"))
            tag.add(Link("abc", "/x"))
            root.add(tag)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<p class=\"kv-text-right\"><del>This is test</del><a href=\"/x\">abc</a></p>",
                element?.innerHTML,
                "Should render correct html tag with children"
            )
        }
    }

    @Test
    fun unaryPlus() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val tag = Tag(TAG.H1, rich = true) {
                +"This is <b>h1</b>"
            }
            root.add(tag)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<h1><span style=\"display: contents;\">This is <b>h1</b></span></h1>",
                element?.innerHTML,
                "Should render correct HTML markup for children"
            )
        }
    }
}
