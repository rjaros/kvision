@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.geojson.Feature
import externals.geojson.GeoJsonObject
import externals.leaflet.geo.LatLng
import externals.leaflet.layer.vector.PathOptions

open external class GeoJSON<P: Any>(
    geojson: GeoJsonObject = definedExternally,
    options: GeoJSONOptions<P> = definedExternally
) : FeatureGroup<P> {
    open fun addData(data: GeoJsonObject): GeoJSON<P> /* this */
    open fun resetStyle(layer: Layer = definedExternally): GeoJSON<P> /* this */
    override fun setStyle(style: PathOptions): GeoJSON<P> /* this */
    open fun setStyle(style: StyleFunction<P>): GeoJSON<P> /* this */
    open var options: GeoJSONOptions<P>

    companion object {
        fun <P> geometryToLayer(
            featureData: Feature<dynamic /* typealias GeometryObject = dynamic */, P>,
            options: GeoJSONOptions<P> = definedExternally
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

        fun <P> asFeature(geojson: Feature<dynamic /* typealias GeometryObject = dynamic */, P>): Feature<dynamic /* typealias GeometryObject = dynamic */, P>
        fun <P> asFeature(geojson: dynamic /* typealias GeometryObject = dynamic */): Feature<dynamic /* typealias GeometryObject = dynamic */, P>
    }
}
