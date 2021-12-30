@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.geo

import externals.leaflet.geometry.Bounds
import externals.leaflet.geometry.Point


external class Projection {
    var bounds: Bounds

    fun project(latlng: LatLng): Point
    //    fun project(latlng: LatLngLiteral): Point
    fun unproject(point: Point): LatLng
//    fun unproject(point: Any /* JsTuple<Number, Number> */): LatLng

    companion object {
        val LonLat: Projection
        val Mercator: Projection
        val SphericalMercator: Projection
    }
}
