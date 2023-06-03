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

import io.kvision.core.Border
import io.kvision.core.BorderStyle
import io.kvision.core.Col
import io.kvision.core.Color
import io.kvision.core.inheritingStyle
import io.kvision.core.style
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.utils.px
import kotlinx.browser.document
import kotlin.test.Test

class InheritingStyleSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                val cardStyle = style(".card") {
                    margin = 2.px
                    padding = 4.px
                    border = Border(1.px, BorderStyle.SOLID, Color.name(Col.BLACK))
                }
                inheritingStyle(".redcard", parentStyle = cardStyle) {
                    border = Border(1.px, BorderStyle.SOLID, Color.name(Col.RED))
                }
                inheritingStyle(".bluecard", parentStyle = cardStyle) {
                    border = Border(1.px, BorderStyle.SOLID, Color.name(Col.BLUE))
                    paddingTop = 5.px
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                """<style>
.card {
margin: 2px;
padding: 4px;
border: 1px solid black;
}
.redcard {
margin-bottom: 2px;
margin-top: 2px;
margin-left: 2px;
margin-right: 2px;
padding-bottom: 4px;
padding-top: 4px;
padding-left: 4px;
padding-right: 4px;
border: 1px solid red;
}
.bluecard {
margin-bottom: 2px;
margin-top: 2px;
margin-left: 2px;
margin-right: 2px;
padding-bottom: 4px;
padding-top: 5px;
padding-left: 4px;
padding-right: 4px;
border: 1px solid blue;
}
</style>""",
                element?.innerHTML,
                "Should render correct inherited styles"
            )
        }
    }
}
