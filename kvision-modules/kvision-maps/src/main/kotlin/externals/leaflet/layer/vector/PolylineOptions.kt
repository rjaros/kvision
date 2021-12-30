@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

external interface PolylineOptions : PathOptions {
    /**
     * How much to simplify the polyline on each zoom level. More means better performance and
     * smoother look, and less means more accurate representation.
     */
    var smoothFactor: Number?
    /** Disable polyline clipping. */
    var noClip: Boolean?
}
