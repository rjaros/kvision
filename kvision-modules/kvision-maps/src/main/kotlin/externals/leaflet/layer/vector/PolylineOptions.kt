@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

external interface PolylineOptions : PathOptions {
    var smoothFactor: Number?
    var noClip: Boolean?
}
