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
package test.pl.treksoft.kvision.onsenui.splitter

import pl.treksoft.kvision.html.div
import pl.treksoft.kvision.onsenui.core.page
import pl.treksoft.kvision.onsenui.splitter.splitter
import pl.treksoft.kvision.onsenui.splitter.splitterContent
import pl.treksoft.kvision.onsenui.splitter.splitterSide
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class SplitterSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            root.splitter {
                splitterSide {
                    page {
                        div("menu")
                    }
                }
                splitterContent {
                    page {
                        div("content")
                    }
                }
            }

            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ons-splitter style=\"touch-action: pan-y; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\"><ons-splitter-side side=\"left\" mode=\"split\" style=\"width: 80%;\"><ons-page class=\"page\"><div class=\"page__background\"></div><div class=\"page__content\"><div>menu</div></div><span></span></ons-page></ons-splitter-side><ons-splitter-content style=\"left: 80%;\"><ons-page class=\"page\"><div class=\"page__background\"></div><div class=\"page__content\"><div>content</div></div><span></span></ons-page></ons-splitter-content><ons-splitter-mask style=\"display: none;\"></ons-splitter-mask></ons-splitter>",
                element?.innerHTML?.replace(Regex("data-device-back-button-handler-id=\"[0-9]+\""),""),
                "Should render Onsen UI splitter component"
            )
        }
    }
}
