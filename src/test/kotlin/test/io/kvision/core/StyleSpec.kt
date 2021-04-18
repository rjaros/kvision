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
package test.io.kvision.core

import io.kvision.core.Col
import io.kvision.core.Color
import io.kvision.core.Overflow
import io.kvision.core.PClass
import io.kvision.core.PElement
import io.kvision.core.style
import io.kvision.core.widget
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.utils.px
import kotlinx.browser.document
import kotlin.test.Test

class StyleSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                widget {
                    style {
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                        overflow = Overflow.SCROLL
                        setStyle("box-shadow", "10px 10px")
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>.kv_styleclass_0 {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\nbox-shadow: 10px 10px;\n}</style><div class=\"kv_styleclass_0\"></div>",
                element?.innerHTML,
                "Should render correct style element"
            )
        }
    }

    @Test
    fun renderCustomClass() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                widget {
                    style("customclass") {
                        margin = 2.px
                        color = Color.name(Col.SILVER)
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
        }
    }

    @Test
    fun renderSubclass() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                widget {
                    style("customclass") {
                        margin = 2.px
                        color = Color.name(Col.SILVER)
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
        }
    }

    @Test
    fun renderPseudoClass() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                widget {
                    style("customclass", PClass.HOVER) {
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                        overflow = Overflow.SCROLL
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>.customclass:hover {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}</style><div class=\"customclass\"></div>",
                element?.innerHTML,
                "Should render correct style element with a pseudo class name"
            )
        }
    }

    @Test
    fun renderPseudoElement() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                widget {
                    style("customclass", pElement = PElement.FIRSTLETTER) {
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                        overflow = Overflow.SCROLL
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>.customclass::first-letter {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}</style><div class=\"customclass\"></div>",
                element?.innerHTML,
                "Should render correct style element with a pseudo element name"
            )
        }
    }
}
