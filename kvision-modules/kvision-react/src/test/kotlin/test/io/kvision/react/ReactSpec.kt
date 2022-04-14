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
package test.io.kvision.react

import io.kvision.panel.ContainerType
import io.kvision.panel.Root
import io.kvision.react.react
import io.kvision.test.DomSpec
import kotlinx.browser.document
import org.w3c.dom.HTMLInputElement
import react.RefCallback
import react.dom.html.ReactHTML.input
import react.useRefCallback
import kotlin.test.Test

class ReactSpec : DomSpec {

    @Test
    fun react() {
        runAsync { resolve, _ ->
            val root = Root("test", containerType = ContainerType.FIXED)
            var callback: RefCallback<HTMLInputElement>? = null
            val react = root.react("initial") { getState, changeState ->
                input {
                    value = getState()
                    onChange = { e ->
                        val target = e.target
                        changeState {
                            target.value
                        }
                    }
                    ref = callback
                }
            }
            callback = useRefCallback {
                val element = document.getElementById("test")
                assertEqualsHtml(
                    "<div><input value=\"initial\"></div>",
                    element?.innerHTML,
                    "Should render React input component"
                )
                react.state = "changed"
                assertEqualsHtml(
                    "<div><input value=\"changed\"></div>",
                    element?.innerHTML,
                    "Should render React input component with changed state"
                )
                resolve()
            }
        }
    }
}
