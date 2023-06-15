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

package io.kvision.tabulator

import io.kvision.core.Border
import io.kvision.core.BorderStyle
import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.html.TAG
import io.kvision.html.div
import io.kvision.html.tag
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableValue
import io.kvision.state.bind

/**
 * Pagination state.
 *
 * @constructor
 * @param currentPage current page number
 * @param maxPages maximum number of pages
 * @param buttonCount number of page buttons
 */
data class PaginationState(val currentPage: Int, val maxPages: Int, val buttonCount: Int = 5)

/**
 * External tabulator pagination component.
 *
 * @constructor
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class TabulatorPagination<T : Any>(
    className: String? = null, init: (TabulatorPagination<T>.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "tabulator") {

    internal var tabulator: Tabulator<T>? = null
    internal val paginationState = ObservableValue(PaginationState(1, 0))

    init {
        div(className = "tabulator-footer") {
            borderTop = Border(style = BorderStyle.NONE)
            bind(paginationState) { state ->
                tag(TAG.BUTTON, "⯇⯇", className = "tabulator-page") {
                    role = "button"
                    setAttribute("type", "button")
                    setAttribute("aria-label", "<<")
                    title = "<<"
                    if (state.currentPage == 1) {
                        setAttribute("disabled", "disabled")
                    }
                    onClick {
                        tabulator?.setPage(1)
                    }
                }
                tag(TAG.BUTTON, "⯇", className = "tabulator-page") {
                    role = "button"
                    setAttribute("type", "button")
                    setAttribute("aria-label", "<")
                    title = "<"
                    if (state.currentPage == 1) {
                        setAttribute("disabled", "disabled")
                    }
                    onClick {
                        tabulator?.previousPage()
                    }
                }
                var firstButtonNumber = maxOf(state.currentPage - state.buttonCount + 1, 1)
                var lastButtonNumber = minOf(state.currentPage + state.buttonCount - 1, state.maxPages)
                while (lastButtonNumber - firstButtonNumber >= state.buttonCount) {
                    if (state.currentPage - firstButtonNumber >= lastButtonNumber - state.currentPage) {
                        firstButtonNumber++
                    } else {
                        lastButtonNumber--
                    }
                }
                for (pageNumber in firstButtonNumber..lastButtonNumber) {
                    val buttonClassName = if (pageNumber == state.currentPage) "tabulator-page active" else "tabulator-page"
                    tag(TAG.BUTTON, "$pageNumber", className = buttonClassName) {
                        role = "button"
                        setAttribute("type", "button")
                        setAttribute("aria-label", "# $pageNumber")
                        title = "# $pageNumber"
                        onClick {
                            tabulator?.setPage(pageNumber)
                        }
                    }
                }
                tag(TAG.BUTTON, "⯈", className = "tabulator-page") {
                    role = "button"
                    setAttribute("type", "button")
                    setAttribute("aria-label", ">")
                    title = ">"
                    if (state.currentPage >= state.maxPages) {
                        setAttribute("disabled", "disabled")
                    }
                    onClick {
                        tabulator?.nextPage()
                    }
                }
                tag(TAG.BUTTON, "⯈⯈", className = "tabulator-page") {
                    role = "button"
                    setAttribute("type", "button")
                    setAttribute("aria-label", ">>")
                    title = ">>"
                    if (state.currentPage >= state.maxPages) {
                        setAttribute("disabled", "disabled")
                    }
                    onClick {
                        tabulator?.setPage(state.maxPages)
                    }
                }
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T : Any> Container.tabulatorPagination(
    className: String? = null,
    init: (TabulatorPagination<T>.() -> Unit)? = null
): TabulatorPagination<T> {
    val tabulatorPagination = TabulatorPagination(className, init)
    this.add(tabulatorPagination)
    return tabulatorPagination
}
