@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.leaflet.geo.LatLng


open external class Polygon<P : Any>
    : Polyline<dynamic /* geojson.Polygon | geojson.MultiPolygon */, P> {
    constructor(
        latlngs: Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>,
        options: PolylineOptions = definedExternally
    )
    //    constructor(latlngs: Array<Any /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>)
    constructor(
        latlngs: Array<Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>,
        options: PolylineOptions = definedExternally
    )
    //    constructor(latlngs: Array<Array<Any /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>)
    constructor(
        latlngs: Array<Array<Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>>,
        options: PolylineOptions = definedExternally
    )
//    constructor(latlngs: Array<Array<Array<Any /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>>)
}