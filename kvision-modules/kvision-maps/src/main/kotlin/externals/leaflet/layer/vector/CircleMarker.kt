@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.geojson.Feature
import externals.leaflet.geo.LatLng

open external class CircleMarker(
    latlng: LatLng,
    options: CircleMarkerOptions = definedExternally,
) : Path<CircleMarker.CircleMarkerOptions> {

    open var feature: Feature<externals.geojson.Point>?

    open fun toGeoJSON(precision: Number = definedExternally): Feature<externals.geojson.Point>
    open fun setLatLng(latLng: LatLng): CircleMarker /* this */
    open fun getLatLng(): LatLng
    open fun setRadius(radius: Number): CircleMarker /* this */
    open fun getRadius(): Number

    interface CircleMarkerOptions : PathOptions {
        var radius: Number?
    }

}
