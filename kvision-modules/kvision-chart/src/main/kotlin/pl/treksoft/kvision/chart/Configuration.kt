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
@file:Suppress("TooManyFunctions")

package pl.treksoft.kvision.chart

import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.chart.js.Chart
import pl.treksoft.kvision.core.Color
import pl.treksoft.kvision.core.FontStyle
import pl.treksoft.kvision.utils.obj


/**
 * Chart types.
 */
enum class ChartType(internal val type: String) {
    LINE("line"),
    BAR("bar"),
    HORIZONTALBAR("horizontalBar"),
    RADAR("radar"),
    DOUGHNUT("doughnut"),
    PIE("pie"),
    POLARAREA("polarArea"),
    BUBBLE("bubble"),
    SCATTER("scatter")
}

/**
 * Chart interaction modes.
 */
enum class InteractionMode(internal val mode: String) {
    POINT("point"),
    NEAREST("nearest"),
    INDEX("index"),
    DATASET("dataset"),
    X("x"),
    Y("y")
}

/**
 * Chart animation easings.
 */
enum class Eeasing(internal val mode: String) {
    LINEAR("linear"),
    EASEINQUAD("easeInQuad"),
    EASEOUTQUAD("easeOutQuad"),
    EASEINOUTQUAD("easeInOutQuad"),
    EASEINCUBIC("easeInCubic"),
    EASEOUTCUBIC("easeOutCubic"),
    EASEINOUTCUBIC("easeInOutCubic"),
    EASEINQUART("easeInQuart"),
    EASEOUTQUART("easeOutQuart"),
    EASEINOUTQUART("easeInOutQuart"),
    EASEINQUINT("easeInQuint"),
    EASEOUTQUINT("easeOutQuint"),
    EASEINOUTQUINT("easeInOutQuint"),
    EASEINSINE("easeInSine"),
    EASEOUTSINE("easeOutSine"),
    EASEINOUTSINE("easeInOutSine"),
    EASEINEXPO("easeInExpo"),
    EASEOUTEXPO("easeOutExpo"),
    EASEINOUTEXPO("easeInOutExpo"),
    EASEINCIRC("easeInCirc"),
    EASEOUTCIRC("easeOutCirc"),
    EASEINOUTCIRC("easeInOutCirc"),
    EASEINELASTIC("easeInElastic"),
    EASEOUTELASTIC("easeOutElastic"),
    EASEINOUTELASTIC("easeInOutElastic"),
    EASEINBACK("easeInBack"),
    EASEOUTBACK("easeOutBack"),
    EASEINOUTBACK("easeInOutBack"),
    EASEINBOUNCE("easeInBounce"),
    EASEOUTBOUNCE("easeOutBounce"),
    EASEINOUTBOUNCE("easeInOutBounce")
}

/**
 * Chart objects positions.
 */
enum class Position(internal val position: String) {
    TOP("top"),
    LEFT("left"),
    RIGHT("right"),
    BOTTOM("bottom")
}

/**
 * Chart tooltip positions.
 */
enum class TooltipPosition(internal val mode: String) {
    AVERAGE("average"),
    NEAREST("nearest")
}

/**
 * Chart point styles.
 */
enum class PointStyle(internal val style: String) {
    CIRCLE("circle"),
    CROSS("cross"),
    CROSSROT("crossRot"),
    DASH("dash"),
    LINE("line"),
    RECT("rect"),
    RECTROUNDED("rectRounded"),
    RECTROT("rectRot"),
    START("star"),
    TRIANGLE("triangle")
}

/**
 * Chart interpolation modes.
 */
enum class InterpolationMode(internal val mode: String) {
    DEFAULT("default"),
    MONOTONE("monotone")
}

/**
 * Chart scales.
 */
enum class ScalesType(internal val type: String) {
    CATEGORY("category"),
    LINEAR("linear"),
    LOGARITHMIC("logarithmic"),
    TIME("time"),
    RADIALLINEAR("radialLinear")
}

/**
 * Canvas line end point styles.
 */
enum class LineCap(internal val mode: String) {
    BUTT("butt"),
    ROUND("round"),
    SQUARE("square")
}

/**
 * Canvas line join styles.
 */
enum class LineJoin(internal val mode: String) {
    BEVEL("bevel"),
    ROUND("round"),
    MITER("miter")
}

/**
 * Chart hover options.
 */
data class HoverOptions(
    val mode: InteractionMode = InteractionMode.NEAREST,
    val animationDuration: Int = 400,
    val intersect: Boolean = true,
    val axis: String = "x"
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun HoverOptions.toJs(): dynamic {
    return obj {
        this.mode = mode.mode
        this.animationDuration = animationDuration
        this.intersect = intersect
        this.axis = axis
    }
}

/**
 * Chart animation options.
 */
data class AnimationOptions(
    val duration: Int = 1000,
    var easing: Eeasing = Eeasing.EASEOUTQUART,
    val onProgress: ((obj: Chart.ChartAnimationObject) -> Unit)? = null,
    val onComplete: ((obj: Chart.ChartAnimationObject) -> Unit)? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun AnimationOptions.toJs(): dynamic {
    return obj {
        this.duration = duration
        this.easing = easing.mode
        if (onProgress != null) this.onProgress = onProgress
        if (onComplete != null) this.onComplete = onComplete
    }
}

/**
 * Chart layout padding options.
 */
data class LayoutPaddingObject(
    val top: Int? = null,
    val right: Int? = null,
    val bottom: Int? = null,
    val left: Int? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LayoutPaddingObject.toJs(): dynamic {
    return obj {
        if (top != null) this.top = top
        if (right != null) this.right = right
        if (bottom != null) this.bottom = bottom
        if (left != null) this.left = left
    }
}

/**
 * Chart layout options.
 */
data class LayoutOptions(val padding: LayoutPaddingObject)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LayoutOptions.toJs(): dynamic {
    return obj {
        this.padding = padding.toJs()
    }
}

/**
 * Chart legend label options.
 */
data class LegendLabelOptions(
    val boxWidth: Int = 40,
    val fontSize: Int = 12,
    val fontStyle: FontStyle? = null,
    val fontColor: Color? = null,
    val fontFamily: String? = null,
    val padding: Int = 10,
    val generateLabels: ((chart: Any) -> Any)? = null,
    val filter: ((legendItem: Chart.ChartLegendLabelItem, data: Chart.ChartData) -> Any)? = null,
    val usePointStyle: Boolean = false
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LegendLabelOptions.toJs(): dynamic {
    return obj {
        this.boxWidth = boxWidth
        this.fontSize = fontSize
        if (fontStyle != null) this.fontStyle = fontStyle.name
        if (fontColor != null) this.fontColor = fontColor.asString()
        if (fontFamily != null) this.fontFamily = fontFamily
        this.padding = padding
        if (generateLabels != null) this.generateLabels = generateLabels
        if (filter != null) this.filter = filter
        this.usePointStyle = usePointStyle
    }
}

/**
 * Chart legend options.
 */
data class LegendOptions(
    val display: Boolean = true,
    val position: Position = Position.TOP,
    val fullWidth: Boolean = true,
    val reverse: Boolean = false,
    val onClick: ((event: MouseEvent, legendItem: Chart.ChartLegendLabelItem) -> Unit)? = null,
    val onHover: ((event: MouseEvent, legendItem: Chart.ChartLegendLabelItem) -> Unit)? = null,
    val labels: LegendLabelOptions? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LegendOptions.toJs(): dynamic {
    return obj {
        this.display = display
        this.position = position.position
        this.fullWidth = fullWidth
        this.reverse = reverse
        if (onClick != null) this.onClick = onClick
        if (onHover != null) this.onHover = onHover
        if (labels != null) this.labels = labels.toJs()
    }
}

/**
 * Chart title options.
 */
data class TitleOptions(
    val display: Boolean = false,
    val position: Position = Position.TOP,
    val fontSize: Int = 12,
    val fontStyle: FontStyle? = null,
    val fontColor: Color? = null,
    val fontFamily: String? = null,
    val padding: Int = 10,
    val lineHeight: String? = null,
    val text: List<String>? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun TitleOptions.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        this.display = display
        this.position = position.position
        this.fontSize = fontSize
        if (fontStyle != null) this.fontStyle = fontStyle.name
        if (fontColor != null) this.fontColor = fontColor.asString()
        if (fontFamily != null) this.fontFamily = fontFamily
        this.padding = padding
        if (lineHeight != null) this.lineHeight = lineHeight
        if (text != null) this.text = text.map(i18nTranslator).toTypedArray()
    }
}

/**
 * Chart tooltips callbacks.
 */
data class TooltipCallback(
    val beforeTitle: ((item: Array<Chart.ChartTooltipItem>, data: Chart.ChartData) -> dynamic)? = null,
    val title: ((item: Array<Chart.ChartTooltipItem>, data: Chart.ChartData) -> dynamic)? = null,
    val afterTitle: ((item: Array<Chart.ChartTooltipItem>, data: Chart.ChartData) -> dynamic)? = null,
    val beforeBody: ((item: Array<Chart.ChartTooltipItem>, data: Chart.ChartData) -> dynamic)? = null,
    val beforeLabel: ((tooltipItem: Chart.ChartTooltipItem, data: Chart.ChartData) -> dynamic)? = null,
    val label: ((tooltipItem: Chart.ChartTooltipItem, data: Chart.ChartData) -> dynamic)? = null,
    val labelColor: ((tooltipItem: Chart.ChartTooltipItem, chart: Chart) -> Chart.ChartTooltipLabelColor)? = null,
    val labelTextColor: ((tooltipItem: Chart.ChartTooltipItem, chart: Chart) -> String)? = null,
    val afterLabel: ((tooltipItem: Chart.ChartTooltipItem, data: Chart.ChartData) -> dynamic)? = null,
    val afterBody: ((item: Array<Chart.ChartTooltipItem>, data: Chart.ChartData) -> dynamic)? = null,
    val beforeFooter: ((item: Array<Chart.ChartTooltipItem>, data: Chart.ChartData) -> dynamic)? = null,
    val footer: ((item: Array<Chart.ChartTooltipItem>, data: Chart.ChartData) -> dynamic)? = null,
    val afterFooter: ((item: Array<Chart.ChartTooltipItem>, data: Chart.ChartData) -> dynamic)? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("ComplexMethod")
fun TooltipCallback.toJs(): dynamic {
    return obj {
        if (beforeTitle != null) this.beforeTitle = beforeTitle
        if (title != null) this.title = title
        if (afterTitle != null) this.afterTitle = afterTitle
        if (beforeBody != null) this.beforeBody = beforeBody
        if (beforeLabel != null) this.beforeLabel = beforeLabel
        if (label != null) this.label = label
        if (labelColor != null) this.labelColor = labelColor
        if (labelTextColor != null) this.labelTextColor = labelTextColor
        if (afterLabel != null) this.afterLabel = afterLabel
        if (afterBody != null) this.afterBody = afterBody
        if (beforeFooter != null) this.beforeFooter = beforeFooter
        if (footer != null) this.footer = footer
        if (afterFooter != null) this.afterFooter = afterFooter
    }
}

/**
 * Chart tooltip options.
 */
data class TooltipOptions(
    val enabled: Boolean = true,
    val custom: ((a: Any) -> Unit)? = null,
    val mode: InteractionMode = InteractionMode.NEAREST,
    val intersect: Boolean = true,
    val position: TooltipPosition = TooltipPosition.AVERAGE,
    val callbacks: TooltipCallback? = null,
    val filter: ((item: Chart.ChartTooltipItem, data: Chart.ChartData) -> Boolean)? = null,
    val itemSort: ((itemA: Chart.ChartTooltipItem, itemB: Chart.ChartTooltipItem) -> Number)? = null,
    val backgroundColor: Color? = null,
    val titleFontSize: Int = 12,
    val titleFontStyle: FontStyle? = null,
    val titleFontColor: Color? = null,
    val titleFontFamily: String? = null,
    val titleSpacing: Int = 2,
    val titleMarginBottom: Int = 6,
    val bodyFontSize: Int = 12,
    val bodyFontStyle: FontStyle? = null,
    val bodyFontColor: Color? = null,
    val bodyFontFamily: String? = null,
    val bodySpacing: Int = 2,
    val footerFontSize: Int = 12,
    val footerFontStyle: FontStyle? = null,
    val footerFontColor: Color? = null,
    val footerFontFamily: String? = null,
    val footerSpacing: Int = 2,
    val footerMarginTop: Int = 6,
    val xPadding: Int = 6,
    val yPadding: Int = 6,
    val caretPadding: Int = 2,
    val caretSize: Int = 5,
    val cornerRadius: Int = 6,
    val multiKeyBackground: Color? = null,
    val displayColors: Boolean = true,
    val borderColor: Color? = null,
    val borderWidth: Int = 0
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("ComplexMethod")
fun TooltipOptions.toJs(): dynamic {
    return obj {
        this.enabled = enabled
        if (custom != null) this.custom = custom
        this.mode = mode.mode
        this.intersect = intersect
        this.position = position.mode
        if (callbacks != null) this.callbacks = callbacks.toJs()
        if (filter != null) this.filter = filter
        if (itemSort != null) this.itemSort = itemSort
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        this.titleFontSize = titleFontSize
        if (titleFontStyle != null) this.titleFontStyle = titleFontStyle.name
        if (titleFontColor != null) this.titleFontColor = titleFontColor.asString()
        if (titleFontFamily != null) this.titleFontFamily = titleFontFamily
        this.titleSpacing = titleSpacing
        this.titleMarginBottom = titleMarginBottom
        this.bodyFontSize = bodyFontSize
        if (bodyFontStyle != null) this.bodyFontStyle = bodyFontStyle.name
        if (bodyFontColor != null) this.bodyFontColor = bodyFontColor.asString()
        if (bodyFontFamily != null) this.bodyFontFamily = bodyFontFamily
        this.bodySpacing = bodySpacing
        this.footerFontSize = footerFontSize
        if (footerFontStyle != null) this.footerFontStyle = footerFontStyle.name
        if (footerFontColor != null) this.footerFontColor = footerFontColor.asString()
        if (footerFontFamily != null) this.footerFontFamily = footerFontFamily
        this.footerSpacing = footerSpacing
        this.footerMarginTop = footerMarginTop
        this.xPadding = xPadding
        this.yPadding = yPadding
        this.caretPadding = caretPadding
        this.caretSize = caretSize
        this.cornerRadius = cornerRadius
        if (multiKeyBackground != null) this.multiKeyBackground = multiKeyBackground.asString()
        this.displayColors = displayColors
        if (borderColor != null) this.borderColor = borderColor.asString()
        this.borderWidth = borderWidth
    }
}

/**
 * Chart point options.
 */
data class PointOptions(
    val radius: Int = 3,
    val pointStyle: PointStyle = PointStyle.CIRCLE,
    val backgroundColor: Color? = null,
    val borderWidth: Int = 1,
    val borderColor: Color? = null,
    val hitRadius: Int = 1,
    val hoverRadius: Int = 4,
    val hoverBorderWidth: Int = 1
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun PointOptions.toJs(): dynamic {
    return obj {
        this.radius = radius
        this.pointStyle = pointStyle.style
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        this.borderWidth = borderWidth
        if (borderColor != null) this.borderColor = borderColor.asString()
        this.hitRadius = hitRadius
        this.hoverRadius = hoverRadius
        this.hoverBorderWidth = hoverBorderWidth
    }
}

/**
 * Chart line options.
 */
data class LineOptions(
    val cubicInterpolationMode: InterpolationMode = InterpolationMode.DEFAULT,
    val tension: Double = 0.2,
    val backgroundColor: Color? = null,
    val borderWidth: Int = 1,
    val borderColor: Color? = null,
    val borderCapStyle: LineCap? = null,
    val borderDash: List<Any>? = null,
    val borderDashOffset: Int = 0,
    val borderJoinStyle: LineJoin? = null,
    val capBezierPoints: Boolean = true,
    val fill: Boolean = true,
    val stepped: Boolean = false
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LineOptions.toJs(): dynamic {
    return obj {
        this.cubicInterpolationMode = cubicInterpolationMode.mode
        this.tension = tension
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        this.borderWidth = borderWidth
        if (borderColor != null) this.borderColor = borderColor.asString()
        if (borderCapStyle != null) this.borderCapStyle = borderCapStyle.mode
        if (borderDash != null) this.borderDash = borderDash.toTypedArray()
        this.borderDashOffset = borderDashOffset
        if (borderJoinStyle != null) this.borderJoinStyle = borderJoinStyle.mode
        this.capBezierPoints = capBezierPoints
        this.fill = fill
        this.stepped = stepped
    }
}

/**
 * Chart arc options.
 */
data class ArcOptions(
    val backgroundColor: Color? = null,
    val borderColor: Color? = null,
    val borderWidth: Int = 2
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ArcOptions.toJs(): dynamic {
    return obj {
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        if (borderColor != null) this.borderColor = borderColor.asString()
        this.borderWidth = borderWidth
    }
}

/**
 * Chart rectangle options.
 */
data class RectangleOptions(
    val backgroundColor: Color? = null,
    val borderColor: Color? = null,
    val borderWidth: Int = 0,
    val borderSkipped: Position = Position.BOTTOM
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun RectangleOptions.toJs(): dynamic {
    return obj {
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        if (borderColor != null) this.borderColor = borderColor.asString()
        this.borderWidth = borderWidth
        this.borderSkipped = borderSkipped.position
    }
}

/**
 * Chart elements options.
 */
data class ElementsOptions(
    val point: PointOptions? = null,
    val line: LineOptions? = null,
    val arc: ArcOptions? = null,
    val rectangle: RectangleOptions? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ElementsOptions.toJs(): dynamic {
    return obj {
        if (point != null) this.point = point.toJs()
        if (line != null) this.line = line.toJs()
        if (arc != null) this.arc = arc.toJs()
        if (rectangle != null) this.rectangle = rectangle.toJs()
    }
}

/**
 * Chart grid line options.
 */
data class GridLineOptions(
    val display: Boolean = true,
    val color: Color? = null,
    val borderDash: List<Int>? = null,
    val borderDashOffset: Int = 0,
    val lineWidth: Int = 1,
    val drawBorder: Boolean = true,
    val drawOnChartArea: Boolean = true,
    val drawTicks: Boolean = true,
    val tickMarkLength: Int = 10,
    val zeroLineWidth: Int = 1,
    val zeroLineColor: Color? = null,
    val zeroLineBorderDash: List<Int>? = null,
    val zeroLineBorderDashOffset: Int = 0,
    val offsetGridLines: Boolean = false
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun GridLineOptions.toJs(): dynamic {
    return obj {
        this.display = display
        if (color != null) this.color = color.asString()
        if (borderDash != null) this.borderDash = borderDash.toTypedArray()
        this.borderDashOffset = borderDashOffset
        this.lineWidth = lineWidth
        this.drawBorder = drawBorder
        this.drawOnChartArea = drawOnChartArea
        this.drawTicks = drawTicks
        this.tickMarkLength = tickMarkLength
        this.zeroLineWidth = zeroLineWidth
        if (zeroLineColor != null) this.zeroLineColor = zeroLineColor.asString()
        if (zeroLineBorderDash != null) this.zeroLineBorderDash = zeroLineBorderDash.toTypedArray()
        this.zeroLineBorderDashOffset = zeroLineBorderDashOffset
        this.offsetGridLines = offsetGridLines
    }
}

/**
 * Chart scale title options.
 */
data class ScaleTitleOptions(
    val display: Boolean = false,
    val labelString: String? = null,
    val fontSize: Int = 12,
    val fontStyle: FontStyle? = null,
    val fontColor: Color? = null,
    val fontFamily: String? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ScaleTitleOptions.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        this.display = display
        if (labelString != null) this.labelString = i18nTranslator(labelString)
        this.fontSize = fontSize
        if (fontStyle != null) this.fontStyle = fontStyle.name
        if (fontColor != null) this.fontColor = fontColor.asString()
        if (fontFamily != null) this.fontFamily = fontFamily
    }
}

/**
 * Chart tick options.
 */
data class TickOptions(
    val callback: ((value: Any, index: Any, values: Any) -> dynamic)? = null,
    val display: Boolean = true,
    val fontSize: Int = 12,
    val fontStyle: FontStyle? = null,
    val fontColor: Color? = null,
    val fontFamily: String? = null,
    val reverse: Boolean = false,
    val minor: dynamic = null,
    val major: dynamic = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun TickOptions.toJs(): dynamic {
    return obj {
        if (callback != null) this.callback = callback
        this.display = display
        this.fontSize = fontSize
        if (fontStyle != null) this.fontStyle = fontStyle.name
        if (fontColor != null) this.fontColor = fontColor.asString()
        if (fontFamily != null) this.fontFamily = fontFamily
        this.reverse = reverse
        if (minor != null) this.minor = minor
        if (major != null) this.major = major
    }
}

/**
 * Chart scales.
 */
data class ChartScales(
    val type: ScalesType? = null,
    val display: Boolean = true,
    val position: Position? = null,
    val gridLines: GridLineOptions? = null,
    val scaleLabel: ScaleTitleOptions? = null,
    val ticks: TickOptions? = null,
    val xAxes: List<dynamic>? = null,
    val yAxes: List<dynamic>? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ChartScales.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        if (type != null) this.type = type.type
        this.display = display
        if (position != null) this.position = position.position
        if (gridLines != null) this.gridLines = gridLines.toJs()
        if (scaleLabel != null) this.scaleLabel = scaleLabel.toJs(i18nTranslator)
        if (ticks != null) this.ticks = ticks.toJs()
        if (xAxes != null) this.xAxes = xAxes.toTypedArray()
        if (yAxes != null) this.yAxes = yAxes.toTypedArray()
    }
}

/**
 * Chart options.
 */
data class ChartOptions(
    val responsive: Boolean = true,
    val responsiveAnimationDuration: Int = 0,
    val aspectRatio: Int = 2,
    val maintainAspectRatio: Boolean = true,
    val onResize: ((chart: Chart, newSize: Chart.ChartSize) -> Unit)? = null,
    val devicePixelRatio: Int? = null,
    val hover: HoverOptions? = null,
    val events: List<String>? = null,
    val onHover: ((chart: Chart, event: MouseEvent, activeElements: Array<Any>) -> Any)? = null,
    val onClick: ((event: MouseEvent, activeElements: Array<Any>) -> Any)? = null,
    val animation: AnimationOptions? = null,
    val layout: LayoutOptions? = null,
    val legend: LegendOptions? = null,
    val legendCallback: ((chart: Chart) -> String)? = null,
    val title: TitleOptions? = null,
    val tooltips: TooltipOptions? = null,
    val elements: ElementsOptions? = null,
    val scales: ChartScales? = null,
    val showLines: Boolean? = null,
    val spanGaps: Boolean? = null,
    val cutoutPercentage: Int? = null,
    val circumference: Double? = null,
    val rotation: Double? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("ComplexMethod")
fun ChartOptions.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        this.responsive = responsive
        this.responsiveAnimationDuration = responsiveAnimationDuration
        this.aspectRatio = aspectRatio
        this.maintainAspectRatio = maintainAspectRatio
        if (onResize != null) this.onResize = onResize
        if (devicePixelRatio != null) this.devicePixelRatio = devicePixelRatio
        if (hover != null) this.hover = hover.toJs()
        if (events != null) this.events = events.toTypedArray()
        if (onHover != null) this.onHover = onHover
        if (onClick != null) this.onClick = onClick
        if (animation != null) this.animation = animation.toJs()
        if (layout != null) this.layout = layout.toJs()
        if (legend != null) this.legend = legend.toJs()
        if (legendCallback != null) this.legendCallback = legendCallback
        if (title != null) this.title = title.toJs(i18nTranslator)
        if (tooltips != null) this.tooltips = tooltips.toJs()
        if (elements != null) this.elements = elements.toJs()
        if (scales != null) this.scales = scales.toJs(i18nTranslator)
        if (showLines != null) this.showLines = showLines
        if (spanGaps != null) this.spanGaps = spanGaps
        if (cutoutPercentage != null) this.cutoutPercentage = cutoutPercentage
        if (circumference != null) this.circumference = circumference
        if (rotation != null) this.rotation = rotation
    }
}

/**
 * Chart data sets.
 */
data class DataSets(
    val cubicInterpolationMode: InterpolationMode? = null,
    val backgroundColor: List<Color>? = null,
    val borderWidth: List<Int>? = null,
    val borderColor: List<Color>? = null,
    val borderCapStyle: List<LineCap>? = null,
    val borderDash: List<Int>? = null,
    val borderDashOffset: Int? = null,
    val borderJoinStyle: List<LineJoin>? = null,
    val borderSkipped: List<Position>? = null,
    val data: List<dynamic>? = null,
    val fill: Boolean? = null,
    val hoverBackgroundColor: List<Color>? = null,
    val hoverBorderColor: List<Color>? = null,
    val hoverBorderWidth: List<Int>? = null,
    val label: String? = null,
    val lineTension: Number? = null,
    val steppedLine: Boolean? = null,
    val pointBorderColor: List<Color>? = null,
    val pointBackgroundColor: List<Color>? = null,
    val pointBorderWidth: List<Int>? = null,
    val pointRadius: List<Int>? = null,
    val pointHoverRadius: List<Int>? = null,
    val pointHitRadius: List<Int>? = null,
    val pointHoverBackgroundColor: List<Color>? = null,
    val pointHoverBorderColor: List<Color>? = null,
    val pointHoverBorderWidth: List<Int>? = null,
    val pointStyle: List<PointStyle>? = null,
    val xAxisID: String? = null,
    val yAxisID: String? = null,
    val type: ChartType? = null,
    val hidden: Boolean? = null,
    val hideInLegendAndTooltip: Boolean? = null,
    val showLine: Boolean? = null,
    val stack: String? = null,
    val spanGaps: Boolean? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("ComplexMethod")
fun DataSets.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        if (cubicInterpolationMode != null) this.cubicInterpolationMode = cubicInterpolationMode.mode
        if (backgroundColor != null) this.backgroundColor =
            backgroundColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (borderWidth != null) this.borderWidth = borderWidth.toTypedArray().checkSingleValue()
        if (borderColor != null) this.borderColor = borderColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (borderCapStyle != null) this.borderCapStyle =
            borderCapStyle.map { it.mode }.toTypedArray().checkSingleValue()
        if (borderDash != null) this.borderDash = borderDash.toTypedArray()
        if (borderDashOffset != null) this.borderDashOffset = borderDashOffset
        if (borderJoinStyle != null) this.borderJoinStyle =
            borderJoinStyle.map { it.mode }.toTypedArray().checkSingleValue()
        if (borderSkipped != null) this.borderSkipped =
            borderSkipped.map { it.position }.toTypedArray().checkSingleValue()
        if (data != null) this.data = data.toTypedArray()
        if (fill != null) this.fill = fill
        if (hoverBackgroundColor != null) this.hoverBackgroundColor =
            hoverBackgroundColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (hoverBorderColor != null) this.hoverBorderColor =
            hoverBorderColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (hoverBorderWidth != null) this.hoverBorderWidth = hoverBorderWidth.toTypedArray().checkSingleValue()
        if (label != null) this.label = i18nTranslator(label)
        if (lineTension != null) this.lineTension = lineTension
        if (steppedLine != null) this.steppedLine = steppedLine
        if (pointBorderColor != null) this.pointBorderColor =
            pointBorderColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (pointBackgroundColor != null) this.pointBackgroundColor =
            pointBackgroundColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (pointBorderWidth != null) this.pointBorderWidth = pointBorderWidth.toTypedArray().checkSingleValue()
        if (pointRadius != null) this.pointRadius = pointRadius.toTypedArray().checkSingleValue()
        if (pointHoverRadius != null) this.pointHoverRadius = pointHoverRadius.toTypedArray().checkSingleValue()
        if (pointHitRadius != null) this.pointHitRadius = pointHitRadius.toTypedArray().checkSingleValue()
        if (pointHoverBackgroundColor != null) this.pointHoverBackgroundColor =
            pointHoverBackgroundColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (pointHoverBorderColor != null) this.pointHoverBorderColor =
            pointHoverBorderColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (pointHoverBorderWidth != null) this.pointHoverBorderWidth =
            pointHoverBorderWidth.toTypedArray().checkSingleValue()
        if (pointStyle != null) this.pointStyle = pointStyle.map { it.style }.toTypedArray().checkSingleValue()
        if (xAxisID != null) this.xAxisID = xAxisID
        if (yAxisID != null) this.yAxisID = yAxisID
        if (type != null) this.type = type.type
        if (hidden != null) this.hidden = hidden
        if (hideInLegendAndTooltip != null) this.hideInLegendAndTooltip = hideInLegendAndTooltip
        if (showLine != null) this.showLine = showLine
        if (stack != null) this.stack = stack
        if (spanGaps != null) this.spanGaps = spanGaps
    }
}

/**
 * Chart configuration.
 */
data class Configuration(
    val type: ChartType,
    val dataSets: List<DataSets>,
    val labels: List<String>? = null,
    val options: ChartOptions? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
fun Configuration.toJs(i18nTranslator: (String) -> (String)): Chart.ChartConfiguration {
    return obj {
        this.type = type.type
        this.data = obj {
            if (labels != null) this.labels = labels.map(i18nTranslator).toTypedArray()
            this.datasets = dataSets.map {
                @Suppress("UnsafeCastFromDynamic")
                it.toJs(i18nTranslator)
            }.toTypedArray()
        }
        if (options != null) this.options = options.toJs(i18nTranslator)
    } as Chart.ChartConfiguration
}

private fun Array<dynamic>.checkSingleValue(): dynamic {
    return if (this.size == 1) {
        this[0]
    } else {
        this
    }
}
