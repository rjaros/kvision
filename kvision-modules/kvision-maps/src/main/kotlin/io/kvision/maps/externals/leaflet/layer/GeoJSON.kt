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

package io.kvision.maps.externals.leaflet.layer

import io.kvision.maps.externals.geojson.Feature
import io.kvision.maps.externals.geojson.GeoJsonGeometry
import io.kvision.maps.externals.geojson.GeoJsonObject
import io.kvision.maps.externals.geojson.Point
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.layer.vector.Path.PathOptions

/**
 * Represents a GeoJSON object or an array of GeoJSON objects.
 * Allows you to parse GeoJSON data and display it on the map.
 */
open external class GeoJSON(
    geojson: GeoJsonObject = definedExternally,
    options: GeoJSONOptions = definedExternally
) : FeatureGroup {

    open fun addData(data: GeoJsonObject): GeoJSON /* this */
    open fun resetStyle(layer: Layer<*> = definedExternally): GeoJSON /* this */
    override fun setStyle(style: PathOptions): GeoJSON /* this */
    fun setStyle(style: StyleFunction): GeoJSON /* this */

    companion object {
        fun geometryToLayer(
            featureData: Feature<GeoJsonGeometry>,
            options: GeoJSONOptions = definedExternally
        ): Layer<*>

        fun coordsToLatLng(coords: Any /* JsTuple<Number, Number> */): LatLng
        fun coordsToLatLngs(
            coords: Array<Any>,
            levelsDeep: Number = definedExternally,
            coordsToLatLng: (coords: Array<Number> /* JsTuple<Number, Number> | JsTuple<Number, Number, Number> */) -> LatLng = definedExternally
        ): Array<Any>

        fun latLngToCoords(latlng: LatLng): Array<Number> /* JsTuple<Number, Number> | JsTuple<Number, Number, Number> */
        fun latLngsToCoords(
            latlngs: Array<Any>,
            levelsDeep: Number = definedExternally,
            closed: Boolean = definedExternally
        ): Array<Any>

        fun asFeature(geojson: Feature<GeoJsonGeometry>): Feature<GeoJsonGeometry>
        fun asFeature(geojson: GeoJsonGeometry): Feature<GeoJsonGeometry>
    }

    interface GeoJSONOptions : InteractiveLayerOptions {

        /**
         * A Function defining how GeoJSON points spawn Leaflet layers. It is internally called
         * when data is added, passing the GeoJSON [Feature<Point>][externals.geojson.Feature]
         * and its [LatLng].
         *
         * The default is to spawn a default Marker:
         */
        val pointToLayer: ((geoJsonPoint: Feature<Point>, latlng: LatLng) -> Layer<*>)?
        /**
         * 	A Function defining the [PathOptions] for styling GeoJSON lines and polygons, called
         * 	internally when data is added.
         *
         * 	The default value is to not override any defaults:
         */
        var style: StyleFunction /* PathOptions? | StyleFunction<P>? */
        val onEachFeature: ((feature: Feature<GeoJsonGeometry>, layer: Layer<*>) -> Unit)?
        val filter: ((geoJsonFeature: Feature<GeoJsonGeometry>) -> Boolean)?
        /**
         * A Function that will be used for converting GeoJSON coordinates to [LatLng]s.
         *
         * The default is [GeoJSON.Companion.coordsToLatLng].
         */
        val coordsToLatLng: ((coords: Array<Number> /* JsTuple<Number, Number> | JsTuple<Number, Number, Number> */) -> LatLng)?
        var markersInheritOptions: Boolean?
    }

}
