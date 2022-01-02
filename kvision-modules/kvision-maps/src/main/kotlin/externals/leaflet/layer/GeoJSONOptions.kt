@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.geojson.Feature
import externals.geojson.GeoJsonGeometry
import externals.geojson.Point
import externals.leaflet.geo.LatLng

external interface GeoJSONOptions : InteractiveLayerOptions {
    val pointToLayer: ((geoJsonPoint: Feature<Point, *>, latlng: LatLng) -> Layer)?
    var style: dynamic /* PathOptions? | StyleFunction<P>? */
    val onEachFeature: ((feature: Feature<GeoJsonGeometry, *>, layer: Layer) -> Unit)?
    val filter: ((geoJsonFeature: Feature<GeoJsonGeometry, *>) -> Boolean)?
    val coordsToLatLng: ((coords: Any /* JsTuple<Number, Number> | JsTuple<Number, Number, Number> */) -> LatLng)?
    var markersInheritOptions: Boolean?
}
