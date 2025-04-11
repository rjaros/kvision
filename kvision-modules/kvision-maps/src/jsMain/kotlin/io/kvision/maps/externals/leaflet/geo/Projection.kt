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

@file:JsModule("leaflet/dist/leaflet-src.esm.js")

package io.kvision.maps.externals.leaflet.geo

import io.kvision.maps.externals.leaflet.geometry.Bounds
import io.kvision.maps.externals.leaflet.geometry.Point

/**
 * An object with methods for projecting geographical coordinates of the world onto a flat surface
 * (and back).
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#projection
 */
external class Projection {

    /** The bounds (specified in CRS units) where the projection is valid */
    var bounds: Bounds

    /**
     * Projects geographical coordinates into a 2D point. Only accepts actual [LatLng] instances,
     * not arrays.
     */
    fun project(latlng: LatLng): Point

    /**
     * The inverse of [project]. Projects a 2D point into a geographical location. Only accepts
     * actual [Point] instances, not arrays.
     *
     * Note that the projection instances do not inherit from Leaflet's
     * [Class][io.kvision.maps.externals.leaflet.core.Class] object, and can't be instantiated.
     * Also, new classes can't inherit from them, and methods can't be added to them with the include
     * function.
     */
    fun unproject(point: Point): LatLng

    companion object {
        /**
         * Equirectangular, or Plate Carree projection — the most simple projection, mostly used by
         * GIS enthusiasts. Directly maps `x` as longitude, and `y` as latitude. Also suitable for
         * flat worlds, e.g. game maps. Used by the
         * [EPSG3857][io.kvision.maps.externals.leaflet.geo.CRS.EPSG3857] and
         * [Simple][io.kvision.maps.externals.leaflet.geo.CRS.Simple] CRS.
         */
        val LonLat: Projection
        /**
         * Elliptical Mercator projection — more complex than Spherical Mercator. Assumes that
         * Earth is an ellipsoid. Used by the
         * [EPSG3857][io.kvision.maps.externals.leaflet.geo.CRS.EPSG3857] CRS.
         */
        val Mercator: Projection
        /**
         * Spherical Mercator projection — the most common projection for online maps, used by
         * almost all free and commercial tile providers. Assumes that Earth is a sphere.
         * Used by the [EPSG3857][io.kvision.maps.externals.leaflet.geo.CRS.EPSG3857] CRS.
         */
        val SphericalMercator: Projection
    }
}
