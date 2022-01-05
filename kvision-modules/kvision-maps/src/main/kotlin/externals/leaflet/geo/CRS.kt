/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.geo

import externals.leaflet.core.Class
import externals.leaflet.geometry.Bounds
import externals.leaflet.geometry.Point

/**
 * ### Coordinate Reference System
 *
 * Object that defines coordinate reference systems for projecting geographical points into pixel
 * (screen) coordinates and back (and to coordinates in other units for
 * [WMS][externals.leaflet.layer.tile.WMS] services).
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
abstract external class CRS {
    var code: String?

    /**
     * A pair of two numbers defining whether the longitude (horizontal) coordinate axis wraps
     * around a given range and how. Defaults to `[-180, 180]` in most geographical CRSs.
     *
     * If undefined, the longitude axis does not wrap around.
     * @see [wrapLat]
     */
    var wrapLng: Pair<Number, Number>? /* JsTuple<Number, Number> */
    /**
     * Like [wrapLng], but for the latitude (vertical) axis.
     * @see [wrapLng]
     */
    var wrapLat: Pair<Number, Number>? /* JsTuple<Number, Number> */
    /** If true, the coordinate space will be unbounded (infinite in both axes) */
    var infinite: Boolean?

    fun latLngToPoint(latlng: LatLng, zoom: Number): Point
    fun pointToLatLng(point: Point, zoom: Number): LatLng
    fun project(latlng: LatLng): Point
    fun unproject(point: Point): LatLng
    fun scale(zoom: Number): Number
    fun zoom(scale: Number): Number
    fun getProjectedBounds(zoom: Number): Bounds
    fun distance(latlng1: LatLng, latlng2: LatLng): Number
    fun wrapLatLng(latlng: LatLng): LatLng

    companion object {
        /**
         * Rarely used by some commercial tile providers. Uses Elliptical Mercator projection.
         *
         * See: [`https://leafletjs.com/reference.html#crs-l-crs-epsg3395`](https://leafletjs.com/reference.html#crs-l-crs-epsg3395)
         */
        val EPSG3395: CRS
        /**
         * The most common [CRS] for online maps, used by almost all free and commercial tile
         * providers. Uses Spherical Mercator projection. Set in by default in Map's
         * [`crs` option][externals.leaflet.map.LeafletMap.LeafletMapOptions.crs].
         */
        val EPSG3857: CRS
        /** A common CRS among GIS enthusiasts. Uses simple equirectangular projection. */
        val EPSG4326: CRS
        /**
         * Serves as the base for [CRS] that are global such that they cover the earth. Can only be
         * used as the base for other CRS and cannot be used directly, since it does not have a
         * [code], `projection` or `transformation`.
         *
         * [CRS.distance] returns meters.
         */
        val Earth: CRS
        /**
         * A simple CRS that maps longitude and latitude into x and y directly. May be used for
         * maps of flat surfaces (e.g. game maps). Note that the y-axis should still be inverted
         * (going from bottom to top). `distance()` returns simple euclidean distance.
         *
         * See: [`https://leafletjs.com/reference.html#crs-l-crs-simple`](https://leafletjs.com/reference.html#crs-l-crs-simple)
         */
        val Simple: CRS
        /**
         * Object that defines coordinate reference systems for projecting geographical points
         * into pixel (screen) coordinates and back (and to coordinates in other units for
         * [WMS][externals.leaflet.layer.tile.WMS] services).
         */
        val Base: CRS

        // val EPSG900913: CRS // defined in DefinitelyTyped, but undocumented
    }
}
