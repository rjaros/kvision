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

package io.kvision.maps.externals.leaflet.layer.vector

import io.kvision.maps.externals.geojson.Feature
import io.kvision.maps.externals.geojson.Point
import io.kvision.maps.externals.leaflet.geo.LatLng

open external class CircleMarker(
    latlng: LatLng,
    options: CircleMarkerOptions = definedExternally,
) : Path<CircleMarker.CircleMarkerOptions> {

    open var feature: Feature<Point>?

    open fun toGeoJSON(precision: Number = definedExternally): Feature<Point>
    open fun setLatLng(latLng: LatLng): CircleMarker /* this */
    open fun getLatLng(): LatLng
    open fun setRadius(radius: Number): CircleMarker /* this */
    open fun getRadius(): Number

    interface CircleMarkerOptions : PathOptions {
        var radius: Number?
    }

}
