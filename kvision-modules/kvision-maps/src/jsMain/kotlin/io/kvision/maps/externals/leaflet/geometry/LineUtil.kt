@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
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

package io.kvision.maps.externals.leaflet.geometry

import io.kvision.maps.externals.leaflet.geo.LatLng

/**
 * Utility functions for polyline points processing.
 */
external object LineUtil {
    /** Reduces the number of points in a polyline while retaining its shape. */
    fun simplify(points: Array<Point>, tolerance: Double): Array<Point>

    /** Returns the distance between `p` and the segment `p1`-`p2`. */
    fun pointToSegmentDistance(p: Point, p1: Point, p2: Point): Double

    /** Returns the closest point on the segment `p1`-`p2` to `p`. */
    fun closestPointOnSegment(p: Point, p1: Point, p2: Point): Point

    /** Clips the segment `a`-`b` by rectangular bounds. */
    fun clipSegment(
        a: Point,
        b: Point,
        bounds: Bounds,
        useLastCode: Boolean = definedExternally,
        round: Boolean = definedExternally
    ): dynamic

    fun _getEdgeIntersection(
        a: Point,
        b: Point,
        code: Int,
        bounds: Bounds,
        round: Boolean = definedExternally
    ): Point

    fun _getBitCode(p: Point, bounds: Bounds): Int

    fun _sqClosestPointOnSegment(p: Point, p1: Point, p2: Point, sqDist: Boolean): dynamic

    /** Returns `true` if `latlngs` is a flat array. */
    fun isFlat(latlngs: Array<dynamic>): Boolean

    /** Returns the center of the passed LatLngs. */
    fun polylineCenter(latlngs: Array<dynamic>, crs: dynamic): LatLng

    fun _projectRing(latlngs: Array<dynamic>, crs: dynamic): Array<Point>
}
