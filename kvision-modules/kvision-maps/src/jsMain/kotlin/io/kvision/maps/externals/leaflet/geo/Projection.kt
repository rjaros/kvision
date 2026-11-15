@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

package io.kvision.maps.externals.leaflet.geo

import io.kvision.maps.externals.leaflet.geometry.Bounds
import io.kvision.maps.externals.leaflet.geometry.Point

/** An object with methods for projecting geographical coordinates onto a flat surface and back. */
external interface Projection {
    /** The bounds where the projection is valid. */
    var bounds: Bounds

    fun project(latlng: LatLng): Point
    fun unproject(point: Point): LatLng

    /** Earth radius used by the projection. */
    var R: Double?

    /** Minor earth radius used by the elliptical Mercator projection. */
    var R_MINOR: Double?

    /** Maximum latitude supported by the spherical Mercator projection. */
    var MAX_LATITUDE: Double?
}

/** Equirectangular projection used by [EPSG4326] and [SimpleCRS]. */
external val LonLatProjection: Projection

/** Elliptical Mercator projection used by [EPSG3395]. */
external val MercatorProjection: Projection

/** Spherical Mercator projection used by [EPSG3857]. */
external val SphericalMercatorProjection: Projection

