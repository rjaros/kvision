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
import io.kvision.maps.externals.leaflet.geometry.Transformation

/**
 * Object that defines coordinate reference systems for projecting geographical points into pixel
 * coordinates and back.
 */
external interface CRS {
    /** Standard code name of the CRS passed into WMS services (e.g. `EPSG:3857`). */
    var code: String?

    /** The projection the CRS uses to turn geographical coordinates into projected ones. */
    var projection: Projection

    /** The transformation the CRS uses to turn projected coordinates into pixels at zoom 0. */
    var transformation: Transformation?

    /** The longitude wrapping range. */
    var wrapLng: Pair<Number, Number>?

    /** The latitude wrapping range. */
    var wrapLat: Pair<Number, Number>?

    /** If true, the coordinate space is unbounded in both axes. */
    var infinite: Boolean?

    fun latLngToPoint(latlng: LatLng, zoom: Int): Point
    fun pointToLatLng(point: Point, zoom: Int): LatLng
    fun project(latlng: LatLng): Point
    fun unproject(point: Point): LatLng
    fun scale(zoom: Int): Double
    fun zoom(scale: Double): Double
    fun getProjectedBounds(zoom: Int): Bounds?
    fun distance(latlng1: LatLng, latlng2: LatLng): Number
    fun wrapLatLng(latlng: LatLng): LatLng
    fun wrapLatLngBounds(bounds: Bounds): LatLngBounds
}

/** Rarely used by some commercial tile providers. Uses Elliptical Mercator projection. */
external val EPSG3395: CRS

/** The most common CRS for online maps. Uses Spherical Mercator projection. */
external val EPSG3857: CRS

/** The same CRS as [EPSG3857], with the legacy `EPSG:900913` code. */
external val EPSG900913: CRS

/** A common CRS among GIS enthusiasts. Uses simple equirectangular projection. */
external val EPSG4326: CRS

/** Serves as the base for CRS that cover the earth. */
external val EarthCRS: CRS

/** A simple CRS that maps longitude and latitude into x and y directly. */
external val SimpleCRS: CRS
