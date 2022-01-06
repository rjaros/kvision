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

package io.kvision.maps.externals.leaflet.layer.vector

import io.kvision.maps.externals.geojson.GeoJsonSingleOrMultiLineString
import io.kvision.maps.externals.geojson.GeoJsonSingleOrMultiPolygon
import io.kvision.maps.externals.leaflet.geo.LatLng

/**
 * A class for drawing polygon overlays on a map. Extends [Polyline].
 *
 * Note that points you pass when creating a polygon shouldn't have an additional last point equal
 * to the first one — it's better to filter out such points.
 *
 * @param T [externals.geojson.Polygon] or [externals.geojson.MultiPolygon]
 */
open external class Polygon<T : GeoJsonSingleOrMultiPolygon> :
    Polyline<GeoJsonSingleOrMultiLineString> {

    /** Create a polygon from an array of LatLng points. */
    constructor(
        latlngs: Array<LatLng>,
        options: PolylineOptions = definedExternally
    )

    /**
     * @param[latlngs] An array of arrays of [LatLng], with the first array representing the
     * outer shape and the other arrays representing holes in the outer shape.
     */
    constructor(
        latlngs: Array<Array<LatLng>>,
        options: PolylineOptions = definedExternally
    )

    /**
     * @param[latlngs] a multi-dimensional array to represent a MultiPolygon shape.
     */
    constructor(
        latlngs: Array<Array<Array<LatLng>>>,
        options: PolylineOptions = definedExternally
    )

}
