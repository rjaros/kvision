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
package test.pl.treksoft.kvision.state

import pl.treksoft.kvision.html.div
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.state.observableListOf
import pl.treksoft.kvision.state.stateBinding
import pl.treksoft.kvision.state.stateUpdate
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class StateBindingSpec : DomSpec {

    @Test
    fun bind() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val container = SimplePanel()
            val observableList = observableListOf(1, 2, 3)
            container.bind(observableList) { state ->
                setAttribute("data-count", "${state.size}")
                state.forEach {
                    div("$it")
                }
            }
            root.add(container)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div data-count=\"3\"><div>1</div><div>2</div><div>3</div></div>",
                element?.innerHTML,
                "Should render initial state of the widget"
            )
            observableList.add(4)
            assertEqualsHtml(
                "<div data-count=\"4\"><div>1</div><div>2</div><div>3</div><div>4</div></div>",
                element?.innerHTML,
                "Should render changed state of the widget"
            )
        }
    }

    @Suppress("DEPRECATION")
    @Test
    fun stateBinding() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val container = SimplePanel()
            val observableList = observableListOf(1, 2, 3)
            container.stateBinding(observableList) { state ->
                state.forEach {
                    div("$it")
                }
            }
            root.add(container)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><div></div><div>1</div><div>2</div><div>3</div></div>",
                element?.innerHTML,
                "Should render initial state of the container"
            )
            observableList.add(4)
            assertEqualsHtml(
                "<div><div></div><div>1</div><div>2</div><div>3</div><div>4</div></div>",
                element?.innerHTML,
                "Should render changed state of the container"
            )
        }
    }

    @Suppress("DEPRECATION")
    @Test
    fun stateUpdate() {
        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val container = SimplePanel()
            val observableList = observableListOf(1)
            container.stateUpdate(observableList) { state ->
                div("${state[0]}")
            } updateWith { state, d ->
                d.content = "${state[0]}"
            }
            root.add(container)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div><div></div><div>1</div></div>",
                element?.innerHTML,
                "Should render initial state of the container"
            )
            observableList[0] = 2
            assertEqualsHtml(
                "<div><div></div><div>2</div></div>",
                element?.innerHTML,
                "Should render changed state of the container"
            )
        }
    }

}
