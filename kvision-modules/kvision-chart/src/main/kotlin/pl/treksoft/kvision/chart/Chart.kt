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
import pl.treksoft.kvision.chart.js.Chart.PluginServiceGlobalRegistration
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.set
import pl.treksoft.kvision.chart.js.Chart as JsChart

/**
 * Chart component.
 *
 * @constructor
 * @param configuration chart configuration
 * @param chartWidth chart width in pixels
 * @param chartHeight chart height in pixels
 * @param classes a set of CSS class names
 */
open class Chart(
    configuration: Configuration,
    chartWidth: Int? = null,
    chartHeight: Int? = null,
    classes: Set<String> = setOf()
) : Widget(classes) {

    /**
     * Chart configuration.
     */
    var configuration
        get() = chartCanvas.configuration
        set(value) {
            chartCanvas.configuration = value
        }

    internal val chartCanvas: ChartCanvas = ChartCanvas(chartWidth, chartHeight, configuration)

    override fun render(): VNode {
        return render("div", arrayOf(chartCanvas.renderVNode()))
    }

    /**
     * Returns chart configuration in the form of native JS object.
     */
    open fun getNativeConfig(): ChartConfiguration? {
        return chartCanvas.getNativeConfig()
    }

    /**
     * Resets the chart.
     */
    open fun reset() {
        chartCanvas.reset()
    }

    /**
     * Renders the chart.
     * @param duration animation duration
     * @param lazy if true, the animation can be interrupted by other animations
     */
    open fun render(duration: Int? = null, lazy: Boolean = false) {
        chartCanvas.render(duration, lazy)
    }

    /**
     * Stops the animation.
     */
    open fun stop() {
        chartCanvas.stop()
    }

    /**
     * Resizes the chart to the size of the container.
     */
    open fun resizeChart() {
        chartCanvas.resizeChart()
    }

    /**
     * Clears the chart.
     */
    open fun clearChart() {
        chartCanvas.clearChart()
    }

    /**
     * Returns a base64 encoded string of the chart in its current state.
     */
    open fun toBase64Image(): String? {
        return chartCanvas.toBase64Image()
    }

    companion object {

        fun registerPlugin(plugin: dynamic) {
            @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
            JsChart.plugins.register(plugin as PluginServiceGlobalRegistration)
        }

        fun unregisterPlugin(plugin: dynamic) {
            @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
            JsChart.plugins.unregister(plugin as PluginServiceGlobalRegistration)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.chart(
    configuration: Configuration,
    chartWidth: Int? = null,
    chartHeight: Int? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Chart.() -> Unit)? = null
): Chart {
    val chart = Chart(configuration, chartWidth, chartHeight, classes ?: className.set).apply { init?.invoke(this) }
    this.add(chart)
    return chart
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.chart(
    state: ObservableState<S>,
    configuration: Configuration,
    chartWidth: Int? = null,
    chartHeight: Int? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Chart.(S) -> Unit)
) = chart(configuration, chartWidth, chartHeight, classes, className).bind(state, true, init)
