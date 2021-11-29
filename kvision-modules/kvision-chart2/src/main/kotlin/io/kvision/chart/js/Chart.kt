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
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
    "unused", "PropertyName", "TooManyFunctions", "VariableNaming", "MaxLineLength"
)

package io.kvision.chart.js

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import io.kvision.chart.js.Chart.*
import kotlin.js.Json

typealias InteractionMode = Any

typealias ChartTooltipPositioner = (elements: Array<Any>, eventPosition: Point) -> Point

external interface Defaults {
    var global: ChartOptions /* Chart.ChartOptions & Chart.ChartFontOptions */
}

@Suppress("NOTHING_TO_INLINE", "UnsafeCastFromDynamic")
inline operator fun Defaults.get(key: String): Any? = asDynamic()[key]

@Suppress("NOTHING_TO_INLINE")
inline operator fun Defaults.set(key: String, value: Any) {
    asDynamic()[key] = value
}

open external class Chart {
    constructor(context: String, options: ChartConfiguration)
    constructor(context: CanvasRenderingContext2D, options: ChartConfiguration)
    constructor(context: HTMLCanvasElement, options: ChartConfiguration)
    constructor(context: Array<dynamic /* CanvasRenderingContext2D | HTMLCanvasElement */>, options: ChartConfiguration)

    open var config: ChartConfiguration
    open var data: ChartData
    open var options: ChartOptions
    open fun destroy(): Any = definedExternally
    open fun update(duration: Any? = definedExternally, lazy: Any? = definedExternally): Any = definedExternally
    open fun render(duration: Any? = definedExternally, lazy: Any? = definedExternally): Any = definedExternally
    open fun stop(): Any = definedExternally
    open fun resize(): Any = definedExternally
    open fun clear(): Any = definedExternally
    open fun reset(): Any = definedExternally
    open fun toBase64Image(): String = definedExternally
    open fun generateLegend(): Any = definedExternally
    open fun getElementAtEvent(e: Any): Any = definedExternally
    open fun getElementsAtEvent(e: Any): Array<Any> = definedExternally
    open fun getElementsAtXAxis(e: Any): Array<Any> = definedExternally
    open fun getDatasetAtEvent(e: Any): Array<Any> = definedExternally
    open fun getDatasetMeta(index: Number): Meta = definedExternally
    open fun getVisibleDatasetCount(): Number = definedExternally
    open fun isDatasetVisible(datasetIndex: Number): Boolean = definedExternally
    open fun setDatasetVisibility(datasetIndex: Number, visible: Boolean): Unit = definedExternally
    open var ctx: CanvasRenderingContext2D?
    open var canvas: HTMLCanvasElement?
    open var width: Number?
    open var height: Number?
    open var aspectRatio: Number?
    open var chartArea: ChartArea

    companion object {
        var Chart: Any? = definedExternally
        var pluginService: PluginServiceStatic = definedExternally
        var plugins: PluginServiceStatic = definedExternally
        var defaults: Defaults = definedExternally
        var controllers: Json = definedExternally
        var helpers: Json = definedExternally
        var Tooltip: ChartTooltipsStaticConfiguration = definedExternally
    }


    interface InteractionModeRegistry {
        operator fun get(key: String): String /* 'x-axis' */
        operator fun set(key: String, value: String /* 'x-axis' */)
        var point: String /* 'point' */
        var nearest: String /* 'nearest' */
        var single: String /* 'single' */
        var label: String /* 'label' */
        var index: String /* 'index' */
        var dataset: String /* 'dataset' */
        var x: String /* 'x' */
        var y: String /* 'y' */
    }

    interface ChartArea {
        var top: Number
        var right: Number
        var bottom: Number
        var left: Number
    }

    interface ChartLegendItem {
        var text: String?
            get() = definedExternally
            set(value) = definedExternally
        var fillStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var hidden: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var index: Number?
            get() = definedExternally
            set(value) = definedExternally
        var lineCap: String /* 'butt' | 'round' | 'square' */
        var lineDash: Array<Number>?
            get() = definedExternally
            set(value) = definedExternally
        var lineDashOffset: Number?
            get() = definedExternally
            set(value) = definedExternally
        var lineJoin: String /* 'bevel' | 'round' | 'miter' */
        var lineWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var strokeStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var pointStyle: String /* 'circle' | 'cross' | 'crossRot' | 'dash' | 'line' | 'rect' | 'rectRounded' | 'rectRot' | 'star' | 'triangle' */
    }

    interface ChartLegendLabelItem : ChartLegendItem {
        var datasetIndex: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartTooltipItem {
        var label: String?
            get() = definedExternally
            set(value) = definedExternally
        var value: String?
            get() = definedExternally
            set(value) = definedExternally
        var xLabel: dynamic /* String | Number */
            get() = definedExternally
            set(value) = definedExternally
        var yLabel: dynamic /* String | Number */
            get() = definedExternally
            set(value) = definedExternally
        var datasetIndex: Number?
            get() = definedExternally
            set(value) = definedExternally
        var index: Number?
            get() = definedExternally
            set(value) = definedExternally
        var x: Number?
            get() = definedExternally
            set(value) = definedExternally
        var y: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartTooltipLabelColor {
        var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartTooltipCallback {
        val beforeTitle: ((item: Array<ChartTooltipItem>, data: ChartData) -> dynamic)?
            get() = definedExternally
        val title: ((item: Array<ChartTooltipItem>, data: ChartData) -> dynamic)?
            get() = definedExternally
        val afterTitle: ((item: Array<ChartTooltipItem>, data: ChartData) -> dynamic)?
            get() = definedExternally
        val beforeBody: ((item: Array<ChartTooltipItem>, data: ChartData) -> dynamic)?
            get() = definedExternally
        val beforeLabel: ((tooltipItem: ChartTooltipItem, data: ChartData) -> dynamic)?
            get() = definedExternally
        val label: ((tooltipItem: ChartTooltipItem, data: ChartData) -> dynamic)?
            get() = definedExternally
        val labelColor: ((tooltipItem: ChartTooltipItem, chart: Chart) -> ChartTooltipLabelColor)?
            get() = definedExternally
        val labelTextColor: ((tooltipItem: ChartTooltipItem, chart: Chart) -> String)?
            get() = definedExternally
        val afterLabel: ((tooltipItem: ChartTooltipItem, data: ChartData) -> dynamic)?
            get() = definedExternally
        val afterBody: ((item: Array<ChartTooltipItem>, data: ChartData) -> dynamic)?
            get() = definedExternally
        val beforeFooter: ((item: Array<ChartTooltipItem>, data: ChartData) -> dynamic)?
            get() = definedExternally
        val footer: ((item: Array<ChartTooltipItem>, data: ChartData) -> dynamic)?
            get() = definedExternally
        val afterFooter: ((item: Array<ChartTooltipItem>, data: ChartData) -> dynamic)?
            get() = definedExternally
    }

    interface ChartAnimationParameter {
        var chartInstance: Any?
            get() = definedExternally
            set(value) = definedExternally
        var animationObject: Any?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartPoint {
        var x: dynamic /* Number | String | Date | Moment */
            get() = definedExternally
            set(value) = definedExternally
        var y: dynamic /* Number | String | Date | Moment */
            get() = definedExternally
            set(value) = definedExternally
        var r: Number?
            get() = definedExternally
            set(value) = definedExternally
        var t: dynamic /* Number | String | Date | Moment */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartConfiguration {
        var type: String /* 'line' | 'bar' | 'horizontalBar' | 'radar' | 'doughnut' | 'polarArea' | 'bubble' | 'pie' | 'scatter' | String */
        var data: ChartData?
            get() = definedExternally
            set(value) = definedExternally
        var options: ChartOptions?
            get() = definedExternally
            set(value) = definedExternally
        var plugins: Array<PluginServiceRegistrationOptions>?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartData {
        var labels: Array<dynamic /* String | Array<String> | Number | Array<Number> | Date | Array<Date> | Moment | Array<Moment> */>?
            get() = definedExternally
            set(value) = definedExternally
        var datasets: Array<ChartDataSets>?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface RadialChartOptions : ChartOptions {
        override var scale: RadialLinearScale?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartSize {
        var height: Number
        var width: Number
    }

    interface ChartOptions {
        var responsive: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var responsiveAnimationDuration: Number?
            get() = definedExternally
            set(value) = definedExternally
        var aspectRatio: Number?
            get() = definedExternally
            set(value) = definedExternally
        var maintainAspectRatio: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var events: Array<String>?
            get() = definedExternally
            set(value) = definedExternally
        val legendCallback: ((chart: Chart) -> String)?
            get() = definedExternally
        val onHover: ((self: Chart, event: MouseEvent, activeElements: Array<Any>) -> Any)?
            get() = definedExternally
        val onClick: ((event: MouseEvent, activeElements: Array<Any>) -> Any)?
            get() = definedExternally
        val onResize: ((self: Chart, newSize: ChartSize) -> Unit)?
            get() = definedExternally
        var title: ChartTitleOptions?
            get() = definedExternally
            set(value) = definedExternally
        var legend: ChartLegendOptions?
            get() = definedExternally
            set(value) = definedExternally
        var tooltips: ChartTooltipOptions?
            get() = definedExternally
            set(value) = definedExternally
        var hover: ChartHoverOptions?
            get() = definedExternally
            set(value) = definedExternally
        var animation: ChartAnimationOptions?
            get() = definedExternally
            set(value) = definedExternally
        var elements: ChartElementsOptions?
            get() = definedExternally
            set(value) = definedExternally
        var layout: ChartLayoutOptions?
            get() = definedExternally
            set(value) = definedExternally
        var scale: RadialLinearScale?
            get() = definedExternally
            set(value) = definedExternally
        var scales: dynamic /* ChartScales | LinearScale | LogarithmicScale | TimeScale */
            get() = definedExternally
            set(value) = definedExternally
        var showLines: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var spanGaps: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var cutoutPercentage: Number?
            get() = definedExternally
            set(value) = definedExternally
        var circumference: Number?
            get() = definedExternally
            set(value) = definedExternally
        var rotation: Number?
            get() = definedExternally
            set(value) = definedExternally
        var devicePixelRatio: Number?
            get() = definedExternally
            set(value) = definedExternally
        var plugins: ChartPluginsOptions?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartFontOptions {
        var defaultFontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var defaultFontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var defaultFontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var defaultFontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartTitleOptions {
        var display: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var position: String /* 'left' | 'right' | 'top' | 'bottom' | 'chartArea' */
        var fullWidth: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var fontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var fontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var fontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var fontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var padding: Number?
            get() = definedExternally
            set(value) = definedExternally
        var text: dynamic /* String | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartLegendOptions {
        var align: String /* 'center' | 'end' | 'start' */
        var display: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var position: String /* 'left' | 'right' | 'top' | 'bottom' | 'chartArea' */
        var fullWidth: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        val onClick: ((event: MouseEvent, legendItem: ChartLegendLabelItem) -> Unit)?
            get() = definedExternally
        val onHover: ((event: MouseEvent, legendItem: ChartLegendLabelItem) -> Unit)?
            get() = definedExternally
        val onLeave: ((event: MouseEvent, legendItem: ChartLegendLabelItem) -> Unit)?
            get() = definedExternally
        var labels: ChartLegendLabelOptions?
            get() = definedExternally
            set(value) = definedExternally
        var reverse: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartLegendLabelOptions {
        var boxWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var fontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var fontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var fontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var fontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var padding: Number?
            get() = definedExternally
            set(value) = definedExternally
        val generateLabels: ((chart: Chart) -> Array<ChartLegendLabelItem>)?
            get() = definedExternally
        val filter: ((legendItem: ChartLegendLabelItem, data: ChartData) -> Any)?
            get() = definedExternally
        var usePointStyle: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartTooltipOptions {
        var enabled: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var custom: ((tooltipModel: ChartTooltipModel) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var mode: InteractionMode?
            get() = definedExternally
            set(value) = definedExternally
        var intersect: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var titleAlign: String /* 'left' | 'center' | 'right' */
        var titleFontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var titleFontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var titleFontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var titleFontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var titleSpacing: Number?
            get() = definedExternally
            set(value) = definedExternally
        var titleMarginBottom: Number?
            get() = definedExternally
            set(value) = definedExternally
        var bodyAlign: String /* 'left' | 'center' | 'right' */
        var bodyFontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var bodyFontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var bodyFontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var bodyFontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var bodySpacing: Number?
            get() = definedExternally
            set(value) = definedExternally
        var footerAlign: String /* 'left' | 'center' | 'right' */
        var footerFontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var footerFontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var footerFontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var footerFontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var footerSpacing: Number?
            get() = definedExternally
            set(value) = definedExternally
        var footerMarginTop: Number?
            get() = definedExternally
            set(value) = definedExternally
        var xPadding: Number?
            get() = definedExternally
            set(value) = definedExternally
        var yPadding: Number?
            get() = definedExternally
            set(value) = definedExternally
        var caretSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var cornerRadius: Number?
            get() = definedExternally
            set(value) = definedExternally
        var multiKeyBackground: String?
            get() = definedExternally
            set(value) = definedExternally
        var callbacks: ChartTooltipCallback?
            get() = definedExternally
            set(value) = definedExternally
        val filter: ((item: ChartTooltipItem, data: ChartData) -> Boolean)?
            get() = definedExternally
        val itemSort: ((itemA: ChartTooltipItem, itemB: ChartTooltipItem, data: ChartData) -> Number)?
            get() = definedExternally
        var position: String?
            get() = definedExternally
            set(value) = definedExternally
        var caretPadding: Number?
            get() = definedExternally
            set(value) = definedExternally
        var displayColors: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartTooltipModel {
        var afterBody: Array<String>
        var backgroundColor: String
        var beforeBody: Array<String>
        var body: Array<ChartTooltipModelBody>
        var bodyFontColor: String
        var bodyFontSize: Number
        var bodySpacing: Number
        var borderColor: String
        var borderWidth: Number
        var caretPadding: Number
        var caretSize: Number
        var caretX: Number
        var caretY: Number
        var cornerRadius: Number
        var dataPoints: Array<ChartTooltipItem>
        var displayColors: Boolean
        var footer: Array<String>
        var footerFontColor: String
        var footerFontSize: Number
        var footerMarginTop: Number
        var footerSpacing: Number
        var height: Number
        var labelColors: Array<String>
        var labelTextColors: Array<String>
        var legendColorBackground: String
        var opacity: Number
        var title: Array<String>
        var titleFontColor: String
        var titleFontSize: Number
        var titleMarginBottom: Number
        var titleSpacing: Number
        var width: Number
        var x: Number
        var xAlign: String
        var xPadding: Number
        var y: Number
        var yAlign: String
        var yPadding: Number
        var _bodyAlign: String
        var _bodyFontFamily: String
        var _bodyFontStyle: String
        var _footerAlign: String
        var _footerFontFamily: String
        var _footerFontStyle: String
        var _titleAlign: String
        var _titleFontFamily: String
        var _titleFontStyle: String
    }

    interface ChartTooltipModelBody {
        var before: Array<String>
        var lines: Array<String>
        var after: Array<String>
    }

    interface ChartPluginsOptions

    interface Positioners

    interface ChartTooltipsStaticConfiguration {
        var positioners: Positioners
    }

    interface ChartHoverOptions {
        var mode: InteractionMode?
            get() = definedExternally
            set(value) = definedExternally
        var animationDuration: Number?
            get() = definedExternally
            set(value) = definedExternally
        var intersect: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var axis: String /* 'x' | 'y' | 'xy' */
        val onHover: ((self: Chart, event: MouseEvent, activeElements: Array<Any>) -> Any)?
            get() = definedExternally
    }

    interface ChartAnimationObject {
        var currentStep: Number?
            get() = definedExternally
            set(value) = definedExternally
        var numSteps: Number?
            get() = definedExternally
            set(value) = definedExternally
        var easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */
        val render: ((arg: Any) -> Unit)?
            get() = definedExternally
        val onAnimationProgress: ((arg: Any) -> Unit)?
            get() = definedExternally
        val onAnimationComplete: ((arg: Any) -> Unit)?
            get() = definedExternally
    }

    interface ChartAnimationOptions {
        var duration: Number?
            get() = definedExternally
            set(value) = definedExternally
        var easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */
        val onProgress: ((chart: Any) -> Unit)?
            get() = definedExternally
        val onComplete: ((chart: Any) -> Unit)?
            get() = definedExternally
        var animateRotate: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var animateScale: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartElementsOptions {
        var point: ChartPointOptions?
            get() = definedExternally
            set(value) = definedExternally
        var line: ChartLineOptions?
            get() = definedExternally
            set(value) = definedExternally
        var arc: ChartArcOptions?
            get() = definedExternally
            set(value) = definedExternally
        var rectangle: ChartRectangleOptions?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartArcOptions {
        var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartLineOptions {
        var cubicInterpolationMode: String /* 'default' | 'monotone' */
        var tension: Number?
            get() = definedExternally
            set(value) = definedExternally
        var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderCapStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var borderDash: Array<Any>?
            get() = definedExternally
            set(value) = definedExternally
        var borderDashOffset: Number?
            get() = definedExternally
            set(value) = definedExternally
        var borderJoinStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var capBezierPoints: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var fill: dynamic /* 'zero' | 'top' | 'bottom' | Boolean */
            get() = definedExternally
            set(value) = definedExternally
        var stepped: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartPointOptions {
        var radius: Number?
            get() = definedExternally
            set(value) = definedExternally
        var pointStyle: String /* 'circle' | 'cross' | 'crossRot' | 'dash' | 'line' | 'rect' | 'rectRounded' | 'rectRot' | 'star' | 'triangle' */
        var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var hitRadius: Number?
            get() = definedExternally
            set(value) = definedExternally
        var hoverRadius: Number?
            get() = definedExternally
            set(value) = definedExternally
        var hoverBorderWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartRectangleOptions {
        var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderSkipped: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartLayoutOptions {
        var padding: dynamic /* ChartLayoutPaddingObject | Number */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartLayoutPaddingObject {
        var top: Number?
            get() = definedExternally
            set(value) = definedExternally
        var right: Number?
            get() = definedExternally
            set(value) = definedExternally
        var bottom: Number?
            get() = definedExternally
            set(value) = definedExternally
        var left: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface GridLineOptions {
        var display: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var circular: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var color: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderDash: Array<Number>?
            get() = definedExternally
            set(value) = definedExternally
        var borderDashOffset: Number?
            get() = definedExternally
            set(value) = definedExternally
        var lineWidth: dynamic /* Number | Array<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var drawBorder: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var drawOnChartArea: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var drawTicks: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var tickMarkLength: Number?
            get() = definedExternally
            set(value) = definedExternally
        var zeroLineWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var zeroLineColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var zeroLineBorderDash: Array<Number>?
            get() = definedExternally
            set(value) = definedExternally
        var zeroLineBorderDashOffset: Number?
            get() = definedExternally
            set(value) = definedExternally
        var offsetGridLines: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var z: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ScaleTitleOptions {
        var display: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var labelString: String?
            get() = definedExternally
            set(value) = definedExternally
        var lineHeight: dynamic /* Number | String */
            get() = definedExternally
            set(value) = definedExternally
        var fontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var fontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var fontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var fontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var padding: dynamic /* ChartLayoutPaddingObject | Number */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface TickOptions : NestedTickOptions {
        var minor: dynamic /* NestedTickOptions | Boolean */
            get() = definedExternally
            set(value) = definedExternally
        var major: dynamic /* MajorTickOptions | Boolean */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface NestedTickOptions {
        var autoSkip: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var autoSkipPadding: Number?
            get() = definedExternally
            set(value) = definedExternally
        var backdropColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var backdropPaddingX: Number?
            get() = definedExternally
            set(value) = definedExternally
        var backdropPaddingY: Number?
            get() = definedExternally
            set(value) = definedExternally
        var beginAtZero: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        val callback: ((value: dynamic /* Number | String */, index: Number, values: dynamic /* Array<Number> | Array<String> */) -> dynamic)?
            get() = definedExternally
        var display: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var fontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var fontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var fontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var fontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var labelOffset: Number?
            get() = definedExternally
            set(value) = definedExternally
        var lineHeight: Number?
            get() = definedExternally
            set(value) = definedExternally
        var max: Any?
            get() = definedExternally
            set(value) = definedExternally
        var maxRotation: Number?
            get() = definedExternally
            set(value) = definedExternally
        var maxTicksLimit: Number?
            get() = definedExternally
            set(value) = definedExternally
        var min: Any?
            get() = definedExternally
            set(value) = definedExternally
        var minRotation: Number?
            get() = definedExternally
            set(value) = definedExternally
        var mirror: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var padding: Number?
            get() = definedExternally
            set(value) = definedExternally
        var reverse: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var sampleSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var showLabelBackdrop: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var source: String /* 'auto' | 'data' | 'labels' */
        var stepSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var suggestedMax: Number?
            get() = definedExternally
            set(value) = definedExternally
        var suggestedMin: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface MajorTickOptions : NestedTickOptions {
        var enabled: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface AngleLineOptions {
        var display: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var color: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var lineWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var borderDash: Array<Number>?
            get() = definedExternally
            set(value) = definedExternally
        var borderDashOffset: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface PointLabelOptions {
        val callback: ((arg: Any) -> Any)?
            get() = definedExternally
        var fontColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var fontFamily: String?
            get() = definedExternally
            set(value) = definedExternally
        var fontSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var fontStyle: String?
            get() = definedExternally
            set(value) = definedExternally
        var lineHeight: dynamic /* Number | String */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface LinearTickOptions : TickOptions {
        override var maxTicksLimit: Number?
            get() = definedExternally
            set(value) = definedExternally
        override var stepSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var precision: Number?
            get() = definedExternally
            set(value) = definedExternally
        override var suggestedMin: Number?
            get() = definedExternally
            set(value) = definedExternally
        override var suggestedMax: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface LogarithmicTickOptions : TickOptions

    interface ChartDataSets {
        var cubicInterpolationMode: String /* 'default' | 'monotone' */
        var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> | Array<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> | Scriptable<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> */
            get() = definedExternally
            set(value) = definedExternally
        var barPercentage: Number?
            get() = definedExternally
            set(value) = definedExternally
        var barThickness: dynamic /* Number | "flex" */
            get() = definedExternally
            set(value) = definedExternally
        var borderAlign: dynamic /* 'center' | 'inner' | Array<String /* 'center' | 'inner' */> | Scriptable<String /* 'center' | 'inner' */> */
            get() = definedExternally
            set(value) = definedExternally
        var borderWidth: dynamic /* Number | Any | Array<dynamic /* Number | Any */> | Scriptable<dynamic /* Number | Any */> */
            get() = definedExternally
            set(value) = definedExternally
        var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> | Array<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> | Scriptable<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> */
            get() = definedExternally
            set(value) = definedExternally
        var borderCapStyle: String /* 'butt' | 'round' | 'square' */
        var borderDash: Array<Number>?
            get() = definedExternally
            set(value) = definedExternally
        var borderDashOffset: Number?
            get() = definedExternally
            set(value) = definedExternally
        var borderJoinStyle: String /* 'bevel' | 'round' | 'miter' */
        var borderSkipped: dynamic /* 'left' | 'right' | 'top' | 'bottom' | 'chartArea' | Array<String /* 'left' | 'right' | 'top' | 'bottom' | 'chartArea' */> | Scriptable<String /* 'left' | 'right' | 'top' | 'bottom' | 'chartArea' */> */
            get() = definedExternally
            set(value) = definedExternally
        var categoryPercentage: Number?
            get() = definedExternally
            set(value) = definedExternally
        var data: dynamic /* Array<dynamic /* Number | Nothing? | Nothing? | Array<Number> */> | Array<ChartPoint> */
            get() = definedExternally
            set(value) = definedExternally
        var fill: dynamic /* Boolean | Number | String */
            get() = definedExternally
            set(value) = definedExternally
        var hitRadius: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var hoverBackgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> | Array<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> | Scriptable<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> */
            get() = definedExternally
            set(value) = definedExternally
        var hoverBorderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> | Array<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> | Scriptable<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> */
            get() = definedExternally
            set(value) = definedExternally
        var hoverBorderWidth: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var hoverRadius: Number?
            get() = definedExternally
            set(value) = definedExternally
        var label: String?
            get() = definedExternally
            set(value) = definedExternally
        var lineTension: Number?
            get() = definedExternally
            set(value) = definedExternally
        var maxBarThickness: Number?
            get() = definedExternally
            set(value) = definedExternally
        var minBarLength: Number?
            get() = definedExternally
            set(value) = definedExternally
        var steppedLine: dynamic /* 'before' | 'after' | 'middle' | Boolean */
            get() = definedExternally
            set(value) = definedExternally
        var order: Number?
            get() = definedExternally
            set(value) = definedExternally
        var pointBorderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> | Array<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> | Scriptable<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> */
            get() = definedExternally
            set(value) = definedExternally
        var pointBackgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> | Array<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> | Scriptable<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> */
            get() = definedExternally
            set(value) = definedExternally
        var pointBorderWidth: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var pointRadius: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var pointRotation: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var pointHoverRadius: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var pointHitRadius: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var pointHoverBackgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> | Array<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> | Scriptable<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> */
            get() = definedExternally
            set(value) = definedExternally
        var pointHoverBorderColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> | Array<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> | Scriptable<dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */> */
            get() = definedExternally
            set(value) = definedExternally
        var pointHoverBorderWidth: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var pointStyle: dynamic /* 'circle' | 'cross' | 'crossRot' | 'dash' | 'line' | 'rect' | 'rectRounded' | 'rectRot' | 'star' | 'triangle' | HTMLImageElement | HTMLCanvasElement | Array<dynamic /* 'circle' | 'cross' | 'crossRot' | 'dash' | 'line' | 'rect' | 'rectRounded' | 'rectRot' | 'star' | 'triangle' | HTMLImageElement | HTMLCanvasElement */> | Scriptable<dynamic /* 'circle' | 'cross' | 'crossRot' | 'dash' | 'line' | 'rect' | 'rectRounded' | 'rectRot' | 'star' | 'triangle' | HTMLImageElement | HTMLCanvasElement */> */
            get() = definedExternally
            set(value) = definedExternally
        var radius: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var rotation: dynamic /* Number | Array<Number> | Scriptable<Number> */
            get() = definedExternally
            set(value) = definedExternally
        var xAxisID: String?
            get() = definedExternally
            set(value) = definedExternally
        var yAxisID: String?
            get() = definedExternally
            set(value) = definedExternally
        var type: String /* 'line' | 'bar' | 'horizontalBar' | 'radar' | 'doughnut' | 'polarArea' | 'bubble' | 'pie' | 'scatter' | String */
        var hidden: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var hideInLegendAndTooltip: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var showLine: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var stack: String?
            get() = definedExternally
            set(value) = definedExternally
        var spanGaps: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var weight: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ChartScales {
        var type: String /* 'category' | 'linear' | 'logarithmic' | 'time' | 'radialLinear' | String */
        var display: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var position: String /* 'left' | 'right' | 'top' | 'bottom' | 'chartArea' | String */
        var gridLines: GridLineOptions?
            get() = definedExternally
            set(value) = definedExternally
        var scaleLabel: ScaleTitleOptions?
            get() = definedExternally
            set(value) = definedExternally
        var ticks: TickOptions?
            get() = definedExternally
            set(value) = definedExternally
        var xAxes: Array<ChartXAxe>?
            get() = definedExternally
            set(value) = definedExternally
        var yAxes: Array<ChartYAxe>?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface CommonAxe {
        var bounds: String?
            get() = definedExternally
            set(value) = definedExternally
        var type: String /* 'category' | 'linear' | 'logarithmic' | 'time' | 'radialLinear' | String */
        var display: dynamic /* Boolean | String */
            get() = definedExternally
            set(value) = definedExternally
        var id: String?
            get() = definedExternally
            set(value) = definedExternally
        var stacked: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var position: String?
            get() = definedExternally
            set(value) = definedExternally
        var ticks: TickOptions?
            get() = definedExternally
            set(value) = definedExternally
        var gridLines: GridLineOptions?
            get() = definedExternally
            set(value) = definedExternally
        var scaleLabel: ScaleTitleOptions?
            get() = definedExternally
            set(value) = definedExternally
        var time: TimeScale?
            get() = definedExternally
            set(value) = definedExternally
        var offset: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        val beforeUpdate: ((scale: Any) -> Unit)?
            get() = definedExternally
        val beforeSetDimension: ((scale: Any) -> Unit)?
            get() = definedExternally
        val beforeDataLimits: ((scale: Any) -> Unit)?
            get() = definedExternally
        val beforeBuildTicks: ((scale: Any) -> Unit)?
            get() = definedExternally
        val beforeTickToLabelConversion: ((scale: Any) -> Unit)?
            get() = definedExternally
        val beforeCalculateTickRotation: ((scale: Any) -> Unit)?
            get() = definedExternally
        val beforeFit: ((scale: Any) -> Unit)?
            get() = definedExternally
        val afterUpdate: ((scale: Any) -> Unit)?
            get() = definedExternally
        val afterSetDimension: ((scale: Any) -> Unit)?
            get() = definedExternally
        val afterDataLimits: ((scale: Any) -> Unit)?
            get() = definedExternally
        val afterBuildTicks: ((scale: Any, ticks: Array<Number>) -> Array<Number>)?
            get() = definedExternally
        val afterTickToLabelConversion: ((scale: Any) -> Unit)?
            get() = definedExternally
        val afterCalculateTickRotation: ((scale: Any) -> Unit)?
            get() = definedExternally
        val afterFit: ((scale: Any) -> Unit)?
            get() = definedExternally
    }

    interface ChartXAxe : CommonAxe {
        var distribution: String /* 'linear' | 'series' */
    }

    interface ChartYAxe : CommonAxe
    interface LinearScale : ChartScales {
        override var ticks: TickOptions?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface LogarithmicScale : ChartScales {
        override var ticks: TickOptions?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface TimeDisplayFormat {
        var millisecond: String?
            get() = definedExternally
            set(value) = definedExternally
        var second: String?
            get() = definedExternally
            set(value) = definedExternally
        var minute: String?
            get() = definedExternally
            set(value) = definedExternally
        var hour: String?
            get() = definedExternally
            set(value) = definedExternally
        var day: String?
            get() = definedExternally
            set(value) = definedExternally
        var week: String?
            get() = definedExternally
            set(value) = definedExternally
        var month: String?
            get() = definedExternally
            set(value) = definedExternally
        var quarter: String?
            get() = definedExternally
            set(value) = definedExternally
        var year: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface TimeScale : ChartScales {
        var displayFormats: TimeDisplayFormat?
            get() = definedExternally
            set(value) = definedExternally
        var isoWeekday: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var max: String?
            get() = definedExternally
            set(value) = definedExternally
        var min: String?
            get() = definedExternally
            set(value) = definedExternally
        var parser: dynamic /* String | (arg: Any) -> Any */
            get() = definedExternally
            set(value) = definedExternally
        var round: String /* 'millisecond' | 'second' | 'minute' | 'hour' | 'day' | 'week' | 'month' | 'quarter' | 'year' */
        var tooltipFormat: String?
            get() = definedExternally
            set(value) = definedExternally
        var unit: String /* 'millisecond' | 'second' | 'minute' | 'hour' | 'day' | 'week' | 'month' | 'quarter' | 'year' */
        var unitStepSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var stepSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var minUnit: String /* 'millisecond' | 'second' | 'minute' | 'hour' | 'day' | 'week' | 'month' | 'quarter' | 'year' */
    }

    interface RadialLinearScale {
        var animate: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var position: String /* 'left' | 'right' | 'top' | 'bottom' | 'chartArea' */
        var angleLines: AngleLineOptions?
            get() = definedExternally
            set(value) = definedExternally
        var pointLabels: PointLabelOptions?
            get() = definedExternally
            set(value) = definedExternally
        var ticks: LinearTickOptions?
            get() = definedExternally
            set(value) = definedExternally
        var display: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var gridLines: GridLineOptions?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface Point {
        var x: Number
        var y: Number
    }

    interface PluginServiceGlobalRegistration {
        var id: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface PluginServiceRegistrationOptions {
        val beforeInit: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val afterInit: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val beforeUpdate: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val afterUpdate: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val beforeLayout: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val afterLayout: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val beforeDatasetsUpdate: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val afterDatasetsUpdate: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val beforeDatasetUpdate: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val afterDatasetUpdate: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val beforeRender: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val afterRender: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
        val beforeDraw: ((chartInstance: Chart, easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */, options: Any) -> Unit)?
            get() = definedExternally
        val afterDraw: ((chartInstance: Chart, easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */, options: Any) -> Unit)?
            get() = definedExternally
        val beforeDatasetsDraw: ((chartInstance: Chart, easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */, options: Any) -> Unit)?
            get() = definedExternally
        val afterDatasetsDraw: ((chartInstance: Chart, easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */, options: Any) -> Unit)?
            get() = definedExternally
        val beforeDatasetDraw: ((chartInstance: Chart, easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */, options: Any) -> Unit)?
            get() = definedExternally
        val afterDatasetDraw: ((chartInstance: Chart, easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */, options: Any) -> Unit)?
            get() = definedExternally
        val beforeTooltipDraw: ((chartInstance: Chart, tooltipData: Any, options: Any) -> Unit)?
            get() = definedExternally
        val afterTooltipDraw: ((chartInstance: Chart, tooltipData: Any, options: Any) -> Unit)?
            get() = definedExternally
        val beforeEvent: ((chartInstance: Chart, event: Event, options: Any) -> Unit)?
            get() = definedExternally
        val afterEvent: ((chartInstance: Chart, event: Event, options: Any) -> Unit)?
            get() = definedExternally
        val resize: ((chartInstance: Chart, newChartSize: ChartSize, options: Any) -> Unit)?
            get() = definedExternally
        val destroy: ((chartInstance: Chart) -> Unit)?
            get() = definedExternally
        val afterScaleUpdate: ((chartInstance: Chart, options: Any) -> Unit)?
            get() = definedExternally
    }

    interface ChartUpdateProps {
        var duration: Number?
            get() = definedExternally
            set(value) = definedExternally
        var lazy: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */
    }

    interface ChartRenderProps {
        var duration: Number?
            get() = definedExternally
            set(value) = definedExternally
        var lazy: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var easing: String /* 'linear' | 'easeInQuad' | 'easeOutQuad' | 'easeInOutQuad' | 'easeInCubic' | 'easeOutCubic' | 'easeInOutCubic' | 'easeInQuart' | 'easeOutQuart' | 'easeInOutQuart' | 'easeInQuint' | 'easeOutQuint' | 'easeInOutQuint' | 'easeInSine' | 'easeOutSine' | 'easeInOutSine' | 'easeInExpo' | 'easeOutExpo' | 'easeInOutExpo' | 'easeInCirc' | 'easeOutCirc' | 'easeInOutCirc' | 'easeInElastic' | 'easeOutElastic' | 'easeInOutElastic' | 'easeInBack' | 'easeOutBack' | 'easeInOutBack' | 'easeInBounce' | 'easeOutBounce' | 'easeInOutBounce' */
    }

    interface DoughnutModel {
        var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | Array<String> */
            get() = definedExternally
            set(value) = definedExternally
        var borderAlign: String /* 'center' | 'inner' */
        var borderColor: String
        var borderWidth: Number
        var circumference: Number
        var endAngle: Number
        var innerRadius: Number
        var outerRadius: Number
        var startAngle: Number
        var x: Number
        var y: Number
    }
}

open external class PluginServiceStatic {
    open fun register(plugin: PluginServiceGlobalRegistration /* Chart.PluginServiceGlobalRegistration & Chart.PluginServiceRegistrationOptions */)
    open fun unregister(plugin: PluginServiceGlobalRegistration /* Chart.PluginServiceGlobalRegistration & Chart.PluginServiceRegistrationOptions */)
}

external interface Meta {
    var type: String /* 'line' | 'bar' | 'horizontalBar' | 'radar' | 'doughnut' | 'polarArea' | 'bubble' | 'pie' | 'scatter' */
    var data: Array<MetaData>
    var dataset: ChartDataSets?
        get() = definedExternally
        set(value) = definedExternally
    var controller: Json
    var hidden: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var total: String?
        get() = definedExternally
        set(value) = definedExternally
    var xAxisID: String?
        get() = definedExternally
        set(value) = definedExternally
    var yAxisID: String?
        get() = definedExternally
        set(value) = definedExternally
    var `$filler`: Json?
        get() = definedExternally
        set(value) = definedExternally
}

external interface MetaData {
    var _chart: Chart
    var _datasetIndex: Number
    var _index: Number
    var _model: Model
    var _start: Any?
        get() = definedExternally
        set(value) = definedExternally
    var _view: Model
    var _xScale: ChartScales
    var _yScale: ChartScales
    var hidden: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Model {
    var backgroundColor: String
    var borderAlign: String /* 'center' | 'inner' */
    var borderColor: String
    var borderWidth: Number?
        get() = definedExternally
        set(value) = definedExternally
    var circumference: Number?
        get() = definedExternally
        set(value) = definedExternally
    var controlPointNextX: Number
    var controlPointNextY: Number
    var controlPointPreviousX: Number
    var controlPointPreviousY: Number
    var endAngle: Number?
        get() = definedExternally
        set(value) = definedExternally
    var hitRadius: Number
    var innerRadius: Number?
        get() = definedExternally
        set(value) = definedExternally
    var outerRadius: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pointStyle: String
    var radius: String
    var skip: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var startAngle: Number?
        get() = definedExternally
        set(value) = definedExternally
    var steppedLine: Any?
        get() = definedExternally
        set(value) = definedExternally
    var tension: Number
    var x: Number
    var y: Number
    var base: Number
    var head: Number
}