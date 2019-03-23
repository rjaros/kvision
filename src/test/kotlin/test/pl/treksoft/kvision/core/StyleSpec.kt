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
package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.core.Col
import pl.treksoft.kvision.core.Color
import pl.treksoft.kvision.core.Overflow
import pl.treksoft.kvision.core.Style
import pl.treksoft.kvision.core.Style.Companion.style
import pl.treksoft.kvision.core.Widget.Companion.widget
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.utils.px
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class StyleSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test", true) {
                widget {
                    style {
                        margin = 2.px
                        color = Color(Col.SILVER)
                        overflow = Overflow.SCROLL
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>.kv_styleclass_0 {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}</style><div class=\"kv_styleclass_0\"></div>",
                element?.innerHTML,
                "Should render correct style element"
            )
            Style.styles.clear()
        }
    }

    @Test
    fun renderCustomClass() {
        run {
            Root("test", true) {
                widget {
                    style("customclass") {
                        margin = 2.px
                        color = Color(Col.SILVER)
                        overflow = Overflow.SCROLL
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>.customclass {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}</style><div class=\"customclass\"></div>",
                element?.innerHTML,
                "Should render correct style element with custom class name"
            )
            Style.styles.clear()
        }
    }

    @Test
    fun renderSubclass() {
        run {
            Root("test", true) {
                widget {
                    style("customclass") {
                        margin = 2.px
                        color = Color(Col.SILVER)
                        overflow = Overflow.SCROLL
                        style("image") {
                            marginTop = 10.px
                        }
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>.customclass {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}\n" +
                        ".customclass image {\n" +
                        "margin-top: 10px;\n" +
                        "}</style>" +
                        "<div class=\"customclass\"></div>",
                element?.innerHTML,
                "Should render correct child style class name"
            )
            Style.styles.clear()
        }
    }
}