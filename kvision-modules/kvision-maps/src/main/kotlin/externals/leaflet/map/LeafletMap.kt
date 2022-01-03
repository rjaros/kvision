@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.map

import externals.leaflet.control.Attribution
import externals.leaflet.control.Control
import externals.leaflet.control.Zoom
import externals.leaflet.control.Zoom.ZoomOptions
import externals.leaflet.core.Handler
import externals.leaflet.events.Evented
import externals.leaflet.geo.CRS
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.geometry.Bounds
import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import externals.leaflet.layer.overlay.Popup
import externals.leaflet.layer.overlay.Popup.PopupOptions
import externals.leaflet.layer.overlay.Tooltip
import externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import externals.leaflet.layer.vector.Path
import externals.leaflet.layer.vector.Renderer
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent

@JsName("Map")
// rename the implemented class to `LeafletMap` to avoid name confusion with Kotlin's Map<K, V>
open external class LeafletMap : Evented {

    constructor(element: String, options: MapOptions = definedExternally)
    constructor(element: HTMLElement, options: MapOptions = definedExternally)

    open var attributionControl: Attribution
    open var boxZoom: Handler
    open var doubleClickZoom: Handler
    open var dragging: Handler
    open var keyboard: Handler
    open var scrollWheelZoom: Handler
    open var tap: Handler?
    open var touchZoom: Handler
    open var zoomControl: Zoom
    open var options: MapOptions

    open fun getRenderer(layer: Path): Renderer

    open fun addControl(control: Control): LeafletMap
    open fun removeControl(control: Control): LeafletMap

    open fun addLayer(layer: Layer): LeafletMap
    open fun removeLayer(layer: Layer): LeafletMap
    open fun hasLayer(layer: Layer): Boolean
    open fun eachLayer(fn: (layer: Layer) -> Unit, context: Any = definedExternally): LeafletMap

    open fun openPopup(popup: Popup): LeafletMap
    open fun openPopup(
        content: String,
        latlng: LatLng,
        options: PopupOptions = definedExternally
    ): LeafletMap

    open fun openPopup(
        content: HTMLElement,
        latlng: LatLng,
        options: PopupOptions = definedExternally
    ): LeafletMap

    open fun closePopup(popup: Popup = definedExternally): LeafletMap

    open fun openTooltip(tooltip: Tooltip): LeafletMap
    open fun openTooltip(
        content: String,
        latlng: LatLng,
        options: TooltipOptions = definedExternally
    ): LeafletMap

    open fun openTooltip(
        content: HTMLElement,
        latlng: LatLng,
        options: TooltipOptions = definedExternally
    ): LeafletMap

    open fun closeTooltip(tooltip: Tooltip = definedExternally): LeafletMap

    open fun setView(
        center: LatLng,
        zoom: Number = definedExternally,
        options: ZoomPanOptions = definedExternally
    ): LeafletMap

    open fun setZoom(zoom: Number, options: ZoomPanOptions = definedExternally): LeafletMap
    open fun zoomIn(
        delta: Number = definedExternally,
        options: ZoomOptions = definedExternally
    ): LeafletMap

    open fun zoomOut(
        delta: Number = definedExternally,
        options: ZoomOptions = definedExternally
    ): LeafletMap

    open fun setZoomAround(
        position: Point,
        zoom: Number,
        options: ZoomOptions = definedExternally
    ): LeafletMap

    open fun setZoomAround(
        position: LatLng,
        zoom: Number,
        options: ZoomOptions = definedExternally
    ): LeafletMap

    open fun fitBounds(
        bounds: LatLngBounds,
        options: FitBoundsOptions = definedExternally
    ): LeafletMap

    open fun fitWorld(options: FitBoundsOptions = definedExternally): LeafletMap

    open fun setMaxBounds(bounds: LatLngBounds): LeafletMap
    open fun setMinZoom(zoom: Number): LeafletMap
    open fun setMaxZoom(zoom: Number): LeafletMap

    open fun panTo(latlng: LatLng, options: PanOptions = definedExternally): LeafletMap
    open fun panBy(offset: Point, options: PanOptions = definedExternally): LeafletMap
    open fun panInside(latLng: LatLng, options: PanInsideOptions = definedExternally): LeafletMap
    open fun panInsideBounds(
        bounds: LatLngBounds,
        options: PanOptions = definedExternally
    ): LeafletMap


    /**
     * Checks if the map container size changed and updates the map if so — call it after you've
     * changed the map size dynamically, also animating pan by default.
     */
    open fun invalidateSize(options: Boolean = definedExternally): LeafletMap
    /**
     * Checks if the map container size changed and updates the map if so — call it after you've
     * changed the map size dynamically, also animating pan by default. If
     * [InvalidateSizeOptions.pan] is false, panning will not occur. If
     * [InvalidateSizeOptions.debounceMoveend] is true, it will delay `moveend event` so that it
     * doesn't happen often even if the method is called many times in a row.
     */
    open fun invalidateSize(options: ZoomPanOptions = definedExternally): LeafletMap

    open fun stop(): LeafletMap

    open fun flyTo(
        latlng: LatLng,
        zoom: Number = definedExternally,
        options: ZoomPanOptions = definedExternally
    ): LeafletMap

    open fun flyToBounds(
        bounds: LatLngBounds,
        options: FitBoundsOptions = definedExternally
    ): LeafletMap

    open fun addHandler(name: String, HandlerClass: Any): LeafletMap
    open fun remove(): LeafletMap
    open fun createPane(name: String, container: HTMLElement = definedExternally): HTMLElement
    open fun getPane(pane: String): HTMLElement?
    open fun getPane(pane: HTMLElement): HTMLElement?
    open fun getPanes(): MapPanes
    open fun getContainer(): HTMLElement
    open fun whenReady(fn: () -> Unit, context: Any = definedExternally): LeafletMap
    open fun getCenter(): LatLng
    open fun getZoom(): Number
    open fun getBounds(): LatLngBounds
    open fun getMinZoom(): Number
    open fun getMaxZoom(): Number
    open fun getBoundsZoom(
        bounds: LatLngBounds,
        inside: Boolean = definedExternally,
        padding: Point = definedExternally
    ): Number

    open fun getSize(): Point
    open fun getPixelBounds(): Bounds
    open fun getPixelOrigin(): Point
    open fun getPixelWorldBounds(zoom: Number = definedExternally): Bounds
    open fun getZoomScale(toZoom: Number, fromZoom: Number = definedExternally): Number
    open fun getScaleZoom(scale: Number, fromZoom: Number = definedExternally): Number
    open fun project(latlng: LatLng, zoom: Number = definedExternally): Point
    open fun unproject(point: Point, zoom: Number = definedExternally): LatLng
    open fun layerPointToLatLng(point: Point): LatLng
    open fun latLngToLayerPoint(latlng: LatLng): Point
    open fun wrapLatLng(latlng: LatLng): LatLng
    open fun wrapLatLngBounds(bounds: LatLngBounds): LatLngBounds
    open fun distance(latlng1: LatLng, latlng2: LatLng): Number
    open fun containerPointToLayerPoint(point: Point): Point
    open fun containerPointToLatLng(point: Point): LatLng
    open fun layerPointToContainerPoint(point: Point): Point
    open fun latLngToContainerPoint(latlng: LatLng): Point
    open fun mouseEventToContainerPoint(ev: MouseEvent): Point
    open fun mouseEventToLayerPoint(ev: MouseEvent): Point
    open fun mouseEventToLatLng(ev: MouseEvent): LatLng
    open fun locate(options: LocateOptions = definedExternally): LeafletMap
    open fun stopLocate(): LeafletMap


    /** Constructor parameters for [LeafletMap] */
    interface MapOptions {
        var preferCanvas: Boolean?
        var attributionControl: Boolean?
        var zoomControl: Boolean?
        var closePopupOnClick: Boolean?
        var zoomSnap: Number?
        var zoomDelta: Number?
        var trackResize: Boolean?
        var boxZoom: Boolean?
        var doubleClickZoom: dynamic /* Boolean? | "center" */
        var dragging: Boolean?
        var crs: CRS?
        var center: LatLng?
        var zoom: Number?
        var minZoom: Number?
        var maxZoom: Number?
        var layers: Array<Layer>?
        var maxBounds: LatLngBounds /* LatLngBounds? | LatLngBoundsLiteral? */
        var renderer: Renderer?
        var fadeAnimation: Boolean?
        var markerZoomAnimation: Boolean?
        var transform3DLimit: Number?
        var zoomAnimation: Boolean?
        var zoomAnimationThreshold: Number?
        var inertia: Boolean?
        var inertiaDeceleration: Number?
        var inertiaMaxSpeed: Number?
        var easeLinearity: Number?
        var worldCopyJump: Boolean?
        var maxBoundsViscosity: Number?
        var keyboard: Boolean?
        var keyboardPanDelta: Number?
        var scrollWheelZoom: dynamic /* Boolean? | "center" */
        var wheelDebounceTime: Number?
        var wheelPxPerZoomLevel: Number?
        var tap: Boolean?
        var tapTolerance: Number?
        var touchZoom: dynamic /* Boolean? | "center" */
        var bounceAtZoomLimits: Boolean?
    }

    /**
     * @see panTo
     * @see panBy
     * @see panInsideBounds
     */
      interface PanOptions {
        var animate: Boolean?
        var duration: Number?
        var easeLinearity: Number?
        var noMoveStart: Boolean?
    }

    /**
     * @see fitBounds
     * @see fitWorld
     * @see flyToBounds
     */
    interface FitBoundsOptions : ZoomOptions, PanOptions {
        var paddingTopLeft: Point
        var paddingBottomRight: Point
        var padding: Point
        var maxZoom: Number?
        override var animate: Boolean?
    }

    /** @see invalidateSize */
    interface InvalidateSizeOptions : ZoomPanOptions {
        var debounceMoveend: Boolean?
        var pan: Boolean?
    }

    /**
     * @see flyTo
     * @see setZoom
     * @see setView
     * @see invalidateSize
     */
    interface ZoomPanOptions : ZoomOptions, PanOptions

    /** @see locate */
    interface LocateOptions {
        var watch: Boolean?
        var setView: Boolean?
        var maxZoom: Number?
        var timeout: Number?
        var maximumAge: Number?
        var enableHighAccuracy: Boolean?
    }

    /** @see panInside */
    interface PanInsideOptions {
        var paddingTopLeft: Point
        var paddingBottomRight: Point
        var padding: Point
    }

}
