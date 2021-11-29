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

package io.kvision.chart.js

external interface LayoutItem {
    var position: dynamic /* "left" | "top" | "right" | "bottom" | "center" | "chartArea" | `T$49` */
        get() = definedExternally
        set(value) = definedExternally
    var weight: Number
    var fullSize: Boolean
    var width: Number
    var height: Number
    var left: Number
    var top: Number
    var right: Number
    var bottom: Number
    val beforeLayout: (() -> Unit)?
    fun draw(chartArea: ChartArea)
    val getPadding: (() -> ChartArea)?
    fun isHorizontal(): Boolean
    fun update(width: Number, height: Number, margins: ChartArea = definedExternally)
}
