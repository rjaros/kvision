@file:JsModule("leaflet")
@file:JsNonModule


package externals.leaflet.layer.tile


external interface GridLayerOptions {
    var tileSize: dynamic /* Number? | Point? */
    var opacity: Number?
    var updateWhenIdle: Boolean?
    var updateWhenZooming: Boolean?
    var updateInterval: Number?
    var attribution: String?
    var zIndex: Number?
    var bounds: dynamic /* LatLngBounds? | LatLngBoundsLiteral? */
    var minZoom: Number?
    var maxZoom: Number?
    var maxNativeZoom: Number?
    var minNativeZoom: Number?
    var noWrap: Boolean?
    var pane: String?
    var className: String?
    var keepBuffer: Number?
}
