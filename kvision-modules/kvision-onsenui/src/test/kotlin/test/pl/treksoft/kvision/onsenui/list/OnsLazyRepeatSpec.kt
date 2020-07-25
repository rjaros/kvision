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

import pl.treksoft.kvision.onsenui.OnsenUi
import pl.treksoft.kvision.onsenui.core.page
import pl.treksoft.kvision.onsenui.list.onsLazyRepeat
import pl.treksoft.kvision.onsenui.list.onsList
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class OnsLazyRepeatSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            root.page {
                onsList {
                    onsLazyRepeat(1) {
                        OnsenUi.createElement("<ons-list-item>item $it</ons-list-item>")
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ons-page class=\"page\"><div class=\"page__background\"></div><div class=\"page__content\"><ons-list class=\"list\"><ons-lazy-repeat></ons-lazy-repeat></ons-list></div><span></span></ons-page>",
                element?.innerHTML,
                "Should render Onsen UI lazy repeat component"
            )
        }
    }
}
