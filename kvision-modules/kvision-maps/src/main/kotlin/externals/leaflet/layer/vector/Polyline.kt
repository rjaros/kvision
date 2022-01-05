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

package externals.leaflet.layer.vector

import externals.geojson.Feature
import externals.geojson.GeoJsonCoordinatedGeometry
import externals.geojson.GeoJsonSingleOrMultiLineString
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.geometry.Point

/**
 * A class for drawing polyline overlays on a map. Extends [Path].
 *
 * See [`https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/layer/vector/Polyline.js`](https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/layer/vector/Polyline.js)
 *
 * @param T [externals.geojson.LineString] or [externals.geojson.MultiLineString]
 */
open external class Polyline<T : GeoJsonSingleOrMultiLineString> : Path<Polyline.PolylineOptions> {

    /** Create a polyline from an array of [LatLng] points */
    constructor(
        latlngs: Array<LatLng>,
        options: PolylineOptions = definedExternally
    )

    /** @param[latlngs] pass a multi-dimensional array to represent a `MultiPolyline` shape */
    constructor(
        latlngs: Array<Array<LatLng>>,
        options: PolylineOptions = definedExternally
    )

    open var feature: Feature<GeoJsonCoordinatedGeometry>?

    open fun toGeoJSON(precision: Number = definedExternally): Feature<T>

    open fun getLatLngs(): dynamic /* Array<LatLng> | Array<Array<LatLng>> | Array<Array<Array<LatLng>>> */

    open fun setLatLngs(latlngs: Array<LatLng>): Polyline<T> /* this */
    open fun setLatLngs(latlngs: Array<Array<LatLng>>): Polyline<T> /* this */
    open fun setLatLngs(latlngs: Array<Array<Array<LatLng>>>): Polyline<T> /* this */

    open fun addLatLng(
        latlng: LatLng,
        latlngs: Array<LatLng> = definedExternally
    ): Polyline<T> /* this */

    open fun addLatLng(
        latlng: Array<LatLng>,
        latlngs: Array<LatLng> = definedExternally
    ): Polyline<T> /* this */

    open fun isEmpty(): Boolean
    open fun getCenter(): LatLng
    open fun getBounds(): LatLngBounds

    open fun closestLayerPoint(p: Point): Point

    interface PolylineOptions : PathOptions {
        /**
         * How much to simplify the polyline on each zoom level. More means better performance and
         * smoother look, and less means more accurate representation.
         */
        var smoothFactor: Number?
        /** Disable polyline clipping. */
        var noClip: Boolean?
    }

}
