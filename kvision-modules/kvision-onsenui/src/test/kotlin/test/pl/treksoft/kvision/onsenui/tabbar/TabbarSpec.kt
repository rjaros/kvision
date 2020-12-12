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
package test.pl.treksoft.kvision.onsenui.tabbar

import pl.treksoft.kvision.onsenui.core.page
import pl.treksoft.kvision.onsenui.tabbar.tab
import pl.treksoft.kvision.onsenui.tabbar.tabbar
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class TabbarSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            root.page {
                tabbar {
                    tab("tab 1") {
                        page {
                        }
                    }
                    tab("tab 2") {
                        page {
                        }
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ons-page class=\"page page--wrapper\"><div class=\"page__background\"></div><div class=\"page__content\" style=\"\"><ons-tabbar id=\"kv_ons_tabbar_0\"><div class=\"tabbar__content ons-tabbar__content ons-swiper\" style=\"touch-action: pan-y; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\"><div class=\"ons-swiper-target\" style=\"transform: translate3d(0px, 0px, 0px);\"></div><div class=\"ons-swiper-blocker\"></div></div><div class=\"tabbar ons-tabbar__footer ons-swiper-tabbar\"><ons-tab page=\"kv_ons_tab_0\" label=\"tab 1\" active=\"\"></ons-tab><ons-tab page=\"kv_ons_tab_1\" label=\"tab 2\"></ons-tab><div class=\"tabbar__border\"></div></div></ons-tabbar></div><span></span></ons-page>",
                element?.innerHTML,
                "Should render Onsen UI tab bar component"
            )
        }
    }
}