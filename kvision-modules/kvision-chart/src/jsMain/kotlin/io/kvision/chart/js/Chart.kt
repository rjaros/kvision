@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)
@file:JsModule("chart.js/auto")
@file:JsNonModule

package io.kvision.chart.js

import io.kvision.chart.BorderOptions
import io.kvision.utils.NumberFormatOptions
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.CanvasRenderingContext2DSettings
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.Event

external interface ScriptableContext {
    var active: Boolean
    var chart: Chart
    var dataIndex: Number
    var dataset: UnionToIntersection<DeepPartial<Any> /* DeepPartial<Any> & ChartDatasetProperties<TType, DefaultDataPoint<TType>> */>
    var datasetIndex: Number
    var parsed: UnionToIntersection<Any>
    var raw: Any
}

external interface ScriptableLineSegmentContext {
    var type: String /* "segment" */
    var p0: PointElement__0
    var p1: PointElement__0
    var p0DataIndex: Number
    var p1DataIndex: Number
    var datasetIndex: Number
}

external interface ParsingOptions {
    var parsing: dynamic /* `T$0` | Boolean */
        get() = definedExternally
        set(value) = definedExternally
    var normalized: Boolean
}

external interface ControllerDatasetOptions : ParsingOptions {
    var indexAxis: String /* "x" | "y" */
    var clip: dynamic /* Number | ChartArea */
        get() = definedExternally
        set(value) = definedExternally
    var label: String
    var order: Number
    var stack: String
    var hidden: Boolean
}

external interface BarControllerDatasetOptions : ControllerDatasetOptions,
    AnimationOptions {
    var xAxisID: String
    var yAxisID: String
    var barPercentage: Number
    var categoryPercentage: Number
    var barThickness: dynamic /* Number | "flex" */
        get() = definedExternally
        set(value) = definedExternally
    var maxBarThickness: Number
    var minBarLength: Number
    var pointStyle: dynamic /* "circle" | "cross" | "crossRot" | "dash" | "line" | "rect" | "rectRounded" | "rectRot" | "star" | "triangle" | HTMLImageElement | HTMLCanvasElement */
        get() = definedExternally
        set(value) = definedExternally
}

external interface BarControllerChartOptions {
    var skipNull: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external var BarController: ChartComponent /* ChartComponent & `T$1` */

external interface BubbleControllerDatasetOptions : ControllerDatasetOptions


external interface BubbleDataPoint {
    var x: Number
    var y: Number
    var r: Number
}

external var BubbleController: ChartComponent /* ChartComponent & `T$2` */

external interface LineControllerDatasetOptions : ControllerDatasetOptions,
    AnimationOptions {
    var xAxisID: String
    var yAxisID: String
    var spanGaps: dynamic /* Boolean | Number */
        get() = definedExternally
        set(value) = definedExternally
    var showLine: Boolean
}

external interface LineControllerChartOptions {
    var spanGaps: dynamic /* Boolean | Number */
        get() = definedExternally
        set(value) = definedExternally
    var showLine: Boolean
}

external var LineController: ChartComponent /* ChartComponent & `T$3` */

external interface ScatterDataPoint {
    var x: Number
    var y: Number
}

external var ScatterController: ChartComponent /* ChartComponent & `T$4` */

external interface DoughnutControllerDatasetOptions : ControllerDatasetOptions,
    AnimationOptions {
    var circumference: Number
    var rotation: Number
    var weight: Number
    var spacing: Number
}

external interface DoughnutAnimationOptions {
    var animateRotate: Boolean
    var animateScale: Boolean
}

external interface DoughnutControllerChartOptions {
    var circumference: Number
    var cutout: dynamic /* Number | String | (ctx: ScriptableContext<String /* "doughnut" */>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var radius: dynamic /* Number | String | (ctx: ScriptableContext<String /* "doughnut" */>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var rotation: Number
    var spacing: Number
    var animation: dynamic /* Boolean | DoughnutAnimationOptions */
        get() = definedExternally
        set(value) = definedExternally
}

@Suppress("EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface DoughnutController : DatasetController {
    var innerRadius: Number
    var outerRadius: Number
    var offsetX: Number
    var offsetY: Number
    fun calculateTotal(): Number
    fun calculateCircumference(value: Number): Number

    companion object : ChartComponent by definedExternally
}

external interface DoughnutMetaExtensions {
    var total: Number
}

external var PieController: ChartComponent /* ChartComponent & `T$6` */

external interface PolarAreaControllerDatasetOptions : DoughnutControllerDatasetOptions {
    var angle: Number
}

external interface PolarAreaControllerChartOptions {
    var startAngle: Number
    var animation: dynamic /* Boolean | PolarAreaAnimationOptions */
        get() = definedExternally
        set(value) = definedExternally
}

@Suppress("EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface PolarAreaController : DoughnutController {
    fun countVisibleElements(): Number

    companion object : ChartComponent by definedExternally
}

external interface RadarControllerDatasetOptions : ControllerDatasetOptions,
    AnimationOptions {
    var xAxisID: String
    var yAxisID: String
    var spanGaps: dynamic /* Boolean | Number */
        get() = definedExternally
        set(value) = definedExternally
    var showLine: Boolean
}

external var RadarController: ChartComponent /* ChartComponent & `T$8` */

external interface ChartMetaCommon<TElement : Element, TDatasetElement : Element> {
    var type: String
    var controller: DatasetController
    var order: Number
    var label: String
    var index: Number
    var visible: Boolean
    var stack: Number
    var indexAxis: String /* "x" | "y" */
    var data: Array<TElement>
    var dataset: TDatasetElement?
        get() = definedExternally
        set(value) = definedExternally
    var hidden: Boolean
    var xAxisID: String?
        get() = definedExternally
        set(value) = definedExternally
    var yAxisID: String?
        get() = definedExternally
        set(value) = definedExternally
    var rAxisID: String?
        get() = definedExternally
        set(value) = definedExternally
    var iAxisID: String
    var vAxisID: String
    var xScale: Scale?
        get() = definedExternally
        set(value) = definedExternally
    var yScale: Scale?
        get() = definedExternally
        set(value) = definedExternally
    var rScale: Scale?
        get() = definedExternally
        set(value) = definedExternally
    var iScale: Scale?
        get() = definedExternally
        set(value) = definedExternally
    var vScale: Scale?
        get() = definedExternally
        set(value) = definedExternally
    var _sorted: Boolean
    var _stacked: dynamic /* Boolean | "single" */
        get() = definedExternally
        set(value) = definedExternally
    var _parsed: Array<Any>
}

external interface ActiveDataPoint {
    var datasetIndex: Number
    var index: Number
}

external interface ActiveElement : ActiveDataPoint {
    var element: Element
}

open external class Chart {
    open var platform: BasePlatform
    open var id: String
    open var canvas: HTMLCanvasElement
    open var ctx: CanvasRenderingContext2D
    open var config: ChartConfiguration
    open var width: Number
    open var height: Number
    open var aspectRatio: Number
    open var boxes: Array<LayoutItem>
    open var currentDevicePixelRatio: Number
    open var chartArea: ChartArea
    open var scales: dynamic
    open var attached: Boolean
    open var tooltip: TooltipModel
    open var data: ChartData
    open var options: ChartOptions

    constructor(item: String, config: ChartConfiguration)
    constructor(item: CanvasRenderingContext2D, config: ChartConfiguration)
    constructor(item: HTMLCanvasElement, config: ChartConfiguration)
    constructor(item: `T$76`, config: ChartConfiguration)
    constructor(item: dynamic, config: ChartConfiguration)

    open fun clear(): Chart /* this */
    open fun stop(): Chart /* this */
    open fun resize(width: Number = definedExternally, height: Number = definedExternally)
    open fun ensureScalesHaveIDs()
    open fun buildOrUpdateScales()
    open fun buildOrUpdateControllers()
    open fun reset()
    open fun update(mode: Any? = definedExternally)
    open fun render()
    open fun draw()
    open fun getElementsAtEventForMode(
        e: Event,
        mode: String,
        options: InteractionOptions,
        useFinalPosition: Boolean
    ): Array<InteractionItem>

    open fun getSortedVisibleDatasetMetas(): Array<DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<Element, Element> */>
    open fun getDatasetMeta(datasetIndex: Number): DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<Element, Element> */
    open fun getVisibleDatasetCount(): Number
    open fun isDatasetVisible(datasetIndex: Number): Boolean
    open fun setDatasetVisibility(datasetIndex: Number, visible: Boolean)
    open fun toggleDataVisibility(index: Number)
    open fun getDataVisibility(index: Number): Boolean
    open fun hide(datasetIndex: Number, dataIndex: Number = definedExternally)
    open fun show(datasetIndex: Number, dataIndex: Number = definedExternally)
    open fun getActiveElements(): Array<ActiveElement>
    open fun setActiveElements(active: Array<ActiveDataPoint>)
    open fun destroy()
    open fun toBase64Image(type: String = definedExternally, quality: Any = definedExternally): String
    open fun bindEvents()
    open fun unbindEvents()
    open fun updateHoverStyle(items: Element, mode: String /* "dataset" */, enabled: Boolean)
    open fun notifyPlugins(hook: String, args: AnyObject = definedExternally): dynamic /* Boolean | Unit */

    companion object {
        var defaults: Defaults
        var overrides: Overrides
        var version: String
        var instances: dynamic
        var registry: Registry
        fun getChart(key: String): Chart?
        fun getChart(key: CanvasRenderingContext2D): Chart?
        fun getChart(key: HTMLCanvasElement): Chart?
        fun register(vararg items: Any /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */)
        fun unregister(vararg items: Any /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */)
    }
}

external var registerables: Array<dynamic /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */>

external interface `T$76` {
    var canvas: HTMLCanvasElement
}

sealed external class UpdateModeEnum {
    object resize: UpdateModeEnum /* = 'resize' */
    object reset: UpdateModeEnum /* = 'reset' */
    object none: UpdateModeEnum /* = 'none' */
    object hide: UpdateModeEnum /* = 'hide' */
    object show: UpdateModeEnum /* = 'show' */
    object normal: UpdateModeEnum /* = 'normal' */
    object active: UpdateModeEnum /* = 'active' */
}

external interface `T$11` {
    var label: String
    var value: String
}

external interface `T$12` {
    var min: Number
    var max: Number
}

open external class DatasetController(
    chart: Chart,
    datasetIndex: Number
) {
    open var chart: Chart
    open var index: Number
    open var _cachedMeta: DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<TElement, TDatasetElement> */
    open var enableOptionSharing: Boolean
    open var supportsDecimation: Boolean
    open fun linkScales()
    open fun getAllParsedValues(scale: Scale): Array<Number>
    open fun getLabelAndValue(index: Number): `T$11`
    open fun updateElements(elements: Array<Element>, start: Number, count: Number, mode: Any)
    open fun update(mode: Any)
    open fun updateIndex(datasetIndex: Number)
    open fun getMaxOverflow(): dynamic /* Boolean | Number */
    open fun draw()
    open fun reset()
    open fun getDataset(): DeepPartial<Any> /* DeepPartial<Any> & ChartDatasetProperties<String /* "bar" | "line" | "scatter" | "bubble" | "pie" | "doughnut" | "polarArea" | "radar" */, DefaultDataPoint<TType>> */
    open fun getMeta(): DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<TElement, TDatasetElement> */
    open fun getScaleForId(scaleID: String): Scale?
    open fun configure()
    open fun initialize()
    open fun addElements()
    open fun buildOrUpdateElements(resetNewElements: Boolean = definedExternally)
    open fun getStyle(index: Number, active: Boolean): AnyObject
    open fun resolveDatasetElementOptions(mode: Any): AnyObject
    open fun resolveDataElementOptions(index: Number, mode: Any): AnyObject
    open fun getSharedOptions(options: AnyObject): AnyObject?
    open fun includeOptions(mode: Any, sharedOptions: AnyObject): Boolean
    open fun updateElement(element: Element, index: Number?, properties: AnyObject, mode: Any)
    open fun updateSharedOptions(sharedOptions: AnyObject, mode: Any, newOptions: AnyObject)
    open fun removeHoverStyle(element: Element, datasetIndex: Number, index: Number)
    open fun setHoverStyle(element: Element, datasetIndex: Number, index: Number)
    open fun parse(start: Number, count: Number)
    open fun parsePrimitiveData(
        meta: DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<TElement, TDatasetElement> */,
        data: Array<AnyObject>,
        start: Number,
        count: Number
    ): Array<AnyObject>

    open fun parseArrayData(
        meta: DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<TElement, TDatasetElement> */,
        data: Array<AnyObject>,
        start: Number,
        count: Number
    ): Array<AnyObject>

    open fun parseObjectData(
        meta: DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<TElement, TDatasetElement> */,
        data: Array<AnyObject>,
        start: Number,
        count: Number
    ): Array<AnyObject>

    open fun getParsed(index: Number): dynamic
    open fun applyStack(scale: Scale, parsed: Array<Any>): Number
    open fun updateRangeFromParsed(range: `T$12`, scale: Scale, parsed: Array<Any>, stack: Boolean)
    open fun updateRangeFromParsed(range: `T$12`, scale: Scale, parsed: Array<Any>, stack: String)
    open fun getMinMax(scale: Scale, canStack: Boolean = definedExternally): `T$12`
}

external interface DatasetControllerChartComponent : ChartComponent

external interface Defaults :
    CoreChartOptions,
    ElementChartOptions,
    PluginChartOptions {
    var scale: Any
    var scales: Any
    fun set(values: AnyObject): AnyObject
    fun set(scope: String, values: AnyObject): AnyObject
    fun get(scope: String): AnyObject
    fun describe(scope: String, values: AnyObject): AnyObject
    fun override(scope: String, values: AnyObject): AnyObject
    fun route(scope: String, name: String, targetScope: String, targetName: String)
}

external var defaults: Defaults

external interface InteractionOptions {
    var axis: String?
        get() = definedExternally
        set(value) = definedExternally
    var intersect: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var includeInvisible: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface InteractionItem {
    var element: Element
    var datasetIndex: Number
    var index: Number
}

external interface InteractionModeMap {
    var index: InteractionModeFunction
    var dataset: InteractionModeFunction
    var point: InteractionModeFunction
    var nearest: InteractionModeFunction
    var x: InteractionModeFunction
    var y: InteractionModeFunction
}

external object Interaction {
    var modes: InteractionModeMap
}

external interface `T$14` {
    var fullSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var position: dynamic /* "left" | "top" | "right" | "bottom" | "center" | "chartArea" | `T$49`? */
        get() = definedExternally
        set(value) = definedExternally
    var weight: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external object layouts {
    fun addBox(chart: Chart, item: LayoutItem)
    fun removeBox(chart: Chart, layoutItem: LayoutItem)
    fun configure(chart: Chart, item: LayoutItem, options: `T$14`)
    fun update(chart: Chart, width: Number, height: Number)
}

external interface `T$15` {
    var mode: Any
    var cancelable: Boolean
}

external interface `T$16` {
    var mode: Any
}

external interface `T$17` {
    var index: Number
    var meta: DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<Element, Element> */
    var mode: Any
    var cancelable: Boolean
}

external interface `T$18` {
    var cancelable: Boolean
}

external interface `T$19` {
    var scale: Scale
}

external interface `T$20` {
    var index: Number
    var meta: DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<Element, Element> */
}

external interface `T$21` {
    var event: ChartEvent
    var replay: Boolean
    var cancelable: Boolean
}

external interface `T$22` {
    var event: ChartEvent
    var replay: Boolean
    var changed: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var cancelable: Boolean
}

external interface ChartSize {
    var width: Number
    var height: Number
}

external interface `T$24` {
    var size: ChartSize
}

external interface Plugin<O> : ExtendedPlugin__2<O> {
    var id: String
    val install: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val start: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val stop: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val beforeInit: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val afterInit: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val beforeUpdate: ((chart: Chart, args: `T$15`, options: O) -> dynamic)?
    val afterUpdate: ((chart: Chart, args: `T$16`, options: O) -> Unit)?
    val beforeElementsUpdate: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val reset: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val beforeDatasetsUpdate: ((chart: Chart, args: `T$16`, options: O) -> dynamic)?
    val afterDatasetsUpdate: ((chart: Chart, args: `T$15`, options: O) -> Unit)?
    val beforeDatasetUpdate: ((chart: Chart, args: `T$17`, options: O) -> dynamic)?
    val afterDatasetUpdate: ((chart: Chart, args: `T$17`, options: O) -> Unit)?
    val beforeLayout: ((chart: Chart, args: `T$18`, options: O) -> dynamic)?
    val beforeDataLimits: ((chart: Chart, args: `T$19`, options: O) -> Unit)?
    val afterDataLimits: ((chart: Chart, args: `T$19`, options: O) -> Unit)?
    val beforeBuildTicks: ((chart: Chart, args: `T$19`, options: O) -> Unit)?
    val afterBuildTicks: ((chart: Chart, args: `T$19`, options: O) -> Unit)?
    val afterLayout: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val beforeRender: ((chart: Chart, args: `T$18`, options: O) -> dynamic)?
    val afterRender: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val beforeDraw: ((chart: Chart, args: `T$18`, options: O) -> dynamic)?
    val afterDraw: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val beforeDatasetsDraw: ((chart: Chart, args: `T$18`, options: O) -> dynamic)?
    val afterDatasetsDraw: ((chart: Chart, args: EmptyObject, options: O, cancelable: Boolean) -> Unit)?
    val beforeDatasetDraw: ((chart: Chart, args: `T$20`, options: O) -> dynamic)?
    val afterDatasetDraw: ((chart: Chart, args: `T$20`, options: O) -> Unit)?
    val beforeEvent: ((chart: Chart, args: `T$21`, options: O) -> dynamic)?
    val afterEvent: ((chart: Chart, args: `T$22`, options: O) -> Unit)?
    val resize: ((chart: Chart, args: `T$24`, options: O) -> Unit)?
    val beforeDestroy: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val afterDestroy: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
    val uninstall: ((chart: Chart, args: EmptyObject, options: O) -> Unit)?
}

external interface Registry {
    var controllers: TypedRegistry<DatasetController>
    var elements: TypedRegistry<Element>
    var plugins: TypedRegistry<Plugin<AnyObject>>
    var scales: TypedRegistry<Scale>
    fun add(vararg args: Any /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */)
    fun remove(vararg args: Any /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */)
    fun addControllers(vararg args: Any /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */)
    fun addElements(vararg args: Any /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */)
    fun addPlugins(vararg args: Any /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */)
    fun addScales(vararg args: Any /* ChartComponent | Array<ChartComponent> | `T$77` | Plugin__0 | Array<Plugin__0> */)
    fun getController(id: String): DatasetController?
    fun getElement(id: String): Element?
    fun getPlugin(id: String): Plugin<AnyObject>?
    fun getScale(id: String): Scale?
}

external var registry: Registry

external interface Tick {
    var value: Number
    var label: dynamic /* String? | Array<String>? */
        get() = definedExternally
        set(value) = definedExternally
    var major: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CoreScaleOptions {
    var display: dynamic /* Boolean | "auto" */
        get() = definedExternally
        set(value) = definedExternally
    var alignToPixels: Boolean
    var reverse: Boolean
    var weight: Number
    fun beforeUpdate(axis: Scale)
    fun beforeSetDimensions(axis: Scale)
    fun afterSetDimensions(axis: Scale)
    fun beforeDataLimits(axis: Scale)
    fun afterDataLimits(axis: Scale)
    fun beforeBuildTicks(axis: Scale)
    fun afterBuildTicks(axis: Scale)
    fun beforeTickToLabelConversion(axis: Scale)
    fun afterTickToLabelConversion(axis: Scale)
    fun beforeCalculateLabelRotation(axis: Scale)
    fun afterCalculateLabelRotation(axis: Scale)
    fun beforeFit(axis: Scale)
    fun afterFit(axis: Scale)
    fun afterUpdate(axis: Scale)
}

external interface `T$25` {
    var min: Number
    var max: Number
    var minDefined: Boolean
    var maxDefined: Boolean
}

external interface `T$26` {
    var id: String
    var type: String
    var ctx: CanvasRenderingContext2D
    var chart: Chart
}

open external class Scale(cfg: `T$26`) : Element, LayoutItem {
    override var x: Number
    override var y: Number
    override var active: Boolean
    override fun tooltipPosition(useFinalPosition: Boolean): Point
    override fun hasValue(): Boolean
    override fun <P : Array<Any>> getProps(props: P, final: Boolean): dynamic
    override var position: dynamic /* "left" | "top" | "right" | "bottom" | "center" | "chartArea" | `T$49` */
    override var weight: Number
    override var fullSize: Boolean
    override var width: Number
    override var height: Number
    override var left: Number
    override var top: Number
    override var right: Number
    override var bottom: Number
    override val beforeLayout: (() -> Unit)?
    override fun draw(chartArea: ChartArea)
    override val getPadding: (() -> ChartArea)?
    override fun isHorizontal(): Boolean
    override fun update(width: Number, height: Number, margins: ChartArea)
    open var id: String
    open var type: String
    open var ctx: CanvasRenderingContext2D
    open var chart: Chart
    open var maxWidth: Number
    open var maxHeight: Number
    open var paddingTop: Number
    open var paddingBottom: Number
    open var paddingLeft: Number
    open var paddingRight: Number
    open var axis: String
    open var labelRotation: Number
    open var min: Number
    open var max: Number
    open var ticks: Array<Tick>
    open fun getMatchingVisibleMetas(type: String = definedExternally): Array<DeepPartial<Any> /* DeepPartial<Any> & ChartMetaCommon<Element, Element> */>
    open fun drawTitle(chartArea: ChartArea)
    open fun drawLabels(chartArea: ChartArea)
    open fun drawGrid(chartArea: ChartArea)
    open fun getDecimalForPixel(pixel: Number): Number
    open fun getPixelForDecimal(decimal: Number): Number
    open fun getPixelForTick(index: Number): Number
    open fun getLabelForValue(value: Number): String
    open fun getLineWidthForValue(value: Number): Number
    open fun getPixelForValue(value: Number, index: Number = definedExternally): Number
    open fun getValueForPixel(pixel: Number): Number?
    open fun getBaseValue(): Number
    open fun getBasePixel(): Number
    open fun init(options: AnyObject)
    open fun parse(raw: Any, index: Number): Any
    open fun getUserBounds(): `T$25`
    open fun getMinMax(canStack: Boolean): `T$12`
    open fun getTicks(): Array<Tick>
    open fun getLabels(): Array<String>
    open fun beforeUpdate()
    open fun configure()
    open fun afterUpdate()
    open fun beforeSetDimensions()
    open fun setDimensions()
    open fun afterSetDimensions()
    open fun beforeDataLimits()
    open fun determineDataLimits()
    open fun afterDataLimits()
    open fun beforeBuildTicks()
    open fun buildTicks(): Array<Tick>
    open fun afterBuildTicks()
    open fun beforeTickToLabelConversion()
    open fun generateTickLabels(ticks: Array<Tick>)
    open fun afterTickToLabelConversion()
    open fun beforeCalculateLabelRotation()
    open fun calculateLabelRotation()
    open fun afterCalculateLabelRotation()
    open fun beforeFit()
    open fun fit()
    open fun afterFit()
    open fun isFullSize(): Boolean
    override var options: AnyObject
}

external interface ScriptableScaleContext {
    var chart: Chart
    var scale: Scale
    var index: Number
    var tick: Tick
}

external interface ScriptableScalePointLabelContext {
    var chart: Chart
    var scale: Scale
    var index: Number
    var label: String
}

external interface `T$27` {
    var value: Number
}

external interface `T$28` {
    fun values(value: Any): dynamic /* String | Array<String> */
    fun numeric(tickValue: Number, index: Number, ticks: Array<`T$27`>): String
    fun logarithmic(tickValue: Number, index: Number, ticks: Array<`T$27`>): String
}

external object Ticks {
    var formatters: `T$28`
}

external interface TypedRegistry<T> {
    fun register(item: ChartComponent): String
    fun get(id: String): T?
    fun unregister(item: ChartComponent)
}

external interface ChartEvent {
    var type: String /* "contextmenu" | "mouseenter" | "mousedown" | "mousemove" | "mouseup" | "mouseout" | "click" | "dblclick" | "keydown" | "keypress" | "keyup" | "resize" */
    var native: Event?
    var x: Number?
    var y: Number?
}

external interface ChartComponent {
    var id: String
    var defaults: AnyObject?
        get() = definedExternally
        set(value) = definedExternally
    var defaultRoutes: dynamic
        get() = definedExternally
        set(value) = definedExternally
    val beforeRegister: (() -> Unit)?
    val afterRegister: (() -> Unit)?
    val beforeUnregister: (() -> Unit)?
    val afterUnregister: (() -> Unit)?
}

external interface CoreInteractionOptions {
    var mode: String /* "index" | "dataset" | "point" | "nearest" | "x" | "y" */
    var intersect: Boolean
    var axis: String /* "x" | "y" | "xy" */
    var includeInvisible: Boolean
}

external interface `T$29`<TType> {
    var autoPadding: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var padding: dynamic /* Number? | Any? | ((ctx: ScriptableContext<TType>, options: AnyObject) -> dynamic)? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface CoreChartOptions : ParsingOptions, AnimationOptions {
    var datasets: Any
    var indexAxis: String /* "x" | "y" */
    var color: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var font: FontSpecPartial
    var responsive: Boolean
    var maintainAspectRatio: Boolean
    var resizeDelay: Number
    var aspectRatio: Number
    var locale: String
    fun onResize(chart: Chart, size: ChartSize)
    var devicePixelRatio: Number
    var interaction: CoreInteractionOptions
    var hover: CoreInteractionOptions
    var events: Array<String /* "mousemove" | "mouseout" | "click" | "touchstart" | "touchmove" | "touchend" | "pointerenter" | "pointerdown" | "pointermove" | "pointerup" | "pointerleave" | "pointerout" */>
    fun onHover(event: ChartEvent, elements: Array<ActiveElement>, chart: Chart)
    fun onClick(event: ChartEvent, elements: Array<ActiveElement>, chart: Chart)
    var layout: `T$29`<Any?>
}

external interface AnimationSpec {
    var duration: dynamic /* Number? | ((ctx: ScriptableContext<TType>, options: AnyObject) -> Number?)? */
        get() = definedExternally
        set(value) = definedExternally
    var easing: dynamic /* "linear" | "easeInQuad" | "easeOutQuad" | "easeInOutQuad" | "easeInCubic" | "easeOutCubic" | "easeInOutCubic" | "easeInQuart" | "easeOutQuart" | "easeInOutQuart" | "easeInQuint" | "easeOutQuint" | "easeInOutQuint" | "easeInSine" | "easeOutSine" | "easeInOutSine" | "easeInExpo" | "easeOutExpo" | "easeInOutExpo" | "easeInCirc" | "easeOutCirc" | "easeInOutCirc" | "easeInElastic" | "easeOutElastic" | "easeInOutElastic" | "easeInBack" | "easeOutBack" | "easeInOutBack" | "easeInBounce" | "easeOutBounce" | "easeInOutBounce" | ((ctx: ScriptableContext<TType>, options: AnyObject) -> String)? */
        get() = definedExternally
        set(value) = definedExternally
    var delay: dynamic /* Number? | ((ctx: ScriptableContext<TType>, options: AnyObject) -> Number?)? */
        get() = definedExternally
        set(value) = definedExternally
    var loop: dynamic /* Boolean? | ((ctx: ScriptableContext<TType>, options: AnyObject) -> Boolean?)? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface TransitionSpec {
    var animation: AnimationSpec
    var animations: dynamic
}

external interface AnimationOptions {
    var animation: dynamic /* Boolean | AnimationSpec<TType> & `T$31` */
        get() = definedExternally
        set(value) = definedExternally
    var animations: dynamic
    var transitions: dynamic
}

external interface FontSpec {
    var family: String
    var size: Number
    var style: String /* "normal" | "italic" | "oblique" | "initial" | "inherit" */
    var weight: String?
    var lineHeight: dynamic /* Number | String */
        get() = definedExternally
        set(value) = definedExternally
}

external interface FontSpecPartial {
    var family: String?
        get() = definedExternally
        set(value) = definedExternally
    var size: Number?
        get() = definedExternally
        set(value) = definedExternally
    var style: String? /* "normal" | "italic" | "oblique" | "initial" | "inherit" */
        get() = definedExternally
        set(value) = definedExternally
    var weight: String?
        get() = definedExternally
        set(value) = definedExternally
    var lineHeight: dynamic /* Number? | String? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$32` {
    var x: Number
    var y: Number
}

external interface VisualElement {
    fun draw(ctx: CanvasRenderingContext2D, area: ChartArea = definedExternally)
    fun inRange(mouseX: Number, mouseY: Number, useFinalPosition: Boolean = definedExternally): Boolean
    fun inXRange(mouseX: Number, useFinalPosition: Boolean = definedExternally): Boolean
    fun inYRange(mouseY: Number, useFinalPosition: Boolean = definedExternally): Boolean
    fun getCenterPoint(useFinalPosition: Boolean = definedExternally): `T$32`
    val getRange: ((axis: String /* "x" | "y" */) -> Number)?
}

external interface CommonElementOptions {
    var borderWidth: Number
    var borderColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
}

external interface CommonHoverOptions {
    var hoverBorderWidth: Number
    var hoverBorderColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var hoverBackgroundColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
}

external interface Segment {
    var start: Number
    var end: Number
    var loop: Boolean
}

external interface ArcProps {
    var x: Number
    var y: Number
    var startAngle: Number
    var endAngle: Number
    var innerRadius: Number
    var outerRadius: Number
    var circumference: Number
}

external interface ArcBorderRadius {
    var outerStart: Number
    var outerEnd: Number
    var innerStart: Number
    var innerEnd: Number
}

external interface ArcOptions : CommonElementOptions {
    var borderAlign: String /* "center" | "inner" */
    var offset: Number
    var circular: Boolean
    var borderRadius: dynamic /* Number | ArcBorderRadius */
        get() = definedExternally
        set(value) = definedExternally
}

external interface ArcHoverOptions : CommonHoverOptions {
    var hoverOffset: Number
}

@Suppress("EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface ArcElement<T : ArcProps, O : ArcOptions> : Element, VisualElement {
    companion object : ChartComponent by definedExternally
}

external interface ArcElement__0 : ArcElement<ArcProps, ArcOptions>

external interface LineProps {
    var points: Array<Point>
}

external interface `T$34` {
    var backgroundColor: dynamic /* String? | CanvasGradient? | CanvasPattern? | ((ctx: ScriptableLineSegmentContext, options: AnyObject) -> dynamic)? */
        get() = definedExternally
        set(value) = definedExternally
    var borderColor: dynamic /* String? | CanvasGradient? | CanvasPattern? | ((ctx: ScriptableLineSegmentContext, options: AnyObject) -> dynamic)? */
        get() = definedExternally
        set(value) = definedExternally
    var borderCapStyle: dynamic /* "butt" | "round" | "square" | ((ctx: ScriptableLineSegmentContext, options: AnyObject) -> String)? */
        get() = definedExternally
        set(value) = definedExternally
    var borderDash: dynamic /* Array<Number>? | ((ctx: ScriptableLineSegmentContext, options: AnyObject) -> Array<Number>?)? */
        get() = definedExternally
        set(value) = definedExternally
    var borderDashOffset: dynamic /* Number? | ((ctx: ScriptableLineSegmentContext, options: AnyObject) -> Number?)? */
        get() = definedExternally
        set(value) = definedExternally
    var borderJoinStyle: dynamic /* "bevel" | "miter" | "round" | ((ctx: ScriptableLineSegmentContext, options: AnyObject) -> String)? */
        get() = definedExternally
        set(value) = definedExternally
    var borderWidth: dynamic /* Number? | ((ctx: ScriptableLineSegmentContext, options: AnyObject) -> Number?)? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface LineOptions : CommonElementOptions {
    var borderCapStyle: String /* "butt" | "round" | "square" */
    var borderDash: Array<Number>
    var borderDashOffset: Number
    var borderJoinStyle: String /* "bevel" | "miter" | "round" */
    var capBezierPoints: Boolean
    var cubicInterpolationMode: String /* "default" | "monotone" */
    var tension: Number
    var stepped: dynamic /* "before" | "after" | "middle" | Boolean */
        get() = definedExternally
        set(value) = definedExternally
    var fill: dynamic /* Number | String | `T$27` | "start" | "end" | "origin" | "stack" | "shape" | Boolean | ComplexFillTarget */
        get() = definedExternally
        set(value) = definedExternally
    var segment: `T$34`
}

external interface LineHoverOptions : CommonHoverOptions {
    var hoverBorderCapStyle: String /* "butt" | "round" | "square" */
    var hoverBorderDash: Array<Number>
    var hoverBorderDashOffset: Number
    var hoverBorderJoinStyle: String /* "bevel" | "miter" | "round" */
}

@Suppress("EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface LineElement<T : LineProps, O : LineOptions> : Element, VisualElement {
    fun updateControlPoints(chartArea: ChartArea, indexAxis: String /* "x" | "y" */ = definedExternally)
    var points: Array<Point>
    var segments: Array<Segment>
    fun first(): dynamic /* Point | Boolean */
    fun last(): dynamic /* Point | Boolean */
    fun interpolate(point: Point, property: String /* "x" | "y" */): dynamic /* Point? | Array<Point>? */
    fun pathSegment(ctx: CanvasRenderingContext2D, segment: Segment, params: AnyObject): Boolean?
    fun path(ctx: CanvasRenderingContext2D): Boolean

    companion object : ChartComponent by definedExternally
}

external interface LineElement__0 : LineElement<LineProps, LineOptions>

external interface PointProps {
    var x: Number
    var y: Number
}

external interface PointOptions : CommonElementOptions {
    var radius: Number
    var hitRadius: Number
    var pointStyle: dynamic /* "circle" | "cross" | "crossRot" | "dash" | "line" | "rect" | "rectRounded" | "rectRot" | "star" | "triangle" | HTMLImageElement | HTMLCanvasElement */
        get() = definedExternally
        set(value) = definedExternally
    var rotation: Number
    var drawActiveElementsOnTop: Boolean
}

external interface PointHoverOptions : CommonHoverOptions {
    var hoverRadius: Number
}

external interface PointPrefixedOptions {
    var pointBackgroundColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var pointBorderColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var pointBorderWidth: Number
    var pointHitRadius: Number
    var pointRadius: Number
    var pointRotation: Number
    var pointStyle: dynamic /* "circle" | "cross" | "crossRot" | "dash" | "line" | "rect" | "rectRounded" | "rectRot" | "star" | "triangle" | HTMLImageElement | HTMLCanvasElement */
        get() = definedExternally
        set(value) = definedExternally
}

external interface PointPrefixedHoverOptions {
    var pointHoverBackgroundColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var pointHoverBorderColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var pointHoverBorderWidth: Number
    var pointHoverRadius: Number
}

@Suppress("EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface PointElement<T : PointProps, O : PointOptions> : Element, VisualElement {
    var skip: Boolean
    var parsed: CartesianParsedData

    companion object : ChartComponent by definedExternally
}

external interface PointElement__0 : PointElement<PointProps, PointOptions>

external interface BarProps {
    var x: Number
    var y: Number
    var base: Number
    var horizontal: Boolean
    var width: Number
    var height: Number
}

external interface BarOptions : CommonElementOptions {
    var base: Number
    var borderSkipped: dynamic /* "start" | "end" | "left" | "right" | "bottom" | "top" | Boolean */
        get() = definedExternally
        set(value) = definedExternally
    var borderRadius: dynamic /* Number | BorderRadius */
        get() = definedExternally
        set(value) = definedExternally
    var inflateAmount: dynamic /* Number | "auto" */
        get() = definedExternally
        set(value) = definedExternally
    override var borderWidth: dynamic /* Number | `T$37` */
        get() = definedExternally
        set(value) = definedExternally
}

external interface BorderRadius {
    var topLeft: Number
    var topRight: Number
    var bottomLeft: Number
    var bottomRight: Number
}

external interface BarHoverOptions : CommonHoverOptions {
    var hoverBorderRadius: dynamic /* Number | BorderRadius */
        get() = definedExternally
        set(value) = definedExternally
}

@Suppress("EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface BarElement<T : BarProps, O : BarOptions> : Element, VisualElement {
    companion object : ChartComponent by definedExternally
}

external interface BarElement__0 : BarElement<BarProps, BarOptions>

external interface ElementOptionsByType {
    var arc: ScriptableAndArrayOptions<ArcOptions /* ArcOptions & ArcHoverOptions */, ScriptableContext>
    var bar: ScriptableAndArrayOptions<BarOptions /* BarOptions & BarHoverOptions */, ScriptableContext>
    var line: ScriptableAndArrayOptions<LineOptions /* LineOptions & LineHoverOptions */, ScriptableContext>
    var point: ScriptableAndArrayOptions<PointOptions /* PointOptions & PointHoverOptions */, ScriptableContext>
}

external interface ElementChartOptions {
    var elements: ElementOptionsByType
}

open external class BasePlatform {
    open fun acquireContext(
        canvas: HTMLCanvasElement,
        options: CanvasRenderingContext2DSettings = definedExternally
    ): CanvasRenderingContext2D?

    open fun releaseContext(context: CanvasRenderingContext2D): Boolean
    open fun addEventListener(chart: Chart, type: String, listener: (e: ChartEvent) -> Unit)
    open fun removeEventListener(chart: Chart, type: String, listener: (e: ChartEvent) -> Unit)
    open fun getDevicePixelRatio(): Number
    open fun getMaximumSize(
        canvas: HTMLCanvasElement,
        width: Number = definedExternally,
        height: Number = definedExternally,
        aspectRatio: Number = definedExternally
    ): ChartSize

    open fun isAttached(canvas: HTMLCanvasElement): Boolean
    open fun updateConfig(config: ChartConfiguration)
}

open external class BasicPlatform : BasePlatform

open external class DomPlatform : BasePlatform

external var Decimation: Plugin<AnyObject>

external sealed class DecimationAlgorithm {
    object lttb: DecimationAlgorithm /* = 'lttb' */
    object minmax: DecimationAlgorithm /* = 'min-max' */
}

external interface BaseDecimationOptions {
    var enabled: Boolean
    var threshold: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LttbDecimationOptions : BaseDecimationOptions {
    var algorithm: dynamic /* DecimationAlgorithm.lttb | "lttb" */
        get() = definedExternally
        set(value) = definedExternally
    var samples: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface MinMaxDecimationOptions : BaseDecimationOptions {
    var algorithm: dynamic /* DecimationAlgorithm.minmax | "min-max" */
        get() = definedExternally
        set(value) = definedExternally
}

external var Filler: Plugin<AnyObject>

external interface FillerOptions {
    var drawTime: String /* "beforeDatasetDraw" | "beforeDatasetsDraw" */
    var propagate: Boolean
}

external interface ComplexFillTarget {
    var target: dynamic /* Number | String | `T$27` | "start" | "end" | "origin" | "stack" | "shape" | Boolean */
        get() = definedExternally
        set(value) = definedExternally
    var above: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var below: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
}

external interface FillerControllerDatasetOptions {
    var fill: dynamic /* Number | String | `T$27` | "start" | "end" | "origin" | "stack" | "shape" | Boolean | ComplexFillTarget */
        get() = definedExternally
        set(value) = definedExternally
}

external var Legend: Plugin<AnyObject>

external interface LegendItem {
    var text: String
    var borderRadius: dynamic /* Number? | BorderRadius? */
        get() = definedExternally
        set(value) = definedExternally
    var datasetIndex: Number
    var fillStyle: dynamic /* String? | CanvasGradient? | CanvasPattern? */
        get() = definedExternally
        set(value) = definedExternally
    var fontColor: dynamic /* String? | CanvasGradient? | CanvasPattern? */
        get() = definedExternally
        set(value) = definedExternally
    var hidden: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var lineCap: String? /* "butt" | "round" | "square" */
        get() = definedExternally
        set(value) = definedExternally
    var lineDash: Array<Number>?
        get() = definedExternally
        set(value) = definedExternally
    var lineDashOffset: Number?
        get() = definedExternally
        set(value) = definedExternally
    var lineJoin: String? /* "bevel" | "miter" | "round" */
        get() = definedExternally
        set(value) = definedExternally
    var lineWidth: Number?
        get() = definedExternally
        set(value) = definedExternally
    var strokeStyle: dynamic /* String? | CanvasGradient? | CanvasPattern? */
        get() = definedExternally
        set(value) = definedExternally
    var pointStyle: dynamic /* "circle" | "cross" | "crossRot" | "dash" | "line" | "rect" | "rectRounded" | "rectRot" | "star" | "triangle" | HTMLImageElement? | HTMLCanvasElement? */
        get() = definedExternally
        set(value) = definedExternally
    var rotation: Number?
        get() = definedExternally
        set(value) = definedExternally
    var textAlign: String? /* "left" | "center" | "right" */
        get() = definedExternally
        set(value) = definedExternally
}

external interface LegendElement : Element, LayoutItem {
    var chart: Chart
    var ctx: CanvasRenderingContext2D
    var legendItems: Array<LegendItem>?
        get() = definedExternally
        set(value) = definedExternally
    override var options: dynamic
}

external interface `T$39` {
    var boxWidth: Number
    var boxHeight: Number
    var boxPadding: Number
    var color: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var font: FontSpec
    var padding: Number
    fun generateLabels(chart: Chart): Array<LegendItem>
    fun filter(item: LegendItem, data: ChartData): Boolean
    fun sort(a: LegendItem, b: LegendItem, data: ChartData): Number
    var pointStyle: dynamic /* "circle" | "cross" | "crossRot" | "dash" | "line" | "rect" | "rectRounded" | "rectRot" | "star" | "triangle" | HTMLImageElement | HTMLCanvasElement */
        get() = definedExternally
        set(value) = definedExternally
    var textAlign: String? /* "left" | "center" | "right" */
        get() = definedExternally
        set(value) = definedExternally
    var usePointStyle: Boolean
}

external interface `T$40` {
    var display: Boolean
    var color: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var font: FontSpec
    var position: String /* "center" | "start" | "end" */
    var padding: dynamic /* Number? | ChartArea? */
        get() = definedExternally
        set(value) = definedExternally
    var text: String
}

external interface LegendOptions {
    var display: Boolean
    var position: dynamic /* "left" | "top" | "right" | "bottom" | "center" | "chartArea" | `T$49` */
        get() = definedExternally
        set(value) = definedExternally
    var align: String /* "start" | "center" | "end" */
    var maxHeight: Number
    var maxWidth: Number
    var fullSize: Boolean
    var reverse: Boolean
    fun onClick(e: ChartEvent, legendItem: LegendItem, legend: LegendElement)
    fun onHover(e: ChartEvent, legendItem: LegendItem, legend: LegendElement)
    fun onLeave(e: ChartEvent, legendItem: LegendItem, legend: LegendElement)
    var labels: `T$39`
    var rtl: Boolean
    var textDirection: String
    var title: `T$40`
}

external var SubTitle: Plugin<AnyObject>

external var Title: Plugin<AnyObject>

external interface TitleOptions {
    var align: String /* "start" | "center" | "end" */
    var display: Boolean
    var position: String /* "top" | "left" | "bottom" | "right" */
    var color: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var font: FontSpec
    var fullSize: Boolean
    var padding: dynamic /* Number | `T$41` */
        get() = definedExternally
        set(value) = definedExternally
    var text: dynamic /* String | Array<String> */
        get() = definedExternally
        set(value) = definedExternally
}

external interface TooltipLabelStyle {
    var borderColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var borderWidth: Number?
        get() = definedExternally
        set(value) = definedExternally
    var borderDash: dynamic /* JsTuple<Number, Number> */
        get() = definedExternally
        set(value) = definedExternally
    var borderDashOffset: Number?
        get() = definedExternally
        set(value) = definedExternally
    var borderRadius: dynamic /* Number? | BorderRadius? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$42` {
    var before: Array<String>
    var lines: Array<String>
    var after: Array<String>
}

external interface `T$43` {
    var pointStyle: dynamic /* "circle" | "cross" | "crossRot" | "dash" | "line" | "rect" | "rectRounded" | "rectRot" | "star" | "triangle" | HTMLImageElement | HTMLCanvasElement */
        get() = definedExternally
        set(value) = definedExternally
    var rotation: Number
}

external interface TooltipModel {
    var dataPoints: Array<TooltipItem>
    var xAlign: String /* "left" | "center" | "right" */
    var yAlign: String /* "top" | "center" | "bottom" */
    var x: Number
    var y: Number
    var width: Number
    var height: Number
    var caretX: Number
    var caretY: Number
    var body: Array<`T$42`>
    var beforeBody: Array<String>
    var afterBody: Array<String>
    var title: Array<String>
    var footer: Array<String>
    var labelColors: Array<TooltipLabelStyle>
    var labelTextColors: Array<dynamic /* String | CanvasGradient | CanvasPattern */>
    var labelPointStyles: Array<`T$43`>
    var opacity: Number
    var options: TooltipOptions
    fun getActiveElements(): Array<ActiveElement>
    fun setActiveElements(active: Array<ActiveDataPoint>, eventPosition: `T$32`)
}

external var Tooltip: Plugin<AnyObject> /* Plugin__0 & `T$45` */

external interface TooltipCallbacks<Model, Item> {
    fun beforeTitle(tooltipItems: Array<Item>): dynamic /* String | Array<String> */
    fun title(tooltipItems: Array<Item>): dynamic /* String | Array<String> */
    fun afterTitle(tooltipItems: Array<Item>): dynamic /* String | Array<String> */
    fun beforeBody(tooltipItems: Array<Item>): dynamic /* String | Array<String> */
    fun afterBody(tooltipItems: Array<Item>): dynamic /* String | Array<String> */
    fun beforeLabel(tooltipItem: Item): dynamic /* String | Array<String> */
    fun label(tooltipItem: Item): dynamic /* String | Array<String> */
    fun afterLabel(tooltipItem: Item): dynamic /* String | Array<String> */
    fun labelColor(tooltipItem: Item): TooltipLabelStyle
    fun labelTextColor(tooltipItem: Item): dynamic /* String | CanvasGradient | CanvasPattern */
    fun labelPointStyle(tooltipItem: Item): `T$43`
    fun beforeFooter(tooltipItems: Array<Item>): dynamic /* String | Array<String> */
    fun footer(tooltipItems: Array<Item>): dynamic /* String | Array<String> */
    fun afterFooter(tooltipItems: Array<Item>): dynamic /* String | Array<String> */
}

external interface `T$46`<Model> {
    var tooltip: Model
}

external interface ExtendedPlugin<O, Model> {
    val beforeTooltipDraw: ((chart: Chart, args: `T$46`<Any?>, options: O) -> dynamic)?
    val afterTooltipDraw: ((chart: Chart, args: `T$46`<Any?>, options: O) -> Unit)?
}

external interface ExtendedPlugin__2<O> : ExtendedPlugin<O, TooltipModel>

external interface ScriptableTooltipContext {
    var chart: UnionToIntersection<Chart>
    var tooltip: UnionToIntersection<TooltipModel>
    var tooltipItems: Array<TooltipItem>
}

external interface TooltipContext {
    var chart: Chart
    var tooltip: TooltipModel
}

external interface TooltipOptions : CoreInteractionOptions {
    var enabled: dynamic /* Boolean | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Boolean? */
        get() = definedExternally
        set(value) = definedExternally

    fun external(args: TooltipContext)
    var position: dynamic /* "average" | "nearest" | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> String */
        get() = definedExternally
        set(value) = definedExternally
    var xAlign: dynamic /* "left" | "center" | "right" | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> String */
        get() = definedExternally
        set(value) = definedExternally
    var yAlign: dynamic /* "top" | "center" | "bottom" | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> String */
        get() = definedExternally
        set(value) = definedExternally
    var itemSort: (a: TooltipItem, b: TooltipItem, data: ChartData) -> Number
    var filter: (e: TooltipItem, index: Number, array: Array<TooltipItem>, data: ChartData) -> Boolean
    var backgroundColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var boxPadding: Number
    var titleColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var titleFont: dynamic /* FontSpec | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> FontSpec? */
        get() = definedExternally
        set(value) = definedExternally
    var titleSpacing: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var titleMarginBottom: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var titleAlign: dynamic /* "left" | "center" | "right" | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> String */
        get() = definedExternally
        set(value) = definedExternally
    var bodySpacing: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var bodyColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var bodyFont: dynamic /* FontSpec | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> FontSpec? */
        get() = definedExternally
        set(value) = definedExternally
    var bodyAlign: dynamic /* "left" | "center" | "right" | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> String */
        get() = definedExternally
        set(value) = definedExternally
    var footerSpacing: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var footerMarginTop: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var footerColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var footerFont: dynamic /* FontSpec | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> FontSpec? */
        get() = definedExternally
        set(value) = definedExternally
    var footerAlign: dynamic /* "left" | "center" | "right" | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> String */
        get() = definedExternally
        set(value) = definedExternally
    var padding: dynamic /* Number | ChartArea | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var caretPadding: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var caretSize: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var cornerRadius: dynamic /* Number | BorderRadius | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var multiKeyBackground: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var displayColors: dynamic /* Boolean | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Boolean? */
        get() = definedExternally
        set(value) = definedExternally
    var boxWidth: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var boxHeight: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var usePointStyle: dynamic /* Boolean | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Boolean? */
        get() = definedExternally
        set(value) = definedExternally
    var borderColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var borderWidth: dynamic /* Number | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var rtl: dynamic /* Boolean | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> Boolean? */
        get() = definedExternally
        set(value) = definedExternally
    var textDirection: dynamic /* String | (ctx: ScriptableTooltipContext<TType>, options: AnyObject) -> String? */
        get() = definedExternally
        set(value) = definedExternally
    var animation: AnimationSpec
    var animations: dynamic
    var callbacks: TooltipCallbacks<AnyObject, AnyObject>
}

external interface TooltipItem {
    var chart: Chart
    var label: String
    var parsed: UnionToIntersection<Any>
    var raw: Any
    var formattedValue: String
    var dataset: UnionToIntersection<DeepPartial<Any> /* DeepPartial<Any> & ChartDatasetProperties<TType, DefaultDataPoint<TType>> */>
    var datasetIndex: Number
    var dataIndex: Number
    var element: Element
}

external interface PluginOptionsByType {
    var decimation: dynamic /* LttbDecimationOptions | MinMaxDecimationOptions */
        get() = definedExternally
        set(value) = definedExternally
    var filler: FillerOptions
    var legend: LegendOptions
    var subtitle: TitleOptions
    var title: TitleOptions
    var tooltip: TooltipOptions
}

external interface PluginChartOptions {
    var plugins: PluginOptionsByType
}

external interface BorderOptions {
    var display: Boolean
    var color: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var width: Number
    var dash: Array<Number>
    var dashOffset: dynamic /* Number | (ctx: ScriptableScaleContext, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface GridLineOptions {
    var display: Boolean
    var circular: Boolean
    var color: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableScaleContext, options: AnyObject) -> dynamic | Array<dynamic /* String | CanvasGradient | CanvasPattern */> */
        get() = definedExternally
        set(value) = definedExternally
    var lineWidth: dynamic /* Number | (ctx: ScriptableScaleContext, options: AnyObject) -> Number? | Array<Number> */
        get() = definedExternally
        set(value) = definedExternally
    var drawOnChartArea: Boolean
    var drawTicks: Boolean
    var tickBorderDash: Array<Number>
    var tickBorderDashOffset: dynamic /* Number | (ctx: ScriptableScaleContext, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var tickColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableScaleContext, options: AnyObject) -> dynamic | Array<dynamic /* String | CanvasGradient | CanvasPattern */> */
        get() = definedExternally
        set(value) = definedExternally
    var tickLength: Number
    var tickWidth: Number
    var offset: Boolean
    var z: Number
}

external interface `T$48` {
    var enabled: Boolean
}

external interface TickOptions {
    var backdropColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableScaleContext, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var backdropPadding: dynamic /* Number | ChartArea */
        get() = definedExternally
        set(value) = definedExternally
    var callback: (self: Scale, tickValue: dynamic /* Number | String */, index: Number, ticks: Array<Tick>) -> dynamic
    var display: Boolean
    var color: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableScaleContext, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var font: dynamic /* FontSpec | (ctx: ScriptableScaleContext, options: AnyObject) -> FontSpec? */
        get() = definedExternally
        set(value) = definedExternally
    var padding: Number
    var showLabelBackdrop: dynamic /* Boolean | (ctx: ScriptableScaleContext, options: AnyObject) -> Boolean? */
        get() = definedExternally
        set(value) = definedExternally
    var textStrokeColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableScaleContext, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var textStrokeWidth: dynamic /* Number | (ctx: ScriptableScaleContext, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var z: Number
    var major: `T$48`
}

external interface `T$50` {
    var display: Boolean
    var align: String /* "start" | "center" | "end" */
    var text: dynamic /* String | Array<String> */
        get() = definedExternally
        set(value) = definedExternally
    var color: dynamic /* String | CanvasGradient | CanvasPattern */
        get() = definedExternally
        set(value) = definedExternally
    var font: FontSpec
    var padding: dynamic /* Number | `T$41` */
        get() = definedExternally
        set(value) = definedExternally
}

external interface CartesianScaleOptions : CoreScaleOptions {
    var bounds: String /* "ticks" | "data" */
    var position: dynamic /* "left" | "top" | "right" | "bottom" | "center" | `T$49` */
        get() = definedExternally
        set(value) = definedExternally
    var stack: String?
        get() = definedExternally
        set(value) = definedExternally
    var stackWeight: Number?
        get() = definedExternally
        set(value) = definedExternally
    var axis: String /* "x" | "y" */
    var min: Number
    var max: Number
    var offset: Boolean
    var grid: GridLineOptions
    var border: BorderOptions
    var title: `T$50`
    var stacked: dynamic /* Boolean? | "single" */
        get() = definedExternally
        set(value) = definedExternally
    var ticks: TickOptions /* TickOptions & `T$51` */
}

external var CategoryScale: ChartComponent /* ChartComponent & `T$52` */

external interface `T$79` {
    var format: NumberFormatOptions
    var maxTicksLimit: Number
    var precision: Number
    var stepSize: Number
    var count: Number
}

external var LinearScale: ChartComponent /* ChartComponent & `T$53` */

external interface `T$81` {
    var format: NumberFormatOptions
}

external var LogarithmicScale: ChartComponent /* ChartComponent & `T$54` */

external interface `T$83` {
    var date: Any
}

external interface `T$84` {
    var parser: dynamic /* String | (v: Any) -> Number */
        get() = definedExternally
        set(value) = definedExternally
    var round: dynamic /* Boolean | "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */
        get() = definedExternally
        set(value) = definedExternally
    var isoWeekday: dynamic /* Boolean | Number */
        get() = definedExternally
        set(value) = definedExternally
    var displayFormats: dynamic
    var tooltipFormat: String
    var unit: dynamic /* Boolean | "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */
        get() = definedExternally
        set(value) = definedExternally
    var stepSize: Number
    var minUnit: String /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */
}

external interface `T$85` {
    var source: String /* "labels" | "auto" | "data" */
}

@Suppress("EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface TimeScale : Scale {
    fun getDataTimestamps(): Array<Number>
    fun getLabelTimestamps(): Array<String>
    fun normalize(values: Array<Number>): Array<Number>

    companion object : ChartComponent by definedExternally
}

external var TimeSeriesScale: ChartComponent /* ChartComponent & `T$56` */

external interface `T$87` {
    var display: Boolean
    var color: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableScaleContext, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var lineWidth: dynamic /* Number | (ctx: ScriptableScaleContext, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
    var borderDash: dynamic /* Array<Number> | (ctx: ScriptableScaleContext, options: AnyObject) -> Array<Number>? */
        get() = definedExternally
        set(value) = definedExternally
    var borderDashOffset: dynamic /* Number | (ctx: ScriptableScaleContext, options: AnyObject) -> Number? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$88` {
    var backdropColor: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableScalePointLabelContext, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var backdropPadding: dynamic /* Number | ChartArea | (ctx: ScriptableScalePointLabelContext, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var borderRadius: dynamic
        get() = definedExternally
        set(value) = definedExternally
    var display: Boolean
    var color: dynamic /* String | CanvasGradient | CanvasPattern | (ctx: ScriptableScalePointLabelContext, options: AnyObject) -> dynamic */
        get() = definedExternally
        set(value) = definedExternally
    var font: dynamic /* FontSpec | (ctx: ScriptableScalePointLabelContext, options: AnyObject) -> FontSpec? */
        get() = definedExternally
        set(value) = definedExternally
    var callback: (label: String, index: Number) -> String
    var centerPointLabels: Boolean
}

external interface `T$57` {
    var x: Number
    var y: Number
    var angle: Number
}

@Suppress("EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface RadialLinearScale : Scale {
    fun setCenterPoint(leftMovement: Number, rightMovement: Number, topMovement: Number, bottomMovement: Number)
    fun getIndexAngle(index: Number): Number
    fun getDistanceFromCenterForValue(value: Number): Number
    fun getValueForDistanceFromCenter(distance: Number): Number
    fun getPointPosition(index: Number, distanceFromCenter: Number): `T$57`
    fun getPointPositionForValue(index: Number, value: Number): `T$57`
    fun getPointLabelPosition(index: Number): ChartArea
    fun getBasePosition(index: Number): `T$57`

    companion object : ChartComponent by definedExternally
}

external interface `T$59` {
    var options: CartesianScaleOptions /* CartesianScaleOptions & `T$80` */
}

external interface `T$60` {
    var options: CartesianScaleOptions /* CartesianScaleOptions & `T$82` */
}

external interface `T$61` {
    var options: CartesianScaleOptions /* CartesianScaleOptions & `T$78` */
}

external interface `T$62` {
    var options: CartesianScaleOptions /* CartesianScaleOptions & `T$86` */
}

external interface CartesianScaleTypeRegistry {
    var linear: `T$59`
    var logarithmic: `T$60`
    var category: `T$61`
    var time: `T$62`
    var timeseries: `T$62`
}

external interface `T$63` {
    var options: CoreScaleOptions /* CoreScaleOptions & `T$89` */
}

external interface RadialScaleTypeRegistry {
    var radialLinear: `T$63`
}

external interface ScaleTypeRegistry : CartesianScaleTypeRegistry, RadialScaleTypeRegistry

external interface CartesianParsedData {
    var x: Number
    var y: Number
    var _stacks: dynamic
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$66` {
    var barStart: Number
    var barEnd: Number
    var start: Number
    var end: Number
    var min: Number
    var max: Number
}

external interface BarParsedData : CartesianParsedData {
    var _custom: `T$66`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BubbleParsedData : CartesianParsedData {
    var _custom: Number
}

external interface RadialParsedData {
    var r: Number
}

external interface `T$67` {
    var chartOptions: BarControllerChartOptions
    var datasetOptions: BarControllerDatasetOptions
    var defaultDataPoint: Number
    var metaExtensions: Any
    var parsedDataType: BarParsedData
    var scales: String /* "linear" | "logarithmic" | "category" | "time" | "timeseries" */
}

external interface `T$68` {
    var chartOptions: LineControllerChartOptions
    var datasetOptions: LineControllerDatasetOptions /* LineControllerDatasetOptions & FillerControllerDatasetOptions */
    var defaultDataPoint: dynamic /* ScatterDataPoint? | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var metaExtensions: Any
    var parsedDataType: CartesianParsedData
    var scales: String /* "linear" | "logarithmic" | "category" | "time" | "timeseries" */
}

external interface `T$69` {
    var chartOptions: ScatterControllerChartOptions
    var datasetOptions: ScatterControllerDatasetOptions
    var defaultDataPoint: dynamic /* ScatterDataPoint? | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var metaExtensions: Any
    var parsedDataType: CartesianParsedData
    var scales: String /* "linear" | "logarithmic" | "category" | "time" | "timeseries" */
}

external interface `T$70` {
    var chartOptions: Any
    var datasetOptions: BubbleControllerDatasetOptions
    var defaultDataPoint: BubbleDataPoint
    var metaExtensions: Any
    var parsedDataType: BubbleParsedData
    var scales: String /* "linear" | "logarithmic" | "category" | "time" | "timeseries" */
}

external interface `T$71` {
    var chartOptions: PieControllerChartOptions
    var datasetOptions: PieControllerDatasetOptions
    var defaultDataPoint: PieDataPoint
    var metaExtensions: PieMetaExtensions
    var parsedDataType: Number
    var scales: String /* "linear" | "logarithmic" | "category" | "time" | "timeseries" */
}

external interface `T$72` {
    var chartOptions: DoughnutControllerChartOptions
    var datasetOptions: DoughnutControllerDatasetOptions
    var defaultDataPoint: DoughnutDataPoint
    var metaExtensions: DoughnutMetaExtensions
    var parsedDataType: Number
    var scales: String /* "linear" | "logarithmic" | "category" | "time" | "timeseries" */
}

external interface `T$73` {
    var chartOptions: PolarAreaControllerChartOptions
    var datasetOptions: PolarAreaControllerDatasetOptions
    var defaultDataPoint: Number
    var metaExtensions: Any
    var parsedDataType: RadialParsedData
    var scales: String /* "radialLinear" */
}

external interface `T$74` {
    var chartOptions: RadarControllerChartOptions
    var datasetOptions: RadarControllerDatasetOptions /* RadarControllerDatasetOptions & FillerControllerDatasetOptions */
    var defaultDataPoint: Number?
    var metaExtensions: Any
    var parsedDataType: RadialParsedData
    var scales: String /* "radialLinear" */
}

external interface ChartTypeRegistry {
    var bar: `T$67`
    var line: `T$68`
    var scatter: `T$69`
    var bubble: `T$70`
    var pie: `T$71`
    var doughnut: `T$72`
    var polarArea: `T$73`
    var radar: `T$74`
}

external interface ScaleChartOptions {
    var scales: dynamic
}

external interface ChartDatasetProperties {
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var data: dynamic
}

external interface ChartData {
    var labels: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var datasets: Array<DeepPartial<Any> /* DeepPartial<Any> & ChartDatasetProperties<TType, TData> */>
}

external interface ChartConfiguration {
    var type: String
    var data: ChartData?
        get() = definedExternally
        set(value) = definedExternally
    var options: ChartOptions?
        get() = definedExternally
        set(value) = definedExternally
    var plugins: Array<Plugin<String>>?
        get() = definedExternally
        set(value) = definedExternally
}
