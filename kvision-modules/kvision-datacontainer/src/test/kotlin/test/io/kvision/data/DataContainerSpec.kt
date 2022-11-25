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
@file:Suppress("DEPRECATION")

package test.io.kvision.data

import io.kvision.data.BaseDataComponent
import io.kvision.data.DataContainer
import io.kvision.html.Span
import io.kvision.panel.Root
import io.kvision.panel.VPanel
import io.kvision.state.observableListOf
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class DataContainerSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)

            class Model(value: String) : BaseDataComponent() {
                var value: String by obs(value)
            }

            val model = observableListOf(Model("First"), Model("Second"))
            val container = DataContainer(model, { m, _, _ -> Span(m.value) }, VPanel())
            root.add(container)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div style=\"display: flex; flex-direction: column;\"><span>First</span><span>Second</span></div>",
                element?.innerHTML,
                "Should render correct data container"
            )
            model.add(Model("Third"))
            assertEqualsHtml(
                "<div style=\"display: flex; flex-direction: column;\"><span>First</span><span>Second</span><span>Third</span></div>",
                element?.innerHTML,
                "Should render correct data container after model change"
            )
            model[1].value = "Changed"
            assertEqualsHtml(
                "<div style=\"display: flex; flex-direction: column;\"><span>First</span><span>Changed</span><span>Third</span></div>",
                element?.innerHTML,
                "Should render correct data container after model element change"
            )
        }
    }

}
