@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.geo

import externals.leaflet.geometry.Bounds
import externals.leaflet.geometry.Point

/**
 * Object that defines coordinate reference systems for projecting geographical points into pixel
 * (screen) coordinates and back (and to coordinates in other units for WMS services).
 *
 * Leaflet defines the most usual CRSs by default. If you want to use a CRS not defined by default,
 * take a look at the [Proj4Leaflet plugin](https://github.com/kartena/Proj4Leaflet).
 *
 * Note that the CRS instances do not inherit from Leaflet's [Class] object, and can't be
 * instantiated. Also, new classes can't inherit from them, and methods can't be added to them
 * with the `include` function.
 *
 * See: [`https://leafletjs.com/reference.html#crs-l-crs-base`](https://leafletjs.com/reference.html#crs-l-crs-base)
 */
//@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external class CRS {
    fun latLngToPoint(latlng: LatLng, zoom: Number): Point
    fun pointToLatLng(point: Point, zoom: Number): LatLng
    fun project(latlng: LatLng): Point
    fun unproject(point: Point): LatLng
    fun scale(zoom: Number): Number
    fun zoom(scale: Number): Number
    fun getProjectedBounds(zoom: Number): Bounds
    fun distance(latlng1: LatLng, latlng2: LatLng): Number
    fun wrapLatLng(latlng: LatLng): LatLng
    var code: String?
    var wrapLng: dynamic /* JsTuple<Number, Number> */
    var wrapLat: dynamic /* JsTuple<Number, Number> */
    var infinite: Boolean?

    companion object {
        /**
         * Rarely used by some commercial tile providers. Uses Elliptical Mercator projection.
         *
         * See: [`https://leafletjs.com/reference.html#crs-l-crs-epsg3395`](https://leafletjs.com/reference.html#crs-l-crs-epsg3395)
         */
        val EPSG3395: CRS
        val EPSG3857: CRS
        val EPSG4326: CRS
        val EPSG900913: CRS
        val Earth: CRS
        /**
         * A simple CRS that maps longitude and latitude into x and y directly. May be used for
         * maps of flat surfaces (e.g. game maps). Note that the y-axis should still be inverted
         * (going from bottom to top). `distance()` returns simple euclidean distance.
         *
         * See: [`https://leafletjs.com/reference.html#crs-l-crs-simple`](https://leafletjs.com/reference.html#crs-l-crs-simple)
         */
        val Simple: CRS
    }
}
