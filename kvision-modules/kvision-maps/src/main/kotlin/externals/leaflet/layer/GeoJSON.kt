@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.geojson.Feature
import externals.geojson.GeoJsonGeometry
import externals.geojson.GeoJsonObject
import externals.leaflet.geo.LatLng
import externals.leaflet.layer.vector.PathOptions

/**
 * Represents a GeoJSON object or an array of GeoJSON objects.
 * Allows you to parse GeoJSON data and display it on the map.
 */
open external class GeoJSON(
    geojson: GeoJsonObject = definedExternally,
    options: GeoJSONOptions = definedExternally
) : FeatureGroup {

    open var options: GeoJSONOptions

    open fun addData(data: GeoJsonObject): GeoJSON /* this */
    open fun resetStyle(layer: Layer = definedExternally): GeoJSON /* this */
    override fun setStyle(style: PathOptions): GeoJSON /* this */
    open fun setStyle(style: StyleFunction): GeoJSON /* this */

    companion object {
        fun geometryToLayer(
            featureData: Feature<GeoJsonGeometry, *>,
            options: GeoJSONOptions = definedExternally
        ): Layer

        fun coordsToLatLng(coords: Any /* JsTuple<Number, Number> */): LatLng
        fun coordsToLatLngs(
            coords: Array<Any>,
            levelsDeep: Number = definedExternally,
            coordsToLatLng: (coords: Any /* JsTuple<Number, Number> | JsTuple<Number, Number, Number> */) -> LatLng = definedExternally
        ): Array<Any>

        fun latLngToCoords(latlng: LatLng): dynamic /* JsTuple<Number, Number> | JsTuple<Number, Number, Number> */
        fun latLngsToCoords(
            latlngs: Array<Any>,
            levelsDeep: Number = definedExternally,
            closed: Boolean = definedExternally
        ): Array<Any>

        fun asFeature(geojson: Feature<GeoJsonGeometry, *>): Feature<GeoJsonGeometry, *>
        fun asFeature(geojson: GeoJsonGeometry): Feature<GeoJsonGeometry, *>
    }
}
