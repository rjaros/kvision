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

package io.kvision.chart

import io.kvision.chart.js.*
import io.kvision.chart.js.Chart
import io.kvision.core.Color
import io.kvision.i18n.I18n
import io.kvision.utils.obj


/**
 * Chart types.
 */
enum class ChartType(internal val type: String) {
    LINE("line"),
    BAR("bar"),
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
    BOTTOM("bottom"),
    CENTER("center"),
    CHARTAREA("chartArea")
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
    TIMESERIES("timeseries"),
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
 * Legend align options.
 */
enum class LegendAlign(internal val mode: String) {
    CENTER("center"),
    END("end"),
    START("start")
}

/**
 * Title and body align options.
 */
enum class TooltipAlign(internal val mode: String) {
    CENTER("center"),
    LEFT("left"),
    RIGHT("right")
}

/**
 * Legend item align options.
 */
enum class LegendLabelAlign(internal val mode: String) {
    CENTER("center"),
    LEFT("left"),
    RIGHT("right")
}

/**
 * Title align options.
 */
enum class TitleAlign(internal val mode: String) {
    CENTER("center"),
    LEFT("left"),
    RIGHT("right"),
    INNER("inner")
}

/**
 * Legend text direction options.
 */
enum class TextDirection(internal val mode: String) {
    LTR("ltr"),
    RTL("rtl")
}

/**
 * Chart bar border skip modes.
 */
enum class SkipMode(internal val mode: String) {
    TOP("top"),
    LEFT("left"),
    RIGHT("right"),
    BOTTOM("bottom"),
    START("start"),
    END("end"),
    MIDDLE("middle"),
}

/**
 * Border align modes.
 */
enum class BorderAlign(internal val mode: String) {
    CENTER("center"),
    INNER("inner")
}

/**
 * Tick cross align modes.
 */
enum class CrossAlign(internal val mode: String) {
    CENTER("center"),
    NEAR("near"),
    FAR("far")
}

/**
 * Scales bounds modes.
 */
enum class ScaleBounds(internal val mode: String) {
    DATA("data"),
    TICKS("ticks")
}

/**
 * Chart font options.
 */
data class ChartFont(
    val family: String? = null,
    val size: Number? = null,
    val style: String? = null,
    val weight: String? = null,
    val lineHeight: Number? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ChartFont.toJs(): dynamic {
    return obj {
        if (family != null) this.family = family
        if (size != null) this.size = size
        if (style != null) this.style = style
        if (weight != null) this.weight = weight
        if (lineHeight != null) this.lineHeight = lineHeight
    }
}

/**
 * Chart interactions options.
 */
data class InteractionOptions(
    val mode: InteractionMode = InteractionMode.NEAREST,
    val intersect: Boolean = true,
    val axis: String = "x",
    val includeInvisible: Boolean = true
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun InteractionOptions.toJs(): dynamic {
    return obj {
        this.mode = mode.mode
        this.intersect = intersect
        this.axis = axis
        this.includeInvisible = includeInvisible
    }
}

/**
 * Chart animation options.
 */
data class AnimationOptions(
    val duration: Int = 1000,
    var easing: Eeasing = Eeasing.EASEOUTQUART,
    val delay: Number? = null,
    val loop: Boolean? = null,
    val onProgress: ((obj: AnimationEvent) -> Unit)? = null,
    val onComplete: ((obj: AnimationEvent) -> Unit)? = null,
    val disabled: Boolean? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun AnimationOptions.toJs(): dynamic {
    return if (this.disabled == true) false else obj {
        this.duration = duration
        this.easing = easing.mode
        if (delay != null) this.delay = delay
        if (loop != null) this.loop = loop
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
data class LayoutOptions(val autoPadding: Boolean = true, val padding: LayoutPaddingObject? = null)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LayoutOptions.toJs(): dynamic {
    return obj {
        this.autoPadding = autoPadding
        if (padding != null) this.padding = padding.toJs()
    }
}

/**
 * Chart legend label options.
 */
data class LegendLabelOptions(
    val boxWidth: Int = 40,
    val boxHeight: Int? = null,
    val color: Color? = null,
    val font: ChartFont? = null,
    val padding: Int = 10,
    val generateLabels: ((chart: Any) -> Array<LegendItem>)? = null,
    val filter: ((legendItem: LegendItem, data: ChartData) -> Boolean)? = null,
    val sort: ((a: LegendItem, b: LegendItem, data: ChartData) -> Number)? = null,
    val usePointStyle: Boolean = false,
    val pointStyle: PointStyle? = null,
    val textAlign: LegendLabelAlign? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LegendLabelOptions.toJs(): dynamic {
    return obj {
        this.boxWidth = boxWidth
        if (boxHeight != null) this.boxHeight = boxHeight
        if (color != null) this.color = color.asString()
        if (font != null) this.font = font.toJs()
        this.padding = padding
        if (generateLabels != null) this.generateLabels = generateLabels
        if (filter != null) this.filter = filter
        if (sort != null) this.sort = sort
        this.usePointStyle = usePointStyle
        if (pointStyle != null) this.pointStyle = pointStyle.style
        if (textAlign != null) this.textAlign = textAlign.mode
    }
}

/**
 * Chart legend title options.
 */
data class LegendTitleOptions(
    val display: Boolean? = null,
    val color: Color? = null,
    val font: ChartFont? = null,
    val padding: Int? = null,
    val text: String? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LegendTitleOptions.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        if (display != null) this.display = display
        if (color != null) this.color = color.asString()
        if (font != null) this.font = font.toJs()
        if (padding != null) this.padding = padding
        if (text != null) this.text = i18nTranslator(text)
    }
}

/**
 * Chart legend options.
 */
data class LegendOptions(
    val display: Boolean = true,
    val position: Position = Position.TOP,
    val align: LegendAlign? = null,
    val maxHeight: Int? = null,
    val maxWidth: Int? = null,
    val fullSize: Boolean = true,
    val reverse: Boolean = false,
    val labels: LegendLabelOptions? = null,
    val rtl: Boolean? = null,
    val textDirection: TextDirection? = null,
    val title: LegendTitleOptions? = null,
    val onClick: ((event: ChartEvent, legendItem: LegendItem, legend: LegendElement) -> Unit)? = null,
    val onHover: ((event: ChartEvent, legendItem: LegendItem, legend: LegendElement) -> Unit)? = null,
    val onLeave: ((event: ChartEvent, legendItem: LegendItem, legend: LegendElement) -> Unit)? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LegendOptions.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        this.display = display
        this.position = position.position
        if (align != null) this.align = align.mode
        if (maxHeight != null) this.maxHeight = maxHeight
        if (maxWidth != null) this.maxWidth = maxWidth
        this.fullSize = fullSize
        this.reverse = reverse
        if (labels != null) this.labels = labels.toJs()
        if (rtl != null) this.rtl = rtl
        if (textDirection != null) this.textDirection = textDirection.mode
        if (title != null) this.title = title.toJs(i18nTranslator)
        if (onClick != null) this.onClick = onClick
        if (onHover != null) this.onHover = onHover
        if (onLeave != null) this.onLeave = onLeave
    }
}

/**
 * Chart title options.
 */
data class TitleOptions(
    val align: TitleAlign? = null,
    val display: Boolean = false,
    val position: Position = Position.TOP,
    val color: Color? = null,
    val font: ChartFont? = null,
    val padding: Int = 10,
    val fullSize: Boolean? = null,
    val text: List<String>? = null,
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
        if (fullWidth != null) this.fullWidth = fullWidth
    }
}

/**
 * Chart tooltips callbacks.
 */
data class TooltipCallback(
    val beforeTitle: ((item: Array<TooltipItem>) -> dynamic)? = null,
    val title: ((item: Array<TooltipItem>) -> dynamic)? = null,
    val afterTitle: ((item: Array<TooltipItem>) -> dynamic)? = null,
    val beforeBody: ((item: Array<TooltipItem>) -> dynamic)? = null,
    val beforeLabel: ((tooltipItem: TooltipItem) -> dynamic)? = null,
    val label: ((tooltipItem: TooltipItem) -> dynamic)? = null,
    val labelColor: ((tooltipItem: TooltipItem, chart: Chart) -> TooltipLabelStyle)? = null,
    val labelTextColor: ((tooltipItem: TooltipItem, chart: Chart) -> String)? = null,
    val afterLabel: ((tooltipItem: TooltipItem) -> dynamic)? = null,
    val afterBody: ((item: Array<TooltipItem>) -> dynamic)? = null,
    val beforeFooter: ((item: Array<TooltipItem>) -> dynamic)? = null,
    val footer: ((item: Array<TooltipItem>) -> dynamic)? = null,
    val afterFooter: ((item: Array<TooltipItem>) -> dynamic)? = null
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
    val external: ((ctx: TooltipContext) -> Unit)? = null,
    val mode: InteractionMode? = null,
    val intersect: Boolean? = null,
    val position: TooltipPosition? = null,
    val callbacks: TooltipCallback? = null,
    val filter: ((e: TooltipItem, index: Number, array: Array<TooltipItem>, data: ChartData) -> Boolean)? = null,
    val itemSort: ((a: TooltipItem, b: TooltipItem, data: ChartData) -> Number)? = null,
    val backgroundColor: Color? = null,
    val titleColor: Color? = null,
    val titleFont: ChartFont? = null,
    val titleAlign: TooltipAlign? = null,
    val titleSpacing: Int? = null,
    val titleMarginBottom: Int? = null,
    val bodyColor: Color? = null,
    val bodyFont: ChartFont? = null,
    val bodyAlign: TooltipAlign? = null,
    val bodySpacing: Int? = null,
    val footerColor: Color? = null,
    val footerFont: ChartFont? = null,
    val footerAlign: TooltipAlign? = null,
    val footerSpacing: Int? = null,
    val footerMarginTop: Int? = null,
    val padding: LayoutPaddingObject? = null,
    val caretPadding: Int? = null,
    val caretSize: Int? = null,
    val cornerRadius: Int? = null,
    val multiKeyBackground: Color? = null,
    val displayColors: Boolean? = null,
    val boxWidth: Int? = null,
    val boxHeight: Int? = null,
    val boxPadding: Number? = null,
    val usePointStyle: Boolean? = null,
    val borderColor: Color? = null,
    val borderWidth: Int? = null,
    val rtl: Boolean? = null,
    val textDirection: TextDirection? = null,
    val xAlign: TooltipAlign? = null,
    val yAlign: TooltipAlign? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("ComplexMethod")
fun TooltipOptions.toJs(): dynamic {
    return obj {
        this.enabled = enabled
        if (external != null) this.external = external
        if (mode != null) this.mode = mode.mode
        if (intersect != null) this.intersect = intersect
        if (position != null) this.position = position.mode
        if (callbacks != null) this.callbacks = callbacks.toJs()
        if (filter != null) this.filter = filter
        if (itemSort != null) this.itemSort = itemSort
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        if (titleColor != null) this.titleColor = titleColor.asString()
        if (titleFont != null) this.titleFont = titleFont.toJs()
        if (titleAlign != null) this.titleAlign = titleAlign.mode
        if (titleSpacing != null) this.titleSpacing = titleSpacing
        if (titleMarginBottom != null) this.titleMarginBottom = titleMarginBottom
        if (bodyColor != null) this.bodyColor = bodyColor.asString()
        if (bodyFont != null) this.bodyFont = bodyFont.toJs()
        if (bodyAlign != null) this.bodyAlign = bodyAlign.mode
        if (bodySpacing != null) this.bodySpacing = bodySpacing
        if (footerColor != null) this.footerColor = footerColor.asString()
        if (footerFont != null) this.footerFont = footerFont.toJs()
        if (footerAlign != null) this.footerAlign = footerAlign.mode
        if (footerSpacing != null) this.footerSpacing = footerSpacing
        if (footerMarginTop != null) this.footerMarginTop = footerMarginTop
        if (padding != null) this.padding = padding.toJs()
        if (caretPadding != null) this.caretPadding = caretPadding
        if (caretSize != null) this.caretSize = caretSize
        if (cornerRadius != null) this.cornerRadius = cornerRadius
        if (multiKeyBackground != null) this.multiKeyBackground = multiKeyBackground.asString()
        if (displayColors != null) this.displayColors = displayColors
        if (boxWidth != null) this.boxWidth = boxWidth
        if (boxHeight != null) this.boxHeight = boxHeight
        if (boxPadding != null) this.boxPadding = boxPadding
        if (usePointStyle != null) this.usePointStyle = usePointStyle
        if (borderColor != null) this.borderColor = borderColor.asString()
        if (borderWidth != null) this.borderWidth = borderWidth
        if (rtl != null) this.rtl = rtl
        if (textDirection != null) this.textDirection = textDirection.mode
        if (xAlign != null) this.xAlign = xAlign
        if (yAlign != null) this.yAlign = yAlign
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
    val tension: Double? = null,
    val backgroundColor: Color? = null,
    val borderWidth: Int? = null,
    val borderColor: Color? = null,
    val borderCapStyle: LineCap? = null,
    val borderDash: List<Any>? = null,
    val borderDashOffset: Number? = null,
    val borderJoinStyle: LineJoin? = null,
    val capBezierPoints: Boolean? = null,
    val fill: Boolean? = null,
    val stepped: Boolean? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun LineOptions.toJs(): dynamic {
    return obj {
        this.cubicInterpolationMode = cubicInterpolationMode.mode
        if (tension != null) this.tension = tension
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        if (borderWidth != null) this.borderWidth = borderWidth
        if (borderColor != null) this.borderColor = borderColor.asString()
        if (borderCapStyle != null) this.borderCapStyle = borderCapStyle.mode
        if (borderDash != null) this.borderDash = borderDash.toTypedArray()
        if (borderDashOffset != null) this.borderDashOffset = borderDashOffset
        if (borderJoinStyle != null) this.borderJoinStyle = borderJoinStyle.mode
        if (capBezierPoints != null) this.capBezierPoints = capBezierPoints
        if (fill != null) this.fill = fill
        if (stepped != null) this.stepped = stepped
    }
}

/**
 * Chart arc options.
 */
data class ArcOptions(
    val angle: Number? = null,
    val backgroundColor: Color? = null,
    val borderAlign: TitleAlign? = null,
    val borderColor: Color? = null,
    val borderWidth: Int? = null,
    val borderJoinStyle: LineJoin? = null,
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ArcOptions.toJs(): dynamic {
    return obj {
        if (angle != null) this.angle = angle
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        if (borderAlign != null) this.borderAlign = borderAlign.mode
        if (borderColor != null) this.borderColor = borderColor.asString()
        if (borderWidth != null) this.borderWidth = borderWidth
        if (borderJoinStyle != null) this.borderJoinStyle = borderJoinStyle.mode
    }
}

/**
 * Chart bar options.
 */
data class BarOptions(
    val backgroundColor: Color? = null,
    val borderColor: Color? = null,
    val borderWidth: Int? = null,
    val borderSkipped: SkipMode? = null,
    val borderRadius: Number? = null,
    val inflateAmount: Number? = null,
    val pointStyle: PointStyle? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun BarOptions.toJs(): dynamic {
    return obj {
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        if (borderColor != null) this.borderColor = borderColor.asString()
        if (borderWidth != null) this.borderWidth = borderWidth
        if (borderSkipped != null) this.borderSkipped = borderSkipped.mode
        if (borderRadius != null) this.borderRadius = borderRadius
        if (inflateAmount != null) this.inflateAmount = inflateAmount
        if (pointStyle != null) this.pointStyle = pointStyle.style
    }
}

/**
 * Chart elements options.
 */
data class ElementsOptions(
    val point: PointOptions? = null,
    val line: LineOptions? = null,
    val arc: ArcOptions? = null,
    val bar: BarOptions? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ElementsOptions.toJs(): dynamic {
    return obj {
        if (point != null) this.point = point.toJs()
        if (line != null) this.line = line.toJs()
        if (arc != null) this.arc = arc.toJs()
        if (bar != null) this.bar = bar.toJs()
    }
}

/**
 * Chart grid line options.
 */
data class GridLineOptions(
    val display: Boolean = true,
    val color: Color? = null,
    val lineWidth: Int? = null,
    val drawOnChartArea: Boolean? = null,
    val drawTicks: Boolean? = null,
    val offset: Boolean? = null,
    val tickLength: Number? = null,
    val tickWidth: Number? = null,
    val tickColor: Color? = null,
    val tickBorderDash: List<Number>? = null,
    val tickBorderDashOffset: Number? = null,
    val circular: Boolean? = null,
    val z: Number? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun GridLineOptions.toJs(): dynamic {
    return obj {
        this.display = display
        if (color != null) this.color = color.asString()
        if (lineWidth != null) this.lineWidth = lineWidth
        if (drawOnChartArea != null) this.drawOnChartArea = drawOnChartArea
        if (drawTicks != null) this.drawTicks = drawTicks
        if (offset != null) this.offset = offset
        if (tickLength != null) this.tickLength = tickLength
        if (tickWidth != null) this.tickWidth = tickWidth
        if (tickColor != null) this.tickColor = tickColor.asString()
        if (tickBorderDash != null) this.tickBorderDash = tickBorderDash.toTypedArray()
        if (tickBorderDashOffset != null) this.tickBorderDashOffset = tickBorderDashOffset
        if (circular != null) this.circular = circular
        if (z != null) this.z = z
    }
}

/**
 * Chart scale title options.
 */
data class ScaleTitleOptions(
    val display: Boolean? = null,
    val color: Color? = null,
    val font: ChartFont? = null,
    val padding: LayoutPaddingObject? = null,
    val align: TitleAlign? = null,
    val text: String? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ScaleTitleOptions.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        if (display != null) this.display = display
        if (color != null) this.color = color.asString()
        if (font != null) this.font = font.toJs()
        if (padding != null) this.padding = padding.toJs()
        if (align != null) this.align = align.mode
        if (text != null) this.text = i18nTranslator(text)
    }
}

/**
 * Chart tick options.
 */
data class TickOptions(
    val callback: ((value: Any, index: Any, values: Any) -> dynamic)? = null,
    val display: Boolean = true,
    val backdropColor: Color? = null,
    val backdropPadding: LayoutPaddingObject? = null,
    val color: Color? = null,
    val font: ChartFont? = null,
    val major: dynamic = null,
    val padding: Number? = null,
    val showLabelBackdrop: Boolean? = null,
    val textStrokeColor: Color? = null,
    val textStrokeWidth: Number? = null,
    val z: Number? = null,
    val align: TitleAlign? = null,
    val crossAlign: CrossAlign? = null,
    val sampleSize: Number? = null,
    val autoSkip: Boolean? = null,
    val autoSkipPadding: Number? = null,
    val includeBounds: Boolean? = null,
    val labelOffset: Number? = null,
    val maxRotation: Number? = null,
    val minRotation: Number? = null,
    val mirror: Boolean? = null,
    val count: Number? = null,
    val format: dynamic = null,
    val maxTicksLimit: Int? = null,
    val precision: Number? = null,
    val stepSize: Number? = null,
    val source: dynamic = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun TickOptions.toJs(): dynamic {
    return obj {
        if (callback != null) this.callback = callback
        this.display = display
        if (backdropColor != null) this.backdropColor = backdropColor.asString()
        if (backdropPadding != null) this.backdropPadding = backdropPadding.toJs()
        if (color != null) this.color = color.asString()
        if (font != null) this.font = font.toJs()
        if (major != null) this.major = major
        if (padding != null) this.padding = padding
        if (showLabelBackdrop != null) this.showLabelBackdrop = showLabelBackdrop
        if (textStrokeColor != null) this.textStrokeColor = textStrokeColor.asString()
        if (textStrokeWidth != null) this.textStrokeWidth = textStrokeWidth
        if (align != null) this.align = align.mode
        if (crossAlign != null) this.crossAlign = crossAlign.mode
        if (sampleSize != null) this.sampleSize = sampleSize
        if (autoSkip != null) this.autoSkip = autoSkip
        if (autoSkipPadding != null) this.autoSkipPadding = autoSkipPadding
        if (includeBounds != null) this.includeBounds = includeBounds
        if (labelOffset != null) this.labelOffset = labelOffset
        if (maxRotation != null) this.maxRotation = maxRotation
        if (minRotation != null) this.minRotation = minRotation
        if (mirror != null) this.mirror = mirror
        if (count != null) this.count = count
        if (format != null) this.format = format
        if (maxTicksLimit != null) this.maxTicksLimit = maxTicksLimit
        if (precision != null) this.precision = precision
        if (stepSize != null) this.stepSize = stepSize
        if (source != null) this.source = source
    }
}

/**
 * Chart border options.
 */
data class BorderOptions(
    val display: Boolean? = null,
    val color: Color? = null,
    val width: Int? = null,
    val dash: List<Number>? = null,
    val dashOffset: Number? = null,
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun BorderOptions.toJs(): dynamic {
    return obj {
        if (display != null) this.display = display
        if (color != null) this.color = color.asString()
        if (width != null) this.width = width
        if (dash != null) this.dash = dash.toTypedArray()
        if (dashOffset != null) this.dashOffset = dashOffset
    }
}

/**
 * Chart scales.
 */
data class ChartScales(
    val type: ScalesType? = null,
    val alignToPixels: Boolean? = null,
    val backgroundColor: Color? = null,
    val display: Boolean? = null,
    val grid: GridLineOptions? = null,
    val border: BorderOptions? = null,
    val min: dynamic = null,
    val max: dynamic = null,
    val reverse: Boolean? = null,
    val stacked: Boolean? = null,
    val suggestedMax: Number? = null,
    val suggestedMin: Number? = null,
    val ticks: TickOptions? = null,
    val weight: Number? = null,
    val bounds: ScaleBounds? = null,
    val position: Position? = null,
    val stack: String? = null,
    val stackWeight: Number? = null,
    val axis: String? = null,
    val offset: Boolean? = null,
    val title: ScaleTitleOptions? = null,
    val labels: List<String>? = null,
    val beginAtZero: Boolean? = null,
    val grace: dynamic = null,
    val adapters: dynamic = null,
    val time: dynamic = null,
    val animate: Boolean? = null,
    val angleLines: dynamic = null,
    val pointLabels: dynamic = null,
    val startAngle: Number? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
fun ChartScales.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        if (type != null) this.type = type.type
        if (alignToPixels != null) this.alignToPixels = alignToPixels
        if (backgroundColor != null) this.backgroundColor = backgroundColor.asString()
        if (display != null) this.display = display
        if (grid != null) this.grid = grid.toJs()
        if (border != null) this.border = border.toJs()
        if (min != null) this.min = min
        if (max != null) this.max = max
        if (reverse != null) this.reverse = reverse
        if (stacked != null) this.stacked = stacked
        if (suggestedMax != null) this.suggestedMax = suggestedMax
        if (suggestedMin != null) this.suggestedMin = suggestedMin
        if (ticks != null) this.ticks = ticks.toJs()
        if (weight != null) this.weight = max
        if (bounds != null) this.bounds = bounds.mode
        if (position != null) this.position = position.position
        if (stack != null) this.stack = stack
        if (stackWeight != null) this.stackWeight = stackWeight
        if (axis != null) this.axis = axis
        if (offset != null) this.offset = offset
        if (title != null) this.title = title.toJs(i18nTranslator)
        if (labels != null) this.labels = labels.map { i18nTranslator(it) }.toTypedArray()
        if (beginAtZero != null) this.beginAtZero = beginAtZero
        if (grace != null) this.grace = grace
        if (adapters != null) this.adapters = adapters
        if (time != null) this.time = time
        if (animate != null) this.animate = animate
        if (angleLines != null) this.angleLines = angleLines
        if (pointLabels != null) this.pointLabels = pointLabels
        if (startAngle != null) this.startAngle = startAngle
    }
}

/**
 * Built-in plugins options.
 */
data class PluginsOptions(
    val legend: LegendOptions? = null,
    val title: TitleOptions? = null,
    val subtitle: TitleOptions? = null,
    val tooltip: TooltipOptions? = null,
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("ComplexMethod")
fun PluginsOptions.toJs(i18nTranslator: (String) -> (String)): dynamic {
    return obj {
        if (legend != null) this.legend = legend.toJs(i18nTranslator)
        if (title != null) this.title = title.toJs(i18nTranslator)
        if (subtitle != null) this.subtitle = subtitle.toJs(i18nTranslator)
        if (tooltip != null) this.tooltip = tooltip.toJs()
    }
}

/**
 * Chart options.
 */
data class ChartOptions(
    val responsive: Boolean = true,
    val aspectRatio: Number? = null,
    val maintainAspectRatio: Boolean = true,
    val onResize: ((chart: Chart, newSize: ChartSize) -> Unit)? = null,
    val resizeDelay: Int? = null,
    val devicePixelRatio: Number? = null,
    val locale: String? = null,
    val interaction: InteractionOptions? = null,
    val hover: InteractionOptions? = null,
    val events: List<String>? = null,
    val onHover: ((event: ChartEvent, elements: Array<ActiveElement>, chart: Chart) -> Any)? = null,
    val onClick: ((event: ChartEvent, elements: Array<ActiveElement>, chart: Chart) -> Any)? = null,
    val animation: AnimationOptions? = null,
    val animations: dynamic = null,
    val transitions: dynamic = null,
    val layout: LayoutOptions? = null,
    val elements: ElementsOptions? = null,
    val elementsDynamic: dynamic = null,
    val scales: Map<String, ChartScales>? = null,
    val scalesDynamic: dynamic = null,
    val showLine: Boolean? = null,
    val spanGaps: Boolean? = null,
    val cutoutPercentage: Int? = null,
    val circumference: Double? = null,
    val rotation: Double? = null,
    val plugins: PluginsOptions? = null,
    val pluginsDynamic: dynamic = null,
    val datasets: dynamic = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("ComplexMethod")
fun ChartOptions.toJs(i18nTranslator: (String) -> (String)): dynamic {
    val elementsTmp = if (elementsDynamic != null) {
        if (elements == null) {
            elementsDynamic
        } else {
            js("Object").assign(elementsDynamic, elements.toJs())
        }
    } else {
        elements?.toJs()
    }
    val scalesTmp = if (scalesDynamic != null) {
        if (scales == null) {
            scalesDynamic
        } else {
            val s = js("{}")
            scales.forEach {
                s[it.key] = it.value.toJs(i18nTranslator)
            }
            js("Object").assign(scalesDynamic, s)
        }
    } else if (scales != null) {
        val s = js("{}")
        scales.forEach {
            s[it.key] = it.value.toJs(i18nTranslator)
        }
        s
    } else null
    val pluginsTmp = if (pluginsDynamic != null) {
        if (plugins == null) {
            pluginsDynamic
        } else {
            js("Object").assign(pluginsDynamic, plugins.toJs(i18nTranslator))
        }
    } else {
        plugins?.toJs(i18nTranslator)
    }
    return obj {
        this.responsive = responsive
        this.responsiveAnimationDuration = responsiveAnimationDuration
        if (aspectRatio != null) this.aspectRatio = aspectRatio
        this.maintainAspectRatio = maintainAspectRatio
        if (onResize != null) this.onResize = onResize
        if (resizeDelay != null) this.resizeDelay = resizeDelay
        if (devicePixelRatio != null) this.devicePixelRatio = devicePixelRatio
        this.locale = locale ?: I18n.language
        if (interaction != null) this.interaction = interaction.toJs()
        if (hover != null) this.hover = hover.toJs()
        if (events != null) this.events = events.toTypedArray()
        if (onHover != null) this.onHover = onHover
        if (onClick != null) this.onClick = onClick
        if (animation != null) this.animation = animation.toJs()
        if (animations != null) this.animations = animations
        if (transitions != null) this.transitions = transitions
        if (layout != null) this.layout = layout.toJs()
        if (elementsTmp != null) this.elements = elementsTmp
        if (scalesTmp != null) this.scales = scalesTmp
        if (showLine != null) this.showLine = showLine
        if (spanGaps != null) this.spanGaps = spanGaps
        if (cutoutPercentage != null) this.cutoutPercentage = cutoutPercentage
        if (circumference != null) this.circumference = circumference
        if (rotation != null) this.rotation = rotation
        if (pluginsTmp != null) this.plugins = pluginsTmp
        if (datasets != null) this.datasets = datasets
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
    val borderCapStyle: LineCap? = null,
    val borderDash: List<Int>? = null,
    val borderDashOffset: Number? = null,
    val borderJoinStyle: LineJoin? = null,
    val borderSkipped: List<Position>? = null,
    val clip: dynamic = null,
    val data: List<dynamic>? = null,
    val fill: dynamic = null,
    val hoverBackgroundColor: List<Color>? = null,
    val hoverBorderColor: List<Color>? = null,
    val hoverBorderWidth: List<Int>? = null,
    val hoverBorderCapStyle: LineCap? = null,
    val hoverBorderDash: List<Int>? = null,
    val hoverBorderDashOffset: Number? = null,
    val hoverBorderJoinStyle: LineJoin? = null,
    val indexAxis: String? = null,
    val label: String? = null,
    val order: Number? = null,
    val pointBorderColor: List<Color>? = null,
    val pointBackgroundColor: List<Color>? = null,
    val pointBorderWidth: List<Int>? = null,
    val pointRadius: List<Number>? = null,
    val pointHoverRadius: List<Number>? = null,
    val pointHitRadius: List<Number>? = null,
    val pointHoverBackgroundColor: List<Color>? = null,
    val pointHoverBorderColor: List<Color>? = null,
    val pointHoverBorderWidth: List<Int>? = null,
    val pointRotation: List<Number>? = null,
    val pointStyle: List<PointStyle>? = null,
    val segment: dynamic = null,
    val showLine: Boolean? = null,
    val spanGaps: Boolean? = null,
    val stack: String? = null,
    val stepped: Boolean? = null,
    val tension: Number? = null,
    val xAxisID: String? = null,
    val yAxisID: String? = null,
    val base: Number? = null,
    val barPercentage: Number? = null,
    val barThickness: dynamic = null,
    val borderRadius: List<Number>? = null,
    val categoryPercentage: Number? = null,
    val grouped: Boolean? = null,
    val hoverBorderRadius: List<Number>? = null,
    val inflateAmount: List<Number>? = null,
    val maxBarThickness: Number? = null,
    val minBarLength: Number? = null,
    val skipNull: Boolean? = null,
    val circumference: Number? = null,
    val offset: List<Number>? = null,
    val rotation: Number? = null,
    val spacing: Number? = null,
    val weight: Number? = null,
    val hitRadius: List<Number>? = null,
    val hoverRadius: List<Number>? = null,
    val radius: List<dynamic>? = null,
    val borderAlign: BorderAlign? = null,
    val cutout: dynamic = null,
    val animation: dynamic = null,
    val drawActiveElementsOnTop: Boolean? = null,
    val circular: Boolean? = null
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
        if (borderCapStyle != null) this.borderCapStyle = borderCapStyle.mode
        if (borderDash != null) this.borderDash = borderDash.toTypedArray()
        if (borderDashOffset != null) this.borderDashOffset = borderDashOffset
        if (borderJoinStyle != null) this.borderJoinStyle = borderJoinStyle.mode
        if (borderSkipped != null) this.borderSkipped =
            borderSkipped.map { it.position }.toTypedArray().checkSingleValue()
        if (clip != null) this.clip = clip
        if (data != null) this.data = data.toTypedArray()
        if (fill != null) this.fill = fill
        if (hoverBackgroundColor != null) this.hoverBackgroundColor =
            hoverBackgroundColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (hoverBorderColor != null) this.hoverBorderColor =
            hoverBorderColor.map { it.asString() }.toTypedArray().checkSingleValue()
        if (hoverBorderWidth != null) this.hoverBorderWidth = hoverBorderWidth.toTypedArray().checkSingleValue()
        if (hoverBorderCapStyle != null) this.hoverBorderCapStyle = hoverBorderCapStyle.mode
        if (hoverBorderDash != null) this.hoverBorderDash = hoverBorderDash.toTypedArray()
        if (hoverBorderDashOffset != null) this.hoverBorderDashOffset = hoverBorderDashOffset
        if (hoverBorderJoinStyle != null) this.hoverBorderJoinStyle = hoverBorderJoinStyle.mode
        if (indexAxis != null) this.indexAxis = indexAxis
        if (label != null) this.label = i18nTranslator(label)
        if (order != null) this.order = order
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
        if (pointRotation != null) this.pointRotation = pointRotation.toTypedArray().checkSingleValue()
        if (pointStyle != null) this.pointStyle = pointStyle.map { it.style }.toTypedArray().checkSingleValue()
        if (segment != null) this.segment = segment
        if (showLine != null) this.showLine = showLine
        if (spanGaps != null) this.spanGaps = spanGaps
        if (stack != null) this.stack = stack
        if (stepped != null) this.stepped = stepped
        if (tension != null) this.tension = tension
        if (xAxisID != null) this.xAxisID = xAxisID
        if (yAxisID != null) this.yAxisID = yAxisID
        if (base != null) this.base = base
        if (barPercentage != null) this.barPercentage = barPercentage
        if (barThickness != null) this.barThickness = barThickness
        if (borderRadius != null) this.borderRadius = borderRadius.toTypedArray().checkSingleValue()
        if (categoryPercentage != null) this.categoryPercentage = categoryPercentage
        if (grouped != null) this.grouped = grouped
        if (hoverBorderRadius != null) this.hoverBorderRadius = hoverBorderRadius.toTypedArray().checkSingleValue()
        if (inflateAmount != null) this.inflateAmount = inflateAmount.toTypedArray().checkSingleValue()
        if (maxBarThickness != null) this.maxBarThickness = maxBarThickness
        if (minBarLength != null) this.minBarLength = minBarLength
        if (skipNull != null) this.skipNull = skipNull
        if (circumference != null) this.circumference = circumference
        if (offset != null) this.offset = offset.toTypedArray().checkSingleValue()
        if (rotation != null) this.rotation = rotation
        if (spacing != null) this.spacing = spacing
        if (weight != null) this.weight = weight
        if (hitRadius != null) this.hitRadius = hitRadius.toTypedArray().checkSingleValue()
        if (hoverRadius != null) this.hoverRadius = hoverRadius
        if (radius != null) this.radius = radius.toTypedArray().checkSingleValue()
        if (borderAlign != null) this.borderAlign = borderAlign
        if (cutout != null) this.cutout = cutout
        if (animation != null) this.animation = animation
        if (drawActiveElementsOnTop != null) this.drawActiveElementsOnTop = drawActiveElementsOnTop
        if (circular != null) this.circular = circular
    }
}

/**
 * Chart configuration.
 */
data class Configuration(
    val type: ChartType,
    val dataSets: List<DataSets>,
    val labels: List<String>? = null,
    val options: ChartOptions? = null,
    val optionsDynamic: dynamic = null,
    val plugins: List<dynamic>? = null
)

/**
 * An extension function to convert configuration class to JS object.
 */
@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
fun Configuration.toJs(i18nTranslator: (String) -> (String)): ChartConfiguration {
    val optionsTmp = if (optionsDynamic != null) {
        if (options == null) {
            optionsDynamic
        } else {
            js("Object").assign(optionsDynamic, options.toJs(i18nTranslator))
        }
    } else {
        options?.toJs(i18nTranslator)
    }
    return obj {
        this.type = type.type
        this.data = obj {
            if (labels != null) this.labels = labels.map(i18nTranslator).toTypedArray()
            this.datasets = dataSets.map {
                it.toJs(i18nTranslator)
            }.toTypedArray()
        }
        if (optionsTmp != null) this.options = optionsTmp
        if (plugins != null) this.plugins = plugins.toTypedArray()
    } as ChartConfiguration
}

private fun Array<dynamic>.checkSingleValue(): dynamic {
    return if (this.size == 1) {
        this[0]
    } else {
        this
    }
}
