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
import io.kvision.core.Style
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
                        overflow = Overflow.SCROLL
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                        setStyle("box-shadow", "10px 10px")
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>\n.kv_styleclass_0 {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\nbox-shadow: 10px 10px;\n}\n</style><div class=\"kv_styleclass_0\"></div>",
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
                    style(".customclass") {
                        overflow = Overflow.SCROLL
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>\n.customclass {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}\n</style><div class=\"customclass\"></div>",
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
                    style(".customclass") {
                        overflow = Overflow.SCROLL
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                        style("image") {
                            marginTop = 10.px
                        }
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>\n.customclass {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}\n" +
                        ".customclass image {\n" +
                        "margin-top: 10px;\n" +
                        "}\n</style>" +
                        "<div class=\"customclass\"></div>",
                element?.innerHTML,
                "Should render correct child style class name"
            )
        }
    }

    @Test
    fun renderSubstyle() {
        run {
            lateinit var substyle: Style
            style(".a") {
                padding = 2.px
                substyle = style(".b") {
                    padding = 3.px
                }
            }
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                widget {
                    addCssStyle(substyle)
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>\n.a {\npadding: 2px;\n}\n" +
                        ".a .b {\n" +
                        "padding: 3px;\n" +
                        "}\n</style>" +
                        "<div class=\"b\"></div>",
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
                    style(".customclass", PClass.HOVER) {
                        overflow = Overflow.SCROLL
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>\n.customclass:hover {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}\n</style><div class=\"customclass\"></div>",
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
                    style(".customclass", pElement = PElement.FIRSTLETTER) {
                        overflow = Overflow.SCROLL
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>\n.customclass::first-letter {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\n}\n</style><div class=\"customclass\"></div>",
                element?.innerHTML,
                "Should render correct style element with a pseudo element name"
            )
        }
    }

    @Test
    fun renderMediaQuery() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                widget {
                    style {
                        mediaQuery = "min-width: 700px"
                        overflow = Overflow.SCROLL
                        margin = 2.px
                        color = Color.name(Col.SILVER)
                        setStyle("box-shadow", "10px 10px")
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<style>\n@media (min-width: 700px) {\n.kv_styleclass_1 {\noverflow: scroll;\nmargin: 2px;\ncolor: silver;\nbox-shadow: 10px 10px;\n}\n}\n</style><div class=\"kv_styleclass_1\"></div>",
                element?.innerHTML,
                "Should render correct style element within a media query"
            )
        }
    }
}
