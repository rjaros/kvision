@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.geojson.Feature
import externals.geojson.GeoJsonGeometry
import externals.geojson.GeoJsonObject
import externals.geojson.Point
import externals.leaflet.geo.LatLng
import externals.leaflet.layer.vector.Path.PathOptions

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
