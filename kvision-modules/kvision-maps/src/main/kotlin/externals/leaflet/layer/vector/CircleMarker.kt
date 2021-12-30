@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.geojson.Feature
import externals.leaflet.geo.LatLng

open external class CircleMarker<P> : Path {
    constructor(latlng: LatLng, options: CircleMarkerOptions = definedExternally)
    constructor(latlng: LatLng)
//    constructor(latlng: LatLngLiteral, options: CircleMarkerOptions = definedExternally)
//    constructor(latlng: LatLngLiteral)
//    constructor(latlng: Any, options: CircleMarkerOptions = definedExternally)
//    constructor(latlng: Any)

    override var options: CircleMarkerOptions
    open var feature: Feature<externals.geojson.Point, P>?

    open fun toGeoJSON(precision: Number = definedExternally): Feature<externals.geojson.Point, P>
    open fun setLatLng(latLng: LatLng): CircleMarker<P> /* this */
//    open fun setLatLng(latLng: LatLngLiteral): CircleMarker<P> /* this */
//    open fun setLatLng(latLng: Any /* JsTuple<Number, Number> */): CircleMarker<P> /* this */
    open fun getLatLng(): LatLng
    open fun setRadius(radius: Number): CircleMarker<P> /* this */
    open fun getRadius(): Number
}
