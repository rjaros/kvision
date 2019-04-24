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
package pl.treksoft.kvision.chart

import com.github.snabbdom.VNode
import pl.treksoft.kvision.chart.js.Chart.ChartConfiguration
import pl.treksoft.kvision.html.Canvas
import pl.treksoft.kvision.i18n.I18n
import pl.treksoft.kvision.chart.js.Chart as JsChart

internal class ChartCanvas(
    canvasWidth: Int? = null,
    canvasHeight: Int? = null,
    configuration: Configuration,
    classes: Set<String> = setOf()
) :
    Canvas(canvasWidth, canvasHeight, classes) {

    var configuration: Configuration = configuration
        set(value) {
            field = value
            jsChart?.config = value.toJs(this::translate)
            jsChart?.update()
        }

    private var jsChart: JsChart? = null

    override fun render(): VNode {
        if (lastLanguage != null && lastLanguage != I18n.language) {
            jsChart?.config = configuration.toJs(this::translate)
            jsChart?.update()
        }
        return render("canvas")
    }

    override fun afterInsert(node: VNode) {
        jsChart =
            JsChart(this.context2D, configuration.toJs(this::translate))
    }

    override fun afterDestroy() {
        jsChart?.destroy()
        jsChart = null
    }

    fun getNativeConfig(): ChartConfiguration? {
        return jsChart?.config
    }

    fun reset() {
        jsChart?.reset()
    }

    fun render(duration: Int? = null, lazy: Boolean = false) {
        jsChart?.render(duration, lazy)
    }

    fun stop() {
        jsChart?.stop()
    }

    fun resizeChart() {
        jsChart?.resize()
    }

    fun clearChart() {
        jsChart?.clear()
    }

    fun toBase64Image(): String? {
        return jsChart?.toBase64Image()
    }

}
