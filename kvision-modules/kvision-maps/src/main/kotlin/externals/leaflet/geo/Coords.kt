@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.geo

import externals.leaflet.geometry.Point


open external class Coords : Point {
    var z: Number
}
