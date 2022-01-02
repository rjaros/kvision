@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.leaflet.geo.LatLng

/**
 * A class for drawing polygon overlays on a map. Extends [Polyline].
 *
 * Note that points you pass when creating a polygon shouldn't have an additional last point equal
 * to the first one — it's better to filter out such points.
 */
open external class Polygon
    : Polyline<dynamic /* geojson.Polygon | geojson.MultiPolygon */, Any> {

    /** Create a polygon from an array of LatLng points. */
    constructor(
        latlngs: Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>,
        options: PolylineOptions = definedExternally
    )

    /**
     * @param[latlngs] An array of arrays of [LatLng], with the first array representing the
     * outer shape and the other arrays representing holes in the outer shape.
     */
    constructor(
        latlngs: Array<Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>,
        options: PolylineOptions = definedExternally
    )

    /**
     * @param[latlngs] a multi-dimensional array to represent a MultiPolygon shape.
     */
    constructor(
        latlngs: Array<Array<Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>>,
        options: PolylineOptions = definedExternally
    )

}
