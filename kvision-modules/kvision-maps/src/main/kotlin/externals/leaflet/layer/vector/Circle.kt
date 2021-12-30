@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.geojson.Feature
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.geometry.Point


open external class Circle<P> : CircleMarker<P> {
    constructor(latlng: LatLng, options: CircleMarkerOptions = definedExternally)
//    constructor(latlng: LatLng)
//    constructor(latlng: LatLngLiteral, options: CircleMarkerOptions = definedExternally)
//    constructor(latlng: LatLngLiteral)
//    constructor(latlng: Any, options: CircleMarkerOptions = definedExternally)
//    constructor(latlng: Any)
    constructor(latlng: LatLng, radius: Number, options: CircleMarkerOptions = definedExternally)
//    constructor(latlng: LatLng, radius: Number)
//    constructor(latlng: LatLngLiteral, radius: Number, options: CircleMarkerOptions = definedExternally)
//    constructor(latlng: LatLngLiteral, radius: Number)
//    constructor(latlng: Any, radius: Number, options: CircleMarkerOptions = definedExternally)
//    constructor(latlng: Any, radius: Number)
    open fun getBounds(): LatLngBounds
}
