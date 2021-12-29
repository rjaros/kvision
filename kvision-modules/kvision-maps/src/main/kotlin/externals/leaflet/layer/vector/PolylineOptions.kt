 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer.vector

import externals.leaflet.layer.vector.PathOptions

external interface PolylineOptions : PathOptions {
    var smoothFactor: Number?
    var noClip: Boolean?
}
