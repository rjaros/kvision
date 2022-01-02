@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.geo

import externals.leaflet.geometry.Bounds
import externals.leaflet.geometry.Point


external class Projection {
    var bounds: Bounds

    fun project(latlng: LatLng): Point
    fun unproject(point: Point): LatLng

    companion object {
        val LonLat: Projection
        val Mercator: Projection
        val SphericalMercator: Projection
    }
}
