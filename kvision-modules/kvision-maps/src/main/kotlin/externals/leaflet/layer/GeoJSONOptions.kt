@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.geojson.Feature
import externals.geojson.Point
import externals.leaflet.geo.LatLng

external interface GeoJSONOptions<P> : InteractiveLayerOptions {
    val pointToLayer: ((geoJsonPoint: Feature<Point, P>, latlng: LatLng) -> Layer)?
    var style: dynamic /* PathOptions? | StyleFunction<P>? */
    val onEachFeature: ((feature: Feature<dynamic /* typealias GeometryObject = dynamic */, P>, layer: Layer) -> Unit)?
    val filter: ((geoJsonFeature: Feature<dynamic /* typealias GeometryObject = dynamic */, P>) -> Boolean)?
    val coordsToLatLng: ((coords: Any /* JsTuple<Number, Number> | JsTuple<Number, Number, Number> */) -> LatLng)?
    var markersInheritOptions: Boolean?
}
