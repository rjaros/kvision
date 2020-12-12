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
package test.pl.treksoft.kvision.onsenui.list

import pl.treksoft.kvision.onsenui.list.header
import pl.treksoft.kvision.onsenui.list.item
import pl.treksoft.kvision.onsenui.list.onsList
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class OnsListSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            root.onsList(inset = true, noborder = true) {
                header("list header")
                item {
                    left("left")
                    center("center")
                    right("right")
                }
            }

            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ons-list class=\"list list--inset list--noborder\" modifier=\"inset noborder\"><ons-list-header class=\"list-header\">list header</ons-list-header><ons-list-item class=\"list-item\"><div class=\"center list-item__center\">center</div><div class=\"left list-item__left\">left</div><div class=\"right list-item__right\">right</div></ons-list-item></ons-list>",
                element?.innerHTML,
                "Should render Onsen UI list component"
            )
        }
    }
}
