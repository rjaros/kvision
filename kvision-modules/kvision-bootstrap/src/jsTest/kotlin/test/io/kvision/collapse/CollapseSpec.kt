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
package test.io.kvision.collapse

import io.kvision.collapse.collapse
import io.kvision.collapse.forCollapse
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.toolbar.buttonGroup
import kotlinx.browser.document
import kotlin.test.Test

class CollapseSpec : DomSpec {

    @Test
    fun render() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                val id1 = "collapseExample"
                val id2 = "collapseExample2"
                val group = "collapseGroup"
                buttonGroup {
                    button("Toggle #1") {
                        forCollapse(id1)
                    }
                    button("Toggle #2") {
                        forCollapse(id2)
                    }
                    button("Toggle both") {
                        forCollapse(group, id1, id2)
                    }
                }
                div(className = "row") {
                    div(className = "col") {
                        collapse(id1, group, opened = true) {
                            +"Hello Collapse 1"
                        }
                    }
                    div(className = "col") {
                        collapse(id2, group) {
                            +"Hello Collapse 2"
                        }
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml(
                """<div class="btn-group" role="group"><button class="btn btn-primary" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-controls="collapseExample" aria-expanded="false" type="button">Toggle #1</button><button class="btn btn-primary" data-bs-toggle="collapse" data-bs-target="#collapseExample2" aria-controls="collapseExample2" aria-expanded="false" type="button">Toggle #2</button><button class="btn btn-primary" data-bs-toggle="collapse" data-bs-target=".collapseGroup" aria-controls="collapseExample collapseExample2" aria-expanded="false" type="button">Toggle both</button></div><div class="row"><div class="col"><div class="collapse collapseGroup show" id="collapseExample">Hello Collapse 1</div></div><div class="col"><div class="collapse collapseGroup" id="collapseExample2">Hello Collapse 2</div></div></div>""",
                element?.innerHTML,
                "Should render correct collapse components"
            )
        }
    }
}
