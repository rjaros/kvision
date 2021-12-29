@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.map

import externals.leaflet.geo.CRS
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.Layer
import externals.leaflet.layer.vector.Renderer

external interface MapOptions {
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
    var center: LatLng /* LatLng? | LatLngLiteral? | JsTuple<Number, Number> */
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
